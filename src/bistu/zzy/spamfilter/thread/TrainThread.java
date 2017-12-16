package bistu.zzy.spamfilter.thread;

import bistu.zzy.spamfilter.io.MailFileReader;
import bistu.zzy.spamfilter.main.TrainMain;
import bistu.zzy.spamfilter.ui.SFFrame;
/**
 * ����	ѵ���̣߳��̳����߳��࣬����д��run����
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
		// TODO ִ��ѵ������task code
		uiframe.getButton_train().setEnabled(false);
		uiframe.getTextArea_train().setEditable(true);
		//�ж�����·���Ƿ���ȷ
		boolean hamright = MailFileReader.isRightFile(hampath);
		boolean spamright = MailFileReader.isRightFile(spampath);
		if(hamright&&spamright){
			uiframe.getTextArea_train().append("\nѵ���������ڿ�ʼ�������ĵȴ���\n");
			TrainMain.train(hampath, spampath, uiframe);
//			try {
//				System.out.println("ѵ����������ִ�У����� sleep��3600 ms ��,���������");
//				Thread.sleep(3600);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}else if(!hamright||!spamright){
			uiframe.getTextArea_train().setText("\n�ʼ�ѵ����·��������������ȷ��·����\n");
		}else{
			uiframe.getTextArea_train().setText("\n����һ���쳣��������²���ѧǳ���޷��жϣ��������£�\n");
		}
		uiframe.getButton_train().setEnabled(true);
		uiframe.getTextArea_train().setEditable(false);
	}

}
