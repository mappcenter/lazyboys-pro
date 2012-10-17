package webservlet.Client;

import MiddlewareFrontend.Item;
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

public class TestFeedControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(TestFeedControllerServlet.class);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ClientProtocolException {
        

    }

    private String render(HttpServletRequest req,HttpServletResponse res) throws Exception {
        



        TemplateDataDictionary dic = TemplateDictionary.create();
        
        
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
        Template template = templateLoader.getTemplate("client/index/testfeed.xtm");
        return template;
    }

    private void out(String content, HttpServletResponse resp) throws IOException {

        resp.addHeader("Content-Type", "text/html");
        PrintWriter out = resp.getWriter();
        out.println(content);

    }
}
