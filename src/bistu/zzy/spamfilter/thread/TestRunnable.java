package bistu.zzy.spamfilter.thread;

import bistu.zzy.spamfilter.db.TableP;
import bistu.zzy.spamfilter.db.TableTxtdata;
import bistu.zzy.spamfilter.io.MailFileReader;
import bistu.zzy.spamfilter.main.FilterMain;
import bistu.zzy.spamfilter.ui.SFFrame;

/**
 * ���� �����̣߳�ʵ��Runnable�ӿڣ�����д��run����
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
		// TODO ִ�й��˲�����task code
		uiframe.getButton_test().setEnabled(false);
		uiframe.getTextArea_test().setEditable(true);
		// ִ�й���֮ǰ�����ж��ļ�·���Ƿ���ȷ�����ݿ��Ƿ�������
		boolean tablepempty = TableP.isEmpty();
		boolean tabletdempty = TableTxtdata.isEmpty();
		
		boolean righthamfile = MailFileReader.isRightFile(testhampath);
		boolean rightspamfile = MailFileReader.isRightFile(testspampath);
		// uiframe.getTextArea_filte().setText("���ݿ��Ƿ������ݣ�"+empty);
		if (!tablepempty && !tabletdempty && righthamfile && rightspamfile) {

			// System.out.println("test filteRunnable:"+testpath);
			FilterMain.testFilter(testhampath,testspampath, uiframe);

		} else if (tablepempty) {
			uiframe.getTextArea_test().setText("\n�����ʸ��ʿ��������ݣ�����ѵ����\n");
		} else if (tabletdempty) {
			uiframe.getTextArea_test().setText("\n�ı��������ݿ��������ݣ�����ѵ����\n");
		} else if (!righthamfile) {
			uiframe.getTextArea_test().setText("\n��������ļ�·������������ѡ����ȷ���ļ���\n");
		} else {
			uiframe.getTextArea_test().setText("\n����һ���쳣��������²���ѧǳ���޷��жϣ��������£�\n");
		}
		uiframe.getButton_test().setEnabled(true);
		uiframe.getTextArea_test().setEditable(false);
	}

}
