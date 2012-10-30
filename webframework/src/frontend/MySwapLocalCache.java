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
import myLRUCaching.LazyBoysLRUCache;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class MySwapLocalCache {

   MiddlewareHandler handler = new MiddlewareHandler();
    public static int capacityUser = 5000;
    public static String itemIDTagsKey = "ItemIDTags";
    public static long userExpiredTime = (15 * 60 * 1000); //15minutes
    public static int numberTopTags = 20;
    public static int numberItemIDTags = 30;
    public static int numberTopItems = 15;
    public static int itemIDTagSize = 0;
    public static int numberFavoriteItems = 20;
    public static String listAllTagsKey = "listAllTags";
    public static String listTopTagsKey = "listTopTags";
    public static String listItemIDKey = "listItemID";
    public static String listTopItemsKey="listTopItems";
    public static String listItemsClientCache = "listItemsClientCache";
    public static Map<String, Object> LocalCache = new HashMap<String, Object>();
    public static LazyBoysLRUCache UserLocalCache = new LazyBoysLRUCache(capacityUser);
    public static Map<String, Object> TempCacheToSwap = new HashMap<String, Object>();
    
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
        
        //cache items for client to random
        cacheItemsClient(25);
        List<Item> test = (List<Item>) LocalCache.get(listItemsClientCache);
        int t = test.size();

        //cache top Items
        StartCacheTopItems();
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
    }
    public void StartCacheTopItems() throws TException{
        List<Item> items=handler.getTopItems(numberTopItems);
        LocalCache.put(listTopItemsKey, items);
    }
    public List<Item> getTopItems() throws TException{
        List<Item> items=(List<Item>) LocalCache.get(listTopItemsKey);
        if(items==null){
            items=handler.getTopItems(numberTopItems);
        }
        return items;
    }
    public static List<Item> itemsClientCache = new ArrayList<Item>();

    private void startCacheItem(List<String> itemIDs) throws TException {
        for (int i = 0; i < itemIDs.size(); i++) {
            Item item = handler.getItemFromItemID(itemIDs.get(i));
//            if (i < 30) { 
//                itemsClientCache.add(item);
//            }
            LocalCache.put("item" + itemIDs.get(i), item);
        }
    }

    private void cacheItemsClientDetail(List<Tag> ltags, int i) {
        List<String> itemIDs = (List<String>) LocalCache.get(itemIDTagsKey + ltags.get(i).tagID);
        for (int j = 0; j < itemIDs.size() && j < 3; j++) { //get 5 items each tag to cache client
            //if (j < 2) {
            Item item = (Item) LocalCache.get("item" + itemIDs.get(j));
            itemsClientCache.add(item);
            //}
            //else {
            //    break;
            //}
        }
    }

    // chi cache numtags (ko cache toan bo tags)
    public void cacheItemsClient(int numTags) {
        List<Tag> ltags = (List<Tag>) LocalCache.get(listAllTagsKey);
        int index = getRandomIndex(ltags.size());
        int lTagSize = ltags.size();
        if (numTags > lTagSize) {
            numTags = lTagSize;
        }
        //int size = lTagSize - index;
        for (int i = index; i < lTagSize && numTags > 0; i++) {
            cacheItemsClientDetail(ltags, i);
            numTags--;
        }
        if (numTags > 0) {
            for (int k = 0; k < numTags; k++) {
                cacheItemsClientDetail(ltags, k);
            }
        }

        LocalCache.put(listItemsClientCache, itemsClientCache);
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

    public Item getRandomItemHaveTag(String tagID) {
        String itemID = getRandomItemID(tagID);
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

    public void CacheUserItemsFavorite(String uID) throws TException {
        List<Item> listItems = (List<Item>) UserLocalCache.get(uID);
        if (listItems == null) {
            listItems = handler.getFavouriteItems(uID, numberFavoriteItems);
            UserLocalCache.put(uID, listItems, userExpiredTime);
        }
    }

    public List<Item> getUserItemsLike(String uID) throws TException {
        List<Item> items = (List<Item>) UserLocalCache.get(uID);
        if (items == null) {
            CacheUserItemsFavorite(uID);
        }
        return (List<Item>) UserLocalCache.get(uID);
    }

    //save user item into localcache LRU
    public boolean setUserItemsFavorite(String uID, String itemID) throws TException {
        //check cache User start or not
        CacheUserItemsFavorite(uID);
        List<Item> listItems = (List<Item>) UserLocalCache.get(uID);
        if (listItems == null) {
            listItems = new ArrayList<Item>();
        }
        for (Iterator<Item> it = listItems.iterator(); it.hasNext();) {
            Item item = it.next();
            if (item.itemID.equals(itemID)) {
                return false;
            }
        }
        Item item = handler.getItemFromItemID(itemID);
        if (item != null) {
            listItems.add(item);
        }
        //call middle insert Favorite Item
        if (!handler.insertFavouriteItem(uID, itemID)) {
            return false;
        }
        UserLocalCache.put(uID, listItems, userExpiredTime);
        return true;
    }
    
    public void removeUserItemLike(String uID, String itemID) throws TException {
        List<Item> listItems = (List<Item>) UserLocalCache.get(uID);
        int i = listItems.size();
        if (listItems.size() > 0) {
            for (int j = 0; j < listItems.size(); j++) {
                Item item = listItems.get(j);
                if (item.itemID.equals(itemID)) {
                    listItems.remove(j);
                    UserLocalCache.put(uID, listItems);
                    //int k = listItems.size();
                    handler.deleteFavouriteItem(uID, itemID);
                    return;
                }
            }
        }
    }
    public void removeAllUserItemIDLike(String uID) {
        UserLocalCache.remove(uID);
    }

    public void clearAllUserCaching() {
        //UserLocalCache.
    }
    public void clearUserCaching(String uID){
        UserLocalCache.remove(uID);
    }
    public List<Item> getItemsForClientCache() {
        List<Item> items = (List<Item>) LocalCache.get(listItemsClientCache);
        return items;
    }
    public void clearUserCaching() {
        //UserLocalCache.
    }
    public void clearAllCaching(){
        
    }   
}
