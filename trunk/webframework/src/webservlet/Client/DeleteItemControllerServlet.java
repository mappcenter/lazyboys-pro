package webservlet.Client;

import frontend.MiddlewareHandler;
import frontend.MyLocalCache;
import frontend.UserInfo;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteItemControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(SaveItemControllerServlet.class);
    public static MiddlewareHandler handler = new MiddlewareHandler();
    public static MyLocalCache myLocalCache=new MyLocalCache();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {    
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        String itemID="";
        String userID="";
        //String result="1";
        if(req.getParameter("itemID")!=null && req.getParameter("userID")!=null){
            itemID=req.getParameter("itemID");
            userID=req.getParameter("userID");
        }
        else{
            return;
        }
//        if(req.getParameter("userID")!=null){
//            userID=req.getParameter("userID");
//        }
        try {     
            myLocalCache.removeUserItemLike(userID, itemID);
            
        } catch (TException ex) {            
            java.util.logging.Logger.getLogger(SaveItemControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        return;
        //resp.getWriter().println(result);
    }
}
