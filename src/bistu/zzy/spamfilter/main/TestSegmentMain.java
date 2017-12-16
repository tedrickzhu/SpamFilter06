package bistu.zzy.spamfilter.main;

import java.io.File;

import bistu.zzy.spamfilter.io.MailFileReader;
import bistu.zzy.spamfilter.sgmt.Segment;

public class TestSegmentMain {

	public static void main(String[] args) throws Exception {

		long startTime1 = System.currentTimeMillis();
		long startTime2 = System.nanoTime();

		String hamfilepath = "G:/TrainSet/ham-gb2312-50";
		String spamfilepath = "G:/TrainSet/spam-gb2312-50";
		// String sinput = null;
		// sinput = getMail();
		// sinput =
		// "���Ż������Ŀ��ٷ�չ�������ʼ��𽥳�Ϊ�����ճ������п�ݡ����á��������������ȱʧ����Ҫ�Ĺ�ͨ��ʽ�������ʼ��ĳ��ּ���Ľ���˵ȴ��ż��е����ǣ������ǿ������κ�ʱ�䣬�κεص��շ��ʼ���";
		// sinput = "�� �� �����壬��ÿ�� ���ö��ܿ��ģ���������ã��α����ҷ��գ�";
		Segment.initSegmentation();
		readFile(hamfilepath);
		Segment.CLibrary.Instance.NLPIR_Exit();
		long endTime1 = System.currentTimeMillis();
		long endTime2 = System.nanoTime();

		System.out.println("\n������ʱ��" + (endTime1 - startTime1) + "ms," + (endTime2 - startTime2) + "ns.");

	}

	public static void getOUT(String content) {

		String[] fwords = Segment.getFirstWords(content);

		String sgmtstr = Segment.getSegmentString(content);
		String[] sgmtwords = Segment.getSegmentWords(sgmtstr);

		int countsgmtw = 0;
		int countfw = 0;

		for (int i = 0; i < sgmtwords.length; i++) {
			// System.out.println(fwords[i]);
			if (sgmtwords[i] != null)
				countsgmtw++;
		}
		for (int i = 0; i < fwords.length; i++) {
			// System.out.println(fwords[i]);
			if (fwords[i] != null)
				countfw++;
		}
		System.out.println("�������зִʵĸ�����" + countsgmtw);
		System.out.println("���ǹ��˺�ķִʵĸ�����" + countfw);
		System.out.println("---------------------------------------------------------------");

		/*
		 * String[] kwds = Segment.getFirstWords(content);
		 * System.out.println("ȥ��������ķִʽ����");
		 * 
		 * for (int i = 0; i < kwds.length; i++) { if (kwds[i] != null) {
		 * System.out.println(kwds[i]); } }
		 */

	}

	/*
	 * �����ļ�Ŀ¼����ȡ��Ŀ¼�� �������ļ����������ļ����µ������ļ��� �ļ��ܶ࣬��Ӧ�÷���ֵ��Ӧ�õ���������������ִ��
	 */
	public static void readFile(String filepath) {
		String content = "";
		File file = new File(filepath);
		if (file.isFile()) {
			System.out.println("this is a file, not a directory!");
			System.out.println("path=" + file.getPath());
			System.out.println("absolutepath=" + file.getAbsolutePath());
			System.out.println("name=" + file.getName());

			if (file.isFile()) {
				content = MailFileReader.readSingleFile(file);

				// TODO �˴�Ӧ��ִ�зִʲ���
				getOUT(content);
				// return content;
			}
		} else if (file.isDirectory()) {
			System.out.println("this is a directory!");
			// ��������ļ�
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File sfile = new File(filepath + "\\" + filelist[i]);
				if (!sfile.isDirectory()) {
					System.out.println("path=" + sfile.getPath());
					// System.out.println("absolutepath=" +
					// sfile.getAbsolutePath());
					// System.out.println("name=" + sfile.getName());
					content = MailFileReader.readSingleFile(sfile);

					// TODO �˴�Ӧ��ִ�зִʲ���
					getOUT(content);

				} else if (sfile.isDirectory()) {
					// �ݹ���ã���ȡ���ļ����µ��ļ�
					readFile(filepath + "\\" + filelist[i]);
				}
			}

		}else {
			System.out.println("ѵ���ı�·���д�����ȷ���ļ�·����ȷ��");
		}
		// return content;
	}

}
