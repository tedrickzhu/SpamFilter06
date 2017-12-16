package bistu.zzy.spamfilter.util;

import java.util.List;

import bistu.zzy.spamfilter.object.KeyWord;
import bistu.zzy.spamfilter.object.TxtData;

public class Counter {

	/* * �������� ������������ʼ�ѵ���������ʼ��ı�������ͳ�ƺõ�num_t_c ��num_nt_c������ List��num_t_c
	 * ��num_nt_c�������ֶε����� ��ӵ������ʼ�ѵ���������ʼ��ı�������ͳ�ƺõ�num_t_nc ��num_nt_nc������ List��ȥ��
	 * ������ͳ�ƺ�num_t_c ��num_nt_c��num_t_nc ��num_nt_nc�����ĸ��ֶε����ݵ�List
	 * <KeyWord> ������listд�����ݿ� TODO �����д����
	 * 
	 * @param spamkeywords
	 *            �����ʼ�ѵ���������ʼ��ı�������ͳ�ƺõ�num_t_nc ��num_nt_nc������ List<KeyWord>
	 * @param hamkeywords
	 *            �����ʼ�ѵ���������ʼ��ı�������ͳ�ƺõ�num_t_c ��num_nt_c������ List<KeyWord>
	 */
	public static List<KeyWord> getSumDF(List<KeyWord> rskeywords, List<KeyWord> tmpkeywords, TxtData txtdata) {
		List<KeyWord> dfkeywords;

		for (int i = 0; i < tmpkeywords.size(); i++) {
			// ���һ���������ʼ�ѵ����keywordlist(��������ѵ������list)������������
			KeyWord tmpkw = tmpkeywords.get(i);
			String tmpkwname = tmpkw.getName();
			int tmpkwnumtc = tmpkw.getNum_t_c();
			int tmpkwnumntc = tmpkw.getNum_nt_c();
			int tmpkwnumtnc = tmpkw.getNum_t_nc();
			int tmpkwnumntnc = tmpkw.getNum_nt_nc();

			// ����һ����ʾ������ȷ���ô��Ƿ�Ϊ�´�
			Boolean isnew = true;
			if (tmpkwname != null) {
				if (rskeywords.size() == 0) {
					KeyWord nkw = new KeyWord();
					nkw.setName(tmpkwname);
					nkw.setNum_t_c(tmpkwnumtc);
					nkw.setNum_nt_c(tmpkwnumntc);
					nkw.setNum_t_nc(tmpkwnumtnc);
					nkw.setNum_nt_nc(tmpkwnumntnc);
					rskeywords.add(nkw);
					tmpkeywords.remove(tmpkw);
					getSumDF(rskeywords, tmpkeywords, txtdata);
					if (tmpkeywords.size() == 0)
						break;
				} else {
					for (int j = 0; j < rskeywords.size(); j++) {
						// ����������ʼ�ѵ���������Ĺؼ���list�е�ĳ����
						KeyWord rskw = rskeywords.get(j);
						String rskwname = rskw.getName();
						int rskwnumtc = rskw.getNum_t_c();
						int rskwnumntc = rskw.getNum_nt_c();
						int rskwnumtnc = rskw.getNum_t_nc();
						int rskwnumntnc = rskw.getNum_nt_nc();

						// �ж�ham keywords list�еĸô��Ƿ���spam keywords list�г���
						// ������������´ʣ�ͬʱ��num_t_c,num_nt_c��ӵ�spam keywords list
						// ����Ӧ��λ��
						if (rskwname.equals(tmpkwname)) {
							isnew = false;
							rskw.setNum_t_c(tmpkwnumtc+rskwnumtc);
							rskw.setNum_nt_c(tmpkwnumntc+rskwnumntc);
							rskw.setNum_t_nc(tmpkwnumtnc+rskwnumtnc);
							rskw.setNum_nt_nc(tmpkwnumntnc+rskwnumntnc);
							tmpkeywords.remove(tmpkw);
						} else if (j == (rskeywords.size() - 1) && isnew) { 
							// �ж��Ƿ��ѯ��spamlist(������ѵ����list)�е�����keyword������δ�鵽���´�
							KeyWord nkw = new KeyWord();
							nkw.setName(tmpkwname);
							nkw.setNum_t_c(tmpkwnumtc);
							nkw.setNum_nt_c(tmpkwnumntc);
							nkw.setNum_t_nc(tmpkwnumtnc);
							nkw.setNum_nt_nc(tmpkwnumntnc);
							rskeywords.add(nkw);
							tmpkeywords.remove(tmpkw);
							getSumDF(rskeywords, tmpkeywords, txtdata);
							if (tmpkeywords.size() == 0)
								break;
						}
					}
				}
			}
		}
		dfkeywords = sortDF(rskeywords, txtdata);
		return dfkeywords;
	}
	
	/**
	 * �������� ������������ʼ�ѵ���������ʼ��ı�������ͳ�ƺõ�num_t_c ��num_nt_c������ List��num_t_c
	 * ��num_nt_c�������ֶε����� ��ӵ������ʼ�ѵ���������ʼ��ı�������ͳ�ƺõ�num_t_nc ��num_nt_nc������ List��ȥ��
	 * ������ͳ�ƺ�num_t_c ��num_nt_c��num_t_nc ��num_nt_nc�����ĸ��ֶε����ݵ�List
	 * <KeyWord> ������listд�����ݿ� TODO �����д����
	 * 
	 * @param spamkeywords
	 *            �����ʼ�ѵ���������ʼ��ı�������ͳ�ƺõ�num_t_nc ��num_nt_nc������ List<KeyWord>
	 * @param hamkeywords
	 *            �����ʼ�ѵ���������ʼ��ı�������ͳ�ƺõ�num_t_c ��num_nt_c������ List<KeyWord>
	 */
//	public static List<KeyWord> countDF(List<KeyWord> spamkeywords, List<KeyWord> hamkeywords, TxtData txtdata) {
//		List<KeyWord> dfkeywords;
//
//		for (int i = 0; i < hamkeywords.size(); i++) {
//			// ���һ���������ʼ�ѵ����������numtc��numntc��ֵ��������
//			KeyWord hamkw = hamkeywords.get(i);
//			String hamkwname = hamkw.getName();
//			int hamkwnumtc = hamkw.getNum_t_c();
//			int hamkwnumntc = hamkw.getNum_nt_c();
//
//			// ����һ����ʾ������ȷ���ô��Ƿ�Ϊ�´�
//			Boolean isnew = true;
//			if (hamkwname != null) {
//				if (spamkeywords.size() == 0) {
//					KeyWord nkw = new KeyWord();
//					nkw.setName(hamkwname);
//					nkw.setNum_t_c(hamkwnumtc);
//					nkw.setNum_nt_c(hamkwnumntc);
//					spamkeywords.add(nkw);
//					hamkeywords.remove(hamkw);
//					countDF(spamkeywords, hamkeywords, txtdata);
//					if (hamkeywords.size() == 0)
//						break;
//				} else {
//					for (int j = 0; j < spamkeywords.size(); j++) {
//						// ����������ʼ�ѵ���������Ĺؼ���list�е�ĳ����
//						KeyWord spamkw = spamkeywords.get(j);
//						String spamkwname = spamkw.getName();
//						// int spamkwnumtnc = spamkw.getNum_t_nc();
//						// int spamkwnumntnc = spamkw.getNum_nt_nc();
//
//						// �ж�ham keywords list�еĸô��Ƿ���spam keywords list�г���
//						// ������������´ʣ�ͬʱ��num_t_c,num_nt_c��ӵ�spam keywords list
//						// ����Ӧ��λ��
//						if (spamkwname.equals(hamkwname)) {
//							isnew = false;
//							spamkw.setNum_t_c(hamkwnumtc);
//							spamkw.setNum_nt_c(hamkwnumntc);
//							hamkeywords.remove(hamkw);
//						} else if (j == (spamkeywords.size() - 1) && isnew) { // �ж��Ƿ��ѯ��spam
//																				// list�е�����keyword������δ�鵽���´�
//							KeyWord nkw = new KeyWord();
//							nkw.setName(hamkwname);
//							nkw.setNum_t_c(hamkwnumtc);
//							nkw.setNum_nt_c(hamkwnumntc);
//							spamkeywords.add(nkw);
//							hamkeywords.remove(hamkw);
//							countDF(spamkeywords, hamkeywords, txtdata);
//							if (hamkeywords.size() == 0)
//								break;
//						}
//					}
//				}
//			}
//		}
//		dfkeywords = sortDF(spamkeywords, txtdata);
//		return dfkeywords;
//	}

	/**
	 * ��������  ����ؼ���numtc,numntc ��numtnc,numntnc������ͬʱΪ0 ������
	 * ��������ͳ�����������ʵ� numtc,numntc ��numtnc,numntnc�����ĸ��ֶε�ֵʱ
	 * ��ĳ�ؼ���ֻ��һ���ĵ��г���ʱ�������numtc,numntc ��numtnc,numntnc������ͬʱΪ0 ������
	 * ��ʱ������CHI,����ֵ��Ϊ0�����ǲ��Ե�
	 * @param countdfkws
	 * @param txtdata
	 * @return
	 */
	public static List<KeyWord> sortDF(List<KeyWord> countdfkws, TxtData txtdata) {
		List<KeyWord> sortdfkws = null;
		// int ntxt = txtdata.getTxtnum();
		int nham = txtdata.getTxthamnum();
		int nspam = txtdata.getTxtspamnum();
		for (int j = 0; j < countdfkws.size(); j++) {
			// ��ùؼ���list�е�ĳ����
			KeyWord kw = countdfkws.get(j);
			// String kwname = kw.getName();
			int numtc = kw.getNum_t_c();
			int numntc = kw.getNum_nt_c();
			int numtnc = kw.getNum_t_nc();
			int numntnc = kw.getNum_nt_nc();

			if (numtc == 0) {
				if (numntc == 0) {
					kw.setNum_nt_c(nham);
				}
			}
			if (numtnc == 0) {
				if (numntnc == 0) {
					kw.setNum_nt_nc(nspam);
				}
			}
		}
		sortdfkws = countdfkws;
		return sortdfkws;
	}

	/**
	 * �������� ���ݴ��뵥���ʼ��ı���������List<String>ͳ���ۼ������ʼ�ѵ������ĳ�ʵ�num_t_c ��num_nt_c�������ֶε�����
	 * ��ͳ�������ʼ�ѵ�����г��ָôʵ��ĵ�����δ���ָôʵ��ĵ���
	 * 
	 * @param keywords
	 *            ��Ϊ���������ݿ����ʱ�������б����ϸ������е�����
	 * @param fwordslist
	 *            ���ı��������������ظ��ʵ�List<String>��
	 */
	public static List<KeyWord> countHamDF(List<KeyWord> keywords, List<String> fwordslist, int countham) {
		List<KeyWord> nkeywords;

		// TODO n ��ѵ�����У������ʼ��ı������ڶ�ȡ�ļ���ʱ���ɻ�ã�д�����ݿ⣬�˴�ֱ�Ӷ�������
		int nham = countham;

		while (fwordslist.size() != 0) {
			int i = 0;
			String fkwname = fwordslist.get(i);
			// String fkwname = fkword.getName();
			// int fkwnumtc = fkword.getNum_t_c();

			// ����һ����ʾ������ȷ���ô��Ƿ�Ϊ�´�
			Boolean isnew = true;
			if (fkwname != null) {
				if (keywords.size() == 0) {
					KeyWord nkw = new KeyWord();
					nkw.setName(fkwname);
					nkw.setNum_t_c(1);
					nkw.setNum_nt_c(nham - nkw.getNum_t_c());
					keywords.add(nkw);
					fwordslist.remove(fkwname);
					countHamDF(keywords, fwordslist, nham);
					// if(fwordslist.size()==0)break;
				} else {
					for (int j = 0; j < keywords.size(); j++) {
						// ��ùؼ���list�е�ĳ����
						KeyWord kword = keywords.get(j);
						String kwname = kword.getName();
						int kwnumtc = kword.getNum_t_c();

						// �ж�list�еĸô��Ƿ������ĵ��г���,������������´ʣ�ͬʱnum_t_c������һ,num_nt_cΪ�����ʼ�������ȥnum_t_c
						if (fkwname.equals(kwname)) {
							isnew = false;
							kword.setNum_t_c(kwnumtc + 1);
							kword.setNum_nt_c(nham - kword.getNum_t_c());
							fwordslist.remove(fkwname);
						} else if (j == (keywords.size() - 1) && isnew) { // �ж��Ƿ��ѯ��list�е�����keyword������δ�鵽���´�
							KeyWord nkw = new KeyWord();
							nkw.setName(fkwname);
							nkw.setNum_t_c(1);
							nkw.setNum_nt_c(nham - nkw.getNum_t_c());
							keywords.add(nkw);
							fwordslist.remove(fkwname);
							countHamDF(keywords, fwordslist, nham);
							if (fwordslist.size() == 0)
								break;
						}
					}
				}

			}

			i++;
		}

		nkeywords = keywords;
		return nkeywords;
	}

	/**
	 * �������� ���ݴ��뵥���ʼ��ı���������List<String>ͳ���ۼ������ʼ�ѵ������ĳ�ʵ�num_t_nc ��num_nt_nc�������ֶε�����
	 * ��ͳ�������ʼ�ѵ�����г��ָôʵ��ĵ�����δ���ָôʵ��ĵ���
	 * 
	 * @param keywords
	 *            ��Ϊ���������ݿ����ʱ�������б����ϸ������е�����
	 * @param fwordslist
	 *            ���ı��������������ظ��ʵ�List<String>��
	 */
	public static List<KeyWord> countSpamDF(List<KeyWord> keywords, List<String> fwordslist, int countspam) {
		List<KeyWord> nkeywords;

		// TODO n ��ѵ�����У������ʼ��ı������ڶ�ȡ�ļ���ʱ���ɻ�ã�д�����ݿ⣬�˴�ֱ�Ӷ�������
		int nspam = countspam;

		while (fwordslist.size() != 0) {
			int i = 0;
			String fkwname = fwordslist.get(i);
			// String fkwname = fkword.getName();
			// int fkwnumtc = fkword.getNum_t_c();

			// ����һ����ʾ������ȷ���ô��Ƿ�Ϊ�´�
			Boolean isnew = true;
			if (fkwname != null) {
				if (keywords.size() == 0) {
					KeyWord nkw = new KeyWord();
					nkw.setName(fkwname);
					nkw.setNum_t_nc(1);
					nkw.setNum_nt_nc(nspam - nkw.getNum_t_nc());
					keywords.add(nkw);
					fwordslist.remove(fkwname);
					countSpamDF(keywords, fwordslist, nspam);
					// if(fwordslist.size()==0)break;
				} else {
					for (int j = 0; j < keywords.size(); j++) {
						// ��ùؼ���list�е�ĳ����
						KeyWord kword = keywords.get(j);
						String kwname = kword.getName();
						int kwnumtc = kword.getNum_t_nc();

						// �ж�list�еĸô��Ƿ������ĵ��г���,������������´ʣ�ͬʱnum_t_nc������һ,num_nt_ncΪ�����ʼ�������ȥnum_t_c
						if (fkwname.equals(kwname)) {
							isnew = false;
							kword.setNum_t_nc(kwnumtc + 1);
							kword.setNum_nt_nc(nspam - kword.getNum_t_nc());
							fwordslist.remove(fkwname);
						} else if (j == (keywords.size() - 1) && isnew) { // �ж��Ƿ��ѯ��list�е�����keyword������δ�鵽���´�
							KeyWord nkw = new KeyWord();
							nkw.setName(fkwname);
							nkw.setNum_t_nc(1);
							nkw.setNum_nt_nc(nspam - nkw.getNum_t_nc());
							keywords.add(nkw);
							fwordslist.remove(fkwname);
							countSpamDF(keywords, fwordslist, nspam);
							if (fwordslist.size() == 0)
								break;
						}
					}
				}

			}

			i++;
		}

		nkeywords = keywords;
		return nkeywords;
	}

	/**
	 * �������� ���ݴ���ĳ�ļ����µ������ʼ��ı���ͳ�ƺ�num_t_c ��num_nt_c�������� List
	 * <KeyWord> ͳ���ۼ������ʼ�ѵ������ĳ�ʵ�num_t_c ��num_nt_c�������ֶε�����
	 * ��ͳ�������ʼ�ѵ�����г��ָôʵ��ĵ�����δ���ָôʵ��ĵ���
	 * 
	 * @param keywords
	 *            ��Ϊ���������ݿ����ʱ�������б����ϸ������е�����
	 * @param kwtmpset
	 *            ĳ�ļ����º�ͳ�ƺ�num_t_c ��num_nt_c�������� List<KeyWord>
	 */
	public static List<KeyWord> countHamDFtmp(List<KeyWord> keywords, List<KeyWord> kwtmpset) {
		List<KeyWord> nkeywords;

		// TODO n ��ѵ�����У������ʼ��ı������ڶ�ȡ�ļ���ʱ���ɻ�ã�д�����ݿ⣬�˴�ֱ�Ӷ�������
		int nham = 100;

		for (int i = 0; i < kwtmpset.size(); i++) {
			// ���һ������ȥ�ش���������ĵ����´�
			KeyWord kwtmp = kwtmpset.get(i);
			String fkwname = kwtmp.getName();
			// int fkwnumtc = fkword.getNum_t_c();

			// ����һ����ʾ������ȷ���ô��Ƿ�Ϊ�´�
			Boolean isnew = true;
			if (fkwname != null) {
				if (keywords.size() == 0) {
					KeyWord nkw = new KeyWord();
					nkw.setName(fkwname);
					nkw.setNum_t_c(1);
					keywords.add(nkw);
					kwtmpset.remove(kwtmp);
					countHamDFtmp(keywords, kwtmpset);
					if (kwtmpset.size() == 0)
						break;
				} else {
					for (int j = 0; j < keywords.size(); j++) {
						// ��ùؼ���list�е�ĳ����
						KeyWord kword = keywords.get(j);
						String kwname = kword.getName();
						int kwnumtc = kword.getNum_t_c();

						// �ж�list�еĸô��Ƿ������ĵ��г���,������������´ʣ�ͬʱnum_t_c������һ,num_nt_cΪ�����ʼ�������ȥnum_t_c
						if (fkwname.equals(kwname)) {
							isnew = false;
							kword.setNum_t_c(kwnumtc + 1);
							kword.setNum_nt_c(nham - kword.getNum_t_c());
							kwtmpset.remove(kwtmp);
						} else if (j == (keywords.size() - 1) && isnew) { // �ж��Ƿ��ѯ��list�е�����keyword������δ�鵽���´�
							KeyWord nkw = new KeyWord();
							nkw.setName(fkwname);
							nkw.setNum_t_c(1);
							keywords.add(nkw);
							kwtmpset.remove(kwtmp);
							countHamDFtmp(keywords, kwtmpset);
							if (kwtmpset.size() == 0)
								break;
						}
					}
				}

			}

		}
		nkeywords = keywords;
		return nkeywords;
	}

	/**
	 * �������� ���ݴ���ĳ�ļ����µ������ʼ��ı���ͳ�ƺ�num_t_nc ��num_nt_nc�������� List
	 * <KeyWord> ͳ���ۼ������ʼ�ѵ������ĳ�ʵ�num_t_nc ��num_nt_nc�������ֶε�����
	 * ��ͳ�������ʼ�ѵ�����г��ָôʵ��ĵ�����δ���ָôʵ��ĵ���
	 * 
	 * @param keywords
	 *            ��Ϊ���������ݿ����ʱ�������б����ϸ������е�����
	 * @param kwtmpset
	 *            ĳ�ļ����º�ͳ�ƺ�num_t_nc ��num_nt_nc�������� List<KeyWord>
	 */
	public static List<KeyWord> countSpamDFtmp(List<KeyWord> keywords, List<KeyWord> kwtmpset) {
		List<KeyWord> nkeywords;

		// TODO n ��ѵ�����У������ʼ��ı������ڶ�ȡ�ļ���ʱ���ɻ�ã�д�����ݿ⣬�˴�ֱ�Ӷ�������
		int nham = 100;

		for (int i = 0; i < kwtmpset.size(); i++) {
			// ���һ������ȥ�ش���������ĵ����´�
			KeyWord kwtmp = kwtmpset.get(i);
			String fkwname = kwtmp.getName();
			// int fkwnumtc = fkword.getNum_t_c();

			// ����һ����ʾ������ȷ���ô��Ƿ�Ϊ�´�
			Boolean isnew = true;
			if (fkwname != null) {
				if (keywords.size() == 0) {
					KeyWord nkw = new KeyWord();
					nkw.setName(fkwname);
					nkw.setNum_t_nc(1);
					keywords.add(nkw);
					kwtmpset.remove(kwtmp);
					countSpamDFtmp(keywords, kwtmpset);
					if (kwtmpset.size() == 0)
						break;
				} else {
					for (int j = 0; j < keywords.size(); j++) {
						// ��ùؼ���list�е�ĳ����
						KeyWord kword = keywords.get(j);
						String kwname = kword.getName();
						int kwnumtc = kword.getNum_t_nc();

						// �ж�list�еĸô��Ƿ������ĵ��г���,������������´ʣ�ͬʱnum_t_c������һ,num_nt_cΪ�����ʼ�������ȥnum_t_c
						if (fkwname.equals(kwname)) {
							isnew = false;
							kword.setNum_t_nc(kwnumtc + 1);
							kword.setNum_nt_nc(nham - kword.getNum_t_nc());
							kwtmpset.remove(kwtmp);
						} else if (j == (keywords.size() - 1) && isnew) { // �ж��Ƿ��ѯ��list�е�����keyword������δ�鵽���´�
							KeyWord nkw = new KeyWord();
							nkw.setName(fkwname);
							nkw.setNum_t_nc(1);
							keywords.add(nkw);
							kwtmpset.remove(kwtmp);
							countSpamDFtmp(keywords, kwtmpset);
							if (kwtmpset.size() == 0)
								break;
						}
					}
				}

			}

		}
		nkeywords = keywords;
		return nkeywords;
	}

}
