/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import libs.Item;
import org.apache.thrift.TException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 *
 * @author chanhlt
 */
public class hellohandler extends AbstractHandler {

    @Override
    public void handle(String string, Request baseReq, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html; charset=utf-8");
        res.setStatus(HttpServletResponse.SC_OK);
        baseReq.setHandled(true);
        FrontendHandler handler = null;
        try {
            handler = new FrontendHandler(); 
        } catch (TException ex) {
            Logger.getLogger(hellohandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Item> item = null;
        try {
            item = handler.getFavouriteItems("userID", 10);
        } catch (TException ex) {
            Logger.getLogger(hellohandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=0; i<item.size(); i++){
            res.getWriter().println(item.get(i).content+"<hr/>");
        }
        //res.getWriter().println(item);
    }
}
