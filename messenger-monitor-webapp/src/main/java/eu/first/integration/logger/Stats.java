package eu.first.integration.logger;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "statistics")
public class Stats {
	@DatabaseField
	String name;
	@DatabaseField
	long sendCount;
	@DatabaseField
	long sendQueueCount;
	@DatabaseField
	long receiveCount;
	@DatabaseField
	long receiveQueueCount;
	@DatabaseField
	Date timestamp;
	//helper field
	long lostOnReceive;
	
	@DatabaseField(generatedId = true)	
	private int id;
	
	public long getLostOnReceive() {
		return lostOnReceive;
	}
	public void setLostOnReceive(long lostOnReceive) {
		this.lostOnReceive = lostOnReceive;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public long getSendCount() {
		return sendCount;
	}
	public void setSendCount(long sendCount) {
		this.sendCount = sendCount;
	}
	public long getSendQueueCount() {
		return sendQueueCount;
	}
	public void setSendQueueCount(long sendQueueCount) {
		this.sendQueueCount = sendQueueCount;
	}
	public long getReceiveCount() {
		return receiveCount;
	}
	public void setReceiveCount(long receiveCount) {
		this.receiveCount = receiveCount;
	}
	public long getReceiveQueueCount() {
		return receiveQueueCount;
	}
	public void setReceiveQueueCount(long receiveQueueCount) {
		this.receiveQueueCount = receiveQueueCount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		
		return "[name: "+name+"\trecv: "+receiveCount+"\trecvq: "+receiveQueueCount+"\tsend: "+sendCount+"\tsendq: "+sendQueueCount+"\ttime: "+timestamp+"\t]\n";
	}
	
}
