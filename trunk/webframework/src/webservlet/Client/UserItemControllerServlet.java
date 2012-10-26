package webservlet.Client;

import com.google.gson.Gson;
import com.vng.jcore.profiler.ProfilerLog;
import frontend.Item;
import frontend.MiddlewareHandler;
import frontend.UserInfo;
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserItemControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(UserItemControllerServlet.class);
    public static MiddlewareHandler handler = new MiddlewareHandler();

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
        resp.setContentType("text/html; charset=UTF-8");
        doGet(req, resp);
    }

    private String render(HttpServletRequest req) throws Exception {
        TemplateDataDictionary dic = TemplateDictionary.create();

        String userID = "";
        if (req.getParameter("userID") != null) {
            userID = req.getParameter("userID");
        }

        List<Item> uItems = null;
        try {
            //UserInfo usr=new UserInfo();
            //uItems=usr.getUserFavoriteItems(userID);//get from memcache
            //if(uItems==null) {
            //uItems=handler.getFavouriteItems(userID, 20);
            uItems = MiddlewareHandler.myLocalCache.getUserItemsLike(userID);
            // }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserItemControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (uItems != null) {
            Gson gson = new Gson();

            for (int i = 0; i < uItems.size(); i++) {
                TemplateDataDictionary list_uItem = dic.addSection("list_uItem");
                list_uItem.setVariable("itemID", uItems.get(i).itemID);
                list_uItem.setVariable("itemContent", uItems.get(i).content);
                list_uItem.setVariable("lisTagIDs", gson.toJson(uItems.get(i).tagsID));
            }
        }

        Template template = this.getCTemplate();
        String content = template.renderToString(dic);

        return content;


    }

    private Template getCTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("client/index/uItems.xtm");
        return template;
    }

    private void out(String content, HttpServletResponse resp) throws IOException {

        resp.addHeader("Content-Type", "text/html");
        PrintWriter out = resp.getWriter();
        out.println(content);

    }
}
