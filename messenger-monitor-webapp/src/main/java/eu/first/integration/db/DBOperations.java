package eu.first.integration.db;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.h2.tools.Server;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import eu.first.integration.logger.Stats;

public class DBOperations {
	
	Dao<Stats, String> statsdao;
	Server server;

    private DBOperations() { 
//		Base.open("org.h2.Driver", "jdbc:h2:~/test", "sa", "");
//		System.out.println("DB conn");
    	
    	try {
			server = Server.createTcpServer().start();
			String databaseUrl = "jdbc:h2:tcp://localhost/~/monitoring;AUTO_SERVER=TRUE";
			//String databaseUrl = "jdbc:h2:~/test";
	        // create a connection source to our database
	        ConnectionSource connectionSource =
	            new JdbcConnectionSource(databaseUrl, "sa", "sa");

	        // instantiate the dao
	        statsdao = DaoManager.createDao(connectionSource, Stats.class);
	        
	        TableUtils.createTableIfNotExists(connectionSource, Stats.class);
			
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }


    private static class SingletonHolder { 
            public static final DBOperations INSTANCE = new DBOperations();
    }

    public static DBOperations getInstance() {
            return SingletonHolder.INSTANCE;
    }
    
    
    public void storeStat(Stats s) {
        
        // persist the account object to the database
        try {
			statsdao.create(s);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("persistence problem");
		}
    }
    
    public List<Stats> retrieveAllMappings() {
		try {
			return statsdao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("retrieval problem");
			return null;
		}
    }
    

    public List<Stats> getStatsByNameNDays(String name, int days) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -30);
			List<Stats> results = statsdao.queryBuilder().orderBy("timestamp", true).where().eq("name", name).and().ge("timestamp", cal.getTime()).query();
			
			return results;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("retrieval problem");
			return null;
		}
    }
    public List<Stats> getStatsByNameNDaysAggregatedHourly(String name, int days) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -30);
			List<Stats> results = statsdao.queryBuilder().orderBy("timestamp", true).where().eq("name", name).and().ge("timestamp", cal.getTime()).query();
			
			return results;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("retrieval problem");
			return null;
		}
    }
}
