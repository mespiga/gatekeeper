package app.data.cmx.notification;

import static app.properties.ReportConstants.CMX_USER_DEV;
import static app.properties.ReportConstants.CMX_USER_PROD;
import static app.properties.ReportConstants.PROD;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
public class Notification {

	@Getter public String				name;
	@Getter public String				notificationType	= "Movement";
	@Getter	public boolean				enabled;
	@Getter	public String				userId				= PROD?CMX_USER_PROD:CMX_USER_DEV;
	@Getter	public List<Rules>			rules;
	@Getter	public List<Subscribers>	subscribers;
	@Getter	public String				enableMacScrambling	= "false";
	@Getter	public String				macScramblingSalt;

}
