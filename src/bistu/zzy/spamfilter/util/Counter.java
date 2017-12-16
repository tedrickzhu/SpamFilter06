package bistu.zzy.spamfilter.util;

import java.util.List;

import bistu.zzy.spamfilter.object.KeyWord;
import bistu.zzy.spamfilter.object.TxtData;

public class Counter {

	/* * 函数功能 将传入的正常邮件训练集所有邮件文本产生的统计好的num_t_c 和num_nt_c特征词 List的num_t_c
	 * 和num_nt_c这两个字段的数据 添加到垃圾邮件训练集所有邮件文本产生的统计好的num_t_nc 和num_nt_nc特征词 List中去，
	 * 并返回统计好num_t_c 和num_nt_c，num_t_nc 和num_nt_nc，这四个字段的数据的List
	 * <KeyWord> 并将该list写入数据库 TODO 函数有待检测
	 * 
	 * @param spamkeywords
	 *            垃圾邮件训练集所有邮件文本产生的统计好的num_t_nc 和num_nt_nc特征词 List<KeyWord>
	 * @param hamkeywords
	 *            正常邮件训练集所有邮件文本产生的统计好的num_t_c 和num_nt_c特征词 List<KeyWord>
	 */
	public static List<KeyWord> getSumDF(List<KeyWord> rskeywords, List<KeyWord> tmpkeywords, TxtData txtdata) {
		List<KeyWord> dfkeywords;

		for (int i = 0; i < tmpkeywords.size(); i++) {
			// 获得一个由正常邮件训练集keywordlist(或者是新训练集的list)得来的特征词
			KeyWord tmpkw = tmpkeywords.get(i);
			String tmpkwname = tmpkw.getName();
			int tmpkwnumtc = tmpkw.getNum_t_c();
			int tmpkwnumntc = tmpkw.getNum_nt_c();
			int tmpkwnumtnc = tmpkw.getNum_t_nc();
			int tmpkwnumntnc = tmpkw.getNum_nt_nc();

			// 设置一个标示量，以确定该词是否为新词
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
						// 获得由垃圾邮件训练集得来的关键词list中的某个词
						KeyWord rskw = rskeywords.get(j);
						String rskwname = rskw.getName();
						int rskwnumtc = rskw.getNum_t_c();
						int rskwnumntc = rskw.getNum_nt_c();
						int rskwnumtnc = rskw.getNum_t_nc();
						int rskwnumntnc = rskw.getNum_nt_nc();

						// 判断ham keywords list中的该词是否在spam keywords list中出现
						// 如果出现则不是新词，同时将num_t_c,num_nt_c添加到spam keywords list
						// 中相应的位置
						if (rskwname.equals(tmpkwname)) {
							isnew = false;
							rskw.setNum_t_c(tmpkwnumtc+rskwnumtc);
							rskw.setNum_nt_c(tmpkwnumntc+rskwnumntc);
							rskw.setNum_t_nc(tmpkwnumtnc+rskwnumtnc);
							rskw.setNum_nt_nc(tmpkwnumntnc+rskwnumntnc);
							tmpkeywords.remove(tmpkw);
						} else if (j == (rskeywords.size() - 1) && isnew) { 
							// 判断是否查询了spamlist(或者新训练集list)中的所有keyword，并且未查到该新词
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
	 * 函数功能 将传入的正常邮件训练集所有邮件文本产生的统计好的num_t_c 和num_nt_c特征词 List的num_t_c
	 * 和num_nt_c这两个字段的数据 添加到垃圾邮件训练集所有邮件文本产生的统计好的num_t_nc 和num_nt_nc特征词 List中去，
	 * 并返回统计好num_t_c 和num_nt_c，num_t_nc 和num_nt_nc，这四个字段的数据的List
	 * <KeyWord> 并将该list写入数据库 TODO 函数有待检测
	 * 
	 * @param spamkeywords
	 *            垃圾邮件训练集所有邮件文本产生的统计好的num_t_nc 和num_nt_nc特征词 List<KeyWord>
	 * @param hamkeywords
	 *            正常邮件训练集所有邮件文本产生的统计好的num_t_c 和num_nt_c特征词 List<KeyWord>
	 */
//	public static List<KeyWord> countDF(List<KeyWord> spamkeywords, List<KeyWord> hamkeywords, TxtData txtdata) {
//		List<KeyWord> dfkeywords;
//
//		for (int i = 0; i < hamkeywords.size(); i++) {
//			// 获得一个由正常邮件训练集得来的numtc和numntc有值的特征词
//			KeyWord hamkw = hamkeywords.get(i);
//			String hamkwname = hamkw.getName();
//			int hamkwnumtc = hamkw.getNum_t_c();
//			int hamkwnumntc = hamkw.getNum_nt_c();
//
//			// 设置一个标示量，以确定该词是否为新词
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
//						// 获得由垃圾邮件训练集得来的关键词list中的某个词
//						KeyWord spamkw = spamkeywords.get(j);
//						String spamkwname = spamkw.getName();
//						// int spamkwnumtnc = spamkw.getNum_t_nc();
//						// int spamkwnumntnc = spamkw.getNum_nt_nc();
//
//						// 判断ham keywords list中的该词是否在spam keywords list中出现
//						// 如果出现则不是新词，同时将num_t_c,num_nt_c添加到spam keywords list
//						// 中相应的位置
//						if (spamkwname.equals(hamkwname)) {
//							isnew = false;
//							spamkw.setNum_t_c(hamkwnumtc);
//							spamkw.setNum_nt_c(hamkwnumntc);
//							hamkeywords.remove(hamkw);
//						} else if (j == (spamkeywords.size() - 1) && isnew) { // 判断是否查询了spam
//																				// list中的所有keyword，并且未查到该新词
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
	 * 函数功能  解决关键词numtc,numntc 或numtnc,numntnc，两两同时为0 的问题
	 * 由于在做统计整合特征词的 numtc,numntc ，numtnc,numntnc，这四个字段的值时
	 * 当某关键词只在一类文档中出现时，会产生numtc,numntc 或numtnc,numntnc，两两同时为0 的问题
	 * 此时，计算CHI,则其值就为0，这是不对的
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
			// 获得关键词list中的某个词
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
	 * 函数功能 根据传入单个邮件文本的特征词List<String>统计累加正常邮件训练集中某词的num_t_c 和num_nt_c这两个字段的数据
	 * 即统计正常邮件训练集中出现该词的文档数和未出现该词的文档数
	 * 
	 * @param keywords
	 *            做为待存入数据库的临时特征词列表，不断更新其中的数据
	 * @param fwordslist
	 *            新文本处理后产生的无重复词的List<String>，
	 */
	public static List<KeyWord> countHamDF(List<KeyWord> keywords, List<String> fwordslist, int countham) {
		List<KeyWord> nkeywords;

		// TODO n 是训练集中，正常邮件文本数，在读取文件夹时即可获得，写入数据库，此处直接读出即可
		int nham = countham;

		while (fwordslist.size() != 0) {
			int i = 0;
			String fkwname = fwordslist.get(i);
			// String fkwname = fkword.getName();
			// int fkwnumtc = fkword.getNum_t_c();

			// 设置一个标示量，以确定该词是否为新词
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
						// 获得关键词list中的某个词
						KeyWord kword = keywords.get(j);
						String kwname = kword.getName();
						int kwnumtc = kword.getNum_t_c();

						// 判断list中的该词是否在新文档中出现,如果出现则不是新词，同时num_t_c计数加一,num_nt_c为正常邮件总数减去num_t_c
						if (fkwname.equals(kwname)) {
							isnew = false;
							kword.setNum_t_c(kwnumtc + 1);
							kword.setNum_nt_c(nham - kword.getNum_t_c());
							fwordslist.remove(fkwname);
						} else if (j == (keywords.size() - 1) && isnew) { // 判断是否查询了list中的所有keyword，并且未查到该新词
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
	 * 函数功能 根据传入单个邮件文本的特征词List<String>统计累加垃圾邮件训练集中某词的num_t_nc 和num_nt_nc这两个字段的数据
	 * 即统计垃圾邮件训练集中出现该词的文档数和未出现该词的文档数
	 * 
	 * @param keywords
	 *            做为待存入数据库的临时特征词列表，不断更新其中的数据
	 * @param fwordslist
	 *            新文本处理后产生的无重复词的List<String>，
	 */
	public static List<KeyWord> countSpamDF(List<KeyWord> keywords, List<String> fwordslist, int countspam) {
		List<KeyWord> nkeywords;

		// TODO n 是训练集中，正常邮件文本数，在读取文件夹时即可获得，写入数据库，此处直接读出即可
		int nspam = countspam;

		while (fwordslist.size() != 0) {
			int i = 0;
			String fkwname = fwordslist.get(i);
			// String fkwname = fkword.getName();
			// int fkwnumtc = fkword.getNum_t_c();

			// 设置一个标示量，以确定该词是否为新词
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
						// 获得关键词list中的某个词
						KeyWord kword = keywords.get(j);
						String kwname = kword.getName();
						int kwnumtc = kword.getNum_t_nc();

						// 判断list中的该词是否在新文档中出现,如果出现则不是新词，同时num_t_nc计数加一,num_nt_nc为垃圾邮件总数减去num_t_c
						if (fkwname.equals(kwname)) {
							isnew = false;
							kword.setNum_t_nc(kwnumtc + 1);
							kword.setNum_nt_nc(nspam - kword.getNum_t_nc());
							fwordslist.remove(fkwname);
						} else if (j == (keywords.size() - 1) && isnew) { // 判断是否查询了list中的所有keyword，并且未查到该新词
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
	 * 函数功能 根据传入某文件夹下的所有邮件文本的统计好num_t_c 和num_nt_c的特征词 List
	 * <KeyWord> 统计累加正常邮件训练集中某词的num_t_c 和num_nt_c这两个字段的数据
	 * 即统计正常邮件训练集中出现该词的文档数和未出现该词的文档数
	 * 
	 * @param keywords
	 *            做为待存入数据库的临时特征词列表，不断更新其中的数据
	 * @param kwtmpset
	 *            某文件夹下后统计好num_t_c 和num_nt_c的特征词 List<KeyWord>
	 */
	public static List<KeyWord> countHamDFtmp(List<KeyWord> keywords, List<KeyWord> kwtmpset) {
		List<KeyWord> nkeywords;

		// TODO n 是训练集中，正常邮件文本数，在读取文件夹时即可获得，写入数据库，此处直接读出即可
		int nham = 100;

		for (int i = 0; i < kwtmpset.size(); i++) {
			// 获得一个根据去重处理过的新文档中新词
			KeyWord kwtmp = kwtmpset.get(i);
			String fkwname = kwtmp.getName();
			// int fkwnumtc = fkword.getNum_t_c();

			// 设置一个标示量，以确定该词是否为新词
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
						// 获得关键词list中的某个词
						KeyWord kword = keywords.get(j);
						String kwname = kword.getName();
						int kwnumtc = kword.getNum_t_c();

						// 判断list中的该词是否在新文档中出现,如果出现则不是新词，同时num_t_c计数加一,num_nt_c为正常邮件总数减去num_t_c
						if (fkwname.equals(kwname)) {
							isnew = false;
							kword.setNum_t_c(kwnumtc + 1);
							kword.setNum_nt_c(nham - kword.getNum_t_c());
							kwtmpset.remove(kwtmp);
						} else if (j == (keywords.size() - 1) && isnew) { // 判断是否查询了list中的所有keyword，并且未查到该新词
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
	 * 函数功能 根据传入某文件夹下的所有邮件文本的统计好num_t_nc 和num_nt_nc的特征词 List
	 * <KeyWord> 统计累加垃圾邮件训练集中某词的num_t_nc 和num_nt_nc这两个字段的数据
	 * 即统计垃圾邮件训练集中出现该词的文档数和未出现该词的文档数
	 * 
	 * @param keywords
	 *            做为待存入数据库的临时特征词列表，不断更新其中的数据
	 * @param kwtmpset
	 *            某文件夹下后统计好num_t_nc 和num_nt_nc的特征词 List<KeyWord>
	 */
	public static List<KeyWord> countSpamDFtmp(List<KeyWord> keywords, List<KeyWord> kwtmpset) {
		List<KeyWord> nkeywords;

		// TODO n 是训练集中，正常邮件文本数，在读取文件夹时即可获得，写入数据库，此处直接读出即可
		int nham = 100;

		for (int i = 0; i < kwtmpset.size(); i++) {
			// 获得一个根据去重处理过的新文档中新词
			KeyWord kwtmp = kwtmpset.get(i);
			String fkwname = kwtmp.getName();
			// int fkwnumtc = fkword.getNum_t_c();

			// 设置一个标示量，以确定该词是否为新词
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
						// 获得关键词list中的某个词
						KeyWord kword = keywords.get(j);
						String kwname = kword.getName();
						int kwnumtc = kword.getNum_t_nc();

						// 判断list中的该词是否在新文档中出现,如果出现则不是新词，同时num_t_c计数加一,num_nt_c为正常邮件总数减去num_t_c
						if (fkwname.equals(kwname)) {
							isnew = false;
							kword.setNum_t_nc(kwnumtc + 1);
							kword.setNum_nt_nc(nham - kword.getNum_t_nc());
							kwtmpset.remove(kwtmp);
						} else if (j == (keywords.size() - 1) && isnew) { // 判断是否查询了list中的所有keyword，并且未查到该新词
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
