package bistu.zzy.spamfilter.thread;

import bistu.zzy.spamfilter.io.MailFileReader;
import bistu.zzy.spamfilter.main.TrainMain;
import bistu.zzy.spamfilter.ui.SFFrame;
/**
 * 介绍	训练线程，继承自线程类，并重写其run方法
 * @author zhuzhengyi
 */
public class TrainThread extends Thread {
	private String hampath;
	private String spampath;
	private SFFrame uiframe;

	public TrainThread(String hampath, String spampath, SFFrame uiframe) {
		// TODO Auto-generated constructor stub
		this.hampath = hampath;
		this.spampath = spampath;
		this.uiframe = uiframe;
	}

	public void run() {
		// TODO 执行训练操作task code
		uiframe.getButton_train().setEnabled(false);
		uiframe.getTextArea_train().setEditable(true);
		//判断数据路径是否正确
		boolean hamright = MailFileReader.isRightFile(hampath);
		boolean spamright = MailFileReader.isRightFile(spampath);
		if(hamright&&spamright){
			uiframe.getTextArea_train().append("\n训练过程正在开始！请耐心等待！\n");
			TrainMain.train(hampath, spampath, uiframe);
//			try {
//				System.out.println("训练过程正在执行，现在 sleep，3600 ms 后,程序继续！");
//				Thread.sleep(3600);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}else if(!hamright||!spamright){
			uiframe.getTextArea_train().setText("\n邮件训练集路径有误，请输入正确的路径！\n");
		}else{
			uiframe.getTextArea_train().setText("\n这是一个异常情况！在下才疏学浅，无法判断！还望见谅！\n");
		}
		uiframe.getButton_train().setEnabled(true);
		uiframe.getTextArea_train().setEditable(false);
	}

}
