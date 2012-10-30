/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import frontend.MiddlewareHandler;
import frontend.MyLocalCache;
import frontend.Tag;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.naming.spi.DirStateFactory;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class editTagServlet extends HttpServlet {

    MiddlewareHandler handler = new MiddlewareHandler();
    MyLocalCache localCache=new MyLocalCache();    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String tagID = req.getParameter("tagID");
        String tagName = req.getParameter("tagName");
        boolean result = false;
        try {
            result = handler.editTag(tagID, tagName);
        } catch (TException ex) {
            Logger.getLogger(editTagServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result) {         
            res.getWriter().println("Update success");
        } else {
            res.getWriter().println("Update failed");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doGet(req, res);
    }
}
