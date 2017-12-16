package bistu.zzy.spamfilter.main;

import java.io.File;
import java.util.List;

import bistu.zzy.spamfilter.db.TableP;
import bistu.zzy.spamfilter.io.MailFileReader;
import bistu.zzy.spamfilter.object.KeyWord;
import bistu.zzy.spamfilter.sgmt.Segment;
import bistu.zzy.spamfilter.ui.SFFrame;
import bistu.zzy.spamfilter.util.Bayes;
import bistu.zzy.spamfilter.util.Calculator;

public class FilterMain {
	
	/**
	 * 函数功能 根据文件目录，读取测试文件或测试邮件集目录下 的所有文件
	 * 
	 * @param testpath
	 * @param uiframe
	 */
	public static void filter(String testpath, SFFrame uiframe) {
		long startTime1 = System.currentTimeMillis();
		long startTime2 = System.nanoTime();

		// List<KeyWord> fkwslist = null;
		String content = "";
		File file = new File(testpath);
		// 初始化分词器
		// Segment.initSegmentation();

		if (file.isFile()) {
			if (file.isFile()) {
				content = MailFileReader.readSingleFile(file);
				// System.out.println(content);
				// TODO 此处应该执行分词操作
				// System.out.println("path=" + file.getPath());
				uiframe.getTextArea_filte().append("path=" + file.getPath() + "\n");
				uiframe.getTextArea_filte().append("邮件内容为：\n" + content + "\n\n");
				doFilte(content, uiframe);
			}
		} else if (file.isDirectory()) {
			// System.out.println("this is a directory!");
			// 获得所有文件
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File sfile = new File(testpath + "\\" + filelist[i]);
				if (!sfile.isDirectory()) {
					// System.out.println("path=" + sfile.getPath());
					uiframe.getTextArea_filte().append("path=" + sfile.getPath() + "\n");
					content = MailFileReader.readSingleFile(sfile);
					uiframe.getTextArea_filte().append("邮件内容为：\n" + content + "\n\n");
					// TODO 此处应该执行分词操作,及后续操作
					doFilte(content, uiframe);
				} else if (sfile.isDirectory()) {
					// 递归调用，读取子文件夹下的文件
					filter(testpath + "\\" + filelist[i], uiframe);
				}
			}
		} else {
			// System.out.println("过滤测试邮件路径有错误！请确定文件路径正确！\n");
			uiframe.getTextArea_filte().append("过滤测试邮件路径有错误！请确定文件路径正确！\n");
		}
		// 关闭分词器
		// Segment.CLibrary.Instance.NLPIR_Exit();
		long endTime1 = System.currentTimeMillis();
		long endTime2 = System.nanoTime();
		// System.out.println("\n运算用时：" + (endTime1 - startTime1) + "ms," +
		// (endTime2 - startTime2) + "ns.");
		uiframe.getTextArea_filte().append("\n---过滤测试完成！---\n");
		uiframe.getTextArea_filte()
				.append("\n运算用时：" + (endTime1 - startTime1) + "ms," + (endTime2 - startTime2) + "ns.");
	}

	/**
	 * 函数功能 对每个 邮件的文本进行分析判断，给出结果
	 * 
	 * @param content
	 * @param uiframe
	 */
	public static void doFilte(String content, SFFrame uiframe) {
		uiframe.getTextArea_filte().append("---正在进行分词---请耐心等候！\n");
		// 获得去除无用词的词
		List<KeyWord> fkwslist = Segment.getFkwslist(content);
		// 只保留在特征词概率库中有数据的词
		fkwslist = TableP.selectTablep(fkwslist);
		// 计算文本的两个分类概率
		uiframe.getTextArea_filte().append("---正在计算分类概率---请耐心等候！\n\n");
		double[] txtp = Bayes.getTxtP(fkwslist);
		// 计算两个概率的比值和差值
		double rqhs = Calculator.getRatioQuotient(txtp[0], txtp[1]);
		double rdhs = Calculator.getRatioDifference(txtp[0], txtp[1]);
		// 输出概率的值
		uiframe.getTextArea_filte().append("该邮件是正常邮件的概率是：" + txtp[0] + "\n");
		uiframe.getTextArea_filte().append("该邮件是垃圾邮件的概率是：" + txtp[1] + "\n");
		uiframe.getTextArea_filte().append("垃圾邮件概率与正常邮件的概率比是：" + rqhs + "\n");
		uiframe.getTextArea_filte().append("垃圾邮件概率与正常邮件的概率差是：" + rdhs + "\n\n");
		// System.out.println("该邮件是正常邮件的概率是：" + txtp[0]);
		// System.out.println("该邮件是垃圾邮件的概率是：" + txtp[1]);
		// System.out.println("垃圾邮件概率与正常邮件的概率比是：" + rqhs);
		// System.out.println("垃圾邮件概率与正常邮件的概率差是：" + rdhs);
		// 根据大小，比值，差值进行判断
		if (txtp[0] < txtp[1]) {
			// System.out.println("\n该邮件是垃圾邮件!\n");
			uiframe.getTextArea_filte().append("该邮件是垃圾邮件!\n\n");
		} else if (txtp[0] > txtp[1]) {
			// System.out.println("\n该邮件是正常邮件!\n");
			uiframe.getTextArea_filte().append("该邮件是正常邮件!\n\n");
		} else {
			// System.out.println("\n这是一个异常情况！在下才学疏浅，无法判断！还望见谅！\n");
			uiframe.getTextArea_filte().append("这是一个异常情况！在下才学疏浅，无法判断！还望见谅！\n");
		}

	}

	
	public static void testFilter(String testhampath,String testspampath, SFFrame uiframe){
		long startTime1 = System.currentTimeMillis();
		long startTime2 = System.nanoTime();
		uiframe.getTextArea_test().append("正在进行性能测试，请耐心等待！\n");
		int[] resultham = getData(testhampath, uiframe);
		int[] resultspam = getData(testspampath, uiframe);
		
		int a = resultspam[1];
		int b = resultham[1];
		int c = resultspam[0];
		int d = resultham[0];
		
		int ham = resultham[0]+resultham[1]+resultham[2];
		int spam = resultspam[0]+resultspam[1]+resultspam[2];
		
		double recall = (double)a/(a+c); 
		double miss = (double)b/(b+d);
		double less = (double)c/(a+c);
		double correct = (double)(a+d)/(a+b+c+d);
		
		uiframe.getTextArea_test().append("\n------共测试合法邮件："+ham+" 个\n");
		uiframe.getTextArea_test().append("--------判为合法邮件："+resultham[0]+" 个;判为垃圾邮件："+resultham[1]+" 个;待定邮件："+resultham[2]+"个\n");
		uiframe.getTextArea_test().append("\n------共测试垃圾邮件："+spam+" 个\n");
		uiframe.getTextArea_test().append("--------判为垃圾邮件："+resultspam[1]+" 个 ;判为合法邮件："+resultspam[0]+" 个;待定邮件："+resultspam[2]+"个\n");
		uiframe.getTextArea_test().append("\n------召回率是："+recall);
		uiframe.getTextArea_test().append("\n------误判率是："+miss);
		uiframe.getTextArea_test().append("\n------漏判率是："+less);
		uiframe.getTextArea_test().append("\n------正确率是："+correct+"\n");
		
		long endTime1 = System.currentTimeMillis();
		long endTime2 = System.nanoTime();
		// System.out.println("\n运算用时：" + (endTime1 - startTime1) + "ms," +
		// (endTime2 - startTime2) + "ns.");
		uiframe.getTextArea_test().append("\n---过滤测试完成！---\n");
		uiframe.getTextArea_test().append("\n运算用时：" + (endTime1 - startTime1) + "ms," + (endTime2 - startTime2) + "ns.");
	
	}
	
	/**
	 * 函数功能   获得计算召回率，误判率，漏判率，正确率的所需数据
	 * @param testpath
	 * @param uiframe
	 * @return
	 */
	public static int[] getData(String testpath, SFFrame uiframe) {

		int[] results = new int[3];
		int result0 = 0;
		int result1 = 0;
		int result2 = 0;
		int resulttmp ;
		
		String content = "";
		File file = new File(testpath);
		if (file.isFile()) {
			if (file.isFile()) {
				content = MailFileReader.readSingleFile(file);
				uiframe.getTextArea_test().append("path=" + file.getPath() + "\n");
				resulttmp = dotestFilte(content, uiframe);
				if (resulttmp == 0){
					result0 = result0 + 1;
				}else if (resulttmp == 1){
					result1 = result1 + 1;
				}else if (resulttmp == 2){
					result2 = result2 + 1;
				}
			}
		} else if (file.isDirectory()) {
			// 获得所有文件
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File sfile = new File(testpath + "\\" + filelist[i]);
				if (!sfile.isDirectory()) {
					// System.out.println("path=" + sfile.getPath());
//					uiframe.getTextArea_test().append("path=" + sfile.getPath() + "\n");
					content = MailFileReader.readSingleFile(sfile);
					resulttmp = dotestFilte(content, uiframe);
					if (resulttmp == 0){
						result0 = result0 + 1;
					}else if (resulttmp == 1){
						result1 = result1 + 1;
					}else if (resulttmp == 2){
						result2 = result2 + 1;
					}
				} else if (sfile.isDirectory()) {
					// 递归调用，读取子文件夹下的文件
					getData(testpath + "\\" + filelist[i], uiframe);
				}
			}
		} else {
			// System.out.println("过滤测试邮件路径有错误！请确定文件路径正确！\n");
			uiframe.getTextArea_test().append("过滤测试邮件路径有错误！请确定文件路径正确！\n");
		}
		results[0]=result0;
		results[1]=result1;
		results[2]=result2;
		
		return results;
	}

	/**
	 * 函数功能 对每个 邮件的文本进行分析判断，给出结果
	 * 
	 * @param content
	 * @param uiframe
	 */
	public static int dotestFilte(String content, SFFrame uiframe) {
		int result = 2;
//		uiframe.getTextArea_test().append("---正在进行分词---请耐心等候！\n");
		// 获得去除无用词的词
		List<KeyWord> fkwslist = Segment.getFkwslist(content);
		// 只保留在特征词概率库中有数据的词
		fkwslist = TableP.selectTablep(fkwslist);
		// 计算文本的两个分类概率
//		uiframe.getTextArea_test().append("---正在计算分类概率---请耐心等候！\n\n");
		double[] txtp = Bayes.getTxtP(fkwslist);
		// 计算两个概率的比值和差值
		double rqhs = Calculator.getRatioQuotient(txtp[0], txtp[1]);
		double rdhs = Calculator.getRatioDifference(txtp[0], txtp[1]);
		// 输出概率的值
//		uiframe.getTextArea_test().append("该邮件是正常邮件的概率是：" + txtp[0] + "\n");
//		uiframe.getTextArea_test().append("该邮件是垃圾邮件的概率是：" + txtp[1] + "\n");
//		uiframe.getTextArea_test().append("垃圾邮件概率与正常邮件的概率比是：" + rqhs + "\n");
//		uiframe.getTextArea_test().append("垃圾邮件概率与正常邮件的概率差是：" + rdhs + "\n\n");
		// System.out.println("该邮件是正常邮件的概率是：" + txtp[0]);
		// System.out.println("该邮件是垃圾邮件的概率是：" + txtp[1]);
		// System.out.println("垃圾邮件概率与正常邮件的概率比是：" + rqhs);
		// System.out.println("垃圾邮件概率与正常邮件的概率差是：" + rdhs);
		// 根据大小，比值，差值进行判断
		if (txtp[0] < txtp[1]) {
			// System.out.println("\n该邮件是垃圾邮件!\n");
//			uiframe.getTextArea_test().append("该邮件是垃圾邮件!\n");
			result = 1;
		} else if (txtp[0] > txtp[1]) {
			// System.out.println("\n该邮件是正常邮件!\n");
//			uiframe.getTextArea_test().append("该邮件是正常邮件!\n");
			result = 0;
		} else {
			// System.out.println("\n这是一个异常情况！在下才学疏浅，无法判断！还望见谅！\n");
//			uiframe.getTextArea_test().append("这是一个异常情况！在下才学疏浅，无法判断！还望见谅！\n");
			result = 2;
		}
		return result;
	}

	// public static void main(String[] args) throws
	// MySQLIntegrityConstraintViolationException {
	// long startTime1 = System.currentTimeMillis();
	// long startTime2 = System.nanoTime();
	// String testpath = "G:/TrainSet/ham-5";
	//
	// //Filter(testpath);
	//
	// long endTime1 = System.currentTimeMillis();
	// long endTime2 = System.nanoTime();
	// System.out.println("\n运算用时：" + (endTime1 - startTime1) + "ms," +
	// (endTime2 - startTime2) + "ns.");
	// }

}
