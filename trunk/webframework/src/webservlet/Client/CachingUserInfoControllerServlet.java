package webservlet.Client;

import com.google.gson.Gson;
import com.vng.jcore.common.Config;
import com.vng.jcore.profiler.ProfilerLog;
import frontend.Item;
import frontend.MiddlewareHandler;
import frontend.MyLocalCache;
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
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import webservlet.Admin.randomItemServlet;

public class CachingUserInfoControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CachingUserInfoControllerServlet.class);
    public static MiddlewareHandler handler = new MiddlewareHandler();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {                        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");                               
        if(!(req.getParameter("userID")==null) && !req.getParameter("userID").isEmpty()){
            String userID=req.getParameter("userID"); 
            MyLocalCache mycache=new MyLocalCache();
            try {
                mycache.CacheUserItemsFavorite(userID);
            } catch (TException ex) {
                java.util.logging.Logger.getLogger(CachingUserInfoControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }        
        
    }    
}
