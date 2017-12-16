package bistu.zzy.spamfilter.util;

import java.util.List;

import bistu.zzy.spamfilter.db.TableTxtdata;
import bistu.zzy.spamfilter.object.KeyWord;
import bistu.zzy.spamfilter.object.TxtData;

public class Bayes {

	/**
	 * 函数功能 计算待测文本的分类概率
	 * 
	 * @param txtkws
	 *            待测文本的特征词集合，包含每个词的特征词概率
	 * @return 待测文本的两个分类概率
	 */
	public static double[] getTxtP(List<KeyWord> txtkws) {
		// 从数据库读取训练集中两类文本的数量
		TxtData txtdata = TableTxtdata.selectTxtdata();
		double[] txtp = Bayes.getTxtP(txtkws, txtdata);

		return txtp;
	}

	/**
	 * 函数功能 计算待测文本的分类概率
	 * 
	 * @param txtkws
	 *            待测文本的特征词集合，包含每个词的特征词概率,txtdata 训练集的文本数量的数据
	 * @return 待测文本的两个分类概率
	 */
	public static double[] getTxtP(List<KeyWord> txtkws, TxtData txtdata) {
		double[] txtp = new double[2];

		int txtnum = txtdata.getTxtnum();
		int txthamnum = txtdata.getTxthamnum();
		int txtspamnum = txtdata.getTxtspamnum();
		// TxtData txtdata = TableTxtdata.selectTxtdata();
		double rpham = Calculator.getRatioC(txtnum, txthamnum);
		double rpspam = Calculator.getRatioC(txtnum, txtspamnum);
		// System.out.println("this is a test bayes!"+txtnum);
		// System.out.println("this is a test bayes!"+txthamnum);
		double txtpham = 1.00;
		double txtpspam = 1.00;
		for (int j = 0; j < txtkws.size(); j++) {
			// 获得关键词list中的某个词
			KeyWord kword = txtkws.get(j);
			double wordpham = kword.getWordpham();
			double wordpspam = kword.getWordpspam();
			txtpham = txtpham * wordpham;
			txtpspam = txtpspam * wordpspam;
			// System.out.println("this is a test bayes wordpham!"+wordpham);
			// System.out.println("this is a test bayes wordpspam!"+wordpspam);
			// System.out.println("this is a test bayes txtpham!"+txtpham);
			// System.out.println("this is a test bayes txtpspam!"+txtpspam);
			// System.out.println();
		}
		txtp[0] = txtpham * rpham;
		txtp[1] = txtpspam * rpspam;
		// System.out.println("this is a test bayes rpham!"+rpham);
		// System.out.println("this is a test bayes rpspam!"+rpspam);
		// System.out.println("this is a test bayes!"+rpham);
		// System.out.println("this is a test bayes!"+rpspam);
		// System.out.println("this is a test bayes!"+txtp[0]);
		// System.out.println("this is a test bayes!"+txtp[1]);
		return txtp;
	}

	/**
	 * 函数功能 计算训练过程中2次降维后的每个特征词的特征概率
	 * 
	 * @param chikeywords
	 *            利用chi进行第2次降维后产生的特征词的集合
	 * @param txtdata
	 *            训练集的相关数据，总文本数，正常邮件数，垃圾邮件数
	 * @return 包含特征词分类特征概率的特征词集合
	 */
	public static List<KeyWord> getWordsP(List<KeyWord> chikeywords, TxtData txtdata) {
		// TODO Auto-generated constructor stub
		List<KeyWord> wpkeywords = null;
		/*
		 * TODO 计算每个词的属于垃圾邮件的概率和属于正常邮件的概率，并将其写入数据库 ntxt
		 * 是训练集中，所有邮件文本数，在读取文件夹时即可获得，写入数据库，此处直接读出即可 ntxtham
		 * 是训练集中，所有正常邮件文本数，在读取文件夹时即可获得，写入数据库，此处直接读出即可 ntxtspam
		 * 是训练集中，所有垃圾邮件文本数，在读取文件夹时即可获得，写入数据库，此处直接读出即可
		 */
		int ntxtham = txtdata.getTxthamnum();
		int ntxtspam = txtdata.getTxtspamnum();
		// int ntxt = ntxtham + ntxtspam;
		// int ntxt = txtdata.getTxtnum();
		// chikwsum 即m,该字段是利用chi降维后产生的特征词的总数
		int m = chikeywords.size();

		for (int j = 0; j < chikeywords.size(); j++) {
			// 获得关键词list中的某个词
			KeyWord kword = chikeywords.get(j);
			// String name = kword.getName();
			int numtc = kword.getNum_t_c();
			// int numntc = kword.getNum_nt_c();
			int numtnc = kword.getNum_t_nc();
			// int numntnc = kword.getNum_nt_nc();
			double wordpham = Calculator.getWordPC(ntxtham, numtc, m);
			double wordpspam = Calculator.getWordPC(ntxtspam, numtnc, m);
			kword.setWordpham(wordpham);
			kword.setWordpspam(wordpspam);
			// TODO 了解内存运行机制，list的remove方法是从内存中剔除还是从list中剔除索引，

		}
		wpkeywords = chikeywords;
		return wpkeywords;
	}

}
