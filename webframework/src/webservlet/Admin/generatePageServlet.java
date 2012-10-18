/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import com.vng.jcore.profiler.ProfilerLog;
import frontend.MiddlewareHandler;
import frontend.getConfig;
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author root
 */
public class generatePageServlet extends HttpServlet {
    MiddlewareHandler handler=new MiddlewareHandler();
      private static final Logger log = LoggerFactory.getLogger(generatePageServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        res.setContentType("text/html; charset=utf-8");
        boolean dbg = ("on".equals(req.getParameter("jdbg")));
        ProfilerLog profiler = new ProfilerLog(dbg);
        req.setAttribute("profiler", profiler);
        profiler.doStartLog("wholereq");

        try {
            String content = this.render(req);

            this.out(content, res);
        } catch (Exception ex) {

            log.error(ex.getMessage());
            this.out("Error exception: " + ex.getMessage(), res);
        }

        profiler.doEndLog("wholereq");
        if (dbg) {
            String tmp = profiler.dumpLogHtml();
            this.out(tmp, res);
        }
    }
    
    private String render(HttpServletRequest req) throws IOException, TException, Exception{
        int page = 1;
        String tagID="-1";
        int itemPerPage =getConfig.getInstance().getItemPerPage();
       
        try {
            if (req.getParameter("p") != null) {
                page = Integer.parseInt(req.getParameter("p"));
            }
            if (req.getParameter("t") != null) {
                tagID = req.getParameter("t");
            }

        } catch (Exception ex) {
            return "Error: " + ex.getMessage();
        }
        
        TemplateDataDictionary dic = TemplateDictionary.create();
        int itemTagSize=1;
        if(tagID.equals("-1")){
            itemTagSize=(int) handler.itemdbSize();
        }
        else
        {
            itemTagSize = (int) handler.itemtagSize(tagID);
        }
        int pageCount = (int) Math.ceil(itemTagSize / itemPerPage);

        int start=Math.max(page-4, 1);
        int end=Math.min(page+4, pageCount);

        
        for (int i = start; i <= end; i++) {
            if (i == page) {
                TemplateDataDictionary listPageSection = dic.addSection("listPage_section");
                listPageSection.setVariable("page", "<a href=\"#\" class=\"graybutton pagelink active\" rel=\"" + page + "\">" + page + "</a>");
            } else {
                TemplateDataDictionary listPageSection = dic.addSection("listPage_section");
                listPageSection.setVariable("page", "<a href=\"#\" onclick='aaa("+i+");' class=\"graybutton pagelink\" rel=\"" + i + "\">" + i + "</a>");
            }
        }
        dic.setVariable("last", String.valueOf(pageCount));

        Template template = this.getCTemplate();
        String content = template.renderToString(dic);

        return content;
    }
    


    private Template getCTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("admin/index/paging.xtm");
        return template;
    }

    private void out(String content, HttpServletResponse resp) throws IOException {

        resp.addHeader("Content-Type", "text/html");
        PrintWriter out = resp.getWriter();
        out.println(content);

    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        doGet(req, res);
    }
}
