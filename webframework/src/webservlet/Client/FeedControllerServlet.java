package webservlet.Client;

import frontend.Item;
import appinfo.MyAppInfo;
import com.google.gson.Gson;
import com.vng.jcore.common.Config;
import com.vng.jcore.profiler.ProfilerLog;
import frontend.MiddlewareHandler;
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
import servlet.indexServerlet;
import servlet.randomItemServlet;
import zme.api.exception.ZingMeApiException;
import zme.api.feed.ZME_Feed;
import zme.api.feed.ZME_FeedItem;
import zme.api.graph.ZME_Me;
import zme.api.oauth.ZME_AccessTokenData;
import zme.api.oauth.ZME_Authentication;

public class FeedControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(FeedControllerServlet.class);
    public static MiddlewareHandler handler = new MiddlewareHandler();
    
     public ZME_Authentication zme;
    //public ZME_GraphAPI zApi;
    public String url=null;
    public String authorCode=null;
    public String token=null;
    public ZME_AccessTokenData zdata;
    public String test="hello thiensuhack";
    private ZME_Me zm;
    HashMap<String,Object> me = null;
    String sigKey=null;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ClientProtocolException {

//        resp.setContentType("text/html; charset=utf-8");
//        boolean dbg = ("on".equals(req.getParameter("jdbg")));
//        ProfilerLog profiler = new ProfilerLog(dbg);
//        req.setAttribute("profiler", profiler);
//        profiler.doStartLog("wholereq");
//
//        try {
//            String content = this.render(req,resp);
//            this.out(content, resp);
//        } catch (Exception ex) {
//
//            log.error(ex.getMessage());
//            this.out("Error exception: " + ex.getMessage(), resp);
//        }
//
//        profiler.doEndLog("wholereq");
//        if (dbg) {
//            String tmp = profiler.dumpLogHtml();
//            this.out(tmp, resp);
//        }
        
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ClientProtocolException {
        resp.setContentType("text/html; charset=utf-8");
        String text=req.getParameter("itemContent");
        String uIdTo=req.getParameter("userIdTo");
        int userTo=Integer.valueOf(uIdTo);
        ZME_Feed zfeed=new ZME_Feed(MyAppInfo.getInstance().getzConfig());
        ZME_FeedItem itemfeed=new ZME_FeedItem(
                userTo, 
                userTo, 
                1, 
                (short)3, 
                "", 
                "", 
                "", 
                "", 
                text, 
                (short)1, 
                "http://d.f12.photo.zdn.vn/upload/original/2012/10/11/13/34/1349937282850465_574_574.jpg", 
                "", 
                "", 
                ""
                );
        String signKey="";
        try {
            signKey=zfeed.generateSignatureKey(itemfeed);
        } catch (ZingMeApiException ex) {
            java.util.logging.Logger.getLogger(FeedControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }        
        resp.getWriter().print(signKey);
    }

    private String render(HttpServletRequest req,HttpServletResponse res) throws Exception {
        
        List<Item> listItem = this.testCallThriftService(req);
        String config_host = this.testGetConfig(req);
        String productName = listItem.get(1).itemID;


        TemplateDataDictionary dic = TemplateDictionary.create();

        dic.setVariable("title", "This is title of layout - config=" + config_host + " - product=" + productName);
        

        for (int i = 0; i < listItem.size(); i++) {
            TemplateDataDictionary listsection = dic.addSection("list_section");
            listsection.setVariable("itemContent", listItem.get(i).content);
        }
        
         Item item = null;
        
        try {
            item = handler.getRandomItem();
        } catch (TException ex) {
            java.util.logging.Logger.getLogger(randomItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson=new Gson();
        String strItem=gson.toJson(item);
        
        TemplateDataDictionary itemRan= dic.addSection("itemRan");
        itemRan.setVariable("strItem", strItem);
        
        zme=new ZME_Authentication(MyAppInfo.getInstance().getzConfig());
        url= zme.getAuthorizedUrl(MyAppInfo.getInstance().getUrlToRedirect(), url);
        
        if(req.getParameter("code")==null || req.getParameter("code").isEmpty())
        {
            res.sendRedirect(url);
        }
        else{
            try {
                authorCode=req.getParameter("code");
                zdata=zme.getAccessTokenFromCode(URLDecoder.decode(authorCode,"UTF-8"));
                //String sigRequest=req.getParameter("signed_request");
                //zATData=zme.getAccessTokenForAppAuthentication();
                int a=0;
            } catch (ZingMeApiException ex) {
                java.util.logging.Logger.getLogger(indexServerlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            zm=new ZME_Me(MyAppInfo.getInstance().getzConfig());
            
            try {               
                me = zm.getInfo(zdata.accessToken,"displayname");
            } catch (ZingMeApiException ex) {
                java.util.logging.Logger.getLogger(indexServerlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
////        try {
////            zdata =zme.getAccessTokenForAppAuthentication();
////        } catch (ZingMeApiException ex) {
////            java.util.logging.Logger.getLogger(IndexControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
////        }
////
////        zm=new ZME_Me(MyAppInfo.getInstance().getzConfig());
////
////        try {               
////            me = zm.getInfo(zdata.accessToken,"displayname");
////        } catch (ZingMeApiException ex) {
////            java.util.logging.Logger.getLogger(indexServerlet.class.getName()).log(Level.SEVERE, null, ex);
////        }
        
        ZME_Feed feed=new ZME_Feed(MyAppInfo.getInstance().getzConfig());
        //ZME_FeedItem feedItem=new ZME_FeedItem((int)me.get("id"), (int)me.get("id"), 1, 3, "", "attach name","http://attachHref.com", "caption vnexpress.net", "description", 1, "mediaImage", "mediaSource", "actionLinkText", "actionLinkH");
        int id=Integer.valueOf(me.get("id").toString());
        
        ZME_FeedItem itemFeed=new ZME_FeedItem(id,
                id,
                1,
                (short)3,
                "",
                "Ca sĩ Quang Hà chuẩn bị kiện nhân chứng Vietnam Airlines",
                "http://vnexpress.net/gl/kinh-doanh/2011/05/ca-si-quang-ha-chuan-bi-kien-nhan-chung-vietnam-airlines/",
                "vnexpress.net",
                "Nhận được tin nhắn xin lỗi được cho là của bà Eileen Tan - nhân chứng trong vụ HLV Taekwondo, nhưng ca sĩ Quang Hà vẫn quyết đưa việc này ra tòa.",
                (short)1,
                "http://vnexpress.net/Files/Subject/3b/a2/99/39/quang-ha-250.jpg.thumb150x0.ns.jpg",
                "http://vnexpress.net/gl/kinh-doanh/2011/05/ca-si-quang-ha-chuan-bi-kien-nhan-chung-vietnam-airlines/",
                "Link",
                "http://me.zing.vn/apps/link"
                );
        
        try {
            sigKey=feed.generateSignatureKey(itemFeed);
        } catch (ZingMeApiException ex) {
            java.util.logging.Logger.getLogger(indexServerlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        itemRan.setVariable("signkey", sigKey);
        itemRan.setVariable("token", me.toString());
        
        Template template = this.getCTemplate();
        String content = template.renderToString(dic);

        return content;


    }
    public Item getRandomItem(){
        Item item = null;
        
        try {
            item = handler.getRandomItem();
        } catch (TException ex) {
            java.util.logging.Logger.getLogger(randomItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }
    private String testGetConfig(HttpServletRequest req) {
        String config = Config.getParam("sample", "host");
        return config;
    }

    private List<Item> testCallThriftService(HttpServletRequest req) throws TException {

        ProfilerLog profiler = (ProfilerLog) req.getAttribute("profiler");
        if (profiler != null) {
            profiler.doStartLog("fresherthriftservice");
        }
        MiddlewareHandler handler = new MiddlewareHandler();

        List<Item> listItem = handler.getAllItems(100);

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
        out.println(content);

    }
}
