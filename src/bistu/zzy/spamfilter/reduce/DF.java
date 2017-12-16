package bistu.zzy.spamfilter.reduce;

import java.util.List;

import bistu.zzy.spamfilter.db.TableParam;
import bistu.zzy.spamfilter.object.FilterParam;
import bistu.zzy.spamfilter.object.KeyWord;

public class DF {
/**
 * 函数功能	根据文档频率进行第一步降维
 * @param keywordslist
 * @return
 */
	public static List<KeyWord> useDF(List<KeyWord> keywordslist) {
		List<KeyWord> dfwordslist = null;
		// TODO 利用DF文档频率过滤处理某文档的所有特征词,为了计算CHI而降维
		FilterParam fparam = TableParam.selectParam();
		int df = fparam.getDf(); // TODO 设置文档频率过滤的阈值
		// int size = keywordslist.size();

		for (int j = 0; j < keywordslist.size(); j++) {
			// 获得关键词list中的某个词
			KeyWord kword = keywordslist.get(j);
			// String kwname = kword.getName();
			int kwnumtc = kword.getNum_t_c();
			int kwnumtnc = kword.getNum_t_nc();
			// System.out.println("这是DF中的函数，获得numtc："+kwnumtc);
			if (kwnumtc < df) {
				if (kwnumtnc < df) {
					keywordslist.remove(kword);
					if (j > keywordslist.size() - 1)
						break;
					j = j - 1;
				}
			}
			if (j == keywordslist.size() - 1)
				break;
		}
		dfwordslist = keywordslist;
		return dfwordslist;
	}

	public static List<KeyWord> useDFtc(List<KeyWord> keywordslist) {
		List<KeyWord> dfwordslist = null;
		// TODO 利用DF文档频率过滤处理后的某文档的所有特征词,为了计算CHI而降维
		int df = 2; // TODO 设置文档频率过滤的阈值
		// int size = keywordslist.size();

		for (int j = 0; j < keywordslist.size(); j++) {
			// 获得关键词list中的某个词
			KeyWord kword = keywordslist.get(j);
			// String kwname = kword.getName();
			int kwnumtc = kword.getNum_t_c();
			int kwnumtnc = kword.getNum_t_nc();
			// System.out.println("这是DF中的函数，获得numtc："+kwnumtc);
			if (kwnumtc < df) {
				keywordslist.remove(kword);
				if (j > keywordslist.size() - 1)
					break;
				j = j - 1;
				// System.out.println("这是if函数");
			}
			if (j == keywordslist.size() - 1)
				break;
		}
		dfwordslist = keywordslist;
		return dfwordslist;
	}

	public static List<KeyWord> useDFtnc(List<KeyWord> keywordslist) {
		List<KeyWord> dfwordslist = null;
		// TODO 利用DF文档频率过滤处理后的某文档的所有特征词,为了计算CHI而降维
		int df = 2; // TODO 设置文档频率过滤的阈值
		// int size = keywordslist.size();

		for (int j = 0; j < keywordslist.size(); j++) {
			// 获得关键词list中的某个词
			KeyWord kword = keywordslist.get(j);
			// String kwname = kword.getName();
			int kwnumtc = kword.getNum_t_c();
			int kwnumtnc = kword.getNum_t_nc();
			// System.out.println("这是DF中的函数，获得numtc："+kwnumtc);
			// System.out.println("这是if函数");
			if (kwnumtnc < df) {
				keywordslist.remove(kword);
				if (j > keywordslist.size() - 1)
					break;
				j = j - 1;
			}

			if (j == keywordslist.size() - 1)
				break;
		}
		dfwordslist = keywordslist;
		return dfwordslist;
	}
}

/*
 * while(keywordslist.size()!=0){ int j = 0; KeyWord kword =
 * keywordslist.get(j); //String kwname = kword.getName(); int kwnumtc =
 * kword.getNum_t_c(); int kwnumtnc = kword.getNum_t_nc();
 * //System.out.println("这是DF中的函数，获得numtc："+kwnumtc); if (kwnumtc < df) {
 * keywordslist.remove(kword); //System.out.println("这是if函数");
 * getDF(keywordslist); if(j == keywordslist.size()-1) break; if (kwnumtnc < df)
 * { keywordslist.remove(kword); } }
 * 
 * j++; }
 */
