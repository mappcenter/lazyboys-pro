/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import libs.MiddlewareFrontend;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;

/**
 *
 * @author chanhlt
 */
public class main {

    public static void main(String[] args) throws InterruptedException, Exception {

//        Server server=new Server(8080);
//        server.setHandler(new hellohandler());
//        server.start();
//        server.join();

       

        int port = getConfig.getInstance().getPortListen();

        TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
        libs.MiddlewareFrontend.Processor processor =
                new MiddlewareFrontend.Processor(new FrontendHandler());

        TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).
                processor(processor));
        System.out.println("Starting server on port " + port + " ...");
        server.serve();

    }
}
