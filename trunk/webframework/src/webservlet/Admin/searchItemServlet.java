/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import com.google.gson.Gson;
import com.vng.jcore.common.Config;
import frontend.Item;
import frontend.MiddlewareHandler;
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class searchItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html; charset=utf-8");
        try {
            String content = renderStatus(req);
            res.getWriter().println(content);
        } catch (Exception ex) {
            Logger.getLogger(searchItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doGet(req, res);
    }

    private Template getItemTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("admin/index/paging_item_1.xtm");
        return template;
    }

    private String renderStatus(HttpServletRequest req) throws Exception {
        int page = 1;
        int itemPerPage = Integer.valueOf(Config.getParam("paging", "itemPerPage"));
        List<Item> listItem = new ArrayList<Item>();
        String keyword = "";
        String tagID = "-1";
        String type = "keyword";
        try {
            if (req.getParameter("p") != null) {
                page = Integer.parseInt(req.getParameter("p"));
            }
            if (req.getParameter("key") != null) {
                keyword = req.getParameter("key");
            }
            if (req.getParameter("tagID") != null) {
                tagID = req.getParameter("tagID");
            }
            if (req.getParameter("type") != null) {
                type = req.getParameter("type");
            }
            MiddlewareHandler handler = new MiddlewareHandler();
            if ("keyword".equals(type)) {
                if ("-1".equals(tagID)) {
                    listItem = handler.getItemsPageKeyword(keyword, page, itemPerPage);
                } else {
                    listItem = handler.getItemsPageKeywordOfTag(keyword, tagID, page, itemPerPage);
                }
            } else {
                Item item = handler.getItemFromItemID(keyword);
                if(!"-1".equals(item.itemID)) {
                    listItem.add(item);
                }
            }
            if (listItem.isEmpty()) {
                return "No result ...";
            }
        } catch (Exception ex) {
            return "Error: " + ex.getMessage();
        }
        Gson gson = new Gson();

        TemplateDataDictionary dic = TemplateDictionary.create();
        for (int i = 0; i < listItem.size(); i++) {
            TemplateDataDictionary listsection = dic.addSection("list_section");
            listsection.setVariable("itemContent", listItem.get(i).content);
            listsection.setVariable("itemID", listItem.get(i).itemID);
            listsection.setVariable("tagIDs", gson.toJson(listItem.get(i).tagsID));
        }

        int back = Math.max(1, page - 1);

        dic.setVariable("back", String.valueOf(back));
        dic.setVariable("next", String.valueOf(page + 1));
        dic.setVariable("page", String.valueOf(page));

        Template template = this.getItemTemplate();
        String content = template.renderToString(dic);
        return content;
    }
}
