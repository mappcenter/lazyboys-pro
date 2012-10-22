/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class UserInfo {

    public List<Item> getUserFavoriteItems(String uID) throws IOException {
        String key = "myItems" + uID;
        List<Item> items = (List<Item>) MyCache.getInstance().get(key);
        return items;
    }

    public void setUserFavoriteItems(String uID, Item _item) throws IOException {
        String key = "myItems" + uID;
        List<Item> items = this.getUserFavoriteItems(uID);
        if (items == null) {
            items = new ArrayList<Item>();
        }
        if (items.size() < 31) {
            if (checkExistedUserItems(items, _item.itemID)) {
                return;
            }
            items.add(_item);
            MyCache.getInstance().set(key, 3600, items);
        }
    }

    public boolean checkExistedUserItems(List<Item> _items, String ItemID) {
        for (int i = 0; i < _items.size(); i++) {
            if (_items.get(i).itemID.compareTo(ItemID) == 0) {
                return true;
            }
        }
        return false;
    }

    public void removeUserItems(String uID, String ItemID) throws IOException {
        String key = "myItems" + uID;
        List<Item> items = this.getUserFavoriteItems(uID);
        if (items == null) {
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).itemID.compareTo(ItemID) == 0) {
                items.remove(i);
            }
            MyCache.getInstance().set(key, 3600, items);
        }
    }

    public void CachingUserItems(List<Item> items, String uID) throws IOException {
        String key = "myItems" + uID;
        MyCache.getInstance().set(key, 3600, items);
    }
}
