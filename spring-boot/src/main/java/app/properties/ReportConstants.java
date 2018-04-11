package app.properties;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

abstract public class ReportConstants {
	// PRODUCTION CMX
    public static final boolean PROD                    = false;
    public static final boolean STAGING                 = true;
    // public static final String  CMX_IP_PROD             = "172.31.207.224";
    // public static final String  CMX_IP_PROD             = "172.20.156.2";
    public static final String  CMX_IP_PROD             = "172.20.156.6";
    public static final String  CMX_PORT_PROD           = "443";
    public static final String  CMX_USER_PROD           = "admin";
    public static final String  CMX_PASS_PROD           = "CilnetQuisco1!";

    public static final String  CMX_IP_DEV             = "cmxlocationsandbox.cisco.com";
    public static final String  CMX_PORT_DEV           = "443";
    public static final String  CMX_USER_DEV           = "learning";
    public static final String  CMX_PASS_DEV           = "learning";

    public static final String SECRET                   = "56776riQW@#$w";
    public static final String AUTH                     = "cilnet";
    public static final int    EXPIRE                   = 60;

    // public static final String CLIENT_PROD                 = "http://172.25.255.99:8080";
    public static final String CLIENT_PROD                 = "*";
    public static final String CLIENT_DEV                  = "*";

    
    // COMMON CONSTANTS

    public static final DateFormat dateFormat    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String   PROD_FILE_NAME_MESSAGES_PATH   = "C:\\cilnet\\number-rotation\\cofidis.properties";
    public static final String   DEV_FILE_NAME_MESSAGES_PATH    = "/Users/miguelespiga/Documents/workspace/xcode/cofidis-update-callingParty/update-calling-party/src/main/resources/cofidis.properties";

    public static final String   PROD_FILE_NAME_PATH    = "/home/ubuntuadmin/remarkable-cilnet-sw/call-records/config/paths.properties";
    public static final String   DEV_FILE_NAME_PATH    = "/Users/miguelespiga/Documents/workspace/xcode/cofidis-update-callingParty/update-calling-party/src/main/resources/paths.properties";

    public static final String   DEV_FILE_NAME_LOGGEDSTATUS_PATH  = "/Users/miguelespiga/Documents/workspace/xcode/cofidis-update-callingParty/update-calling-party/src/main/resources/loggedStatus.config.properties";
    public static final String   PROD_FILE_NAME_LOGGEDSTATUS_PATH = "C:\\cilnet\\number-rotation\\cofidis.properties\\logs";
    

    public static final String   TOKEN          = "1234567890qwertyuiop";
    public static final String   TOKEN_DELETE   = "qwertyuiop1234567890";

}
