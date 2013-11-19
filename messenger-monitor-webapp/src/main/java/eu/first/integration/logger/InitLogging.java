package eu.first.integration.logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class InitLogging {

	
	public static void main(String[] args) throws InterruptedException, IOException {
		InitLogging c = InitLogging.getInstance();
	}
	

	
    // Private constructor prevents instantiation from other classes
    private InitLogging() {
    	init();
    }

    private static class SingletonHolder { 
            public static final InitLogging INSTANCE = new InitLogging();
    }

    public static InitLogging getInstance() {
            return SingletonHolder.INSTANCE;
    }
    
    //start all monitoring threads (one thread for each component)
    //attach monitoring class for receiving notifications
	public void init() {
		System.out.println("Initializing logging...");
		//String[] names = {"Sender", "Receiver", "Classification-1", "Classification-2"};
		//String[] address = {"tcp://127.0.0.1:4440", "tcp://127.0.0.1:4441", "tcp://127.0.0.1:4442", "tcp://127.0.0.1:4443"};
		
		HashMap<String, String> config = ConfigReader.getInstance().getConfig();
		int interval = ConfigReader.getInstance().getInterval();
		boolean debug = ConfigReader.getInstance().getDebug();
		
		MessageLogger mlog = MessageLogger.getInstance();
		
		//if debug is on, the start console printing threads 
		if (debug == true) {
			Thread log = new Thread(mlog);
			log.start();			
		}
		
		for (String name : config.keySet()) {
			ClientThread client = new ClientThread(new String[]{name}, new String[]{config.get(name)});
			client.setInterval(interval);
			client.addObserver(mlog);
			
			Thread t = new Thread(client);
			t.start();
		}
	}
	


}
