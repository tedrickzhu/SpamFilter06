package bistu.zzy.spamfilter.study;

import java.util.List;

import bistu.zzy.spamfilter.db.TableKeyword;
import bistu.zzy.spamfilter.db.TableP;
import bistu.zzy.spamfilter.db.TableTxtdata;
import bistu.zzy.spamfilter.main.TrainMain;
import bistu.zzy.spamfilter.object.KeyWord;
import bistu.zzy.spamfilter.object.TxtData;
import bistu.zzy.spamfilter.ui.SFFrame;
import bistu.zzy.spamfilter.util.Counter;

public class AutoStudy {
	/**
	 * �������� ����ѵ����·���Թ�������������ѧϰѵ��
	 * 
	 * @param hampath
	 *            �����ʼ�ѵ����·��
	 * @param spampath
	 *            �����ʼ�ѵ����·��
	 * @param uiframe
	 *            �������
	 */
	public static void TrainStudy(String hampath, String spampath, SFFrame uiframe) {
		long startTime1 = System.currentTimeMillis();
		long startTime2 = System.nanoTime();
		// Segment.initSegmentation();
		uiframe.getTextArea_train().append("---��ӭ�ٴ�ִ��ѵ�����̣�---�����ĵȺ�ѵ������������������\n");
		// ������ѵ�������ı�����
		uiframe.getTextArea_train().append("\n-----�����ĵȺ�-----���ڻ�ȡ��ѵ�������ı���������������\n");
		TxtData txtdata = TrainMain.getSetTxtData(hampath, spampath);
		// �������ݿ���ѵ�����ı������ļ�¼
		TxtData sumtxtdata = AutoStudy.getSumTxtdata(txtdata);
		// ���µļ�¼sumtxtdata ����д�����ݿ�
		uiframe.getTextArea_train().append("---�����ĵȺ�---���ڸ���tabletxtdata���ݿ�ļ�¼����������\n");
		TableTxtdata.insertTxtdata(sumtxtdata);
		uiframe.getTextArea_train().append("����tabletxtdata���ݿ�ִ�н���!\n");

		uiframe.getTextArea_train().append("\n---�����ĵȺ�---���ڻ�ȡ��ѵ�����������ʡ���������\n");
		List<KeyWord> newkeywords = TrainMain.getSetKeywords(hampath, spampath);

		// ����´ʼ������ݺ���Ҫ�Ѿ���������������ںϣ�Ȼ��ά
		uiframe.getTextArea_train().append("-----�����ĵȺ�-----����ѧϰ��ѵ�����������ʡ���������\n");
		List<KeyWord> sumkeywords = AutoStudy.getSumKeywords(newkeywords, sumtxtdata);
		// ���Խ�������ݴ������ݿ��ˣ�ֱ�ӵ���ÿ�����insert��������
		// ����������ı�list�Ĵʵ����� ���Դ˴���Ӧ��д�����ݿ�
		//System.out.println("---�����ĵȺ�---���ڲ���tablekeyword���ݿ⡣��������\n");
		uiframe.getTextArea_train().append("---�����ĵȺ����ڸ���ѧϰ������ѵ�����������ʸ���tablekeyword���ݿ⡣��������\n");
		TableKeyword.insertTablekw(sumkeywords);
		//System.out.println("����tablekw���ݿ�ִ�н���!\n");
		uiframe.getTextArea_train().append("����tablekeyword���ݿ�ִ�н���!\n");

		// ���н�ά�����������ʵķ�����ʣ�ֻ���������ʸ��ʿ⣬chi����ڴ洢
		uiframe.getTextArea_train().append("\n-----�����ĵȺ�-----���ڽ��н�ά��������������\n");
		List<KeyWord> rskeywords = TrainMain.reduceRatio(sumkeywords);
		// ���Խ�������ݴ������ݿ��ˣ�ֱ�ӵ���ÿ�����insert��������
		// ����������ı�list�Ĵʵ����� ���Դ˴���Ӧ��д�����ݿ�
		//System.out.println("-----�����ĵȺ�-----���ڲ���tablep���ݿ⡣��������\n");
		uiframe.getTextArea_train().append("-----�����ĵȺ�-----���ڸ���tablep���ݿ⡣��������\n");
		TableP.insertTableP(rskeywords);
		//System.out.println("����tablep���ݿ���ɣ�\n");
		uiframe.getTextArea_train().append("����tablep���ݿ���ɣ�\n");

		long endTime1 = System.currentTimeMillis();
		long endTime2 = System.nanoTime();
		// System.out.println("\n������ʱ��" + (endTime1 - startTime1) + "ms," +
		// (endTime2 - startTime2) + "ns.");
		uiframe.getTextArea_train().append("\n---�������ٴ�ѵ��������ѧϰѵ��������ɣ�---\n");
		uiframe.getTextArea_train()
				.append("\n������ʱ��" + (endTime1 - startTime1) + "ms," + (endTime2 - startTime2) + "ns.");
	}

	/**
	 * �������� ������ѧϰѵ�����������������ݿ��е��������ں�
	 * 
	 * @param newkeywords
	 * @param sumtxtdata
	 * @return sumkeywords
	 */
	public static List<KeyWord> getSumKeywords(List<KeyWord> newkeywords, TxtData sumtxtdata) {
		// TODO Auto-generated constructor stub
		List<KeyWord> dbkeywords = TableKeyword.selectTablekw();

		List<KeyWord> sumkeywords = Counter.getSumDF(dbkeywords, newkeywords, sumtxtdata);

		return sumkeywords;
	}

	/**
	 * �������� ������ѧϰѵ�������ı������������ݿ��е��ı��������ں�
	 * 
	 * @param newtxtdata
	 * @return sumtxtdata
	 */
	public static TxtData getSumTxtdata(TxtData newtxtdata) {
		// TODO Auto-generated constructor stub
		TxtData dbtxtdata = TableTxtdata.selectTxtdata();

		int sumtxtnum = dbtxtdata.getTxtnum() + newtxtdata.getTxtnum();
		int sumtxthamnum = dbtxtdata.getTxthamnum() + newtxtdata.getTxthamnum();
		int sumtxtspamnum = dbtxtdata.getTxtspamnum() + newtxtdata.getTxtspamnum();

		newtxtdata.setTxtnum(sumtxtnum);
		newtxtdata.setTxthamnum(sumtxthamnum);
		newtxtdata.setTxtspamnum(sumtxtspamnum);

		return newtxtdata;
	}

	public static void FilteStudy() {
		// TODO Auto-generated constructor stub
	}

}
