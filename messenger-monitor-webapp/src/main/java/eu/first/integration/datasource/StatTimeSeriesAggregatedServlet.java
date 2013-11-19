package eu.first.integration.datasource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
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

public class StatTimeSeriesAggregatedServlet extends HttpServlet {
	
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
			
			ArrayList<Stats> filtered = filterStats(dbo.getStatsByNameNDays(name, 15));
			
			// we take one stat every 1 hour
			List<Stats> deltas = new ArrayList<Stats>();
			for (int i=0; i<filtered.size(); i++) {
				if (i>=1) {
					Stats d = new Stats();
					d.setName(name);
					d.setSendCount(filtered.get(i).getSendCount() - filtered.get(i-1).getSendCount());
					d.setSendQueueCount(filtered.get(i).getSendQueueCount() - filtered.get(i-1).getSendQueueCount());
					d.setReceiveCount(filtered.get(i).getReceiveCount() - filtered.get(i-1).getReceiveCount());
					d.setReceiveQueueCount(filtered.get(i).getReceiveQueueCount() - filtered.get(i-1).getReceiveQueueCount());
					d.setTimestamp(filtered.get(i).getTimestamp());
					
					
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
	
	private ArrayList<Stats> filterStats(List<Stats> r) {
		ArrayList<Stats> filtered = new ArrayList<Stats>();
		
		Calendar cal = Calendar.getInstance();
		if (r.isEmpty() == false) {
			cal.setTime(r.get(0).getTimestamp());
			
			for (Stats s : r) {
				if (s.getTimestamp().after(cal.getTime())) {
					filtered.add(s);
					cal.add(Calendar.HOUR, 1);
				}
			}			
		}
		
		return filtered;
	}
}
