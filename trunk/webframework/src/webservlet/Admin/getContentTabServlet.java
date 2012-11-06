/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import com.google.gson.Gson;
import com.vng.jcore.common.Config;
import com.vng.jcore.profiler.ProfilerLog;
import frontend.Item;
import frontend.MiddlewareHandler;
import frontend.Tag;
import frontend.User;
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class getContentTabServlet extends HttpServlet {

    //private static final Logger log = LoggerFactory.getLogger(IndexServlet.class);
    MiddlewareHandler handler = new MiddlewareHandler();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        boolean dbg = ("on".equals(req.getParameter("jdbg")));
        ProfilerLog profiler = new ProfilerLog(dbg);
        req.setAttribute("profiler", profiler);
        profiler.doStartLog("wholereq");
        try {
            String content = this.render(req);
            this.out(content, resp);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(getContentTabServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=utf-8");
        boolean dbg = ("on".equals(req.getParameter("jdbg")));
        ProfilerLog profiler = new ProfilerLog(dbg);
        req.setAttribute("profiler", profiler);
        profiler.doStartLog("wholereq");
        try {
            String content = this.render(req);
            this.out(content, res);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(getContentTabServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String render(HttpServletRequest req) throws Exception {
        String tabName = null;
        try {
            if (req.getParameter("tabName") != null) {
                tabName = req.getParameter("tabName");
            }
        } catch (Exception ex) {
            return "Error: " + ex.getMessage();
        }
        if (tabName.compareTo("Status") == 0) {
            return renderStatus(req);
        }
        if (tabName.compareTo("Tags") == 0) {
            return renderTags(req);
        }
        if (tabName.compareTo("Users") == 0) {
            return renderUsers(req);
        }
        if (tabName.compareTo("Cache") == 0) {
            return renderCache(req);
        }
        if (tabName.compareTo("Settings") == 0) {
            return renderSetting(req);
        }
        return null;
    }

    private void out(String content, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html");
        resp.setContentType("text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(content);
    }

    private String renderStatus(HttpServletRequest req) throws Exception {
        int page = 1;
        String tagID = "-1";
        int itemPerPage = Integer.valueOf(Config.getParam("paging", "itemPerPage"));

        try {
            if (req.getParameter("p") != null) {
                page = Integer.parseInt(req.getParameter("p"));
            }
            if (req.getParameter("t") != null) {
                tagID = req.getParameter("t");
            }

        } catch (Exception ex) {
            return "Error: " + ex.getMessage();
        }
        List<Item> listItem = handler.getItemsPage(page, itemPerPage, tagID);
        Gson gson = new Gson();

        TemplateDataDictionary dic = TemplateDictionary.create();
        for (int i = 0; i < listItem.size(); i++) {
            TemplateDataDictionary listsection = dic.addSection("list_section");
            listsection.setVariable("itemContent", listItem.get(i).content);
            listsection.setVariable("itemID", listItem.get(i).itemID);
            listsection.setVariable("tagIDs", gson.toJson(listItem.get(i).tagsID));
        }

        int itemTagSize;
        if ("-1".equals(tagID)) {
            itemTagSize = (int) handler.itemdbSize();
        } else {
            itemTagSize = (int) handler.itemtagSize(tagID);
        }
        int pageCount = (int) Math.ceil((float) itemTagSize / itemPerPage);

        int start = Math.max(page - 4, 1);
        int end = Math.min(page + 4, pageCount);

        for (int i = start; i <= end; i++) {
            if (i == page) {
                TemplateDataDictionary listPageSection = dic.addSection("listPage_section");
                listPageSection.setVariable("page", "<a href=\"#\" class=\"graybutton pagelink active\" rel=\"" + page + "\">" + page + "</a>");
            } else {
                TemplateDataDictionary listPageSection = dic.addSection("listPage_section");
                listPageSection.setVariable("page", "<a href=\"javascript:paging(" + i + ");\" class=\"graybutton pagelink\" rel=\"" + i + "\">" + i + "</a>");
            }
        }
        dic.setVariable("last", String.valueOf(pageCount));

        Template template = this.getItemTemplate();
        String content = template.renderToString(dic);
        return content;
    }

    private String renderTags(HttpServletRequest req) throws TException, Exception {
        int page = 1;
        int itemPerPage = Integer.valueOf(Config.getParam("paging", "itemPerPage"));
        if (req.getParameter("p") != null) {
            page = Integer.parseInt(req.getParameter("p"));
        }

        List<Tag> listTags = handler.getAllTag();
        int size = listTags.size();

        //Tags[] tagArray = null;
        List<Tags> lTags = new ArrayList<Tags>();

        for (int i = 0; i < size; i++) {
            lTags.add(new Tags(listTags.get(i).tagID, listTags.get(i).tagName));
            //tagArray[i] = new Tags(listTags.get(i).tagID, listTags.get(i).tagName);
        }
        //tagArray=(Tags[]) lTags.toArray();
        Collections.sort(lTags);


        //System.out.println(lTags);

        TemplateDataDictionary dic = TemplateDictionary.create();
        for (int i = (page - 1) * itemPerPage; i < page * itemPerPage && i < size; i++) {
            TemplateDataDictionary listTagSection = dic.addSection("tag_section");
            listTagSection.setVariable("tagID", lTags.get(i).tagID());
            listTagSection.setVariable("tagName", lTags.get(i).tagName());
        }

        int pageCount = (int) Math.ceil((float) size / itemPerPage);

        int start = Math.max(page - 4, 1);
        int end = Math.min(page + 4, pageCount);

        for (int i = start; i <= end; i++) {
            if (i == page) {
                TemplateDataDictionary listPageSection = dic.addSection("listPage_section");
                listPageSection.setVariable("page", "<a href=\"#\" class=\"graybutton pagelink active\" rel=\"" + page + "\">" + page + "</a>");
            } else {
                TemplateDataDictionary listPageSection = dic.addSection("listPage_section");
                listPageSection.setVariable("page", "<a href=\"javascript:paging(" + i + ");\"  class=\"graybutton pagelink\" rel=\"" + i + "\">" + i + "</a>");
            }
        }
        dic.setVariable("last", String.valueOf(pageCount));
        Template template = this.getTagTemplate();
        String content = template.renderToString(dic);
        return content;
    }

    private String renderUsers(HttpServletRequest req) throws TException, Exception {
        int page = 1;
        int itemPerPage = Integer.valueOf(Config.getParam("paging", "itemPerPage"));
        if (req.getParameter("p") != null) {
            page = Integer.parseInt(req.getParameter("p"));
        }
        List<String> listUsers = handler.getAllUser();
        int size = listUsers.size();
        if (size == 0) {
            return null;
        }

        TemplateDataDictionary dic = TemplateDictionary.create();

        for (int i = (page - 1) * itemPerPage; i < page * itemPerPage && i < size; i++) {
            User user = handler.getUser(listUsers.get(i).toString());
            TemplateDataDictionary listUserSection = dic.addSection("list_section");
            listUserSection.setVariable("userID", user.userID);
            listUserSection.setVariable("userToken", user.userToken);
            listUserSection.setVariable("userRole", String.valueOf(user.userRole));
            String userRoleName = null;
            if (user.userRole == 1) {
                userRoleName = "Admin";
            }
            if (user.userRole == 0) {
                userRoleName = "User";
            }
            if (user.userRole == -1) {
                userRoleName = "Blocked";
            }
            listUserSection.setVariable("userRoleName", userRoleName);
        }

        int pageCount = (int) Math.ceil((float) size / itemPerPage);

        int start = Math.max(page - 4, 1);
        int end = Math.min(page + 4, pageCount);

        for (int i = start; i <= end; i++) {
            if (i == page) {
                TemplateDataDictionary listPageSection = dic.addSection("listPage_section");
                listPageSection.setVariable("page", "<a href=\"#\" class=\"graybutton pagelink active\" rel=\"" + page + "\">" + page + "</a>");
            } else {
                TemplateDataDictionary listPageSection = dic.addSection("listPage_section");
                listPageSection.setVariable("page", "<a href=\"#\" onclick='userList(" + i + ");' class=\"graybutton pagelink\" rel=\"" + i + "\">" + i + "</a>");
            }
        }

        Template template = this.getUserTemplate();
        String content = template.renderToString(dic);
        return content;
    }

    private String renderCache(HttpServletRequest req) {
        return null;
    }

    private String renderSetting(HttpServletRequest req) {
        return null;
    }

    private Template getTagTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("admin/index/paging_tag.xtm");
        return template;
    }

    private Template getItemTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("admin/index/paging_item.xtm");
        return template;
    }

    private Template getUserTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("admin/index/paging_user.xtm");
        return template;
    }
}

class Tags implements Comparable<Tags> {

    private String tagID;
    private String tagName;

    public Tags(String tagID, String tagName) {
        if (tagID == null || tagName == null) {
            throw new NullPointerException();
        }
        this.tagID = tagID;
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tags)) {
            return false;
        }
        Tags t = (Tags) obj;

        return t.tagID.equals(this.tagID);
    }

    @Override
    public int hashCode() {
        return 31 * tagID.hashCode();
    }

    @Override
    public String toString() {
        return tagID + " " + tagName;
    }

    @Override
    public int compareTo(Tags obj) {
//        throw new UnsupportedOperationException("Not supported yet.");
        if (obj.tagID.length() < this.tagID.length()) {
            return -1;
        }
        if (obj.tagID.length() > this.tagID.length()) {
            return 1;
        }
        return this.tagID.compareTo(obj.tagID);
    }

    public String tagID() {
        return tagID;
    }

    public String tagName() {
        return tagName;
    }
}