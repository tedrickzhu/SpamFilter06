package bistu.zzy.spamfilter.thread;

import bistu.zzy.spamfilter.db.TableP;
import bistu.zzy.spamfilter.db.TableTxtdata;
import bistu.zzy.spamfilter.io.MailFileReader;
import bistu.zzy.spamfilter.main.FilterMain;
import bistu.zzy.spamfilter.ui.SFFrame;
/**
 * ����	�����̣߳�ʵ��Runnable�ӿڣ�����д��run����
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
		// TODO ִ�й��˲�����task code
		uiframe.getButton_filte().setEnabled(false);
		uiframe.getTextArea_filte().setEditable(true);
		// ִ�й���֮ǰ�����ж��ļ�·���Ƿ���ȷ�����ݿ��Ƿ�������
		boolean tablepempty = TableP.isEmpty();
		boolean tabletdempty = TableTxtdata.isEmpty();
		boolean rightfile = MailFileReader.isRightFile(testpath);
		//uiframe.getTextArea_filte().setText("���ݿ��Ƿ������ݣ�"+empty);
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
			uiframe.getTextArea_filte().setText("\n�����ʸ��ʿ��������ݣ�����ѵ����\n");
		}else if(tabletdempty){
			uiframe.getTextArea_filte().setText("\n�ı��������ݿ��������ݣ�����ѵ����\n");
		}else if(!rightfile){
			uiframe.getTextArea_filte().setText("\n��������ļ�·������������ѡ����ȷ���ļ���\n");
		}else {
			uiframe.getTextArea_filte().setText("\n����һ���쳣��������²���ѧǳ���޷��жϣ��������£�\n");
		}
		uiframe.getButton_filte().setEnabled(true);
		uiframe.getTextArea_filte().setEditable(false);
	}

}
