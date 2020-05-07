package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTemplate {

	public static Connection getConnection() { // static 공통으로 사용되는 데이터를 관리할때 사용
		Connection conn = null;
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc","1234");
				
			conn.setAutoCommit(false); // 자동으로 커밋하는 걸 방지
			
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	
	
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void commit(Connection conn ) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void rollback(Connection conn ) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
