package app.services.cmx;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import app.connectors.ConnectionUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CmxLocationAPI {
	
	/** ACTIVE CLIENTS API*/
	public String getActiveClients() {
		String result = null;
		try {
			ConnectionUtils httpsClient = new ConnectionUtils();
			httpsClient.byPassSSL();

			result = httpsClient.doGet("/api/location/v2/clients");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;

	}
	
	public int getTotalActiveClients() {
		int result = 0;
		try {
			ConnectionUtils httpsClient = new ConnectionUtils();
			httpsClient.byPassSSL();

			//{"deviceType": "Wireless_Client","deviceQueryString": null,"count": 72}
			String response  = httpsClient.doGet("/api/location/v2/clients/count");
			if(response!=null) {
				JSONObject jsonObj = (JSONObject) new JSONParser().parse(response);
				result = (int) jsonObj.get("count");
				
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;

	}

}
