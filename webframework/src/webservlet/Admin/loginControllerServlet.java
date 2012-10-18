/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

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

/**
 *
 * @author root
 */
public class loginControllerServlet extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType("text/html; charset=utf-8");
        try {
            String content = this.render(req);
            this.out(content, res);
        } catch (Exception ex) {
            this.out("Error exception: " + ex.getMessage(), res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
    }
       
    private String render(HttpServletRequest req) throws Exception {
              
        TemplateDataDictionary dic = TemplateDictionary.create();
        Template template = this.getCTemplate();
        String content = template.renderToString(dic);
        return content;
    }
  
    private Template getCTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("admin/index/index_2.xtm");
        return template;
    }

    private void out(String content, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html");
        PrintWriter out = resp.getWriter();
        out.println(content);
    }
}
