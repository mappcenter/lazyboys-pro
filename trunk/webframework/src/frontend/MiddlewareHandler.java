/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import MiddlewareFrontend.Item;
import MiddlewareFrontend.MiddlewareFrontend;
import MiddlewareFrontend.Tag;
import com.vng.jcore.common.Config;
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

/**
 *
 * @author chanhlt
 */
public class MiddlewareHandler implements MiddlewareFrontend.Iface {

    TTransport transport;
    MiddlewareFrontend.Client client;
    TFramedTransport framedTransport;
    TProtocol protocol;

    void init() throws IOException {

        String host = Config.getParam("fresher2012service", "host");
        int port = Integer.valueOf(Config.getParam("fresher2012service", "port"));

        transport = new TSocket(host, port);
        framedTransport = new TFramedTransport(transport);
        protocol = new TBinaryProtocol(framedTransport);
        client = new MiddlewareFrontend.Client(protocol);

    }

    @Override
    public List<Tag> getAllTag() throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        List<Tag> result = client.getTopTags(number);
        transport.close();

        return result;
    }

    @Override
    public List<Item> getAllItems(long number) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Item> listItem;

        transport.open();
        listItem = client.getAllItems(number);
        transport.close();

        return listItem;
    }

    @Override
    public List<Item> getAllItemshaveTag(String tagID, int numberItems) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Item> listItem;

        transport.open();
        listItem = client.getAllItemshaveTag(tagID, numberItems);
        transport.close();

        return listItem;
    }

    @Override
    public List<Item> pagingItemsTag(String tagID, int pageNumber, int numberItems) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        List<Item> result = client.pagingItemsTag(tagID, pageNumber, numberItems);
        transport.close();

        return result;
    }

    public List<String> getAllItemsIDhaveTag(String tagID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<String> listItemID;

        transport.open();
        listItemID = client.getAllItemsIDhaveTag(tagID, 12);
        transport.close();

        return listItemID;
    }

    @Override
    public Item getRandomItem() throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        Item resItem = client.getRandomItemhaveTag(tagID);
        transport.close();

        return resItem;
    }

    @Override
    public void increaseViewCountItem(String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        client.increaseViewCountItem(itemID);
        transport.close();


    }

    @Override
    public boolean deleteItem(String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        boolean result = client.editItem(itemID, newItemValue, newTagIDs);
        transport.close();

        return result;
    }

    @Override
    public List<Item> getItemKeyword(String keyWord) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        List<Item> result = client.getTopItemsofTag(number, tagID);
        transport.close();

        return result;
    }

   

    @Override
    public boolean blockUser(String userName) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        boolean result = client.blockUser(userName);
        transport.close();

        return result;
    }

    @Override
    public boolean unblockUser(String userName) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        boolean result = client.unblockUser(userName);
        transport.close();

        return result;
    }

    @Override
    public boolean deleteUser(String usrName) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        boolean result = client.deleteUser(usrName);
        transport.close();

        return result;
    }

    @Override
    public Item getItemFromItemID(String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        Item result = client.getItemFromItemID(itemID);
        transport.close();

        return result;
    }

    @Override
    public List<Item> getItemsFromListItemID(List<String> itemIDs) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        List<Item> result = client.getItemsFromListItemID(itemIDs);
        transport.close();

        return result;
    }

    @Override
    public void increaseLikeCountItem(String itemID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        String result = client.insertItem(content, tagIDs);
        transport.close();

        return result;
    }

    @Override
    public List<Item> getItemsPage(long pageNumber, long itemNumber, String tagID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        List<Item> result = client.getItemsPage(pageNumber, itemNumber, tagID);
        transport.close();

        return result;
    }

    

    

    @Override
    public boolean addUser(String userID, String userToken, int userRole) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        boolean result = client.addUser(userID, userToken, userRole);
        transport.close();

        return result;
    }

    @Override
    public List<String> getAllItemsIDhaveTag(String tagID, int numberItemsID) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        List<String> result = client.getAllItemsIDhaveTag(tagID, numberItemsID);
        transport.close();

        return result;
    }

   

    @Override
    public boolean editUser(String userID, String userToken, int userRole) throws TException {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        transport.open();
        boolean result = client.deleteAllUser();
        transport.close();

        return result;
    }

    @Override
    public List<Item> getFavoriteItems(long number) throws TException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean insertFavoriteItem(List<Item> listFavoriteItems, Item item) throws TException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean deleteFavoriteItem(List<Item> listFavoriteItems, Item item) throws TException {
        throw new UnsupportedOperationException("Not supported yet.");
    }  
    
}
