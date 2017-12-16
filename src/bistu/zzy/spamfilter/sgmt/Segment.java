package bistu.zzy.spamfilter.sgmt;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.sun.jna.Library;
import com.sun.jna.Native;

import bistu.zzy.spamfilter.object.KeyWord;

public class Segment {

	// ����ӿ�CLibrary���̳���com.sun.jna.Library
	public interface CLibrary extends Library {
		/*
		 * ���岢��ʼ���ӿڵľ�̬���� ��һ�������������dll�ģ�
		 * ע��dll�ļ���·�������Ǿ���·��Ҳ���������·����ֻ��Ҫ��дdll���ļ��������ܼӺ�׺��
		 */
		CLibrary Instance = (CLibrary) Native.loadLibrary("NLPIR", CLibrary.class);

		// D://programme//JEEMars//JnaTest_V1//NLPIR
		// printf��������
		public int NLPIR_Init(byte[] sDataPath, int encoding, byte[] sLicenceCode);

		public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);

		public String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit, boolean bWeightOut);

		// �����û��ʻ㵽�û��ʵ�
		public int NLPIR_SaveTheUsrDic();

		// �����û��Զ���ʵ䣺�Զ���ʵ�·����bOverwrite=true��ʾ�����ǰ���Զ���ʵ䣬false��ʾ��ӵ���ǰ�Զ���ʵ��
		public int NLPIR_ImportUserDict(String sFilename, boolean bOverwrite);

		public void NLPIR_Exit();
	}

	public static String transString(String aidString, String ori_encoding, String new_encoding) {
		try {
			return new String(aidString.getBytes(ori_encoding), new_encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * �������� ��ʼ���ִ���
	 */
	public static void initSegmentation() {
		String argu = "";
		// String system_charset = "GBK";//GBK----0
		String system_charset = "GBK";
		int charset_type = 1;
		// int charset_type = 0;
		// ����printf��ӡ��Ϣ
		int init_flag = 0;
		try {
			init_flag = CLibrary.Instance.NLPIR_Init(argu.getBytes(system_charset), charset_type,
					"0".getBytes(system_charset));
			// System.err.println(init_flag);
			if (0 == init_flag) {
				System.err.println("��ʼ��ʧ�ܣ�");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * ע�����Ӧ�ڷ������ĵ����ͷŷִ��� 1.�ڷ����������ĵ����ͷŷִ��� 2.ÿ�η���һ���ĵ�ʱ�³�ʼ��һ�������ĵ������������ͷ�
		 * ����ʹ�÷���һ CLibrary.Instance.NLPIR_Exit();
		 */

	}

	/*
	 * �������� ����������ַ������÷ִʺ�������ֺ��������˺�����������й��ˣ��޳����ôʣ�������� ������õ�list �������� �ɷִʺ�õ���
	 * �ɡ� ��/���� ����ɵ��ַ�������
	 */
	public static List<String> getFWordslist(String sinput) {

		String[] sgmtwords = Segment.getSegmentWords(Segment.getSegmentString(sinput));

		Set<String> fwordset = getFirstWords(sgmtwords);

		List<String> fwliststr = new ArrayList<String>(fwordset);

		// for (int i = 0; i < fwliststr.size(); i++) {
		// String kwstr1 = fwliststr.get(i);
		// for (int j = i + 1; j < fwliststr.size();) {
		// String kwstr2 = fwliststr.get(j);
		// if (kwstr1.equals(kwstr2)) {
		// System.out.println("set��ȥ��Ч����δ��ɣ�");
		// fwliststr.remove(j);
		// j = j - 1;
		// }
		// j++;
		// }
		// }
		//
		// for (int a = 0; a < fwliststr.size(); a++) {
		// System.out.println(fwliststr.get(a));
		// }

		return fwliststr;
	}

	public static List<KeyWord> getFkwslist(String sinput) {

		List<KeyWord> fkwslist = new ArrayList<KeyWord>();

		String[] sgmtwords = Segment.getSegmentWords(Segment.getSegmentString(sinput));

		Set<String> fwordset = getFirstWords(sgmtwords);

		List<String> fwliststr = new ArrayList<String>(fwordset);

		for (int i = 0; i < fwliststr.size(); i++) {
			KeyWord nkw = new KeyWord();
			String name = fwliststr.get(i);
			nkw.setName(name);
			fkwslist.add(nkw);
		}

		return fkwslist;
	}

	/*
	 * �������� ����������ַ������÷ִʺ�������ֺ��������˺�����������й��ˣ��޳����ôʣ�������� ������õ�string�ʹ��飬��ʽΪ�ʣ�����
	 * 
	 * �������� �ɷִʺ�õ��� �ɡ� ��/���� ����ɵ��ַ�������
	 */
	public static String[] getFirstWords(String sinput) {

		Set<String> fwordset = null;

		String[] sgmtwords = Segment.getSegmentWords(Segment.getSegmentString(sinput));

		fwordset = getFirstWords(sgmtwords);

		List<String> fwordslist = new ArrayList<String>(fwordset);

		// TODO ��ȥ���ظ��ʵ��ĵ������ʷ���string���黹��list�У�����set �У�
		// ���ﻹ�Ƿ���string������
		String[] fwords = new String[350];

		for (int i = 0; i < fwordslist.size(); i++) {
			fwords[i] = fwordslist.get(i);
		}

		return fwords;
	}

	/*
	 * �������� ����������ַ������ı�����������зִʣ��õ��ִʽ��string �ִʽ�����ַ������ͣ���ʽΪ����������/����[]��/���ԡ���������
	 * 
	 * �������� Ҫ�ִʵ��ı����ַ���
	 */
	public static String getSegmentString(String sInput) {
		// ���ڽ��շִʽ�����ַ������ͣ���ʽΪ����������/����[]��/���ԡ���������
		String sgmtstr = null;
		/*
		 * ��ʼ���ִ���,��Ӧ�� ��������ã� ���ڴ������ļ��Ļ����ͻ��ʼ�������û�����Ҫ��ֻ���˷��ڴ棬
		 * ����main���������ʱ�պÿ����ͷ�
		 */
		// initSegmentation();

		// ����û��ʵ�ִ�
//		int count = 0;
//		String userDir = "spamdict.txt"; // �û��ʵ�·��
//		//byte[] userDirb = userDir.getBytes();
//		count = CLibrary.Instance.NLPIR_ImportUserDict(userDir, false);
//		System.out.println("\n�����û��ʸ�����\t" + count);

		sgmtstr = CLibrary.Instance.NLPIR_ParagraphProcess(sInput, 1);
		// String nativeStr = new String(nativeBytes, 0,
		// nativeBytes.length,"utf-8");
		// System.out.println("���ִ��ı�Ϊ�� " + sInput);
		// System.out.println("�ִʽ��Ϊ�� " + sgmtstr);

		return sgmtstr;
	}

	/*
	 * �������� ��������ķִʽ���ַ�������������ַ�����֣� ������õ�string�ʹ��飬��ʽΪ����/���ԡ�
	 * 
	 * �������� Ҫ�ִʺ�õ��� �ɡ� ��/���� ����ɵ��ַ���
	 */
	public static String[] getSegmentWords(String sgmtstr) {
		String[] sgmtwords = null;
		try {
			/*
			 * ����ַ���������.��StringTokenizer����в��� ���Ա��������ո� �����⣬���ǣ����鳤�Ȳ������Զ����ӣ� TODO
			 * ������G:/TrainSet/spam-gb2312/16�ļ�ʱ�����������ո��޷����������⡣
			 * ���鿴ԭʼ�ļ����ǲ��ǿո񣬶��Ƕ���һ��δ֪���ţ� �����ԣ���δ֪����Ϊȫ�ǿո�
			 * ���ԣ�StringTokenizer����в���ַ��� ���Ժ��������ո�Ľ�������ȷ��
			 */
			// ���տո���н�ȡ
			StringTokenizer token1 = new StringTokenizer(sgmtstr, " ");
			// StringTokenizer token2 = new StringTokenizer(str2, "/");
			// ����"/"���н�ȡ
			// ����һ���ַ�������,�����洢��ֺ�Ĵ��顣
			sgmtwords = new String[2000];
			// ���ȫ�ǿո�Ƿ��洢���⣬�����޳������ٴ洢
			int i = 0;
			String wpos = "";
			while (token1.hasMoreTokens()) {
				// ���ָ�����ַ�������wpos�У�Ȼ���ж��Ƿ�Ϊȫ�ǿո񣬲��ǲŴ洢������
				wpos = token1.nextToken();
				if (!wpos.equals("��")) {
					sgmtwords[i] = wpos;
					i++;
				}
			}
			// System.out.println("���-��/����-��string�飡 ");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// for (int a = 0; a < sgmtwords.length; a++) {
		// System.out.println(sgmtwords[a]);
		// }
		return sgmtwords;
	}

	/*
	 * �������� ��������ķִʽ���ַ�������������ַ�����֣� ������õ�List<String>�ʹ��飬��ʽΪ����/���ԡ�
	 * 
	 * �������� Ҫ�ִʺ�õ��� �ɡ� ��/���� ����ɵ��ַ���
	 */
	public static List<String> getSegmentWordslist(String sgmtstr) {
		List<String> sgmtwdslist = new ArrayList<String>();
		try {
			// ���տո���н�ȡ
			StringTokenizer token1 = new StringTokenizer(sgmtstr, " ");

			// ���ȫ�ǿո�Ƿ��洢���⣬�����޳������ٴ洢
			int i = 0;
			String wpos = "";
			while (token1.hasMoreTokens()) {
				// ���ָ�����ַ�������wpos�У�Ȼ���ж��Ƿ�Ϊȫ�ǿո񣬲��ǲŴ洢������
				wpos = token1.nextToken();
				if (!wpos.equals("��")) {
					sgmtwdslist.add(wpos);
					i++;
				}

			}
			// System.out.println("���-��/����-��string�飡 ");

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return sgmtwdslist;
	}

	/*
	 * �������� ��������Ĳ�ֺõķִʴ��飬������й��ˣ��޳����ôʣ�������� ������õ�string�ʹ��飬��ʽΪ�ʣ�����
	 * 
	 * �������� �ɷִʺ�õ��� �ɡ� ��/���� ����ɵ��ַ�������
	 */
	public static Set<String> getFirstWords(String[] sgmtwords) {
		/**
		 * ����һ��set������hashset����ļ��ϣ����ظ��Ĵ洢��õĴʣ� ����һƪ�ĵ��е��ظ����ֵĴʲ��ᱻ��¼��Σ�ֻ��¼һ��
		 * ���ǣ�����set������������ݣ��轫��ת����List<String>;
		 */
		Set<String> fwordset = new HashSet<String>();
		try {
			/*
			 * 2.��StringTokenizer����в��� ���Ա��������ո� �����⣬���ǣ����鳤�Ȳ������Զ����ӣ�
			 */

			// TODO �����ԣ��˴��޷���list����Ϊlist�У����𿪵Ĵ�add��list���޷�׼ȷ��ȡ
			// List<String> array2 = new ArrayList<String>();

			// ����һ���ַ�������,�����洢���β�ֺ�Ĵʼ����ԡ�
			String[] array2 = new String[2];
			array2[0]="";
			array2[1]="o";

			// int wiki = 0;

			for (int i = 0; i < sgmtwords.length; i++) {
				if (sgmtwords[i] != null) {
					int k = 0;
					// ����"/"���н�ȡ
					StringTokenizer token2 = new StringTokenizer(sgmtwords[i], "/");
					while (token2.hasMoreTokens()) {
						array2[k] = token2.nextToken();// ���ָ�����ַ�������������
						k++;
						if(k>1)break;
					}
					/*
					 * TODO
					 * �����´�����ԣ���������ж����/������ָ�����������ַ�����������ʱ�洢������array�ĳ���Ӧ�ô���2��
					 * �纬����ַ��http://www.baidu.com,��ִʽ��Ϊhttp://www.baidu.com/xu
					 * ��ʱ����/���ָ����ƻ�����ַ�������ԣ�����3���ַ�����http:,www.baidu.com,xu
					 * ��������ַ��http://www.hecaitou.com/blogs/hecaitou/archives/
					 * 122023.aspx, �����ַ���������������ַ���
					 */
					// int c = 0;
					// for(int a=0;a<array2.length;a++){
					// if(array2[a]!=null)c++;
					// }
					// System.out.println("����ÿ������б�ָܷ��󣬲��������ַ�����------------------------------"+c);

					/*
					 * TODO ��������ʽ���д���ƥ��,Ȼ�����
					 * n���ʣ�tʱ��ʣ�s�����ʣ�f��λ�ʣ�v���ʣ�a���ݴʣ�b����ʣ�z״̬�ʣ�r����
					 * m���ʣ�q���ʣ�d���ʣ�p��ʣ�c���ʣ�u���ʣ�e̾�ʣ�y�����ʣ�o�����ʣ�hǰ׺ k��׺��x�ַ�����w������
					 */
					if (!array2[1].matches("^(nr|rr|z|r|m|q|d|p|c|u|e|y|o|h|k|x|w).*")) {
						// fwords[wiki] = array2[0];
						// wiki++;
						fwordset.add(array2[0]);// ���������ôʵĴʡ����ظ��ķ��뵽set�����У�
						// System.out.println("this is test set!----" +
						// array2[0]);
					}

					// if ((!array2[1].equals("wt") &&
					// (!array2[1].equals("wd")))) {
					// fwords[wiki] = array2[0];
					// wiki++;
					// }
				}
			}

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return fwordset;
	}

	/*
	 * �������� ��������Ĳ�ֺõķִʴ��飬������й��ˣ��޳����ôʣ�������� ������õ�string�ʹ��飬��ʽΪ�ʣ�����
	 * 
	 * �������� �ɷִʺ�õ��� �ɡ� ��/���� ����ɵ��ַ�������
	 */
	public static Set<String> getFirstWords(List<String> sgmtwdslist) {
		/**
		 * ����һ��set������hashset����ļ��ϣ����ظ��Ĵ洢��õĴʣ� ����һƪ�ĵ��е��ظ����ֵĴʲ��ᱻ��¼��Σ�ֻ��¼һ��
		 * ���ǣ�����set������������ݣ��轫��ת����List<String>;
		 */
		Set<String> fwordset = new HashSet<String>();
		try {

			// ����һ���ַ�������,�����洢��ֺ�Ĵ��顣
			String[] array2 = new String[10];
			// int wiki = 0;

			for (int i = 0; i < sgmtwdslist.size(); i++) {
				if (sgmtwdslist.get(i) != null) {
					int k = 0;
					// ����"/"���н�ȡ
					StringTokenizer token2 = new StringTokenizer(sgmtwdslist.get(i), "/");
					while (token2.hasMoreTokens()) {
						array2[k] = token2.nextToken();// ���ָ�����ַ�������������
						k++;
					}

					/*
					 * TODO ��������ʽ���д���ƥ��,Ȼ�����
					 * n���ʣ�tʱ��ʣ�s�����ʣ�f��λ�ʣ�v���ʣ�a���ݴʣ�b����ʣ�z״̬�ʣ�r����
					 * m���ʣ�q���ʣ�d���ʣ�p��ʣ�c���ʣ�u���ʣ�e̾�ʣ�y�����ʣ�o�����ʣ�hǰ׺ k��׺��x�ַ�����w������
					 */
					if (!array2[1].matches("^(nr|rr|z|r|m|q|d|p|c|u|e|y|o|h|k|x|w).*")) {
						// fwords[wiki] = array2[0];
						// wiki++;
						fwordset.add(array2[0]);// ���������ôʵĴʡ����ظ��ķ��뵽set�����У�
					}
				}
			}

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return fwordset;
	}

}
