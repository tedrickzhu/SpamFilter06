package bistu.zzy.spamfilter.object;

/**
 * ��¼�ı����������������� ����һ��������ֶε����Ը���KeyWord���󣬽����ͳ�ƺõ�List<KeyWord>�еĵ�һ��������Ϊ�洢��Щ���ݵĶ���
 * ���ʱ���ڶ�ȡ�ı����ִʵ�ͬʱ����ͳ���ı���������¼����
 * ����������д����ͳ���ı����ĺ����������ٶ�ѵ����Ŀ¼��ͳ���ı���������Ϊһ��TxtData���󴫸���Ӧ����Ҫ�ĸ����ݵĺ��������д�����ݿ�
 * ���������ڹ��˹����У������������ı������ȡ��Щ���ݣ��÷���һ���޷���ȡ���÷�������ɻ�ȡ���������¼���Ҳ��
 * 
 * @author zhuzhengyi
 */

public class TxtData {
	/*
	 * ���Խ��� int txtnum;ѵ�����е������ʼ����������������ʼ����������ʼ����ĺ� int txthamnum;ѵ�����������ʼ������� int
	 * txtspamnum;ѵ�����������ʼ������� int pwordsnum;����chi��ά����������йؼ��ʵĸ���
	 */

	private int txtnum;

	private int txthamnum;

	private int txtspamnum;

	private int chikwsum;

	public int getTxtnum() {
		return txtnum;
	}

	public void setTxtnum(int txtnum) {
		this.txtnum = txtnum;
	}

	public int getTxthamnum() {
		return txthamnum;
	}

	public void setTxthamnum(int txthamnum) {
		this.txthamnum = txthamnum;
	}

	public int getTxtspamnum() {
		return txtspamnum;
	}

	public void setTxtspamnum(int txtspamnum) {
		this.txtspamnum = txtspamnum;
	}

	public int getChikwsum() {
		return chikwsum;
	}

	public void setChikwsum(int chikwsum) {
		this.chikwsum = chikwsum;
	}

}
