package bistu.zzy.spamfilter.object;

public class KeyWord {

	/*
	 * 属性解释 int id ; 表示特征词序号 , String name; 表示特征词 , String pos; 表示特征词词性
	 */
	private int id;
	private String name;
	private String pos;

	/*
	 * 属性解释 int num_t_c ; 表示在正常邮件集中包含词 t的文档频数 , int num_t_nc; 表示在垃圾邮件集中包含词t的文档频数
	 * int num_nt_c; 表示在正常邮件集中不包含 t 的文档频数, int num_nt_nc; 表示在垃圾邮件集中不包含 t的文档频数
	 */
	private int num_t_c;
	private int num_t_nc;
	private int num_nt_c;
	private int num_nt_nc;

	/*
	 * 属性解释 double chihame ; 表示特征词属于正常邮件的卡方统计量 double chispame;表示特征词属于垃圾邮件的卡方统计量
	 */
	private double chiham;
	private double chispam;

	/*
	 * 属性解释 double wordpham ; 表示特征词属于正常邮件的概率 double wordpspam; 表示特征词属于垃圾邮件的概率
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
