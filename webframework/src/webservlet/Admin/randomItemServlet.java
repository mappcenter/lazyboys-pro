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

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(IndexControllerServlet.class);
    public static MiddlewareHandler handler = new MiddlewareHandler();
    int count = 0;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("Count = "+count);
        res.setContentType("text/html; charset=utf-8");
        boolean dbg = ("on".equals(req.getParameter("jdbg")));
        ProfilerLog profiler = new ProfilerLog(dbg);
        req.setAttribute("profiler", profiler);
        profiler.doStartLog("wholereq");
        
        long t1 = System.currentTimeMillis();
        res.setContentType("text/html; charset=UTF-8");
        Item item = null;
        try {
            MyLocalCache mycache = new MyLocalCache();
            item = mycache.getFastRandom();
        } catch (Exception ex) {
            Logger.getLogger(randomItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        long t2 = System.currentTimeMillis();
        //Gson gson = new Gson();
        //String strItem = gson.toJson(item);
        String str = "<html><body><center><h1>Welcome to LazyBoys!</h1></center><br/>Count = "+count+"<br/>Time: " + (t2 - t1) + " ms<br/>" + item.toString() + "</body></html>";
        this.out(str, res);
        profiler.doEndLog("wholereq");
        if (dbg) {
            String tmp = profiler.dumpLogHtml();
            this.out(tmp, res);
        }
        count++;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        doGet(req, res);
    }

    private void out(String content, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html");
        resp.setContentType("text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(content);
    }
}
