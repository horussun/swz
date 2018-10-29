package swz.infra.tools.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import swz.infra.tools.log.Log;


public class DBCon {
	static Logger logger = Log.getLogger(DBCon.class.getName());
	private static Context initCtx;
	private static DataSource ds;
	private static Connection conn = null;

	private static String ConnType = null;
	private static String DbDriver = null;
	private static String DbUrl = null;
	private static String DbUserName = null;
	private static String DbPassword = null;
	private static String dataSourceJNDI = null;

	/**
	 * Default constructor.
	 */
	static {
		if (!initialize()) {
			logger.debug("Db connectin initialization error in get properties file!");
		}
	}

	public static boolean initialize() {
		boolean result = false;
		try {
			Properties props = new Properties();
			props.load(DBCon.class.getResourceAsStream("db.properties"));
			if (props != null) {
				ConnType = props.getProperty("CONNTYPE");
				DbDriver = props.getProperty("DBDRIVER");
				DbUrl = props.getProperty("DBURL");
				DbUserName = props.getProperty("DBUSERNAME");
				DbPassword = props.getProperty("DBPASSWORD");
				dataSourceJNDI = props.getProperty("DATASOURCEJNDI");
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
/**
 * Singlton for local:conn
 *              ds:jndi 
 * @return
 */
	public static Connection getConn() {
		Connection tmpCon = null;
		try {
//			logger.debug("get conn by " + ConnType);
			if (ConnType.equalsIgnoreCase("local")) {
				if (conn == null) {
					Class.forName(DbDriver).newInstance();
					conn = DriverManager.getConnection(DbUrl, DbUserName, DbPassword);
				}
				tmpCon= conn;
			} else if (ConnType.equalsIgnoreCase("jndi")) {
				if(ds==null) {
					initCtx = new InitialContext();
					ds = (DataSource) initCtx.lookup(dataSourceJNDI);	
				}
				tmpCon = ds.getConnection();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmpCon;
	}

	// public static void close(Connection conn) throws SQLException {
	// if (conn != null) {
	// conn.close();
	// }
	// }

	// public static void close(ResultSet rs) throws SQLException {
	// if (rs != null) {
	// rs.close();
	// }
	// }

	// public static void close(Statement stmt) throws SQLException {
	// if (stmt != null) {
	// stmt.close();
	// }
	// }

	// public static void close(PreparedStatement pst) throws SQLException {
	// if (pst != null) {
	// pst.close();
	// }
	// }

	public static void closeQuietly(Connection conn) {
		try {
			if(!ConnType.equalsIgnoreCase("local")) {
				if (conn != null) {
					conn.close();
				}		
			}
		} catch (SQLException e) {
			// quiet
		}
	}

	/**
	 * 
	 * @param rs
	 *            close resultset
	 */
	public static void closeQuietly(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());// quiet
		}
	}

	/**
	 * 
	 * @param rs
	 *            close resultset and related statement and connection
	 */
	public static void closeChainQuietly(ResultSet rs) {
		try {
			if (rs != null) {
			    Statement tmpSt=rs.getStatement();
			    Connection tmpConn=tmpSt.getConnection();
			    rs.close();
			    tmpSt.close();
			    if(!ConnType.equalsIgnoreCase("local")) {
			    	tmpConn.close();			    	
			    }
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());// quiet
		}
	}
	
	public static void closeQuietly(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());// quiet
		}
	}

	public static void closeQuietly(PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());// quiet
		}
	}

	public static void closeQuietly(Connection conn, Statement stmt, ResultSet rs) {

		try {
			closeQuietly(rs);
		} finally {
			try {
				closeQuietly(stmt);
			} finally {
				closeQuietly(conn);
			}
		}

	}
}
