package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	
	public static Connection conn;
	public static Statement stmt;
	
	public static void init() {
		try {
			// 1. 오라클 드라이버 설치
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. 드라이버 매니저 연결
			//작업관리자 -> 서비스 -> OracleService(X아이디? S아이디?)
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@127.0.0.1:1521:XE",
					"temp", "1234");
			
			//stmt 객체가 db로 들어가서 sql문을 날려줘 원하는 결과를 가져올 수 있음
			stmt = conn.createStatement();
			System.out.println("db 연결 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 에러");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("DB 연결 오류 or 쿼리 오류 입니다.");
			e.printStackTrace();
		}
		
	}
	
	public static ResultSet getResultSet(String sql) {
		try {
			Statement stmt = conn.createStatement();
			return stmt.executeQuery(sql);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	// 원래 정수형 return 하지만 그냥 단순 수행만을 위해 void 선언함
	public static void executeQuery(String sql) {
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
