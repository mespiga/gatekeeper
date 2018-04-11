package app.connectors;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import app.connectors.DriverShim;
import app.properties.ReportConstants;
import org.springframework.web.util.UriComponentsBuilder;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.BufferedReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.io.IOUtils;
import java.util.Base64;

public class ConnectionUtils extends ReportConstants{

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public ConnectionUtils() {
		super();
		// TODO Auto-generated constructor stub
	}


	public static Connection getConnectionJDBC(boolean isLive,String ambiente){
		
		// Connection conn = null;
		// // ResultSet rs = null;
		// 		String testName = "Connect to Informix database";
		// 		System.out.println(">>>" + testName + " test.");
		// 		System.out.println("URL = \"" + ambiente + "\"");

		// 		try {
		// 			String instancia=INSTANCE_DEV;
		// 			String user = USER_DEV;
		// 			String pass = PASS_DEV;
		// 			if (ambiente.compareTo(IP_PROD)==0){
		// 				if (isLive){
		// 					ambiente = IP_PROD_LIVE;
		// 					instancia= INSTANCE_PROD_LIVE;
		// 				}else{
		// 					instancia= INSTANCE_PROD;
		// 				}
		// 				user = USER_PROD;
		// 				pass = PASS_PROD;
						
		// 			}
		// 			Driver d = new com.informix.jdbc.IfxDriver();
		// 			// Driver d = new IfxDriver();
		// 			DriverManager.registerDriver(new DriverShim(d));
		// 			conn = DriverManager.getConnection("jdbc:informix-sqli://"
		// 					+ ambiente + ":1504/db_cra:informixserver=" + instancia,
		// 					user, pass);
		// 			// conn = DriverManager.getConnection("jdbc:informix-sqli://"
		// 			// 		+ ambiente + ":1504/db_cra:informixserver=" + INSTANCE_DEV,
		// 			// 		USER_DEV, PASS_DEV);
		// 		} catch (Exception e) {
		// 			System.out.println("ERROR: failed to load Informix JDBC driver.");
		// 		}
		// 		return conn;
		return null;
	}

	public boolean byPassSSL() {
		/*
	     *  fix for
	     *    Exception in thread "main" javax.net.ssl.SSLHandshakeException:
	     *       sun.security.validator.ValidatorException:
	     *           PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
	     *               unable to find valid certification path to requested target
	     */
	    TrustManager[] trustAllCerts = new TrustManager[] {
	       new X509TrustManager() {
	          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	            return null;
	          }

	          public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

	          public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

	       }
	    };
	    try{
		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		    // Create all-trusting host name verifier
		    HostnameVerifier allHostsValid = new HostnameVerifier() {
		        public boolean verify(String hostname, SSLSession session) {
		          return true;
		        }
		    };
		    // Install the all-trusting host verifier
		    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

		    /*
		     * end of the fix
		     */
		}catch(Exception e){
			log.error(e.getMessage());
		}

		return true;
	}

	public String doGet(String url){
			try{

			String userPassword = (PROD?CMX_USER_PROD:CMX_USER_DEV)  + ":" + (PROD?CMX_PASS_PROD:CMX_PASS_DEV);
	  		String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());

            UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
			String host = "https://" + (PROD?CMX_IP_PROD:CMX_IP_DEV)+":"+(PROD?CMX_PORT_PROD:CMX_PORT_DEV);
			builder = builder.fromHttpUrl(host);

			String uriString = builder.build().encode().toUriString() + url;
			log.info("Retrieving data from: " + uriString);
			URL obj = new URL(uriString);
			HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();

			//add request header
			conn.setRequestMethod("GET");

			conn.setRequestProperty("X-Frame-Options","SAMEORIGIN");
			//conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + encoding);
			conn.setFollowRedirects(true);
			// Send get request
			conn.setDoOutput(true);
			// DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			// wr.writeBytes(data);
			// wr.flush();
			// wr.close();

			if (conn.getResponseCode() != 200) {
	            log.error("Error code: " + conn.getResponseCode());
	            BufferedReader br = new BufferedReader(new  InputStreamReader(conn.getInputStream()));
	            StringBuilder sb = new StringBuilder();
	            String line;

	            while ((line = br.readLine()) != null) {
	                sb.append(line);
	            }
	            br.close();
	            conn.disconnect();

	            String returnedObject = sb.toString();

	            if (returnedObject != null) {
	                log.debug("If 400, this is the object gained: " +     returnedObject);
	            } else {
	                log.error("didn't get any JSON object");
	            }

	            conn.disconnect();
	            log.debug("Returned object: " + returnedObject);
	            return returnedObject;

	        }
	        else {
	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            log.info("Got successful response with response code = " + conn.getResponseCode());
	            StringBuilder sb = new StringBuilder();

	            String line;

	            while ((line = br.readLine()) != null) {
	                sb.append(line);
	            }
	            br.close();
	            conn.disconnect();

				String returnedObject = sb.toString();

				log.debug("RETURNING ");
	            log.debug(returnedObject);

	            return returnedObject;
	        }
		}catch(Exception e){
			log.error(e.getMessage());
		}

		return "Maps: none";
	}

	public String doGetImage(String url){
		try{

			String userPassword = (PROD?CMX_USER_PROD:CMX_USER_DEV)  + ":" + (PROD?CMX_PASS_PROD:CMX_PASS_DEV);
	  		String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());

			String host = "https://" + (PROD?CMX_IP_PROD:CMX_IP_DEV)+":"+(PROD?CMX_PORT_PROD:CMX_PORT_DEV);
            UriComponentsBuilder builder = UriComponentsBuilder.newInstance().fromHttpUrl(host+url);

			String uriString = builder.build().encode().toUriString();

			log.info("Retrieving image from: " + uriString);
			URL obj = new URL(uriString);
			HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();

			//add request header
			conn.setRequestMethod("GET");

			conn.setRequestProperty("X-Frame-Options","SAMEORIGIN");
			//conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + encoding);
			conn.setFollowRedirects(true);
			// Send get request
			conn.setDoOutput(true);
			// DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			// wr.writeBytes(data);
			// wr.flush();
			// wr.close();

			if (conn.getResponseCode() != 200) {
	            log.error("Error code: " + conn.getResponseCode());
	            BufferedReader br = new BufferedReader(new  InputStreamReader(conn.getInputStream()));
	            StringBuilder sb = new StringBuilder();
	            String line;

	            while ((line = br.readLine()) != null) {
	                sb.append(line);
	            }
	            br.close();
	            conn.disconnect();

	            String returnedObject = sb.toString();

	            if (returnedObject != null) {
	                log.info("If 400, this is the object gained: " +     returnedObject);
	            } else {
	                log.error("didn't get any JSON object");
	            }

	            conn.disconnect();
	            log.debug("RETURN:");
	            log.debug(returnedObject);
	            return returnedObject;

	        }
	        else {
	            //BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            log.info("Got sucessful response with response code = " + conn.getResponseCode());

				byte[] imageBytes = IOUtils.toByteArray(conn.getInputStream());
				String encoded = Base64.getEncoder().encodeToString(imageBytes);

	            conn.disconnect();

				log.debug("RETURN:");
	            log.debug(encoded);

	            return encoded;
	        }

			// int responseCode = conn.getResponseCode();
			// System.out.println("\nSending 'GET' request to URL : " + url);
			// System.out.println("Response Code : " + responseCode);
			// System.out.println("Response Message : " + conn.getResponseMessage());
		}catch(Exception e){
			log.error(e.getMessage());
		}

		return "image: none";
	}
	
	public String doPut(String url, String data) {
		return doCall("PUT", url, data);
	}
	
	public String doPost( String url, String data) {
		return doCall("POST", url, data);
	}
	
	public String doDelete(String url, String data) {
		return doCall("DELETE", url, data);
	}
	
	public String doCall(String method, String url, String data) {
		try {

			String userPassword = (PROD ? CMX_USER_PROD : CMX_USER_DEV) + ":" + (PROD ? CMX_PASS_PROD : CMX_PASS_DEV);
			String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());

			UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
			String host = "https://" + (PROD ? CMX_IP_PROD : CMX_IP_DEV) + ":" + (PROD ? CMX_PORT_PROD : CMX_PORT_DEV);
			builder = builder.fromHttpUrl(host);

			String uriString = builder.build().encode().toUriString() + url;
			log.info("Retrieving data from: " + uriString);
			URL obj = new URL(uriString);
			HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();

			// add request header
			conn.setRequestMethod(method);

			conn.setRequestProperty("X-Frame-Options", "SAMEORIGIN");
			conn.setRequestProperty("Accept", "application/json");
			conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setRequestProperty("Authorization", "Basic " + encoding);
			conn.setFollowRedirects(true);
			// Send get request
			conn.setDoOutput(true);
			if(data!=null){
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				wr.writeBytes(data);
				wr.flush();
				wr.close();
			}

			if (conn.getResponseCode() > 299) {
				try {
					log.error("Error code: " + conn.getResponseCode());
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					StringBuilder sb = new StringBuilder();
					String line;

					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
					br.close();
					conn.disconnect();

					String returnedObject = sb.toString();

					if (returnedObject != null) {
						log.debug("If 400, this is the object gained: " + returnedObject);
					} else {
						log.error("didn't get any JSON object");
					}

					conn.disconnect();
					log.debug("Returned object: " + returnedObject);
					return returnedObject;
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				log.info("Got successful response with response code = " + conn.getResponseCode());
				StringBuilder sb = new StringBuilder();

				String line;

				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
				conn.disconnect();

				String returnedObject = sb.toString();

				log.debug("RETURNING ");
				log.debug(returnedObject);

				return returnedObject;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return null;
	}

}