/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import frontend.MiddlewareHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class editItemServlet extends HttpServlet{
   MiddlewareHandler handler=new MiddlewareHandler();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        boolean result=true;
        String itemID, itemContent, listTagID;
         List<String> tagIDs;
        try{
        itemID=req.getParameter("itemID");
        itemContent=req.getParameter("itemContent");
        listTagID=req.getParameter("tagIDs");
        
        String[] str=listTagID.split(",");
        tagIDs = new ArrayList<String>();
        tagIDs.addAll(Arrays.asList(str));
        }
        catch(Exception e){
            return;
        }       
        try {
            result = handler.editItem(itemID, itemContent, tagIDs);
        } catch (TException ex) {
            Logger.getLogger(editItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(result) {
            res.getWriter().println("Update successful");
        }
        else{
            res.getWriter().println("Update failed");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        doGet(req, res);
    }
}
