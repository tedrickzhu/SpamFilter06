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
		// "随着互联网的快速发展．电子邮件逐渐成为人们日常生活中快捷、经济、不可替代、不可缺失的重要的沟通方式。电子邮件的出现极大的解放了等待信件中的人们，让人们可以在任何时间，任何地点收发邮件，";
		// sinput = "我 是 朱正义，我每天 过得都很开心！生活很美好，何必自找烦恼！";
		Segment.initSegmentation();
		readFile(hamfilepath);
		Segment.CLibrary.Instance.NLPIR_Exit();
		long endTime1 = System.currentTimeMillis();
		long endTime2 = System.nanoTime();

		System.out.println("\n运算用时：" + (endTime1 - startTime1) + "ms," + (endTime2 - startTime2) + "ns.");

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
		System.out.println("这是所有分词的个数：" + countsgmtw);
		System.out.println("这是过滤后的分词的个数：" + countfw);
		System.out.println("---------------------------------------------------------------");

		/*
		 * String[] kwds = Segment.getFirstWords(content);
		 * System.out.println("去除，！后的分词结果：");
		 * 
		 * for (int i = 0; i < kwds.length; i++) { if (kwds[i] != null) {
		 * System.out.println(kwds[i]); } }
		 */

	}

	/*
	 * 根据文件目录，读取该目录下 的所有文件，包括子文件夹下的所有文件， 文件很多，不应该返回值，应该调用其他函数继续执行
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

				// TODO 此处应该执行分词操作
				getOUT(content);
				// return content;
			}
		} else if (file.isDirectory()) {
			System.out.println("this is a directory!");
			// 获得所有文件
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File sfile = new File(filepath + "\\" + filelist[i]);
				if (!sfile.isDirectory()) {
					System.out.println("path=" + sfile.getPath());
					// System.out.println("absolutepath=" +
					// sfile.getAbsolutePath());
					// System.out.println("name=" + sfile.getName());
					content = MailFileReader.readSingleFile(sfile);

					// TODO 此处应该执行分词操作
					getOUT(content);

				} else if (sfile.isDirectory()) {
					// 递归调用，读取子文件夹下的文件
					readFile(filepath + "\\" + filelist[i]);
				}
			}

		}else {
			System.out.println("训练文本路径有错误！请确定文件路径正确！");
		}
		// return content;
	}

}
