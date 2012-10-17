/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import frontend.MiddlewareHandler;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import MiddlewareFrontend.Item;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class listAllItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html; charset=utf-8");
        res.setStatus(HttpServletResponse.SC_OK);
        MiddlewareHandler handler = new MiddlewareHandler();
        List<Item> listItem = null;
        try {
            int number = 30;

            if (req.getParameter("n") != null) {
                number = Integer.parseInt(req.getParameter("n"));
            }
            listItem = handler.getAllItems(number);
        } catch (TException ex) {
            Logger.getLogger(listAllItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < listItem.size(); i++) {
            res.getWriter().println(listItem.get(i));
            res.getWriter().println("<br/>");
            res.getWriter().println("<hr/>");

        }
        res.getWriter().println("session=" + req.getSession(true).getId());
    }
}
