package bistu.zzy.spamfilter.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import bistu.zzy.spamfilter.object.KeyWord;
import bistu.zzy.spamfilter.sgmt.Segment;
import bistu.zzy.spamfilter.util.Counter;

public class MailFileReader {

	/*
	 * �����ļ�Ŀ¼����ȡ�����ʼ�ѵ����Ŀ¼�� �������ļ����������ļ����µ������ļ��� �ļ��ܶ࣬
	 * ���ؼ����num_t_c��num_nt_c�������ֶε�list<KeyWord>
	 */
	public static List<KeyWord> readFileHam(String hampath,int countham) {
		List<KeyWord> resultkws = new ArrayList<KeyWord>();
		// List<KeyWord> rskwsingle = new ArrayList<KeyWord>();
		List<KeyWord> rskwset = new ArrayList<KeyWord>();
		
		String content = "";
		File file = new File(hampath);
		if (file.isFile()) {

			if (file.isFile()) {
				content = MailFileReader.readSingleFile(file);

				// TODO �˴�Ӧ��ִ�зִʲ���
				List<String> fwordslist = Segment.getFWordslist(content);

				resultkws = Counter.countHamDF(resultkws, fwordslist,countham);
				
				// return content;
			}
		} else if (file.isDirectory()) {
			//System.out.println("this is a directory!");
			// ��������ļ�
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File sfile = new File(hampath + "\\" + filelist[i]);
				if (!sfile.isDirectory()) {
					content = MailFileReader.readSingleFile(sfile);
					// System.out.println("before getout!"+content);
					// TODO �˴�Ӧ��ִ�зִʲ���
					List<String> fwordslist = Segment.getFWordslist(content);

					/*for(int a=0;a<fwordslist.size();a++){
						System.out.println(fwordslist.get(a));
					}
					*/
					resultkws = Counter.countHamDF(resultkws, fwordslist,countham);
					// System.out.println("after getout!");

				} else if (sfile.isDirectory()) {
					// �ݹ���ã���ȡ���ļ����µ��ļ�
					rskwset = readFileHam(hampath + "\\" + filelist[i],countham);
					resultkws = Counter.countHamDFtmp(resultkws, rskwset);
				}
			}

		} else {
			System.out.println("ѵ���ı�·���д�����ȷ���ļ�·����ȷ��");
		}
		return resultkws;
	}

	/*
	 * �����ļ�Ŀ¼����ȡ�����ʼ�ѵ����Ŀ¼�� �������ļ����������ļ����µ������ļ��� �ļ��ܶ�
	 * ���ؼ����num_t_nc��num_nt_nc�������ֶε�list<KeyWord>
	 */
	public static List<KeyWord> readFileSpam(String spampath,int countspam) {
		List<KeyWord> resultkws = new ArrayList<KeyWord>();
		// List<KeyWord> rskwsingle = new ArrayList<KeyWord>();
		List<KeyWord> rskwset = new ArrayList<KeyWord>();

		//int countspam = countFile(spampath);
		String content = "";
		File file = new File(spampath);
		if (file.isFile()) {

			if (file.isFile()) {
				content = MailFileReader.readSingleFile(file);

				// TODO �˴�Ӧ��ִ�зִʲ���
				List<String> fwordslist = Segment.getFWordslist(content);

				resultkws = Counter.countSpamDF(resultkws, fwordslist,countspam);
				// return content;
			}
		} else if (file.isDirectory()) {
			//System.out.println("this is a directory!");
			// ��������ļ�
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File sfile = new File(spampath + "\\" + filelist[i]);
				if (!sfile.isDirectory()) {
					content = MailFileReader.readSingleFile(sfile);
					// System.out.println("before getout!"+content);
					// TODO �˴�Ӧ��ִ�зִʲ���
					List<String> fwordslist = Segment.getFWordslist(content);

					resultkws = Counter.countSpamDF(resultkws, fwordslist,countspam);
					// System.out.println("after getout!");

				} else if (sfile.isDirectory()) {
					// �ݹ���ã���ȡ���ļ����µ��ļ�
					rskwset = readFileSpam(spampath + "\\" + filelist[i],countspam);
					resultkws = Counter.countSpamDFtmp(resultkws, rskwset);
				}
			}

		} else {
			System.out.println("ѵ���ı�·���д�����ȷ���ļ�·����ȷ��");
		}
		return resultkws;
	}

	
	/*
	 * ��������  �˷���������ȡͳ���ʼ���������ȡ��Ŀ¼�� �������ļ����������ļ����µ������ļ�
	 */
	public static int countFile(String filepath) {
		int count = 0;
		File file = new File(filepath);
		if (!file.isDirectory()) {
			if (file.isFile()) {
				count = 1;
				return count;
			}
		} else if (file.isDirectory()) {
			//System.out.println("this is a directory!");
			// ��������ļ�
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File sfile = new File(filepath + "\\" + filelist[i]);
				if (!sfile.isDirectory()) {
					count = count + 1;
				} else if (sfile.isDirectory()) {
					// �ݹ���ã���ȡ���ļ����µ��ļ�
					countFile(filepath + "\\" + filelist[i]);
				}
			}

		}else{
			System.out.println("�ļ�·��������������ȷ��·����");
		}
		return count;
	}

	/*
	 * ���ݵ����ļ�·������ȡ�ʼ����ݣ���������в�֣������ʼ�����
	 */
	public static String splitMail(String sfilepath) {
		String content = "";
		String data = null;
		BufferedReader bfreader = getBfreader(sfilepath);
		try {
			while ((data = bfreader.readLine()) != null) {
				if (data.equals(""))
					break;
			}
			while ((data = bfreader.readLine()) != null) {
				content = content + data;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	/*
	 * ���ݵ����ļ���������ȡ�ʼ����ݣ���������в�֣������ʼ�����
	 */
	public static String splitMail(File sfile) {
		String content = "";
		String data = null;
		BufferedReader bfreader = getBfreader(sfile);

		try {
			while ((data = bfreader.readLine()) != null) {
				if (data.equals(""))
					break;
			}
			while ((data = bfreader.readLine()) != null) {
				content = content + data;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	/*
	 * ����BufferedReader�ļ�����������ȡ�ʼ����ݣ���������в�֣������ʼ�����
	 */
	public static String splitMail(BufferedReader bfreader) {
		String content = "";
		String data = null;
		try {
			while ((data = bfreader.readLine()) != null) {
				if (data.equals(""))
					break;
			}
			while ((data = bfreader.readLine()) != null) {
				content = content + data;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;

	}

	/*
	 * ���ݵ����ļ���������ȡ�ļ���BufferedReader����
	 */
	public static BufferedReader getBfreader(File sfile) {
		BufferedReader bfreader = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(sfile);
			InputStreamReader reader = new InputStreamReader(fileInputStream);
			bfreader = new BufferedReader(reader);
			// content = splitmail(bfreader);
			// System.out.println(content);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bfreader;
	}

	/*
	 * ���ݵ����ļ�·��������ȡ�ļ���BufferedReader����
	 */
	public static BufferedReader getBfreader(String sfilepath) {
		BufferedReader bfreader = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(sfilepath);
			InputStreamReader reader = new InputStreamReader(fileInputStream);
			bfreader = new BufferedReader(reader);
			// content = splitmail(bfreader);
			// System.out.println(content);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bfreader;
	}

	/*
	 * ���ݵ�һ�ļ���·������ȡ�ļ�,����ȡ�ʼ�������
	 */
	public static String readSingleFile(String sfilepath) {
		String content = "";
		File file = new File(sfilepath);
		// FileInputStream fileInputStream;
		if (!file.isFile()) {
			System.out.println(sfilepath + " is not a file!");
		} else if (file.isFile()) {
			// ���ַ�������һ�ּ���
			// content = splitMail(sfilepath);
			content = splitMail(file);
		}
		return content;
	}

	/*
	 * ���ݵ�һ�ļ���������ȡ�ļ�,����ȡ�ʼ�������
	 */
	public static String readSingleFile(File singlefile) {
		String content = "";
		// File file = new File(sfilepath);
		// FileInputStream fileInputStream;
		if (!singlefile.isFile()) {
			System.out.println(singlefile + " is not a file!");
		} else if (singlefile.isFile()) {
			content = splitMail(singlefile);
		}
		return content;
	}

	public static boolean isRightFile(String path){
		boolean rightfile = false;
		File file = new File(path);
		if (file.isFile()) {
			rightfile = true;
		} else if (file.isDirectory()) {
			rightfile = true;
		}else{
			return rightfile;
		}
		
		return rightfile;
	}
	
	/*
	 * public static void main(String[] agrs) { //
	 * readsinglefile("G:/TrainSet/ham-gb2312");//�Ƿ����룬Ӧ�����������ļ�������Ŀ¼ //
	 * readsinglefile("G:/TrainSet/ham-gb2312/20");
	 * readFile("G:/TrainSet/ham-5"); //
	 * readdirectory("G:/TrainSet/ham-gb2312/20");//�Ƿ����룬Ӧ������Ŀ¼�����Ǿ�����ļ� }
	 */

}
