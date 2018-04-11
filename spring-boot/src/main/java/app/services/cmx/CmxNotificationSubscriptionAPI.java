package app.services.cmx;

import static app.properties.ReportConstants.CMX_USER_DEV;
import static app.properties.ReportConstants.CMX_USER_PROD;
import static app.properties.ReportConstants.PROD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.connectors.ConnectionUtils;
import app.data.cmx.notification.Notification.NotificationBuilder;
import app.data.cmx.notification.Rules;
import app.data.cmx.notification.Rules.RulesBuilder;
import app.data.cmx.notification.Subscribers;
import app.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CmxNotificationSubscriptionAPI {

	private boolean isToSendAllNotifications = false;

	public void createNotificationByMacAddress(String name, String mac) {
		try {
			ConnectionUtils httpsClient = new ConnectionUtils();
			httpsClient.byPassSSL();

			NotificationBuilder notifBuilder = app.data.cmx.notification.Notification.builder()
					.name(name)
					.notificationType("Movement")
					.enabled(true)
					.userId(PROD ? CMX_USER_PROD : CMX_USER_DEV)
					.enableMacScrambling("false");

			List<Rules> rules = new ArrayList<>();

			RulesBuilder rulesBuilder = Rules.builder().conditions("movement.distance > 1");
			if (!isToSendAllNotifications) {
				rulesBuilder.conditions("movement.macAddressList == " + mac);
			}
			rules.add(rulesBuilder.build());
			notifBuilder.rules(rules);

			List<Subscribers> subscribers = new ArrayList<>();
			List<Map<String, String>> receivers = new ArrayList<>();
			Map<String, String> receiver = new HashMap<>();
			receiver.put("uri", PROD ? "http://172.25.255.99:8000" : "http://127.0.0.1:8080");
			receiver.put("messageFormat", "JSON");
			receiver.put("qos", "AT_MOST_ONCE");
			receivers.add(receiver);
			subscribers.add(Subscribers.builder().receivers(receivers).build());
			notifBuilder.subscribers(subscribers);

			String notifJson = JsonUtil.toJsonString(notifBuilder.build());
			log.debug(notifJson);
			httpsClient.doPut("/api/config/v1/notification", notifJson);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public void deleteNotificationByName(String name) {
		try {
			ConnectionUtils httpsClient = new ConnectionUtils();
			httpsClient.byPassSSL();

			httpsClient.doDelete("/api/config/v1/notifications/" + name, null);
			log.info("deleteNotificationByName() | " + name + " | DELETED ");
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public String getAllNotificationSubscriptions() {
		String result = null;
		try {
			ConnectionUtils httpsClient = new ConnectionUtils();
			httpsClient.byPassSSL();

			result = httpsClient.doGet("/api/config/v1/notifications");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;

	}

	public String getNotificationSubscriptionByName(String name) {
		String result = null;
		try {
			ConnectionUtils httpsClient = new ConnectionUtils();
			httpsClient.byPassSSL();

			result = httpsClient.doGet("/api/config/v1/notifications/" + name);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;

	}

}
