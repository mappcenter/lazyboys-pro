package webservlet.Client;

import frontend.Item;
import com.google.gson.Gson;
import com.vng.jcore.common.Config;
import com.vng.jcore.profiler.ProfilerLog;
import frontend.MiddlewareHandler;
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
import servlet.randomItemServlet;

public class SaveItemControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(SaveItemControllerServlet.class);
    public static MiddlewareHandler handler = new MiddlewareHandler();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html; charset=utf-8");
        boolean dbg = ("on".equals(req.getParameter("jdbg")));
        ProfilerLog profiler = new ProfilerLog(dbg);
        req.setAttribute("profiler", profiler);
        profiler.doStartLog("wholereq");

        try {
            String content = this.render(req);

            this.out(content, resp);
        } catch (Exception ex) {

            log.error(ex.getMessage());
            this.out("Error exception: " + ex.getMessage(), resp);
        }

        profiler.doEndLog("wholereq");
        if (dbg) {
            String tmp = profiler.dumpLogHtml();
            this.out(tmp, resp);
        }
        
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        System.out.println("test hello thiensuhack");
    }

    private String render(HttpServletRequest req) throws Exception {
        
        List<Item> listItem = this.testCallThriftService(req);
        String config_host = this.testGetConfig(req);
        String productName = listItem.get(1).itemID;


        TemplateDataDictionary dic = TemplateDictionary.create();

        dic.setVariable("title", "This is title of layout - config=" + config_host + " - product=" + productName);
        

        for (int i = 0; i < listItem.size(); i++) {
            TemplateDataDictionary listsection = dic.addSection("list_section");
            listsection.setVariable("itemContent", listItem.get(i).content);
        }
        
         Item item = null;
        
        try {
            item = handler.getRandomItem();
        } catch (TException ex) {
            java.util.logging.Logger.getLogger(randomItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson=new Gson();
        String strItem=gson.toJson(item);
        
        TemplateDataDictionary itemRan= dic.addSection("itemRan");
        itemRan.setVariable("strItem", strItem);
        
        
        Template template = this.getCTemplate();
        String content = template.renderToString(dic);

        return content;


    }
    public Item getRandomItem(){
        Item item = null;
        
        try {
            item = handler.getRandomItem();
        } catch (TException ex) {
            java.util.logging.Logger.getLogger(randomItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }
    private String testGetConfig(HttpServletRequest req) {
        String config = Config.getParam("sample", "host");
        return config;
    }

    private List<Item> testCallThriftService(HttpServletRequest req) throws TException {

        ProfilerLog profiler = (ProfilerLog) req.getAttribute("profiler");
        if (profiler != null) {
            profiler.doStartLog("fresherthriftservice");
        }
        MiddlewareHandler handler = new MiddlewareHandler();

        List<Item> listItem = handler.getAllItems(100);

        if (profiler != null) {
            profiler.doEndLog("fresherthriftservice");
        }

        return listItem;
    }

    private Template getCTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("sample/index/index_1.xtm");
        return template;
    }

    private void out(String content, HttpServletResponse resp) throws IOException {

        resp.addHeader("Content-Type", "text/html");
        PrintWriter out = resp.getWriter();
        out.println(content);

    }
}
