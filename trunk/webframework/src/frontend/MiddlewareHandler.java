/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author chanhlt
 */
public class MiddlewareHandler implements MiddlewareFrontend.Iface {

    ConnectionPool connectionPool = ConnectionPool.getInstance();
    static Map<String, Object> LocalCache = new HashMap<String, Object>();
    public static MyLocalCache myLocalCache = new MyLocalCache();

    public synchronized static void init() throws IOException, TTransportException, TException {
        //startLocalCache();
    }

    public void startLocalCache() throws TException, InterruptedException {
        Connection connect = connectionPool.getConnection();
        List<Tag> topTags = connect.getClient().getTopTags(40);
        connectionPool.releaseConnection(connect);
        LocalCache.put("topTags", topTags);

        List<Tag> listTags = connect.getClient().getAllTag();
        LocalCache.put("listAllTags", listTags);
        myLocalCache.startMyLocalCache();
    }

    public Object getTopTags() {
        return LocalCache.get("topTags");
    }

    @Override
    public List<Tag> getAllTag() throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Tag> lisTag;
            lisTag = connect.getClient().getAllTag();
            connectionPool.releaseConnection(connect);
            return lisTag;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean insertTag(String tagName) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().insertTag(tagName);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteTag(String tagID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().deleteTag(tagID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteAllTag(List<String> tagIDs) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().deleteAllTag(tagIDs);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean editTag(String tagID, String tagName) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().editTag(tagID, tagName);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Tag getTag(String tagID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            Tag result = connect.getClient().getTag(tagID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void setViewCountTag(String tagID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            connect.getClient().setViewCountTag(tagID);
            connectionPool.releaseConnection(connect);
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Tag> getTopTags(long number) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Tag> result = connect.getClient().getTopTags(number);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Item> getAllItems(long number) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().getAllItems(number);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Item> getAllItemshaveTag(String tagID, int numberItems) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().getAllItemshaveTag(tagID, numberItems);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Item> pagingItemsTag(String tagID, int pageNumber, int numberItems) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().pagingItemsTag(tagID, pageNumber, numberItems);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<String> getAllItemsIDhaveTag(String tagID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<String> result = connect.getClient().getAllItemsIDhaveTag(tagID, 12);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Item getRandomItem() throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            Item result = connect.getClient().getRandomItem();
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Item getRandomItemhaveTag(String tagID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            Item result = connect.getClient().getRandomItemhaveTag(tagID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void increaseViewCountItem(String itemID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            connect.getClient().increaseViewCountItem(itemID);
            connectionPool.releaseConnection(connect);
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean deleteItem(String itemID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().deleteItem(itemID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteAllItem(List<String> itemIDs) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().deleteAllItem(itemIDs);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean editItem(String itemID, String newItemValue, List<String> newTagIDs) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().editItem(itemID, newItemValue, newTagIDs);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Item> getItemKeyword(String keyWord) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().getItemKeyword(keyWord);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Item> getItemKeywordTag(String keyWord, String tagID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().getItemKeywordTag(keyWord, tagID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Item> getTopItems(long number) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().getTopItems(number);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Item> getTopItemsofTag(long number, String tagID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().getTopItemsofTag(number, tagID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean blockUser(String userName) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().blockUser(userName);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean unblockUser(String userName) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().unblockUser(userName);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteUser(String usrName) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().deleteUser(usrName);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Item getItemFromItemID(String itemID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            Item result = connect.getClient().getItemFromItemID(itemID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Item> getItemsFromListItemID(List<String> itemIDs) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().getItemsFromListItemID(itemIDs);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void increaseLikeCountItem(String itemID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            connect.getClient().increaseLikeCountItem(itemID);
            connectionPool.releaseConnection(connect);
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void increaseDislikeCountItem(String itemID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            connect.getClient().increaseDislikeCountItem(itemID);
            connectionPool.releaseConnection(connect);
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String insertItem(String content, List<String> tagIDs) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            String result = connect.getClient().insertItem(content, tagIDs);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Item> getItemsPage(long pageNumber, long itemNumber, String tagID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().getItemsPage(pageNumber, itemNumber, tagID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean addUser(String userID, String userToken, int userRole) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().addUser(userID, userToken, userRole);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<String> getAllItemsIDhaveTag(String tagID, int numberItemsID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<String> result = connect.getClient().getAllItemsIDhaveTag(tagID, numberItemsID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean editUser(String userID, String userToken, int userRole) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().editUser(userID, userToken, userRole);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteAllUser() throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().deleteAllUser();
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Tag> getTagKeyword(String keyWord) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Tag> result = connect.getClient().getTagKeyword(keyWord);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Item> getFavouriteItems(String userID, long number) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().getFavouriteItems(userID, number);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Item> getFavouriteItemsofTag(String userID, long number, String tagID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().getFavouriteItemsofTag(userID, number, tagID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean insertFavouriteItem(String userID, String itemID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().insertFavouriteItem(userID, itemID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteFavouriteItem(String userID, String itemID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().deleteFavouriteItem(userID, itemID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public long itemdbSize() throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            long result = connect.getClient().itemdbSize();
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public long tagdbSize() throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            long result = connect.getClient().tagdbSize();
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public long itemtagdbSize() throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            long result = connect.getClient().itemtagdbSize();
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public long itemtagSize(String tagID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            long result = connect.getClient().itemtagSize(tagID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public long userdbSize() throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            long result = connect.getClient().userdbSize();
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public long itemsLikeSize(String userID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            long result = connect.getClient().itemsLikeSize(userID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public long itemsDislikeSize(String userID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            long result = connect.getClient().itemsDislikeSize(userID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public long favouriteItemsSize(String userID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            long result = connect.getClient().favouriteItemsSize(userID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public List<String> getAllItemsIDLike(String userID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<String> result = connect.getClient().getAllItemsIDLike(userID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Item> getAllItemsLike(String userID, int number) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().getAllItemsLike(userID, number);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean insertLikedItem(String userID, String itemID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().insertLikedItem(userID, itemID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteLikedItem(String userID, String itemID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().deleteLikedItem(userID, itemID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<String> getAllItemsIDDislike(String userID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<String> result = connect.getClient().getAllItemsIDDislike(userID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Item> getAllItemsDislike(String userID, int number) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().getAllItemsDislike(userID, number);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean insertDislikedItem(String userID, String itemID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().insertDislikedItem(userID, itemID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteDislikedItem(String userID, String itemID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().deleteDislikedItem(userID, itemID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean userExisted(String userID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            boolean result = connect.getClient().userExisted(userID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public User getUser(String userID) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            User result = connect.getClient().getUser(userID);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<String> getAllUser() throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<String> result = connect.getClient().getAllUser();
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Item> getItemsPageKeyword(String keyWord, long pageNumber, long itemNumber) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().getItemsPageKeyword(keyWord, pageNumber, itemNumber);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Item> getItemsPageKeywordOfTag(String keyWord, String tagID, long pageNumber, long itemNumber) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<Item> result = connect.getClient().getItemsPageKeywordOfTag(keyWord, tagID, pageNumber, itemNumber);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<String> friendLikesItemID(String itemID, List<String> listFriends) throws TException {
        try {
            Connection connect = connectionPool.getConnection();
            List<String> result = connect.getClient().friendLikesItemID(itemID, listFriends);
            connectionPool.releaseConnection(connect);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(MiddlewareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
