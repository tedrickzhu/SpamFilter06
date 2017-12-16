package bistu.zzy.spamfilter.util;

import java.util.List;

import bistu.zzy.spamfilter.db.TableTxtdata;
import bistu.zzy.spamfilter.object.KeyWord;
import bistu.zzy.spamfilter.object.TxtData;

public class Bayes {

	/**
	 * �������� ��������ı��ķ������
	 * 
	 * @param txtkws
	 *            �����ı��������ʼ��ϣ�����ÿ���ʵ������ʸ���
	 * @return �����ı��������������
	 */
	public static double[] getTxtP(List<KeyWord> txtkws) {
		// �����ݿ��ȡѵ�����������ı�������
		TxtData txtdata = TableTxtdata.selectTxtdata();
		double[] txtp = Bayes.getTxtP(txtkws, txtdata);

		return txtp;
	}

	/**
	 * �������� ��������ı��ķ������
	 * 
	 * @param txtkws
	 *            �����ı��������ʼ��ϣ�����ÿ���ʵ������ʸ���,txtdata ѵ�������ı�����������
	 * @return �����ı��������������
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
			// ��ùؼ���list�е�ĳ����
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
	 * �������� ����ѵ��������2�ν�ά���ÿ�������ʵ���������
	 * 
	 * @param chikeywords
	 *            ����chi���е�2�ν�ά������������ʵļ���
	 * @param txtdata
	 *            ѵ������������ݣ����ı����������ʼ����������ʼ���
	 * @return ���������ʷ����������ʵ������ʼ���
	 */
	public static List<KeyWord> getWordsP(List<KeyWord> chikeywords, TxtData txtdata) {
		// TODO Auto-generated constructor stub
		List<KeyWord> wpkeywords = null;
		/*
		 * TODO ����ÿ���ʵ����������ʼ��ĸ��ʺ����������ʼ��ĸ��ʣ�������д�����ݿ� ntxt
		 * ��ѵ�����У������ʼ��ı������ڶ�ȡ�ļ���ʱ���ɻ�ã�д�����ݿ⣬�˴�ֱ�Ӷ������� ntxtham
		 * ��ѵ�����У����������ʼ��ı������ڶ�ȡ�ļ���ʱ���ɻ�ã�д�����ݿ⣬�˴�ֱ�Ӷ������� ntxtspam
		 * ��ѵ�����У����������ʼ��ı������ڶ�ȡ�ļ���ʱ���ɻ�ã�д�����ݿ⣬�˴�ֱ�Ӷ�������
		 */
		int ntxtham = txtdata.getTxthamnum();
		int ntxtspam = txtdata.getTxtspamnum();
		// int ntxt = ntxtham + ntxtspam;
		// int ntxt = txtdata.getTxtnum();
		// chikwsum ��m,���ֶ�������chi��ά������������ʵ�����
		int m = chikeywords.size();

		for (int j = 0; j < chikeywords.size(); j++) {
			// ��ùؼ���list�е�ĳ����
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
			// TODO �˽��ڴ����л��ƣ�list��remove�����Ǵ��ڴ����޳����Ǵ�list���޳�������

		}
		wpkeywords = chikeywords;
		return wpkeywords;
	}

}
