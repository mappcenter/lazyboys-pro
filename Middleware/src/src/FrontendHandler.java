/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import libs.Item;
import libs.Tag;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import memcache.MyCache;
import org.apache.thrift.TException;

/**
 * interface between middle ware and front end
 *
 * @author chanhlt
 */public class FrontendHandler implements libs.MiddlewareFrontend.Iface {

    BackendHandler handler = new BackendHandler();

    public void Caching() throws TException {
        List<Tag> listTag = getAllTag();
        for (int i = 0; i < listTag.size(); i++) {
            getAllItemsIDhaveTag(listTag.get(i).tagID, 1);
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

        List<Tag> listTag = null;
        try {
            listTag = (List<Tag>) MyCache.getInstance().get("listTag");
            if (listTag == null) {
                listTag = handler.getAllTag();
                Properties pro = new Properties();
                pro.load(new FileInputStream("src/conf/config.ini"));
                int ttl = Integer.parseInt(pro.getProperty("ttl"));
                MyCache.getInstance().set("listTag", ttl, listTag);
                //System.out.println("get from database");
            }
//            } else {
//                System.out.println("get from memcache");
//            }
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
        Tag tag = getRandomTag();
        Item item = getRandomItemhaveTag(tag.tagID);
        return item;
    }

    public int getRandomIndex(int size) {
        int index = (new Random()).nextInt(size);
        return index;
    }

    public Tag getRandomTag() throws TException {
        List<Tag> listag = getAllTag();
        int index = getRandomIndex(listag.size());
        Tag tag = listag.get(index);
        return tag;
    }

    @Override
    public Item getRandomItemhaveTag(String tagID) throws TException {
        Item item = null;
        //an com roi lam tiep
        List<String> listitem = getAllItemsIDhaveTag(tagID, 1000);
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
