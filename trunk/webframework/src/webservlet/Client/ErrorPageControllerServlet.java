package webservlet.Client;

import com.google.gson.Gson;
import com.vng.jcore.common.Config;
import com.vng.jcore.profiler.ProfilerLog;
import frontend.Item;
import frontend.MiddlewareHandler;
import frontend.MyAppInfo;
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

public class ErrorPageControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(ErrorPageControllerServlet.class);
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
        try {
            String content = this.render(req, resp);
            this.out(content, resp);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            this.out("Error exception: " + ex.getMessage(), resp);
        }
    }
    @Override
    protected synchronized void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    private String render(HttpServletRequest req, HttpServletResponse res) throws Exception {
        TemplateDataDictionary dic = TemplateDictionary.create();
        Template template = this.getCTemplate();
        String content = template.renderToString(dic);
        return content;


    }
    private Template getCTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("client/index/error.xtm");
        return template;
    }

    private void out(String content, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html");
        PrintWriter out = resp.getWriter();
        out.print(content);

    }
}