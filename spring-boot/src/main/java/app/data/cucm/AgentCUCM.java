package app.data.cucm;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AgentCUCM {

	String macid;
	String name;
	String userid;

	String telephonenumber;
	String mobile;
	String department;
	String building;
	String site;
	String email;


	public AgentCUCM(String macid, String userid, String name){
		this.setMacid(macid);
		this.setUserid(userid);
		this.setName(name);
	}

	public AgentCUCM(String telephonenumber, String mobile, String name, String department, String building, String site, String email){
		this.setTelephonenumber(telephonenumber);
		this.setMobile(mobile);
		this.setDepartment(department);
		this.setBuilding(building);
		this.setSite(site);
		this.setEmail(email);
		this.setName(name);
		this.setMacid("");
		this.setUserid("");

	}

	public String getMacid() {
		return macid;
	}

	@XmlElement
	public void setMacid(String macid) {
		this.macid = macid;
	}

	@XmlElement
	public void setUserid(String userid) {
		this.userid = userid;
	}

	@XmlElement
	public String getUserid() {
		return userid;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement
	public String getEmail() {
		return email;
	}

	@XmlElement
	public void setSite(String site) {
		this.site = site;
	}

	@XmlElement
	public String getSite() {
		return site;
	}
	@XmlElement
	public void setBuilding(String building) {
		this.building = building;
	}

	@XmlElement
	public String getBuilding() {
		return building;
	}
	@XmlElement
	public void setDepartment(String department) {
		this.department = department;
	}

	@XmlElement
	public String getDepartment() {
		return department;
	}

	@XmlElement
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@XmlElement
	public String getMobile() {
		return mobile;
	}

	@XmlElement
	public void setTelephonenumber(String telephonenumber) {
		this.telephonenumber = telephonenumber;
	}

	@XmlElement
	public String getTelephonenumber() {
		return telephonenumber;
	}
}