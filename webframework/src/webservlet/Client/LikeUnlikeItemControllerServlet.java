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

public class LikeUnlikeItemControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(LikeUnlikeItemControllerServlet.class);
    public static MiddlewareHandler handler = new MiddlewareHandler();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        if(req.getParameter("typeAction")==null){
            return;
        }
        String result="0";
        String action=req.getParameter("typeAction");
        if (req.getParameter("userID") == null || req.getParameter("userID").toString().isEmpty()) {
        } 
        else {
            String itemID=req.getParameter("itemID");
            String userID=req.getParameter("userID");
            if (action.compareTo("like") == 0) {
                try {
                    if(handler.insertLikedItem(userID, itemID)){
                        result="1";
                    }
                } catch (TException ex) {
                    java.util.logging.Logger.getLogger(LikeUnlikeItemControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        resp.getWriter().println(result);
    }
}
