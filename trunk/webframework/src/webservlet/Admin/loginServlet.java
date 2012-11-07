/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservlet.Admin;

import frontend.getConfig;
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author root
 */
public class loginServlet extends HttpServlet {

    /**
     *
     * @param req
     * @param res
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            Template template = this.getCTemplate();
            TemplateDataDictionary dic = TemplateDictionary.create();
            res.getWriter().println(template.renderToString(dic));
        } catch (Exception ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username.equals(getConfig.getInstance().userName()) && password.equals(getConfig.getInstance().password())) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", username);
            res.sendRedirect("/admin");
        }
        res.getWriter().println("<script>alert('Username or password invalid!!!');</script>");
        doGet(req, res);
    }

    private Template getCTemplate() throws Exception {
        TemplateLoader templateLoader = TemplateResourceLoader.create("tpl/");
        Template template = templateLoader.getTemplate("admin/index/login.xtm");
        return template;
    }
}
