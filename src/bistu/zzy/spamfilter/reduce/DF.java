package bistu.zzy.spamfilter.reduce;

import java.util.List;

import bistu.zzy.spamfilter.db.TableParam;
import bistu.zzy.spamfilter.object.FilterParam;
import bistu.zzy.spamfilter.object.KeyWord;

public class DF {
/**
 * ��������	�����ĵ�Ƶ�ʽ��е�һ����ά
 * @param keywordslist
 * @return
 */
	public static List<KeyWord> useDF(List<KeyWord> keywordslist) {
		List<KeyWord> dfwordslist = null;
		// TODO ����DF�ĵ�Ƶ�ʹ��˴���ĳ�ĵ�������������,Ϊ�˼���CHI����ά
		FilterParam fparam = TableParam.selectParam();
		int df = fparam.getDf(); // TODO �����ĵ�Ƶ�ʹ��˵���ֵ
		// int size = keywordslist.size();

		for (int j = 0; j < keywordslist.size(); j++) {
			// ��ùؼ���list�е�ĳ����
			KeyWord kword = keywordslist.get(j);
			// String kwname = kword.getName();
			int kwnumtc = kword.getNum_t_c();
			int kwnumtnc = kword.getNum_t_nc();
			// System.out.println("����DF�еĺ��������numtc��"+kwnumtc);
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
		// TODO ����DF�ĵ�Ƶ�ʹ��˴�����ĳ�ĵ�������������,Ϊ�˼���CHI����ά
		int df = 2; // TODO �����ĵ�Ƶ�ʹ��˵���ֵ
		// int size = keywordslist.size();

		for (int j = 0; j < keywordslist.size(); j++) {
			// ��ùؼ���list�е�ĳ����
			KeyWord kword = keywordslist.get(j);
			// String kwname = kword.getName();
			int kwnumtc = kword.getNum_t_c();
			int kwnumtnc = kword.getNum_t_nc();
			// System.out.println("����DF�еĺ��������numtc��"+kwnumtc);
			if (kwnumtc < df) {
				keywordslist.remove(kword);
				if (j > keywordslist.size() - 1)
					break;
				j = j - 1;
				// System.out.println("����if����");
			}
			if (j == keywordslist.size() - 1)
				break;
		}
		dfwordslist = keywordslist;
		return dfwordslist;
	}

	public static List<KeyWord> useDFtnc(List<KeyWord> keywordslist) {
		List<KeyWord> dfwordslist = null;
		// TODO ����DF�ĵ�Ƶ�ʹ��˴�����ĳ�ĵ�������������,Ϊ�˼���CHI����ά
		int df = 2; // TODO �����ĵ�Ƶ�ʹ��˵���ֵ
		// int size = keywordslist.size();

		for (int j = 0; j < keywordslist.size(); j++) {
			// ��ùؼ���list�е�ĳ����
			KeyWord kword = keywordslist.get(j);
			// String kwname = kword.getName();
			int kwnumtc = kword.getNum_t_c();
			int kwnumtnc = kword.getNum_t_nc();
			// System.out.println("����DF�еĺ��������numtc��"+kwnumtc);
			// System.out.println("����if����");
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
 * //System.out.println("����DF�еĺ��������numtc��"+kwnumtc); if (kwnumtc < df) {
 * keywordslist.remove(kword); //System.out.println("����if����");
 * getDF(keywordslist); if(j == keywordslist.size()-1) break; if (kwnumtnc < df)
 * { keywordslist.remove(kword); } }
 * 
 * j++; }
 */
