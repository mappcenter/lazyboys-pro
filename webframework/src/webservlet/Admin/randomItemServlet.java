/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import frontend.Item;
import frontend.MiddlewareHandler;
import org.apache.thrift.TException;

/**
 *
 * @author chanhlt
 */
public class randomItemServlet extends HttpServlet {

    public static MiddlewareHandler handler = new MiddlewareHandler();    
    int count = 0;
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        long t1 = System.currentTimeMillis();
        res.setContentType("text/html; charset=UTF-8");
        Item item = null;
        try {
            item = handler.getRandomItem();
            int end = (int) System.currentTimeMillis();
        } catch (TException ex) {
            Logger.getLogger(randomItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        long t2 = System.currentTimeMillis();
        //Gson gson = new Gson();
        //String strItem = gson.toJson(item);
        res.getWriter().println("<html><body><center><h1>Welcome to LazyBoys!</h1></center><br/>Time: " + (t2 - t1) + " ms<br/>" + item.toString() + "</body></html>");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        long t1 = System.currentTimeMillis();
        res.setContentType("text/html; charset=UTF-8");
        Item item = null;
        if (req.getParameter("tagID") == null) {
            System.out.println("Param Null");
            try {
                item = handler.getRandomItem();
            } catch (TException ex) {
                Logger.getLogger(randomItemServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String tagID = req.getParameter("tagID").toString();
            System.out.println(tagID);
            try {
                item = handler.getRandomItemhaveTag(tagID);
            } catch (TException ex) {
                Logger.getLogger(randomItemServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        long t2 = System.currentTimeMillis();
        //Gson gson = new Gson();
        //String strItem = gson.toJson(item);
        res.getWriter().println("<html><body><center><h1>Welcome to LazyBoys!</h1></center><br/>Time: " + (t2 - t1) + " ms<br/>" + item.content + "</body></html>");
    }
}
