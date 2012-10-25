/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import frontend.MiddlewareHandler;
import frontend.Tag;
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
public class deleteTagServlet extends HttpServlet{
    MiddlewareHandler handler=new MiddlewareHandler();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        String tagID=req.getParameter("tagID");
        boolean result=false;
        try {
            result = handler.deleteTag(tagID);
        } catch (TException ex) {
            Logger.getLogger(deleteTagServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(result){
            if(MiddlewareHandler.LocalCache.containsKey("getAllTag")){
                MiddlewareHandler.LocalCache.remove("getAllTag");
                try {
                    List<Tag> listTag=handler.getAllTag();
                    MiddlewareHandler.LocalCache.put("getAllTag", listTag);
                } catch (TException ex) {
                    Logger.getLogger(deleteTagServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            res.getWriter().println("Delete success");
        }
        else{
            res.getWriter().println("Delete failed");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        doGet(req, res);
    }
}
