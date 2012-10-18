package webservlet.Client;

import appinfo.MyAppInfo;
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
import webservlet.Admin.randomItemServlet;
import zme.api.exception.ZingMeApiException;
import zme.api.graph.ZME_Me;
import zme.api.oauth.ZME_AccessTokenData;
import zme.api.oauth.ZME_Authentication;

public class IndexControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(IndexControllerServlet.class);
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

        resp.setContentType("text/html; charset=utf-8");
        boolean dbg = ("on".equals(req.getParameter("jdbg")));
        ProfilerLog profiler = new ProfilerLog(dbg);
        req.setAttribute("profiler", profiler);
        profiler.doStartLog("wholereq");

        try {
            String content = this.render(req,resp);
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
        Item item = null;
        if (req.getParameter("tagID") == null) {
            System.out.println("Param Null");
            try {
                item = handler.getRandomItem();
            } catch (TException ex) {
                java.util.logging.Logger.getLogger(randomItemServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String tagID = req.getParameter("tagID").toString();
            System.out.println(tagID);
            try {
                item = handler.getRandomItemhaveTag(tagID);
            } catch (TException ex) {
                java.util.logging.Logger.getLogger(randomItemServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Gson gson = new Gson();
        String strItem = gson.toJson(item);
        resp.getWriter().println(strItem);
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
        
        
        itemRan.setVariable("userID", me.get("id").toString());
        itemRan.setVariable("userName", me.get("displayname").toString());
        
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
        handler = new MiddlewareHandler();

        List<Item> listItem = handler.getAllItems(20);        
        //List<Item> listItem = handler.getTopItems(20);        

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