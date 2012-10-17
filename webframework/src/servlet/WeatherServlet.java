/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

/**
 *
 * @author root
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author veerasundar.com/blog
 *
 */
public class WeatherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WeatherServlet() {
		super();
	}

    @Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

    @Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String city = request.getParameter("cityName");
		String report = getWeather(city);
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println("" + report + "");
		out.flush();
		out.close();
	}

	private String getWeather(String city) {
		String report;

		if (city.toLowerCase().equals("trivandrum"))
			report = "Currently it is not raining in Trivandrum. Average temperature is 20";
		else if (city.toLowerCase().equals("chennai"))
			report = "It's a rainy season in Chennai now. Better get a umbrella before going out.";
		else if (city.toLowerCase().equals("bangalore"))
			report = "It's mostly cloudy in Bangalore. Good weather for a cricket match.";
		else
			report = "The City you have entered is not present in our system. May be it has been destroyed "
					+ "in last World War or not yet built by the mankind";
		return report;
	}

}