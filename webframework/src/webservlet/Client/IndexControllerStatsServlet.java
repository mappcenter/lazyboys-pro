package webservlet.Client;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class IndexControllerStatsServlet extends HttpServlet {

	private static final Logger log = LoggerFactory.getLogger(IndexControllerStatsServlet.class);

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
		this.doGet(req, resp);
	}

	private void out(String content, HttpServletResponse resp){
		try {
			//resp.addHeader("Content-Type", "text/javascript");
			resp.addHeader("Content-Type", "text/html");
			PrintWriter out = resp.getWriter();
			out.println(content);
			out.close();
		}
		catch(Exception e) {

		}
	}

}
