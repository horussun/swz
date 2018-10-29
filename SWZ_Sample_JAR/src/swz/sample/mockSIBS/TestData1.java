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

public class TestData1 extends TestCase {
	private static final Logger logger = Logger.getLogger(TestData1.class.getName());
	
	public static final String ESB_URL = "http://127.0.0.1:8082/services/";
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
		ArrayList<String> al = new ArrayList<String>();
		al.add("1511100103");
		
		al.add("1511100111");
		al.add("1511100114");
		al.add("1511100201");
		al.add("1511101001");
		al.add("1512100104");
		al.add("1512100107");
		al.add("1512100111");
		al.add("1512100201");
		al.add("1512100301");
		al.add("1512100401");
		al.add("1512100503");
		al.add("1512102301");
		al.add("1512110101");
		al.add("1512110201");
		al.add("1512110202");
		al.add("1512110301");
		al.add("1521100602");
		al.add("1521100603");
		al.add("1521100801");
		al.add("1521100901");
		al.add("1522000201");
		al.add("1533100102");
		al.add("1533100103");
		al.add("1541100601");
		al.add("1541100901");
		al.add("1541101001");
		al.add("1541101101");
		al.add("1541101201");
		al.add("1541101601");
		al.add("1541101901");
		al.add("1541103101");
		al.add("1541103102");
		al.add("1542100302");
		al.add("1542100401");
		al.add("1542100701");
		al.add("1542101401");
		al.add("1542101501");
		al.add("1542101701");
		al.add("1542110801");
		al.add("1542441003");
		al.add("1542441005");
		al.add("1542500102");
		al.add("1542500103");
		al.add("1543100301");
		al.add("1543100401");
		al.add("1545100101");
		al.add("1545100204");
		al.add("1545100304");
		al.add("1545100401");
		al.add("1545100501");
		al.add("1545100502");
		al.add("1545100601");
		al.add("1545100701");
		al.add("1545100901");
		al.add("1545100902");
		al.add("1545101001");
		al.add("1551000101");
		al.add("1551000102");
		al.add("2001000101");
		al.add("2001000201");
		al.add("2001000203");
		al.add("2001000301");
		al.add("2001000401");
		al.add("2002001002");
		al.add("2007002008");
		al.add("2008002003");
		al.add("1611000302");
		al.add("1620000103");
		al.add("1010020102");
		al.add("1505000105");
		al.add("1505000110");
		al.add("1505000111");
		al.add("1505000112");
		
		for (String optID: al) {
			callHttpPost(optID);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//start = Integer.parseInt(args[0]==null?"0":args[0]);
		//end = Integer.parseInt(args[0]==null?"0":args[1]);
		org.junit.runner.JUnitCore.runClasses(TestData1.class);
	}
	
	public static ArrayList<OptBean> invoke(String optID) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OptBean> optList = new ArrayList<OptBean>();
		try {
			//"select SVC_MSG_CHAR,OPT_ID from SVCMGMT.T_SVC_TRACE_2 where opt_id='1010020101'";
			//select SVC_TRACE_PK,SVC_MSG_CHAR,OPT_ID from SVCMGMT.T_SVC_TRACE_2 where SVC_TRACE_PK between 690870744 and 690871744
			String sql = "select SVC_TRACE_PK,SVC_MSG_CHAR,OPT_ID from SVCMGMT.T_SVC_TRACE_1 where opt_id='"+optID+"' fetch first 10 rows only";
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
	
	public static void callHttpPost(String s) {
		
		ArrayList<OptBean> optList = invoke(s);
		for (OptBean bean: optList) {
			try {
				HttpVistorThread vistor = new HttpVistorThread();
				vistor.setReqt(bean.getReqt());
				vistor.setUrl(bean.getUrl());
				vistor.start();
				Thread.sleep(7000);
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
