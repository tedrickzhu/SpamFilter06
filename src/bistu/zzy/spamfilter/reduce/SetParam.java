package bistu.zzy.spamfilter.reduce;

import bistu.zzy.spamfilter.db.TableParam;
import bistu.zzy.spamfilter.object.FilterParam;

public class SetParam {

	public static void setFilterParam(int df,double chi) {
		FilterParam fparam = new FilterParam();
		fparam.setDf(df);
		fparam.setChi(chi);
		
		TableParam.insertParam(fparam);
		
	}

}
