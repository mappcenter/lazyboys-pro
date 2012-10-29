/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.sound.midi.SysexMessage;
import libs.Item;
import libs.Tag;
import libs.User;
import org.apache.thrift.TException;

/**
 * interface between middle ware and front end
 *
 * @author chanhlt
 */
public class FrontendHandler implements libs.MiddlewareFrontend.Iface {

    BackendHandler handler = new BackendHandler();
    static Map<String, Object> local_cache = new HashMap<>();
    private static List<Tag> listTag = new ArrayList<>();
    static long numberTopTags = 0;
    static long numberItemsIDofTag = 0;

    public FrontendHandler() throws TException, IOException {
    }

    @Override
    public List<Tag> getAllTag() throws TException {
        if (!listTag.isEmpty()) {
            System.out.println("getAllTag from cache ...");
            return listTag;
        }
        listTag = handler.getAllTag();
        for (Tag tag : listTag) {
            local_cache.put("getTag" + tag.tagID, tag);
        }
        System.out.println("getAllTag from backend ...");
        return listTag;
    }

    @Override
    public boolean insertTag(String tagName) throws TException {
        boolean result = handler.insertTag(tagName);
        if (result) {
            System.out.println("insert tag success ...");
            listTag = handler.getAllTag();
        } else {
            System.out.println("insert tag failed ...");
        }
        return result;
    }

    @Override
    public boolean deleteTag(String tagID) throws TException {
        boolean result = handler.deleteTag(tagID);
        if (result) {
            System.out.println("delete tag success ...");
            listTag = handler.getAllTag();
        } else {
            System.out.println("delete tag failed ...");
        }
        return result;
    }

    @Override
    public boolean deleteAllTag(List<String> tagIDs) throws TException {
        boolean result = handler.deleteAllTag(tagIDs);
        if (result) {
            System.out.println("delete all tag success ...");
            listTag = handler.getAllTag();
        } else {
            System.out.println("delete all tag failed ...");
        }
        return result;
    }

    @Override
    public boolean editTag(String tagID, String tagName) throws TException {
        boolean result = handler.editTag(tagID, tagName);
        if (result) {
            System.out.println("edit tag success ...");
            listTag = handler.getAllTag();
        } else {
            System.out.println("edit tag failed ...");
        }
        return result;
    }

    @Override
    public Tag getTag(String tagID) throws TException {
        if (local_cache.containsKey("getTag" + tagID)) {
            System.out.println("get tag from cache ...");
            return (Tag) local_cache.get("getTag" + tagID);
        }
        System.out.println("get tag froom backend ...");
        return handler.getTag(tagID);
    }

    @Override
    public void setViewCountTag(String tagID) throws TException {
        System.out.println("set view count tag ...");
        handler.setViewCountTag(tagID);
    }

    @Override
    public List<Tag> getTopTags(long number) throws TException {
        if (number != numberTopTags) {
            System.out.println("get top tag from cache ...");
            List<Tag> result = handler.getTopTags(number);
            numberTopTags = number;
            return result;
        }
        System.out.println("get top tag from backend ...");
        return (List<Tag>) local_cache.get("getTopTags");
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
        System.out.println("get " + number + " items  ...");
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
        System.out.println("get " + numberItems + " item have tag " + tagID + " ...");
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
        System.out.println("paging status of tag: " + tagID + " ...");
        return listItem;
        //return handler.pagingItemsTag(tagID, pageNumber, numberItems);
    }

    @Override
    public List<String> getAllItemsIDhaveTag(String tagID, int numberItemsID) throws TException {
        List<String> listItemID;
        if (local_cache.containsKey("getAllItemsIDhaveTag" + tagID) && numberItemsID <= numberItemsIDofTag) {
            System.out.println("get all itemID have tag " + tagID + " from cache...");
            listItemID = (List<String>) local_cache.get("getAllItemsIDhaveTag" + tagID);
            return listItemID;
        }
        listItemID = handler.getAllItemsIDhaveTag(tagID, numberItemsID);
        System.out.println("get all itemID have tag " + tagID + " from backend...");
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
        Item item;
        List<String> listitem = getAllItemsIDhaveTag(tagID, 1000);
        int index = getRandomIndex(listitem.size());
        String itemID = listitem.get(index);
        item = getItemFromItemID(itemID);
        System.out.println("get random item " + itemID + " from tag " + tagID + " ..");
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

        if (local_cache.containsKey("item" + itemID)) {
            System.out.println("get item " + itemID + " from cache ...");
            return (Item) local_cache.get("item" + itemID);
        }
        System.out.println("get item " + itemID + " from backend ...");
        Item item = handler.getItemFromItemID(itemID);
        local_cache.put("item" + itemID, item);
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

    @Override
    public User getUser(String userID) throws TException {
        return handler.getUser(userID);
    }

    @Override
    public List<String> getAllUser() throws TException {
        return handler.getAllUser();
    }

    @Override
    public List<Item> getItemsPageKeyword(String keyWord, long pageNumber, long itemNumber) throws TException {
        return handler.getItemsPageKeyword(keyWord, pageNumber, itemNumber);
    }
}
