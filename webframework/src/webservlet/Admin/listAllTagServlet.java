/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import com.google.gson.Gson;
import frontend.MiddlewareHandler;
import frontend.Tag;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class listAllTagServlet extends HttpServlet{
    public static Gson gson=new Gson();
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws IOException, ServletException {
        
        res.setContentType("text/html; charset=utf-8");
    
        MiddlewareHandler handler=new MiddlewareHandler();
        
        List<Tag> listTag = null;
        try {
            listTag =handler.getAllTag();
        } catch (TException ex) {
            Logger.getLogger(listAllTagServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        String json=gson.toJson(listTag);
        res.getWriter().println(json);
        
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) 
            throws IOException, ServletException{
        doGet(req, res);
    }
}
