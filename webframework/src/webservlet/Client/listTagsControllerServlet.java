package webservlet.Client;

import com.google.gson.Gson;
import frontend.MiddlewareHandler;
import frontend.Tag;
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.ClientProtocolException;

public class listTagsControllerServlet extends HttpServlet {

    private static MiddlewareHandler handler = new MiddlewareHandler();
    public static List<Tag> ltags = new ArrayList<Tag>();
    private static String contentFile = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ClientProtocolException {

        resp.setContentType("text/html; charset=utf-8");
        try {
            String content = this.render(req, resp);
            this.out(content, resp);
        } catch (Exception ex) {
            this.out("Error exception: " + ex.getMessage(), resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private String render(HttpServletRequest req, HttpServletResponse res) throws Exception {

        TemplateDataDictionary dic = TemplateDictionary.create();
        Template template = this.getCTemplate();       
        if (contentFile == null) {
            ltags=handler.getAllTag();
            Gson gson = new Gson();
            contentFile = "var listTags='";
            contentFile += gson.toJson(ltags) + "';";
        }
        TemplateDataDictionary sFile = dic.addSection("listTags");
        sFile.setVariable("contentListTags", contentFile);
        String content = template.renderToString(dic);
        return content;


    }

    private Template getCTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("client/index/listtags.xtm");
        return template;
    }

    private void out(String content, HttpServletResponse resp) throws IOException {        
        PrintWriter out = resp.getWriter();
        out.println(content);

    }
}
