package bistu.zzy.spamfilter.object;

/**
 * 记录文本的数量，两个方法 方法一：将相关字段的属性赋给KeyWord对象，将最后统计好的List<KeyWord>中的第一个对象作为存储这些数据的对象
 * 则此时，在读取文本并分词的同时即可统计文本数，并记录下来
 * 方法二：另写方法统计文本数的函数，重新再读训练集目录，统计文本数后将其作为一个TxtData对象传给相应的需要的该数据的函数，最后写入数据库
 * 分析：对于过滤过程中，对于来的新文本如需读取这些数据，用方法一则无法获取，用方法二则可获取，或者重新计算也可
 * 
 * @author zhuzhengyi
 */

public class TxtData {
	/*
	 * 属性解释 int txtnum;训练集中的所有邮件的数量，是正常邮件数和垃圾邮件数的和 int txthamnum;训练集中正常邮件的数量 int
	 * txtspamnum;训练集中垃圾邮件的数量 int pwordsnum;利用chi降维后产生的所有关键词的个数
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
