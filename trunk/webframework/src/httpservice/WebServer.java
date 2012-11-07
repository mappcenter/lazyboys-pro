package httpservice;

import frontend.*;
import java.util.Timer;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import webservlet.Admin.*;
import webservlet.Client.*;

public class WebServer {

    //private static Logger logger_ = Logger.getLogger(WebServer.class);

    public void start() throws Exception {

        Server server = new Server();
        
        int min_thread = getConfig.getInstance().getNumber_min_thread();
        int max_thread = getConfig.getInstance().getNumber_max_thread();
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMinThreads(min_thread);
        threadPool.setMaxThreads(max_thread);
        server.setThreadPool(threadPool);

        int port_listen = getConfig.getInstance().getPortListen();
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port_listen);
        connector.setMaxIdleTime(30000);
        connector.setConfidentialPort(8443);
        connector.setStatsOn(false);
        connector.setLowResourcesConnections(20000);
        connector.setLowResourcesMaxIdleTime(5000);

        server.setConnectors(new Connector[]{connector});

        ServletContextHandler servletContextHandler=new ServletContextHandler(ServletContextHandler.SESSIONS);

        // user services
        
        servletContextHandler.addServlet(new ServletHolder(new IndexControllerServlet()), "/");
        
        servletContextHandler.addServlet(new ServletHolder(new TestFeedControllerServlet()), "/testfeed");
        
        // lay favorite items cua user tu UserItem
        
        servletContextHandler.addServlet(new ServletHolder(new UserItemControllerServlet()), "/userItem");
        // return noi dung file  xcall.proxy-1.00.html cho viec Feed len zingMe
        
        servletContextHandler.addServlet(new ServletHolder(new XcallProxyControllerServlet()), "/xcall.proxy-1.00.html");
        //tra ve mot list tag va list top tag dang bien js Ex: var myTags=[json], var myTopTags=[json]
        
        servletContextHandler.addServlet(new ServletHolder(new listTagsControllerServlet()), "/listtags.js");
        //tao ra signkey de feed len zingMe
        
        servletContextHandler.addServlet(new ServletHolder(new FeedControllerServlet()), "/feedItem");
        //just test feed ^^
        
        servletContextHandler.addServlet(new ServletHolder(new TestFeedControllerServlet()), "/abc/*");
        //xu ly like or unlike item
        
        servletContextHandler.addServlet(new ServletHolder(new LikeUnlikeItemControllerServlet()), "/like_unlike");
        //luu favorite items cho user
        
        servletContextHandler.addServlet(new ServletHolder(new SaveItemControllerServlet()), "/saveItem");
        //xoa' favorite item cua  user
        
        servletContextHandler.addServlet(new ServletHolder(new DeleteItemControllerServlet()), "/delItem");
        //tra ve chuoi ID Items ma User da like.(POST method jquery)
        
        servletContextHandler.addServlet(new ServletHolder(new UserLikesItemControllerServlet()), "/uLikes");
        //Chua support luc nay'
        
        servletContextHandler.addServlet(new ServletHolder(new UserDisLikesItemControllerServlet()), "/uDisLikes");
        //lay 1 item random bat ki
        
        servletContextHandler.addServlet(new ServletHolder(new randomItemControllerServlet()), "/uRandom");
        //
        //handler.addServletWithMapping("webservlet.Client.SaveItemControllerServlet", "/saveItem");
        
        //cache items xuong client de random bang js
        
        servletContextHandler.addServlet(new ServletHolder(new CachingClientItemsControllerServlet()), "/uItemsCaching");
        //caching sang thong tin user khi truy cap vao ung dung (Ex: userFavoriteItem)
        
        servletContextHandler.addServlet(new ServletHolder(new CachingUserInfoControllerServlet()), "/uCaching");
        
        //Block user page
        
        servletContextHandler.addServlet(new ServletHolder(new ErrorUserControllerServlet()), "/blockUser");
        //Error page
        
        servletContextHandler.addServlet(new ServletHolder(new ErrorPageControllerServlet()), "/errorpage");
        //get items of tag
       
        servletContextHandler.addServlet(new ServletHolder(new GetItemsOfTagControllerServlet()), "/getItemsTag");
        //get items of tag for random client
        
        servletContextHandler.addServlet(new ServletHolder(new GetItemsOfTagForRandomControllerServlet()), "/getItemsTagForRandom");
        //handler.addServletWithMapping("servlet.randomItemServlet","/random");
        //admin services


        //Admin

        //trang chu admin
        servletContextHandler.addServlet(new ServletHolder(new IndexServlet()), "/admin");
        
        
        servletContextHandler.addServlet(new ServletHolder(new randomItemServlet()), "/random");
        
        
        servletContextHandler.addServlet(new ServletHolder(new getContentTabServlet()), "/getContentTab");
        
        
        servletContextHandler.addServlet(new ServletHolder(new getItemPageServlet()), "/getItemPage");

        
        servletContextHandler.addServlet(new ServletHolder(new deleteItemServlet()), "/deleteItem");
        
        
        servletContextHandler.addServlet(new ServletHolder(new listAllTagServlet()), "/listAllTag");
        
        
        servletContextHandler.addServlet(new ServletHolder(new editItemServlet()), "/editItem");
        
       
        servletContextHandler.addServlet(new ServletHolder(new getTopStatusServlet()), "/getTopStatus");
        
       
        servletContextHandler.addServlet(new ServletHolder(new insertItemServlet()), "/insertItem");
        
       
        servletContextHandler.addServlet(new ServletHolder(new editTagServlet()), "/editTag");
        
        
        servletContextHandler.addServlet(new ServletHolder(new deleteTagServlet()), "/deleteTag");
        
        
        servletContextHandler.addServlet(new ServletHolder(new addTagServlet()), "/addTag");
        
       
        servletContextHandler.addServlet(new ServletHolder(new addUserServlet()), "/addUser");
        
        
        servletContextHandler.addServlet(new ServletHolder(new changeUserRoleServlet()), "/changeUserRole");
        
        
        servletContextHandler.addServlet(new ServletHolder(new searchItemServlet()), "/searchItem");
        
        servletContextHandler.addServlet(new ServletHolder(new loginServlet()), "/login");
        
        servletContextHandler.addServlet(new ServletHolder(new deleteUserServlet()), "/deleteUser");
           
        server.setHandler(servletContextHandler);
        
        MiddlewareHandler handler = new MiddlewareHandler();
        System.out.println("Caching data ...");
        handler.startLocalCache();
        Timer timer = new Timer();
        int swapCacheTime=getConfig.getInstance().swapCacheTime();
        timer.schedule(new MyTask(), swapCacheTime, swapCacheTime);
        server.start();
        server.join();
    }
}
