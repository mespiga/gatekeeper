package app.utils;
 
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import app.properties.ReportConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetProperties {
	private String result = "";
	private InputStream inputStream;
	private String propFileName;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	protected static final String PROP_CONFIG_TYPE_PROPS = "PROPERTIES";
	protected static final String PROP_CONFIG_TYPE_LS = "LOGGEDSTATUS";
	protected static final String PROP_CONFIG_TYPE_PATH = "PATHS";
 
	public GetProperties(String configType){
		if(ReportConstants.PROD){
			switch (configType) {
	            case PROP_CONFIG_TYPE_PROPS:  
	            		this.propFileName = ReportConstants.PROD_FILE_NAME_MESSAGES_PATH;
	                    break;
	            case PROP_CONFIG_TYPE_LS:  
	            		this.propFileName = ReportConstants.PROD_FILE_NAME_LOGGEDSTATUS_PATH;
	                    break;
	            case PROP_CONFIG_TYPE_PATH:  
	            		this.propFileName = ReportConstants.PROD_FILE_NAME_PATH;
	                    break;
	        }
	    }else{
	    	switch (configType) {
	            case PROP_CONFIG_TYPE_PROPS:  
	            		this.propFileName = ReportConstants.DEV_FILE_NAME_MESSAGES_PATH;
	                    break;
	            case PROP_CONFIG_TYPE_LS:  
	            		this.propFileName = ReportConstants.DEV_FILE_NAME_LOGGEDSTATUS_PATH;
	                    break;
	            case PROP_CONFIG_TYPE_PATH:  
	            		this.propFileName = ReportConstants.DEV_FILE_NAME_PATH;
	                    break;
	        }
	    }
	}

	public String getConfigValues(String key) throws IOException {
 		String values = "";
		try {
			Properties prop = new Properties();
			// String propFileName = PROP_FILE_NAME;
 
 			FileInputStream fileInputStream = new FileInputStream(this.propFileName);
			if (fileInputStream != null) {
				// prop.load(inputStream);
				prop.load(new FileInputStream(this.propFileName));
			} else {
				throw new FileNotFoundException("property file '" + this.propFileName + "' not found in the classpath");
			}
 
			Date time = new Date(System.currentTimeMillis());
 
			// get the property value and print it out
			values = prop.getProperty(key);

 
			result = "Config Values = " + values;
			log.info(result);
		} catch (Exception e) {
			log.error("Exception: " + e);
		} finally {

		}
		return values;
	}

	public List<String> getAllConfigValues() throws IOException {
		log.info("GetProperties.getAllConfigValues <-");
		List<String> valuesList = null;
		try {
			Properties prop = new Properties();
			// String propFileName = PROP_FILE_NAME;
 
 			FileInputStream fileInputStream = new FileInputStream(this.propFileName);
			if (fileInputStream != null) {
				// prop.load(inputStream);
				prop.load(new FileInputStream(this.propFileName));
			} else {
				throw new FileNotFoundException("property file '" + this.propFileName + "' not found in the classpath");
			}
 
			Date time = new Date(System.currentTimeMillis());
 
			// get ALL the property value and print it out
			Set<String>	 valuesSet 	= prop.stringPropertyNames();
			valuesList 				= new ArrayList<String>(valuesSet);

 			for(int i=0;i<valuesList.size();i++){
 				valuesList.set(i,getConfigValues(valuesList.get(i)));
				log.debug("Config Values = " + valuesList.get(i) );

			}
			
		} catch (Exception e) {
			log.error("Exception: " + e);
		} finally {

		}
		return valuesList;
	}

	
}