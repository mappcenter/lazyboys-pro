/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IOFile;

import frontend.Item;
import frontend.MiddlewareFrontend;
import frontend.MiddlewareHandler;
import frontend.MyLocalCache;
import frontend.Tag;
import frontend.User;
import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class CachingIndexPage {

    public static FileIO file = new FileIO();
    public String indexUrl = "src/IOFile/cacheindex.html";
    MyLocalCache mycache = new MyLocalCache();
    public List<String> indexHtlm = new ArrayList<String>();

    public List<String> getPageHtml() {
        List<String> aIndexPageHtml = file.loadFile(indexUrl);
        return aIndexPageHtml;
    }

    public List<String> renderIndexHtml() throws TException {
        CachingIndexPage cachingIndexPage = new CachingIndexPage();
        replaceTopTags(cachingIndexPage);
        //renderIndexHtmlForUser(UserID,UserName);
        return indexHtlm;
    }

    private void replaceTopTags(CachingIndexPage cachingIndexPage) throws TException {
        indexHtlm = cachingIndexPage.getPageHtml();
        String replace = "";
        String strRenderTopItems = cachingIndexPage.renderTopItems();
        String strRenderTopTags=cachingIndexPage.renderListTopTags();
        if (indexHtlm != null) {
            for (int i = 0; i < indexHtlm.size(); i++) {
                if (indexHtlm.get(i).contains("{{topItemsHere}}")) {
                    replace = indexHtlm.get(i).replace("{{topItemsHere}}", strRenderTopItems);
                    indexHtlm.set(i, replace);
                }
                if (indexHtlm.get(i).contains("{{listTopTagsHere}}")) {
                    replace = indexHtlm.get(i).replace("{{listTopTagsHere}}", strRenderTopTags);
                    indexHtlm.set(i, replace);
                }
            }
        }
    }

    public String renderTopItems() throws TException {
        List<Item> items = mycache.getTopItems();
        String result = "";
        for (int i = 0; i < items.size(); i++) {
            result += "<tr>";
            result += "<td id='itemID" + items.get(i).itemID + "' style='width:84%;'>" + items.get(i).content + "</td>";
            result += "<td style='width:16%;'><a class='ufeedItemWall' href='javascript:uFeedWall(" + items.get(i).itemID + ")' rel='" + items.get(i).itemID + "'> Dang</a> |  <a class='saveItem' href='javascript:uSaveItem(" + items.get(i).itemID + ")' rel='" + items.get(i).itemID + "' >Luu</a>";
            result += "</td>";
            result += "</tr>";
        }
        return result;
    }
    public String renderListTopTags() {
        List<Tag> listTopTags = mycache.getTopTags();
        String result = "";
        for (int i = 0; i < listTopTags.size(); i++) {
            result += "<a href='javascript:getRandomItemOfTagForRandom(" + listTopTags.get(i).tagID + ");' rel='" + listTopTags.get(i).tagID + "'style='font-size:" + randomSize(13, 25) + "'>" + listTopTags.get(i).tagName + "</a>";
        }
        return result;
    }    
    public int randomSize(int from, int to) {
        return (from + (int) (Math.random() * to));
    }

    public List<String> renderIndexHtmlForUser(List<String> html,String userID, String userName) {
        String replace = "";
        List<String> temp = html;
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).contains("{{userNameHere}}")) {
                replace = temp.get(i).replace("{{userNameHere}}", userName);
                temp.set(i, replace);
            }
            if (temp.get(i).contains("{{userIDHere}}")) {
                replace = temp.get(i).replace("{{userIDHere}}", userID);
                temp.set(i, replace);
            }
        }        
        return temp;
    }
    public List<String> renderItemRandom(Item item) {

        return null;
    }
}
