package eu.first.integration.logger;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import eu.first.integration.db.DBOperations;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test t =new Test();
		t.test2();
			
	}

	
	
	Test() {
		
	}
	
	public void test() {
		 try {
			XMLConfiguration configuration = new XMLConfiguration("src/main/resources/config.xml");
			String name = configuration.getString("test");
			System.out.println(name);
			
			List<HierarchicalConfiguration> components = configuration.configurationsAt("component");
			for(HierarchicalConfiguration c : components) {
			    System.out.println(c.getString("name"));
			    System.out.println(c.getString("address"));
			}
			
			
			
			
			
			
			
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	
	public void test2() {
		DBOperations dbp = DBOperations.getInstance();
		System.out.println(dbp.getStatsByNameNDays("Receiver", 15));
		
		
	}
}
