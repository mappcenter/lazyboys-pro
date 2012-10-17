/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import appinfo.MyAppInfo;
import com.google.gson.Gson;
import frontend.MiddlewareHandler;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.ClientProtocolException;
import zme.api.exception.ZingMeApiException;
import zme.api.feed.ZME_Feed;
import zme.api.feed.ZME_FeedItem;
import zme.api.graph.ZME_Me;
import zme.api.oauth.ZME_AccessTokenData;
import zme.api.oauth.ZME_Authentication;
/**
 *
 * @author root
 */
public class indexServerlet extends HttpServlet{
    public static MiddlewareHandler handler = new MiddlewareHandler();
    public ZME_Authentication zme;
    //public ZME_GraphAPI zApi;
    public String url=null;
    public String authorCode=null;
    public String token=null;
    public ZME_AccessTokenData zATData;
    public String test="hello thiensuhack";
    private ZME_Me zm;
    HashMap<String,Object> me = null;
    String sigKey=null;
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws IOException, ServletException, ClientProtocolException 
    {
        
        //ZME_Config config=MyAppInfo.getInstance().getzConfig();
        zme=new ZME_Authentication(MyAppInfo.getInstance().getzConfig());               
        url= zme.getAuthorizedUrl(MyAppInfo.getInstance().getUrlToRedirect(), url);
        res.setContentType("text/html; charset=UTF-8"); 
        if(req.getParameter("code")==null || req.getParameter("code").isEmpty())
        {
            res.sendRedirect(url);
        }
        else{
            try {
                authorCode=req.getParameter("code");
                zATData=zme.getAccessTokenFromCode(URLDecoder.decode(authorCode,"UTF-8"));
                //String sigRequest=req.getParameter("signed_request");
                //zATData=zme.getAccessTokenForAppAuthentication();
                int a=0;
            } catch (ZingMeApiException ex) {
                Logger.getLogger(indexServerlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            zm=new ZME_Me(MyAppInfo.getInstance().getzConfig());
            
            try {               
                me = zm.getInfo(zATData.accessToken,"displayname");
            } catch (ZingMeApiException ex) {
                Logger.getLogger(indexServerlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        //req.setAttribute("hello", "servletToJsp");
        //getServletConfig().getServletContext().getRequestDispatcher("/index.jsp").forward(req, res);
        //System.out.println(me); 
        ZME_Feed feed=new ZME_Feed(MyAppInfo.getInstance().getzConfig());
        //ZME_FeedItem feedItem=new ZME_FeedItem((int)me.get("id"), (int)me.get("id"), 1, 3, "", "attach name","http://attachHref.com", "caption vnexpress.net", "description", 1, "mediaImage", "mediaSource", "actionLinkText", "actionLinkH");
        int id=Integer.valueOf(me.get("id").toString());
        
        ZME_FeedItem itemFeed=new ZME_FeedItem(id,
                id,
                1,
                (short)3,
                "",
                "Ca sĩ Quang Hà chuẩn bị kiện nhân chứng Vietnam Airlines",
                "http://vnexpress.net/gl/kinh-doanh/2011/05/ca-si-quang-ha-chuan-bi-kien-nhan-chung-vietnam-airlines/",
                "vnexpress.net",
                "Nhận được tin nhắn xin lỗi được cho là của bà Eileen Tan - nhân chứng trong vụ HLV Taekwondo, nhưng ca sĩ Quang Hà vẫn quyết đưa việc này ra tòa.",
                (short)1,
                "http://vnexpress.net/Files/Subject/3b/a2/99/39/quang-ha-250.jpg.thumb150x0.ns.jpg",
                "http://vnexpress.net/gl/kinh-doanh/2011/05/ca-si-quang-ha-chuan-bi-kien-nhan-chung-vietnam-airlines/",
                "Link",
                "http://me.zing.vn/apps/link"
                );
        
        try {
            sigKey=feed.generateSignatureKey(itemFeed);
        } catch (ZingMeApiException ex) {
            Logger.getLogger(indexServerlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        req.setAttribute("sigkey", sigKey);
        //System.out.println(authorCode); 
        Gson gson=new Gson();
        req.setAttribute("itemFeed", gson.toJson(itemFeed));
        req.setAttribute("me", me);
        getServletConfig().getServletContext().getRequestDispatcher("/index.jsp").forward(req, res);
        
        //System.out.println("hellothiensuhack");
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{                        
        
    }
}
