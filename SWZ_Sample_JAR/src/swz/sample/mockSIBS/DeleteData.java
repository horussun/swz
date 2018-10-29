package swz.sample.mockSIBS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteData {
	

	public static String DB_URL = "jdbc:db2://10.1.82.7:50002/FSESBDB";
	public static String DB_USER = "db2inst2";
	public static String DB_PWD = "passw0rd";

	
	public static String sql = "delete from svcmgmt.T_SVC_TRACE_1 where SVC_TRACE_PK  in (select SVC_TRACE_PK from svcmgmt.T_SVC_TRACE_1 where EVENT_TYPE_CD = '150040' fetch first 4000 rows only)";
		//"delete from svcmgmt.T_SVC_TRACE_1 where SVC_TRACE_PK  in (select SVC_TRACE_PK from svcmgmt.T_SVC_TRACE_1 where EVENT_TYPE_CD = '150020' fetch first 4000 rows only)";
		//"delete from svcmgmt.T_SVC_TRACE_1 where SVC_CORR_ID  in (select SVC_CORR_ID from svcmgmt.T_SVC_TRACE_1 where EVENT_TYPE_CD = '150060' fetch first 2000 rows only)";
		//"delete from svcmgmt.T_SVC_TRACE_1 where SVC_TRACE_PK in (select SVC_TRACE_PK from svcmgmt.T_SVC_TRACE_1 where event_time > '2013-10-29 23:59:59' fetch first 4000 rows only)";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i=0;i <200 ;i++) {
			invoke();
		}

	}
	
	public static void invoke() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block

		} finally {
			try {
				closeConnection(conn, ps, rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
	}
	
	public static Connection getConnection() throws SQLException, IllegalAccessException, InstantiationException,
	ClassNotFoundException {
		Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
		return conn;
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
