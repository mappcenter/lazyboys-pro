/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import libs.Item;
import libs.Tag;
import memcache.MyCache;
import org.apache.thrift.TException;

/**
 * interface between middle ware and front end
 *
 * @author chanhlt
 */
public class FrontendHandler implements libs.MiddlewareFrontend.Iface {

    BackendHandler handler = new BackendHandler();
    public static List<String> tagIDs = new ArrayList<>();
    public static List<String> itemIDs = new ArrayList<>();
    private static List<Tag> listTag = new ArrayList<>();

    public FrontendHandler() throws TException, IOException {
        if (listTag.isEmpty()) {
            listTag = getAllTag();
            for (int i = 0; i < listTag.size(); i++) {
                tagIDs.add(listTag.get(i).tagID);
                //getAllItemshaveTag(listTag.get(i).tagID, 50);
                String key = listTag.get(i).tagID + "listItemID";                                
                    List<String> listItemID=new ArrayList<>();
                    listItemID = handler.getAllItemsIDhaveTag(listTag.get(i).tagID, 100);
                    MyCache.getInstance().set(key, 3600, listItemID); 
                }
            }
        }

    

    public void Caching() throws TException {
        if (listTag.isEmpty()) {
            listTag = getAllTag();
            for (int i = 0; i < listTag.size(); i++) {
                tagIDs.add(listTag.get(i).tagID);
            }
        }
    }

    /**
     * get all tag
     *
     * @return
     * @throws TException
     */
    @Override
    public List<Tag> getAllTag() throws TException {

        //List<Tag> listTag = null;
        try {
            if (listTag.isEmpty()) {
                listTag = (List<Tag>) MyCache.getInstance().get("listTag");
                System.out.println("get listTag from Cached");
            }
            if (listTag == null) {
                System.out.println("get listTag from DB");
                listTag = handler.getAllTag();
                int ttl = getConfig.getInstance().getTtl();
                MyCache.getInstance().set("listTag", ttl, listTag);
            }
        } catch (IOException ex) {
            Logger.getLogger(FrontendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listTag;
    }

    @Override
    public boolean insertTag(String tagName) throws TException {
        return handler.insertTag(tagName);
    }

    @Override
    public boolean deleteTag(String tagID) throws TException {
        return handler.deleteTag(tagID);
    }

    @Override
    public boolean deleteAllTag(List<String> tagIDs) throws TException {
        return handler.deleteAllTag(tagIDs);
    }

    @Override
    public boolean editTag(String tagID, String tagName) throws TException {
        return handler.editTag(tagID, tagName);
    }

    @Override
    public Tag getTag(String tagID) throws TException {
        return handler.getTag(tagID);
    }

    @Override
    public void setViewCountTag(String tagID) throws TException {
        handler.setViewCountTag(tagID);
    }

    @Override
    public List<Tag> getTopTags(long number) throws TException {
        return getTopTags(number);
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
        //call getRandomItem number times

        for (int i = 0; i < number; i++) {
            Item item = getRandomItem();
            listItem.add(item);
        }
        return listItem;
    }

    @Override
    public List<Item> getAllItemshaveTag(String tagID, int numberItems) throws TException {
        return handler.getAllItemshaveTag(tagID, numberItems);
    }

    @Override
    public List<Item> pagingItemsTag(String tagID, int pageNumber, int numberItems) throws TException {
        return handler.pagingItemsTag(tagID, pageNumber, numberItems);
    }

    @Override
    public List<String> getAllItemsIDhaveTag(String tagID, int numberItemsID) throws TException {
        List<String> listItemID = null;
        String key = tagID + "listItemID";
        try {
            listItemID = (List<String>) MyCache.getInstance().get(key);
            if (listItemID == null) {
                System.out.println("get list ItemID from DB");
                listItemID = handler.getAllItemsIDhaveTag(tagID, numberItemsID);
                MyCache.getInstance().set(key, 3600, listItemID);
            }
        } catch (IOException ex) {
            Logger.getLogger(FrontendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        String tagID = getRandomTag();
        Item item = getRandomItemhaveTag(tagID);
        return item;
        //return handler.getRandomItem();
    }

    public int getRandomIndex(int size) {
        return (new Random()).nextInt(size - 1);
    }

    public String getRandomTag() throws TException {
        int index = getRandomIndex((int) tagdbSize());
        String tagID = listTag.get(index).tagID;
        return tagID;
    }

    @Override
    public Item getRandomItemhaveTag(String tagID) throws TException {
        Item item = null;
        //an com roi lam tiep
        List<String> listitem = getAllItemsIDhaveTag(tagID, 100);
        int index = getRandomIndex(listitem.size());
        String itemID = listitem.get(index);
        item = getItemFromItemID(itemID);
        return item;
    }

    @Override
    public void increaseViewCountItem(String itemID) throws TException {
        handler.increaseViewCountItem(itemID);
    }

    @Override
    public boolean deleteItem(String itemID) throws TException {
        return handler.deleteItem(itemID);
    }

    @Override
    public boolean deleteAllItem(List<String> itemIDs) throws TException {
        return handler.deleteAllItem(itemIDs);
    }

    @Override
    public boolean editItem(String itemID, String newItemValue, List<String> newTagIDs) throws TException {
        return handler.editItem(itemID, newItemValue, newTagIDs);
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
        return handler.getTopItems(number);
    }

    @Override
    public List<Item> getTopItemsofTag(long number, String tagID) throws TException {
        return handler.getTopItemsofTag(number, tagID);
    }

    @Override
    public boolean blockUser(String userID) throws TException {
        return blockUser(userID);
    }

    @Override
    public boolean unblockUser(String userID) throws TException {
        return unblockUser(userID);
    }

    @Override
    public boolean deleteUser(String userID) throws TException {
        return handler.deleteUser(userID);
    }

    @Override
    public Item getItemFromItemID(String itemID) throws TException {
        Item item = null;
        try {
            item = (Item) MyCache.getInstance().get(itemID);
            if (item == null) {
                item = handler.getItemFromItemID(itemID);
                MyCache.getInstance().set(itemID, 3600, item);
            }
        } catch (IOException ex) {
            Logger.getLogger(FrontendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    @Override
    public List<Item> getItemsFromListItemID(List<String> itemIDs) throws TException {
        return handler.getItemsFromListItemID(itemIDs);
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
        return handler.editUser(userID, userToken, userRole);
    }

    @Override
    public boolean deleteAllUser() throws TException {
        return handler.deleteAllUser();
    }

    @Override
    public List<Tag> getTagKeyword(String keyWord) throws TException {
        return handler.getTagKeyword(keyWord);
    }

    @Override
    public List<Item> getFavouriteItems(String userID, long number) throws TException {
        return handler.getFavouriteItems(userID, number);
    }

    @Override
    public List<Item> getFavouriteItemsofTag(String userID, long number, String tagID) throws TException {
        return handler.getFavouriteItemsofTag(userID, number, tagID);
    }

    @Override
    public boolean insertFavouriteItem(String userID, String itemID) throws TException {
        return handler.insertFavouriteItem(userID, itemID);
    }

    @Override
    public boolean deleteFavouriteItem(String userID, String itemID) throws TException {
        return handler.deleteFavouriteItem(userID, itemID);
    }

    @Override
    public long itemdbSize() throws TException {
        return handler.itemdbSize();
    }

    @Override
    public long tagdbSize() throws TException {
         return handler.tagdbSize();
    }

    @Override
    public long itemtagdbSize() throws TException {
         return handler.itemtagdbSize();
    }

    @Override
    public long itemtagSize(String tagID) throws TException {
         return handler.itemtagSize(tagID);
    }

    @Override
    public long userdbSize() throws TException {
         return handler.userdbSize();
    }

    @Override
    public long itemsLikeSize(String userID) throws TException {
         return handler.itemsLikeSize(userID);
    }

    @Override
    public long itemsDislikeSize(String userID) throws TException {
        return handler.itemsDislikeSize(userID);
    }

    @Override
    public long favouriteItemsSize(String userID) throws TException {
         return handler.favouriteItemsSize(userID);
    }

    @Override
    public List<String> getAllItemsIDLike(String userID) throws TException {
         return handler.getAllItemsIDLike(userID);
    }

    @Override
    public List<Item> getAllItemsLike(String userID, int number) throws TException {
         return handler.getAllItemsLike(userID, number);
    }

    @Override
    public boolean insertLikedItem(String userID, String itemID) throws TException {
         return handler.insertLikedItem(userID, itemID);
    }

    @Override
    public boolean deleteLikedItem(String userID, String itemID) throws TException {
         return handler.deleteLikedItem(userID, itemID);
    }

    @Override
    public List<String> getAllItemsIDDislike(String userID) throws TException {
         return handler.getAllItemsIDDislike(userID);
    }

    @Override
    public List<Item> getAllItemsDislike(String userID, int number) throws TException {
         return handler.getAllItemsDislike(userID, number);
    }

    @Override
    public boolean insertDislikedItem(String userID, String itemID) throws TException {
         return handler.insertDislikedItem(userID, itemID);
    }

    @Override
    public boolean deleteDislikedItem(String userID, String itemID) throws TException {
         return handler.deleteDislikedItem(userID, itemID);
    }

    @Override
    public boolean userExisted(String userID) throws TException {
         return handler.userExisted(userID);
    }
}
