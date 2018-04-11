package app.connectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.data.redis.domain.RedisNotification;
import app.data.redis.repository.RedisListRepository;
import app.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NotificationListener {
	private ServerSocket						serverSocket;
	private volatile boolean					wasLoaded	= false;

	public static ConcurrentLinkedQueue<String>	queue		= new ConcurrentLinkedQueue<>();

	@Autowired
	RedisListRepository							redisListRepository;

	@PostConstruct
	private void init() {
		new Thread(() -> {
			startSocket();
		}).start();

	}

	private void startSocket() {
		try {
			if (initConfigurations()) {
				try {

					while (true) {
						Socket clientSocket = serverSocket.accept();
						log.debug("startSocket() | Connected to: " + clientSocket.getRemoteSocketAddress());
						InputStream in = clientSocket.getInputStream();
						processingData(in);
						log.debug("startSocket() | Ending connection: " + clientSocket.getRemoteSocketAddress());
						clientSocket.close();
					}
				} catch (Exception e) {
					log.error("startSocket() | ERROR | " + LocalDateTime.now() + " | " + e.getMessage());
				}
			}
		} catch (Exception e) {
			log.error("startSocket() | Error starting the socket | " + LocalDateTime.now() + " | " + e.getMessage());
			log.debug("ERROR", e);

		}
	}

	@SuppressWarnings("unchecked")
	private void processingData(InputStream in) {
		try {
			String response = processHttpResponse(in);
			// save to a queue or send it to ServerThread socket
			if (response != null && !response.isEmpty()) {
				log.info("processingData() | Got Response:  | " + response);
				JSONObject jsonObject = (JSONObject) new JSONParser().parse(response);
				JSONArray jsonArray = (JSONArray) jsonObject.get("notifications");

				// for each notification received check what sessions refer to the respective
				// macAddres and add a new notification
				jsonArray.parallelStream().forEach(object -> {
					JSONObject notif = (JSONObject) object;
					String deviceId = (String) notif.get("deviceId");
					String redisNotif = JsonUtil.toJsonString(notif);
					queue.add(redisNotif);
					String MacKey = redisListRepository.getMacAddressKey(deviceId);
					RedisNotification notification = RedisNotification.builder().notification(redisNotif).build();

					redisListRepository.saveRData(MacKey, JsonUtil.toJsonString(notification));
					log.debug("processingData() | Save notification to redis:  | " + redisNotif);
				});
			}
		} catch (Exception e) {
			log.error("processingData() | Error processing data", e);
		}
	}

	private synchronized boolean initConfigurations() {
		if (!wasLoaded) {
			try {
				serverSocket = new ServerSocket(8000);
				wasLoaded = true;
				log.info("initConfigurations() | Server Listening on " + serverSocket.getInetAddress() + ":"
						+ serverSocket.getLocalPort());
			} catch (Exception e) {
				log.error("initConfigurations() | Error creating the socket | " + e.getMessage());
				log.debug("ERROR", e);
			}
		}
		return wasLoaded;
	}

	@SuppressWarnings("unused")
	private String processResponseFromClient(InputStream inputStream) throws IOException {
		String response = null;
		try {
			byte[] buffer = new byte[1024];
			int read;
			while (inputStream.available() > 0 && (read = inputStream.read(buffer)) != -1) {
				String output = new String(buffer, 0, read);
				response = output;
			}
		} catch (Exception e) {
			log.error("processResponseFromClient() | Error processing response from client | ", e);
		}
		return response;

	}

	private static String processHttpResponse(InputStream inputStream) throws IOException {
		String response = null;
		try {
			if (inputStream != null && inputStream.available() > 0) {
				InputStreamReader isReader = new InputStreamReader(inputStream);
				BufferedReader br = new BufferedReader(isReader);

				// code to read and print headers
				String headerLine = null;
				List<String> headers = new ArrayList<>();
				while ((headerLine = br.readLine()).length() != 0) {
					headers.add(headerLine);
				}
				log.debug("Headers: " + headers.toString());

				// code to read the post payload data
				StringBuilder payload = new StringBuilder();
				while (br.ready()) {
					payload.append((char) br.read());
				}
				response = payload.toString();
				log.debug("Body: " + payload.toString());

			}
		} catch (Exception e) {
			log.error("processHttpResponse() | Error processing response from client | ", e);
		}
		return response;
	}

	@PreDestroy
	private void close() {
		try {
			serverSocket.close();
		} catch (Exception e) {
			log.error("close() | Error closing socket | " + e.getMessage());
		}

	}

}
