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
public class changeUserRoleServlet extends HttpServlet {

    MiddlewareHandler handler = new MiddlewareHandler();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String userID = req.getParameter("userID");
        int userRole = Integer.parseInt(req.getParameter("userRole"));
        String userToken = req.getParameter("userToken");
        boolean result = false;
        try {
            result = handler.editUser(userID, userToken, userRole);
            MyLocalCache mycache=new MyLocalCache();
            mycache.setBlockUser(userID, userToken, userRole);
        } catch (TException ex) {
            Logger.getLogger(changeUserRoleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        res.getWriter().println(result);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        doGet(req, res);
    }
}
