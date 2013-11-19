package eu.first.integration.logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import eu.first.integration.db.DBOperations;

public class MessageLogger implements Runnable, Observer {
	private boolean running = false;
	//local stat cache, stores only the last Stats object
	HashMap<String, Stats> stats = new HashMap<String, Stats>();
	DBOperations dboperations;
	HashMap<String, String> config;
	int interval;

	
    // Private constructor prevents instantiation from other classes
    private MessageLogger() {
    	dboperations = dboperations.getInstance();
    	config = ConfigReader.getInstance().getConfig();
    	interval = ConfigReader.getInstance().getInterval();
    }

    /**
    * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
    * or the first access to SingletonHolder.INSTANCE, not before.
    */
    private static class SingletonHolder { 
            public static final MessageLogger INSTANCE = new MessageLogger();
    }

    public static MessageLogger getInstance() {
            return SingletonHolder.INSTANCE;
    }
    
	
	@Override
	public void update(Observable o, Object arg) {
		Stats s;
		
		if (arg instanceof Stats) {
			s = (Stats) arg;
			
			//store in the DB
			dboperations.storeStat(s);
			
			//store in temporary table for printing purposes
			stats.put(s.getName(), s);			
		}		
	}

	
	
	public void printStats() {
		//String[] components = {"s", "r", "c-1", "c-2"};
		//String[] params = {"recv", "recvq", "send", "sendq"};
		//String[] rows = new String[components.length];
		
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		for (String comp : config.keySet()) {
			sb.append("\t" + comp);
		}
		sb.append("\n");
		
		sb.append("recv");
		for (String comp : config.keySet()) {
			sb.append("\t\t" + showRc(stats.get(comp)));
		}
		sb.append("\n");
		
		sb.append("recvq");
		for (String comp : config.keySet()) {
			sb.append("\t\t" + showRqc(stats.get(comp)));
		}
		sb.append("\n");

		sb.append("send");
		for (String comp : config.keySet()) {
			sb.append("\t\t" + showSc(stats.get(comp)));
		}
		sb.append("\n");

		sb.append("sendq");
		for (String comp : config.keySet()) {
			sb.append("\t\t" + showSqc(stats.get(comp)));
		}
		sb.append("\n");

		System.out.println(sb.toString());		
	}
	
	//true - ok
	//false - not connected or expired
	private boolean checkTimestamp(Stats s) {
		int interval = this.interval;
		if (s != null) {
			Date now = new Date(); 
			long i = now.getTime() - s.getTimestamp().getTime();
			if (i > (interval * 3)) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	private String showRc(Stats s) {
		if (checkTimestamp(s) == true) {
			return ""+s.getReceiveCount();
		} else {
			return "-";
		}		
	}
	private String showRqc(Stats s) {
		if (checkTimestamp(s) == true) {
			return ""+s.getReceiveQueueCount();
		} else {
			return "-";
		}		
	}
	private String showSc(Stats s) {
		if (checkTimestamp(s) == true) {
			return ""+s.getSendCount();
		} else {
			return "-";
		}		
	}
	private String showSqc(Stats s) {
		if (checkTimestamp(s) == true) {
			return ""+s.getSendQueueCount();
		} else {
			return "-";
		}		
	}

	public void stopThread() {
		this.running = false;
	}

	/* 
	 * The console printing thread. Runs only if configured with "debug = true" in config.xml
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		//int interval = InitLogging.getInstance().getInterval();
		if (running == false) {
			running = true;
		} 
		while (running == true) {
			try {
				//this is only interval for statistics printing
				Thread.sleep(2000);
				printStats();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
