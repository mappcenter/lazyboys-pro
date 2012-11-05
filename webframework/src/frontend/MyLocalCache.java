/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import IOFile.CachingIndexPage;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Dictionary;
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
public class MyLocalCache {

    MiddlewareHandler handler = new MiddlewareHandler();
    private static int capacityUser = 10000; //1 user: user favorite items and user like itemIDs. => 5000 user info
    private static String itemIDTagsKey = "ItemIDTags";
    private static long userExpiredTime = (10 * 60 * 1000); //15minutes
    private static int numberTopTags = 20;
    private static int numberItemIDTags = 30;
    private static int numberTopItems = 15;
    public static int itemIDTagSize = 0;
    public static int numberFavoriteItems = 20;
    public static String listAllTagsKey = "listAllTags";
    public static String listTopTagsKey = "listTopTags";
    public static String listItemIDKey = "listItemID";
    public static String listTopItemsKey = "listTopItems";
    public static String listItemsClientCache = "listItemsClientCache";
    public static String listItemsIDUserLike = "ItemIdUserLike";
    public static String listItemsUserFavorite = "ItemUserFavorite";
    public static String indexpage = "indexpage";
    public static Map<String, Object> LocalCache = new HashMap<String, Object>();
    public static LazyBoysLRUCache UserLocalCache = new LazyBoysLRUCache(capacityUser);
    public static LazyBoysLRUCache hostItems = new LazyBoysLRUCache(20);
    public static Map<String, Object> TempCacheToSwap = new HashMap<String, Object>();

    public MyLocalCache() {
    }

    public void SwapData() {

        if (MySwapLocalCache.LocalCache.size() > 0) {
            TempCacheToSwap = LocalCache;
            System.out.println("Starting....! Swapping data...");
            LocalCache = MySwapLocalCache.LocalCache;
            numberFavoriteItems = MySwapLocalCache.numberFavoriteItems;
            numberItemIDTags = MySwapLocalCache.numberItemIDTags;
            numberTopTags = MySwapLocalCache.numberTopTags;
            itemIDTagSize = numberItemIDTags;

            TempCacheToSwap.clear();
            MySwapLocalCache.LocalCache = TempCacheToSwap;
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
        int tg=lTags.size();
        startCacheItemIDTags(lTags, numberItemIDTags);
        
        tg=lTags.size();
        
        LocalCache.put(listAllTagsKey, lTags);
        for (int i = 0; i < lTags.size(); i++) {
            if(lTags.get(i).tagID.compareTo("548")==0){
                int a=0;
            }
        }
        tg=lTags.size();
        itemIDTagSize = numberItemIDTags;
        List<Tag> listTopTags = handler.getTopTags(numberTopTags);
        LocalCache.put(listTopTagsKey, listTopTags);


        //cache items for client to random
        cacheItemsClient(25);
        List<Item> test = (List<Item>) LocalCache.get(listItemsClientCache);
        int t = test.size();

        //cache top Items
        StartCacheTopItems();

        //Cache index page;
        CachingIndexPage cacheIndexPage = new CachingIndexPage();
        List<String> indexPageHtml = cacheIndexPage.renderIndexHtml();
        LocalCache.put(indexpage, indexPageHtml);

        System.out.println("Caching Completed! ;)) ");
    }
    
    private void startCacheItemIDTags(List<Tag> tags, int numberItemIDs) throws TException {
        List<String> listItemIDs = new ArrayList<String>();
        List<String> tagNoneItems=new ArrayList<String>();
        for (int i = 0; i < tags.size(); i++) {
            String key = itemIDTagsKey + tags.get(i).tagID;
            if(tags.get(i).tagID.compareTo("548")==0){
                String a=tags.get(i).tagID;
            }
            List<String> itemIDs = handler.getAllItemsIDhaveTag(tags.get(i).tagID, numberItemIDs);
            if (itemIDs != null && itemIDs.size()>0) {
                listItemIDs.addAll(itemIDs);
                LocalCache.put(key, itemIDs);
                startCacheItem(itemIDs);
            }
            else{
                tagNoneItems.add(tags.get(i).tagID);
            }            
            //LocalCache.put("tag"+tags.get(i).tagID, tags);            
        }

        for (int i = 0; i < tagNoneItems.size(); i++) {
            for (int j = 0; j < tags.size(); j++){ 
                if(tagNoneItems.get(i).compareTo(tags.get(j).tagID)==0){
                    tags.remove(j);
                }
            }
        }
        LocalCache.put(listItemIDKey, listItemIDs);
    }

    public void StartCacheTopItems() throws TException {
        List<Item> items = handler.getTopItems(numberTopItems);
        LocalCache.put(listTopItemsKey, items);
    }

    public List<Item> getTopItems() throws TException {
        List<Item> items = (List<Item>) LocalCache.get(listTopItemsKey);
        if (items == null) {
            items = handler.getTopItems(numberTopItems);
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
            if (item != null) {
                LocalCache.put("item" + itemIDs.get(i), item);
            }
        }
    }

    private void cacheItemsClientDetail(List<Tag> ltags, int i) {
        List<String> itemIDs = (List<String>) LocalCache.get(itemIDTagsKey + ltags.get(i).tagID);
        for (int j = 0; j < itemIDs.size() && j < 2; j++) { //get 5 items each tag to cache client
            //if (j < 2) {
            if (LocalCache.get("item" + itemIDs.get(j)) != null) {
                Item item = (Item) LocalCache.get("item" + itemIDs.get(j));
                itemsClientCache.add(item);
            }
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

    public List<Item> CacheUserItemsFavorite(String uID) throws TException {
        List<Item> listItems = (List<Item>) UserLocalCache.get(listItemsUserFavorite + uID);
        if (listItems == null) {
            listItems = handler.getFavouriteItems(uID, numberFavoriteItems);
            UserLocalCache.put(listItemsUserFavorite + uID, listItems, userExpiredTime);
        }
        return listItems;
    }

    public List<Item> getUserFavoriteItems(String uID) throws TException {
        List<Item> items = (List<Item>) UserLocalCache.get(listItemsUserFavorite + uID);
        if (items != null) {
            return items;
        }
        return CacheUserItemsFavorite(uID);
    }
    //save user item into localcache LRU

    public boolean setUserItemsFavorite(String uID, String itemID) throws TException {
        //check cache User start or not
        CacheUserItemsFavorite(uID);
        List<Item> listItems = (List<Item>) UserLocalCache.get(listItemsUserFavorite + uID);
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

    public void removeUserItemsFavourite(String uID, String itemID) throws TException {
        List<Item> listItems = (List<Item>) UserLocalCache.get(listItemsUserFavorite + uID);
        //int i = listItems.size();
        if (listItems.size() > 0) {
            for (int j = 0; j < listItems.size(); j++) {
                Item item = listItems.get(j);
                if (item.itemID.equals(itemID)) {
                    listItems.remove(j);
                    UserLocalCache.put(listItemsUserFavorite + uID, listItems);
                    //int k = listItems.size();
                    handler.deleteFavouriteItem(uID, itemID);
                    return;
                }
            }
        }
    }
    //End User Favorite Items

    //User like
    public List<String> CacheUserItemIDsLike(String uID) throws TException {
        List<String> listItemsIDs = (List<String>) UserLocalCache.get(listItemsIDUserLike + uID);
        if (listItemsIDs == null) {
            listItemsIDs = handler.getAllItemsIDLike(uID);
            UserLocalCache.put(listItemsIDUserLike + uID, listItemsIDs, userExpiredTime);
        }
        return listItemsIDs;
    }

    public List<String> getUserItemIDsLike(String uID) throws TException {
        List<String> itemIDs = (List<String>) UserLocalCache.get(listItemsIDUserLike + uID);
        if (itemIDs != null) {
            return itemIDs;
        }
        return CacheUserItemIDsLike(uID);
    }

    public boolean setUserItemIDLike(String uID, String itemID) throws TException {
        //check cache User start or not
        CacheUserItemIDsLike(uID);
        List<String> listItemIDs = (List<String>) UserLocalCache.get(listItemsIDUserLike + uID);
        if (listItemIDs == null) {
            listItemIDs = new ArrayList<String>();
        }
        if (listItemIDs.size() < 1) {
            return false;
        }
        for (Iterator<String> it = listItemIDs.iterator(); it.hasNext();) {
            String item;
            item = it.next();
            if (item.equals(itemID)) {
                return false;
            }
        }
        if (!handler.insertLikedItem(uID, itemID)) {
            return false;
        }
        listItemIDs.add(itemID);
        UserLocalCache.put(listItemsIDUserLike + uID, listItemIDs, userExpiredTime);
        return true;
    }

    public boolean removeUserItemIDLike(String uID, String itemID) throws TException {
        List<String> listItemIDs = (List<String>) UserLocalCache.get(listItemsIDUserLike + uID);
        //int i = listItemIDs.size();
        if (listItemIDs.size() > 0) {
            for (int j = 0; j < listItemIDs.size(); j++) {
                String item = listItemIDs.get(j);
                if (item.equals(itemID)) {
                    listItemIDs.remove(j);
                    UserLocalCache.put(listItemsIDUserLike + uID, listItemIDs);
                    //int k = listItems.size();
                    if (!handler.deleteLikedItem(uID, itemID)) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }
    //end of user ItemIDs Like

    public User cacheUserInfo(String uID) throws TException {
        User user=null;
        if(UserLocalCache.get(uID)!=null)
        {
            user = (User) UserLocalCache.get(uID);
        }        
        else {
            user = handler.getUser(uID);
            UserLocalCache.put(uID, user, userExpiredTime);
        }
        return user;
    }

    public User getUserInfo(String uID) throws TException {
        
        if (UserLocalCache.get(uID)!= null) {
            User user = (User) UserLocalCache.get(uID);
            return user;
        }
        return cacheUserInfo(uID);
    }

    public void removeUserInfo(String uID) {
    }

    public void removeAllUserFavoriteItems(String uID) {
        UserLocalCache.remove(listItemsUserFavorite + uID);
    }

    public void clearAllUserCaching() {
        //UserLocalCache.
    }

    public void clearUserCaching(String uID) {
        UserLocalCache.remove(uID);
    }

    public List<Item> getItemsForClientCache() {
        List<Item> items = (List<Item>) LocalCache.get(listItemsClientCache);
        return items;
    }

    public boolean isBlockUser(String uID) throws TException {
        User user = getUserInfo(uID);
        if (user.userRole < 0) {
            return true;// is blocked user
        }
        return false; //not blocked
    }

    public void setBlockUser(String uID, String uToken, int uRole) throws TException {
        User user = (User) UserLocalCache.get(uID);
        if (user != null) {
            user.userRole = uRole;
            user.userToken = uToken;
            UserLocalCache.put(uID, user);
        }
        //handler.blockUser(uID);
    }

    public List<Item> getItemsTag(String tagID) {
        List<String> itemIDs = (List<String>) LocalCache.get(itemIDTagsKey + tagID);
        List<Item> items = new ArrayList<Item>();
        for (int i = 0; i < itemIDs.size() && i < 16; i++) { // chi lay 15 items
            Item temp = (Item) LocalCache.get("item" + itemIDs.get(i));
            if (temp != null) {
                items.add(temp);
            }
        }
        //if(items.size()>0){
        return items;
        //}
        //return null;
    }

    public List<String> getCacheIndexPageWithUser(String userID, String userName) {
        List<String> result = (List<String>) LocalCache.get(indexpage);
        String replace = "";

        if (result != null) {
            CachingIndexPage cachingPage = new CachingIndexPage();
            result = cachingPage.renderIndexHtmlForUser(result, userID, userName);
        }
        return result;
    }
}
