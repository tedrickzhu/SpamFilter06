package bistu.zzy.spamfilter.reduce;

import java.util.List;

import bistu.zzy.spamfilter.db.TableParam;
import bistu.zzy.spamfilter.object.FilterParam;
import bistu.zzy.spamfilter.object.KeyWord;
import bistu.zzy.spamfilter.object.TxtData;
import bistu.zzy.spamfilter.util.Calculator;

public class CHI {

	/**
	 * �������� ����CHI���еڶ��ν�ά
	 * 
	 * @param chikeywords
	 *            �����CHI��keyword��list
	 * @param txtdata
	 *            �������õ���������ݣ��磬�����ʼ��������������ʼ��������������ʼ�����������
	 * @return ��������CHI��ά���keyword list
	 */

	public static List<KeyWord> useCHI(List<KeyWord> chikeywords, TxtData txtdata) {
		//�����ݿ��ж�ȡchi ��ֵ
		FilterParam fparam = TableParam.selectParam();
		double chi = fparam.getChi();

		for (int j = 0; j < chikeywords.size(); j++) {
			// ��ùؼ���list�е�ĳ����
			KeyWord kword = chikeywords.get(j);
			// String name = kword.getName();
			double chiham = kword.getChiham();
			// double chispam = kword.getChispam();
			/*
			 * TODO ����CHI��������ֵ��������������ά��removeһЩ����Բ���Ĵ� dfwordslist.remove(Object
			 * o)����dfwordslist.remove(index i)
			 * �˽��ڴ����л��ƣ�list��remove�����Ǵ��ڴ����޳����Ǵ�list���޳�������
			 */
			if (chiham < chi) {
				chikeywords.remove(kword);
			}
		}
		return chikeywords;
	}

	/**
	 * �������� ���� �����ĵ�Ƶ�ʽ�ά��������ʵ�CHI
	 * 
	 * @param dfkeywords
	 *            �����ĵ�Ƶ�ʽ�ά���list
	 * @param txtdata
	 *            �������õ���������ݣ��磬�����ʼ��������������ʼ��������������ʼ�����������
	 * @return ���ؼ����CHI��list
	 */
	public static List<KeyWord> getCHI(List<KeyWord> dfkeywords, TxtData txtdata) {
		List<KeyWord> chiwordslist = null;
		// TODO ����ÿ���ʵ�CHI��������д�����ݿ�
		// TODO ntxt ��ѵ�����У������ʼ��ı������ڶ�ȡ�ļ���ʱ���ɻ�ã�д�����ݿ⣬�˴�ֱ�Ӷ�������
		int ntxt = txtdata.getTxtnum();

		for (int j = 0; j < dfkeywords.size(); j++) {
			// ��ùؼ���list�е�ĳ����
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
