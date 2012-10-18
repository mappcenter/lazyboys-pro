/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import zme.api.core.ZME_Config;
import zme.api.core.ZME_Environment;
import zme.api.oauth.ZME_Authentication;

/**
 *
 * @author root
 */
public class MyAppInfo {

    private static String appName;
    private static String appKey;
    private static String secretKey;
    private static MyAppInfo instance = null;
    private static ZME_Config zConfig = null;
    /**
     * @return the zConfig
     */
    private ZME_Authentication zme;
    private static Properties prop;

    private MyAppInfo() throws FileNotFoundException, IOException {
        prop = new Properties();
        prop.load(new FileInputStream("src/conf/config.ini"));
        appName = prop.getProperty("appName");
        appKey = prop.getProperty("appKey");
        secretKey = prop.getProperty("secretKey");
        String enviroment = prop.getProperty("environment");
        ZME_Environment z = ZME_Environment.DEVELOPMENT;
        try {
            zConfig = new ZME_Config(appName, appKey, secretKey, z);
        } catch (Exception ex) {
        }
    }

    public static synchronized MyAppInfo getInstance() throws FileNotFoundException, IOException {
        if (instance == null) {
            instance = new MyAppInfo();
        }
        return instance;
    }

    /**
     * @return the appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * @param appName the appName to set
     */
    public void setAppName(String appName) {
        MyAppInfo.appName = appName;
    }

    /**
     * @return the appKey
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * @param appKey the appKey to set
     */
    public void setAppKey(String appKey) {
        MyAppInfo.appKey = appKey;
    }

    /**
     * @return the secretKey
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * @param secretKey the secretKey to set
     */
    public void setSecretKey(String secretKey) {
        MyAppInfo.secretKey = secretKey;
    }

    public String getUrlToRedirect() {
        return prop.getProperty("urlAuthorCodeRedirect");
    }

    /**
     * @return the zConfig
     */
    public ZME_Config getzConfig() {
        return zConfig;
    }

    /**
     * @param zConfig the zConfig to set
     */
    public void setzConfig(ZME_Config zConfig) {
        MyAppInfo.zConfig = zConfig;
    }
}
