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

/**
 *
 * @author root
 */
public class getTopStatusServlet extends HttpServlet {

    MiddlewareHandler handler = new MiddlewareHandler();

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
        
        int itemPerPage = Integer.valueOf(Config.getParam("paging", "itemPerPage"));

        try {
            if (req.getParameter("p") != null) {
                page = Integer.parseInt(req.getParameter("p"));
            }

        } catch (Exception ex) {
            return "Error: " + ex.getMessage();
        }

        List<Item> listItem = handler.getTopItems(300);
        listItem=listItem.subList((page-1)*itemPerPage, (page-1)*itemPerPage+itemPerPage);

        TemplateDataDictionary dic = TemplateDictionary.create();
        Gson gson = new Gson();

        for (int i = 0; i < listItem.size(); i++) {
            TemplateDataDictionary listsection = dic.addSection("list_section");
            listsection.setVariable("itemContent", listItem.get(i).content);
            listsection.setVariable("itemID", listItem.get(i).itemID);
            listsection.setVariable("tagIDs", gson.toJson(listItem.get(i).tagsID));
        }
     
        int pageCount = (int) Math.ceil((float) 300 / itemPerPage);

        int start = Math.max(page - 4, 1);
        int end = Math.min(page + 4, pageCount);

        for (int i = start; i <= end; i++) {
            if (i == page) {
                TemplateDataDictionary listPageSection = dic.addSection("listPage_section");
                listPageSection.setVariable("page", "<a href=\"#\" class=\"graybutton pagelink active\" rel=\"" + page + "\">" + page + "</a>");
            } else {
                TemplateDataDictionary listPageSection = dic.addSection("listPage_section");
                listPageSection.setVariable("page", "<a href=\"#\" onclick='topItemList(" + i + ");' class=\"graybutton pagelink\" rel=\"" + i + "\">" + i + "</a>");
            }
        }
        dic.setVariable("onclick_first", "topItemList(1);");
        dic.setVariable("onclick_last", "topItemList($(this).attr('rel'));");
        dic.setVariable("last", String.valueOf(pageCount));

        Template template = this.getCTemplate();
        String content = template.renderToString(dic);

        return content;

    }
}
