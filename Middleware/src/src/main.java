/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.Timer;
import libs.MiddlewareFrontend;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.eclipse.jetty.server.Server;

/**
 *
 * @author chanhlt
 */
public class main {

    public static void main(String[] args) throws InterruptedException, Exception {
        int port = getConfig.getInstance().getPortListen();
        int numthread = getConfig.getInstance().getNumthread();
        TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(port);
        libs.MiddlewareFrontend.Processor processor = new MiddlewareFrontend.Processor(new FrontendHandler());

        THsHaServer.Args options = new THsHaServer.Args(serverTransport);
        options.workerThreads(numthread);
        options.processor(processor);

        TServer server = new THsHaServer(options);
         System.out.println("Start cache data ...");
        (new FrontendHandler()).startCache();
        System.out.println("Cache data complete ...");
        Timer timer = new Timer();
        timer.schedule(new myTask(), 10 * 60 * 1000, 10 * 60 * 1000);
        System.out.println("Server is listening on port " + port + " ...");
        server.serve();
    }
}
