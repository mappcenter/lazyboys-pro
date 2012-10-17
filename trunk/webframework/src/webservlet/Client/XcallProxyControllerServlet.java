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
import java.util.ArrayList;
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
import testFile.FileIO;
import zme.api.exception.ZingMeApiException;
import zme.api.feed.ZME_Feed;
import zme.api.feed.ZME_FeedItem;
import zme.api.graph.ZME_Me;
import zme.api.oauth.ZME_AccessTokenData;
import zme.api.oauth.ZME_Authentication;

public class XcallProxyControllerServlet extends HttpServlet {

    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ClientProtocolException {

        resp.setContentType("text/html; charset=utf-8");  
        try {
            String content = this.render(req,resp);
            this.out(content, resp);
        } catch (Exception ex) {            
            this.out("Error exception: " + ex.getMessage(), resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                
    }
    private String render(HttpServletRequest req,HttpServletResponse res) throws Exception {

        TemplateDataDictionary dic = TemplateDictionary.create();             
        Template template = this.getCTemplate();
        FileIO file =new FileIO();
        ArrayList aFile=file.loadFile("xcall.proxy-1.00.html");
        String strFile="";
        for(int i=0;i<aFile.size();i++){
            strFile+="\n"+aFile.get(i);
        }
        TemplateDataDictionary sFile = dic.addSection("File");
        sFile.setVariable("contentFile", strFile);
        
        String content = template.renderToString(dic);
        return content;


    }
          
    private Template getCTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("client/index/xcall.xtm");
        return template;
    }

    private void out(String content, HttpServletResponse resp) throws IOException {        
        PrintWriter out = resp.getWriter();
        out.println(content);

    }
}
