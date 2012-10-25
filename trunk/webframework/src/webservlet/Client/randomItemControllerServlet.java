/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Client;

import com.google.gson.Gson;
import frontend.Item;
import frontend.MiddlewareHandler;
import frontend.MyLocalCache;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;

/**
 *
 * @author chanhlt
 */
public class randomItemControllerServlet extends HttpServlet {

    public static MiddlewareHandler handler = new MiddlewareHandler();
    public static MyLocalCache myLocalCache=new MyLocalCache();
    int count = 0;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {        
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        //long t1 = System.currentTimeMillis();
        res.setContentType("text/html; charset=UTF-8");
        Item item = null;
        if (req.getParameter("tagID") == null) {
            return;
        } else {
            String tagID = req.getParameter("tagID").toString();
            try {
                item=myLocalCache.getRandomItem();
                if(item==null) {
                    item = handler.getRandomItemhaveTag(tagID);
                }
            } catch (TException ex) {
                Logger.getLogger(randomItemControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
        //long t2 = System.currentTimeMillis();
        Gson gson = new Gson();
        String strItem = gson.toJson(item);
        res.getWriter().println(strItem);
    }
}
