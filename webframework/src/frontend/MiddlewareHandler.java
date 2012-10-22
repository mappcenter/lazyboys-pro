/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
/**
 *
 * @author chanhlt
 */
public class MiddlewareHandler implements MiddlewareFrontend.Iface {

    static TTransport  transport;
    static MiddlewareFrontend.Client client;
    static TFramedTransport framedTransport;
    static TProtocol protocol;
    static String host;
    static int port;
    

    public static void init() throws IOException, TTransportException {

        host = getConfig.getInstance().getHost();
        port = getConfig.getInstance().getPort();
        
        transport = new TSocket(host, port);
        framedTransport = new TFramedTransport(transport);
        protocol = new TBinaryProtocol(framedTransport);
        client = new MiddlewareFrontend.Client(protocol);
        transport.open();     
    }

    @Override
    public List<Tag> getAllTag() throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Tag> lisTag;
        lisTag = client.getAllTag();
        return lisTag;
    }

    @Override
    public boolean insertTag(String tagName) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean result = client.insertTag(tagName);
        return result;
    }

    @Override
    public boolean deleteTag(String tagID) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean result = client.deleteTag(tagID);
        return result;
    }

    @Override
    public boolean deleteAllTag(List<String> tagIDs) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean result = client.deleteAllTag(tagIDs);
        return result;
    }

    @Override
    public boolean editTag(String tagID, String tagName) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean result = client.editTag(tagID, tagName);
        return result;
    }

    @Override
    public Tag getTag(String tagID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        Tag result = client.getTag(tagID);
        return result;
    }

    @Override
    public void setViewCountTag(String tagID) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        client.setViewCountTag(tagID);
    }

    @Override
    public List<Tag> getTopTags(long number) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Tag> result = client.getTopTags(number);

        return result;
    }

    @Override
    public List<Item> getAllItems(long number) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Item> listItem;

        listItem = client.getAllItems(number);

        return listItem;
    }

    @Override
    public List<Item> getAllItemshaveTag(String tagID, int numberItems) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Item> listItem;
        listItem = client.getAllItemshaveTag(tagID, numberItems);
        return listItem;
    }

    @Override
    public List<Item> pagingItemsTag(String tagID, int pageNumber, int numberItems) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Item> result = client.pagingItemsTag(tagID, pageNumber, numberItems);
        return result;
    }

    public List<String> getAllItemsIDhaveTag(String tagID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<String> listItemID;

        listItemID = client.getAllItemsIDhaveTag(tagID, 12);

        return listItemID;
    }

    @Override
    public synchronized Item getRandomItem() throws TException, TTransportException {
        try {
            if(transport==null ||transport.isOpen()==false) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Item resItem = client.getRandomItem();
        
        return resItem;

    }

    @Override
    public synchronized Item getRandomItemhaveTag(String tagID) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        Item resItem = client.getRandomItemhaveTag(tagID);
        return resItem;
    }

    @Override
    public void increaseViewCountItem(String itemID) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        client.increaseViewCountItem(itemID);
    }

    @Override
    public boolean deleteItem(String itemID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean result = client.deleteItem(itemID);
        return result;
    }

    @Override
    public boolean deleteAllItem(List<String> itemIDs) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean result = client.deleteAllItem(itemIDs);
        return result;
    }

    @Override
    public boolean editItem(String itemID, String newItemValue, List<String> newTagIDs) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean result = client.editItem(itemID, newItemValue, newTagIDs);
        return result;
    }

    @Override
    public List<Item> getItemKeyword(String keyWord) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Item> result = client.getItemKeyword(keyWord);
        return result;
    }

    @Override
    public List<Item> getItemKeywordTag(String keyWord, String tagID) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Item> result = client.getItemKeywordTag(keyWord, tagID);
        return result;
    }

    @Override
    public List<Item> getTopItems(long number) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Item> result = client.getTopItems(number);
        return result;
    }

    @Override
    public List<Item> getTopItemsofTag(long number, String tagID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Item> result = client.getTopItemsofTag(number, tagID);
        return result;
    }

   

    @Override
    public boolean blockUser(String userName) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean result = client.blockUser(userName);
        return result;
    }

    @Override
    public boolean unblockUser(String userName) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }


        boolean result = client.unblockUser(userName);
        return result;
    }

    @Override
    public boolean deleteUser(String usrName) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean result = client.deleteUser(usrName);
        return result;
    }

    @Override
    public Item getItemFromItemID(String itemID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        Item result = client.getItemFromItemID(itemID);
        return result;
    }

    @Override
    public List<Item> getItemsFromListItemID(List<String> itemIDs) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Item> result = client.getItemsFromListItemID(itemIDs);
        return result;
    }

    @Override
    public void increaseLikeCountItem(String itemID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        client.increaseLikeCountItem(itemID);
    }

    @Override
    public void increaseDislikeCountItem(String itemID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        client.increaseDislikeCountItem(itemID);
    }

    @Override
    public String insertItem(String content, List<String> tagIDs) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        String result = client.insertItem(content, tagIDs);
        return result;
    }

    @Override
    public List<Item> getItemsPage(long pageNumber, long itemNumber, String tagID) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Item> result = client.getItemsPage(pageNumber, itemNumber, tagID);
        return result;
    }

    

    

    @Override
    public boolean addUser(String userID, String userToken, int userRole) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean result = client.addUser(userID, userToken, userRole);
        return result;
    }

    @Override
    public List<String> getAllItemsIDhaveTag(String tagID, int numberItemsID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<String> result = client.getAllItemsIDhaveTag(tagID, numberItemsID);
        return result;
    }

   

    @Override
    public boolean editUser(String userID, String userToken, int userRole) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }


        boolean result = client.editUser(userID, userToken, userRole);
        return result;
    }

    @Override
    public boolean deleteAllUser() throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean result = client.deleteAllUser();
        return result;
    }

    @Override
    public List<Tag> getTagKeyword(String keyWord) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Tag> result = client.getTagKeyword(keyWord);
        return result;
    }

    @Override
    public List<Item> getFavouriteItems(String userID, long number) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Item> result = client.getFavouriteItems(userID, number);
        return result;
    }

    @Override
    public List<Item> getFavouriteItemsofTag(String userID, long number, String tagID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Item> result = client.getFavouriteItemsofTag(userID, number, tagID);
        return result;
    }

    @Override
    public boolean insertFavouriteItem(String userID, String itemID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean result = client.insertFavouriteItem(userID, itemID);
        return result;
    }

    @Override
    public boolean deleteFavouriteItem(String userID, String itemID) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean result = client.deleteFavouriteItem(userID, itemID);
        return result;
    }

    @Override
    public long itemdbSize() throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        long result = client.itemdbSize();
        return result;
    }

    @Override
    public long tagdbSize() throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        long result = client.tagdbSize();
        return result;
    }

    @Override
    public long itemtagdbSize() throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        long result = client.itemtagdbSize();
        return result;
    }

    @Override
    public long itemtagSize(String tagID) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        long result = client.itemtagSize(tagID);
        return result;
    }

    @Override
    public long userdbSize() throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        long result = client.userdbSize();
        return result;
    }

    @Override
    public long itemsLikeSize(String userID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        long result = client.itemsLikeSize(userID);
        return result;
    }

    @Override
    public long itemsDislikeSize(String userID) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        long result = client.itemsDislikeSize(userID);
        return result;
    }

    @Override
    public long favouriteItemsSize(String userID) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        long result = client.favouriteItemsSize(userID);
        return result;
    }

    @Override
    public List<String> getAllItemsIDLike(String userID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<String> result = client.getAllItemsIDLike(userID);
        return result;
    }

    @Override
    public List<Item> getAllItemsLike(String userID, int number) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Item> result = client.getAllItemsLike(userID, number);
        return result;
    }

    @Override
    public boolean insertLikedItem(String userID, String itemID) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean result = client.insertLikedItem(userID, itemID);
        return result;
    }

    @Override
    public boolean deleteLikedItem(String userID, String itemID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean result = client.deleteLikedItem(userID, itemID);
        return result;
    }

    @Override
    public List<String> getAllItemsIDDislike(String userID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> result = client.getAllItemsIDDislike(userID);
        return result;
    }

    @Override
    public List<Item> getAllItemsDislike(String userID, int number) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Item> result = client.getAllItemsDislike(userID, number);
        return result;
    }

    @Override
    public boolean insertDislikedItem(String userID, String itemID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean result = client.insertDislikedItem(userID, itemID);
        return result;
    }

    @Override
    public boolean deleteDislikedItem(String userID, String itemID) throws TException {
       try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean result = client.deleteDislikedItem(userID, itemID);
        return result;
    }

    @Override
    public boolean userExisted(String userID) throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean result = client.userExisted(userID);
        return result;
    }

    @Override
    public User getUser(String userID) throws TException {
        try {
           if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        User result = client.getUser(userID);
        return result;
    }

    @Override
    public List<String> getAllUser() throws TException {
        try {
            if(transport==null || ! transport.isOpen()) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<String> result = client.getAllUser();
        return result;
    }
}
