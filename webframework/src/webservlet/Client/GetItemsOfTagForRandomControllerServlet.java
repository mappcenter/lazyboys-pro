package webservlet.Client;

import com.google.gson.Gson;
import frontend.Item;
import frontend.MiddlewareHandler;
import frontend.MyLocalCache;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetItemsOfTagForRandomControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(SaveItemControllerServlet.class);
    public static MiddlewareHandler handler = new MiddlewareHandler();
    public static MyLocalCache myLocalCache = new MyLocalCache();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        String tagID = "";
        List<Item> items;
        items = new ArrayList<Item>();
        Gson gson = new Gson();
        String result = "none";
        if (req.getParameter("tagID") != null && req.getParameter("tagID") != null && req.getParameter("tagID").compareTo("-11") != 0) {
            tagID = req.getParameter("tagID");
            items = myLocalCache.getItemsTag(tagID);
        } 
        result = gson.toJson(items);
        resp.getWriter().println(result);
    }
}
