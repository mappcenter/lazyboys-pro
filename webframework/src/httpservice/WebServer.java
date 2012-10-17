package httpservice;

import com.vng.jcore.common.Config;
import java.lang.management.ManagementFactory;
import javax.management.Attribute;
import org.apache.log4j.Logger;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletContextHandler.Context;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.jmx.WebAppContextMBean;



public class WebServer {

    private static Logger logger_ = Logger.getLogger(WebServer.class);

    public void start() throws Exception {

        Server server = new Server();

        // Setup JMX
        MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());


        server.getContainer().addEventListener(mbContainer);
        server.addBean(mbContainer);
        mbContainer.addBean(Log.getLog());

        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMinThreads(100);
        threadPool.setMaxThreads(2000);
        server.setThreadPool(threadPool);

        int port_listen = Integer.valueOf(Config.getParam("rest", "port_listen"));
        
        

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port_listen);
        connector.setMaxIdleTime(30000);
        connector.setConfidentialPort(8443);
        connector.setStatsOn(false);
        connector.setLowResourcesConnections(20000);
        connector.setLowResourcesMaxIdleTime(5000);

        server.setConnectors(new Connector[]{connector});

        
        
        ServletHandler handler = new ServletHandler();
        
        
       // handler.setFilterMappings();
        handler.addServletWithMapping("webservlet.Client.IndexControllerServlet", "/");
        handler.addServletWithMapping("webservlet.Client.UserItemControllerServlet","/userItem");
        handler.addServletWithMapping("webservlet.Client.XcallProxyControllerServlet","/xcall.proxy-1.00.html");
        handler.addServletWithMapping("webservlet.Client.listTagsControllerServlet","/listtags.js");
        handler.addServletWithMapping("webservlet.Client.FeedControllerServlet","/feedItem");
        handler.addServletWithMapping("webservlet.Client.TestFeedControllerServlet","/testfeed");
        handler.addServletWithMapping("webservlet.Client.LikeUnlikeItemControllerServlet","/like_unlike");
        handler.addServletWithMapping("servlet.randomItemServlet","/random");
        
        
        
//        
//        ResourceHandler resource_handler = new ResourceHandler();
//        resource_handler.setDirectoriesListed(true);
//        resource_handler.setWelcomeFiles(new String[]{ "index.jsp" });
//
//        resource_handler.setResourceBase("src/webservlet/Client");
//
//        HandlerList handlers = new HandlerList();
//        handlers.setHandlers(new Handler[] { handler,resource_handler});
        server.setHandler(handler);

        
        
        
        
        //server.setHandler(handlerList);
        
       server.setStopAtShutdown(true);
       server.setSendServerVersion(true);
       
        server.start();
        server.join();


    }
}
