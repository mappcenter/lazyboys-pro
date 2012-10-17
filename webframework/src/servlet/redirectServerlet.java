/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.ClientProtocolException;

/**
 *
 * @author root
 */
public class redirectServerlet extends HttpServlet{    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws IOException, ServletException, ClientProtocolException 
    {
        System.out.println("servlet path= " + req.getServletPath());
        System.out.println("request URL= " + req.getRequestURL());
        System.out.println("request URI= " + req.getRequestURI());     
        
        //System.out.println("hellothiensuhack");
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{                        
        
    }
}
