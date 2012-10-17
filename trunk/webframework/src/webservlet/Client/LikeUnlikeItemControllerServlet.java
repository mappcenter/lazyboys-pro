package webservlet.Client;

import frontend.MiddlewareHandler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        String action = req.getParameter("typeAction");
        if (req.getAttribute("userID") == null || req.getAttribute("userID").toString().isEmpty()) {
        } else {

            if (action.compareTo("like") == 0) {
                
            }
        }
    }
}
