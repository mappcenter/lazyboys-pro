package webservlet.Client;

import com.google.gson.Gson;
import com.vng.jcore.profiler.ProfilerLog;
import frontend.Item;
import frontend.MiddlewareHandler;
import frontend.MyAppInfo;
import frontend.MyLocalCache;
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webservlet.Admin.indexServerlet;
import zme.api.exception.ZingMeApiException;
import zme.api.graph.ZME_Me;
import zme.api.oauth.ZME_Authentication;

public class IndexControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(IndexControllerServlet.class);
    public static MiddlewareHandler handler = new MiddlewareHandler();
    public static ZME_Authentication zmAuthen;
    private static ZME_Me zmMe;
    HashMap<String, Object> me = null;
    MyLocalCache mycache = new MyLocalCache();

    public IndexControllerServlet() throws FileNotFoundException, IOException {
        zmMe = new ZME_Me(MyAppInfo.getInstance().getzConfig());
        zmAuthen = new ZME_Authentication(MyAppInfo.getInstance().getzConfig());
    }

    @Override
    protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ClientProtocolException {
        resp.setContentType("text/html; charset=utf-8");
        boolean dbg = ("on".equals(req.getParameter("jdbg")));
        ProfilerLog profiler = new ProfilerLog(dbg);
        req.setAttribute("profiler", profiler);
        profiler.doStartLog("wholereq");
        long start = System.currentTimeMillis();
        try {
            String content = this.render(req, resp);
            this.out(content, resp);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            this.out("Error exception: " + ex.getMessage(), resp);
        }
        long end = System.currentTimeMillis();
        long t = end - start;
        System.out.println("Render page Time:" + (end - start) + " ms");

        profiler.doEndLog("wholereq");
        String tmp = profiler.dumpLogHtml();
        if (dbg) {
            tmp = profiler.dumpLogHtml();
            System.out.print(tmp);
            this.out(tmp, resp);
        }
    }

    @Override
    protected synchronized void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        Item item = null;
        if (req.getParameter("tagID") == null) {
            item = MiddlewareHandler.myLocalCache.getFastRandom();
        } else {
            String tagID = req.getParameter("tagID").toString();
            try {
                item = MiddlewareHandler.myLocalCache.getRandomItemHaveTag(tagID);
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(randomItemControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
        //long t2 = System.currentTimeMillis();
        Gson gson = new Gson();
        String strItem = gson.toJson(item);
        resp.getWriter().println(strItem);
    }

    private String render(HttpServletRequest req, HttpServletResponse res) throws Exception {
        long zingStart = System.currentTimeMillis();        
        String accesstoken;
        String signed_request = req.getParameter("signed_request");
        accesstoken = zmAuthen.getAccessTokenFromSignedRequest(signed_request);
        String meID="5037964";
        String myName="thiensuhack";
        try {
            me = zmMe.getInfo(accesstoken, "displayname");
        } catch (ZingMeApiException ex) {
            java.util.logging.Logger.getLogger(indexServerlet.class.getName()).log(Level.SEVERE, null, ex);
            res.sendRedirect("/blockUser");
        }
         long zingEnd = System.currentTimeMillis();
        System.out.println("Zing time:" + (zingEnd - zingStart) + " ms");

        if (!handler.userExisted(me.get("id").toString())) {
            boolean temp = handler.addUser(me.get("id").toString(), accesstoken, 0);// normal user:0, admin:1, blockuser:-1            
        } else {
            if (mycache.isBlockUser(me.get("id").toString())) {
                res.sendRedirect("/blockUser");
            }
        }
        TemplateDataDictionary dic = TemplateDictionary.create();
        String strIndexHtml = mycache.getCacheIndexPageWithUser(me.get("id").toString(), me.get("displayname").toString());
        //long start = System.currentTimeMillis();
        if (strIndexHtml != null && !strIndexHtml.isEmpty()) {
           // for (int i = 0; i < strIndexHtml.size(); i++) {
                TemplateDataDictionary listsection = dic.addSection("list_content");
                listsection.setVariable("contentHtml", strIndexHtml);
           // }
        }
        // long end = System.currentTimeMillis();
        //System.out.print("repair before render:" + (end - start) + " ms");
        Template template = IndexControllerServlet.getCTemplate();
        String content = template.renderToString(dic);
        return content;
    }

    private static Template getCTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("client/index/index.xtm");
        return template;
    }

    private void out(String content, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html");
        PrintWriter out = resp.getWriter();
        out.print(content);
    }
}