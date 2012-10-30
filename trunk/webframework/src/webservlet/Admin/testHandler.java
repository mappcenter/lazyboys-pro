/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import frontend.Item;
import frontend.MiddlewareHandler;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 *
 * @author root
 */
public class testHandler extends AbstractHandler {

    @Override
    public void handle(String string, Request rqst, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html; charset=utf-8");
        res.setStatus(HttpServletResponse.SC_OK);
        rqst.setHandled(true);
        MiddlewareHandler handler=new MiddlewareHandler();
        String keyword = req.getParameter("key");
        int page = Integer.parseInt(req.getParameter("p"));
        int itemPerPage = Integer.parseInt(req.getParameter("n"));
        try {
            List<Item> listItem=handler.getItemsPageKeyword(keyword, page, itemPerPage);
            for(Item item:listItem){
                res.getWriter().println(item+"<br/><hr/>");
            }
        } catch (TException ex) {
            Logger.getLogger(testHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
