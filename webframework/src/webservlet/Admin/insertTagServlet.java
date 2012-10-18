/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import frontend.MiddlewareHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
@WebServlet("/insertTagServlet")
public class insertTagServlet extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        String tagName = req.getParameter("username");
        MiddlewareHandler handler=new MiddlewareHandler();
        boolean result = false;
        try {
            result = handler.insertTag(tagName);
        } catch (TException ex) {
            Logger.getLogger(insertTagServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        res.getWriter().println(result);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        doGet(req, res);
    }
}
