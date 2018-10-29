package swz.infra.tools.db.pool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

public class DBConnectionManager {

	private static final Logger logger = Logger.getLogger(DBConnectionManager.class);

	private static DBConnectionManager dbInstance;
	private static int dbClients;
	private Vector<Driver> drivers = new Vector<Driver>();
	private Hashtable<String, DBConnectionPool> pools = new Hashtable<String, DBConnectionPool>();

	/**
	 * 返回唯一实例.如果是第一次调用此方法,则创建实例
	 * 
	 * @return DBConnectionManager 唯一实例
	 */
	public synchronized static DBConnectionManager getInstance() {
		if (dbInstance == null) {
			dbInstance = new DBConnectionManager();
		}
		dbClients++;
		return dbInstance;
	}

	private DBConnectionManager() {
		init();
	}
	
	private void init() {
		try {
			ResourceBundle rsBundle = ResourceBundle.getBundle("db");
			loadDreives(rsBundle);
			createPools(rsBundle);
		} catch (Exception e) {
			logger.error("db connection pool initialize failure", e);
		}	
	}
	
	private void loadDreives(ResourceBundle rsBundle) {
		String driverClasses = rsBundle.getString("drivers");
		StringTokenizer st = new StringTokenizer(driverClasses);
		while (st.hasMoreElements()) {
			String driverClassName = st.nextToken().trim();
			try {
				Driver driver = (Driver) Class.forName(driverClassName).newInstance();
				DriverManager.registerDriver(driver);
				drivers.addElement(driver);
			} catch (Exception e) {
				logger.error("can't register driver "+driverClassName, e);
			}
		}
	}
	
	private void createPools(ResourceBundle rsBundle) {
		Enumeration<?> rsbNames = rsBundle.getKeys();
		while(rsbNames.hasMoreElements()) {
			String name = (String)rsbNames.nextElement();
			if (name.endsWith(".dburl")) {
				String poolName = name.substring(0, name.lastIndexOf("."));
				String url = rsBundle.getString(poolName + ".dburl");
				String user = rsBundle.getString(poolName + ".username");
				String password = rsBundle.getString(poolName + ".password");
				int maxconn = Integer.valueOf(rsBundle.getString(poolName + ".maxconn")).intValue();
				DBConnectionPool pool = new DBConnectionPool(poolName, url, user, password, maxconn);
				pools.put(poolName, pool);
			}
		}
	}

	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			pool.freeConnection(con);
		}
	}

	public Connection getConnection(String name) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			return pool.getConnection();
		}
		return null;
	}

	/**
	 * 获得一个可用连接.若没有可用连接,且已有连接数小于最大连接数限制, 则创建并返回新连接.否则,在指定的时间内等待其它线程释放连接.
	 * 
	 * @param name
	 *            连接池名字
	 * @param time
	 *            以毫秒计的等待时间
	 * @return Connection 可用连接或null
	 */
	public Connection getConnection(String name, long time) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			return pool.getConnection(time);
		}
		return null;
	}

	/**
	 * 关闭所有连接,撤销驱动程序的注册
	 */
	public synchronized void release() {
		// 等待直到最后一个客户程序调用
		if (--dbClients != 0) {
			return;
		}
		Enumeration<DBConnectionPool> allPools = pools.elements();
		while (allPools.hasMoreElements()) {
			DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
			pool.release();
		}
		Enumeration<Driver> allDrivers = drivers.elements();
		while (allDrivers.hasMoreElements()) {
			Driver driver = (Driver) allDrivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				logger.info("撤销JDBC驱动程序 " + driver.getClass().getName() + "的注册");
			} catch (SQLException e) {
				logger.info("无法撤销下列JDBC驱动程序的注册: " + driver.getClass().getName(), e);
			}
		}
	}

}
