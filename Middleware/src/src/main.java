/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import com.vng.jcore.common.LogUtil;
import java.util.Timer;
import libs.MiddlewareFrontend;
import org.apache.log4j.Logger;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 *
 * @author chanhlt
 */
public class main {

    public static Logger logger_ = Logger.getLogger(main.class);

    public static void main(String[] args) throws InterruptedException, Exception {
        LogUtil.init();
        int port = getConfig.getInstance().getPortListen();
        int numthread = getConfig.getInstance().getNumthread();
        TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(port);
        libs.MiddlewareFrontend.Processor processor = new MiddlewareFrontend.Processor(new FrontendHandler());

        THsHaServer.Args options = new THsHaServer.Args(serverTransport);
        options.workerThreads(numthread);
        options.processor(processor);


        TServer server = new THsHaServer(options);
        logger_.info("Start cache data ...");
        
        (new FrontendHandler()).startCache();
        logger_.info("Cache data complete ...");
        
        Timer timer = new Timer();
        int expireTime = getConfig.getInstance().expireTime();
        timer.schedule(new myTask(), expireTime, expireTime);
        
        logger_.info("Server is listening on port " + port + " ...");
        server.serve();
    }
}
