package httpservice;

import frontend.*;
import java.lang.management.ManagementFactory;
import java.util.Timer;
import org.apache.log4j.Logger;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import webservlet.Admin.testHandler;
import webservlet.Client.CachingClientItemsControllerServlet;
import webservlet.Client.CachingUserInfoControllerServlet;
import webservlet.Client.DeleteItemControllerServlet;
import webservlet.Client.ErrorPageControllerServlet;
import webservlet.Client.ErrorUserControllerServlet;
import webservlet.Client.FeedControllerServlet;
import webservlet.Client.GetItemsOfTagControllerServlet;
import webservlet.Client.GetItemsOfTagForRandomControllerServlet;
import webservlet.Client.IndexControllerServlet;
import webservlet.Client.LikeUnlikeItemControllerServlet;
import webservlet.Client.SaveItemControllerServlet;
import webservlet.Client.TestFeedControllerServlet;
import webservlet.Client.UserDisLikesItemControllerServlet;
import webservlet.Client.UserItemControllerServlet;
import webservlet.Client.UserLikesItemControllerServlet;
import webservlet.Client.XcallProxyControllerServlet;
import webservlet.Client.listTagsControllerServlet;
import webservlet.Client.randomItemControllerServlet;

public class WebServer {

    private static Logger logger_ = Logger.getLogger(WebServer.class);

    public void start() throws Exception {

        Server server = new Server();

        // Setup JMX
        MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());

        server.getContainer().addEventListener(mbContainer);
        server.addBean(mbContainer);
        mbContainer.addBean(Log.getLog());
        
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

        ServletHandler handler = new ServletHandler();
        ServletContextHandler servletContextHandler=new ServletContextHandler(ServletContextHandler.SESSIONS);

        // user services
        handler.addServletWithMapping("webservlet.Client.IndexControllerServlet", "/");
        servletContextHandler.addServlet(new ServletHolder(new IndexControllerServlet()), "/");
        handler.addServletWithMapping("webservlet.Client.TestFeedControllerServlet", "/testfeed");
        servletContextHandler.addServlet(new ServletHolder(new TestFeedControllerServlet()), "/testfeed");
        
        // lay favorite items cua user tu UserItem
        handler.addServletWithMapping("webservlet.Client.UserItemControllerServlet", "/userItem"); //get 
        servletContextHandler.addServlet(new ServletHolder(new UserItemControllerServlet()), "/userItem");
        // return noi dung file  xcall.proxy-1.00.html cho viec Feed len zingMe
        handler.addServletWithMapping("webservlet.Client.XcallProxyControllerServlet", "/xcall.proxy-1.00.html");
        servletContextHandler.addServlet(new ServletHolder(new XcallProxyControllerServlet()), "/xcall.proxy-1.00.html");
        //tra ve mot list tag va list top tag dang bien js Ex: var myTags=[json], var myTopTags=[json]
        handler.addServletWithMapping("webservlet.Client.listTagsControllerServlet", "/listtags.js");
        servletContextHandler.addServlet(new ServletHolder(new listTagsControllerServlet()), "/listtags.js");
        //tao ra signkey de feed len zingMe
        handler.addServletWithMapping("webservlet.Client.FeedControllerServlet", "/feedItem");
        servletContextHandler.addServlet(new ServletHolder(new FeedControllerServlet()), "/feedItem");
        //just test feed ^^
        handler.addServletWithMapping("webservlet.Client.TestFeedControllerServlet", "/abc/*");
        servletContextHandler.addServlet(new ServletHolder(new TestFeedControllerServlet()), "/abc/*");
        //xu ly like or unlike item
        handler.addServletWithMapping("webservlet.Client.LikeUnlikeItemControllerServlet", "/like_unlike");
        servletContextHandler.addServlet(new ServletHolder(new LikeUnlikeItemControllerServlet()), "/like_unlike");
        //luu favorite items cho user
        handler.addServletWithMapping("webservlet.Client.SaveItemControllerServlet", "/saveItem");
        servletContextHandler.addServlet(new ServletHolder(new SaveItemControllerServlet()), "/saveItem");
        //xoa' favorite item cua  user
        handler.addServletWithMapping("webservlet.Client.DeleteItemControllerServlet", "/delItem");
        servletContextHandler.addServlet(new ServletHolder(new DeleteItemControllerServlet()), "/delItem");
        //tra ve chuoi ID Items ma User da like.(POST method jquery)
        handler.addServletWithMapping("webservlet.Client.UserLikesItemControllerServlet", "/uLikes");
        servletContextHandler.addServlet(new ServletHolder(new UserLikesItemControllerServlet()), "/uLikes");
        //Chua support luc nay'
        handler.addServletWithMapping("webservlet.Client.UserDisLikesItemControllerServlet", "/uDisLikes");
        servletContextHandler.addServlet(new ServletHolder(new UserDisLikesItemControllerServlet()), "/uDisLikes");
        //lay 1 item random bat ki
        handler.addServletWithMapping("webservlet.Client.randomItemControllerServlet", "/uRandom");
        servletContextHandler.addServlet(new ServletHolder(new randomItemControllerServlet()), "/uRandom");
        //
        //handler.addServletWithMapping("webservlet.Client.SaveItemControllerServlet", "/saveItem");
        
        //cache items xuong client de random bang js
        handler.addServletWithMapping("webservlet.Client.CachingClientItemsControllerServlet", "/uItemsCaching");
        servletContextHandler.addServlet(new ServletHolder(new CachingClientItemsControllerServlet()), "/uItemsCaching");
        //caching sang thong tin user khi truy cap vao ung dung (Ex: userFavoriteItem)
        handler.addServletWithMapping("webservlet.Client.CachingUserInfoControllerServlet", "/uCaching");
        servletContextHandler.addServlet(new ServletHolder(new CachingUserInfoControllerServlet()), "/uCaching");
        
        //Block user page
        handler.addServletWithMapping("webservlet.Client.ErrorUserControllerServlet", "/blockUser");
        servletContextHandler.addServlet(new ServletHolder(new ErrorUserControllerServlet()), "/blockUser");
        //Error page
        handler.addServletWithMapping("webservlet.Client.ErrorPageControllerServlet", "/errorpage");
        servletContextHandler.addServlet(new ServletHolder(new ErrorPageControllerServlet()), "/errorpage");
        //get items of tag
        handler.addServletWithMapping("webservlet.Client.GetItemsOfTagControllerServlet", "/getItemsTag");
        servletContextHandler.addServlet(new ServletHolder(new GetItemsOfTagControllerServlet()), "/getItemsTag");
        //get items of tag for random client
        handler.addServletWithMapping("webservlet.Client.GetItemsOfTagForRandomControllerServlet", "/getItemsTagForRandom");
        servletContextHandler.addServlet(new ServletHolder(new GetItemsOfTagForRandomControllerServlet()), "/getItemsTagForRandom");
        //handler.addServletWithMapping("servlet.randomItemServlet","/random");
        //admin services


        //Admin

        handler.addServletWithMapping("webservlet.Admin.IndexControllerServlet", "/admin");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.IndexControllerServlet()), "/admin");
        
        handler.addServletWithMapping("webservlet.Admin.randomItemServlet", "/random");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.randomItemServlet()), "/random");
        
        handler.addServletWithMapping("webservlet.Admin.getContentTabServlet", "/getContentTab");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.getContentTabServlet()), "/getContentTab");
        
        handler.addServletWithMapping("webservlet.Admin.getItemPageServlet", "/getItemPage");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.getItemPageServlet()), "/getItemPage");

        handler.addServletWithMapping("webservlet.Admin.deleteItemServlet", "/deleteItem");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.deleteItemServlet()), "/deleteItem");
        
        handler.addServletWithMapping("webservlet.Admin.listAllTagServlet", "/listAllTag");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.listAllTagServlet()), "/listAllTag");
        
        handler.addServletWithMapping("webservlet.Admin.editItemServlet", "/editItem");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.editItemServlet()), "/editItem");
        
        handler.addServletWithMapping("webservlet.Admin.getTopStatusServlet", "/getTopStatus");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.getTopStatusServlet()), "/getTopStatus");
        
        handler.addServletWithMapping("webservlet.Admin.insertItemServlet", "/insertItem");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.insertItemServlet()), "/insertItem");
        
        handler.addServletWithMapping("webservlet.Admin.editTagServlet", "/editTag");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.editTagServlet()), "/editTag");
        
        handler.addServletWithMapping("webservlet.Admin.deleteTagServlet", "/deleteTag");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.deleteTagServlet()), "/deleteTag");
        
        handler.addServletWithMapping("webservlet.Admin.addTagServlet", "/addTag");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.addTagServlet()), "/addTag");
        
        handler.addServletWithMapping("webservlet.Admin.addUserServlet", "/addUser");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.addUserServlet()), "/addUser");
        
        handler.addServletWithMapping("webservlet.Admin.changeUserRoleServlet", "/changeUserRole");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.changeUserRoleServlet()), "/changeUserRole");
        
        handler.addServletWithMapping("webservlet.Admin.searchItemServlet", "/searchItem");
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.searchItemServlet()), "/searchItem");
        
        servletContextHandler.addServlet(new ServletHolder(new webservlet.Admin.loginServlet()), "/login");
        

        HandlerList handlerList=new HandlerList();
        handlerList.addHandler(handler);
        handlerList.addHandler(servletContextHandler);
        server.setHandler(servletContextHandler);

        server.setStopAtShutdown(true);
        server.setSendServerVersion(true);
        MiddlewareHandler h = new MiddlewareHandler();
        System.out.println("Caching data ...");
        h.startLocalCache();
        Timer timer = new Timer();
        timer.schedule(new MyTask(), (10 * 60 * 1000), (10 * 60 * 1000));
        server.start();
        server.join();
    }
}
