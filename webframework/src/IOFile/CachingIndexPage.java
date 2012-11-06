/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IOFile;

import frontend.Item;
import frontend.MyLocalCache;
import frontend.Tag;
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
    public String indexHtlm ="";

    public String getPageHtml() {
        List<String> aIndexPageHtml = file.loadFile(indexUrl);
        String temp="";
        for (int i = 0; i < aIndexPageHtml.size(); i++) {
            temp+=aIndexPageHtml.get(i);
        }
        return temp;
    }

    public String renderIndexHtml() throws TException {
        CachingIndexPage cachingIndexPage = new CachingIndexPage();
        replaceTopTags(cachingIndexPage);
        //renderIndexHtmlForUser(UserID,UserName);
        return indexHtlm;
    }

    private void replaceTopTags(CachingIndexPage cachingIndexPage) throws TException {
        indexHtlm = cachingIndexPage.getPageHtml();
        //String replace = "";
        String strRenderTopItems = cachingIndexPage.renderTopItems();
        String strRenderTopTags=cachingIndexPage.renderListTopTags();
        String topItem="";
        if (indexHtlm != null) {
            //for (int i = 0; i < indexHtlm.size(); i++) {
                if (indexHtlm.contains("{{topItemsHere}}")) {
                    indexHtlm = indexHtlm.replace("{{topItemsHere}}", strRenderTopItems);
                    //indexHtlm.set(i, replaceAll);
                }
                if (indexHtlm.contains("{{listTopTagsHere}}")) {
                    indexHtlm = indexHtlm.replace("{{listTopTagsHere}}", strRenderTopTags);
                    //indexHtlm.set(i, replaceAll);
                }
            //}
        }
    }

    public String renderTopItems() throws TException {
        List<Item> items = mycache.getTopItems();
        String result = "";
        for (int i = 0; i < items.size(); i++) {
            result += "<tr>";
            result += "<td id='itemID" + items.get(i).itemID + "' style='width:84%;'>" + items.get(i).content + "</td>";
            result += "<td style='width:16%;'><a class='ufeedItemWall' href='javascript:uFeedWall(" + items.get(i).itemID + ")' rel='" + items.get(i).itemID + "'>Đăng</a> |  <a class='saveItem' href='javascript:uSaveItem(" + items.get(i).itemID + ")' rel='" + items.get(i).itemID + "' >Lưu</a>";
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

    public String renderIndexHtmlForUser(String html,String userID, String userName) {
        String temp = html;
        //for (int i = 0; i < temp.size(); i++) {
            if (temp.contains("{{userNameHere}}")) {
                temp = temp.replace("{{userNameHere}}", userName);
                //temp.set(i, replaceAll);
            }
            if (temp.contains("{{userIDHere}}")) {
                temp = temp.replace("{{userIDHere}}", userID);
                //temp.set(i, replaceAll);
            }
        //}        
        return temp;
    }
    public List<String> renderItemRandom(Item item) {

        return null;
    }
}
