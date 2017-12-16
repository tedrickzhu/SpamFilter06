package bistu.zzy.spamfilter.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import bistu.zzy.spamfilter.sgmt.Segment;
import bistu.zzy.spamfilter.thread.ClearRunnable;
import bistu.zzy.spamfilter.thread.FilteRunnable;
import bistu.zzy.spamfilter.thread.SetRunnable;
import bistu.zzy.spamfilter.thread.TestRunnable;
import bistu.zzy.spamfilter.thread.TrainThread;

public class SFFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	// 程序窗口的宽和高
	private static final int Fwidth = 700;
	private static final int Fheight = 500;

	private JPanel contentPane;

	// 上面的多选项布局面板，位于borderlayout布局的center
	private JTabbedPane tabbedPane;

	// 训练面板
	private JPanel panel_train;
	// 训练面板中的控制面板
	private JPanel p_t_head;
	private JLabel label_hampath;
	private JTextField textField_hampath;
	// 选择ham训练集按钮
	private JButton button_hampath;
	// 训练操作按钮
	private JButton button_train;
	private JLabel label_spampath;
	private JTextField textField_spampath;
	// 选择spam训练集按钮
	private JButton button_spampath;
	// 训练面板中的显示面板
	private JScrollPane p_t_scrollPane;
	// 训练结果提示框
	private JTextArea textArea_train;

	// 过滤面板
	private JPanel panel_filte;
	// 过滤面板中的控制面板
	private JPanel p_f_head;
	private JLabel label_testpath;
	private JTextField textField_testpath;
	// 选择测试集按钮
	private JButton button_testpath;
	// 过滤操作按钮
	private JButton button_filte;
	// 过滤面板中的显示面板
	private JScrollPane p_f_scrollPane;
	// 过滤面板结果提示框
	private JTextArea textArea_filte;

	// 底部面板，位于borderlayout的south
	private JPanel panel_end;
	// 退出按钮
	private JButton button_exit;

	private JFileChooser jfc = new JFileChooser();// 文件选择器

	// about面板，介绍此application
	private JPanel panel_about;
	private JPanel panel_version;
	private JLabel lblNewLabel_3;
	// 设置面板
	private JPanel panel_set;
	private JPanel panel_param;
	private JLabel lblDf;
	private JTextField textField_df;
	private JLabel lblChi;
	private JTextField textField_chi;
	private JButton button_param;
	private JTextPane textpanel_param;
	private JTextPane textPanel_about;
	private JLabel lblkeyword;
	private JButton button_clearkeyword;
	private JLabel lblpwords;
	private JButton button_clearpwords;
	private JLabel lbltxtdata;
	private JButton button_cleartxtdata;
	private JLabel lblfilterparame;
	private JButton button_clearfilterparam;
	private JLabel label;
	private JLabel label_clearall;
	private JButton button_clearall;
	private JLabel label_result;
	private JTextField textField_result;
	private JPanel panel_test;
	private JPanel panel_test_head;
	private JLabel label_thp;
	private JTextField textField_thp;
	private JButton button_thp;
	private JButton button_test;
	private JLabel label_tsp;
	private JTextField textField_tsp;
	private JButton button_tsp;
	private JScrollPane scrollpanel_test;
	private JTextArea textArea_test;

	/**
	 * Create the frame.
	 */
	public SFFrame() {

		setTitle("\u5783\u573E\u90AE\u4EF6\u8FC7\u6EE4\u5668");
		setResizable(false);
		setFont(new Font("Adobe 楷体 Std R", Font.PLAIN, 12));
		setBackground(SystemColor.inactiveCaption);
		this.setSize(Fwidth, Fheight);// 设定窗口大小
		// 下面两行是取得屏幕的高度和宽度
		int lx = Toolkit.getDefaultToolkit().getScreenSize().width;
		int ly = Toolkit.getDefaultToolkit().getScreenSize().height;
		int x = (lx - this.getWidth()) / 2;
		int y = (ly - this.getHeight()) / 2 - 30;
		this.setLocation(x, y);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(100, 100, 450, 300);

		jfc.setCurrentDirectory(new File("g:\\"));// 文件选择器的初始目录定为d盘

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		showSet();

		showTrain();

		showFilte();
		
		showTest();

		showAbout();

		showExit();

	}

	public void showSet() {
		panel_set = new JPanel();
		tabbedPane.addTab("设置", null, panel_set, null);
		panel_set.setLayout(new BorderLayout(0, 0));

		panel_param = new JPanel();
		panel_set.add(panel_param, BorderLayout.NORTH);
		GridBagLayout gbl_panel_param = new GridBagLayout();
		gbl_panel_param.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_param.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_param.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_param.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_param.setLayout(gbl_panel_param);
		panel_param.setBorder(new TitledBorder("Set Parameter"));

		lblDf = new JLabel("DF\u9608\u503C");
		GridBagConstraints gbc_lblDf = new GridBagConstraints();
		gbc_lblDf.anchor = GridBagConstraints.EAST;
		gbc_lblDf.insets = new Insets(0, 0, 5, 5);
		gbc_lblDf.gridx = 0;
		gbc_lblDf.gridy = 1;
		panel_param.add(lblDf, gbc_lblDf);

		textField_df = new JTextField();
		textField_df.setFont(new Font("SimSun", Font.PLAIN, 12));
		textField_df.setColumns(10);
		// textField_df.setSize(50, 10);
		GridBagConstraints gbc_textField_df = new GridBagConstraints();
		gbc_textField_df.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_df.insets = new Insets(0, 0, 5, 5);
		gbc_textField_df.gridx = 1;
		gbc_textField_df.gridy = 1;
		panel_param.add(textField_df, gbc_textField_df);

		label = new JLabel("          ");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 1;
		panel_param.add(label, gbc_label);

		lbltxtdata = new JLabel("\u6E05\u6D17\u8868\u683CTxtData");
		GridBagConstraints gbc_lbltxtdata = new GridBagConstraints();
		gbc_lbltxtdata.insets = new Insets(0, 0, 5, 5);
		gbc_lbltxtdata.gridx = 3;
		gbc_lbltxtdata.gridy = 1;
		panel_param.add(lbltxtdata, gbc_lbltxtdata);

		lblfilterparame = new JLabel("\u6E05\u6D17\u8868\u683CFilterParam");
		GridBagConstraints gbc_lblfilterparame = new GridBagConstraints();
		gbc_lblfilterparame.insets = new Insets(0, 0, 5, 5);
		gbc_lblfilterparame.gridx = 5;
		gbc_lblfilterparame.gridy = 1;
		panel_param.add(lblfilterparame, gbc_lblfilterparame);

		label_clearall = new JLabel("\u6E05\u6D17\u6240\u6709\u8868\u683C");
		GridBagConstraints gbc_label_clearall = new GridBagConstraints();
		gbc_label_clearall.insets = new Insets(0, 0, 5, 0);
		gbc_label_clearall.gridx = 7;
		gbc_label_clearall.gridy = 1;
		panel_param.add(label_clearall, gbc_label_clearall);

		lblChi = new JLabel("CHI\u9608\u503C");
		GridBagConstraints gbc_lblChi = new GridBagConstraints();
		gbc_lblChi.anchor = GridBagConstraints.EAST;
		gbc_lblChi.insets = new Insets(0, 0, 5, 5);
		gbc_lblChi.gridx = 0;
		gbc_lblChi.gridy = 2;
		panel_param.add(lblChi, gbc_lblChi);

		textField_chi = new JTextField();
		textField_chi.setFont(new Font("SimSun", Font.PLAIN, 12));
		textField_chi.setColumns(10);
		GridBagConstraints gbc_textField_chi = new GridBagConstraints();
		gbc_textField_chi.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_chi.insets = new Insets(0, 0, 5, 5);
		gbc_textField_chi.gridx = 1;
		gbc_textField_chi.gridy = 2;
		panel_param.add(textField_chi, gbc_textField_chi);

		button_cleartxtdata = new JButton("\u786E\u5B9A");
		button_cleartxtdata.addActionListener(this);
		GridBagConstraints gbc_button_cleartxtdata = new GridBagConstraints();
		gbc_button_cleartxtdata.insets = new Insets(0, 0, 5, 5);
		gbc_button_cleartxtdata.gridx = 3;
		gbc_button_cleartxtdata.gridy = 2;
		panel_param.add(button_cleartxtdata, gbc_button_cleartxtdata);

		button_clearfilterparam = new JButton("\u786E\u5B9A");
		button_clearfilterparam.addActionListener(this);
		GridBagConstraints gbc_button_clearfilterparam = new GridBagConstraints();
		gbc_button_clearfilterparam.insets = new Insets(0, 0, 5, 5);
		gbc_button_clearfilterparam.gridx = 5;
		gbc_button_clearfilterparam.gridy = 2;
		panel_param.add(button_clearfilterparam, gbc_button_clearfilterparam);

		button_param = new JButton("\u786E\u5B9A");
		button_param.addActionListener(this);

		button_clearall = new JButton("\u786E\u5B9A");
		button_clearall.addActionListener(this);
		GridBagConstraints gbc_button_clearall = new GridBagConstraints();
		gbc_button_clearall.insets = new Insets(0, 0, 5, 0);
		gbc_button_clearall.gridx = 7;
		gbc_button_clearall.gridy = 2;
		panel_param.add(button_clearall, gbc_button_clearall);
		GridBagConstraints gbc_button_param = new GridBagConstraints();
		gbc_button_param.insets = new Insets(0, 0, 5, 5);
		gbc_button_param.gridx = 1;
		gbc_button_param.gridy = 4;
		panel_param.add(button_param, gbc_button_param);

		lblkeyword = new JLabel("\u6E05\u6D17\u8868\u683Ckeyword");
		GridBagConstraints gbc_lblkeyword = new GridBagConstraints();
		gbc_lblkeyword.insets = new Insets(0, 0, 5, 5);
		gbc_lblkeyword.gridx = 3;
		gbc_lblkeyword.gridy = 4;
		panel_param.add(lblkeyword, gbc_lblkeyword);

		lblpwords = new JLabel("\u6E05\u6D17\u8868\u683Cpwords");
		GridBagConstraints gbc_lblpwords = new GridBagConstraints();
		gbc_lblpwords.insets = new Insets(0, 0, 5, 5);
		gbc_lblpwords.gridx = 5;
		gbc_lblpwords.gridy = 4;
		panel_param.add(lblpwords, gbc_lblpwords);

		button_clearkeyword = new JButton("\u786E\u5B9A");
		button_clearkeyword.addActionListener(this);

		label_result = new JLabel("\u7ED3\u679C\u63D0\u793A");

		GridBagConstraints gbc_label_result = new GridBagConstraints();
		gbc_label_result.anchor = GridBagConstraints.EAST;
		gbc_label_result.insets = new Insets(0, 0, 0, 5);
		gbc_label_result.gridx = 0;
		gbc_label_result.gridy = 5;
		panel_param.add(label_result, gbc_label_result);

		textField_result = new JTextField();
		textField_result.setEnabled(false);
		GridBagConstraints gbc_textField_result = new GridBagConstraints();
		gbc_textField_result.insets = new Insets(0, 0, 0, 5);
		gbc_textField_result.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_result.gridx = 1;
		gbc_textField_result.gridy = 5;
		panel_param.add(textField_result, gbc_textField_result);
		textField_result.setColumns(10);
		GridBagConstraints gbc_button_clearkeyword = new GridBagConstraints();
		gbc_button_clearkeyword.insets = new Insets(0, 0, 0, 5);
		gbc_button_clearkeyword.gridx = 3;
		gbc_button_clearkeyword.gridy = 5;
		panel_param.add(button_clearkeyword, gbc_button_clearkeyword);

		button_clearpwords = new JButton("\u786E\u5B9A");
		button_clearpwords.addActionListener(this);
		GridBagConstraints gbc_button_clearpwords = new GridBagConstraints();
		gbc_button_clearpwords.insets = new Insets(0, 0, 0, 5);
		gbc_button_clearpwords.gridx = 5;
		gbc_button_clearpwords.gridy = 5;
		panel_param.add(button_clearpwords, gbc_button_clearpwords);

		textpanel_param = new JTextPane();
		textpanel_param.setBorder(new TitledBorder("Parameter Introduction and Attention"));
		textpanel_param.setText(
				"\r\n\u9608\u503C\u53C2\u6570\u4ECB\u7ECD\uFF1A\r\n\r\n\t1.DF\u8868\u793A\u7279\u5F81\u8BCD\u51FA\u73B0\u7684\u6587\u6863\u9891\u6570\r\n\t2.CHI\u8868\u793A\u7279\u5F81\u8BCD\u7684\u5361\u65B9\u7EDF\u8BA1\u91CF\uFF0C\u7531\u5361\u65B9\u7EDF\u8BA1\u91CF\u8BA1\u7B97\u516C\u5F0F\u5F97\u5230\r\n\r\n\u9608\u503C\u53C2\u6570\u8BBE\u7F6E\u8BF4\u660E\uFF1A\r\n\r\n\t1.DF\uFF0CCHI\u4E00\u822C\u6839\u636E\u7ECF\u9A8C\u503C\u8BBE\u7F6E\r\n\t2.DF\u4E3Aint\u578B\u6570\u636E\uFF0C\u76EE\u524D\u6570\u636E\u5E93\u4E2D\u5B58\u50A8\u7684\u503C\u4E3A5\r\n\t3.CHI\u4E3Adouble\u578B\u6570\u636E\uFF0C\u76EE\u524D\u6570\u636E\u5E93\u4E2D\u5B58\u50A8\u7684\u503C\u4E3A30");
		textpanel_param.setBackground(new Color(211, 211, 211));
		panel_set.add(textpanel_param, BorderLayout.CENTER);

	}

	public void showTrain() {

		panel_train = new JPanel();
		tabbedPane.addTab("训练", null, panel_train, null);
		panel_train.setLayout(new BorderLayout(0, 0));

		p_t_head = new JPanel();
		panel_train.add(p_t_head, BorderLayout.NORTH);
		GridBagLayout gbl_p_t_head = new GridBagLayout();
		gbl_p_t_head.columnWidths = new int[] { 0, 0, 0, 0, 5 };
		gbl_p_t_head.rowHeights = new int[] { 0, 0, 0, 0, 5 };
		gbl_p_t_head.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_p_t_head.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		p_t_head.setLayout(gbl_p_t_head);
		p_t_head.setBorder(new TitledBorder("训练集路径选择"));

		label_hampath = new JLabel("ham\u76EE\u5F55");
		GridBagConstraints gbc_label_hampath = new GridBagConstraints();
		gbc_label_hampath.anchor = GridBagConstraints.EAST;
		gbc_label_hampath.insets = new Insets(0, 0, 5, 5);
		gbc_label_hampath.gridx = 0;
		gbc_label_hampath.gridy = 0;
		p_t_head.add(label_hampath, gbc_label_hampath);

		textField_hampath = new JTextField();
		GridBagConstraints gbc_textField_hampath = new GridBagConstraints();
		gbc_textField_hampath.insets = new Insets(0, 0, 5, 5);
		gbc_textField_hampath.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_hampath.gridx = 1;
		gbc_textField_hampath.gridy = 0;
		p_t_head.add(textField_hampath, gbc_textField_hampath);
		textField_hampath.setColumns(10);

		button_hampath = new JButton("\u6D4F\u89C8");
		button_hampath.addActionListener(this);
		GridBagConstraints gbc_button_hampath = new GridBagConstraints();
		gbc_button_hampath.insets = new Insets(0, 0, 5, 5);
		gbc_button_hampath.gridx = 2;
		gbc_button_hampath.gridy = 0;
		p_t_head.add(button_hampath, gbc_button_hampath);

		button_train = new JButton("\u8BAD\u7EC3");
		button_train.addActionListener(this);
		// .addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseClicked(MouseEvent e) {
		// // TODO 训练操作
		// String hampath = textField_hampath.getText().trim();
		// String spampath = textField_spampath.getText().trim();
		// // 启动一个新的线程
		// TrainThread trainthread = new TrainThread(hampath, spampath);
		// trainthread.start();
		// }
		// });
		GridBagConstraints gbc_button_train = new GridBagConstraints();
		gbc_button_train.gridheight = 2;
		gbc_button_train.insets = new Insets(0, 0, 5, 0);
		gbc_button_train.gridx = 3;
		gbc_button_train.gridy = 0;
		p_t_head.add(button_train, gbc_button_train);

		label_spampath = new JLabel("spam\u76EE\u5F55");
		GridBagConstraints gbc_label_spampath = new GridBagConstraints();
		gbc_label_spampath.anchor = GridBagConstraints.EAST;
		gbc_label_spampath.insets = new Insets(0, 0, 5, 5);
		gbc_label_spampath.gridx = 0;
		gbc_label_spampath.gridy = 1;
		p_t_head.add(label_spampath, gbc_label_spampath);

		textField_spampath = new JTextField();
		GridBagConstraints gbc_textField_spampath = new GridBagConstraints();
		gbc_textField_spampath.insets = new Insets(0, 0, 5, 5);
		gbc_textField_spampath.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_spampath.gridx = 1;
		gbc_textField_spampath.gridy = 1;
		p_t_head.add(textField_spampath, gbc_textField_spampath);
		textField_spampath.setColumns(10);

		button_spampath = new JButton("\u6D4F\u89C8");
		button_spampath.addActionListener(this);
		GridBagConstraints gbc_button_spampath = new GridBagConstraints();
		gbc_button_spampath.insets = new Insets(0, 0, 5, 5);
		gbc_button_spampath.gridx = 2;
		gbc_button_spampath.gridy = 1;
		p_t_head.add(button_spampath, gbc_button_spampath);

		p_t_scrollPane = new JScrollPane();
		panel_train.add(p_t_scrollPane, BorderLayout.CENTER);

		textArea_train = new JTextArea();
		textArea_train.setEditable(false);
		p_t_scrollPane.setViewportView(textArea_train);
		p_t_scrollPane.setBorder(new TitledBorder("结果提示"));
	}

	public void showFilte() {
		panel_filte = new JPanel();
		tabbedPane.addTab("过滤", null, panel_filte, null);
		panel_filte.setLayout(new BorderLayout(0, 0));

		p_f_head = new JPanel();
		panel_filte.add(p_f_head, BorderLayout.NORTH);
		GridBagLayout gbl_p_f_head = new GridBagLayout();
		gbl_p_f_head.columnWidths = new int[] { 0, 0, 0, 0, 2 };
		gbl_p_f_head.rowHeights = new int[] { 0, 0, 2 };
		gbl_p_f_head.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_p_f_head.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		p_f_head.setLayout(gbl_p_f_head);
		p_f_head.setBorder(new TitledBorder("过滤测试邮件路径选择"));

		label_testpath = new JLabel("\u6D4B\u8BD5\u76EE\u5F55");
		GridBagConstraints gbc_label_testpath = new GridBagConstraints();
		gbc_label_testpath.insets = new Insets(0, 0, 5, 5);
		gbc_label_testpath.gridx = 0;
		gbc_label_testpath.gridy = 0;
		p_f_head.add(label_testpath, gbc_label_testpath);

		textField_testpath = new JTextField();
		GridBagConstraints gbc_textField_testpath = new GridBagConstraints();
		gbc_textField_testpath.insets = new Insets(0, 0, 5, 5);
		gbc_textField_testpath.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_testpath.gridx = 1;
		gbc_textField_testpath.gridy = 0;
		p_f_head.add(textField_testpath, gbc_textField_testpath);
		textField_testpath.setColumns(10);

		button_testpath = new JButton("\u6D4F\u89C8");
		button_testpath.addActionListener(this);
		GridBagConstraints gbc_button_testpath = new GridBagConstraints();
		gbc_button_testpath.insets = new Insets(0, 0, 5, 5);
		gbc_button_testpath.gridx = 2;
		gbc_button_testpath.gridy = 0;
		p_f_head.add(button_testpath, gbc_button_testpath);

		button_filte = new JButton("\u8FC7\u6EE4");
		button_filte.addActionListener(this);
		// .addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseClicked(MouseEvent e) {
		// // TODO 执行过滤操作
		// String testpath = textField_testpath.getText().trim();
		// // 启动一个新的线程
		// Runnable filterunnable = new FilteRunnable(testpath);
		// Thread filtethread = new Thread(filterunnable);
		// filtethread.start();
		// }
		// });
		GridBagConstraints gbc_button_filte = new GridBagConstraints();
		gbc_button_filte.insets = new Insets(0, 0, 5, 0);
		gbc_button_filte.gridx = 3;
		gbc_button_filte.gridy = 0;
		p_f_head.add(button_filte, gbc_button_filte);

		p_f_scrollPane = new JScrollPane();
		panel_filte.add(p_f_scrollPane, BorderLayout.CENTER);

		textArea_filte = new JTextArea();
		// 用户无法编辑文本框，只能在线程中编辑
		textArea_filte.setEditable(false);
		// 激活自动换行功能
		textArea_filte.setLineWrap(true);
		// 激活断行不断字功能
		textArea_filte.setWrapStyleWord(true);
		p_f_scrollPane.setViewportView(textArea_filte);
		p_f_scrollPane.setBorder(new TitledBorder("结果提示"));

	}

	public void showTest() {
		// 性能测试面板
		panel_test = new JPanel();
		tabbedPane.addTab("\u6027\u80FD\u6D4B\u8BD5", null, panel_test, null);
		panel_test.setLayout(new BorderLayout(0, 0));

		panel_test_head = new JPanel();
		panel_test_head.setBorder(new TitledBorder("测试集路径选择"));
		panel_test.add(panel_test_head, BorderLayout.NORTH);
		GridBagLayout gbl_panel_test_head = new GridBagLayout();
		gbl_panel_test_head.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_test_head.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_test_head.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_test_head.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_test_head.setLayout(gbl_panel_test_head);

		label_thp = new JLabel("ham\u76EE\u5F55");
		GridBagConstraints gbc_label_thp = new GridBagConstraints();
		gbc_label_thp.anchor = GridBagConstraints.EAST;
		gbc_label_thp.insets = new Insets(0, 0, 5, 5);
		gbc_label_thp.gridx = 0;
		gbc_label_thp.gridy = 0;
		panel_test_head.add(label_thp, gbc_label_thp);

		textField_thp = new JTextField();
		textField_thp.setColumns(10);
		GridBagConstraints gbc_textField_thp = new GridBagConstraints();
		gbc_textField_thp.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_thp.insets = new Insets(0, 0, 5, 5);
		gbc_textField_thp.gridx = 1;
		gbc_textField_thp.gridy = 0;
		panel_test_head.add(textField_thp, gbc_textField_thp);

		button_thp = new JButton("\u6D4F\u89C8");
		button_thp.addActionListener(this);
		GridBagConstraints gbc_button_thp = new GridBagConstraints();
		gbc_button_thp.insets = new Insets(0, 0, 5, 5);
		gbc_button_thp.gridx = 2;
		gbc_button_thp.gridy = 0;
		panel_test_head.add(button_thp, gbc_button_thp);

		button_test = new JButton("\u5F00\u59CB\u6D4B\u8BD5");
		button_test.addActionListener(this);
		GridBagConstraints gbc_button_test = new GridBagConstraints();
		gbc_button_test.gridheight = 2;
		gbc_button_test.insets = new Insets(0, 0, 5, 0);
		gbc_button_test.gridx = 3;
		gbc_button_test.gridy = 0;
		panel_test_head.add(button_test, gbc_button_test);

		label_tsp = new JLabel("spam\u76EE\u5F55");
		GridBagConstraints gbc_label_tsp = new GridBagConstraints();
		gbc_label_tsp.anchor = GridBagConstraints.EAST;
		gbc_label_tsp.insets = new Insets(0, 0, 5, 5);
		gbc_label_tsp.gridx = 0;
		gbc_label_tsp.gridy = 1;
		panel_test_head.add(label_tsp, gbc_label_tsp);

		textField_tsp = new JTextField();
		textField_tsp.setColumns(10);
		GridBagConstraints gbc_textField_tsp = new GridBagConstraints();
		gbc_textField_tsp.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_tsp.insets = new Insets(0, 0, 5, 5);
		gbc_textField_tsp.gridx = 1;
		gbc_textField_tsp.gridy = 1;
		panel_test_head.add(textField_tsp, gbc_textField_tsp);

		button_tsp = new JButton("\u6D4F\u89C8");
		button_tsp.addActionListener(this);
		GridBagConstraints gbc_button_tsp = new GridBagConstraints();
		gbc_button_tsp.insets = new Insets(0, 0, 5, 5);
		gbc_button_tsp.gridx = 2;
		gbc_button_tsp.gridy = 1;
		panel_test_head.add(button_tsp, gbc_button_tsp);

		scrollpanel_test = new JScrollPane();
		panel_test.add(scrollpanel_test, BorderLayout.CENTER);

		textArea_test = new JTextArea();
		// 用户无法编辑文本框，只能在线程中编辑
		textArea_test.setEditable(false);
		// 激活自动换行功能
		textArea_test.setLineWrap(true);
		// 激活断行不断字功能
		textArea_test.setWrapStyleWord(true);

		scrollpanel_test.setViewportView(textArea_test);
		scrollpanel_test.setBorder(new TitledBorder("性能测试结果提示"));
	}

	public void showAbout() {
		// 关于面板
		panel_about = new JPanel();
		tabbedPane.addTab("\u5173\u4E8E", null, panel_about, null);
		panel_about.setLayout(new BorderLayout(0, 0));

		panel_version = new JPanel();
		panel_version.setBorder(new TitledBorder("Version Introduction"));
		panel_about.add(panel_version, BorderLayout.NORTH);

		lblNewLabel_3 = new JLabel(
				"This application is a standalone version,it need to start the MySQL database as support!");
		lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 14));
		panel_version.add(lblNewLabel_3);

		textPanel_about = new JTextPane();
		textPanel_about.setBorder(new TitledBorder("Instructions and Attentions"));
		textPanel_about.setText(
				"\r\nInstructions:\r\n\r\n\t1.Set the parameter 2.Train the SpamFilter 3.Test the filte function\r\n\r\nMatters Need Attention:\r\n\r\n\t1.You can set the parameter or not ,because there are values in the database.\r\n\t2.You can train the SpamFilter or not ,because I have trained it ,and there are values in the database.\r\n\t3.If you really want to test the application , you have to choose a test file or a test directory and press that button , and then magical things will happen.\r\n\t4.Ofcaurse , there are so many bugs about this application , so please show your mercy and don't make me died ! Thank you so much !\r\n\r\n\tAuthor : Zhu Zhengyi\r\n\tDate : 2016-4-20");
		textPanel_about.setBackground(new Color(211, 211, 211));
		textPanel_about.setEditable(false);
		panel_about.add(textPanel_about, BorderLayout.CENTER);

	}

	public void showExit() {
		panel_end = new JPanel();
		contentPane.add(panel_end, BorderLayout.SOUTH);

		button_exit = new JButton("\u9000\u51FA");
		button_exit.setHorizontalAlignment(SwingConstants.RIGHT);
		button_exit.addActionListener(this);
		panel_end.add(button_exit);
	}

	/**
	 * 函数功能 事件监听处理，处理不同按钮的事件
	 */
	@Override
	public void actionPerformed(ActionEvent e) {// 事件处理
		if (e.getSource().equals(button_hampath)) {// 判断触发方法的按钮是否为选择正常邮件训练集
			jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;// 撤销则返回
			} else {
				File f = jfc.getSelectedFile();// f为选择到的目录
				textField_hampath.setText(f.getAbsolutePath());
			}
		} else if (e.getSource().equals(button_spampath)) {// 判断触发方法的按钮是否为选择垃圾邮件训练集
			jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;// 撤销则返回
			} else {
				File f = jfc.getSelectedFile();// f为选择到的文件
				textField_spampath.setText(f.getAbsolutePath());
			}
		} else if (e.getSource().equals(button_testpath)) {// 选择测试集按钮
			jfc.setFileSelectionMode(2);// 设定能选择到文件或文件夹
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;// 撤销则返回
			} else {
				File f = jfc.getSelectedFile();// f为选择到的文件
				textField_testpath.setText(f.getAbsolutePath());
			}
		} else if (e.getSource().equals(button_thp)) {// 判断触发方法的按钮是否为选择合法邮件性能测试集
			jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;// 撤销则返回
			} else {
				File f = jfc.getSelectedFile();// f为选择到的文件
				textField_thp.setText(f.getAbsolutePath());
			}
		} else if (e.getSource().equals(button_tsp)) {// 判断触发方法的按钮是否为选择垃圾邮件性能测试集
			jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;// 撤销则返回
			} else {
				File f = jfc.getSelectedFile();// f为选择到的文件
				textField_tsp.setText(f.getAbsolutePath());
			}
		} else if (e.getSource().equals(button_exit)) {// 退出按钮
			// 执行退出操作
			System.exit(1);
			Segment.CLibrary.Instance.NLPIR_Exit();
		} else if (e.getSource().equals(button_cleartxtdata)) {// 清洗数据库按钮
			// TODO 清洗操作
			// 启动一个新的线程,用来设置参数
			String button = "cleartxtdata";
			Runnable clearrunnable = new ClearRunnable(button, this);
			Thread clearthread = new Thread(clearrunnable);
			clearthread.start();
		} else if (e.getSource().equals(button_clearfilterparam)) {// 清洗数据库按钮
			// TODO 清洗操作
			// 启动一个新的线程,用来设置参数
			String button = "clearfilterparam";
			Runnable clearrunnable = new ClearRunnable(button, this);
			Thread clearthread = new Thread(clearrunnable);
			clearthread.start();
		} else if (e.getSource().equals(button_clearkeyword)) {// 清洗数据库按钮
			// TODO 清洗操作
			// 启动一个新的线程,用来设置参数
			String button = "clearkeywords";
			Runnable clearrunnable = new ClearRunnable(button, this);
			Thread clearthread = new Thread(clearrunnable);
			clearthread.start();
		} else if (e.getSource().equals(button_clearpwords)) {// 清洗数据库按钮
			// TODO 清洗操作
			// 启动一个新的线程,用来设置参数
			String button = "clearpwords";
			Runnable clearrunnable = new ClearRunnable(button, this);
			Thread clearthread = new Thread(clearrunnable);
			clearthread.start();
		} else if (e.getSource().equals(button_clearall)) {// 清洗数据库按钮
			// TODO 清洗操作
			// 启动一个新的线程,用来设置参数
			String button = "clearall";
			Runnable clearrunnable = new ClearRunnable(button, this);
			Thread clearthread = new Thread(clearrunnable);
			clearthread.start();
		} else if (e.getSource().equals(button_param)) {// 设置按钮
			// TODO 设置操作
			String dfstr = this.textField_df.getText().trim();
			String chistr = this.textField_chi.getText().trim();
			if ((!dfstr.equals("")) && (!chistr.equals(""))) {
				int df = Integer.parseInt(dfstr);
				double chi = Double.parseDouble(chistr);
				// 启动一个新的线程,用来设置参数
				Runnable setrunnable = new SetRunnable(df, chi, this);
				Thread setthread = new Thread(setrunnable);
				setthread.start();
			} else {
				this.textField_df.setText("请输入正确的数据");
				this.textField_chi.setText("请输入正确的数据");
			}

		} else if (e.getSource().equals(button_filte)) {// 过滤按钮
			// TODO 执行操作
			// this.button_filte.setEnabled(false);
			this.getTextArea_filte().setText(null);
			String testpath = textField_testpath.getText().trim();
			// 启动一个新的线程，用来执行过滤操作
			Runnable filterunnable = new FilteRunnable(testpath, this);
			Thread filtethread = new Thread(filterunnable);
			filtethread.start();
//			filtethread.destroy();
			// this.button_filte.setEnabled(true);
		} else if (e.getSource().equals(button_train)) {// 训练按钮
			// TODO 训练操作
			this.getTextArea_train().setText(null);
			// this.button_train.setEnabled(false);
			String hampath = textField_hampath.getText().trim();
			String spampath = textField_spampath.getText().trim();
			// 启动一个新的线程，用来执行训练操作
			TrainThread trainthread = new TrainThread(hampath, spampath, this);
			trainthread.start();
		} else if (e.getSource().equals(button_test)) {// 性能测试按钮
			// TODO 性能测试操作
			this.getTextArea_test().setText(null);
			// this.button_train.setEnabled(false);
			String testhampath = textField_thp.getText().trim();
			String testspampath = textField_tsp.getText().trim();
			// 启动一个新的线程，用来执行性能测试操作
			Runnable testrunnable = new TestRunnable(testhampath, testspampath, this);
			Thread testthread = new Thread(testrunnable);
			testthread.start();
		}

	}

	public JButton getButton_test() {
		return button_test;
	}

	public void setButton_test(JButton button_test) {
		this.button_test = button_test;
	}

	public JTextField getTextField_result() {
		return textField_result;
	}

	public void setTextField_result(JTextField textField_result) {
		this.textField_result = textField_result;
	}

	public JButton getButton_train() {
		return button_train;
	}

	public void setButton_train(JButton button_train) {
		this.button_train = button_train;
	}

	public JButton getButton_filte() {
		return button_filte;
	}

	public void setButton_filte(JButton button_filte) {
		this.button_filte = button_filte;
	}

	public JButton getButton_param() {
		return button_param;
	}

	public void setButton_param(JButton button_param) {
		this.button_param = button_param;
	}

	public JTextField getTextField_hampath() {
		return textField_hampath;
	}

	public void setTextField_hampath(JTextField textField_hampath) {
		this.textField_hampath = textField_hampath;
	}

	public JTextField getTextField_spampath() {
		return textField_spampath;
	}

	public void setTextField_spampath(JTextField textField_spampath) {
		this.textField_spampath = textField_spampath;
	}

	public JTextArea getTextArea_train() {
		return textArea_train;
	}

	public void setTextArea_train(JTextArea textArea_train) {
		this.textArea_train = textArea_train;
	}

	public JTextField getTextField_testpath() {
		return textField_testpath;
	}

	public void setTextField_testpath(JTextField textField_testpath) {
		this.textField_testpath = textField_testpath;
	}

	public JTextArea getTextArea_filte() {
		return textArea_filte;
	}

	public void setTextArea_filte(JTextArea textArea_filte) {
		this.textArea_filte = textArea_filte;
	}

	public JTextField getTextField_df() {
		return textField_df;
	}

	public void setTextField_df(JTextField textField_df) {
		this.textField_df = textField_df;
	}

	public JTextField getTextField_chi() {
		return textField_chi;
	}

	public void setTextField_chi(JTextField textField_chi) {
		this.textField_chi = textField_chi;
	}

	public JTextArea getTextArea_test() {
		return textArea_test;
	}

	public void setTextArea_test(JTextArea textArea_test) {
		this.textArea_test = textArea_test;
	}

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SFFrame frame = new SFFrame();
//					frame.setVisible(true);
//					// 在启动界面时就初始化分词器，当退出时即可释放
//					Segment.initSegmentation();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

}
