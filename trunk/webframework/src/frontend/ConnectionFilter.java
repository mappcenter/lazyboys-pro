/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.eclipse.jetty.servlets.QoSFilter;

/**
 *
 * @author root
 */
public class ConnectionFilter extends QoSFilter{

    private static ConnectionFilter connectionFilter;

    public static ConnectionFilter getInstance() {
        return connectionFilter;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void setMaxRequests(int value) {
        super.setMaxRequests(value);
    }

    @Override
    public void setWaitMs(long value) {
        super.setWaitMs(value);
    }
}
