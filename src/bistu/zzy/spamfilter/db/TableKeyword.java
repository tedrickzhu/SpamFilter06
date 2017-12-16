package bistu.zzy.spamfilter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import bistu.zzy.spamfilter.object.KeyWord;

public class TableKeyword {

	public static void insertTablekw(List<KeyWord> keywords) {
		Connection mydbcon = DB.getConn();
		PreparedStatement pstmt = null;
		
		// TODO 每次插入数据前，先把原有数据清除
		String trunsql = "truncate table keywords;";
		pstmt = DB.getPStmt(mydbcon, trunsql);
		DB.executeUpdate(pstmt);
		DB.closePStmt(pstmt);
		
		for (int i = 0; i < keywords.size(); i++) {
			KeyWord kword = keywords.get(i);
			String kwname = kword.getName();
			int numtc = kword.getNum_t_c();
			int numtnc = kword.getNum_t_nc();
			int numntc = kword.getNum_nt_c();
			int numntnc = kword.getNum_nt_nc();
			
			String insql = "insert into keywords(id,wordname,num_t_c,num_t_nc,num_nt_c,num_nt_nc)"
					+ "values(null,'" + kwname + "'," + numtc + "," + numtnc + "," + numntc + "," + numntnc + ")";
			 pstmt = DB.getPStmt(mydbcon, insql);
			DB.executeUpdate(pstmt); 
			DB.closePStmt(pstmt);
		}
		DB.closeConn(mydbcon);
	}

	public static List<KeyWord> selectTablekw() {
		Connection mydbcon = DB.getConn();
		List<KeyWord> dbkeywords = new ArrayList<KeyWord>();
		
		String sql = "select id,wordname,num_t_c,num_t_nc,num_nt_c,num_nt_nc from keywords";
		
		try {
			Statement stmt = mydbcon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				
				KeyWord kword = new KeyWord();
				String kwname = rs.getString("wordname");
				
				int numtc = Integer.parseInt(rs.getString("num_t_c"));
				int numtnc = Integer.parseInt(rs.getString("num_t_c"));
				int numntc = Integer.parseInt(rs.getString("num_t_c"));
				int numntnc = Integer.parseInt(rs.getString("num_t_c"));
				
				kword.setName(kwname);
				kword.setNum_t_c(numtc);
				kword.setNum_t_nc(numtnc);
				kword.setNum_nt_c(numntc);
				kword.setNum_nt_nc(numntnc);
				
				dbkeywords.add(kword);
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB.closeConn(mydbcon);
		return dbkeywords;
	}

	public static boolean isEmpty() {
		Connection mydbcon = DB.getConn();
		boolean empty = true;
		String sql = "select id,wordname,num_t_c,num_t_nc,num_nt_c,num_nt_nc from keywords";
		try {
			PreparedStatement pstmt = DB.getPStmt(mydbcon, sql);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				empty = false;
			} else {
				empty = true;
			}
			DB.closePStmt(pstmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB.closeConn(mydbcon);
		return empty;
	}

	
	public static boolean clearTablekw() {
		boolean clear = true;
		Connection mydbcon = DB.getConn();
		PreparedStatement pstmt = null;
		
		// TODO 每次插入数据前，先把原有数据清除
		String trunsql = "truncate table keywords;";
		pstmt = DB.getPStmt(mydbcon, trunsql);
		DB.executeUpdate(pstmt);
		DB.closePStmt(pstmt);
		
		DB.closeConn(mydbcon);
		return clear;
	}

	public static void deleteTablekw() {

	}

}
