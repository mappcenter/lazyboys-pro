/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import myLRUCaching.LazyBoysLRUCache;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class MyLocalCache {
    MiddlewareHandler handler = new MiddlewareHandler();
    private static int capacityUser = 5000;
    private static String itemIDTagsKey = "ItemIDTags";
    private static long userExpiredTime = (15 * 60 * 1000); //15minutes
    private static int numberTopTags = 30;
    private static int numberItemIDTags = 30;
    public static int itemIDTagSize = 0;
    public static int numberFavoriteItems = 20;
    public static String listAllTagsKey = "listAllTags";
    public static String listTopTagsKey = "listTopTags";
    public static String listItemIDKey = "listItemID";
    public static String listItemsClientCache = "listItemsClientCache";
    
    public static Map<String, Object> LocalCache = new HashMap<String, Object>();
    public static LazyBoysLRUCache UserLocalCache = new LazyBoysLRUCache(capacityUser);
    
    public static Map<String, Object> TempCacheToSwap = new HashMap<String, Object>();

    public MyLocalCache() {
    }

    public void SwapData() {
        
        if (MySwapLocalCache.LocalCache.size() > 0) {
            TempCacheToSwap=LocalCache;
            System.out.println("Starting....! Swapping data...");
            LocalCache = MySwapLocalCache.LocalCache;
            numberFavoriteItems = MySwapLocalCache.numberFavoriteItems;
            numberItemIDTags = MySwapLocalCache.numberItemIDTags;
            numberTopTags = MySwapLocalCache.numberTopTags;
            itemIDTagSize = numberItemIDTags;
            
            TempCacheToSwap.clear();
            MySwapLocalCache.LocalCache=TempCacheToSwap;
        }
        //MySwapLocalCache.clearAllCaching();
        System.out.println("End...! Swap data");
    }

    public void updateListTags() throws TException {
        List<Tag> tem = (List<Tag>) LocalCache.get(listAllTagsKey);
        int count = tem.size();
        List<Tag> tags = handler.getAllTag();
        count = tags.size();
        LocalCache.put(listAllTagsKey, tags);
    }

    public void startMyLocalCache() throws TException {
        List<Tag> lTags = handler.getAllTag();
        LocalCache.put(listAllTagsKey, lTags);
        startCacheItemIDTags(lTags, numberItemIDTags);
        itemIDTagSize = numberItemIDTags;        
        List<Tag> listTopTags = handler.getTopTags(numberTopTags);
        LocalCache.put(listTopTagsKey, listTopTags);
        System.out.println("Caching Completed! ;)) ");
    }

    private void startCacheItemIDTags(List<Tag> tags, int numberItemIDs) throws TException {
        List<String> listItemIDs = new ArrayList<String>();
        for (int i = 0; i < tags.size(); i++) {
            String key = itemIDTagsKey + tags.get(i).tagID;
            List<String> itemIDs = handler.getAllItemsIDhaveTag(tags.get(i).tagID, numberItemIDs);
            listItemIDs.addAll(itemIDs);
            LocalCache.put(key, itemIDs);
            startCacheItem(itemIDs);
        }
        LocalCache.put(listItemIDKey, listItemIDs);
        LocalCache.put(listItemsClientCache, itemsClientCache);
    }
    public static List<Item> itemsClientCache=new ArrayList<Item>();
    private void startCacheItem(List<String> itemIDs) throws TException {
        for (int i = 0; i < itemIDs.size(); i++) {           
            Item item=handler.getItemFromItemID(itemIDs.get(i));
            if(i<11) { //get 10 items each tag to cache client
                itemsClientCache.add(item);
            }
            LocalCache.put("item" + itemIDs.get(i),item);
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
    
    public String getFastRandomItemID() {
        List<String> itemIDs = (List<String>) LocalCache.get(listItemIDKey);
        int index = getRandomIndex(itemIDs.size());
        return itemIDs.get(index);
    }

    public Item getFastRandom() {
        String itemID = getFastRandomItemID();
        Item item = null;
        item = (Item) LocalCache.get("item" + itemID);
        return item;
    }
    public Item getRandomItemHaveTag(String tagID){
        String itemID=getRandomItemID(tagID);
        Item item = null;
        item = (Item) LocalCache.get("item" + itemID);
        return item;        
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

    public void checkUserQueue() {
    }

    public void CacheUserItemIDLike(String uID) throws TException {
        List<Item> listItems = (List<Item>) UserLocalCache.get(uID);
        if (listItems == null) {
            listItems = handler.getFavouriteItems(uID, numberFavoriteItems);
            UserLocalCache.put(uID, listItems, userExpiredTime);
        }
    }

    public Object getUserItemIDLike(String uID) {
        return UserLocalCache.get(uID);
    }

    public void setUserItemIDLike(String uID, String itemID) throws TException {
        List<Item> listItems = (List<Item>) UserLocalCache.get(uID);
        for (Iterator<Item> it = listItems.iterator(); it.hasNext();) {
            Item item = it.next();
            if (item.itemID.equals(itemID)) {
                return;
            }
        }
        Item item = handler.getItemFromItemID(itemID);
        if (item != null) {
            listItems.add(item);
        }
        UserLocalCache.put(uID, listItems, userExpiredTime);
    }

    public void removeUserItemLike(String uID, String itemID) {
        List<Item> listItems = (List<Item>) UserLocalCache.get(uID);
        int i = listItems.size();
        if (listItems.size() > 0) {
            for (int j = 0; j < listItems.size(); j++) {
                Item item = listItems.get(j);
                if (item.itemID.equals(itemID)) {
                    listItems.remove(j);
                    UserLocalCache.put(uID, listItems);
                    int k = listItems.size();
                    return;
                }
            }
        }
    }

    public void removeAllUserItemIDLike(String uID) {
        UserLocalCache.remove(uID);
    }
    public void clearUserCaching() {
        //UserLocalCache.
    }
    public List<Item> getItemsForClientCache(){
        List<Item> items=(List<Item>)LocalCache.get(listItemsClientCache);
        return items;
    }
}
