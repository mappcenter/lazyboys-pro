/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author root
 */
public class getConfig {

    private static String host;
    private static int port;
    private static int port_listen;
    private static getConfig instance;
    private static int ttl;

    /**
     * @return the ttl
     */
    public int getTtl() {
        return ttl;
    }

    /**
     * @param aTtl the ttl to set
     */
    public void setTtl(int aTtl) {
        ttl = aTtl;
    }

    public getConfig() throws FileNotFoundException, IOException {
        Properties pro = new Properties();
        pro.load(new FileInputStream("src/conf/config.ini"));

        port_listen = Integer.parseInt(pro.getProperty("port_listen"));
        port = Integer.parseInt(pro.getProperty("port"));
        host = pro.getProperty("host");
        ttl = Integer.parseInt(pro.getProperty("ttl"));
    }

    public static synchronized getConfig getInstance() throws FileNotFoundException, IOException {
        if (instance == null) {
            instance = new getConfig();
        }
        return instance;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getPortListen() {
        return port_listen;
    }
}
