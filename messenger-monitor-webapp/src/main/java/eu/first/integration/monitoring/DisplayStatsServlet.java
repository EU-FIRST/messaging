package eu.first.integration.monitoring;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayStatsServlet extends HttpServlet {

	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		//request.setAttribute("company", company);
		RequestDispatcher dispatcher =
			      request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);	
	}
}
