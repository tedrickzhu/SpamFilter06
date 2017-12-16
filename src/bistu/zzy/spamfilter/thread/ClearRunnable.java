package bistu.zzy.spamfilter.thread;

import bistu.zzy.spamfilter.db.TableKeyword;
import bistu.zzy.spamfilter.db.TableP;
import bistu.zzy.spamfilter.db.TableParam;
import bistu.zzy.spamfilter.db.TableTxtdata;
import bistu.zzy.spamfilter.ui.SFFrame;

public class ClearRunnable implements Runnable {
	private String button;
	private SFFrame uiframe;

	public ClearRunnable(String button, SFFrame uiframe) {
		this.button = button;
		this.uiframe = uiframe;
	}

	@Override
	public void run() {
		uiframe.getTextField_result().setEnabled(true);
		boolean clear = false ;
		String rs = "";
		if (button.equals("clearall")) {
			 clear = TableParam.clearAll();
			 rs = "清洗所有数据库表格成功";
		} else if (button.equals("clearkeywords")) {
			 clear = TableKeyword.clearTablekw();
			 rs = "清洗keywords数据库表格成功";
		} else if (button.equals("clearpwords")) {
			 clear = TableP.clearTablep();
			 rs = "清洗pwords数据库表格成功";
		} else if (button.equals("cleartxtdata")) {
			 clear = TableTxtdata.clearTxtdata();
			 rs = "清洗txtdata数据库表格成功";
		} else if (button.equals("clearfilterparam")) {
			 clear = TableParam.clearFilterParam();
			 rs = "清洗filterparam数据库表格成功";
		}
		if(clear){
			uiframe.getTextField_result().setText(rs);
		}else{
			uiframe.getTextField_result().setText("清洗数据库表格失败");
		}
		
		uiframe.getTextField_result().setEnabled(false);

	}

}
