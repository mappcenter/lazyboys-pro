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

    private String host;
    private int port;
    private int port_listen;
    private static getConfig instance;

    public getConfig() throws FileNotFoundException, IOException {
        Properties pro = new Properties();
        pro.load(new FileInputStream("src/conf/config.ini"));

        port_listen = Integer.parseInt(pro.getProperty("port_listen"));
        port = Integer.parseInt(pro.getProperty("port"));
        host = pro.getProperty("host");
    }
    
    public static synchronized getConfig getInstance() throws FileNotFoundException, IOException{
        if(instance==null){
            instance=new getConfig();
        }
        return instance;
    }
    
    public String getHost(){
        return host;
    }
    
    public int getPort(){
        return port;
    }
    
    public int getPortListen(){
        return port_listen;
    }
}
