package bistu.zzy.spamfilter.db;

//引入SQL相关包，用多少引入多少，也可直接import java.sql.*;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	/*
	 * 单例模式封装数据库的连接，便于扩展
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
			System.out.println("出现重复插入， 该词数据库已存在！不再插入");
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值 //
	 * 避免中文乱码要指定useUnicode和characterEncoding //
	 * 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定， // 下面语句之前就要先创建javademo数据库 String url =
	 * "jdbc:mysql://localhost:3306/javademo?" +
	 * "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
	 * Class.forName("com.mysql.jdbc.Driver"); Connection con =
	 * DriverManager.getConnection("jdbc:mysql://localhost:3306/anote",
	 * "root","root"); PreparedStatement state =
	 * (PreparedStatement)con.prepareStatement(sql);
	 * 
	 * // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来， //
	 * 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
	 * //Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动 // or: //
	 * com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver(); // or： // new
	 * com.mysql.jdbc.Driver();
	 * 
	 * // 一个Connection代表一个数据库连接 conn = DriverManager.getConnection(url);
	 * 
	 * // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
	 */
}
