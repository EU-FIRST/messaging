package eu.first.integration.logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class ClientThread extends Observable  implements Runnable {
	String[] labels = {"Recv", "Recv Queue", "Send", "Send Queue"};
	String[] names;
	
	String request = "stats";
	
	ArrayList<Socket> monitorSockets = new ArrayList<ZMQ.Socket>();
	HashMap<String, Socket> socketList = new HashMap<String, ZMQ.Socket>();
	
	Context context;
	private boolean running = true;
	int interval = 2000;
	
	ClientThread(String[] names, String[] addr) {
		context = ZMQ.context(1);
		this.names = names;
		
		for (int i=0; i<addr.length; i++) {
			Socket monitorSocket = context.socket(ZMQ.REQ);
			monitorSocket.connect(addr[i]);
			monitorSockets.add(monitorSocket);
			socketList.put(this.names[i], monitorSocket);
		}
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	public void stopThread() {
		this.running = false;			
	}
	
	public void run() {
		while(this.running == true) {
			try {
				
				for (String sname : socketList.keySet()) {
					Socket socket = socketList.get(sname);
					socket.send(request.getBytes(), 0);
					byte[] recv = socket.recv(0);
					String response = new String(recv);
					String[] stats = response.split(" ");
					Stats stat = new Stats();
					stat.setName(sname);
					stat.setReceiveCount(Long.parseLong(stats[0]));
					stat.setReceiveQueueCount(Long.parseLong(stats[1]));
					stat.setSendCount(Long.parseLong(stats[2]));
					stat.setSendQueueCount(Long.parseLong(stats[3]));
					stat.setTimestamp(new Date());
					setChanged();
	                notifyObservers(stat);
				}
				
				Thread.sleep(this.interval);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//nothing more to do... close all sockets
		for (Socket s : monitorSockets) {
			s.close();
		}

		context.term();

	}
}
