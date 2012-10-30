/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import com.vng.jcore.common.Config;
import java.util.concurrent.ArrayBlockingQueue;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author root
 */
class Connection {

    private TTransport transport;
    private MiddlewareFrontend.Client client;
    private TFramedTransport framedTransport;
    private TProtocol protocol;

    public Connection(String host, int port) {
        transport = new TSocket(host, port);
        framedTransport = new TFramedTransport(transport);
        protocol = new TBinaryProtocol(framedTransport);
        client = new MiddlewareFrontend.Client(protocol);
    }

    public boolean isOpen() {
        if (transport.isOpen()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean open() {
        try {
            transport.open();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean close() {
        try {
            transport.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return the client
     */
    public synchronized MiddlewareFrontend.Client getClient() {
        return client;
    }
}

public class ConnectionPool {

    private static ArrayBlockingQueue<Connection> queue;
    private static ConnectionPool connectionPool = null;
    int count = 0;

    private ConnectionPool() {
        int size_ = getConfig.getInstance().getNumberConnection();
        queue = new ArrayBlockingQueue<Connection>(size_);
        String host = getConfig.getInstance().getHost();
        int port = getConfig.getInstance().getPort();
        for (int i = 0; i < size_; i++) {
            try {
                queue.put(createConnection(host, port));
            } catch (Exception e) {
                System.out.printf(e.getMessage());
            }
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public Connection getConnection() throws InterruptedException {
        Connection con = queue.take();
        if (con.isOpen() == false) {
            con.open();
        }
        return (con);
    }

    public void releaseConnection(Connection con) throws InterruptedException {
        queue.put(con);
    }

    private Connection createConnection(String host, int port) {
        Connection con = new Connection(host, port);
        con.open();
        return con;
    }
}
