package bistu.zzy.spamfilter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import bistu.zzy.spamfilter.object.KeyWord;

public class TableCHI {

	public static void insertTableCHI(List<KeyWord> keywords) {
		Connection mydbcon = DB.getConn();

		// TODO 每次插入数据前，先把原有数据清除
		String trunsql = "truncate table chiwords;";
		try {
			Statement stmt = mydbcon.createStatement();
			stmt.executeUpdate(trunsql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0;i<keywords.size();i++){
			KeyWord kword = keywords.get(i);
			String kwname = kword.getName();
			int numtc = kword.getNum_t_c();
			int numtnc = kword.getNum_t_nc();
			int numntc = kword.getNum_nt_c();
			int numntnc = kword.getNum_nt_nc();
			double chiham = kword.getChiham();
			double chispam = kword.getChispam();
			
			String insql = "insert into chiwords(id,wordname,num_t_c,num_t_nc,num_nt_c,num_nt_nc,chiham,chispam)"
					+ "values(null,'"+kwname+"',"+numtc+","+numtnc+","+numntc+","+numntnc+","+chiham+","+chispam+")";
			try {
				PreparedStatement pst = mydbcon.prepareStatement(insql);
				pst.executeUpdate();
			}catch (MySQLIntegrityConstraintViolationException e){
				System.out.println("词名："+ kwname + " 该词数据库已存在！不再插入");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

	}

	public static void updateTablechi() {

	}

	public static void deleteTablechi() {

	}

}
