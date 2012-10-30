/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import frontend.Item;
import frontend.MiddlewareHandler;
import frontend.MyLocalCache;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        MyLocalCache myLocalCache = new MyLocalCache();
        item = myLocalCache.getFastRandom();
        //item = handler.getRandomItem();          
        long t2 = System.currentTimeMillis();
        res.getWriter().println("<html><body><center><h1>Welcome to LazyBoys!</h1></center><br/>Time: " + (t2 - t1) + " ms<br/>" + item.toString() + "</body></html>");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        doGet(req, res);
    }
}
