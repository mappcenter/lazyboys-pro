/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import libs.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author chanhlt
 */
public class BackendHandler implements libs.BackendMiddleware.Iface {

    TTransport transport;
    libs.BackendMiddleware.Client client;

    void init() throws IOException {

        String host = getConfig.getInstance().getHost();
        int port = getConfig.getInstance().getPort();

        transport = new TSocket(host, port);

        TFramedTransport framedTransport = new TFramedTransport(transport);

        TProtocol protocol = new TBinaryProtocol(framedTransport);

        client = new BackendMiddleware.Client(protocol);

    }

    @Override
    public List<Tag> getAllTag() throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Tag> lisTag;

        transport.open();
        lisTag = client.getAllTag();
        transport.close();
        return lisTag;
    }

    @Override
    public boolean insertTag(String tagName) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.insertTag(tagName);
        transport.close();
        return result;
    }

    @Override
    public boolean deleteTag(String tagID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.deleteTag(tagID);
        transport.close();
        return result;
    }

    @Override
    public boolean deleteAllTag(List<String> tagIDs) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.deleteAllTag(tagIDs);
        transport.close();
        return result;
    }

    @Override
    public boolean editTag(String tagID, String tagName) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.editTag(tagID, tagName);
        transport.close();
        return result;
    }

    @Override
    public Tag getTag(String tagID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        Tag result = client.getTag(tagID);
        transport.close();
        return result;
    }

    @Override
    public void setViewCountTag(String tagID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        client.setViewCountTag(tagID);
        transport.close();
    }

    @Override
    public List<Tag> getTopTags(long number) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<Tag> listag = client.getTopTags(number);
        transport.close();
        return listag;
    }

    @Override
    public List<Item> getAllItems(long number) throws TException {
        List<Item> listItem = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Item item = getRandomItem();
            listItem.add(item);
        }
        return listItem;
    }

    @Override
    public Item getItemFromItemID(String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        Item item = client.getItemFromItemID(itemID);
        transport.close();
        return item;
    }

    @Override
    public List<Item> getItemsFromListItemID(List<String> itemIDs) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<Item> listItem = client.getItemsFromListItemID(itemIDs);
        transport.close();
        return listItem;
    }

    @Override
    public List<Item> getAllItemshaveTag(String tagID, int numberItems) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<Item> result = client.getAllItemshaveTag(tagID, numberItems);
        transport.close();
        return result;
    }

    @Override
    public List<Item> pagingItemsTag(String tagID, int pageNumber, int numberItems) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<Item> result = client.pagingItemsTag(tagID, pageNumber, numberItems);
        transport.close();
        return result;
    }

    @Override
    public List<String> getAllItemsIDhaveTag(String tagID, int numberItemsID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<String> result = client.getAllItemsIDhaveTag(tagID, numberItemsID);
        transport.close();
        return result;
    }

    @Override
    public Item getRandomItem() throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        Item resItem = client.getRandomItem();
        transport.close();
        return resItem;
    }

    @Override
    public Item getRandomItemhaveTag(String tagID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        Item result = client.getRandomItemhaveTag(tagID);
        transport.close();
        return result;
    }

    @Override
    public void increaseViewCountItem(String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        client.increaseViewCountItem(itemID);
        transport.close();
    }

    @Override
    public void increaseLikeCountItem(String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        client.increaseLikeCountItem(itemID);
        transport.close();
    }

    @Override
    public void increaseDislikeCountItem(String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        client.increaseDislikeCountItem(itemID);
        transport.close();
    }

    @Override
    public String insertItem(String content, List<String> tagIDs) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        String string = client.insertItem(content, tagIDs);
        transport.close();
        return string;
    }

    @Override
    public boolean deleteItem(String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.deleteItem(itemID);
        transport.close();
        return result;
    }

    @Override
    public boolean deleteAllItem(List<String> itemIDs) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.deleteAllItem(itemIDs);
        transport.close();
        return result;
    }

    @Override
    public boolean editItem(String itemID, String newItemValue, List<String> newTagIDs) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.editItem(itemID, newItemValue, newTagIDs);
        transport.close();
        return result;
    }

    @Override
    public List<Item> getItemsPage(long pageNumber, long itemNumber, String tagID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<Item> listItem = client.getItemsPage(pageNumber, itemNumber, tagID);
        transport.close();
        return listItem;
    }

    @Override
    public List<Item> getItemKeyword(String keyWord) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<Item> result = client.getItemKeyword(keyWord);
        transport.close();
        return result;
    }

    @Override
    public List<Item> getItemKeywordTag(String keyWord, String tagID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<Item> result = client.getItemKeywordTag(keyWord, tagID);
        transport.close();
        return result;
    }

    @Override
    public List<Item> getTopItems(long number) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<Item> result = client.getTopItems(number);
        transport.close();
        return result;
    }

    @Override
    public List<Item> getTopItemsofTag(long number, String tagID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<Item> result = client.getTopItemsofTag(number, tagID);
        transport.close();
        return result;
    }

    @Override
    public boolean blockUser(String userID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.blockUser(userID);
        transport.close();
        return result;
    }

    @Override
    public boolean unblockUser(String userID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.unblockUser(userID);
        transport.close();
        return result;
    }

    @Override
    public boolean addUser(String userID, String userToken, int userRole) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.addUser(userID, userToken, userRole);
        transport.close();
        return result;
    }

    @Override
    public boolean deleteUser(String userID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.deleteUser(userID);
        transport.close();
        return result;
    }

    @Override
    public boolean editUser(String userID, String userToken, int userRole) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.editUser(userID, userToken, userRole);
        transport.close();
        return result;
    }

    @Override
    public boolean deleteAllUser() throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.deleteAllUser();
        transport.close();
        return result;
    }

    @Override
    public List<Tag> getTagKeyword(String keyWord) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<Tag> result = client.getTagKeyword(keyWord);
        transport.close();
        return result;
    }

    @Override
    public List<Item> getFavouriteItems(String userID, long number) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<Item> result = client.getFavouriteItems(userID, number);
        transport.close();
        return result;
    }

    @Override
    public List<Item> getFavouriteItemsofTag(String userID, long number, String tagID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<Item> result = client.getFavouriteItemsofTag(userID, number, tagID);
        transport.close();
        return result;
    }

    @Override
    public boolean insertFavouriteItem(String userID, String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.insertFavouriteItem(userID, itemID);
        transport.close();
        return result;
    }

    @Override
    public boolean deleteFavouriteItem(String userID, String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.deleteFavouriteItem(userID, itemID);
        transport.close();
        return result;
    }

    @Override
    public long itemdbSize() throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        long result = client.itemdbSize();
        transport.close();
        return result;
    }

    @Override
    public long tagdbSize() throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        long result = client.tagdbSize();
        transport.close();
        return result;
    }

    @Override
    public long itemtagdbSize() throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        long result = client.itemtagdbSize();
        transport.close();
        return result;
    }

    @Override
    public long itemtagSize(String tagID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        long result = client.itemtagSize(tagID);
        transport.close();
        return result;
    }

    @Override
    public long userdbSize() throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        long result = client.userdbSize();
        transport.close();
        return result;
    }

    @Override
    public long itemsLikeSize(String userID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        long result = client.itemsLikeSize(userID);
        transport.close();
        return result;
    }

    @Override
    public long itemsDislikeSize(String userID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        long result = client.itemsDislikeSize(userID);
        transport.close();
        return result;
    }

    @Override
    public long favouriteItemsSize(String userID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        long result = client.favouriteItemsSize(userID);
        transport.close();
        return result;
    }

    @Override
    public List<String> getAllItemsIDLike(String userID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<String> result = client.getAllItemsIDLike(userID);
        transport.close();
        return result;
    }

    @Override
    public List<Item> getAllItemsLike(String userID, int number) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<Item> result = client.getAllItemsLike(userID, number);
        transport.close();
        return result;
    }

    @Override
    public boolean insertLikedItem(String userID, String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.insertLikedItem(userID, itemID);
        transport.close();
        return result;
    }

    @Override
    public boolean deleteLikedItem(String userID, String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.deleteLikedItem(userID, itemID);
        transport.close();
        return result;
    }

    @Override
    public List<String> getAllItemsIDDislike(String userID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<String> result = client.getAllItemsIDDislike(userID);
        transport.close();
        return result;
    }

    @Override
    public List<Item> getAllItemsDislike(String userID, int number) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<Item> result = client.getAllItemsDislike(userID, number);
        transport.close();
        return result;
    }

    @Override
    public boolean insertDislikedItem(String userID, String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.insertDislikedItem(userID, itemID);
        transport.close();
        return result;
    }

    @Override
    public boolean deleteDislikedItem(String userID, String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.deleteDislikedItem(userID, itemID);
        transport.close();
        return result;
    }

    @Override
    public boolean userExisted(String userID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        boolean result = client.userExisted(userID);
        transport.close();
        return result;
    }

    @Override
    public User getUser(String userID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        User result = client.getUser(userID);
        transport.close();
        return result;
    }

    @Override
    public List<String> getAllUser() throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(BackendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transport.open();
        List<String> result = client.getAllUser();
        transport.close();
        return result;
    }
}
