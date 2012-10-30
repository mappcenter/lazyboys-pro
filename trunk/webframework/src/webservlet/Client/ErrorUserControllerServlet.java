package webservlet.Client;

import com.vng.jcore.profiler.ProfilerLog;
import frontend.MiddlewareHandler;
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorUserControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(ErrorUserControllerServlet.class);
    public static MiddlewareHandler handler = new MiddlewareHandler();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {                        
        resp.setContentType("text/html; charset=utf-8");
        boolean dbg = ("on".equals(req.getParameter("jdbg")));
        ProfilerLog profiler = new ProfilerLog(dbg);
        req.setAttribute("profiler", profiler);
        profiler.doStartLog("wholereq");
        try {
            String content = this.render(req, resp);
            this.out(content, resp);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            this.out("Error exception: " + ex.getMessage(), resp);
        }

        profiler.doEndLog("wholereq");
        String tmp = profiler.dumpLogHtml();
        if (dbg) {
            tmp = profiler.dumpLogHtml();
            System.out.print(tmp);
            this.out(tmp, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html; charset=UTF-8");                
//        List<Item> items=null;
//        items=MiddlewareHandler.myLocalCache.getItemsForClientCache();
//        Gson gson=new Gson();
//        resp.getWriter().print(gson.toJson(items));
        
    }    
    private String render(HttpServletRequest req, HttpServletResponse res) throws Exception {
        TemplateDataDictionary dic = TemplateDictionary.create();       
        Template template = this.getCTemplate();
        String content = template.renderToString(dic);
        return content;


    }        
    private Template getCTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("client/index/blockeduser.xtm");
        return template;
    }

    private void out(String content, HttpServletResponse resp) throws IOException {

        resp.addHeader("Content-Type", "text/html");
        PrintWriter out = resp.getWriter();
        out.print(content);

    }
}
