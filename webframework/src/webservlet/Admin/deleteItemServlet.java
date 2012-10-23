/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import frontend.MiddlewareHandler;
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
public class deleteItemServlet extends HttpServlet{
    private MiddlewareHandler handler=new MiddlewareHandler();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        String itemID = req.getParameter("listItemID");
        String[] listItemID=itemID.split(",");
        //boolean result = false;
        try {
            for(int i=0; i<listItemID.length; i++){
                handler.deleteItem(listItemID[i]);
            }
            
        } catch (TException ex) {
            Logger.getLogger(deleteItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //res.getWriter().println(result);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        doGet(req, res);
        
    }
}
