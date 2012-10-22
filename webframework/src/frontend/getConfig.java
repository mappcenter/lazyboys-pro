/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import com.vng.jcore.common.Config;

/**
 *
 * @author root
 */
public class getConfig {
    private static int itemNumber;
    private static int itemPerPage;
    private static String host;
    private static int port;
    private static int port_listen;
    private static getConfig instance;
    
    
    public getConfig(){
        itemNumber=Integer.parseInt(Config.getParam("paging", "itemNumber"));
        itemPerPage=Integer.parseInt(Config.getParam("paging", "itemPerPage"));
        host=Config.getParam("fresher2012service", "host");
        port=Integer.valueOf(Config.getParam("fresher2012service", "port"));
        port_listen=Integer.valueOf(Config.getParam("rest", "port_listen"));
    }
    
    public static synchronized getConfig getInstance(){
        if(instance==null) {
            instance=new getConfig();
        }
        return instance;
    }
    
    public int getItemPerPackage(){
        return itemNumber;
    }
    public int getItemPerPage(){
        return itemPerPage;
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
