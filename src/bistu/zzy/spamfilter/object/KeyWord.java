package bistu.zzy.spamfilter.object;

public class KeyWord {

	/*
	 * ���Խ��� int id ; ��ʾ��������� , String name; ��ʾ������ , String pos; ��ʾ�����ʴ���
	 */
	private int id;
	private String name;
	private String pos;

	/*
	 * ���Խ��� int num_t_c ; ��ʾ�������ʼ����а����� t���ĵ�Ƶ�� , int num_t_nc; ��ʾ�������ʼ����а�����t���ĵ�Ƶ��
	 * int num_nt_c; ��ʾ�������ʼ����в����� t ���ĵ�Ƶ��, int num_nt_nc; ��ʾ�������ʼ����в����� t���ĵ�Ƶ��
	 */
	private int num_t_c;
	private int num_t_nc;
	private int num_nt_c;
	private int num_nt_nc;

	/*
	 * ���Խ��� double chihame ; ��ʾ���������������ʼ��Ŀ���ͳ���� double chispame;��ʾ���������������ʼ��Ŀ���ͳ����
	 */
	private double chiham;
	private double chispam;

	/*
	 * ���Խ��� double wordpham ; ��ʾ���������������ʼ��ĸ��� double wordpspam; ��ʾ���������������ʼ��ĸ���
	 */
	private double wordpham;
	private double wordpspam;

	public int getId() {
		return id;
	}

	// public void setId(int id) {
	// this.id = id;
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPos() {
		return pos;
	}

	// public void setPos(String pos) {
	// this.pos = pos;
	// }

	public int getNum_t_c() {
		return num_t_c;
	}

	public void setNum_t_c(int num_t_c) {
		this.num_t_c = num_t_c;
	}

	public int getNum_t_nc() {
		return num_t_nc;
	}

	public void setNum_t_nc(int num_t_nc) {
		this.num_t_nc = num_t_nc;
	}

	public int getNum_nt_c() {
		return num_nt_c;
	}

	public void setNum_nt_c(int num_nt_c) {
		this.num_nt_c = num_nt_c;
	}

	public int getNum_nt_nc() {
		return num_nt_nc;
	}

	public void setNum_nt_nc(int num_nt_nc) {
		this.num_nt_nc = num_nt_nc;
	}

	public double getChiham() {
		return chiham;
	}

	public void setChiham(double chiham) {
		this.chiham = chiham;
	}

	public double getChispam() {
		return chispam;
	}

	public void setChispam(double chispam) {
		this.chispam = chispam;
	}

	public double getWordpham() {
		return wordpham;
	}

	public void setWordpham(double wordpham) {
		this.wordpham = wordpham;
	}

	public double getWordpspam() {
		return wordpspam;
	}

	public void setWordpspam(double wordpspam) {
		this.wordpspam = wordpspam;
	}

}
