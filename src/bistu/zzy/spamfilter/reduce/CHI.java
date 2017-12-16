package bistu.zzy.spamfilter.reduce;

import java.util.List;

import bistu.zzy.spamfilter.db.TableParam;
import bistu.zzy.spamfilter.object.FilterParam;
import bistu.zzy.spamfilter.object.KeyWord;
import bistu.zzy.spamfilter.object.TxtData;
import bistu.zzy.spamfilter.util.Calculator;

public class CHI {

	/**
	 * 函数功能 利用CHI进行第二次降维
	 * 
	 * @param chikeywords
	 *            计算好CHI的keyword的list
	 * @param txtdata
	 *            计算需用到的相关数据，如，垃圾邮件的总数，正常邮件的总数，所有邮件的总数，等
	 * @return 返回利用CHI降维后的keyword list
	 */

	public static List<KeyWord> useCHI(List<KeyWord> chikeywords, TxtData txtdata) {
		//从数据库中读取chi 阈值
		FilterParam fparam = TableParam.selectParam();
		double chi = fparam.getChi();

		for (int j = 0; j < chikeywords.size(); j++) {
			// 获得关键词list中的某个词
			KeyWord kword = chikeywords.get(j);
			// String name = kword.getName();
			double chiham = kword.getChiham();
			// double chispam = kword.getChispam();
			/*
			 * TODO 根据CHI，设置阈值，或其他方法降维，remove一些相关性不大的词 dfwordslist.remove(Object
			 * o)或者dfwordslist.remove(index i)
			 * 了解内存运行机制，list的remove方法是从内存中剔除还是从list中剔除索引，
			 */
			if (chiham < chi) {
				chikeywords.remove(kword);
			}
		}
		return chikeywords;
	}

	/**
	 * 函数功能 计算 利用文档频率降维后的特征词的CHI
	 * 
	 * @param dfkeywords
	 *            利用文档频率降维后的list
	 * @param txtdata
	 *            计算需用到的相关数据，如，垃圾邮件的总数，正常邮件的总数，所有邮件的总数，等
	 * @return 返回计算好CHI的list
	 */
	public static List<KeyWord> getCHI(List<KeyWord> dfkeywords, TxtData txtdata) {
		List<KeyWord> chiwordslist = null;
		// TODO 计算每个词的CHI，并将其写入数据库
		// TODO ntxt 是训练集中，所有邮件文本数，在读取文件夹时即可获得，写入数据库，此处直接读出即可
		int ntxt = txtdata.getTxtnum();

		for (int j = 0; j < dfkeywords.size(); j++) {
			// 获得关键词list中的某个词
			KeyWord kword = dfkeywords.get(j);
			String name = kword.getName();
			int numtc = kword.getNum_t_c();
			int numtnc = kword.getNum_t_nc();
			int numntc = kword.getNum_nt_c();
			int numntnc = kword.getNum_nt_nc();

			double chiham = Calculator.getCHIham(ntxt, numtc, numtnc, numntc, numntnc);
			double chispam = Calculator.getCHIspam(ntxt, numtc, numtnc, numntc, numntnc);

			kword.setChiham(chiham);
			kword.setChispam(chispam);
		}
		chiwordslist = dfkeywords;
		return chiwordslist;
	}

}
