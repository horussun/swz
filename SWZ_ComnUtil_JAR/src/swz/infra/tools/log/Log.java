package swz.infra.tools.log;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {
	// static String prop_file = "log4j.properties";
	static String prop_file = "log4j.properties";
	static public Logger root = null;
	static private HashMap hmLogger = new HashMap();

	static {
		init();
	}

	static public synchronized void init() {
		try {
			PropertyConfigurator.configureAndWatch(prop_file);
			root = Logger.getRootLogger();
//			root = null;
		} catch (Exception e) {
			System.out.println("Erron in log init:" + e.getMessage());
		}
	}
    
	static public Logger getLogger(String logName) {
		return Logger.getLogger(logName);
	}
	
	
	static public void debug(String info) {
		root.debug(info);
	}

	static public void info(String info) {
		root.info(info);
	}
	static public void error(String info) {
		root.error(info);
	}
}
