package bistu.zzy.spamfilter.main;

import java.util.List;

import bistu.zzy.spamfilter.io.MailFileReader;
import bistu.zzy.spamfilter.sgmt.Segment;

public class TestBgMain {

	public static void main(String[] args) {

		String sfilepath = "G:/TrainSet/spam-gb2312-50/8";

		Segment.initSegmentation();

		getSingfileWordsNum(sfilepath);

		Segment.CLibrary.Instance.NLPIR_Exit();
	}

	public static void getSingfileWordsNum(String sfilepath) {
		String content = null;
		content = MailFileReader.splitMail(sfilepath);
		System.out.println("这是分词文本：" + content);
		String[] fwords = Segment.getFirstWords(content);

		String sgmtstr = Segment.getSegmentString(content);
		System.out.println("这是分词词串：" + sgmtstr);
		String[] sgmtwords = Segment.getSegmentWords(sgmtstr);
		
		List<String> kwliststr = Segment.getFWordslist(content);
		

		int countsgmtw = 0;
		int countfw = 0;
		System.out.println("这是拆分后分词：");
		for (int i = 0; i < sgmtwords.length; i++) {
			//System.out.println(sgmtwords[i]);
			if (sgmtwords[i] != null)
				countsgmtw++;
		}
		for (int i = 0; i < fwords.length; i++) {
			// System.out.println(fwords[i]);
			if (fwords[i] != null)
				countfw++;
		}
		
		for(int i=0;i<kwliststr.size();i++){
			 System.out.println(kwliststr.get(i));
		}

		System.out.println("这是所有分词的个数：" + countsgmtw);
		System.out.println("这是过滤后的分词的个数：" + countfw);

		System.out.println("这是\t测试\t\t制表符的输出模式！");
	}

}
