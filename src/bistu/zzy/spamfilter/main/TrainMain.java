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
	 * 函数功能 根据训练集路径对过滤器进行训练，训练前判断数据库是否有数据，以此来决定是首次训练还是增量学习
	 * 
	 * @param hampath
	 *            正常邮件训练集路径
	 * @param spampath
	 *            垃圾邮件训练集路径
	 * @param uiframe
	 *            界面对象
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
	 * 函数功能 根据训练集路径对过滤器进行首次训练
	 * 
	 * @param hampath
	 *            正常邮件训练集路径
	 * @param spampath
	 *            垃圾邮件训练集路
	 * @param uiframe
	 *            界面对象
	 */
	public static void trainFirst(String hampath, String spampath, SFFrame uiframe) {
		long startTime1 = System.currentTimeMillis();
		long startTime2 = System.nanoTime();
		// Segment.initSegmentation();
		uiframe.getTextArea_train().append("---首次训练此过滤器！---请耐心等候训练结束！-----\n");
		TxtData txtdata = TrainMain.getSetTxtData(hampath, spampath);
		// 将txtdata 对象写入数据库
		uiframe.getTextArea_train().append("---请耐心等候！---正在更新TxtData数据。。。。。\n");
		TableTxtdata.insertTxtdata(txtdata);
		uiframe.getTextArea_train().append("插入tabletxtdata数据库执行结束!\n");

		List<KeyWord> rskeywords1 = TrainMain.getSetKeywords(hampath, spampath);
		// 可以将相关数据存入数据库了，直接调用每个表的insert函数即可
		// 后续操作会改变list的词的数量 所以此处就应该写入数据库
		// System.out.println("请耐心等候！正在插入tablekeyword数据库。。。。。\n");
		uiframe.getTextArea_train().append("\n---请耐心等候！---正在更新tablekeyword数据库。。。。。\n");
		TableKeyword.insertTablekw(rskeywords1);
		// System.out.println("插入tablekw数据库执行结束!\n");
		uiframe.getTextArea_train().append("插入tablekw数据库执行结束!\n");

		List<KeyWord> rskeywords2 = TrainMain.reduceRatio(rskeywords1);
		// 可以将相关数据存入数据库了，直接调用每个表的insert函数即可
		// 后续操作会改变list的词的数量 所以此处就应该写入数据库
		// System.out.println("-----请耐心等候！-----正在插入tablep数据库。。。。。\n");
		uiframe.getTextArea_train().append("\n---请耐心等候！---正在更新tablep数据库。。。。。\n");
		TableP.insertTableP(rskeywords2);
		// System.out.println("插入tablep数据库完成！\n");
		uiframe.getTextArea_train().append("插入tablep数据库完成！\n");

		long endTime1 = System.currentTimeMillis();
		long endTime2 = System.nanoTime();
		// System.out.println("\n运算用时：" + (endTime1 - startTime1) + "ms," +
		// (endTime2 - startTime2) + "ns.");
		uiframe.getTextArea_train().append("\n---过滤器首次训练过程完成！---\n");
		uiframe.getTextArea_train().append("\n运算用时：" + (endTime1 - startTime1) + "ms," + (endTime2 - startTime2) + "ns.");
	}

	/**
	 * 函数功能	计算训练集的文本数量
	 * @param hampath
	 * @param spampath
	 * @return txtdata
	 */
	public static TxtData getSetTxtData(String hampath, String spampath) {
		/*
		 * 统计文档数等相关信息，并将其存入一个TxtData对象中去，以便其他函数使用
		 */
		int txthamnum = MailFileReader.countFile(hampath);
		int txtspamnum = MailFileReader.countFile(spampath);
		int txtnum = txthamnum + txtspamnum;
		// chikwsum 该字段是利用chi降维后产生的特征词的总数
		// int chikwsum = rskeywords.size();
		TxtData txtdata = new TxtData();
		txtdata.setTxtnum(txtnum);
		txtdata.setTxthamnum(txthamnum);
		txtdata.setTxtspamnum(txtspamnum);
		// txtdata.setChikwsum(chikwsum);

		return txtdata;
	}

	/**
	 * 函数功能	统计训练集中的新的特征词
	 * @param hampath
	 * @param spampath
	 * @return List<KeyWord>
	 */
	public static List<KeyWord> getSetKeywords(String hampath, String spampath) {
		TxtData txtdata = TrainMain.getSetTxtData(hampath, spampath);

		int txthamnum = txtdata.getTxthamnum();
		int txtspamnum = txtdata.getTxtspamnum();

		// 获得正常邮件训练集中的特征词，并统计其文档频率
		List<KeyWord> rskeywords1 = MailFileReader.readFileHam(hampath, txthamnum);
		// 获得垃圾邮件训练集中的特征词，并统计其文档频率
		List<KeyWord> rskeywords2 = MailFileReader.readFileSpam(spampath, txtspamnum);
		// 此时已用不到分词器所以释放
		// Segment.CLibrary.Instance.NLPIR_Exit();
		// 将每个词的四个文档频率字段的值整合在一起，训练结束前将此结果写入数据库
		List<KeyWord> rskeywords3 = Counter.getSumDF(rskeywords2, rskeywords1, txtdata);

		return rskeywords3;
	}

	/**
	 * 函数功能	对特征词list进行降维，然后计算每个词的Bayes分类概率
	 * @param rskeywords
	 * @return rskeywords
	 */
	public static List<KeyWord> reduceRatio(List<KeyWord> rskeywords) {
		TxtData txtdata = TableTxtdata.selectTxtdata();

		// 利用文档频率进行第一次降维，得到一个List<KeyWord>
		rskeywords = DF.useDF(rskeywords);
		// 计算每个词的CHIham和CHIspam，并记录下来
		rskeywords = CHI.getCHI(rskeywords, txtdata);

		// 利用chi进行第二次降维，得到一个list
		rskeywords = CHI.useCHI(rskeywords, txtdata);

		// 利用通过chi降维后的list，计算剩余特征词的分类概率
		rskeywords = Bayes.getWordsP(rskeywords, txtdata);

		return rskeywords;
	}

}
// 程序到此结束，以下为调试代码

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
// System.out.println("\n运算用时：" + (endTime1 - startTime1) + "ms," +
// (endTime2 - startTime2) + "ns.");
// }

// 可以将相关数据存入数据库了，直接调用每个表的insert函数即可
// 后续操作会改变list的词的数量 所以此处就应该写入数据库
// System.out.println("-----请耐心等候！-----正在插入tablechi数据库。。。。。\n");
// uiframe.getTextArea().append("-----请耐心等候！-----正在插入tablechi数据库。。。。。\n");
// TableCHI.insertTableCHI(rskeywords2);
// System.out.println("插入tablechi数据库执行结束!\n");
// uiframe.getTextArea().append("插入tablechi数据库执行结束!\n");

// for (int i = 0; i < rskeywords3.size(); i++) {
// KeyWord kword = rskeywords3.get(i);
// System.out.print("词名是：" + kword.getName());
// System.out.print(" hamdftc：" + kword.getNum_t_c());
// System.out.print(" hamDFntc是：" + kword.getNum_nt_c());
// System.out.print(" spamdftnc：" + kword.getNum_t_nc());
// System.out.print(" spamDFntnc是：" + kword.getNum_nt_nc() + "\n");
// }
//
// for (int i = 0; i < rskeywords6.size(); i++) {
// KeyWord kword = rskeywords6.get(i);
// System.out.print("词名是：" + kword.getName());
// System.out.print(" 正常邮件中出现次数：" + kword.getNum_t_c());
// // System.out.print(" hamDFntc是：" + kword.getNum_nt_c());
// System.out.print(" 垃圾邮件中出现次数：" + kword.getNum_t_nc());
// // System.out.print(" spamDFntnc是：" + kword.getNum_nt_nc());
// System.out.print(" chiham是：" + kword.getChiham());
// System.out.print(" chispam是：" + kword.getChispam() + "\n");
// }
//
// for (int i = 0; i < rskeywords.size(); i++) {
// KeyWord kword = rskeywords.get(i);
// System.out.print("词名是：" + kword.getName());
// System.out.print(" Pham是：" + kword.getWordpham());
// System.out.print(" Pspam是：" + kword.getWordpspam() + "\n");
// }
