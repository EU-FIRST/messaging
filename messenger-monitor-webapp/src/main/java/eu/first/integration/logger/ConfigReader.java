package eu.first.integration.logger;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

public class ConfigReader {

	// Private constructor prevents instantiation from other classes
	private ConfigReader() {
		readConfig();
	}

	private static class SingletonHolder {
		public static final ConfigReader INSTANCE = new ConfigReader();
	}

	public static ConfigReader getInstance() {
		return SingletonHolder.INSTANCE;
	}

	boolean debug = false;
	int interval = 2000;
    HashMap<String, String> config = new HashMap<String, String>();

	private void readConfig() {
		System.out.println("Reading configuration...");
		try {
			XMLConfiguration configuration = new XMLConfiguration("/config.xml");
			interval = configuration.getInt("interval");
			debug = configuration.getBoolean("debug");
			System.out.println("Threads Interval: " + interval);
			System.out.println("Debug: " + debug);

			List<HierarchicalConfiguration> components = configuration.configurationsAt("component");
			for (HierarchicalConfiguration c : components) {
				config.put(c.getString("name"), c.getString("address"));
				// names.add(c.getString("name"));
				// addresses.add(c.getString("address"));
			}

		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public HashMap<String, String> getConfig() {
		return config;
	}

	public int getInterval() {
		return interval;
	}

	public boolean getDebug() {
		return debug;
	}
}
