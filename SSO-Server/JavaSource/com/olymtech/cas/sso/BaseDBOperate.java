package com.olymtech.cas.sso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BaseDBOperate {
    
    public String getNextVal(Connection conn,String seqName)throws SQLException {
	String seq = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
	    String sql = "select "+seqName+".nextval from dual";
	    ps = conn.prepareStatement(sql);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		seq = rs.getString("nextval");
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(ps, rs);
	}

	return seq;
    }
    
    public void closeConnetion(Connection conn) throws SQLException {
	if (conn != null)
	    conn.close();
    }

    public void closeConnection(Connection conn, PreparedStatement ps) throws SQLException {
	if (ps != null)
	    ps.close();
	if (conn != null)
	    conn.close();
    }

    public void closeConnection(PreparedStatement ps, ResultSet rs) throws SQLException {
	if (rs != null)
	    rs.close();
	if (ps != null)
	    ps.close();
    }

    public void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
	if (rs != null)
	    rs.close();
	if (ps != null)
	    ps.close();
	if (conn != null)
	    conn.close();
    }
    
    public static Connection getConnection() {
	Connection conn = null;
	try {
	    Context initContext = new InitialContext();
	    Context envContext = (Context) initContext.lookup("java:/comp/env");
	    DataSource ds = (DataSource) envContext.lookup("jdbc/membership");
	    conn = ds.getConnection();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    
	}
	return conn;
    }
}
