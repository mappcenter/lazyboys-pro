/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import frontend.Item;
import frontend.MiddlewareHandler;
import frontend.Tag;
import frontend.getConfig;
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class getTagPageServlet extends HttpServlet{
    
    private MiddlewareHandler handler=new MiddlewareHandler();
    
    @Override
    protected  void doGet(HttpServletRequest req, HttpServletResponse res){
        
    }

    private List<Tag> getTagPage(int pageNumber, int tagPerPage) throws TException {
        List<Tag> lAllTag=handler.getAllTag();
        List<Tag> result = new ArrayList<Tag>();
        for(int i=0; i<tagPerPage; i++){
            result.add(lAllTag.get(i));
        }
        return result;
    }

   
    private Template getCTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("admin/index/content.xtm");
        return template;
    }
    
   
    private void out(String content, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html");
        PrintWriter out = resp.getWriter();
        out.println(content);

    }

    private String render(HttpServletRequest req) throws Exception {
        int page = 1;

        int itemPerPage = getConfig.getInstance().getItemPerPage();
        try {
            if (req.getParameter("p") != null) {
                page = Integer.parseInt(req.getParameter("p"));
            }
        } catch (Exception ex) {
            return "Error: " + ex.getMessage();
        }

        List<Tag> listTag= this.getTagPage(page, itemPerPage);

        TemplateDataDictionary dic = TemplateDictionary.create();

        for (int i = 0; i < listTag.size(); i++) {
            TemplateDataDictionary listsection = dic.addSection("listTag_section");
            listsection.setVariable("tagName", listTag.get(i).tagName);
            listsection.setVariable("tagID", listTag.get(i).tagID);
        }
       

        Template template = this.getCTemplate();
        String content = template.renderToString(dic);
        
        return content;
    }
}
