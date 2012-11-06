/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import com.vng.jcore.profiler.ProfilerLog;
import frontend.Item;
import frontend.MiddlewareHandler;
import frontend.MyLocalCache;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author chanhlt
 */
public class randomItemServlet extends HttpServlet {
    
    public static MiddlewareHandler handler = new MiddlewareHandler();
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        boolean dbg = ("on".equals(req.getParameter("jdbg")));
        ProfilerLog profiler = new ProfilerLog(dbg);
        req.setAttribute("profiler", profiler);
        profiler.doStartLog("wholereq");
        Item item = null;
        try {
            MyLocalCache mycache = new MyLocalCache();
            item = mycache.getFastRandom();
        } catch (Exception ex) {
            Logger.getLogger(randomItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        res.getWriter().println("<html><body><center><h1>Welcome to LazyBoys!</h1></center><br/>" + item.content.toString() + "</body></html>");
        profiler.doEndLog("wholereq");
        if (dbg) {
            String tmp = profiler.dumpLogHtml();
            res.getWriter().println(tmp);
        }
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        this.doGet(req, res);
    }
}
