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
	 * �������� ��ѵ�����̲����������ʼ�ÿ���ʵ���������д�����ݿ���
	 * 
	 * @param keywords
	 *            ѵ�����̲����İ����������������ʵ������ʼ���
	 */
	public static void insertTableP(List<KeyWord> keywords) {
		Connection mydbcon = DB.getConn();
		PreparedStatement pstmt = null;

		// TODO ÿ�β�������ǰ���Ȱ�ԭ���������
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
	 * �������� ���Ҵ����ı�ÿ�������ʵ��������ʣ���鲻���ôʵ�������ʣ����������Ϊ1���������Ǵ˴ʵ�����
	 * 
	 * @param keywords
	 *            �����ı��������ʼ���
	 * @return ������ÿ���������������ʵĴ����ı��������ʼ���
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
				// System.out.println("���ڶ�ȡ���ݿ��еĴʵĸ��ʣ�");
				pstmt = DB.getPStmt(mydbcon, sql);
				rs = pstmt.executeQuery();
				// ��δ�����ݿ����ҵ��ôʵķ�����ʣ���Ĭ����Ϊ1���������Ǵ˴ʵķ���Ч��
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
				// �ͷ���Դ
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
	// if (rs.last()) {// �ӵ�һ����������ȡ������еļ�¼
	// String userId = rs.getString(1);
	// // ��ͬrs.getString("userId")������һ���ֶ�����
	// String userName = rs.getString(2);// ͬ�ϣ��ڶ����ֶΣ�ȫ��ȡ��ΪString����
	// // ���������ֶΣ�һ����Ҫת��
	// // userName = new String(userName.getBytes("ISO-8859-1"),"gb2312");
	// // ���������¼�Ĳ�ѯ���
	// System.out.println(userId + ":" + userName);
	// }
	// // �ͷ���Դ
	// // rs.close();
	// // smt.close();
	// // con.close();

	public static boolean clearTablep() {
		boolean clear = true;
		Connection mydbcon = DB.getConn();
		PreparedStatement pstmt = null;
		
		// TODO ÿ�β�������ǰ���Ȱ�ԭ���������
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
