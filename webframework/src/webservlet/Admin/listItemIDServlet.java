/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import frontend.MiddlewareHandler;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class listItemIDServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        res.setContentType("text/html; charset=utf-8");        
        res.setStatus(HttpServletResponse.SC_OK);
        MiddlewareHandler handler=new MiddlewareHandler();
        List<String> listItemID = null;
        try {
            listItemID=handler.getAllItemsIDhaveTag("1");
        } catch (TException ex) {
            Logger.getLogger(listItemIDServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=0; i<listItemID.size(); i++){
            res.getWriter().println(listItemID.get(i));
        }
    }
}
