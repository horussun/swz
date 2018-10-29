package swz.infra.tools.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import swz.infra.tools.data.DataUtil;
import swz.infra.tools.db.pool.DBConnectionManager;

public class DBHelper {
	
	private static DBConnectionManager connectionManager;
	
	public static void init() {
		connectionManager = DBConnectionManager.getInstance();
	}
	
	public static Connection getConnection() {
		return connectionManager.getConnection("esbdb");
	}
	
	public static void testPool() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("insert into SVCMGMT.TEST values ('3')");
			ps.execute();
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DataUtil.closeConnection(conn, ps);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
