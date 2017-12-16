package bistu.zzy.spamfilter.sgmt;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.sun.jna.Library;
import com.sun.jna.Native;

import bistu.zzy.spamfilter.object.KeyWord;

public class Segment {

	// 定义接口CLibrary，继承自com.sun.jna.Library
	public interface CLibrary extends Library {
		/*
		 * 定义并初始化接口的静态变量 这一个语句是来加载dll的，
		 * 注意dll文件的路径可以是绝对路径也可以是相对路径，只需要填写dll的文件名，不能加后缀。
		 */
		CLibrary Instance = (CLibrary) Native.loadLibrary("NLPIR", CLibrary.class);

		// D://programme//JEEMars//JnaTest_V1//NLPIR
		// printf函数声明
		public int NLPIR_Init(byte[] sDataPath, int encoding, byte[] sLicenceCode);

		public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);

		public String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit, boolean bWeightOut);

		// 保存用户词汇到用户词典
		public int NLPIR_SaveTheUsrDic();

		// 导入用户自定义词典：自定义词典路径，bOverwrite=true表示替代当前的自定义词典，false表示添加到当前自定义词典后
		public int NLPIR_ImportUserDict(String sFilename, boolean bOverwrite);

		public void NLPIR_Exit();
	}

	public static String transString(String aidString, String ori_encoding, String new_encoding) {
		try {
			return new String(aidString.getBytes(ori_encoding), new_encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 函数功能 初始化分词器
	 */
	public static void initSegmentation() {
		String argu = "";
		// String system_charset = "GBK";//GBK----0
		String system_charset = "GBK";
		int charset_type = 1;
		// int charset_type = 0;
		// 调用printf打印信息
		int init_flag = 0;
		try {
			init_flag = CLibrary.Instance.NLPIR_Init(argu.getBytes(system_charset), charset_type,
					"0".getBytes(system_charset));
			// System.err.println(init_flag);
			if (0 == init_flag) {
				System.err.println("初始化失败！");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * 注意事项，应在分析完文档后释放分词器 1.在分析完所有文档后释放分词器 2.每次分析一个文档时新初始化一个，该文档分析结束后即释放
		 * 建议使用方法一 CLibrary.Instance.NLPIR_Exit();
		 */

	}

	/*
	 * 函数功能 根据输入的字符，调用分词函数，拆分函数，过滤函数，对其进行过滤，剔除无用词，如标点符号 结果：得到list 参数解释 由分词后得到的
	 * 由“ 词/词性 ”组成的字符串词组
	 */
	public static List<String> getFWordslist(String sinput) {

		String[] sgmtwords = Segment.getSegmentWords(Segment.getSegmentString(sinput));

		Set<String> fwordset = getFirstWords(sgmtwords);

		List<String> fwliststr = new ArrayList<String>(fwordset);

		// for (int i = 0; i < fwliststr.size(); i++) {
		// String kwstr1 = fwliststr.get(i);
		// for (int j = i + 1; j < fwliststr.size();) {
		// String kwstr2 = fwliststr.get(j);
		// if (kwstr1.equals(kwstr2)) {
		// System.out.println("set的去重效果并未达成！");
		// fwliststr.remove(j);
		// j = j - 1;
		// }
		// j++;
		// }
		// }
		//
		// for (int a = 0; a < fwliststr.size(); a++) {
		// System.out.println(fwliststr.get(a));
		// }

		return fwliststr;
	}

	public static List<KeyWord> getFkwslist(String sinput) {

		List<KeyWord> fkwslist = new ArrayList<KeyWord>();

		String[] sgmtwords = Segment.getSegmentWords(Segment.getSegmentString(sinput));

		Set<String> fwordset = getFirstWords(sgmtwords);

		List<String> fwliststr = new ArrayList<String>(fwordset);

		for (int i = 0; i < fwliststr.size(); i++) {
			KeyWord nkw = new KeyWord();
			String name = fwliststr.get(i);
			nkw.setName(name);
			fkwslist.add(nkw);
		}

		return fkwslist;
	}

	/*
	 * 函数功能 根据输入的字符，调用分词函数，拆分函数，过滤函数，对其进行过滤，剔除无用词，如标点符号 结果：得到string型词组，格式为词，词性
	 * 
	 * 参数解释 由分词后得到的 由“ 词/词性 ”组成的字符串词组
	 */
	public static String[] getFirstWords(String sinput) {

		Set<String> fwordset = null;

		String[] sgmtwords = Segment.getSegmentWords(Segment.getSegmentString(sinput));

		fwordset = getFirstWords(sgmtwords);

		List<String> fwordslist = new ArrayList<String>(fwordset);

		// TODO 将去除重复词的文档特征词放入string数组还是list中，还是set 中？
		// 这里还是放入string数组中
		String[] fwords = new String[350];

		for (int i = 0; i < fwordslist.size(); i++) {
			fwords[i] = fwordslist.get(i);
		}

		return fwords;
	}

	/*
	 * 函数功能 根据输入的字符串（文本），对其进行分词，得到分词结果string 分词结果：字符串类型，格式为“。。。词/词性[]词/词性。。。。”
	 * 
	 * 参数解释 要分词的文本或字符串
	 */
	public static String getSegmentString(String sInput) {
		// 用于接收分词结果，字符串类型，格式为“。。。词/词性[]词/词性。。。。”
		String sgmtstr = null;
		/*
		 * 初始化分词器,不应该 在这里调用， 对于处理多个文件的话，就会初始化多个，没这个必要，只会浪费内存，
		 * 放在main方法里，结束时刚好可以释放
		 */
		// initSegmentation();

		// 添加用户词典分词
//		int count = 0;
//		String userDir = "spamdict.txt"; // 用户词典路径
//		//byte[] userDirb = userDir.getBytes();
//		count = CLibrary.Instance.NLPIR_ImportUserDict(userDir, false);
//		System.out.println("\n导入用户词个数：\t" + count);

		sgmtstr = CLibrary.Instance.NLPIR_ParagraphProcess(sInput, 1);
		// String nativeStr = new String(nativeBytes, 0,
		// nativeBytes.length,"utf-8");
		// System.out.println("待分词文本为： " + sInput);
		// System.out.println("分词结果为： " + sgmtstr);

		return sgmtstr;
	}

	/*
	 * 函数功能 根据输入的分词结果字符串，对其进行字符串拆分， 结果：得到string型词组，格式为“词/词性”
	 * 
	 * 参数解释 要分词后得到的 由“ 词/词性 ”组成的字符串
	 */
	public static String[] getSegmentWords(String sgmtstr) {
		String[] sgmtwords = null;
		try {
			/*
			 * 拆分字符串方法二.用StringTokenizer类进行操作 可以避免连续空格 的问题，但是，数组长度不可以自动增加， TODO
			 * 在运行G:/TrainSet/spam-gb2312/16文件时，出现连续空格无法消除的问题。
			 * 经查看原始文件，那不是空格，而是而是一种未知符号， 经测试，该未知符号为全角空格
			 * 所以，StringTokenizer类进行拆分字符串 可以忽略连续空格的结论是正确的
			 */
			// 按照空格进行截取
			StringTokenizer token1 = new StringTokenizer(sgmtstr, " ");
			// StringTokenizer token2 = new StringTokenizer(str2, "/");
			// 按照"/"进行截取
			// 定义一个字符串数组,用来存储拆分后的词组。
			sgmtwords = new String[2000];
			// 解决全角空格非法存储问题，将其剔除，不再存储
			int i = 0;
			String wpos = "";
			while (token1.hasMoreTokens()) {
				// 将分割开的子字符串放入wpos中，然后判断是否为全角空格，不是才存储进数组
				wpos = token1.nextToken();
				if (!wpos.equals("　")) {
					sgmtwords[i] = wpos;
					i++;
				}
			}
			// System.out.println("获得-词/词性-的string组！ ");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// for (int a = 0; a < sgmtwords.length; a++) {
		// System.out.println(sgmtwords[a]);
		// }
		return sgmtwords;
	}

	/*
	 * 函数功能 根据输入的分词结果字符串，对其进行字符串拆分， 结果：得到List<String>型词组，格式为“词/词性”
	 * 
	 * 参数解释 要分词后得到的 由“ 词/词性 ”组成的字符串
	 */
	public static List<String> getSegmentWordslist(String sgmtstr) {
		List<String> sgmtwdslist = new ArrayList<String>();
		try {
			// 按照空格进行截取
			StringTokenizer token1 = new StringTokenizer(sgmtstr, " ");

			// 解决全角空格非法存储问题，将其剔除，不再存储
			int i = 0;
			String wpos = "";
			while (token1.hasMoreTokens()) {
				// 将分割开的子字符串放入wpos中，然后判断是否为全角空格，不是才存储进数组
				wpos = token1.nextToken();
				if (!wpos.equals("　")) {
					sgmtwdslist.add(wpos);
					i++;
				}

			}
			// System.out.println("获得-词/词性-的string组！ ");

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return sgmtwdslist;
	}

	/*
	 * 函数功能 根据输入的拆分好的分词词组，对其进行过滤，剔除无用词，如标点符号 结果：得到string型词组，格式为词，词性
	 * 
	 * 参数解释 由分词后得到的 由“ 词/词性 ”组成的字符串词组
	 */
	public static Set<String> getFirstWords(String[] sgmtwords) {
		/**
		 * 定义一个set，利用hashset无序的集合，不重复的存储获得的词， 即，一篇文档中的重复出现的词不会被记录多次，只记录一次
		 * 但是，若从set中逐个读出数据，需将其转化成List<String>;
		 */
		Set<String> fwordset = new HashSet<String>();
		try {
			/*
			 * 2.用StringTokenizer类进行操作 可以避免连续空格 的问题，但是，数组长度不可以自动增加，
			 */

			// TODO 经测试，此处无法用list，因为list中，将拆开的词add入list后，无法准确获取
			// List<String> array2 = new ArrayList<String>();

			// 定义一个字符串数组,用来存储二次拆分后的词及词性。
			String[] array2 = new String[2];
			array2[0]="";
			array2[1]="o";

			// int wiki = 0;

			for (int i = 0; i < sgmtwords.length; i++) {
				if (sgmtwords[i] != null) {
					int k = 0;
					// 按照"/"进行截取
					StringTokenizer token2 = new StringTokenizer(sgmtwords[i], "/");
					while (token2.hasMoreTokens()) {
						array2[k] = token2.nextToken();// 将分割开的子字符串放入数组中
						k++;
						if(k>1)break;
					}
					/*
					 * TODO
					 * 经如下代码测试，如果，含有多个“/”，则分隔后会产生多个字符串，用来临时存储的数组array的长度应该大于2，
					 * 如含有网址，http://www.baidu.com,则分词结果为http://www.baidu.com/xu
					 * 此时按“/”分隔会破坏该网址的完整性，产生3个字符串，http:,www.baidu.com,xu
					 * 若含有网址，http://www.hecaitou.com/blogs/hecaitou/archives/
					 * 122023.aspx, 则拆分字符串后会产生更多的字符。
					 */
					// int c = 0;
					// for(int a=0;a<array2.length;a++){
					// if(array2[a]!=null)c++;
					// }
					// System.out.println("这是每个按照斜杠分隔后，产生几个字符串：------------------------------"+c);

					/*
					 * TODO 用正则表达式进行词性匹配,然后过滤
					 * n名词，t时间词，s处所词，f方位词，v动词，a形容词，b区别词，z状态词，r代词
					 * m数词，q量词，d副词，p介词，c连词，u助词，e叹词，y语气词，o拟声词，h前缀 k后缀，x字符串，w标点符号
					 */
					if (!array2[1].matches("^(nr|rr|z|r|m|q|d|p|c|u|e|y|o|h|k|x|w).*")) {
						// fwords[wiki] = array2[0];
						// wiki++;
						fwordset.add(array2[0]);// 将不是无用词的词。不重复的放入到set集合中，
						// System.out.println("this is test set!----" +
						// array2[0]);
					}

					// if ((!array2[1].equals("wt") &&
					// (!array2[1].equals("wd")))) {
					// fwords[wiki] = array2[0];
					// wiki++;
					// }
				}
			}

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return fwordset;
	}

	/*
	 * 函数功能 根据输入的拆分好的分词词组，对其进行过滤，剔除无用词，如标点符号 结果：得到string型词组，格式为词，词性
	 * 
	 * 参数解释 由分词后得到的 由“ 词/词性 ”组成的字符串词组
	 */
	public static Set<String> getFirstWords(List<String> sgmtwdslist) {
		/**
		 * 定义一个set，利用hashset无序的集合，不重复的存储获得的词， 即，一篇文档中的重复出现的词不会被记录多次，只记录一次
		 * 但是，若从set中逐个读出数据，需将其转化成List<String>;
		 */
		Set<String> fwordset = new HashSet<String>();
		try {

			// 定义一个字符串数组,用来存储拆分后的词组。
			String[] array2 = new String[10];
			// int wiki = 0;

			for (int i = 0; i < sgmtwdslist.size(); i++) {
				if (sgmtwdslist.get(i) != null) {
					int k = 0;
					// 按照"/"进行截取
					StringTokenizer token2 = new StringTokenizer(sgmtwdslist.get(i), "/");
					while (token2.hasMoreTokens()) {
						array2[k] = token2.nextToken();// 将分割开的子字符串放入数组中
						k++;
					}

					/*
					 * TODO 用正则表达式进行词性匹配,然后过滤
					 * n名词，t时间词，s处所词，f方位词，v动词，a形容词，b区别词，z状态词，r代词
					 * m数词，q量词，d副词，p介词，c连词，u助词，e叹词，y语气词，o拟声词，h前缀 k后缀，x字符串，w标点符号
					 */
					if (!array2[1].matches("^(nr|rr|z|r|m|q|d|p|c|u|e|y|o|h|k|x|w).*")) {
						// fwords[wiki] = array2[0];
						// wiki++;
						fwordset.add(array2[0]);// 将不是无用词的词。不重复的放入到set集合中，
					}
				}
			}

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return fwordset;
	}

}
