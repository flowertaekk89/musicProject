package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ±«±Ÿ≈√
 * */
public class ConnectionManager {
	private static final String DRIVE = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "hr";
	private static final String PW = "1120";
	
	static{
		try {
			Class.forName(DRIVE);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}//static
	
	public static Connection getConnection(){
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PW);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}//getConnection
	
	public static void closeConnection(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//closeConnection
	
}//class
