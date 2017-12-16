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
	 * 函数功能 根据训练集路径对过滤器进行增量学习训练
	 * 
	 * @param hampath
	 *            正常邮件训练集路径
	 * @param spampath
	 *            垃圾邮件训练集路径
	 * @param uiframe
	 *            界面对象
	 */
	public static void TrainStudy(String hampath, String spampath, SFFrame uiframe) {
		long startTime1 = System.currentTimeMillis();
		long startTime2 = System.nanoTime();
		// Segment.initSegmentation();
		uiframe.getTextArea_train().append("---欢迎再次执行训练过程！---请耐心等候训练结束！。。。。。\n");
		// 计算新训练集的文本数量
		uiframe.getTextArea_train().append("\n-----请耐心等候！-----正在获取新训练集的文本数量。。。。。\n");
		TxtData txtdata = TrainMain.getSetTxtData(hampath, spampath);
		// 更新数据库中训练集文本数量的记录
		TxtData sumtxtdata = AutoStudy.getSumTxtdata(txtdata);
		// 将新的记录sumtxtdata 对象写入数据库
		uiframe.getTextArea_train().append("---请耐心等候！---正在更新tabletxtdata数据库的记录。。。。。\n");
		TableTxtdata.insertTxtdata(sumtxtdata);
		uiframe.getTextArea_train().append("更新tabletxtdata数据库执行结束!\n");

		uiframe.getTextArea_train().append("\n---请耐心等候！---正在获取新训练集的特征词。。。。。\n");
		List<KeyWord> newkeywords = TrainMain.getSetKeywords(hampath, spampath);

		// 获得新词及新数据后，需要把旧数据提出，二者融合，然后降维
		uiframe.getTextArea_train().append("-----请耐心等候！-----正在学习新训练集的特征词。。。。。\n");
		List<KeyWord> sumkeywords = AutoStudy.getSumKeywords(newkeywords, sumtxtdata);
		// 可以将相关数据存入数据库了，直接调用每个表的insert函数即可
		// 后续操作会改变list的词的数量 所以此处就应该写入数据库
		//System.out.println("---请耐心等候！---正在插入tablekeyword数据库。。。。。\n");
		uiframe.getTextArea_train().append("---请耐心等候！正在根据学习到的新训练集的特征词更新tablekeyword数据库。。。。。\n");
		TableKeyword.insertTablekw(sumkeywords);
		//System.out.println("插入tablekw数据库执行结束!\n");
		uiframe.getTextArea_train().append("更新tablekeyword数据库执行结束!\n");

		// 进行降维，计算特征词的分类概率，只保留特征词概率库，chi库表不在存储
		uiframe.getTextArea_train().append("\n-----请耐心等候！-----正在进行降维操作。。。。。\n");
		List<KeyWord> rskeywords = TrainMain.reduceRatio(sumkeywords);
		// 可以将相关数据存入数据库了，直接调用每个表的insert函数即可
		// 后续操作会改变list的词的数量 所以此处就应该写入数据库
		//System.out.println("-----请耐心等候！-----正在插入tablep数据库。。。。。\n");
		uiframe.getTextArea_train().append("-----请耐心等候！-----正在更新tablep数据库。。。。。\n");
		TableP.insertTableP(rskeywords);
		//System.out.println("插入tablep数据库完成！\n");
		uiframe.getTextArea_train().append("更新tablep数据库完成！\n");

		long endTime1 = System.currentTimeMillis();
		long endTime2 = System.nanoTime();
		// System.out.println("\n运算用时：" + (endTime1 - startTime1) + "ms," +
		// (endTime2 - startTime2) + "ns.");
		uiframe.getTextArea_train().append("\n---过滤器再次训练暨增量学习训练过程完成！---\n");
		uiframe.getTextArea_train()
				.append("\n运算用时：" + (endTime1 - startTime1) + "ms," + (endTime2 - startTime2) + "ns.");
	}

	/**
	 * 函数功能 将增量学习训练集的特征词与数据库中的特征词融合
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
	 * 函数功能 将增量学习训练集的文本数对象与数据库中的文本数对象融合
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
