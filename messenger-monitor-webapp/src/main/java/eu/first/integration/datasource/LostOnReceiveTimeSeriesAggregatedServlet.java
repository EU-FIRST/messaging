package eu.first.integration.datasource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.first.integration.db.DBOperations;
import eu.first.integration.logger.ConfigReader;
import eu.first.integration.logger.InitLogging;
import eu.first.integration.logger.Stats;

public class LostOnReceiveTimeSeriesAggregatedServlet extends HttpServlet {
	
	DBOperations dbo = DBOperations.getInstance();
	//we initialize InitLogging to start all monitoring threads (unless they are already started)
	InitLogging i = InitLogging.getInstance();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String mimeType = getServletContext().getMimeType("test.js");
		response.setContentType(mimeType);
		PrintWriter out = response.getWriter();
		
		int days = 15;
		try {
			if (request.getParameter("days") != null) {
				days = Integer.parseInt(request.getParameter("days"));
			}
			
		} catch (NumberFormatException e) {
			System.out.println(e);
		}
		
		HashMap<String, String> config = ConfigReader.getInstance().getConfig();
		HashMap<String, List<Stats>> lostsSeries = new HashMap<String, List<Stats>>();
		
		for (String name : config.keySet()) {
			ArrayList<Stats> filtered = filterStats(dbo.getStatsByNameNDays(name, days));
			
			List<Stats> losts = new ArrayList<Stats>();
			//Stats prev;
			for (int i=0; i<filtered.size(); i++) {
				if (i>=1) {
					Stats d = new Stats();
					d.setName(name);
					d.setTimestamp(filtered.get(i).getTimestamp());
					//set delta
					d.setLostOnReceive((filtered.get(i).getReceiveCount() - filtered.get(i).getReceiveQueueCount()) - (filtered.get(i-1).getReceiveCount() - filtered.get(i-1).getReceiveQueueCount()));
					
					losts.add(d);
				}
			}
			
			lostsSeries.put(name, losts);
			
		}
	
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
		if (config.keySet().size() != 0) {
			for (String name : config.keySet()) {
				sb.append("\"" + name + "\": [");
				
				for (Stats  s : lostsSeries.get(name)) {
					sb.append("["+s.getTimestamp().getTime()+", "+s.getLostOnReceive()+"],\n");
					
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
