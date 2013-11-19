package eu.first.integration.monitoring;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.first.integration.logger.InitLogging;

public class StatisticsServlet extends HttpServlet {
	InitLogging i;
	public StatisticsServlet() {

		InitLogging i = InitLogging.getInstance();
		
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		//request.setAttribute("company", company);
		RequestDispatcher dispatcher =
			      request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);	
	}
}
