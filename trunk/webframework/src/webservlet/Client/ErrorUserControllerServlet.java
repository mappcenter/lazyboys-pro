package webservlet.Client;

import com.google.gson.Gson;
import frontend.Item;
import frontend.MiddlewareHandler;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorUserControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(ErrorUserControllerServlet.class);
    public static MiddlewareHandler handler = new MiddlewareHandler();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {                        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");                
        List<Item> items=null;
        items=MiddlewareHandler.myLocalCache.getItemsForClientCache();
        Gson gson=new Gson();
        resp.getWriter().print(gson.toJson(items));
        
    }    
}
