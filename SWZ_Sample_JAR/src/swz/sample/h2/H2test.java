package swz.sample.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;

public class H2test {

	private Server server;
	private String port = "9094";
	private String dbDir = "E:/bin/h2/db/";
	private String user = "sa";
	private String password = "";

	public void startServer() {
		try {
			System.out.println("正在启动h2...");
			server = Server.createTcpServer(new String[] { "-tcpPort", port }).start();
		} catch (SQLException e) {
			System.out.println("启动h2出错：" + e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void stopServer() {
		if (server != null) {
			System.out.println("正在关闭h2...");
			server.stop();
			System.out.println("关闭成功.");
		}
	}

	public void useH2() {
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:" + dbDir, user, password);
			Statement stat = conn.createStatement();
			// insert data
			stat.execute("CREATE TABLE TEST(NAME VARCHAR)");
			stat.execute("INSERT INTO TEST VALUES('Hello World')");

			// use data
			ResultSet result = stat.executeQuery("select name from test ");
			int i = 1;
			while (result.next()) {
				System.out.println(i++ + ":" + result.getString("name"));
			}
			result.close();
			stat.close();
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
		/*
		H2test h2 = new H2test();
		h2.startServer();
		h2.useH2();
		h2.stopServer();
		System.out.println("==END==");
		*/
			//Embedded
			String dbUrl = "jdbc:h2:E:/bin/h2/db/test";
			//server:jdbc:h2:tcp://localhost/~/test
			//mem
			String user = "sa";
			String pwd = "";
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection(dbUrl, user, pwd);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM TEST ");   
			while(rs.next()) {   
				System.out.println(rs.getInt("ID")+","+rs.getString("NAME"));
			}
			conn.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}