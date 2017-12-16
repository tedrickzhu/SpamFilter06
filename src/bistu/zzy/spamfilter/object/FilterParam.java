package bistu.zzy.spamfilter.object;

/**
 * FilterParam 对象，用来表示降维时的两个参数DF，CHI
 * 
 * @author zhuzhengyi
 */
public class FilterParam {

	private int df;
	private double chi;

	public FilterParam() {

	}

	public int getDf() {
		return df;
	}

	public void setDf(int df) {
		this.df = df;
	}

	public double getChi() {
		return chi;
	}

	public void setChi(double chi) {
		this.chi = chi;
	}

}
