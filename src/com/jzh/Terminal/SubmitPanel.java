package com.jzh.Terminal;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.UIManager;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import java.util.List;
import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import javax.swing.ScrollPaneConstants;
import java.awt.CardLayout;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.border.LineBorder;

import com.zac.util.JTableFit;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SubmitPanel extends JPanel {
	private JTabbedPane tabbedPane;
	private static JTextField textField_TrafficImportance;
	private static JTextField textField_BandWidth;
	private static JTextField textField_Jitter;
	private static JTextField textField_Delay;
	private static JTextField textField_PacketLoss;

	private static JTable ResultTable;
	private static JTable NetTable;
	private static JScrollPane scrollPaneResult;
	private static JScrollPane scrollPane;
	private static JComboBox comboBox_TrafficType;
	private static JComboBox comboBox_Preference;
	private static JComboBox comboBox;
	private static String Select;
	private static String Type;
	private static String VisivleNetwork;
	static int  Preference;
	static int  TrafficType;
	private JComboBox comboBox_1;

	/**
	 * Create the panel.
	 */
	public SubmitPanel(int selectedTab) {
		setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		add(tabbedPane, BorderLayout.CENTER);
		
		JPanel TrafficPanel = new JPanel();
		tabbedPane.addTab("\u7EC8\u7AEF\u4E1A\u52A1\u8BF7\u6C42", null, TrafficPanel, null);
		TrafficPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		TrafficPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_16 = new JPanel();
		TrafficPanel.add(panel_16);
		panel_16.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_16.add(panel_4);
		panel_4.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "\u4E1A\u52A1\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{128, 176, 0};
		gbl_panel_4.rowHeights = new int[]{209, 0};
		gbl_panel_4.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EmptyBorder(0, 0, 8, 0));
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.insets = new Insets(0, 0, 0, 5);
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 0;
		panel_4.add(panel_5, gbc_panel_5);
		panel_5.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new EmptyBorder(2, 0, 2, 20));
		panel_5.add(panel_8);
		panel_8.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel label_1 = new JLabel("\u4E1A\u52A1\u7C7B\u578B:");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_8.add(label_1);
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new EmptyBorder(2, 0, 2, 20));
		panel_5.add(panel_7);
		panel_7.setLayout(new CardLayout(0, 0));
		
		JLabel label_8 = new JLabel("\u4E1A\u52A1\u53C2\u6570:");
		label_8.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_7.add(label_8, "name_10507174645614");
		label_8.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new EmptyBorder(2, 0, 2, 20));
		panel_5.add(panel_9);
		panel_9.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel label_3 = new JLabel("\u504F\u597D\u7F51\u7EDC:");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_9.add(label_3);
		label_3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new EmptyBorder(2, 0, 2, 20));
		panel_5.add(panel_10);
		panel_10.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel label_2 = new JLabel("\u4E1A\u52A1\u91CD\u8981\u6027:");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_10.add(label_2);
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new EmptyBorder(0, 0, 8, 0));
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 1;
		gbc_panel_6.gridy = 0;
		panel_4.add(panel_6, gbc_panel_6);
		panel_6.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new EmptyBorder(2, 0, 2, 40));
		panel_6.add(panel_12);
		panel_12.setLayout(new GridLayout(1, 1, 0, 0));
		
		comboBox_TrafficType = new JComboBox();
		panel_12.add(comboBox_TrafficType);
		comboBox_TrafficType.setModel(new DefaultComboBoxModel(new String[] {"\u8BF7\u9009\u62E9", "\u4F1A\u8BDD\u7C7B", "\u6D41\u7C7B", "\u4EA4\u4E92\u7C7B", "\u80CC\u666F\u7C7B"}));
		comboBox_TrafficType.setToolTipText("");
		comboBox_TrafficType.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		comboBox_TrafficType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				do_setTrafficTypeLabel(e);
			}
		});
		
		JPanel panel_13 = new JPanel();
		panel_13.setBorder(new EmptyBorder(2, 0, 2, 40));
		panel_6.add(panel_13);
		panel_13.setLayout(new GridLayout(0, 1, 0, 0));
		
		comboBox = new JComboBox();
		panel_13.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u8BF7\u9009\u62E9\u4E1A\u52A1\u7C7B\u578B"}));
		comboBox.setToolTipText("");
		comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				do_setQoSLabel(e);
			}
		});
		
		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new EmptyBorder(2, 0, 2, 40));
		panel_6.add(panel_14);
		panel_14.setLayout(new GridLayout(0, 1, 0, 0));
		
		comboBox_Preference = new JComboBox();
		panel_14.add(comboBox_Preference);
		comboBox_Preference.setModel(new DefaultComboBoxModel(new String[] {"\u8BF7\u9009\u62E9", "WiMAX", "WLAN", "UMTS"}));
		comboBox_Preference.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		comboBox_Preference.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				do_setPreferenceLabel(e);
			}
		});
		
		JPanel panel_15 = new JPanel();
		panel_15.setBorder(new EmptyBorder(2, 0, 2, 40));
		panel_6.add(panel_15);
		panel_15.setLayout(new GridLayout(0, 1, 0, 0));
		
		textField_TrafficImportance = new JTextField();
		panel_15.add(textField_TrafficImportance);
		textField_TrafficImportance.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_TrafficImportance.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_16.add(panel_1);
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "\u4E1A\u52A1QoS\u53C2\u6570", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{125, 136, 104, 0};
		gbl_panel_1.rowHeights = new int[]{209, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JPanel panel_17 = new JPanel();
		panel_17.setBorder(new EmptyBorder(0, 0, 8, 0));
		GridBagConstraints gbc_panel_17 = new GridBagConstraints();
		gbc_panel_17.fill = GridBagConstraints.BOTH;
		gbc_panel_17.insets = new Insets(0, 0, 0, 5);
		gbc_panel_17.gridx = 0;
		gbc_panel_17.gridy = 0;
		panel_1.add(panel_17, gbc_panel_17);
		panel_17.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_20 = new JPanel();
		panel_20.setBorder(new EmptyBorder(2, 0, 2, 20));
		panel_17.add(panel_20);
		panel_20.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel label_5 = new JLabel("\u5E26   \u5BBD:");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_20.add(label_5);
		label_5.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_21 = new JPanel();
		panel_21.setBorder(new EmptyBorder(2, 0, 2, 20));
		panel_17.add(panel_21);
		panel_21.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel label_9 = new JLabel("\u65F6   \u5EF6:");
		label_9.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_21.add(label_9);
		label_9.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_22 = new JPanel();
		panel_22.setBorder(new EmptyBorder(2, 0, 2, 20));
		panel_17.add(panel_22);
		panel_22.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel label_10 = new JLabel("\u4E22\u5305\u7387:");
		label_10.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_22.add(label_10);
		label_10.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_23 = new JPanel();
		panel_23.setBorder(new EmptyBorder(2, 0, 2, 20));
		panel_17.add(panel_23);
		panel_23.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel label_6 = new JLabel("\u6296   \u52A8:");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_23.add(label_6);
		label_6.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_18 = new JPanel();
		panel_18.setBorder(new EmptyBorder(0, 0, 8, 0));
		GridBagConstraints gbc_panel_18 = new GridBagConstraints();
		gbc_panel_18.fill = GridBagConstraints.BOTH;
		gbc_panel_18.insets = new Insets(0, 0, 0, 5);
		gbc_panel_18.gridx = 1;
		gbc_panel_18.gridy = 0;
		panel_1.add(panel_18, gbc_panel_18);
		panel_18.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_25 = new JPanel();
		panel_25.setBorder(new EmptyBorder(2, 0, 2, 0));
		panel_18.add(panel_25);
		panel_25.setLayout(new GridLayout(1, 0, 0, 0));
		
		textField_BandWidth = new JTextField();
		panel_25.add(textField_BandWidth);
		textField_BandWidth.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_BandWidth.setColumns(10);
		
		JPanel panel_26 = new JPanel();
		panel_26.setBorder(new EmptyBorder(2, 0, 2, 0));
		panel_18.add(panel_26);
		panel_26.setLayout(new GridLayout(1, 0, 0, 0));
		
		textField_Delay = new JTextField();
		panel_26.add(textField_Delay);
		textField_Delay.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_Delay.setColumns(10);
		
		JPanel panel_27 = new JPanel();
		panel_27.setBorder(new EmptyBorder(2, 0, 2, 0));
		panel_18.add(panel_27);
		panel_27.setLayout(new GridLayout(1, 0, 0, 0));
		
		textField_PacketLoss = new JTextField();
		panel_27.add(textField_PacketLoss);
		textField_PacketLoss.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_PacketLoss.setColumns(10);
		
		JPanel panel_28 = new JPanel();
		panel_28.setBorder(new EmptyBorder(2, 0, 2, 0));
		panel_18.add(panel_28);
		panel_28.setLayout(new GridLayout(1, 0, 0, 0));
		
		textField_Jitter = new JTextField();
		panel_28.add(textField_Jitter);
		textField_Jitter.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_Jitter.setColumns(10);
		
		JPanel panel_19 = new JPanel();
		panel_19.setBorder(new EmptyBorder(0, 0, 8, 0));
		GridBagConstraints gbc_panel_19 = new GridBagConstraints();
		gbc_panel_19.fill = GridBagConstraints.BOTH;
		gbc_panel_19.gridx = 2;
		gbc_panel_19.gridy = 0;
		panel_1.add(panel_19, gbc_panel_19);
		panel_19.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_30 = new JPanel();
		panel_30.setBorder(new EmptyBorder(2, 0, 2, 0));
		panel_19.add(panel_30);
		panel_30.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel label_7 = new JLabel("kbps");
		panel_30.add(label_7);
		label_7.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_31 = new JPanel();
		panel_31.setBorder(new EmptyBorder(2, 0, 2, 0));
		panel_19.add(panel_31);
		panel_31.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblMs_1 = new JLabel("ms");
		panel_31.add(lblMs_1);
		lblMs_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_32 = new JPanel();
		panel_32.setBorder(new EmptyBorder(2, 0, 2, 0));
		panel_19.add(panel_32);
		panel_32.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel label_12 = new JLabel("%");
		panel_32.add(label_12);
		label_12.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_33 = new JPanel();
		panel_33.setBorder(new EmptyBorder(2, 0, 2, 0));
		panel_19.add(panel_33);
		panel_33.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblMs = new JLabel("ms");
		panel_33.add(lblMs);
		lblMs.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_11 = new JPanel();
		TrafficPanel.add(panel_11);
		GridBagLayout gbl_panel_11 = new GridBagLayout();
		gbl_panel_11.columnWidths = new int[]{482, 127, 0};
		gbl_panel_11.rowHeights = new int[]{203, 0};
		gbl_panel_11.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_11.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_11.setLayout(gbl_panel_11);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		panel_11.add(panel, gbc_panel);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "\u7F51\u7EDC\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		scrollPane = new JScrollPane();
		panel_2.add(scrollPane);
		
		JPanel panel_24 = new JPanel();
		panel.add(panel_24, BorderLayout.EAST);
		panel_24.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_29 = new JPanel();
		panel_24.add(panel_29);
		panel_29.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel label = new JLabel("     \u7F51\u7EDC\u9009\u62E9:     ");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel_29.add(label);
		
		JPanel panel_34 = new JPanel();
		panel_24.add(panel_34);
		panel_34.setLayout(new GridLayout(0, 1, 0, 0));
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"\u8BF7\u9009\u62E9", "\u81EA\u52A8"}));
		comboBox_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		comboBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				VisivleNetwork = NetworkSelectTest.GetVisibleNet();
				System.out.println(VisivleNetwork);
				createNetTab();
			}
		});
		panel_34.add(comboBox_1);
		
		JPanel panel_35 = new JPanel();
		panel_24.add(panel_35);
		
		JPanel panel_38 = new JPanel();
		panel_24.add(panel_38);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 0;
		panel_11.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new GridLayout(2, 2, 0, 0));
		
		JPanel panel_36 = new JPanel();
		panel_36.setBorder(null);
		panel_3.add(panel_36);
		panel_36.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton saveButton = new JButton("\u63D0\u4EA4");
		panel_36.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				
				do_submit_action(e);
				
			}
		});
		saveButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_37 = new JPanel();
		panel_37.setBorder(null);
		panel_3.add(panel_37);
		panel_37.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton emptyButton = new JButton("\u6E05\u7A7A");
		panel_37.add(emptyButton);
		emptyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//textField_LRRMIP.setText("");
				comboBox_TrafficType.setSelectedIndex(0);
				textField_TrafficImportance.setText("");
				comboBox_Preference.setSelectedIndex(0);
				comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u8BF7\u9009\u62E9\u4E1A\u52A1\u7C7B\u578B"}));
				//NetworkSelectTest.GetVisibleNet();
				textField_BandWidth.setText("");
				textField_Delay.setText("");
				textField_Jitter.setText("");
				textField_PacketLoss.setText("");
			}
		});
		emptyButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_answer = new JPanel();
		tabbedPane.addTab("\u4E1A\u52A1\u8BF7\u6C42\u7ED3\u679C\u67E5\u8BE2", null, panel_answer, null);
		panel_answer.setLayout(new BorderLayout(0, 0));
		
		scrollPaneResult = new JScrollPane();
		panel_answer.add(scrollPaneResult);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				do_setTabChanged(e);
			}
		});
		
		if (1 == selectedTab) {		
			createResultTab();
		} 
		
		//设置Tab默认选择项
		tabbedPane.setSelectedIndex(selectedTab);
		

	}
	
	//处理标签页改变事件
	private void do_setTabChanged(ChangeEvent e) {
		if (1 == tabbedPane.getSelectedIndex()) {
			createResultTab();
		} else 
			return;
	}
	
	
	
	//创建评估结果总览表格
	private static void createResultTab() {
		
		DefaultTableModel model;
		model =new DefaultTableModel();
	
		ResultTable = new JTable(model) {
			public boolean isCellEditable(int row, int column){
	                   return false;
	                   }//表格不允许被编辑
		};
		ResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ResultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ResultTable.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		ResultTable.setRowHeight(30);// 设置表体高度
	    JTableHeader header = ResultTable.getTableHeader();// 获得表头对象
	    header.setFont(new Font("微软雅黑", Font.PLAIN, 15));// 设置表头字体
	    header.setPreferredSize(new Dimension(100000, 30));// 设置表头高度        
	    
	    
	   // DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();// 获得渲染器
	   // renderer.setHorizontalAlignment(SwingConstants.LEFT);// 设置表头内容居中显示
	    
	    
	    //DefaultTableModel model = (DefaultTableModel) TrafficTable.getModel();// 获得表格模型
	    model.setRowCount(0);// 清空表格中的数据
	    model.setColumnIdentifiers(new Object[] { "编号    ", "业务类型      ", "业务重要性    ", "用户偏好    ", "可用网络         ", "带宽(kbps)    ", "延迟(ms)    ","抖动(ms)    ","丢包率(%)    ","接入网络类型    ","接入网络编号","接入网络地址"});
	    //model.setColumnIdentifiers(new Object[] { "编号", "业务类型", "用户类型", "任务类型", "二维评估结果", "三维评估结果"});
	    
	    /**
	     * 测试：在修改业务信息之后MADM评估类中获取的业务信息是否也跟着改变
	     * 
	     * 结果：MADM评估类中获取的业务信息未改变，猜测是static类型的原因
	     */
	    /*
	    //List<TrafficBean> trafResults = madmBasedImportance.trafResults;
	    //List<UserBean> userResults =madmBasedImportance.userResults;
	    List<MissionBean> missionResults = madmBasedImportance.missionResults;
	    for(MissionBean mission : missionResults) {
	    	System.out.println(mission.getMissionTitle() + "|" + mission.getMissionBandWidth() + "|" + mission.getMissionReliability() + "|" + mission.getMissionResponseTime());   	
	    }
	    System.out.println("**************************");*/
	    /************************************************************************************/
	    
	    
	    //EvaResultOp.updateTableBased();//更新基于表的评估结果数据库
	    List<TerRequestBean> results = TerminalSqlRequest.queryAll();// 获得数据库中表格的全部数据	    
	    for (TerRequestBean tempRes : results) {// 将数据加载到表格模型中
	    	
			String SQLPreference = null ;
			if(tempRes.getPreference()==1) {
				SQLPreference="WiMAX";
			}
			if(tempRes.getPreference()==3) {
				SQLPreference="WLAN";
			}
			if(tempRes.getPreference()==2) {
				SQLPreference="UMTS";
			}

			
			String SQLTrafficType = null ;
			if(tempRes.getTrafficType()==1) {
				SQLTrafficType="会话类";
			}
			if(tempRes.getTrafficType()==2) {
				SQLTrafficType="流类";
			}
			if(tempRes.getTrafficType()==3) {
				SQLTrafficType="交互类";
			}
			if(tempRes.getTrafficType()==4) {
				SQLTrafficType="背景类";
			}
			
			
			String SQLNetType = null ;
			if(tempRes.getNetType()==0) {
				SQLNetType="-";
			}
			if(tempRes.getNetType()==1) {
				SQLNetType="WiMAX";
			}
			if(tempRes.getNetType()==3) {
				SQLNetType="WLAN";
			}
			if(tempRes.getNetType()==2) {
				SQLNetType="UMTS";
			}

			String SQLNetID = "-" ;
			if(tempRes.getNetId()==0) {
				SQLNetType="-";
			} else {
				SQLNetID=String.valueOf(tempRes.getNetId());
			}
			
			String SQLLRRMIP = null ;
			if(tempRes.getLRRMIP()==null) {
				SQLLRRMIP="-";
			} else {
				SQLLRRMIP=tempRes.getLRRMIP();
			}
	        model.addRow(new Object[] { tempRes.getRequestId(), SQLTrafficType, tempRes.getTrafficImportance(), SQLPreference, 
	        		tempRes.getVisibleNet(), tempRes.getBandWidth(), tempRes.getDelay(), tempRes.getJitter(), tempRes.getPacketLoss(), SQLNetType , SQLNetID, SQLLRRMIP});
	  
	    }       
	    
 
	  //表头列宽随内容变化
	    JTableFit.FitTableColumns(ResultTable);
	    ResultTable.setModel(model);// 应用表格模型		
		
		scrollPaneResult.setViewportView(ResultTable);	

	}
	
	@SuppressWarnings("null")
	private static void createNetTab() {
		DefaultTableModel model;
		model =new DefaultTableModel();
	
		NetTable = new JTable(model) {
			public boolean isCellEditable(int row, int column){
	                   return false;
	                   }//表格不允许被编辑
		};
		//NetTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		NetTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		NetTable.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		NetTable.setRowHeight(30);// 设置表体高度
	    JTableHeader header = NetTable.getTableHeader();// 获得表头对象
	    header.setFont(new Font("微软雅黑", Font.PLAIN, 15));// 设置表头字体
	    header.setPreferredSize(new Dimension(100000, 30));// 设置表头高度        
	    model.setRowCount(0);// 清空表格中的数据
	    model.setColumnIdentifiers(new Object[] { "网络类型    ","网络编号   "});
	    String values[] = VisivleNetwork.split(";");
	    String NetID[] = new String[values.length];;
	    String NetType[] = new String[values.length];
	    for(int i = 0; i < values.length; i++) {
	    	String temp[] = values[i].split("-");
	    	NetType[i] = temp[0];
	    	NetID[i] =temp[1];
	    	String Type = null ;
			if(NetType[i].equals("1")) {
				Type="WiMAX";
			}
			if(NetType[i].equals("3")) {
				Type="WLAN";
			}
			if(NetType[i].equals("2")) {
				Type="UMTS";
			}
	    	model.addRow(new Object[] {Type,NetID[i] });	 
	    }
		  //表头列宽随内容变化
	    //JTableFit.FitTableColumns(NetTable);
	    NetTable.setModel(model);// 应用表格模型		
		
	    scrollPane.setViewportView(NetTable);	

	}
	
	
	static void do_submit_action(ActionEvent e) {
		//String IP = textField_LRRMIP.getText().trim();
		//if (IP.isEmpty()) {
		//    JOptionPane.showMessageDialog(null, "IP地址不能为空！", "", JOptionPane.WARNING_MESSAGE);
		//    return;
		//}
		
		//String str[] = strLineNet.split(":");
		/*String[] IP1 = IP.split("\\.");
		if (IP1.length == 4) {
			//System.out.println(IP1.length);
			for (int i = 0; i < IP1.length; i++) {
				try {
					if (Integer.valueOf(IP1[i]) > 255 || Integer.valueOf(IP1[i]) < 0 ) {
						JOptionPane.showMessageDialog(null, "请输入正确的IP地址格式", "", JOptionPane.WARNING_MESSAGE);
						return;
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "请输入正确的IP地址格式", "", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "请输入正确的IP地址格式", "", JOptionPane.WARNING_MESSAGE);
			return;
		}
		*/
		
		String TrafficImportance = textField_TrafficImportance.getText().trim();
		if (TrafficImportance.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "业务重要性不能为空！", "", JOptionPane.WARNING_MESSAGE);
		    return;
		}
		
		
		String BandWidth = textField_BandWidth.getText().trim();
		if (BandWidth.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "业务带宽要求不能为空！", "", JOptionPane.WARNING_MESSAGE);
		    return;
		}
		
		String Delay = textField_Delay.getText().trim();
		if (Delay.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "业务时延要求不能为空！", "", JOptionPane.WARNING_MESSAGE);
		    return;
		}
		String Jitter = textField_Jitter.getText().trim();
		if (Jitter.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "业务抖动要求不能为空！", "", JOptionPane.WARNING_MESSAGE);
		    return;
		}
		String PacketLoss = textField_PacketLoss.getText().trim();
		if (PacketLoss.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "业务丢包率要求不能为空！", "", JOptionPane.WARNING_MESSAGE);
		    return;
		}
		if ((Double.valueOf(PacketLoss) > 100 || Double.valueOf(PacketLoss) < 0 )) {
		    JOptionPane.showMessageDialog(null, "业务丢包率必须在0到100之间！", "", JOptionPane.WARNING_MESSAGE);
		    return;
		}



		
		TerRequestBean r = new TerRequestBean();
		
		//r.setLRRMIP(textField_LRRMIP.getText());
		r.setTrafficType(TrafficType);
		r.setTrafficImportance(Integer.valueOf(textField_TrafficImportance.getText()));
		r.setPreference(Preference);
		r.setVisibleNet(VisivleNetwork);
		r.setBandWidth(Double.valueOf(textField_BandWidth.getText()));
		r.setDelay(Double.valueOf(textField_Delay.getText()));
		r.setJitter(Double.valueOf(textField_Jitter.getText()));
		r.setPacketLoss(Double.valueOf(textField_PacketLoss.getText()));
		
		
		String Answer = Terminal.submit(r);
		
		
		if (Answer.equals("1")){
			JOptionPane.showMessageDialog(null, "任务提交成功！", "", JOptionPane.WARNING_MESSAGE);
		} else if(Answer.equals("2")) {
			JOptionPane.showMessageDialog(null, "任务提交失败！", "", JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "任务提交失败！", "", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	//处理网络偏好下拉列表框选择项改变处理事件
	private void do_setPreferenceLabel(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			String Pre =((String)comboBox_Preference.getSelectedItem()).trim();
			if(Pre.equals("WiMAX")) {
				Preference=1;
			}else if(Pre.trim().equals("WLAN")) {
				Preference=3;
			}else if(Pre.equals("UMTS")) {
				Preference=2;
			}
		}
	}
	
	
	//处理业务类型下拉列表框选择项改变处理事件
	private void do_setTrafficTypeLabel(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			Type = ((String)comboBox_TrafficType.getSelectedItem()).trim();
			if(Type.equals("会话类")) {
				TrafficType=1;
				comboBox.setModel(new DefaultComboBoxModel(new String[] {"自定义", "视频", "语音"}));
			}else if(Type.equals("流类")) {
				TrafficType=2;
			}else if(Type.equals("交互类")) {
				TrafficType=3;
			}else if(Type.equals("背景类")) {
				TrafficType=4;
			}
		}
	}
	
	//处理业务参数下拉列表框选择项改变处理事件
	private void do_setQoSLabel(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			Select = ((String)comboBox.getSelectedItem()).trim();
			if((Select.equals("视频"))&&(Type.equals("会话类"))) {
				textField_BandWidth.setText("111");
				textField_Delay.setText("222");
				textField_Jitter.setText("333");
				textField_PacketLoss.setText("444");
			}else if(Select.equals("语音")) {
				textField_BandWidth.setText("222");
			}else if(Select.equals("交互类")) {
				textField_BandWidth.setText("111");
			}else if(Select.equals("背景类")) {
				textField_BandWidth.setText("111");
			}
		
		}
	}

	
	
	
}	
	
	


