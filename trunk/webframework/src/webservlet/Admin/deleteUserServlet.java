/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import frontend.MiddlewareHandler;
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
public class deleteUserServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res){
        
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        String userID=req.getParameter("userID");
        MiddlewareHandler handler=new MiddlewareHandler();
        try {
            handler.deleteUser(userID);
        } catch (TException ex) {
            Logger.getLogger(deleteUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
