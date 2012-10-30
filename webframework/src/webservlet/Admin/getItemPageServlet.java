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
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author root
 */
public class getItemPageServlet extends HttpServlet {

    private static MiddlewareHandler handler = handler = new MiddlewareHandler();
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(IndexControllerServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("text/html; charset=utf-8");
        boolean dbg = ("on".equals(req.getParameter("jdbg")));
        ProfilerLog profiler = new ProfilerLog(dbg);
        req.setAttribute("profiler", profiler);
        profiler.doStartLog("wholereq");

        try {
            String content = this.render(req);

            this.out(content, res);
        } catch (Exception ex) {

            log.error(ex.getMessage());
            this.out("Error exception: " + ex.getMessage(), res);
        }

        profiler.doEndLog("wholereq");
        if (dbg) {
            String tmp = profiler.dumpLogHtml();
            this.out(tmp, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doGet(req, res);
    }

    private List<Item> getItemPage(String tagID, int pageNumber, int itemPerPage) throws TException {
        List<Item> result = handler.getItemsPage(pageNumber, itemPerPage, tagID);
        return result;
    }

    private Template getCTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("admin/index/paging_item.xtm");
        return template;
    }

    private void out(String content, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html");
        PrintWriter out = resp.getWriter();
        out.println(content);

    }

    private String render(HttpServletRequest req) throws Exception {
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

        List<Item> listItem = this.getItemPage(tagID, page, itemPerPage);
        int size = listItem.size();



        TemplateDataDictionary dic = TemplateDictionary.create();
        Gson gson = new Gson();

        for (int i = 0; i < listItem.size(); i++) {
            TemplateDataDictionary listsection = dic.addSection("list_section");
            listsection.setVariable("itemContent", listItem.get(i).content);
            listsection.setVariable("itemID", listItem.get(i).itemID);
            listsection.setVariable("tagIDs", gson.toJson(listItem.get(i).tagsID));
        }


        int itemTagSize;
        if (tagID.equals("-1")) {
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

        Template template = this.getCTemplate();
        String content = template.renderToString(dic);

        return content;

    }
}
