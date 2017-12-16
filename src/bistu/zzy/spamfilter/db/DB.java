package bistu.zzy.spamfilter.db;

//����SQL��ذ����ö���������٣�Ҳ��ֱ��import java.sql.*;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	/*
	 * ����ģʽ��װ���ݿ�����ӣ�������չ
	 */

	private DB() {
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConn() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?user=root&password=root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConn(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = null;
		}
	}

	public static Statement getStmt(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}

	public static PreparedStatement getPStmt(Connection conn, String sql) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pstmt;
	}
	
	public static void closeStmt(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stmt = null;
		}
	}
	
	public static void closePStmt(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pstmt = null;
		}
	}

	public static ResultSet executeQuery(Statement stmt, String sql) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public static ResultSet executeQuery(PreparedStatement pstmt) {
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs = null;
		}
	}
	
	public static void executeUpdate(Statement stmt, String sql) {
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void executeUpdate(PreparedStatement pstmt) {
		try {
			pstmt.executeUpdate();
		} catch (MySQLIntegrityConstraintViolationException e){
			System.out.println("�����ظ����룬 �ô����ݿ��Ѵ��ڣ����ٲ���");
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * // MySQL��JDBC URL��д��ʽ��jdbc:mysql://�������ƣ����Ӷ˿�/���ݿ������?����=ֵ //
	 * ������������Ҫָ��useUnicode��characterEncoding //
	 * ִ�����ݿ����֮ǰҪ�����ݿ����ϵͳ�ϴ���һ�����ݿ⣬�����Լ����� // �������֮ǰ��Ҫ�ȴ���javademo���ݿ� String url =
	 * "jdbc:mysql://localhost:3306/javademo?" +
	 * "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
	 * Class.forName("com.mysql.jdbc.Driver"); Connection con =
	 * DriverManager.getConnection("jdbc:mysql://localhost:3306/anote",
	 * "root","root"); PreparedStatement state =
	 * (PreparedStatement)con.prepareStatement(sql);
	 * 
	 * // ֮����Ҫʹ������������䣬����ΪҪʹ��MySQL����������������Ҫ�������������� //
	 * ����ͨ��Class.forName�������ؽ�ȥ��Ҳ����ͨ����ʼ������������������������ʽ������
	 * //Class.forName("com.mysql.jdbc.Driver");// ��̬����mysql���� // or: //
	 * com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver(); // or�� // new
	 * com.mysql.jdbc.Driver();
	 * 
	 * // һ��Connection����һ�����ݿ����� conn = DriverManager.getConnection(url);
	 * 
	 * // Statement������кܶ෽��������executeUpdate����ʵ�ֲ��룬���º�ɾ����
	 */
}
