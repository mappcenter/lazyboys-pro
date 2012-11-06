/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import frontend.Item;
import frontend.MiddlewareHandler;
import frontend.MyLocalCache;
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
public class insertItemServlet extends HttpServlet {

    MiddlewareHandler handler = new MiddlewareHandler();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("text/html; charset=utf-8");
        String tagIDs = req.getParameter("tagIDs");
        String statusContent = req.getParameter("statusContent");
        String[] arrTagIDs = tagIDs.split(",");
        List<String> listTagIDs = new ArrayList<String>();
        listTagIDs.addAll(Arrays.asList(arrTagIDs));
        //listTagIDs.addAll(Arrays.asList(arrTagIDs));
        String result = null;

        try {
            result = handler.insertItem(statusContent, listTagIDs);
            if (result!=null) {
                Item item=handler.getItemFromItemID(result);
                MyLocalCache myLocalCache = new MyLocalCache();
                myLocalCache.setNewItem(item);
            }

        } catch (TException ex) {
            Logger.getLogger(insertItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result != null) {
            res.getWriter().println("Status content: " + statusContent + "\r\nTag list: " + listTagIDs + "\n" + result);
        } else {
            res.getWriter().println("Insert status failed ....");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doGet(req, res);
    }
}
