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
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webservlet.Admin.indexServerlet;
import zme.api.exception.ZingMeApiException;
import zme.api.graph.ZME_Me;
import zme.api.oauth.ZME_AccessTokenData;
import zme.api.oauth.ZME_Authentication;

public class IndexControllerServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(IndexControllerServlet.class);
    public static MiddlewareHandler handler = new MiddlewareHandler();
    public ZME_Authentication zme;
    //public ZME_GraphAPI zApi;
    public String url = null;
    public String authorCode = null;
    public String token = null;
    public ZME_AccessTokenData zdata;
    public String test = "hello thiensuhack";
    private ZME_Me zm;
    HashMap<String, Object> me = null;
    String sigKey = null;

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
        System.out.println("Render page Time 1:" + (end - start) + " ms");

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

        MyLocalCache mycache = new MyLocalCache();
        long zingStart = System.currentTimeMillis();
        zme = new ZME_Authentication(MyAppInfo.getInstance().getzConfig());
        url = zme.getAuthorizedUrl(MyAppInfo.getInstance().getUrlToRedirect(), url);

        if (req.getParameter("code") == null || req.getParameter("code").isEmpty()) {
            res.sendRedirect(url);
        } else {
            try {
                authorCode = req.getParameter("code");
                zdata = zme.getAccessTokenFromCode(URLDecoder.decode(authorCode, "UTF-8"));
                int a = 0;
            } catch (ZingMeApiException ex) {
                java.util.logging.Logger.getLogger(indexServerlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            zm = new ZME_Me(MyAppInfo.getInstance().getzConfig());

            try {
                me = zm.getInfo(zdata.accessToken, "displayname");
            } catch (ZingMeApiException ex) {
                java.util.logging.Logger.getLogger(indexServerlet.class.getName()).log(Level.SEVERE, null, ex);
                res.sendRedirect("/blockUser");
            }
        }
        if (!handler.userExisted(me.get("id").toString())) {
            boolean temp = handler.addUser(me.get("id").toString(), "default", 0);// normal user:0, admin:1, blockuser:-1            
        } else {
            if (mycache.isBlockUser(me.get("id").toString())) {
                res.sendRedirect("/blockUser");
            }
        }
        long zingEnd = System.currentTimeMillis();
        System.out.println("Zing time:" + (zingEnd - zingStart) + " ms");
        //MiddlewareHandler.myLocalCache.CacheUserItemIDLike(me.get("id").toString());

//        List<Item> listItem = this.getTopItems(req);        
//        TemplateDataDictionary dic = TemplateDictionary.create();
//        
//       //dic.setVariable("title", "This is title of layout - config=" + config_host + " - product=" + productName);        
//        for (int i = 0; i < listItem.size(); i++) {
//            TemplateDataDictionary listsection = dic.addSection("list_section");
//            listsection.setVariable("itemID", listItem.get(i).itemID);
//            listsection.setVariable("itemContent", listItem.get(i).content);
//        }
//        TemplateDataDictionary itemRan = dic.addSection("itemRan");
//        
//        itemRan.setVariable("userID", me.get("id").toString());
//        itemRan.setVariable("userName", me.get("displayname").toString());
//
//        Template template = this.getCTemplate();
//        String content = template.renderToString(dic);
//        
//        return content;
        //uID and uName: just for test without zing redirect (zing redirect time ~ 17ms)
//        String uID="5037964";
//        String uName="thiensuhack";
//        List<String> strIndexHtml = mycache.getCacheIndexPageWithUser(uID, uName);

        TemplateDataDictionary dic = TemplateDictionary.create();

        List<String> strIndexHtml = mycache.getCacheIndexPageWithUser(me.get("id").toString(), me.get("displayname").toString());
        long start = System.currentTimeMillis();
        if (strIndexHtml != null && strIndexHtml.size() > 0) {
            for (int i = 0; i < strIndexHtml.size(); i++) {
                TemplateDataDictionary listsection = dic.addSection("list_content");
                listsection.setVariable("contentHtml", strIndexHtml.get(i));
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("repair before render:" + (end - start) + " ms");
        Template template = this.getCTemplate();
        String content = template.renderToString(dic);
        return content;


    }

    private List<Item> getTopItems(HttpServletRequest req) throws TException {
        ProfilerLog profiler = (ProfilerLog) req.getAttribute("profiler");
        if (profiler != null) {
            profiler.doStartLog("fresherthriftservice");
        }
        handler = new MiddlewareHandler();
        List<Item> listItem = MiddlewareHandler.myLocalCache.getTopItems();
        if (profiler != null) {
            profiler.doEndLog("fresherthriftservice");
        }
        return listItem;
    }

    private Template getCTemplate() throws Exception {
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