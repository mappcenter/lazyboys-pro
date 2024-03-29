/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

/**
 *
 * @author root
 */
public class MyCache {

    private static final String NAMESPACE = "chanhdeptrai";
    private static MyCache instance = null;
    private static MemcachedClient[] m = null;
    private int maxthread;

    /**
     *
     * @throws IOException
     */
    private MyCache() throws IOException {

        Properties pro = new Properties();
        pro.load(new FileInputStream("src/conf/config.ini"));
        maxthread = Integer.parseInt(pro.getProperty("maxthread"));
        String host_memcache = pro.getProperty("host_memcache");
        int port_memcache = Integer.parseInt(pro.getProperty("port_memcache"));
        try {
            m = new MemcachedClient[maxthread];//khoi tao so luong doi tuong MemcachedClient
            for (int i = 0; i < maxthread; i++) {
                MemcachedClient c = new MemcachedClient(new BinaryConnectionFactory(),
                        AddrUtil.getAddresses(host_memcache + ":" + port_memcache));
                m[i] = c;
            }
        } catch (Exception e) {
        }
    }

    public static synchronized MyCache getInstance() throws IOException {
       
        if (instance == null) {
            System.out.println("Creating a new instance");
            instance = new MyCache();
        }
        return instance;
    }

    public void set(String key, int ttl, final Object o) {
        getCache().set(NAMESPACE + key, ttl, o);
    }

    public Object get(String key) throws InterruptedException, ExecutionException {
//        Object o = getCache().get(NAMESPACE + key);
//        if (o == null) {
//            System.out.println("Cache miss from key: " + key);
//        } else {
//            System.out.println("Cache hit from key: " + key);
//        }
//        return o;
        Object o = null;
        Future<Object> f = getCache().asyncGet(NAMESPACE + key);
        try {
            o = f.get(50, TimeUnit.MILLISECONDS); 
        } catch (TimeoutException e) {
            // Since we don't need this, go ahead and cancel the operation.  This
            // is not strictly necessary, but it'll save some work on the server.
            System.out.println("Waiting get memcache to Late!!! " + key);
            f.cancel(false);
            // Do other timeout related stuff
        }
        //Object o = getCache().get(NAMESPACE + key);
        if (o == null) {
            System.out.println("Not in memcache key: " + key);
        } else {
            System.out.println("Get from memcache key: " + key);
        }
        return o;
    }

    public Object delete(String key) {
        return getCache().delete(NAMESPACE + key);
    }

    public MemcachedClient getCache() {

        MemcachedClient c = null;
        try {
            int i = (int) (Math.random() % maxthread);
            c = m[i];
        } catch (Exception e) {
        }
        
        return c;

    }
    public OperationFuture<Boolean> CleanCache(){
        return getCache().flush();
    }
}
