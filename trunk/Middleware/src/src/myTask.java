/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import libs.Item;
import libs.Tag;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class myTask extends TimerTask {

    private static List<Tag> listTag_update;
    private static List<Tag> listTag_temp;
    private static Map<String, Object> local_cache_update = new HashMap<>();
    private static Map<String, Object> local_cache_temp = new HashMap<>();

    @Override
    public void run() {
        try {
            swapCache();
        } catch (TException | IOException ex) {
            Logger.getLogger(myTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void swapCache() throws TException, IOException {
        BackendHandler handler = new BackendHandler();
        System.out.println("start swap cache ...");
        listTag_update = handler.getAllTag();
        listTag_temp = FrontendHandler.listTag;
        FrontendHandler.listTag = listTag_update;
        listTag_update = listTag_temp;
        listTag_temp.clear();

        int numberItemID = 0;
        int numberItemCache = 0;
        try {
            numberItemID = getConfig.getInstance().numberItemofTag();
            numberItemCache = getConfig.getInstance().numberItemCache();
        } catch (Exception ex) {
        }
        for (Tag tag : FrontendHandler.listTag) {
            local_cache_update.put("getTag" + tag.tagID, tag);
            List<Item> listItem = handler.getAllItemshaveTag(tag.tagID, numberItemID);
            for (Item item : listItem) {
                local_cache_update.put("item" + item.itemID, item);
            }
        }
        List<Item> listItem = handler.getAllItems(numberItemCache);
        for (Item item : listItem) {
            local_cache_update.put("item" + item.itemID, item);
        }
        List<String> listUserID = handler.getAllUser();
        for (String userID : listUserID) {
            List<String> listItemIDDislike = handler.getAllItemsIDDislike(userID);
            local_cache_update.put("getAllItemsIDDislike" + userID, listItemIDDislike);
            List<String> listItemIDLike = handler.getAllItemsIDLike(userID);
            local_cache_update.put("getAllItemsIDLike" + userID, listItemIDLike);
        }
        local_cache_temp = FrontendHandler.local_cache;
        FrontendHandler.local_cache = local_cache_update;
        local_cache_update = local_cache_temp;
        local_cache_temp.clear();
        System.out.println("Swap cache complete ...");
    }
}
