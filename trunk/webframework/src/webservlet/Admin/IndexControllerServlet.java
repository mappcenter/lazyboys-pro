package webservlet.Admin;

import com.google.gson.Gson;
import com.vng.jcore.common.Config;
import com.vng.jcore.profiler.ProfilerLog;
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import frontend.Item;
import frontend.MiddlewareHandler;
import frontend.Tag;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(IndexControllerServlet.class);
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

            log.error(ex.getMessage());
            this.out("Error exception: " + ex.getMessage(), resp);
        }

        profiler.doEndLog("wholereq");
        if (dbg) {
            String tmp = profiler.dumpLogHtml();
            this.out(tmp, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
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

        List<Item> listItem = this.testPaging(page, itemPerPage, tagID);
        int size = listItem.size();



        TemplateDataDictionary dic = TemplateDictionary.create();


        dic.setVariable("tag", tagID);

        dic.setVariable("title", "This is title of layout");
        if (page > 1) {
            dic.setVariable("preview", String.valueOf(page - 1));
        } else {
            dic.setVariable("preview", "1");
        }


        if (size < 10) {
            dic.setVariable("next", String.valueOf(page));
        } else {
            dic.setVariable("next", String.valueOf(page + 1));
        }

        dic.setVariable("itemPerPage", String.valueOf(itemPerPage));
        dic.setVariable("current", String.valueOf(page));

        Gson gson = new Gson();

        for (int i = 0; i < listItem.size(); i++) {
            TemplateDataDictionary listsection = dic.addSection("list_section");
            listsection.setVariable("itemContent", listItem.get(i).content);
            listsection.setVariable("itemID", listItem.get(i).itemID);
            listsection.setVariable("tagIDs", gson.toJson(listItem.get(i).tagsID));
        }

        List<Tag> listTag = getAllTag();

        for (int i = 0; i < listTag.size(); i++) {
            TemplateDataDictionary listTagSection = dic.addSection("listTag_section");
            listTagSection.setVariable("tagID", listTag.get(i).tagID);
            listTagSection.setVariable("tagName", listTag.get(i).tagName);
            
        }

        int itemTagSize = (int) handler.itemdbSize();
        int pageCount = (int) Math.ceil((float) itemTagSize / itemPerPage);

        int start = Math.max(page - 4, 1);
        int end = Math.min(page + 4, pageCount);

        for (int i = start; i <= end; i++) {
            if (i == page) {
                TemplateDataDictionary listPageSection = dic.addSection("listPage_section");
                listPageSection.setVariable("page", "<a href=\"#\" class=\"graybutton pagelink active\" rel=\"" + page + "\">" + page + "</a>");
            } else {
                TemplateDataDictionary listPageSection = dic.addSection("listPage_section");
                listPageSection.setVariable("page", "<a href=\"javascript:paging("+i+");\"  class=\"graybutton pagelink\" rel=\"" + i + "\">" + i + "</a>");
            }
        }
       
        dic.setVariable("last", String.valueOf(pageCount));

        Template template = this.getCTemplate();
        String content = template.renderToString(dic);

        return content;


    }

    private List<Item> testPaging(int pageNumber, int itemNumber, String tagID) throws TException {
        List<Item> result = handler.getItemsPage(pageNumber, itemNumber, tagID);
        return result;
    }

    private List<Tag> getAllTag() throws TException {
        return handler.getAllTag();
    }

    private Template getCTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("admin/index/index_admin.xtm");
        return template;
    }

    private void out(String content, HttpServletResponse resp) throws IOException {

        resp.addHeader("Content-Type", "text/html");
        PrintWriter out = resp.getWriter();
        out.println(content);

    }
}
