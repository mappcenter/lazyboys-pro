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
    private static getConfig instance;
    public getConfig(){
        itemNumber=Integer.parseInt(Config.getParam("paging", "itemNumber"));
        itemPerPage=Integer.parseInt(Config.getParam("paging", "itemPerPage"));
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
}
