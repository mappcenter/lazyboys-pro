package webservlet.Client;

import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.ClientProtocolException;
import IOFile.FileIO;

public class XcallProxyControllerServlet extends HttpServlet {

    private static String strFile = "";

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
    }

    private String render(HttpServletRequest req, HttpServletResponse res) throws Exception {

        TemplateDataDictionary dic = TemplateDictionary.create();
        Template template = this.getCTemplate();
        if (strFile.isEmpty()) {
            FileIO file = new FileIO();
            ArrayList aFile = file.loadFile("xcall.proxy-1.00.html");

            for (int i = 0; i < aFile.size(); i++) {
                strFile += "\n" + aFile.get(i);
            }
        }
        TemplateDataDictionary sFile = dic.addSection("File");
        sFile.setVariable("contentFile", strFile);

        String content = template.renderToString(dic);
        return content;


    }

    private Template getCTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("client/index/xcall.xtm");
        return template;
    }

    private void out(String content, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println(content);

    }
}
