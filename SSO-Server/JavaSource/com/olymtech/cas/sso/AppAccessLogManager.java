package com.olymtech.cas.sso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AppAccessLogManager extends BaseDBOperate {

    public int doInsert(Connection conn, AppAccessLog p_AppAccessLog) throws SQLException {

	PreparedStatement stmt = null;
	String m_sSql = "";
	int m_iRet = 0;
	// sequence取得
	String logid = getNextVal(conn, "seq_app_access_log");

	try {
	    m_sSql = "INSERT INTO App_access_log(LOG_ID,user_id,ORG_ID,LOGIN_TIME,LOGOUT_TIME,"
		    + " IP_ADDRESS,LOGIN_STATUS,LOGINSITENO) VALUES(?,?,?,sysdate,null,?,'1',?)";
	    stmt = conn.prepareStatement(m_sSql);
	    int i = 1;
	    stmt.setString(i++, logid);
	    stmt.setString(i++, p_AppAccessLog.getUserId());
	    stmt.setString(i++, p_AppAccessLog.getOrgId());
	    stmt.setString(i++, p_AppAccessLog.getIpAddress());
	    stmt.setString(i++, p_AppAccessLog.getLoginSiteno());

	    stmt.executeUpdate();
	    m_iRet = stmt.getUpdateCount();
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(stmt, null);
	}
	return m_iRet;
    }

    public int doUpdate(Connection conn, AppAccessLog p_AppAccessLog) throws SQLException {

	PreparedStatement stmt = null;
	String m_sSql = "";
	int m_iRet = 0;

	try {
	    m_sSql = " UPDATE App_access_log SET  " + " LOGOUT_TIME=sysdate ,LOGIN_STATUS='0' " + " WHERE user_id = ? AND LOGIN_STATUS='1' ";
	    // +" AND LOGINSITENO=? ";
	    stmt = conn.prepareStatement(m_sSql);
	    int i = 1;
	    stmt.setString(i++, p_AppAccessLog.getUserId());
	    // stmt.setString(i++,p_AppAccessLog.getLoginSiteno());

	    stmt.executeUpdate();
	    m_iRet = stmt.getUpdateCount();
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(stmt, null);
	}
	return m_iRet;
    }
    
    public int doUpdatePlus(Connection conn,String userName)throws SQLException {
	PreparedStatement stmt = null;
	String m_sSql = "";
	int m_iRet = 0;

	try {
	    m_sSql = " UPDATE App_access_log SET  " + " LOGOUT_TIME=sysdate ,LOGIN_STATUS='0' " + " WHERE user_id in (select user_id from mem_user where user_name=?) AND LOGIN_STATUS='1' ";
	    // +" AND LOGINSITENO=? ";
	    stmt = conn.prepareStatement(m_sSql);
	    stmt.setString(1, userName);

	    stmt.executeUpdate();
	    m_iRet = stmt.getUpdateCount();
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(conn,stmt);
	}
	return m_iRet;
    }

    public int doDelete(Connection conn, AppAccessLog p_AppAccessLog) throws SQLException {

	PreparedStatement stmt = null;
	String m_sSql = "";
	int m_iRet = 0;

	m_sSql = "DELETE FROM App_access_log WHERE user_id = ? ";
	// +" and LOGINSITENO=? ";

	try {
	    stmt = conn.prepareStatement(m_sSql);
	    stmt.setString(1, p_AppAccessLog.getUserId());
	    // stmt.setString(2, p_AppAccessLog.getLoginSiteno());

	    stmt.executeUpdate();
	    m_iRet = stmt.getUpdateCount();
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(stmt, null);
	}
	return m_iRet;
    }
}
