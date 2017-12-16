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
	 * 根据文件目录，读取正常邮件训练集目录下 的所有文件，包括子文件夹下的所有文件， 文件很多，
	 * 返回计算好num_t_c和num_nt_c这两个字段的list<KeyWord>
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

				// TODO 此处应该执行分词操作
				List<String> fwordslist = Segment.getFWordslist(content);

				resultkws = Counter.countHamDF(resultkws, fwordslist,countham);
				
				// return content;
			}
		} else if (file.isDirectory()) {
			//System.out.println("this is a directory!");
			// 获得所有文件
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File sfile = new File(hampath + "\\" + filelist[i]);
				if (!sfile.isDirectory()) {
					content = MailFileReader.readSingleFile(sfile);
					// System.out.println("before getout!"+content);
					// TODO 此处应该执行分词操作
					List<String> fwordslist = Segment.getFWordslist(content);

					/*for(int a=0;a<fwordslist.size();a++){
						System.out.println(fwordslist.get(a));
					}
					*/
					resultkws = Counter.countHamDF(resultkws, fwordslist,countham);
					// System.out.println("after getout!");

				} else if (sfile.isDirectory()) {
					// 递归调用，读取子文件夹下的文件
					rskwset = readFileHam(hampath + "\\" + filelist[i],countham);
					resultkws = Counter.countHamDFtmp(resultkws, rskwset);
				}
			}

		} else {
			System.out.println("训练文本路径有错误！请确定文件路径正确！");
		}
		return resultkws;
	}

	/*
	 * 根据文件目录，读取垃圾邮件训练集目录下 的所有文件，包括子文件夹下的所有文件， 文件很多
	 * 返回计算好num_t_nc和num_nt_nc这两个字段的list<KeyWord>
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

				// TODO 此处应该执行分词操作
				List<String> fwordslist = Segment.getFWordslist(content);

				resultkws = Counter.countSpamDF(resultkws, fwordslist,countspam);
				// return content;
			}
		} else if (file.isDirectory()) {
			//System.out.println("this is a directory!");
			// 获得所有文件
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File sfile = new File(spampath + "\\" + filelist[i]);
				if (!sfile.isDirectory()) {
					content = MailFileReader.readSingleFile(sfile);
					// System.out.println("before getout!"+content);
					// TODO 此处应该执行分词操作
					List<String> fwordslist = Segment.getFWordslist(content);

					resultkws = Counter.countSpamDF(resultkws, fwordslist,countspam);
					// System.out.println("after getout!");

				} else if (sfile.isDirectory()) {
					// 递归调用，读取子文件夹下的文件
					rskwset = readFileSpam(spampath + "\\" + filelist[i],countspam);
					resultkws = Counter.countSpamDFtmp(resultkws, rskwset);
				}
			}

		} else {
			System.out.println("训练文本路径有错误！请确定文件路径正确！");
		}
		return resultkws;
	}

	
	/*
	 * 函数功能  此方法用来读取统计邮件个数，读取该目录下 的所有文件，包括子文件夹下的所有文件
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
			// 获得所有文件
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File sfile = new File(filepath + "\\" + filelist[i]);
				if (!sfile.isDirectory()) {
					count = count + 1;
				} else if (sfile.isDirectory()) {
					// 递归调用，读取子文件夹下的文件
					countFile(filepath + "\\" + filelist[i]);
				}
			}

		}else{
			System.out.println("文件路径错误，请输入正确的路径！");
		}
		return count;
	}

	/*
	 * 根据单个文件路径来读取邮件内容，并对其进行拆分，返回邮件内容
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
	 * 根据单个文件对象来读取邮件内容，并对其进行拆分，返回邮件内容
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
	 * 根据BufferedReader文件流对象来读取邮件内容，并对其进行拆分，返回邮件内容
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
	 * 根据单个文件对象建立读取文件的BufferedReader流，
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
	 * 根据单个文件路径建立读取文件的BufferedReader流，
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
	 * 根据单一文件的路径来读取文件,并获取邮件的内容
	 */
	public static String readSingleFile(String sfilepath) {
		String content = "";
		File file = new File(sfilepath);
		// FileInputStream fileInputStream;
		if (!file.isFile()) {
			System.out.println(sfilepath + " is not a file!");
		} else if (file.isFile()) {
			// 两种方法任意一种即可
			// content = splitMail(sfilepath);
			content = splitMail(file);
		}
		return content;
	}

	/*
	 * 根据单一文件对象来读取文件,并获取邮件的内容
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
	 * readsinglefile("G:/TrainSet/ham-gb2312");//非法输入，应该输入具体的文件，而非目录 //
	 * readsinglefile("G:/TrainSet/ham-gb2312/20");
	 * readFile("G:/TrainSet/ham-5"); //
	 * readdirectory("G:/TrainSet/ham-gb2312/20");//非法输入，应该输入目录，而非具体的文件 }
	 */

}
