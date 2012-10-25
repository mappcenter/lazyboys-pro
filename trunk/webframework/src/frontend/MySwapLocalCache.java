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
    public static int capacityUser=5000;
    public static String itemIDTagsKey = "ItemIDTags";
    public static long userExpiredTime=(15*60*1000); //15minutes
    public static int numberTopTags = 30;
    public static int numberItemIDTags = 30;
    public static int itemIDTagSize = 0;
    public static int numberFavoriteItems = 20;
    public static String listAllTagsKey = "listAllTags";
    public static String listTopTagsKey = "listTopTags";
    public static String listItemIDKey="listItemID";
    public static Map<String, Object> LocalCache = new HashMap<String, Object>();   
    public static LazyBoysLRUCache UserLocalCache=new LazyBoysLRUCache(capacityUser);

    public MySwapLocalCache(){
    }
    public static String temp = "";

    public void SwapData(){
        //System.out.println("Start Swapping data");
        LocalCache=MySwapLocalCache.LocalCache;
        numberFavoriteItems=MySwapLocalCache.numberFavoriteItems;
        numberItemIDTags=MySwapLocalCache.numberItemIDTags;
        numberTopTags=MySwapLocalCache.numberTopTags;
        itemIDTagSize=numberItemIDTags;
       // System.out.println("End Swapping data");
    }
  
    public void updateListTags() throws TException{
        List<Tag> tem=(List<Tag>) LocalCache.get(listAllTagsKey);
        int count=tem.size();
        List<Tag> tags=handler.getAllTag();
        count=tags.size();
        LocalCache.put(listAllTagsKey, tags);
    }   
    public void startMyLocalCache() throws TException {
        List<Tag> lTags = handler.getAllTag(); 

        LocalCache.put(listAllTagsKey, lTags);

        startCacheItemIDTags(lTags, numberItemIDTags);
        itemIDTagSize = numberItemIDTags;
        Item item = (Item) LocalCache.get("item" + temp);
        List<Tag> listTopTags = handler.getTopTags(numberTopTags);
        LocalCache.put(listTopTagsKey, listTopTags);
        //System.out.println("Caching Completed! ;)) ");

    }
    private void startCacheItemIDTags(List<Tag> tags, int numberItemIDs) throws TException {
        List<String>listItemIDs=new ArrayList<String>();
        for (int i = 0; i < tags.size(); i++) {
            String key = itemIDTagsKey + tags.get(i).tagID;
            List<String> itemIDs = handler.getAllItemsIDhaveTag(tags.get(i).tagID, numberItemIDs);
            listItemIDs.addAll(itemIDs);
            LocalCache.put(key, itemIDs);
            startCacheItem(itemIDs);
        }
        LocalCache.put(listItemIDKey, listItemIDs);
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
    public String getFastRandomItemID(){
        List<String> itemIDs=(List<String>) LocalCache.get(listItemIDKey);
        int index=getRandomIndex(itemIDs.size());
        return itemIDs.get(index);
    }
    public Item getFastRandom(){
        String itemID=getFastRandomItemID();
        Item item=null;
        item=(Item) LocalCache.get("item"+itemID);
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
    public void checkUserQueue(){
       
            
    }
    public void CacheUserItemIDLike(String uID) throws TException {
        List<Item> listItems=(List<Item>) UserLocalCache.get(uID);
        if (listItems==null) {
            listItems = handler.getFavouriteItems(uID, numberFavoriteItems);
            UserLocalCache.put(uID, listItems,userExpiredTime);
        }
    }
    public Object getUserItemIDLike(String uID){
        return UserLocalCache.get(uID);
    }
    public void setUserItemIDLike(String uID, String itemID) throws TException {        
        List<Item> listItems = (List<Item>) UserLocalCache.get(uID);                
        for (Iterator<Item> it = listItems.iterator(); it.hasNext();) {
            Item item = it.next();
            if(item.itemID.equals(itemID)){
                return;
            }            
        }        
        Item item=handler.getItemFromItemID(itemID);
        if(item!=null){
            listItems.add(item);
        }
        UserLocalCache.put(uID, listItems,userExpiredTime);
    }
    public void removeUserItemLike(String uID,String itemID){
        List<Item> listItems = (List<Item>) UserLocalCache.get(uID);
        int i=listItems.size();
        if(listItems.size()>0) {
            for (int j=0;j< listItems.size();j++) {                
                Item item = listItems.get(j);
                if(item.itemID.equals(itemID)){
                    listItems.remove(j);
                    UserLocalCache.put(uID, listItems);
                    int k=listItems.size();
                    return;
                }
            }            
        }        
    }
    public void removeAllUserItemIDLike(String uID){
        UserLocalCache.remove(uID);
    }
    public void clearUserCaching(){
        //UserLocalCache.
    }
    public static void clearAllCaching(){
        LocalCache.clear();
    }
}
