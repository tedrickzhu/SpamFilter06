package bistu.zzy.spamfilter.main;

import java.awt.EventQueue;

import bistu.zzy.spamfilter.sgmt.Segment;
import bistu.zzy.spamfilter.ui.SFFrame;

public class SFMain {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SFFrame frame = new SFFrame();
					frame.setVisible(true);
					// ����������ʱ�ͳ�ʼ���ִ��������˳�ʱ�����ͷ�
					Segment.initSegmentation();
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		});
	}
	
//	public static void main(String[] args) {
//		SFFrame sfframe = new SFFrame();
//		Segment.initSegmentation();
//		sfframe.setVisible(true);
//	}

}
