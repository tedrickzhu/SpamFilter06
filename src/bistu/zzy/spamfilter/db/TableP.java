package bistu.zzy.spamfilter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import bistu.zzy.spamfilter.object.KeyWord;

public class TableP {

	/**
	 * 函数功能 将训练过程产生的特征词及每个词的特征概率写入数据库中
	 * 
	 * @param keywords
	 *            训练过程产生的包含特征词特征概率的特征词集合
	 */
	public static void insertTableP(List<KeyWord> keywords) {
		Connection mydbcon = DB.getConn();
		PreparedStatement pstmt = null;

		// TODO 每次插入数据前，先把原有数据清除
		String trunsql = "truncate table pwords;";
		pstmt = DB.getPStmt(mydbcon, trunsql);
		DB.executeUpdate(pstmt);
		DB.closePStmt(pstmt);

		for (int i = 0; i < keywords.size(); i++) {
			KeyWord kword = keywords.get(i);
			String kwname = kword.getName();
			double wpham = kword.getWordpham();
			double wpspam = kword.getWordpspam();

			String insql = "insert into pwords(id,wordname,wpham,wpspam)" + "values(null,'" + kwname + "'," + wpham
					+ "," + wpspam + ")";
			pstmt = DB.getPStmt(mydbcon, insql);
			DB.executeUpdate(pstmt);
			DB.closePStmt(pstmt);
		}
		DB.closeConn(mydbcon);
	}

	/**
	 * 函数功能 查找待测文本每个特征词的特征概率，如查不到该词的先验概率，则将其概率设为1，即不考虑此词的作用
	 * 
	 * @param keywords
	 *            待测文本的特征词集合
	 * @return 包含了每个特征词特征概率的待测文本的特征词集合
	 */
	public static List<KeyWord> selectTablep(List<KeyWord> keywords) {
		Connection mydbcon = DB.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		for (int i = 0; i < keywords.size(); i++) {
			KeyWord kword = keywords.get(i);
			String kwname = kword.getName();

			String sql = "select wordname,wpham,wpspam from pwords where wordname ='" + kwname + "'";
			try {
				// System.out.println("正在读取数据库中的词的概率！");
				pstmt = DB.getPStmt(mydbcon, sql);
				rs = pstmt.executeQuery();
				// 若未从数据库中找到该词的分类概率，则，默认设为1，即不考虑此词的分类效果
				double wpham = 1.00;
				double wpspam = 1.00;
				while (rs.next()) {
					wpham = Double.parseDouble(rs.getString("wpham"));
					wpspam = Double.parseDouble(rs.getString("wpspam"));
				}
				//System.out.println("wpham:"+wpham);
				//System.out.println("wpspam:"+wpspam);
				kword.setWordpham(wpham);
				kword.setWordpspam(wpspam);
				// 释放资源
				rs.close();
				pstmt.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		DB.closeConn(mydbcon);
		return keywords;
	}

	public static boolean isEmpty() {
		boolean empty = true;
		String sql = "select * from pwords;";
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
	
	// ResultSet rs = null;
	// if (rs.last()) {// 从第一条往后依次取结果集中的记录
	// String userId = rs.getString(1);
	// // 等同rs.getString("userId")，即第一个字段数据
	// String userName = rs.getString(2);// 同上，第二个字段，全部取其为String类型
	// // 若是中文字段，一般需要转码
	// // userName = new String(userName.getBytes("ISO-8859-1"),"gb2312");
	// // 输出此条记录的查询结果
	// System.out.println(userId + ":" + userName);
	// }
	// // 释放资源
	// // rs.close();
	// // smt.close();
	// // con.close();

	public static boolean clearTablep() {
		boolean clear = true;
		Connection mydbcon = DB.getConn();
		PreparedStatement pstmt = null;
		
		// TODO 每次插入数据前，先把原有数据清除
		String trunsql = "truncate table pwords;";
		pstmt = DB.getPStmt(mydbcon, trunsql);
		DB.executeUpdate(pstmt);
		DB.closePStmt(pstmt);
		
		DB.closeConn(mydbcon);
		return clear;
	}

	public static void deleteTablep() {

	}

}
