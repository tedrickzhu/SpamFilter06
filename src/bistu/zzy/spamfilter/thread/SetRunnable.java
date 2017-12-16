package bistu.zzy.spamfilter.thread;

import bistu.zzy.spamfilter.reduce.SetParam;
import bistu.zzy.spamfilter.ui.SFFrame;

public class SetRunnable implements Runnable {
	private int df;
	private double chi;
	private SFFrame uiframe;

	public SetRunnable(int df, double chi, SFFrame uiframe) {
		this.df = df;
		this.chi = chi;
		this.uiframe = uiframe;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// TODO 执行过滤操作，task code
		uiframe.getButton_param().setEnabled(false);
		uiframe.getTextField_result().setEnabled(true);
		SetParam.setFilterParam(df, chi);
		uiframe.getButton_param().setEnabled(true);
		uiframe.getTextField_result().setText("修改parameter成功");
		uiframe.getTextField_result().setEnabled(false);
		uiframe.getTextField_df().setText(null);
		uiframe.getTextField_chi().setText(null);
	}

}
