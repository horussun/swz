package swz.infra.tools.db.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import org.apache.log4j.Logger;

public class DBConnectionPool {

	private static final Logger logger = Logger.getLogger(DBConnectionPool.class);

	private int checkedOut; // 当前连接数
	private Vector<Connection> freeConnections = new Vector<Connection>(); // 保存所有可用连接
	private int maxConn; // 此连接池允许建立的最大连接数
	private String name; // 连接池名字
	private String password; // 密码或null
	private String URL; // 数据库的JDBC URL
	private String user; // 数据库账号或null

	public DBConnectionPool(String name, String URL, String user, String password, int maxConn) {
		this.name = name;
		this.URL = URL;
		this.user = user;
		this.password = password;
		this.maxConn = maxConn;
	}

	/**
	 * 将不再使用的连接返回给连接池
	 * 
	 * @param con
	 *            客户程序释放的连接
	 */
	public synchronized void freeConnection(Connection con) {
		// 将指定连接加入到向量末尾
		freeConnections.addElement(con);
		checkedOut--;
		notifyAll(); // 删除等待队列中的所有线程
	}

	/**
	 * 从连接池获得一个可用连接.如果没有空闲的连接且当前连接数小于最大连接 数限制,则创建新连接.如原来登记为可用的连接不再有效,则从向量删除之,
	 * 然后递归调用自己以尝试新的可用连接.
	 */
	public synchronized Connection getConnection() {
		Connection con = null;
		if (freeConnections.size() > 0) {
			// 获取向量中第一个可用连接
			con = (Connection) freeConnections.firstElement();
			freeConnections.removeElementAt(0);
			try {
				if (con.isClosed()) {
					logger.info("从连接池" + name + "删除一个无效连接");
					// 递归调用自己,尝试再次获取可用连接
					con = getConnection();
				}
			} catch (SQLException e) {
				logger.info("从连接池" + name + "删除一个无效连接");
				// 递归调用自己,尝试再次获取可用连接
				con = getConnection();
			}
		} else if (maxConn == 0 || checkedOut < maxConn) {
			con = newConnection();
		}
		if (con != null) {
			checkedOut++;
		}
		return con;
	}

	/**
	 * 从连接池获取可用连接.可以指定客户程序能够等待的最长时间 参见前一个getConnection()方法.
	 * 
	 * @param timeout
	 *            以毫秒计的等待时间限制
	 */
	public synchronized Connection getConnection(long timeout) {
		long startTime = new Date().getTime();
		Connection con;
		while ((con = getConnection()) == null) {
			try {
				wait(timeout);
			} catch (InterruptedException e) {
			}
			if ((new Date().getTime() - startTime) >= timeout) {
				// wait()返回的原因是超时
				return null;
			}
		}
		return con;
	}

	/**
	 * 关闭所有连接
	 */
	public synchronized void release() {
		Enumeration<Connection> allConnections = freeConnections.elements();
		while (allConnections.hasMoreElements()) {
			Connection con = (Connection) allConnections.nextElement();
			try {
				con.close();
				logger.info("关闭连接池" + name + "中的一个连接");
			} catch (SQLException e) {
				logger.info("无法关闭连接池" + name + "中的连接", e);
			}
		}
		freeConnections.removeAllElements();
	}

	/**
	 * 创建新的连接
	 */
	private Connection newConnection() {
		Connection con = null;
		try {
			if (user == null) {
				con = DriverManager.getConnection(URL);
			} else {
				con = DriverManager.getConnection(URL, user, password);
			}
			logger.info("连接池" + name + "创建一个新的连接");
		} catch (SQLException e) {
			logger.info("无法创建下列URL的连接: " + URL, e);
			return null;
		}
		return con;
	}

}
