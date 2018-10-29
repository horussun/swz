package swz.infra.tools.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import swz.infra.tools.string.DERes;

public class DataUtil {
	
	public static ResultSet sqlQuery(Connection conn, PreparedStatement ps, String sql) throws SQLException {
		ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public static Connection getConnection(String dburl, String user, String pwd) throws SQLException, IllegalAccessException, InstantiationException,
			ClassNotFoundException {
		Class.forName(DERes.s("driverClassName")).newInstance();
		Connection conn = DriverManager.getConnection(dburl, user, pwd);
		return conn;
	}
	
	public static Connection getConnection() throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
		Class.forName(DERes.s("driverClassName")).newInstance();
		Connection conn = DriverManager.getConnection(DERes.s("dburl"), DERes.s("username"), DERes.s("password"));
		return conn;
	}

	public static void closeConnection(Connection conn) throws SQLException {
		if (conn != null)
			conn.close();
	}

	public static void closeConnection(Connection conn, PreparedStatement ps) throws SQLException {
		if (ps != null)
			ps.close();
		if (conn != null)
			conn.close();
	}

	public static void closeConnection(PreparedStatement ps, ResultSet rs) throws SQLException {
		if (rs != null)
			rs.close();
		if (ps != null)
			ps.close();
	}

	public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
		if (rs != null)
			rs.close();
		if (ps != null)
			ps.close();
		if (conn != null)
			conn.close();
	}
}
