package swz.infra.tools.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import swz.infra.tools.log.Log;

public class test {
	static Logger logger = Log.getLogger(DBComOp.class.getName());
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ListView lv=testGetRs();
//		testGetRSet();
		testGetRs();
		logger.debug("ok");
//		orignalDB();
	}
	
	private static ListView testGetRs() {
		String schemaName="DB2ADMIN"; 
		String sql="select * from "+schemaName+".T_Node as node where node.PTID=? and node.nodeid=?";
		String[] values= {"bank01","010101"};
		ListView lv=DBComOp.instance.getRs(sql, values, null);
		lv.print();	
		return lv;
	}
	
	private static ResultSet testGetRSet() {
		String schemaName="DB2ADMIN"; 
		String sql="select * from "+schemaName+".T_Node as node where node.PTID=? and node.nodeid=?";
		String[] values= {"CMB01","020106"};
		String types= null;
		ResultSet rs=DBComOp.instance.getRSet(sql, values, null);
		try {
			while(rs.next()) {
				logger.debug(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	private static void orignalDB() {
		Connection conn = DBCon.getConn();
		String sql="select * from db2admin.T_Node";
		java.sql.PreparedStatement st;
		try {
			st = conn.prepareStatement(sql, java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
					java.sql.ResultSet.CONCUR_READ_ONLY);
			java.sql.ResultSet rs = st.executeQuery();
			logger.debug("ok"+ rs.getRow());
			while(rs.next()) {
				logger.debug(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
