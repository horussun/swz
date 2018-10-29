package swz.sample.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MenTest {
	public void runInsertDelete() {
		try {
			String sourceURL = "jdbc:h2:tcp://localhost/mem:testmemdb";// H2DB
																		// mem
																		// mode
			String user = "sa";
			String key = "";
			try {
				Class.forName("org.h2.Driver");// HSQLDB Driver
			} catch (Exception e) {
				e.printStackTrace();
			}
			Connection conn = DriverManager.getConnection(sourceURL, user, key);// 把驱动放入连接
			Statement stmt = conn.createStatement();
			// 创建一个 Statement 对象来将 SQL 语句发送到数据库。
			// stmt.executeUpdate("DELETE FROM mytable WHERE name=/'NO.2/'");
			// 执行方法找到一个与 methodName 属性同名的方法，并在目标上调用该方法。
			stmt.execute("CREATE TABLE idtable(id INT,name VARCHAR(100));");
			stmt.execute("INSERT INTO idtable VALUES(1,/'MuSoft/')");
			stmt.execute("INSERT INTO idtable VALUES(2,/'StevenStander/')");
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void query(String SQL) {
		try {
			String sourceURL = "jdbc:h2:tcp://localhost/mem:testmemdb";
			String user = "sa";
			String key = "";

			try {
				Class.forName("org.h2.Driver");
			} catch (Exception e) {
				e.printStackTrace();
			}
			Connection conn = DriverManager.getConnection(sourceURL, user, key);// 把驱动放入连接
			Statement stmt = conn.createStatement();// 创建一个 Statement 对象来将 SQL
													// 语句发送到数据库。
			ResultSet rset = stmt.executeQuery(SQL);// 执行方法找到一个与 methodName
													// 属性同名的方法，并在目标上调用该方法。
			while (rset.next()) {
				System.out.println(rset.getInt("id") + "  " + rset.getString("name"));
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createInsert() {
		try {
			String sourceURL = "jdbc:h2:h2/bin/mydb";// H2 database
			String user = "sa";
			String key = "";
			try {
				Class.forName("org.h2.Driver");// H2 Driver
			} catch (Exception e) {
				e.printStackTrace();
			}
			Connection conn = DriverManager.getConnection(sourceURL, user, key);
			Statement stmt = conn.createStatement();
			stmt.execute("CREATE TABLE mytable(name VARCHAR(100),sex VARCHAR(10))");
			stmt.executeUpdate("INSERT INTO mytable VALUES('Steven Stander','male')");
			stmt.executeUpdate("INSERT INTO mytable VALUES('Elizabeth Eames','female')");
			stmt.executeUpdate("DELETE FROM mytable WHERE sex=/'male/'");
			stmt.close();
			conn.close();

		} catch (SQLException sqle) {
			System.err.println(sqle);
		}
	}

	public static void main(String args[]) {
		/*
		 * MenTest mt = new MenTest(); mt.runInsertDelete();
		 * mt.query("SELECT * FROM idtable");
		 */
		try {
			Class.forName("org.h2.Driver");
			//jdbc:h2:~/mem:test default path"C:\Users\IBM_ADMIN/mem:test.h2.db" jdbc:h2:tcp://localhost/mem:test = need start h2 server
			Connection conn = DriverManager.getConnection("jdbc:h2:E:/bin/h2/db/test", "sa", "");
			// add application code here
			Statement stmt = conn.createStatement();

			//stmt.executeUpdate("CREATE TABLE TEST_MEM(ID INT PRIMARY KEY,NAME VARCHAR(255));");
			//stmt.executeUpdate("INSERT INTO TEST_MEM VALUES(2, 'Hello_Mem');");
			ResultSet rs = stmt.executeQuery("SELECT * FROM TEST_MEM");
			while (rs.next()) {
				System.out.println(rs.getInt("ID") + "," + rs.getString("NAME"));
			}
			rs.close();
			stmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}