/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class MySwapLocalCache {

    MiddlewareHandler handler = new MiddlewareHandler();
    public static String itemIDTagsKey = "ItemIDTags";
    public static int numberTopTags = 30;
    public static int numberItemIDTags = 30;
    public static int itemIDTagSize = 0;
    public static int numberFavoriteItems = 20;
    public static String listAllTagsKey = "listAllTags";
    public static String listTopTagsKey = "listTopTags";
    public static Map<String, Object> LocalCache = new HashMap<String, Object>();
    public static Map<String, Object> UserLocalCache = new HashMap<String, Object>();

    public MySwapLocalCache(){
    }
    public static String temp = "";

    public void startMyLocalCache() throws TException {
        List<Tag> lTags = handler.getAllTag(); 

        LocalCache.put(listAllTagsKey, lTags);

        startCacheItemIDTags(lTags, numberItemIDTags);
        itemIDTagSize = numberItemIDTags;
        Item item = (Item) LocalCache.get("item" + temp);
        List<Tag> listTopTags = handler.getTopTags(numberTopTags);
        LocalCache.put(listTopTagsKey, listTopTags);

    }

    private void startCacheItemIDTags(List<Tag> tags, int numberItemIDs) throws TException {
        for (int i = 0; i < tags.size(); i++) {
            String key = itemIDTagsKey + tags.get(i).tagID;
            List<String> itemIDs = handler.getAllItemsIDhaveTag(tags.get(i).tagID, numberItemIDs);
            LocalCache.put(key, itemIDs);
            startCacheItem(itemIDs);
        }
    }

    private void startCacheItem(List<String> itemIDs) throws TException {
        for (int i = 0; i < itemIDs.size(); i++) {
            temp = itemIDs.get(i);
            LocalCache.put("item" + itemIDs.get(i), handler.getItemFromItemID(itemIDs.get(i)));

        }
    }

    public void removeTagsCache(String key) {
        LocalCache.remove(key);
    }

    public String getRandomTagID() {
        List<Tag> ltags = (List<Tag>) LocalCache.get(listAllTagsKey);
        int index = getRandomIndex(ltags.size());
        return ltags.get(index).tagID;
    }

    public String getRandomItemID(String tagID) {
        String key = itemIDTagsKey + tagID;
        List<String> itemIDs = (List<String>) LocalCache.get(key);
        int index = getRandomIndex(itemIDs.size());
        return itemIDs.get(index);
    }

    public Item getRandomItem() {
        String tagID = getRandomTagID();
        String itemID = getRandomItemID(tagID);
        Item item = null;
        item = (Item) LocalCache.get("item" + itemID);
        return item;
    }

    public void removeItem() {
    }

    public List<Tag> getTopTags() {
        return (List<Tag>) LocalCache.get(listTopTagsKey);
    }

    public List<Tag> getAllTags() {
        return (List<Tag>) LocalCache.get(listAllTagsKey);
    }

    public int getRandomIndex(int size) {
        return (new Random()).nextInt(size - 1);

    }

    public void CacheUserItemIDLike(String uID) throws TException {
        if (!UserLocalCache.containsKey(uID)) {
            List<Item> itemIDs = handler.getFavouriteItems(uID, numberFavoriteItems);
            UserLocalCache.put(uID, itemIDs);
        }
    }
    public Object getUserItemIDLike(String uID){
        return UserLocalCache.get(uID);
    }
    public void setUserItemIDLike(String uID, String itemID) {        
        List<String> itemIDs = (List<String>) UserLocalCache.get(uID);
        if (!itemIDs.contains(itemID)) {
            itemIDs.add(itemID);
            UserLocalCache.put(uID, itemIDs);
        }
    }
    public void removeUserItemIDLike(String uID,String itemID){
        List<String> itemIDs = (List<String>) UserLocalCache.get(uID);
        if(itemIDs.size()>0) {
            itemIDs.remove(itemID);
            UserLocalCache.put(uID, itemIDs);
        }        
    }
    public void removeAllUserItemIDLike(String uID){
        UserLocalCache.remove(uID);
    }
    public void clearUserCaching(){
        UserLocalCache.clear();
    }
    public void clearAllCaching(){
        LocalCache.clear();
    }
}
