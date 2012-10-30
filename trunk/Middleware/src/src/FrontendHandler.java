/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import libs.Item;
import libs.Tag;
import libs.User;
import memcache.LazyBoysLRUCache;
import org.apache.thrift.TException;

/**
 * interface between middle ware and front end
 *
 * @author chanhlt
 */
public class FrontendHandler implements libs.MiddlewareFrontend.Iface {

    static BackendHandler handler = new BackendHandler();
    static Map<String, Object> local_cache = new HashMap<>();
    //static LazyBoysLRUCache lzCache = null;
    static List<Tag> listTag = new ArrayList<>();
    static long numberTopTags = 0;
    static long numberItemsIDofTag = 0;

    public FrontendHandler() throws TException, IOException {
    }

//    public static void StartLZCache() {
//        int maxObject = 0;
//        try {
//            maxObject = getConfig.getInstance().maxObject();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(FrontendHandler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(FrontendHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //lzCache = new LazyBoysLRUCache(maxObject);
//    }
    @Override
    public List<Tag> getAllTag() throws TException {
        if (!listTag.isEmpty()) {
            //System.out.println("getAllTag from cache ...");
            return listTag;
        }
        listTag = handler.getAllTag();
        for (Tag tag : listTag) {
            local_cache.put("getTag" + tag.tagID, tag);
        }
        //System.out.println("getAllTag from backend ...");
        return listTag;
    }

    @Override
    public boolean insertTag(String tagName) throws TException {
        boolean result = handler.insertTag(tagName);
        if (result) {
            //System.out.println("insert tag success ...");
            listTag = handler.getAllTag();
        } else {
            //System.out.println("insert tag failed ...");
        }
        return result;
    }

    @Override
    public boolean deleteTag(String tagID) throws TException {
        boolean result = handler.deleteTag(tagID);
        if (result) {
            //System.out.println("delete tag success ...");
            listTag = handler.getAllTag();
        } else {
            //System.out.println("delete tag failed ...");
        }
        return result;
    }

    @Override
    public boolean deleteAllTag(List<String> tagIDs) throws TException {
        boolean result = handler.deleteAllTag(tagIDs);
        if (result) {
            //System.out.println("delete all tag success ...");
            listTag = handler.getAllTag();
        } else {
            //System.out.println("delete all tag failed ...");
        }
        return result;
    }

    @Override
    public boolean editTag(String tagID, String tagName) throws TException {
        boolean result = handler.editTag(tagID, tagName);
        if (result) {
            //System.out.println("edit tag success ...");
            listTag = handler.getAllTag();
        } else {
            //System.out.println("edit tag failed ...");
        }
        return result;
    }

    @Override
    public Tag getTag(String tagID) throws TException {
        if (local_cache.containsKey("getTag" + tagID)) {
            //System.out.println("get tag from cache ...");
            return (Tag) local_cache.get("getTag" + tagID);
        }
        //System.out.println("get tag froom backend ...");
        return handler.getTag(tagID);
    }

    @Override
    public void setViewCountTag(String tagID) throws TException {
        //System.out.println("set view count tag ...");
        handler.setViewCountTag(tagID);
    }

    @Override
    public List<Tag> getTopTags(long number) throws TException {
        List<Tag> result = handler.getTopTags(number);
        return result;
    }

    /**
     * get number random items by taking one random item in number times
     *
     * @param number
     * @return
     * @throws TException
     */
    @Override
    public List<Item> getAllItems(long number) throws TException {
        List<Item> listItem = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Item item = getRandomItem();
            listItem.add(item);
        }
        //System.out.println("get " + number + " items  ...");
        return listItem;
    }

    @Override
    public List<Item> getAllItemshaveTag(String tagID, int numberItems) throws TException {
        List<String> listItemID = getAllItemsIDhaveTag(tagID, numberItems);
        List<Item> listItem = new ArrayList<>();
        for (String itemID : listItemID) {
            Item item = getItemFromItemID(itemID);
            listItem.add(item);
        }
        //System.out.println("get " + numberItems + " item have tag " + tagID + " ...");
        return listItem;
    }

    @Override
    public List<Item> pagingItemsTag(String tagID, int pageNumber, int numberItems) throws TException {
        List<String> listItemID = getAllItemsIDhaveTag(tagID, numberItems);
        List<Item> listItem = new ArrayList<>();
        int start = (pageNumber - 1) * numberItems;
        int end = start + numberItems;
        for (int i = start; i < end; i++) {
            Item item = getItemFromItemID(listItemID.get(i));
            if (!"-1".equals(item.itemID) && item != null) {
                listItem.add(item);
            }
        }
        //System.out.println("paging status of tag: " + tagID + " ...");
        return listItem;
        //return handler.pagingItemsTag(tagID, pageNumber, numberItems);
    }

    @Override
    public List<String> getAllItemsIDhaveTag(String tagID, int numberItemsID) throws TException {
        List<String> listItemID;
        if (local_cache.containsKey("getAllItemsIDhaveTag" + tagID) && numberItemsID <= numberItemsIDofTag) {
            //System.out.println("get all itemID have tag " + tagID + " from cache...");
            listItemID = (List<String>) local_cache.get("getAllItemsIDhaveTag" + tagID);
            return listItemID;
        }
        listItemID = handler.getAllItemsIDhaveTag(tagID, numberItemsID);
        //System.out.println("get all itemID have tag " + tagID + " from backend...");
        numberItemsIDofTag = numberItemsID;
        local_cache.put("getAllItemsIDhaveTag" + tagID, listItemID);
        return listItemID;
    }

    /**
     * get a random item
     *
     * @return
     * @throws TException
     */
    @Override
    public Item getRandomItem() throws TException {
        Item item;
        do {
            String tagID = getRandomTag();
            item = getRandomItemhaveTag(tagID);
        } while (item==null);
        return item;
        //return handler.getRandomItem();
    }

    public int getRandomIndex(int size) {
        if (size == 0) {
            return -1;
        }
        return ((new Random()).nextInt(size));
    }

    public String getRandomTag() throws TException {
        int index = getRandomIndex((int) tagdbSize());
        String tagID = listTag.get(index).tagID;
        return tagID;
    }

    @Override
    public Item getRandomItemhaveTag(String tagID) throws TException {
        Item item;
        List<String> listitem = getAllItemsIDhaveTag(tagID, 1000);
        if (listitem.isEmpty()) {
            return null;
        }
        int index = getRandomIndex(listitem.size());
        String itemID = listitem.get(index);
        item = getItemFromItemID(itemID);
        //System.out.println("get random item " + itemID + " from tag " + tagID + " ..");
        return item;
    }

    @Override
    public void increaseViewCountItem(String itemID) throws TException {
        handler.increaseViewCountItem(itemID);
    }

    @Override
    public boolean deleteItem(String itemID) throws TException {
        boolean result = handler.deleteItem(itemID);
        if (result) {
            local_cache.remove("item" + itemID);
        }
        return result;
    }

    @Override
    public boolean deleteAllItem(List<String> itemIDs) throws TException {
        boolean result = handler.deleteAllItem(itemIDs);
        if (result) {
            for (String itemID : itemIDs) {
                local_cache.remove("item" + itemID);
            }
        }
        return result;
    }

    @Override
    public boolean editItem(String itemID, String newItemValue, List<String> newTagIDs) throws TException {
        boolean result = handler.editItem(itemID, newItemValue, newTagIDs);
        if (result) {
            local_cache.remove("item" + itemID);
        }
        return result;
    }

    @Override
    public List<Item> getItemKeyword(String keyWord) throws TException {
        return handler.getItemKeyword(keyWord);
    }

    @Override
    public List<Item> getItemKeywordTag(String keyWord, String tagID) throws TException {
        return handler.getItemKeywordTag(keyWord, tagID);
    }

    @Override
    public List<Item> getTopItems(long number) throws TException {
        if (local_cache.containsKey("getTopItems")) {
            List<Item> listItem = (List<Item>) local_cache.get("getTopItems");
            if (number <= listItem.size()) {
                //System.out.println("get top item from cache");
                return listItem.subList(0, (int) number - 1);
            }
        }
        //System.out.println("get top item from backend");
        List<Item> listItem = handler.getTopItems(number);
        local_cache.put("getTopItems", listItem);
        return listItem;
    }

    @Override
    public List<Item> getTopItemsofTag(long number, String tagID) throws TException {
        return handler.getTopItemsofTag(number, tagID);
    }

    @Override
    public boolean blockUser(String userID) throws TException {
        boolean result = handler.blockUser(userID);
        if (result && local_cache.containsKey("user" + userID)) {
            local_cache.remove("user" + userID);
        }
        return result;
    }

    @Override
    public boolean unblockUser(String userID) throws TException {
        boolean result = handler.unblockUser(userID);
        if (result && local_cache.containsKey("user" + userID)) {
            local_cache.remove("user" + userID);
        }
        return result;
    }

    @Override
    public boolean deleteUser(String userID) throws TException {
        boolean result = handler.deleteUser(userID);
        if (result && local_cache.containsKey("user" + userID)) {
            local_cache.remove("user" + userID);
        }
        return result;

    }

    @Override
    public Item getItemFromItemID(String itemID) throws TException {
        if (local_cache.containsKey("item" + itemID)) {
            //System.out.println("get item " + itemID + " from cache ...");
            return (Item) local_cache.get("item" + itemID);
        }
        //System.out.println("get item " + itemID + " from backend ...");
        Item item = handler.getItemFromItemID(itemID);
        local_cache.put("item" + itemID, item);
        return item;
    }

    @Override
    public List<Item> getItemsFromListItemID(List<String> itemIDs) throws TException {
        List<Item> listItem = new ArrayList<>();
        for (String itemID : itemIDs) {
            Item item = getItemFromItemID(itemID);
            listItem.add(item);
        }
        return listItem;
    }

    @Override
    public void increaseLikeCountItem(String itemID) throws TException {
        handler.increaseLikeCountItem(itemID);
    }

    @Override
    public void increaseDislikeCountItem(String itemID) throws TException {
        handler.increaseDislikeCountItem(itemID);
    }

    @Override
    public String insertItem(String content, List<String> tagIDs) throws TException {
        return handler.insertItem(content, tagIDs);
    }

    @Override
    public List<Item> getItemsPage(long pageNumber, long itemNumber, String tagID) throws TException {
        return handler.getItemsPage(pageNumber, itemNumber, tagID);
    }

    @Override
    public boolean addUser(String userID, String userToken, int userRole) throws TException {
        return handler.addUser(userID, userToken, userRole);
    }

    @Override
    public boolean editUser(String userID, String userToken, int userRole) throws TException {
        boolean result = handler.editUser(userID, userToken, userRole);
        if (result && local_cache.containsKey("user" + userID)) {
            local_cache.remove("user" + userID);
        }
        return result;

    }

    @Override
    public boolean deleteAllUser() throws TException {
        if (local_cache.containsKey("getAllUser")) {
            local_cache.remove("getAllUser");
        }
        return handler.deleteAllUser();
    }

    @Override
    public List<Tag> getTagKeyword(String keyWord) throws TException {
        return handler.getTagKeyword(keyWord);
    }

    @Override
    public List<Item> getFavouriteItems(String userID, long number) throws TException {
        List<Item> listItem = new ArrayList<>();
        if (local_cache.containsKey("getFavouriteItems" + userID)) {
            //System.out.println("get favorite items of user: " + userID + " from cache");
            listItem = (List<Item>) local_cache.get("getFavouriteItems" + userID);
            return listItem;
        }
        listItem = handler.getFavouriteItems(userID, number);
        local_cache.put("getFavouriteItems" + userID, listItem);
        //System.out.println("get favorite items of user: " + userID + " from backend");
        return listItem;
    }

    @Override
    public List<Item> getFavouriteItemsofTag(String userID, long number, String tagID) throws TException {
        return handler.getFavouriteItemsofTag(userID, number, tagID);
    }

    @Override
    public boolean insertFavouriteItem(String userID, String itemID) throws TException {
        if (local_cache.containsKey("getFavouriteItems" + userID)) {
            local_cache.remove("getFavouriteItems" + userID);
        }
        return handler.insertFavouriteItem(userID, itemID);
    }

    @Override
    public boolean deleteFavouriteItem(String userID, String itemID) throws TException {
        if (local_cache.containsKey("getFavouriteItems" + userID)) {
            local_cache.remove("getFavouriteItems" + userID);
        }
        return handler.deleteFavouriteItem(userID, itemID);
    }

    @Override
    public long itemdbSize() throws TException {
        if (local_cache.containsKey("itemdbSize")) {
            //System.out.println("get itemdb size from cache");
            return (long) local_cache.get("itemdbSize");
        }
        long size = handler.itemdbSize();
        //System.out.println("get itemdb size from backend");
        local_cache.put("itemdbSize", size);
        return size;
    }

    @Override
    public long tagdbSize() throws TException {
        if (local_cache.containsKey("tagdbSize")) {
            //System.out.println("get tagdb size from cache");
            return (long) local_cache.get("tagdbSize");
        }
        long size = handler.tagdbSize();
        //System.out.println("get tagdb size from backend");
        local_cache.put("tagdbSize", size);
        return size;
    }

    @Override
    public long itemtagdbSize() throws TException {
        if (local_cache.containsKey("itemtagdbSize")) {
            //System.out.println("get itemtagdb size from cache");
            return (long) local_cache.get("itemtagdbSize");
        }
        long size = handler.itemtagdbSize();
        //System.out.println("get itemtagdb size from backend");
        local_cache.put("itemtagdbSize", size);
        return size;
    }

    @Override
    public long itemtagSize(String tagID) throws TException {
        if (local_cache.containsKey("itemtagSize" + tagID)) {
            //System.out.println("get itemtag size of tag " + tagID + " from cache");
            return (long) local_cache.get("itemtagSize" + tagID);
        }
        long size = handler.itemtagSize(tagID);
        //System.out.println("get itemtag size of tag " + tagID + "f rom backend");
        local_cache.put("itemtagSize" + tagID, size);
        return size;
    }

    @Override
    public long userdbSize() throws TException {
        if (local_cache.containsKey("userdbSize")) {
            //System.out.println("get userdb size from cache");
            return (long) local_cache.get("userdbSize");
        }
        long size = handler.userdbSize();
        //System.out.println("get userdb size from backend");
        local_cache.put("userdbSize", size);
        return size;
    }

    @Override
    public long itemsLikeSize(String userID) throws TException {
        if (local_cache.containsKey("itemsLikeSize" + userID)) {
            //System.out.println("get itemsLikeSize from cache");
            return (long) local_cache.get("itemsLikeSize" + userID);
        }
        long size = handler.itemsLikeSize(userID);
        //System.out.println("get itemsLikeSize from backend");
        local_cache.put("itemsLikeSize" + userID, size);
        return size;
    }

    @Override
    public long itemsDislikeSize(String userID) throws TException {
        if (local_cache.containsKey("itemsDislikeSize" + userID)) {
            //System.out.println("get itemsDislikeSize from cache");
            return (long) local_cache.get("itemsDislikeSize" + userID);
        }
        long size = handler.itemsDislikeSize(userID);
        //System.out.println("get itemsDislikeSize from backend");
        local_cache.put("itemsDislikeSize" + userID, size);
        return size;
    }

    @Override
    public long favouriteItemsSize(String userID) throws TException {
        if (local_cache.containsKey("favouriteItemsSize" + userID)) {
            //System.out.println("get favouriteItemsSize from cache");
            return (long) local_cache.get("favouriteItemsSize" + userID);
        }
        long size = handler.favouriteItemsSize(userID);
        //System.out.println("get favouriteItemsSize from backend");
        local_cache.put("favouriteItemsSize" + userID, size);
        return size;
    }

    @Override
    public List<String> getAllItemsIDLike(String userID) throws TException {
        if (local_cache.containsKey("getAllItemsIDLike" + userID)) {
            //System.out.println("get get all item id like of user: " + userID + " from cache");
            return (List<String>) local_cache.get("getAllItemsIDLike" + userID);
        }
        List<String> listItemID = handler.getAllItemsIDLike(userID);
        //System.out.println("get get all item id like of user: " + userID + " from backend");
        return listItemID;
    }

    @Override
    public List<Item> getAllItemsLike(String userID, int number) throws TException {
        return handler.getAllItemsLike(userID, number);
    }

    @Override
    public boolean insertLikedItem(String userID, String itemID) throws TException {
        if (local_cache.containsKey("getAllItemsIDLike" + userID)) {
            local_cache.remove("getAllItemsIDLike" + userID);
        }
        return handler.insertLikedItem(userID, itemID);
    }

    @Override
    public boolean deleteLikedItem(String userID, String itemID) throws TException {
        if (local_cache.containsKey("getAllItemsIDLike" + userID)) {
            local_cache.remove("getAllItemsIDLike" + userID);
        }
        return handler.deleteLikedItem(userID, itemID);
    }

    @Override
    public List<String> getAllItemsIDDislike(String userID) throws TException {
        if (local_cache.containsKey("getAllItemsIDDislike" + userID)) {
            List<String> listItemID = (List<String>) local_cache.get("getAllItemsIDDislike" + userID);
            //System.out.println("get all item id dislike of user: " + userID + " from cache");
            return listItemID;
        }
        List<String> listItemID = handler.getAllItemsIDDislike(userID);
        //System.out.println("get all item id dislike of user: " + userID + " from backend");
        local_cache.put("getAllItemsIDDislike" + userID, listItemID);
        return listItemID;
    }

    @Override
    public List<Item> getAllItemsDislike(String userID, int number) throws TException {
        return handler.getAllItemsDislike(userID, number);
    }

    @Override
    public boolean insertDislikedItem(String userID, String itemID) throws TException {
        if (local_cache.containsKey("getAllItemsIDDislike" + userID)) {
            local_cache.remove("getAllItemsIDDislike" + userID);
        }
        return handler.insertDislikedItem(userID, itemID);
    }

    @Override
    public boolean deleteDislikedItem(String userID, String itemID) throws TException {
        if (local_cache.containsKey("getAllItemsIDDislike" + userID)) {
            local_cache.remove("getAllItemsIDDislike" + userID);
        }
        return handler.deleteDislikedItem(userID, itemID);
    }

    @Override
    public boolean userExisted(String userID) throws TException {
        return handler.userExisted(userID);
    }

    @Override
    public User getUser(String userID) throws TException {
        User user;
        if (local_cache.containsKey("user" + userID)) {
            //System.out.println("get user " + userID + "from cache ...");
            user = (User) local_cache.get("user" + userID);
            return user;
        }
        //System.out.println("get user " + userID + "from backend ...");
        user = handler.getUser(userID);
        local_cache.put("user" + userID, user);
        return user;
    }

    @Override
    public List<String> getAllUser() throws TException {
        List<String> listUser;
        if (local_cache.containsKey("getAllUser")) {
            //System.out.println("get all user from cache ...");
            listUser = (List<String>) local_cache.get("getAllUser");
            return listUser;
        }
        //start cache
        //System.out.println("get all user from backend ...");
        listUser = handler.getAllUser();
        local_cache.put("getAllUser", listUser);
        for (String userID : listUser) {
            getUser(userID);
        }
        return listUser;
    }

    @Override
    public List<Item> getItemsPageKeyword(String keyWord, long pageNumber, long itemNumber) throws TException {
        return handler.getItemsPageKeyword(keyWord, pageNumber, itemNumber);
    }

    public void startCache() throws TException {
        List<Tag> lisTag = getAllTag();
        int numberItemID = 0;
        int numberItemCache = 0;
        try {
            numberItemID = getConfig.getInstance().numberItemofTag();
            numberItemCache = getConfig.getInstance().numberItemCache();
        } catch (Exception ex) {
        }
        for (Tag tag : lisTag) {
            getAllItemshaveTag(tag.tagID, numberItemID);
        }
        getAllItems(numberItemCache);
        List<String> listUserID = getAllUser();

        for (String userID : listUserID) {
            getAllItemsIDDislike(userID);
            getAllItemsIDLike(userID);
        }
    }

    @Override
    public List<Item> getItemsPageKeywordOfTag(String keyWord, String tagID, long pageNumber, long itemNumber) throws TException {
        return handler.getItemsPageKeywordOfTag(keyWord, tagID, pageNumber, itemNumber);
    }

    @Override
    public List<String> friendLikesItemID(String itemID, List<String> listFriends) throws TException {
        return handler.friendLikesItemID(itemID, listFriends);
    }
}
