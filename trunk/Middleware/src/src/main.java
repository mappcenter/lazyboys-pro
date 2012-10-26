/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import libs.MiddlewareFrontend;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 *
 * @author chanhlt
 */
public class main {

    public static void main(String[] args) throws InterruptedException, Exception {
        int port = getConfig.getInstance().getPortListen();

        TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(port);
        libs.MiddlewareFrontend.Processor processor = new MiddlewareFrontend.Processor(new FrontendHandler());

        THsHaServer.Args options = new THsHaServer.Args(serverTransport);
        options.workerThreads(300);
        options.processor(processor);

        TServer server = new THsHaServer(options);
        System.out.println("Starting server on port " + port + " ...");
        server.serve();
    }
}
