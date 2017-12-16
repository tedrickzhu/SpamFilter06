package bistu.zzy.spamfilter.thread;

import bistu.zzy.spamfilter.db.TableP;
import bistu.zzy.spamfilter.db.TableTxtdata;
import bistu.zzy.spamfilter.io.MailFileReader;
import bistu.zzy.spamfilter.main.FilterMain;
import bistu.zzy.spamfilter.ui.SFFrame;

/**
 * 介绍 过滤线程，实现Runnable接口，并重写其run方法
 * 
 * @author zhuzhengyi
 */
public class TestRunnable implements Runnable {

	private String testhampath;
	private String testspampath;
	private SFFrame uiframe;

	public TestRunnable(String testhampath, String testspampath, SFFrame uiframe) {
		// TODO Auto-generated constructor stub
		this.testhampath = testhampath;
		this.testspampath = testspampath;
		this.uiframe = uiframe;
	}

	@Override
	public void run() {
		// TODO 执行过滤操作，task code
		uiframe.getButton_test().setEnabled(false);
		uiframe.getTextArea_test().setEditable(true);
		// 执行过滤之前，先判断文件路径是否正确，数据库是否有数据
		boolean tablepempty = TableP.isEmpty();
		boolean tabletdempty = TableTxtdata.isEmpty();
		
		boolean righthamfile = MailFileReader.isRightFile(testhampath);
		boolean rightspamfile = MailFileReader.isRightFile(testspampath);
		// uiframe.getTextArea_filte().setText("数据库是否有数据："+empty);
		if (!tablepempty && !tabletdempty && righthamfile && rightspamfile) {

			// System.out.println("test filteRunnable:"+testpath);
			FilterMain.testFilter(testhampath,testspampath, uiframe);

		} else if (tablepempty) {
			uiframe.getTextArea_test().setText("\n特征词概率库中无数据！请先训练！\n");
		} else if (tabletdempty) {
			uiframe.getTextArea_test().setText("\n文本数量数据库中无数据！请先训练！\n");
		} else if (!righthamfile) {
			uiframe.getTextArea_test().setText("\n输入测试文件路径错误！请重新选择正确的文件！\n");
		} else {
			uiframe.getTextArea_test().setText("\n这是一个异常情况！在下才疏学浅，无法判断！还望见谅！\n");
		}
		uiframe.getButton_test().setEnabled(true);
		uiframe.getTextArea_test().setEditable(false);
	}

}
