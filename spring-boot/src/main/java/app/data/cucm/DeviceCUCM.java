package app.data.cucm;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeviceCUCM {

	String 	name;
	String 	css;
	String 	ext;
	String 	desc;

	public DeviceCUCM(String name, String css){
		this.name 	= name;
		this.css 	= css;
	}

	public DeviceCUCM(String name, String css, String ext, String desc){
		this.name 	= name;
		this.css 	= css;
		this.ext 	= ext;
		this.desc 	= desc;
	}

		public DeviceCUCM(String name, String ext, String desc){
		this.name 	= name;
		this.ext 	= ext;
		this.desc 	= desc;
	}

	public DeviceCUCM(){
	}

	
	@XmlElement
	public void setCss(String css) {
		this.css = css;
	}

	@XmlElement
	public String getCss() {
		return css;
	}

	@XmlElement
	public void setDesc(String desc) {
		this.desc = desc;
	}

	@XmlElement
	public String getDesc() {
		return desc;
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
	public void setExt(String ext) {
		this.ext = ext;
	}

	@XmlElement
	public String getExt() {
		return ext;
	}
}