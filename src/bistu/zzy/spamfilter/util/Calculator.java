package bistu.zzy.spamfilter.util;

public class Calculator {

	/*
	 * 函数功能 获得某词属于正常邮件类别的卡方统计量CHI 参数解释 int ntxt; 表示所有训练语料的文本数，即正常邮件语料数和垃圾邮件语料数之和
	 * int num_t_c ; 表示在正常邮件集中包含词 t的文档频数 , int num_t_nc; 表示在垃圾邮件集中包含词t 的文档频数 ,
	 * int num_nt_c; 表示在正常邮件集中不包含 t 的文档频数, int num_nt_nc; 表示在垃圾邮件集中不包含 t 的文档频数
	 */
	public static double getCHIham(int ntxt, int num_t_c, int num_t_nc, int num_nt_c, int num_nt_nc) {
		// TODO Auto-generated constructor stub
		double CHIham = 1;
		int x = ((num_t_c * num_nt_nc) - (num_nt_c * num_t_nc));// AD-CB
		int y = (num_t_c + num_nt_c) * (num_t_nc + num_nt_nc) * (num_nt_c + num_nt_nc);// (A+C)*(B+D)*(C+D)
		int z = (num_nt_c + num_nt_nc);// C+D
		CHIham = (ntxt * Math.pow(x, 2)) / (y + z);

		return CHIham;
	}

	/*
	 * 函数功能 获得某词属于垃圾邮件类别的卡方统计量CHI 参数解释 int ntxt; 表示所有训练语料的文本数，即正常邮件语料数和垃圾邮件语料数之和
	 * int num_t_c ; 表示在正常邮件集中包含词 t的文档频数 , int num_t_nc; 表示在垃圾邮件集中包含词t 的文档频数 ,
	 * int num_nt_c; 表示在正常邮件集中不包含 t 的文档频数, int num_nt_nc; 表示在垃圾邮件集中不包含 t 的文档频数
	 */
	public static double getCHIspam(int ntxt, int num_t_c, int num_t_nc, int num_nt_c, int num_nt_nc) {
		// TODO Auto-generated constructor stub
		double CHIspam = 1;
		int x = ((num_t_nc * num_nt_c) - (num_nt_nc * num_t_c));// AD-CB
		int y = (num_t_nc + num_nt_nc) * (num_t_c + num_nt_c) * (num_nt_nc + num_nt_c);// (A+C)*(B+D)*(C+D)
		int z = (num_nt_nc + num_nt_c);// C+D
		CHIspam = (ntxt * Math.pow(x, 2)) / (y + z);

		return CHIspam;
	}

	/*
	 * 函数功能 获得某词属于正常邮件或垃圾邮件类别的概率 
	 * 参数解释 
	 * @param int ntxtc 表示所有该类别邮件语料的文本数
	 * int nct 表示在该类邮件集中包含词 t的文档频数 ,即num_t_c 或num_t_nc 
	 * int m 表示chi降维后的特征词的数量
	 */
	public static double getWordPC(int ntxtc, int nct, int m) {
		double wordpham = 0.00;

		wordpham = (double) (nct + 1) / (ntxtc + m);
		// System.out.println("nhamt:"+nhamt);
		// System.out.println("ntxtham:"+ntxtham);
		// System.out.println("m:"+m);
		// System.out.println("计算wordprobabilityham!"+wordpham);
		return wordpham;
	}

	/*
	 * 函数功能 计算正常邮件语料或者垃圾邮件语料占所有训练语料的比例 参数解释 int ntxt;
	 * 表示所有训练语料的文本数，即正常邮件语料数和垃圾邮件语料数之和 int ntxtc; 表示所有正常邮件语料的文本数，
	 */
	public static double getRatioC(int ntxt, int ntxtc) {
		// TODO Auto-generated constructor stub
		double ratioc = 1.00;
		ratioc = (double) ntxtc / ntxt;
		return ratioc;
	}

	/*
	 * 函数功能 获得属于垃圾邮件的概率和属于正常邮件的概率的比值 参数解释 double txtpham; 表示文本属于正常邮件的概率 double
	 * txtpspam; 表示文本属于垃圾邮件的概率
	 */
	public static double getRatioQuotient(double txtpham, double txtpspam) {
		// TODO Auto-generated constructor stub
		double quotient = 1.00;
		quotient = (double) txtpspam / txtpham;
		return quotient;
	}

	/*
	 * 函数功能 获得属于垃圾邮件的概率和属于正常邮件的概率的差值 参数解释 double txtpham; 表示文本属于正常邮件的概率 double
	 * txtpspam; 表示文本属于垃圾邮件的概率
	 */
	public static double getRatioDifference(double txtpham, double txtpspam) {
		// TODO Auto-generated constructor stub
		double difference = 0;
		difference = (double) txtpspam - txtpham;
		return difference;
	}

}
