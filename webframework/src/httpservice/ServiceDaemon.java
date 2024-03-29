package httpservice;

import com.vng.jcore.common.LogUtil;
import java.io.File;
import org.apache.log4j.Logger;

public class ServiceDaemon {

    private static Logger logger_ = Logger.getLogger(ServiceDaemon.class);

    public static void main(String[] args) throws Exception {
        //Test test = new Test();
        LogUtil.init();
        WebServer webserver = new WebServer();

        String pidFile = System.getProperty("pidfile");
        try {
            if (pidFile != null) {
                new File(pidFile).deleteOnExit();
            }
           
            webserver.start();

        } catch (Throwable e) {
            String msg = "Exception encountered during startup.";
            logger_.error(msg, e);

            // try to warn user on stdout too, if we haven't already detached
            System.out.println(msg);
            logger_.error("Uncaught exception: " + e.getMessage());

            System.exit(3);
        }
    }
}
