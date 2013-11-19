package eu.first.integration.datasource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.first.integration.db.DBOperations;
import eu.first.integration.logger.ConfigReader;
import eu.first.integration.logger.InitLogging;
import eu.first.integration.logger.Stats;

public class StatTimeSeriesServlet extends HttpServlet {
	
	DBOperations dbo = DBOperations.getInstance();
	//we initialize InitLogging to start all monitoring threads (unless they are already started)
	InitLogging init = InitLogging.getInstance();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String mimeType = getServletContext().getMimeType("test.js");
		response.setContentType(mimeType);
		PrintWriter out = response.getWriter();
		
		int days = 8;
		try {
			if (request.getParameter("days") != null) {
				days = Integer.parseInt(request.getParameter("days"));
			}
			
		} catch (NumberFormatException e) {
			System.out.println(e);
		}
		
		HashMap<String, String> config = ConfigReader.getInstance().getConfig();
		HashMap<String, List<Stats>> deltaSeries = new HashMap<String, List<Stats>>();
		
		//get names of components 
		for (String name : config.keySet()) {
			List<Stats> r = dbo.getStatsByNameNDays(name, days);
			// we take 1 out of 10
			List<Stats> deltas = new ArrayList<Stats>();
			for (int i=0; i<r.size(); i++) {
				if (i>=1) {
					Stats d = new Stats();
					d.setName(name);
					d.setSendCount(r.get(i).getSendCount() - r.get(i-1).getSendCount());
					d.setSendQueueCount(r.get(i).getSendQueueCount() - r.get(i-1).getSendQueueCount());
					d.setReceiveCount(r.get(i).getReceiveCount() - r.get(i-1).getReceiveCount());
					d.setReceiveQueueCount(r.get(i).getReceiveQueueCount() - r.get(i-1).getReceiveQueueCount());
					d.setTimestamp(r.get(i).getTimestamp());
					
					
					deltas.add(d);
				}
				
			}
			deltaSeries.put(name, deltas);
			
		}
	
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
		if (config.keySet().size() != 0) {
			for (String name : config.keySet()) {
				sb.append("\"" + name + "\": [");
				
				for (Stats  s : deltaSeries.get(name)) {
					sb.append("["+s.getTimestamp().getTime()+", "+s.getReceiveCount()+"],\n");
					
				}
				sb.deleteCharAt(sb.length()-2);
				
				sb.append("],\n");
			
			}
			sb.deleteCharAt(sb.length()-2);								
		}
		sb.append("}");
		
		out.println(sb.toString());
		
		//request.setAttribute("company", company);
//		RequestDispatcher dispatcher =
//			      request.getRequestDispatcher("company-v3.jsp");
//		dispatcher.forward(request, response);	
	}
}
