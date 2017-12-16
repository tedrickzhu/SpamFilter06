package bistu.zzy.spamfilter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bistu.zzy.spamfilter.object.FilterParam;

public class TableParam {

	public static void insertParam(FilterParam fparam) {
		Connection mydbcon = DB.getConn();
		PreparedStatement pstmt = null;
		// TODO 每次插入数据前，先把原有数据清除
		String trunsql = "truncate table filterparam;";
		pstmt = DB.getPStmt(mydbcon, trunsql);
		DB.executeUpdate(pstmt);
		DB.closePStmt(pstmt);
		
		int df = fparam.getDf();
		double chi = fparam.getChi();

		String insql = "insert into filterparam(id,df,chi)" + "values(null,'" + df + "'," + chi+")";
		pstmt = DB.getPStmt(mydbcon, insql);
		DB.executeUpdate(pstmt);
		DB.closePStmt(pstmt);
		
		DB.closeConn(mydbcon);
	}

	public static FilterParam selectParam() {
		FilterParam fparam = new FilterParam();
		int df = 5;
		double chi = 30.0;

		String sql = "select df,chi from filterparam";
		try {
			Connection mydbcon = DB.getConn();
			PreparedStatement pstmt = DB.getPStmt(mydbcon, sql);
			ResultSet rs = DB.executeQuery(pstmt);

			while (rs.next()) {
				df = Integer.parseInt(rs.getString("df"));
				chi = Double.parseDouble(rs.getString("chi"));
			}
			fparam.setDf(df);
			fparam.setChi(chi);

			DB.closeResultSet(rs);
			DB.closePStmt(pstmt);
			DB.closeConn(mydbcon);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fparam;

	}

	public static boolean isEmpty() {
		boolean empty = true;
		String sql = "select df,chi from filterparam;";
		try {
			Connection mydbcon = DB.getConn();
			PreparedStatement pstmt = DB.getPStmt(mydbcon, sql);
			ResultSet rs = DB.executeQuery(pstmt);

			if (rs.next()) {
				empty = false;
			} else {
				empty = true;
			}
			DB.closeResultSet(rs);
			DB.closePStmt(pstmt);
			DB.closeConn(mydbcon);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empty;
	}

	public static boolean clearFilterParam() {
		boolean clear = true;
		Connection mydbcon = DB.getConn();
		PreparedStatement pstmt = null;
		
		// TODO 每次插入数据前，先把原有数据清除
		String trunsql = "truncate table filterparam;";
		pstmt = DB.getPStmt(mydbcon, trunsql);
		DB.executeUpdate(pstmt);
		DB.closePStmt(pstmt);
		
		DB.closeConn(mydbcon);
		return clear;
	}
	
	public static boolean clearAll() {
		boolean clear = true;
		Connection mydbcon = DB.getConn();
		PreparedStatement pstmt = null;
		
		// TODO 每次插入数据前，先把原有数据清除
		String trunsql = "truncate table keywords;";
		pstmt = DB.getPStmt(mydbcon, trunsql);
		DB.executeUpdate(pstmt);
		DB.closePStmt(pstmt);
		
		trunsql = "truncate table pwords;";
		pstmt = DB.getPStmt(mydbcon, trunsql);
		DB.executeUpdate(pstmt);
		DB.closePStmt(pstmt);
		
		trunsql = "truncate table txtdata;";
		pstmt = DB.getPStmt(mydbcon, trunsql);
		DB.executeUpdate(pstmt);
		DB.closePStmt(pstmt);
		
		trunsql = "truncate table filterparam;";
		pstmt = DB.getPStmt(mydbcon, trunsql);
		DB.executeUpdate(pstmt);
		DB.closePStmt(pstmt);
		
		DB.closeConn(mydbcon);
		return clear;
	}


}
