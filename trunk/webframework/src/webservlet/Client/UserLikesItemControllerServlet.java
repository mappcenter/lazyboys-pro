package webservlet.Client;

import frontend.MiddlewareHandler;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLikesItemControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(UserLikesItemControllerServlet.class);
    public static MiddlewareHandler handler = new MiddlewareHandler();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        List<String> result=null;
        if (req.getParameter("userID") == null || req.getParameter("userID").toString().isEmpty()) {
        } else {
            String userID = req.getParameter("userID");
            try {
                result=handler.getAllItemsIDLike(userID);
            } catch (TException ex) {
                java.util.logging.Logger.getLogger(UserLikesItemControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }        
        String strItemIDs="";
        for (int i = 0; i < result.size(); i++) {
            strItemIDs+=result.get(i).toString()+",";
        }
        resp.getWriter().println(strItemIDs);
    }
}
