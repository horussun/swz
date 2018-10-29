package swz.sample.mockSIBS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestData extends TestCase {
	private static final Logger logger = Logger.getLogger(TestData.class.getName());
	
	public static final String ESB_URL = "http://10.1.81.24:8082/services/";
	public static final String DB_URL = "jdbc:db2://10.1.82.7:50002/FSESBDB";
	public static final String DB_USER = "db2inst2";
	public static final String DB_PWD = "passw0rd";
	public static int ROW_S = 697187054;
	public static int ROW_E = 699988798;
	public static int ROW_C = 1000;
	public static int start = 0;
	public static int end = 0;
	
	@Test
	public void testAccessESB() {
		for (int i = start;i < end;i++) {
			callHttpPost(i*ROW_C+ROW_S);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		start = Integer.parseInt(args[0]==null?"0":args[0]);
		end = Integer.parseInt(args[0]==null?"0":args[1]);
		org.junit.runner.JUnitCore.runClasses(TestData.class);
	}
	
	public static ArrayList<OptBean> invoke(int s) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OptBean> optList = new ArrayList<OptBean>();
		try {
			//"select SVC_MSG_CHAR,OPT_ID from SVCMGMT.T_SVC_TRACE_2 where opt_id='1010020101'";
			//select SVC_TRACE_PK,SVC_MSG_CHAR,OPT_ID from SVCMGMT.T_SVC_TRACE_2 where SVC_TRACE_PK between 690870744 and 690871744
			String sql = "select SVC_TRACE_PK,SVC_MSG_CHAR,OPT_ID from SVCMGMT.T_SVC_TRACE_1 where SVC_TRACE_PK between "+s+" and "+(s+ROW_C);
			logger.info(sql);
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			OptBean opt = null;
			while (rs.next()) {
				opt = new OptBean();
				opt.setUrl(ESB_URL+rs.getString("OPT_ID"));
				opt.setReqt(rs.getString("SVC_MSG_CHAR"));
				optList.add(opt);
			}
			
			//HttpVistorThread vistor = new HttpVistorThread();
			//vistor.setOptList(optList);
			//vistor.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		} finally {
			try {
				closeConnection(conn, ps, rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
		return optList;
	}
	
	public static void callHttpPost(int s) {
		
		ArrayList<OptBean> optList = invoke(s);
		for (OptBean bean: optList) {
			try {
				HttpVistorThread vistor = new HttpVistorThread();
				vistor.setReqt(bean.getReqt());
				vistor.setUrl(bean.getUrl());
				vistor.start();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
			}
		}
		logger.info("end");
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
