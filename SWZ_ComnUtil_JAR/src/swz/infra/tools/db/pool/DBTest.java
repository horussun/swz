package swz.infra.tools.db.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DBTest {

	public static void main(String[] args) {
		try {
			DBConnectionManager connectionManager = DBConnectionManager.getInstance();
			Connection conn = connectionManager.getConnection("esbdb");
			/*
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection conn1 = connectionManager.getConnection("ESBDB");
			Connection conn2 = connectionManager.getConnection("ESBDB");
			Connection conn3 = connectionManager.getConnection("ESBDB");
			Connection conn4 = connectionManager.getConnection("ESBDB");
			Connection conn5 = connectionManager.getConnection("ESBDB");
			connectionManager.freeConnection("ESBDB", conn);
			connectionManager.freeConnection("ESBDB", conn1);
			connectionManager.freeConnection("ESBDB", conn2);
			connectionManager.freeConnection("ESBDB", conn3);
			connectionManager.freeConnection("ESBDB", conn4);
			connectionManager.freeConnection("ESBDB", conn5);
			Connection conn6 = connectionManager.getConnection("ESBDB");
			Connection conn7 = connectionManager.getConnection("ESBDB");
			System.out.println(" conn6 == " + conn6 + " conn7 == " + conn7);
			*/
			PreparedStatement ps = conn.prepareStatement("insert into SVCMGMT.TEST values ('2')");
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
