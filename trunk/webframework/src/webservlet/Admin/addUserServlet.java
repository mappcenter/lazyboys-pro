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
public class addUserServlet extends HttpServlet {

    MiddlewareHandler handler = new MiddlewareHandler();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String userID = req.getParameter("userID");
        String accessToken = req.getParameter("accessToken");
        int userRole = Integer.parseInt(req.getParameter("userRole"));
        boolean result = false;
        try {
           
            result = handler.addUser(userID, accessToken, userRole);
           
        } catch (TException ex) {
            Logger.getLogger(addUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(result){
            res.getWriter().println("insert success");
        }
        else{
            res.getWriter().println("insert failed");
        }
    }
}
