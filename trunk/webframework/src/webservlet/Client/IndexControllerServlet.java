package webservlet.Client;

import com.google.gson.Gson;
import com.vng.jcore.profiler.ProfilerLog;
import frontend.Item;
import frontend.MiddlewareHandler;
import frontend.MyAppInfo;
import frontend.MyLocalCache;
import frontend.getConfig;
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.ClientProtocolException;
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
    public static ZME_Authentication zmAuthen;
    public ZME_AccessTokenData zdata;
    private static ZME_Me zmMe;
    MyLocalCache mycache = new MyLocalCache();
    private static int expriedTimeCookie=getConfig.getInstance().getExpriedTimeCookie(); //seconds
    public IndexControllerServlet() throws FileNotFoundException, IOException {
        zmMe = new ZME_Me(MyAppInfo.getInstance().getzConfig());
        zmAuthen = new ZME_Authentication(MyAppInfo.getInstance().getzConfig());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ClientProtocolException {
        resp.setContentType("text/html; charset=utf-8");
        boolean dbg = ("on".equals(req.getParameter("jdbg")));
        ProfilerLog profiler = new ProfilerLog(dbg);
        req.setAttribute("profiler", profiler);
        profiler.doStartLog("wholereq");
        try {

            String content = this.render(req, resp, profiler);
            profiler.doStartLog("renderPage");
            this.out(content, resp);
            profiler.doEndLog("renderPage");

        } catch (Exception ex) {
            log.error(ex.getMessage());
            this.out("Error exception: " + ex.getMessage(), resp);
        }
        profiler.doEndLog("wholereq");
        String tmp = profiler.dumpLogHtml();
        if (dbg) {
            tmp = profiler.dumpLogHtml();
            System.out.print(tmp);
            this.out(tmp, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        Gson gson = new Gson();
        String strItem = gson.toJson(item);
        resp.getWriter().println(strItem);
    }

    private String render(HttpServletRequest req, HttpServletResponse res, ProfilerLog profiler) throws Exception {
        req.setAttribute("profiler", profiler);
        HashMap<String, Object> me = null;
        
        
        Cookie myNameCookie = null;
        Cookie myIDCookie = null;
        Cookie[] cookies = (Cookie[]) req.getCookies();
        boolean newCookie = false;
        
        profiler.doStartLog("checkExistCookies");
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("lazyboysNameCookie")) {
                    myNameCookie = cookies[i];
                }
                if (cookies[i].getName().equals("lazyboysIDCookie")) {
                    myIDCookie = cookies[i];
                }
            }
        }
        profiler.doEndLog("checkExistCookies");
        
        if (myNameCookie == null || myIDCookie == null) {
            profiler.doStartLog("connect_zingMe");
            String accesstoken;
            if (req.getParameter("signed_request") == null || req.getParameter("signed_request").isEmpty()) {
                return "<html><head></head><body><div><h1 style='font-size: 25px;text-align: center;'>Vui lòng đăng nhập vào <a href='http://me.zing.vn' target='_blank'>zingMe</a> trước khi truy cập vào ứng dụng này!<br/>Cảm ơn!<img src='http://static.me.zing.vn/v3/images/smilley/default/56.jpg' /><h1><div></body></html>";
            }
            String signed_request = req.getParameter("signed_request");
            accesstoken = zmAuthen.getAccessTokenFromSignedRequest(signed_request);
            try {
                me = zmMe.getInfo(accesstoken, "displayname");
            } catch (ZingMeApiException ex) {
                java.util.logging.Logger.getLogger(indexServerlet.class.getName()).log(Level.SEVERE, null, ex);
                res.sendRedirect("/blockUser");
            }
            profiler.doEndLog("connect_zingMe");

            profiler.doStartLog("Check&Save_User");
            if (!handler.userExisted(me.get("id").toString())) {
                boolean temp = handler.addUser(me.get("id").toString(), "default ;))", 0);// normal user:0, admin:1, blockuser:-1            
            } else {
                if (mycache.isBlockUser(me.get("id").toString())) {
                    res.sendRedirect("/blockUser");
                }
            }
            profiler.doEndLog("Check&Save_User");
            
            profiler.doStartLog("createUserCookies");
            String myName=URLEncoder.encode(me.get("displayname").toString(), "UTF-8");
            String myID=me.get("id").toString();
            myNameCookie = new Cookie("lazyboysNameCookie", myName);
            myNameCookie.setPath(req.getContextPath());
            myNameCookie.setMaxAge(expriedTimeCookie);            
            myIDCookie = new Cookie("lazyboysIDCookie", myID);
            myIDCookie.setPath(req.getContextPath());
            myIDCookie.setMaxAge(expriedTimeCookie);
            res.addCookie(myNameCookie);
            res.addCookie(myIDCookie);
            
            profiler.doEndLog("createUserCookies");
        }

        profiler.doStartLog("getCacheIndex4User");
        TemplateDataDictionary dic = TemplateDictionary.create();
        String strIndexHtml = mycache.getCacheIndexPageWithUser(myIDCookie.getValue(), URLDecoder.decode(myNameCookie.getValue(),"UTF-8"));

        if (strIndexHtml != null && !strIndexHtml.isEmpty()) {
            TemplateDataDictionary listsection = dic.addSection("list_content");
            listsection.setVariable("contentHtml", strIndexHtml);
        }
        profiler.doStartLog("getCacheIndex4User");

        profiler.doStartLog("getTemplate");
        Template template = IndexControllerServlet.getCTemplate();
        String content = template.renderToString(dic);
        profiler.doEndLog("getTemplate");
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
