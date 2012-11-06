/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class MyTask extends TimerTask{
    static int a=1;

    MySwapLocalCache mySwapCache=new MySwapLocalCache();
    MyLocalCache myLocalCache=new MyLocalCache();
    @Override
    public void run() {
        mySwapCache.clearAllCaching();        
        try {
            MySwapLocalCache.newItems=MyLocalCache.newItems;
            mySwapCache.startMyLocalCache();
        } catch (TException ex) {
            Logger.getLogger(MyTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        myLocalCache.SwapData();
    }
    
}
