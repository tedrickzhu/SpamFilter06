package bistu.zzy.spamfilter.thread;

import bistu.zzy.spamfilter.db.TableP;
import bistu.zzy.spamfilter.db.TableTxtdata;
import bistu.zzy.spamfilter.io.MailFileReader;
import bistu.zzy.spamfilter.main.FilterMain;
import bistu.zzy.spamfilter.ui.SFFrame;
/**
 * 介绍	过滤线程，实现Runnable接口，并重写其run方法
 * @author zhuzhengyi
 */
public class FilteRunnable implements Runnable {

	private String testpath;
	private SFFrame uiframe;

	public FilteRunnable(String testpath, SFFrame uiframe) {
		// TODO Auto-generated constructor stub
		this.testpath = testpath;
		this.uiframe = uiframe;
	}

	@Override
	public void run() {
		// TODO 执行过滤操作，task code
		uiframe.getButton_filte().setEnabled(false);
		uiframe.getTextArea_filte().setEditable(true);
		// 执行过滤之前，先判断文件路径是否正确，数据库是否有数据
		boolean tablepempty = TableP.isEmpty();
		boolean tabletdempty = TableTxtdata.isEmpty();
		boolean rightfile = MailFileReader.isRightFile(testpath);
		//uiframe.getTextArea_filte().setText("数据库是否有数据："+empty);
		if (!tablepempty && !tabletdempty && rightfile) {
			// System.out.println("test filteRunnable:"+testpath);
			FilterMain.filter(testpath, uiframe);
//			try {
//				Thread.sleep(3600);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}else if(tablepempty){
			uiframe.getTextArea_filte().setText("\n特征词概率库中无数据！请先训练！\n");
		}else if(tabletdempty){
			uiframe.getTextArea_filte().setText("\n文本数量数据库中无数据！请先训练！\n");
		}else if(!rightfile){
			uiframe.getTextArea_filte().setText("\n输入测试文件路径错误！请重新选择正确的文件！\n");
		}else {
			uiframe.getTextArea_filte().setText("\n这是一个异常情况！在下才疏学浅，无法判断！还望见谅！\n");
		}
		uiframe.getButton_filte().setEnabled(true);
		uiframe.getTextArea_filte().setEditable(false);
	}

}
