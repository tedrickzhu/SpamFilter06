package bistu.zzy.spamfilter.main;

import java.util.List;

import bistu.zzy.spamfilter.db.TableKeyword;
import bistu.zzy.spamfilter.db.TableP;
import bistu.zzy.spamfilter.db.TableTxtdata;
import bistu.zzy.spamfilter.io.MailFileReader;
import bistu.zzy.spamfilter.object.KeyWord;
import bistu.zzy.spamfilter.object.TxtData;
import bistu.zzy.spamfilter.reduce.CHI;
import bistu.zzy.spamfilter.reduce.DF;
import bistu.zzy.spamfilter.study.AutoStudy;
import bistu.zzy.spamfilter.ui.SFFrame;
import bistu.zzy.spamfilter.util.Bayes;
import bistu.zzy.spamfilter.util.Counter;

public class TrainMain {
	/**
	 * �������� ����ѵ����·���Թ���������ѵ����ѵ��ǰ�ж����ݿ��Ƿ������ݣ��Դ����������״�ѵ����������ѧϰ
	 * 
	 * @param hampath
	 *            �����ʼ�ѵ����·��
	 * @param spampath
	 *            �����ʼ�ѵ����·��
	 * @param uiframe
	 *            �������
	 */
	public static void train(String hampath, String spampath, SFFrame uiframe) {
		boolean tablekw = TableKeyword.isEmpty();
		boolean tabletxt = TableTxtdata.isEmpty();
		if (tablekw && tabletxt) {
			TrainMain.trainFirst(hampath, spampath, uiframe);
		} else {
			AutoStudy.TrainStudy(hampath, spampath, uiframe);
		}
	}

	/**
	 * �������� ����ѵ����·���Թ����������״�ѵ��
	 * 
	 * @param hampath
	 *            �����ʼ�ѵ����·��
	 * @param spampath
	 *            �����ʼ�ѵ����·
	 * @param uiframe
	 *            �������
	 */
	public static void trainFirst(String hampath, String spampath, SFFrame uiframe) {
		long startTime1 = System.currentTimeMillis();
		long startTime2 = System.nanoTime();
		// Segment.initSegmentation();
		uiframe.getTextArea_train().append("---�״�ѵ���˹�������---�����ĵȺ�ѵ��������-----\n");
		TxtData txtdata = TrainMain.getSetTxtData(hampath, spampath);
		// ��txtdata ����д�����ݿ�
		uiframe.getTextArea_train().append("---�����ĵȺ�---���ڸ���TxtData���ݡ���������\n");
		TableTxtdata.insertTxtdata(txtdata);
		uiframe.getTextArea_train().append("����tabletxtdata���ݿ�ִ�н���!\n");

		List<KeyWord> rskeywords1 = TrainMain.getSetKeywords(hampath, spampath);
		// ���Խ�������ݴ������ݿ��ˣ�ֱ�ӵ���ÿ�����insert��������
		// ����������ı�list�Ĵʵ����� ���Դ˴���Ӧ��д�����ݿ�
		// System.out.println("�����ĵȺ����ڲ���tablekeyword���ݿ⡣��������\n");
		uiframe.getTextArea_train().append("\n---�����ĵȺ�---���ڸ���tablekeyword���ݿ⡣��������\n");
		TableKeyword.insertTablekw(rskeywords1);
		// System.out.println("����tablekw���ݿ�ִ�н���!\n");
		uiframe.getTextArea_train().append("����tablekw���ݿ�ִ�н���!\n");

		List<KeyWord> rskeywords2 = TrainMain.reduceRatio(rskeywords1);
		// ���Խ�������ݴ������ݿ��ˣ�ֱ�ӵ���ÿ�����insert��������
		// ����������ı�list�Ĵʵ����� ���Դ˴���Ӧ��д�����ݿ�
		// System.out.println("-----�����ĵȺ�-----���ڲ���tablep���ݿ⡣��������\n");
		uiframe.getTextArea_train().append("\n---�����ĵȺ�---���ڸ���tablep���ݿ⡣��������\n");
		TableP.insertTableP(rskeywords2);
		// System.out.println("����tablep���ݿ���ɣ�\n");
		uiframe.getTextArea_train().append("����tablep���ݿ���ɣ�\n");

		long endTime1 = System.currentTimeMillis();
		long endTime2 = System.nanoTime();
		// System.out.println("\n������ʱ��" + (endTime1 - startTime1) + "ms," +
		// (endTime2 - startTime2) + "ns.");
		uiframe.getTextArea_train().append("\n---�������״�ѵ��������ɣ�---\n");
		uiframe.getTextArea_train().append("\n������ʱ��" + (endTime1 - startTime1) + "ms," + (endTime2 - startTime2) + "ns.");
	}

	/**
	 * ��������	����ѵ�������ı�����
	 * @param hampath
	 * @param spampath
	 * @return txtdata
	 */
	public static TxtData getSetTxtData(String hampath, String spampath) {
		/*
		 * ͳ���ĵ����������Ϣ�����������һ��TxtData������ȥ���Ա���������ʹ��
		 */
		int txthamnum = MailFileReader.countFile(hampath);
		int txtspamnum = MailFileReader.countFile(spampath);
		int txtnum = txthamnum + txtspamnum;
		// chikwsum ���ֶ�������chi��ά������������ʵ�����
		// int chikwsum = rskeywords.size();
		TxtData txtdata = new TxtData();
		txtdata.setTxtnum(txtnum);
		txtdata.setTxthamnum(txthamnum);
		txtdata.setTxtspamnum(txtspamnum);
		// txtdata.setChikwsum(chikwsum);

		return txtdata;
	}

	/**
	 * ��������	ͳ��ѵ�����е��µ�������
	 * @param hampath
	 * @param spampath
	 * @return List<KeyWord>
	 */
	public static List<KeyWord> getSetKeywords(String hampath, String spampath) {
		TxtData txtdata = TrainMain.getSetTxtData(hampath, spampath);

		int txthamnum = txtdata.getTxthamnum();
		int txtspamnum = txtdata.getTxtspamnum();

		// ��������ʼ�ѵ�����е������ʣ���ͳ�����ĵ�Ƶ��
		List<KeyWord> rskeywords1 = MailFileReader.readFileHam(hampath, txthamnum);
		// ��������ʼ�ѵ�����е������ʣ���ͳ�����ĵ�Ƶ��
		List<KeyWord> rskeywords2 = MailFileReader.readFileSpam(spampath, txtspamnum);
		// ��ʱ���ò����ִ��������ͷ�
		// Segment.CLibrary.Instance.NLPIR_Exit();
		// ��ÿ���ʵ��ĸ��ĵ�Ƶ���ֶε�ֵ������һ��ѵ������ǰ���˽��д�����ݿ�
		List<KeyWord> rskeywords3 = Counter.getSumDF(rskeywords2, rskeywords1, txtdata);

		return rskeywords3;
	}

	/**
	 * ��������	��������list���н�ά��Ȼ�����ÿ���ʵ�Bayes�������
	 * @param rskeywords
	 * @return rskeywords
	 */
	public static List<KeyWord> reduceRatio(List<KeyWord> rskeywords) {
		TxtData txtdata = TableTxtdata.selectTxtdata();

		// �����ĵ�Ƶ�ʽ��е�һ�ν�ά���õ�һ��List<KeyWord>
		rskeywords = DF.useDF(rskeywords);
		// ����ÿ���ʵ�CHIham��CHIspam������¼����
		rskeywords = CHI.getCHI(rskeywords, txtdata);

		// ����chi���еڶ��ν�ά���õ�һ��list
		rskeywords = CHI.useCHI(rskeywords, txtdata);

		// ����ͨ��chi��ά���list������ʣ�������ʵķ������
		rskeywords = Bayes.getWordsP(rskeywords, txtdata);

		return rskeywords;
	}

}
// ���򵽴˽���������Ϊ���Դ���

// public static void main(String[] args) throws
// MySQLIntegrityConstraintViolationException {
//
// long startTime1 = System.currentTimeMillis();
// long startTime2 = System.nanoTime();
//
// String hampath = "G:/TrainSet/ham-gb2312-100";
// String spampath = "G:/TrainSet/spam-gb2312-100";
//
// // TrainFilter(hampath,spampath);
//
// long endTime1 = System.currentTimeMillis();
// long endTime2 = System.nanoTime();
//
// System.out.println("\n������ʱ��" + (endTime1 - startTime1) + "ms," +
// (endTime2 - startTime2) + "ns.");
// }

// ���Խ�������ݴ������ݿ��ˣ�ֱ�ӵ���ÿ�����insert��������
// ����������ı�list�Ĵʵ����� ���Դ˴���Ӧ��д�����ݿ�
// System.out.println("-----�����ĵȺ�-----���ڲ���tablechi���ݿ⡣��������\n");
// uiframe.getTextArea().append("-----�����ĵȺ�-----���ڲ���tablechi���ݿ⡣��������\n");
// TableCHI.insertTableCHI(rskeywords2);
// System.out.println("����tablechi���ݿ�ִ�н���!\n");
// uiframe.getTextArea().append("����tablechi���ݿ�ִ�н���!\n");

// for (int i = 0; i < rskeywords3.size(); i++) {
// KeyWord kword = rskeywords3.get(i);
// System.out.print("�����ǣ�" + kword.getName());
// System.out.print(" hamdftc��" + kword.getNum_t_c());
// System.out.print(" hamDFntc�ǣ�" + kword.getNum_nt_c());
// System.out.print(" spamdftnc��" + kword.getNum_t_nc());
// System.out.print(" spamDFntnc�ǣ�" + kword.getNum_nt_nc() + "\n");
// }
//
// for (int i = 0; i < rskeywords6.size(); i++) {
// KeyWord kword = rskeywords6.get(i);
// System.out.print("�����ǣ�" + kword.getName());
// System.out.print(" �����ʼ��г��ִ�����" + kword.getNum_t_c());
// // System.out.print(" hamDFntc�ǣ�" + kword.getNum_nt_c());
// System.out.print(" �����ʼ��г��ִ�����" + kword.getNum_t_nc());
// // System.out.print(" spamDFntnc�ǣ�" + kword.getNum_nt_nc());
// System.out.print(" chiham�ǣ�" + kword.getChiham());
// System.out.print(" chispam�ǣ�" + kword.getChispam() + "\n");
// }
//
// for (int i = 0; i < rskeywords.size(); i++) {
// KeyWord kword = rskeywords.get(i);
// System.out.print("�����ǣ�" + kword.getName());
// System.out.print(" Pham�ǣ�" + kword.getWordpham());
// System.out.print(" Pspam�ǣ�" + kword.getWordpspam() + "\n");
// }
