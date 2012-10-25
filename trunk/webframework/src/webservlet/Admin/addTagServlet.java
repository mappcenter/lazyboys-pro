/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import frontend.MiddlewareHandler;
import frontend.MyLocalCache;
import java.io.IOException;
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
public class addTagServlet extends HttpServlet{
    MiddlewareHandler handler=new MiddlewareHandler();
    MyLocalCache localCache=new MyLocalCache();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        String tagName=req.getParameter("tagName");
        boolean result=false;
        try {
            
            result = handler.insertTag(tagName);
           
        } catch (TException ex) {
            Logger.getLogger(addTagServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(result){
            try {
                localCache.updateListTags();
            } catch (TException ex) {
                Logger.getLogger(addTagServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            res.getWriter().println("Insert success");
        }
        else{
            res.getWriter().println("Insert failed");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        doGet(req, res);
    }
}
