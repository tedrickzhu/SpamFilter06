package bistu.zzy.spamfilter.util;

public class Calculator {

	/*
	 * �������� ���ĳ�����������ʼ����Ŀ���ͳ����CHI �������� int ntxt; ��ʾ����ѵ�����ϵ��ı������������ʼ��������������ʼ�������֮��
	 * int num_t_c ; ��ʾ�������ʼ����а����� t���ĵ�Ƶ�� , int num_t_nc; ��ʾ�������ʼ����а�����t ���ĵ�Ƶ�� ,
	 * int num_nt_c; ��ʾ�������ʼ����в����� t ���ĵ�Ƶ��, int num_nt_nc; ��ʾ�������ʼ����в����� t ���ĵ�Ƶ��
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
	 * �������� ���ĳ�����������ʼ����Ŀ���ͳ����CHI �������� int ntxt; ��ʾ����ѵ�����ϵ��ı������������ʼ��������������ʼ�������֮��
	 * int num_t_c ; ��ʾ�������ʼ����а����� t���ĵ�Ƶ�� , int num_t_nc; ��ʾ�������ʼ����а�����t ���ĵ�Ƶ�� ,
	 * int num_nt_c; ��ʾ�������ʼ����в����� t ���ĵ�Ƶ��, int num_nt_nc; ��ʾ�������ʼ����в����� t ���ĵ�Ƶ��
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
	 * �������� ���ĳ�����������ʼ��������ʼ����ĸ��� 
	 * �������� 
	 * @param int ntxtc ��ʾ���и�����ʼ����ϵ��ı���
	 * int nct ��ʾ�ڸ����ʼ����а����� t���ĵ�Ƶ�� ,��num_t_c ��num_t_nc 
	 * int m ��ʾchi��ά��������ʵ�����
	 */
	public static double getWordPC(int ntxtc, int nct, int m) {
		double wordpham = 0.00;

		wordpham = (double) (nct + 1) / (ntxtc + m);
		// System.out.println("nhamt:"+nhamt);
		// System.out.println("ntxtham:"+ntxtham);
		// System.out.println("m:"+m);
		// System.out.println("����wordprobabilityham!"+wordpham);
		return wordpham;
	}

	/*
	 * �������� ���������ʼ����ϻ��������ʼ�����ռ����ѵ�����ϵı��� �������� int ntxt;
	 * ��ʾ����ѵ�����ϵ��ı������������ʼ��������������ʼ�������֮�� int ntxtc; ��ʾ���������ʼ����ϵ��ı�����
	 */
	public static double getRatioC(int ntxt, int ntxtc) {
		// TODO Auto-generated constructor stub
		double ratioc = 1.00;
		ratioc = (double) ntxtc / ntxt;
		return ratioc;
	}

	/*
	 * �������� ������������ʼ��ĸ��ʺ����������ʼ��ĸ��ʵı�ֵ �������� double txtpham; ��ʾ�ı����������ʼ��ĸ��� double
	 * txtpspam; ��ʾ�ı����������ʼ��ĸ���
	 */
	public static double getRatioQuotient(double txtpham, double txtpspam) {
		// TODO Auto-generated constructor stub
		double quotient = 1.00;
		quotient = (double) txtpspam / txtpham;
		return quotient;
	}

	/*
	 * �������� ������������ʼ��ĸ��ʺ����������ʼ��ĸ��ʵĲ�ֵ �������� double txtpham; ��ʾ�ı����������ʼ��ĸ��� double
	 * txtpspam; ��ʾ�ı����������ʼ��ĸ���
	 */
	public static double getRatioDifference(double txtpham, double txtpspam) {
		// TODO Auto-generated constructor stub
		double difference = 0;
		difference = (double) txtpspam - txtpham;
		return difference;
	}

}
