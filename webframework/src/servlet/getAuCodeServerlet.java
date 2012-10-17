/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import appinfo.MyAppInfo;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class getAuCodeServerlet extends HttpServlet{
    public String authorizationCode=null;
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws IOException, ServletException 
    {
        if(req.getParameter("code")==null ||req.getParameter("code").isEmpty())
        {                         
            //int index=(new Random()).nextInt(100);            
                    
        }
        else{
            authorizationCode=req.getParameter("code");
            String url = "http://fresher2012.dev/index?code="+authorizationCode;               
            res.sendRedirect(url);    
        }
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) 
            throws IOException, ServletException 
    {
        
    }
}
