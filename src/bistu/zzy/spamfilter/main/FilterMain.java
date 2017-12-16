package bistu.zzy.spamfilter.main;

import java.io.File;
import java.util.List;

import bistu.zzy.spamfilter.db.TableP;
import bistu.zzy.spamfilter.io.MailFileReader;
import bistu.zzy.spamfilter.object.KeyWord;
import bistu.zzy.spamfilter.sgmt.Segment;
import bistu.zzy.spamfilter.ui.SFFrame;
import bistu.zzy.spamfilter.util.Bayes;
import bistu.zzy.spamfilter.util.Calculator;

public class FilterMain {
	
	/**
	 * �������� �����ļ�Ŀ¼����ȡ�����ļ�������ʼ���Ŀ¼�� �������ļ�
	 * 
	 * @param testpath
	 * @param uiframe
	 */
	public static void filter(String testpath, SFFrame uiframe) {
		long startTime1 = System.currentTimeMillis();
		long startTime2 = System.nanoTime();

		// List<KeyWord> fkwslist = null;
		String content = "";
		File file = new File(testpath);
		// ��ʼ���ִ���
		// Segment.initSegmentation();

		if (file.isFile()) {
			if (file.isFile()) {
				content = MailFileReader.readSingleFile(file);
				// System.out.println(content);
				// TODO �˴�Ӧ��ִ�зִʲ���
				// System.out.println("path=" + file.getPath());
				uiframe.getTextArea_filte().append("path=" + file.getPath() + "\n");
				uiframe.getTextArea_filte().append("�ʼ�����Ϊ��\n" + content + "\n\n");
				doFilte(content, uiframe);
			}
		} else if (file.isDirectory()) {
			// System.out.println("this is a directory!");
			// ��������ļ�
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File sfile = new File(testpath + "\\" + filelist[i]);
				if (!sfile.isDirectory()) {
					// System.out.println("path=" + sfile.getPath());
					uiframe.getTextArea_filte().append("path=" + sfile.getPath() + "\n");
					content = MailFileReader.readSingleFile(sfile);
					uiframe.getTextArea_filte().append("�ʼ�����Ϊ��\n" + content + "\n\n");
					// TODO �˴�Ӧ��ִ�зִʲ���,����������
					doFilte(content, uiframe);
				} else if (sfile.isDirectory()) {
					// �ݹ���ã���ȡ���ļ����µ��ļ�
					filter(testpath + "\\" + filelist[i], uiframe);
				}
			}
		} else {
			// System.out.println("���˲����ʼ�·���д�����ȷ���ļ�·����ȷ��\n");
			uiframe.getTextArea_filte().append("���˲����ʼ�·���д�����ȷ���ļ�·����ȷ��\n");
		}
		// �رշִ���
		// Segment.CLibrary.Instance.NLPIR_Exit();
		long endTime1 = System.currentTimeMillis();
		long endTime2 = System.nanoTime();
		// System.out.println("\n������ʱ��" + (endTime1 - startTime1) + "ms," +
		// (endTime2 - startTime2) + "ns.");
		uiframe.getTextArea_filte().append("\n---���˲�����ɣ�---\n");
		uiframe.getTextArea_filte()
				.append("\n������ʱ��" + (endTime1 - startTime1) + "ms," + (endTime2 - startTime2) + "ns.");
	}

	/**
	 * �������� ��ÿ�� �ʼ����ı����з����жϣ��������
	 * 
	 * @param content
	 * @param uiframe
	 */
	public static void doFilte(String content, SFFrame uiframe) {
		uiframe.getTextArea_filte().append("---���ڽ��зִ�---�����ĵȺ�\n");
		// ���ȥ�����ôʵĴ�
		List<KeyWord> fkwslist = Segment.getFkwslist(content);
		// ֻ�����������ʸ��ʿ��������ݵĴ�
		fkwslist = TableP.selectTablep(fkwslist);
		// �����ı��������������
		uiframe.getTextArea_filte().append("---���ڼ���������---�����ĵȺ�\n\n");
		double[] txtp = Bayes.getTxtP(fkwslist);
		// �����������ʵı�ֵ�Ͳ�ֵ
		double rqhs = Calculator.getRatioQuotient(txtp[0], txtp[1]);
		double rdhs = Calculator.getRatioDifference(txtp[0], txtp[1]);
		// ������ʵ�ֵ
		uiframe.getTextArea_filte().append("���ʼ��������ʼ��ĸ����ǣ�" + txtp[0] + "\n");
		uiframe.getTextArea_filte().append("���ʼ��������ʼ��ĸ����ǣ�" + txtp[1] + "\n");
		uiframe.getTextArea_filte().append("�����ʼ������������ʼ��ĸ��ʱ��ǣ�" + rqhs + "\n");
		uiframe.getTextArea_filte().append("�����ʼ������������ʼ��ĸ��ʲ��ǣ�" + rdhs + "\n\n");
		// System.out.println("���ʼ��������ʼ��ĸ����ǣ�" + txtp[0]);
		// System.out.println("���ʼ��������ʼ��ĸ����ǣ�" + txtp[1]);
		// System.out.println("�����ʼ������������ʼ��ĸ��ʱ��ǣ�" + rqhs);
		// System.out.println("�����ʼ������������ʼ��ĸ��ʲ��ǣ�" + rdhs);
		// ���ݴ�С����ֵ����ֵ�����ж�
		if (txtp[0] < txtp[1]) {
			// System.out.println("\n���ʼ��������ʼ�!\n");
			uiframe.getTextArea_filte().append("���ʼ��������ʼ�!\n\n");
		} else if (txtp[0] > txtp[1]) {
			// System.out.println("\n���ʼ��������ʼ�!\n");
			uiframe.getTextArea_filte().append("���ʼ��������ʼ�!\n\n");
		} else {
			// System.out.println("\n����һ���쳣��������²�ѧ��ǳ���޷��жϣ��������£�\n");
			uiframe.getTextArea_filte().append("����һ���쳣��������²�ѧ��ǳ���޷��жϣ��������£�\n");
		}

	}

	
	public static void testFilter(String testhampath,String testspampath, SFFrame uiframe){
		long startTime1 = System.currentTimeMillis();
		long startTime2 = System.nanoTime();
		uiframe.getTextArea_test().append("���ڽ������ܲ��ԣ������ĵȴ���\n");
		int[] resultham = getData(testhampath, uiframe);
		int[] resultspam = getData(testspampath, uiframe);
		
		int a = resultspam[1];
		int b = resultham[1];
		int c = resultspam[0];
		int d = resultham[0];
		
		int ham = resultham[0]+resultham[1]+resultham[2];
		int spam = resultspam[0]+resultspam[1]+resultspam[2];
		
		double recall = (double)a/(a+c); 
		double miss = (double)b/(b+d);
		double less = (double)c/(a+c);
		double correct = (double)(a+d)/(a+b+c+d);
		
		uiframe.getTextArea_test().append("\n------�����ԺϷ��ʼ���"+ham+" ��\n");
		uiframe.getTextArea_test().append("--------��Ϊ�Ϸ��ʼ���"+resultham[0]+" ��;��Ϊ�����ʼ���"+resultham[1]+" ��;�����ʼ���"+resultham[2]+"��\n");
		uiframe.getTextArea_test().append("\n------�����������ʼ���"+spam+" ��\n");
		uiframe.getTextArea_test().append("--------��Ϊ�����ʼ���"+resultspam[1]+" �� ;��Ϊ�Ϸ��ʼ���"+resultspam[0]+" ��;�����ʼ���"+resultspam[2]+"��\n");
		uiframe.getTextArea_test().append("\n------�ٻ����ǣ�"+recall);
		uiframe.getTextArea_test().append("\n------�������ǣ�"+miss);
		uiframe.getTextArea_test().append("\n------©�����ǣ�"+less);
		uiframe.getTextArea_test().append("\n------��ȷ���ǣ�"+correct+"\n");
		
		long endTime1 = System.currentTimeMillis();
		long endTime2 = System.nanoTime();
		// System.out.println("\n������ʱ��" + (endTime1 - startTime1) + "ms," +
		// (endTime2 - startTime2) + "ns.");
		uiframe.getTextArea_test().append("\n---���˲�����ɣ�---\n");
		uiframe.getTextArea_test().append("\n������ʱ��" + (endTime1 - startTime1) + "ms," + (endTime2 - startTime2) + "ns.");
	
	}
	
	/**
	 * ��������   ��ü����ٻ��ʣ������ʣ�©���ʣ���ȷ�ʵ���������
	 * @param testpath
	 * @param uiframe
	 * @return
	 */
	public static int[] getData(String testpath, SFFrame uiframe) {

		int[] results = new int[3];
		int result0 = 0;
		int result1 = 0;
		int result2 = 0;
		int resulttmp ;
		
		String content = "";
		File file = new File(testpath);
		if (file.isFile()) {
			if (file.isFile()) {
				content = MailFileReader.readSingleFile(file);
				uiframe.getTextArea_test().append("path=" + file.getPath() + "\n");
				resulttmp = dotestFilte(content, uiframe);
				if (resulttmp == 0){
					result0 = result0 + 1;
				}else if (resulttmp == 1){
					result1 = result1 + 1;
				}else if (resulttmp == 2){
					result2 = result2 + 1;
				}
			}
		} else if (file.isDirectory()) {
			// ��������ļ�
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File sfile = new File(testpath + "\\" + filelist[i]);
				if (!sfile.isDirectory()) {
					// System.out.println("path=" + sfile.getPath());
//					uiframe.getTextArea_test().append("path=" + sfile.getPath() + "\n");
					content = MailFileReader.readSingleFile(sfile);
					resulttmp = dotestFilte(content, uiframe);
					if (resulttmp == 0){
						result0 = result0 + 1;
					}else if (resulttmp == 1){
						result1 = result1 + 1;
					}else if (resulttmp == 2){
						result2 = result2 + 1;
					}
				} else if (sfile.isDirectory()) {
					// �ݹ���ã���ȡ���ļ����µ��ļ�
					getData(testpath + "\\" + filelist[i], uiframe);
				}
			}
		} else {
			// System.out.println("���˲����ʼ�·���д�����ȷ���ļ�·����ȷ��\n");
			uiframe.getTextArea_test().append("���˲����ʼ�·���д�����ȷ���ļ�·����ȷ��\n");
		}
		results[0]=result0;
		results[1]=result1;
		results[2]=result2;
		
		return results;
	}

	/**
	 * �������� ��ÿ�� �ʼ����ı����з����жϣ��������
	 * 
	 * @param content
	 * @param uiframe
	 */
	public static int dotestFilte(String content, SFFrame uiframe) {
		int result = 2;
//		uiframe.getTextArea_test().append("---���ڽ��зִ�---�����ĵȺ�\n");
		// ���ȥ�����ôʵĴ�
		List<KeyWord> fkwslist = Segment.getFkwslist(content);
		// ֻ�����������ʸ��ʿ��������ݵĴ�
		fkwslist = TableP.selectTablep(fkwslist);
		// �����ı��������������
//		uiframe.getTextArea_test().append("---���ڼ���������---�����ĵȺ�\n\n");
		double[] txtp = Bayes.getTxtP(fkwslist);
		// �����������ʵı�ֵ�Ͳ�ֵ
		double rqhs = Calculator.getRatioQuotient(txtp[0], txtp[1]);
		double rdhs = Calculator.getRatioDifference(txtp[0], txtp[1]);
		// ������ʵ�ֵ
//		uiframe.getTextArea_test().append("���ʼ��������ʼ��ĸ����ǣ�" + txtp[0] + "\n");
//		uiframe.getTextArea_test().append("���ʼ��������ʼ��ĸ����ǣ�" + txtp[1] + "\n");
//		uiframe.getTextArea_test().append("�����ʼ������������ʼ��ĸ��ʱ��ǣ�" + rqhs + "\n");
//		uiframe.getTextArea_test().append("�����ʼ������������ʼ��ĸ��ʲ��ǣ�" + rdhs + "\n\n");
		// System.out.println("���ʼ��������ʼ��ĸ����ǣ�" + txtp[0]);
		// System.out.println("���ʼ��������ʼ��ĸ����ǣ�" + txtp[1]);
		// System.out.println("�����ʼ������������ʼ��ĸ��ʱ��ǣ�" + rqhs);
		// System.out.println("�����ʼ������������ʼ��ĸ��ʲ��ǣ�" + rdhs);
		// ���ݴ�С����ֵ����ֵ�����ж�
		if (txtp[0] < txtp[1]) {
			// System.out.println("\n���ʼ��������ʼ�!\n");
//			uiframe.getTextArea_test().append("���ʼ��������ʼ�!\n");
			result = 1;
		} else if (txtp[0] > txtp[1]) {
			// System.out.println("\n���ʼ��������ʼ�!\n");
//			uiframe.getTextArea_test().append("���ʼ��������ʼ�!\n");
			result = 0;
		} else {
			// System.out.println("\n����һ���쳣��������²�ѧ��ǳ���޷��жϣ��������£�\n");
//			uiframe.getTextArea_test().append("����һ���쳣��������²�ѧ��ǳ���޷��жϣ��������£�\n");
			result = 2;
		}
		return result;
	}

	// public static void main(String[] args) throws
	// MySQLIntegrityConstraintViolationException {
	// long startTime1 = System.currentTimeMillis();
	// long startTime2 = System.nanoTime();
	// String testpath = "G:/TrainSet/ham-5";
	//
	// //Filter(testpath);
	//
	// long endTime1 = System.currentTimeMillis();
	// long endTime2 = System.nanoTime();
	// System.out.println("\n������ʱ��" + (endTime1 - startTime1) + "ms," +
	// (endTime2 - startTime2) + "ns.");
	// }

}
