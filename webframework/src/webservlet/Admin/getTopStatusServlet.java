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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class getTopStatusServlet extends HttpServlet{
    MiddlewareHandler handler=new MiddlewareHandler();
    @Override
    protected  void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        List<Item> result;
        try {
            result = handler.getTopItems(300);
        } catch (TException ex) {
            Logger.getLogger(getTopStatusServlet.class.getName()).log(Level.SEVERE, null, ex);
            res.getWriter().println(ex.getMessage());
        }
        
    }
}
