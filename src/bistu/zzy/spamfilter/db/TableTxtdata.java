package bistu.zzy.spamfilter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bistu.zzy.spamfilter.object.TxtData;

public class TableTxtdata {

	public static void insertTxtdata(TxtData txtdata) {
		Connection mydbcon = DB.getConn();
		PreparedStatement pstmt = null;
		// TODO 每次插入数据前，先把原有数据清除
		String trunsql = "truncate table txtdata;";
		pstmt = DB.getPStmt(mydbcon, trunsql);
		DB.executeUpdate(pstmt);
		DB.closePStmt(pstmt);
		
		// TxtData txtdata = new TxtData();
		int txtnum = txtdata.getTxtnum();
		int txthamnum = txtdata.getTxthamnum();
		int txtspamnum = txtdata.getTxtspamnum();

		String insql = "insert into txtdata(id,txtnum,hamnum,spamnum)" + "values(null,'" + txtnum + "'," + txthamnum
				+ "," + txtspamnum + ")";
		pstmt = DB.getPStmt(mydbcon, insql);
		DB.executeUpdate(pstmt);
		DB.closePStmt(pstmt);
		
		DB.closeConn(mydbcon);
	}

	public static TxtData selectTxtdata() {
		TxtData txtdata = new TxtData();
		int txtnum = 0;
		int hamnum = 0;
		int spamnum = 0;
		int chikwsum = 0;

		String sql = "select txtnum,hamnum,spamnum,pwordsnum from txtdata";
		try {
			Connection mydbcon = DB.getConn();
			PreparedStatement pstmt = DB.getPStmt(mydbcon, sql);
			ResultSet rs = DB.executeQuery(pstmt);

			while (rs.next()) {
				txtnum = Integer.parseInt(rs.getString("txtnum"));
				hamnum = Integer.parseInt(rs.getString("hamnum"));
				spamnum = Integer.parseInt(rs.getString("spamnum"));
				chikwsum = Integer.parseInt(rs.getString("pwordsnum"));
			}
			// System.out.print("获得TxtData的数据：" + txtnum);
			txtdata.setTxtnum(txtnum);
			txtdata.setTxthamnum(hamnum);
			txtdata.setTxtspamnum(spamnum);
			txtdata.setChikwsum(chikwsum);

			DB.closeResultSet(rs);
			DB.closePStmt(pstmt);
			DB.closeConn(mydbcon);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return txtdata;

	}

	public static boolean isEmpty() {
		boolean empty = true;
		String sql = "select txtnum,hamnum,spamnum,pwordsnum from txtdata";
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

	public static boolean clearTxtdata() {
		boolean clear = true;
		Connection mydbcon = DB.getConn();
		PreparedStatement pstmt = null;
		
		// TODO 每次插入数据前，先把原有数据清除
		String trunsql = "truncate table txtdata;";
		pstmt = DB.getPStmt(mydbcon, trunsql);
		DB.executeUpdate(pstmt);
		DB.closePStmt(pstmt);
		
		DB.closeConn(mydbcon);
		return clear;
	}

	public static void deleteTxtdata() {

	}

}
