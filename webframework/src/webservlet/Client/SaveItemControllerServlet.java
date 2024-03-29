package webservlet.Client;

import frontend.MiddlewareHandler;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

public class SaveItemControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(SaveItemControllerServlet.class);
    public static MiddlewareHandler handler = new MiddlewareHandler();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        String itemID="";
        String userID="";
        String result="Luu Thanh Cong";
        if(req.getParameter("itemID")!=null){
            itemID=req.getParameter("itemID");
        }
        if(req.getParameter("userID")!=null){
            userID=req.getParameter("userID");
        }
        try {            
            if(!MiddlewareHandler.myLocalCache.setUserItemsFavorite(userID, itemID))
            {
                result="Luu Khong Thanh Cong";
            }                                  
        } catch (TException ex) {
            java.util.logging.Logger.getLogger(SaveItemControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            //resp.getWriter().println(result);
        }
        resp.getWriter().println(result);
    }

    
}
