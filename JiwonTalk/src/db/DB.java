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
			// 1. ����Ŭ ����̹� ��ġ
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. ����̹� �Ŵ��� ����
			//�۾������� -> ���� -> OracleService(X���̵�? S���̵�?)
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@127.0.0.1:1521:XE",
					"temp", "1234");
			
			//stmt ��ü�� db�� ���� sql���� ������ ���ϴ� ����� ������ �� ����
			stmt = conn.createStatement();
			System.out.println("db ���� ����");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC ����̹� �ε� ����");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("DB ���� ���� or ���� ���� �Դϴ�.");
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
	// ���� ������ return ������ �׳� �ܼ� ���ุ�� ���� void ������
	public static void executeQuery(String sql) {
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
