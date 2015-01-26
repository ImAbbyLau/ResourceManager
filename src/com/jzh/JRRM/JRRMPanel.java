package com.jzh.JRRM;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

import com.zac.util.JTableFit;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JRadioButton;

public class JRRMPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	private static JTable JRRMResultTable;
	private static JTable LRRMResultTable;
	private static JTable WeightTab;
	private JTabbedPane tabbedPane;
	private static JScrollPane scrollPaneJRRM;
	private static JScrollPane scrollPaneLRRM;
	private static JScrollPane scrollPaneWeight;
	private JLabel label_12;
	private JLabel label_13;;
	private JLabel label_14;;
	private JLabel label_15;
	private JLabel label_16;
	private JLabel label_17;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JComboBox comboBox_3;
	private JComboBox comboBox_4;
	private JComboBox comboBox_5;
	private JComboBox comboBox_6;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	JRadioButton rdbtnNewRadioButton = new JRadioButton("\u4F1A\u8BDD\u7C7B");
	JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("\u6D41   \u7C7B");
	JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("\u4EA4\u4E92\u7C7B");
	JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("\u80CC\u666F\u7C7B");
	
	private final ButtonGroup buttonGroup1 = new ButtonGroup();
	JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("\u4F1A\u8BDD\u7C7B");
	JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("\u6D41\u7C7B");
	JRadioButton rdbtnNewRadioButton_6 = new JRadioButton("\u4EA4\u4E92\u7C7B");
	JRadioButton rdbtnNewRadioButton_7 = new JRadioButton("\u80CC\u666F\u7C7B");
	
	
	public JRRMPanel(int selectedTab) {
		setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
		tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_LRRM = new JPanel();
		tabbedPane.addTab("LRRM\u4FE1\u606F\u67E5\u8BE2", null, panel_LRRM, null);
		panel_LRRM.setLayout(new BorderLayout(0, 0));
		
		scrollPaneLRRM = new JScrollPane();
		panel_LRRM.add(scrollPaneLRRM);
		
		JPanel panel_1 = new JPanel();
		panel_LRRM.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new GridLayout(0, 4, 0, 0));
		
		ItemListener rbgroupListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getSource() == rdbtnNewRadioButton_4) {
					createLRRMResultTab(1);
				} else if(e.getSource() == rdbtnNewRadioButton_5) {
					createLRRMResultTab(2);
				} else if(e.getSource() == rdbtnNewRadioButton_6) {
					createLRRMResultTab(3);
				} else if(e.getSource() == rdbtnNewRadioButton_7) {
					createLRRMResultTab(4);
				}
			}
		};
		rdbtnNewRadioButton_4.addItemListener(rbgroupListener);
		rdbtnNewRadioButton_5.addItemListener(rbgroupListener);
		rdbtnNewRadioButton_6.addItemListener(rbgroupListener);
		rdbtnNewRadioButton_7.addItemListener(rbgroupListener);
		
		rdbtnNewRadioButton_4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel_1.add(rdbtnNewRadioButton_4);
		buttonGroup1.add(rdbtnNewRadioButton_4);
		rdbtnNewRadioButton_4.setSelected(true);
		
		rdbtnNewRadioButton_5.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel_1.add(rdbtnNewRadioButton_5);
		buttonGroup1.add(rdbtnNewRadioButton_5);
		
		rdbtnNewRadioButton_6.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel_1.add(rdbtnNewRadioButton_6);
		buttonGroup1.add(rdbtnNewRadioButton_6);
		
		rdbtnNewRadioButton_7.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel_1.add(rdbtnNewRadioButton_7);
		buttonGroup1.add(rdbtnNewRadioButton_7);
		
		
		
		JPanel panel_JRRM = new JPanel();
		tabbedPane.addTab("\u8BC4\u4F30\u8BF7\u6C42\u67E5\u8BE2", null, panel_JRRM, null);
		panel_JRRM.setLayout(new BorderLayout(0, 0));
		
		scrollPaneJRRM = new JScrollPane();
		panel_JRRM.add(scrollPaneJRRM);

		
		JPanel panel_weight = new JPanel();
		tabbedPane.addTab("\u6839\u636E\u4E1A\u52A1\u7C7B\u578B\u786E\u5B9A\u6743\u91CD", null, panel_weight, null);
		panel_weight.setLayout(new BorderLayout(0, 0));
		


		
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				do_setTabChanged(e);
			}
		});
	

	
	
	if (0 == selectedTab) {

		createLRRMResultTab(1);
	}  else if (1 == selectedTab) {		
		createJRRMResultTab();
	}  else if (2 == selectedTab) {		
		createWeightTab();
	} 
	
	
//	Boolean IsSelectedButton_4= rdbtnNewRadioButton_4.isSelected();
//	Boolean IsSelectedButton_5 = rdbtnNewRadioButton_5.isSelected();
//	Boolean IsSelectedButton_6 = rdbtnNewRadioButton_6.isSelected();
//	Boolean IsSelectedButton_7 = rdbtnNewRadioButton_7.isSelected();
//	
//	int type = 0;
//	if(IsSelectedButton_4 == true) {
//		type=1;
//		createLRRMResultTab(1);
//	}
//	if(IsSelectedButton_5 == true) {
//		type=2;
//		createLRRMResultTab(2);
//	}
//	if(IsSelectedButton_6 == true) {
//		type=3;
//		createLRRMResultTab(3);
//	}	
//	if(IsSelectedButton_7 == true) {
//		type=4;
//		createLRRMResultTab(4);
//	}
	
	//设置Tab默认选择项
	tabbedPane.setSelectedIndex(selectedTab);
	
	JPanel weightpanel = new JPanel();
	weightpanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "\u56DB\u79CD\u4E1A\u52A1\u7C7B\u578B\u7684QoS\u5C5E\u6027\u6743\u91CD\u8868", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel_weight.add(weightpanel, BorderLayout.CENTER);
	weightpanel.setLayout(new BorderLayout(0, 0));
	
	scrollPaneWeight = new JScrollPane();
	weightpanel.add(scrollPaneWeight);
	

	
	JPanel panel_input1 = new JPanel();
	panel_weight.add(panel_input1, BorderLayout.SOUTH);
	GridBagLayout gbl_panel_input1 = new GridBagLayout();
	gbl_panel_input1.columnWidths = new int[]{154, 592, 93, 0};
	gbl_panel_input1.rowHeights = new int[]{151, 0};
	gbl_panel_input1.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
	gbl_panel_input1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
	panel_input1.setLayout(gbl_panel_input1);
	
	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "\u4E1A\u52A1\u7C7B\u578B", TitledBorder.LEADING, TitledBorder.TOP, new Font("微软雅黑", Font.PLAIN, 15), null));
	GridBagConstraints gbc_panel = new GridBagConstraints();
	gbc_panel.fill = GridBagConstraints.BOTH;
	gbc_panel.insets = new Insets(0, 0, 0, 5);
	gbc_panel.gridx = 0;
	gbc_panel.gridy = 0;
	panel_input1.add(panel, gbc_panel);
	panel.setLayout(new GridLayout(4, 1, 0, 0));
	

	rdbtnNewRadioButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	//rdbtnNewRadioButton.setSelected(true);
	panel.add(rdbtnNewRadioButton);
	buttonGroup.add(rdbtnNewRadioButton);
	

	rdbtnNewRadioButton_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	panel.add(rdbtnNewRadioButton_1);
	buttonGroup.add(rdbtnNewRadioButton_1);
	
	
	rdbtnNewRadioButton_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	panel.add(rdbtnNewRadioButton_2);
	buttonGroup.add(rdbtnNewRadioButton_2);
	
	rdbtnNewRadioButton_3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	panel.add(rdbtnNewRadioButton_3);
	buttonGroup.add(rdbtnNewRadioButton_3);
	
	JPanel panel_input2 = new JPanel();
	panel_input2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "MADM\u5224\u65AD\u77E9\u9635(1-9\u6807\u5EA6:\u6807\u5EA6\u8D8A\u5927\u8868\u793A\u6B64\u884C\u5C5E\u6027\u8F83\u6B64\u5217\u5C5E\u4E8E\u8D8A\u91CD\u8981\u26051-\u540C\u7B49\u91CD\u8981)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	GridBagConstraints gbc_panel_input2 = new GridBagConstraints();
	gbc_panel_input2.anchor = GridBagConstraints.NORTH;
	gbc_panel_input2.fill = GridBagConstraints.HORIZONTAL;
	gbc_panel_input2.insets = new Insets(0, 0, 0, 5);
	gbc_panel_input2.gridx = 1;
	gbc_panel_input2.gridy = 0;
	panel_input1.add(panel_input2, gbc_panel_input2);
	panel_input2.setLayout(new GridLayout(0, 5, 0, 0));
	
	JPanel panel_7 = new JPanel();
	panel_input2.add(panel_7);
	panel_7.setLayout(new BorderLayout(0, 0));
	
	JPanel panel_8 = new JPanel();
	panel_input2.add(panel_8);
	panel_8.setLayout(new BorderLayout(0, 0));
	
	JLabel label = new JLabel("\u65F6   \u5EF6");
	panel_8.add(label, BorderLayout.CENTER);
	label.setHorizontalAlignment(SwingConstants.CENTER);
	label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	
	JPanel panel_9 = new JPanel();
	panel_input2.add(panel_9);
	panel_9.setLayout(new BorderLayout(0, 0));
	
	JLabel label_1 = new JLabel("\u6296   \u52A8");
	panel_9.add(label_1, BorderLayout.CENTER);
	label_1.setHorizontalAlignment(SwingConstants.CENTER);
	label_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	
	JPanel panel_10 = new JPanel();
	panel_input2.add(panel_10);
	panel_10.setLayout(new BorderLayout(0, 0));
	
	JLabel label_2 = new JLabel("\u4E22\u5305\u7387");
	panel_10.add(label_2, BorderLayout.CENTER);
	label_2.setHorizontalAlignment(SwingConstants.CENTER);
	label_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	
	JPanel panel_11 = new JPanel();
	panel_input2.add(panel_11);
	panel_11.setLayout(new BorderLayout(0, 0));
	
	JLabel label_3 = new JLabel("\u5E26   \u5BBD");
	panel_11.add(label_3, BorderLayout.CENTER);
	label_3.setHorizontalAlignment(SwingConstants.CENTER);
	label_3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	
	JPanel panel_13 = new JPanel();
	panel_input2.add(panel_13);
	panel_13.setLayout(new BorderLayout(0, 0));
	
	JLabel label_4 = new JLabel("\u65F6   \u5EF6");
	panel_13.add(label_4, BorderLayout.CENTER);
	label_4.setHorizontalAlignment(SwingConstants.CENTER);
	label_4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	
	JPanel panel_14 = new JPanel();
	panel_input2.add(panel_14);
	panel_14.setLayout(new BorderLayout(0, 0));
	
	JLabel label_8 = new JLabel("1");
	panel_14.add(label_8, BorderLayout.CENTER);
	label_8.setHorizontalAlignment(SwingConstants.CENTER);
	label_8.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	
	JPanel panel_15 = new JPanel();
	panel_input2.add(panel_15);
	panel_15.setLayout(new BorderLayout(0, 0));
	
	comboBox_1 = new JComboBox();
	panel_15.add(comboBox_1, BorderLayout.CENTER);
	comboBox_1.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			do_setLabel(e, label_12);
		}
	});
	comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "1/2", "1/3", "1/4", "1/5", "1/6", "1/7", "1/8", "1/9"}));
	comboBox_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	
	JPanel panel_16 = new JPanel();
	panel_input2.add(panel_16);
	panel_16.setLayout(new BorderLayout(0, 0));
	
	comboBox_2 = new JComboBox();
	panel_16.add(comboBox_2, BorderLayout.CENTER);
	comboBox_2.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			do_setLabel(e, label_13);
		}
	});
	comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "1/2", "1/3", "1/4", "1/5", "1/6", "1/7", "1/8", "1/9"}));
	comboBox_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	
	JPanel panel_17 = new JPanel();
	panel_input2.add(panel_17);
	panel_17.setLayout(new BorderLayout(0, 0));
	
	comboBox_3 = new JComboBox();
	panel_17.add(comboBox_3, BorderLayout.CENTER);
	comboBox_3.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			do_setLabel(e, label_15);
		}
	});
	comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "1/2", "1/3", "1/4", "1/5", "1/6", "1/7", "1/8", "1/9"}));
	comboBox_3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	
	
	JPanel panel_19 = new JPanel();
	panel_input2.add(panel_19);
	panel_19.setLayout(new BorderLayout(0, 0));
	
	JLabel label_5 = new JLabel("\u6296   \u52A8");
	panel_19.add(label_5, BorderLayout.CENTER);
	label_5.setHorizontalAlignment(SwingConstants.CENTER);
	label_5.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	
	JPanel panel_20 = new JPanel();
	panel_input2.add(panel_20);
	panel_20.setLayout(new BorderLayout(0, 0));
	
	
	label_12 = new JLabel("");
	label_12.setText(String.valueOf(1/Double.valueOf((comboBox_1.getSelectedItem()).toString().trim())));
	panel_20.add(label_12, BorderLayout.CENTER);
	label_12.setHorizontalAlignment(SwingConstants.CENTER);
	label_12.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	
	
	JPanel panel_21 = new JPanel();
	panel_input2.add(panel_21);
	panel_21.setLayout(new BorderLayout(0, 0));
	
	JLabel label_9 = new JLabel("1");
	panel_21.add(label_9, BorderLayout.CENTER);
	label_9.setHorizontalAlignment(SwingConstants.CENTER);
	label_9.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	
	JPanel panel_22 = new JPanel();
	panel_input2.add(panel_22);
	panel_22.setLayout(new BorderLayout(0, 0));
	
	comboBox_4 = new JComboBox();
	panel_22.add(comboBox_4, BorderLayout.CENTER);
	comboBox_4.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			do_setLabel(e, label_14);
		}
	});
	comboBox_4.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "1/2", "1/3", "1/4", "1/5", "1/6", "1/7", "1/8", "1/9"}));
	comboBox_4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	
	JPanel panel_23 = new JPanel();
	panel_input2.add(panel_23);
	panel_23.setLayout(new BorderLayout(0, 0));
	
	comboBox_5 = new JComboBox();
	panel_23.add(comboBox_5, BorderLayout.CENTER);
	comboBox_5.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			do_setLabel(e, label_16);
		}
	});
	comboBox_5.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "1/2", "1/3", "1/4", "1/5", "1/6", "1/7", "1/8", "1/9"}));
	comboBox_5.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	
		
		JPanel panel_25 = new JPanel();
		panel_input2.add(panel_25);
		panel_25.setLayout(new BorderLayout(0, 0));
		
		JLabel label_6 = new JLabel("\u4E22\u5305\u7387");
		panel_25.add(label_6, BorderLayout.CENTER);
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_26 = new JPanel();
		panel_input2.add(panel_26);
		panel_26.setLayout(new BorderLayout(0, 0));
		
		label_13 = new JLabel("");
		label_13.setText(String.valueOf(1/Double.valueOf((comboBox_2.getSelectedItem()).toString().trim())));
		panel_26.add(label_13, BorderLayout.CENTER);
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		JPanel panel_27 = new JPanel();
		panel_input2.add(panel_27);
		panel_27.setLayout(new BorderLayout(0, 0));
		
			
			label_14 = new JLabel("");
			label_14.setText(String.valueOf(1/Double.valueOf((comboBox_4.getSelectedItem()).toString().trim())));
			panel_27.add(label_14, BorderLayout.CENTER);
			label_14.setHorizontalAlignment(SwingConstants.CENTER);
			label_14.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			
			JPanel panel_28 = new JPanel();
			panel_input2.add(panel_28);
			panel_28.setLayout(new BorderLayout(0, 0));
			
			JLabel label_10 = new JLabel("1");
			panel_28.add(label_10, BorderLayout.CENTER);
			label_10.setHorizontalAlignment(SwingConstants.CENTER);
			label_10.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			
			JPanel panel_29 = new JPanel();
			panel_input2.add(panel_29);
			panel_29.setLayout(new BorderLayout(0, 0));
			
			comboBox_6 = new JComboBox();
			panel_29.add(comboBox_6, BorderLayout.CENTER);
			comboBox_6.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					do_setLabel(e, label_17);
				}
			});
			comboBox_6.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "1/2", "1/3", "1/4", "1/5", "1/6", "1/7", "1/8", "1/9"}));
			comboBox_6.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			
				
				JPanel panel_2 = new JPanel();
				panel_input2.add(panel_2);
				panel_2.setLayout(new BorderLayout(0, 0));
				
				JLabel label_7 = new JLabel("\u5E26   \u5BBD");
				label_7.setHorizontalAlignment(SwingConstants.CENTER);
				label_7.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				panel_2.add(label_7, BorderLayout.CENTER);
				
				JPanel panel_3 = new JPanel();
				panel_input2.add(panel_3);
				panel_3.setLayout(new BorderLayout(0, 0));
				
				label_15 = new JLabel("");
				label_15.setText(String.valueOf(1/Double.valueOf((comboBox_3.getSelectedItem()).toString().trim())));
				label_15.setHorizontalAlignment(SwingConstants.CENTER);
				label_15.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				panel_3.add(label_15, BorderLayout.CENTER);
				
				JPanel panel_31 = new JPanel();
				panel_input2.add(panel_31);
				panel_31.setLayout(new BorderLayout(0, 0));
				
				label_16 = new JLabel("");
				label_16.setText(String.valueOf(1/Double.valueOf((comboBox_5.getSelectedItem()).toString().trim())));
				label_16.setHorizontalAlignment(SwingConstants.CENTER);
				label_16.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				panel_31.add(label_16, BorderLayout.CENTER);
				
				JPanel panel_32 = new JPanel();
				panel_input2.add(panel_32);
				panel_32.setLayout(new BorderLayout(0, 0));
				
				label_17 = new JLabel("");
				label_17.setText(String.valueOf(1/Double.valueOf((comboBox_6.getSelectedItem()).toString().trim())));
				label_17.setHorizontalAlignment(SwingConstants.CENTER);
				label_17.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				panel_32.add(label_17, BorderLayout.CENTER);
				
				JPanel panel_30 = new JPanel();
				panel_input2.add(panel_30);
				panel_30.setLayout(new BorderLayout(0, 0));
				
				JLabel label_11 = new JLabel("1");
				label_11.setHorizontalAlignment(SwingConstants.CENTER);
				label_11.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				panel_30.add(label_11, BorderLayout.CENTER);
	
	JPanel panel_inputbutton = new JPanel();
	panel_inputbutton.setBorder(new EmptyBorder(10, 0, 10, 5));
	GridBagConstraints gbc_panel_inputbutton = new GridBagConstraints();
	gbc_panel_inputbutton.fill = GridBagConstraints.BOTH;
	gbc_panel_inputbutton.gridx = 2;
	gbc_panel_inputbutton.gridy = 0;
	panel_input1.add(panel_inputbutton, gbc_panel_inputbutton);
	panel_inputbutton.setLayout(new GridLayout(1, 1, 0, 0));
	
	JButton EvaButton = new JButton("\u5F00\u59CB\u8BC4\u4F30");
	EvaButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			do_EvaButton_actionPerformed(e);
		}
	});
	EvaButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	panel_inputbutton.add(EvaButton);
	
	
	
	
	}
	
	

	
	
	
	
	
	
	
	
private static void createJRRMResultTab() {
		
		DefaultTableModel model;
		model =new DefaultTableModel();
	
		JRRMResultTable = new JTable(model) {
			public boolean isCellEditable(int row, int column){
	                   return false;
	                   }//表格不允许被编辑
		};
		JRRMResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		//JRRMResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		//JRRMResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		//JRRMResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		//JRRMResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		
		
		
		JRRMResultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JRRMResultTable.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		JRRMResultTable.setRowHeight(30);// 设置表体高度
	    JTableHeader header = JRRMResultTable.getTableHeader();// 获得表头对象
	    header.setFont(new Font("微软雅黑", Font.PLAIN, 15));// 设置表头字体
	    //JRRMResultTable.getTableHeader().setPreferredSize(new Dimension(100000,30));
	    header.setPreferredSize(new Dimension(100000, 30));// 设置表头高度    

	    //header.setResizingAllowed();
	    /*
	    DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();// 获得渲染器
	    renderer.setHorizontalAlignment(SwingConstants.CENTER);// 设置表头内容居中显示
	    */
	    
	    //DefaultTableModel model = (DefaultTableModel) TrafficTable.getModel();// 获得表格模型
	    model.setRowCount(0);// 清空表格中的数据
	    model.setColumnIdentifiers(new Object[] { "请求终端地址       ", "请求编号  ", "业务类型  ","业务重要性  ", "用户偏好  ", "可用网络                ", "带宽（kbps）  ", "延迟（ms）  ","延迟抖动（ms）      ","丢包率（%） ","接入网络类型  ","接入网络编号  ","接入网络地址      "});
	  
	    

	    //EvaResultOp.updateTableBased();//更新基于表的评估结果数据库
	    List<RequestBean> results = JRRMSqlRequest.queryAll();// 获得数据库中表格的全部数据	    
	    for (RequestBean tempRes : results) {// 将数据加载到表格模型中
	    	
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
			if(tempRes.getLRRMIP().equals("0")) {
				SQLLRRMIP="-";
			} else {
				SQLLRRMIP=tempRes.getLRRMIP();
			}
	
	        model.addRow(new Object[] { tempRes.getAddress(), tempRes.getPackageID(), SQLTrafficType, tempRes.getTrafficImportance(), SQLPreference, 
	        		tempRes.getVisibleNet(), tempRes.getBandWidth(), tempRes.getDelay(), tempRes.getJitter(), tempRes.getPacketLoss(), 
	        		SQLNetType , SQLNetID, SQLLRRMIP});
	        }
	    
	    //表头列宽随内容变化
	    JTableFit.FitTableColumns(JRRMResultTable);	    

	    JRRMResultTable.setModel(model);// 应用表格模型		
	    scrollPaneJRRM.setViewportView(JRRMResultTable);
	    }
	
    private static void createLRRMResultTab(int type) {
		
		DefaultTableModel model;
		model = new DefaultTableModel();
	
		LRRMResultTable = new JTable(model) {
			public boolean isCellEditable(int row, int column){
	                   return false;
	                   }//表格不允许被编辑
		};
		LRRMResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		//JRRMResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		//JRRMResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		//JRRMResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		//JRRMResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		
		
		
		LRRMResultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		LRRMResultTable.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		LRRMResultTable.setRowHeight(30);// 设置表体高度
	    JTableHeader header = LRRMResultTable.getTableHeader();// 获得表头对象
	    header.setFont(new Font("微软雅黑", Font.PLAIN, 15));// 设置表头字体
	    //JRRMResultTable.getTableHeader().setPreferredSize(new Dimension(100000,30));
	    header.setPreferredSize(new Dimension(100000, 30));// 设置表头高度    
	    
	    
	    
	    
	    
	    
	    /*
	    DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();// 获得渲染器
	    renderer.setHorizontalAlignment(SwingConstants.CENTER);// 设置表头内容居中显示
	    */
	    
	    
	    //DefaultTableModel model = (DefaultTableModel) TrafficTable.getModel();// 获得表格模型
	    
	    model.setRowCount(0);// 清空表格中的数据
	    model.setColumnIdentifiers(new Object[] { "LRRM地址       ", "汇报网络类型", "网络编号", "带宽（kbps）   ", "时延（ms）   ", "抖动（ms）   ", "丢包率(%）   ","负载率（%）   "});


	
	    //EvaResultOp.updateTableBased();//更新基于表的评估结果数据库
	    List<ResourceBean> results = JRRMSqlResource.queryAll(type);// 获得数据库中表格的全部数据	    
	    for (ResourceBean tempRes : results) {// 将数据加载到表格模型中
	    	
			String SQLPreference = null ;
			if(tempRes.getNetworkType()==1) {
				SQLPreference="WiMAX";
			}
			if(tempRes.getNetworkType()==3) {
				SQLPreference="WLAN";
			}
			if(tempRes.getNetworkType()==2) {
				SQLPreference="UMTS";
			}
			
			String packetloss = String.format("%.2e" ,tempRes.getPacketLoss());
			model.addRow(new Object[] { tempRes.getAddress(),  SQLPreference, 
	        		tempRes.getNetworkID(), tempRes.getBandWidth(), tempRes.getDelay(), tempRes.getJitter(), packetloss, 
	        		tempRes.getLoadRate()});  
	        }
	    JTableFit.FitTableColumns(LRRMResultTable);	
	    
	    LRRMResultTable.setModel(model);// 应用表格模型		
		
	    scrollPaneLRRM.setViewportView(LRRMResultTable);
	    }
	
    private static void createWeightTab() {
		
		DefaultTableModel model;
		model = new DefaultTableModel();
	
		WeightTab = new JTable(model) {
			public boolean isCellEditable(int row, int column){
	                   return false;
	                   }//表格不允许被编辑
		};
		//WeightTab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		WeightTab.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		//JRRMResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		//JRRMResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		//JRRMResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		
		
		
		WeightTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		WeightTab.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		WeightTab.setRowHeight(30);// 设置表体高度
	    JTableHeader header = LRRMResultTable.getTableHeader();// 获得表头对象
	    header.setFont(new Font("微软雅黑", Font.PLAIN, 15));// 设置表头字体
	    //JRRMResultTable.getTableHeader().setPreferredSize(new Dimension(100000,30));
	    header.setPreferredSize(new Dimension(100000, 30));// 设置表头高度    

	    
	    /*
	    DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();// 获得渲染器
	    renderer.setHorizontalAlignment(SwingConstants.CENTER);// 设置表头内容居中显示
	    */
	    
	    
	    //DefaultTableModel model = (DefaultTableModel) TrafficTable.getModel();// 获得表格模型
	    
	    model.setRowCount(0);// 清空表格中的数据
	    model.setColumnIdentifiers(new Object[] {  "业务类型   ","时延（ms）   ", "抖动（ms）   ", "丢包率(%）   ","带宽（kbps）   "});

    
	    //EvaResultOp.updateTableBased();//更新基于表的评估结果数据库
	    List<WeightBean> results = JRRMSqlWeight.queryAll();// 获得数据库中表格的全部数据	  

	    for (WeightBean tempRes : results) {// 将数据加载到表格模型中
	    	
			String SQLPreference = null ;
			if(tempRes.getTrafficType()==1) {
				SQLPreference="会话类";	    
			}
			if(tempRes.getTrafficType()==2) {
				SQLPreference="流类";
			}
			if(tempRes.getTrafficType()==3) {
				SQLPreference="交互类";
			}
			if(tempRes.getTrafficType()==4) {
				SQLPreference="背景类";
			}
			model.addRow(new Object[] {  SQLPreference, 
	        		 tempRes.getDelay(), tempRes.getJitter(), tempRes.getPacketLoss(),tempRes.getBandWidth() 
	        		});  
	        }
	    JTableFit.FitTableColumns(WeightTab);	
	    
	    WeightTab.setModel(model);// 应用表格模型		
		
	    scrollPaneWeight.setViewportView(WeightTab);
	
	    }
    
    
	private void do_EvaButton_actionPerformed(ActionEvent e) {
		double[][] jm = new double[4][4];
		for(int i = 0; i < 4; i++) {
			jm[i][i] = 1;
		}
		jm[1][0] = Double.valueOf(label_12.getText());
		jm[2][0] = Double.valueOf(label_13.getText());
		jm[2][1] = Double.valueOf(label_14.getText()); 
		jm[3][0] = Double.valueOf(label_15.getText()); 	
		jm[3][1] = Double.valueOf(label_16.getText()); 
		jm[3][2] = Double.valueOf(label_17.getText()); 
		
		jm[0][1] = 1/jm[1][0];
		jm[0][2] = 1/jm[2][0];
		jm[0][3] = 1/jm[3][0];
		jm[1][2] = 1/jm[2][1];
		jm[1][3] = 1/jm[3][1];
		jm[2][3] = 1/jm[3][2];

		Boolean IsSelectedButton = rdbtnNewRadioButton.isSelected();
		Boolean IsSelectedButton_1 = rdbtnNewRadioButton_1.isSelected();
		Boolean IsSelectedButton_2 = rdbtnNewRadioButton_2.isSelected();
		Boolean IsSelectedButton_3 = rdbtnNewRadioButton_3.isSelected();
		
		int type = 0;
		if(IsSelectedButton == true) {
			type=1;
		}
		if(IsSelectedButton_1 == true) {
			type=2;
		}
		if(IsSelectedButton_2 == true) {
			type=3;
		}	
		if(IsSelectedButton_3 == true) {
			type=4;
		}
	
		
		//WeightAlgorithm Weight = new WeightAlgorithm();
		WeightAlgorithm.judgementMatrix = jm;
		double[] weightVector = WeightAlgorithm.getWeightVector();
		/*
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.print(madmBasedImportance.judgementMatrix[i][j] + " ");	
			}
			System.out.println();
		}
		*/
		WeightBean res = new WeightBean();
		res.setTrafficType(type);
		res.setDelay(weightVector[0]);
		res.setJitter(weightVector[1]);
		res.setPacketLoss(weightVector[2]);
		res.setBandWidth(weightVector[3]);
		double cr = weightVector[4];
		if(cr<0.1){
			JOptionPane.showMessageDialog(null, "一致性检验通过！", "", JOptionPane.WARNING_MESSAGE);
			JRRMSqlWeight.saveOrUpdate(res);
		} else {
			JOptionPane.showMessageDialog(null, "一致性检验未通过！", "", JOptionPane.WARNING_MESSAGE);
		}
		createWeightTab();
	}
    
    
	//处理判断矩阵下拉框选择项改变事件
	private void do_setLabel(ItemEvent e, JLabel jlbl) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			String qushen  = ((String)e.getItem()).trim();
			String shasha;
			double doubleValue1;
			if(-1==qushen.indexOf("/")){				
				shasha =qushen;
				doubleValue1 = 1/Double.valueOf(shasha);
				BigDecimal   b   =   new   BigDecimal(doubleValue1);
				double doubleValue2 =  b.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();
				jlbl.setText(String.valueOf(doubleValue2));
			} else {
				String values[] = qushen.split("/");
				shasha =values[1];
				doubleValue1 = Double.valueOf(shasha);
				jlbl.setText(String.valueOf(doubleValue1));
			}
			

		}
	}
	//处理标签页改变事件
	private void do_setTabChanged(ChangeEvent e) {
		if (0 == tabbedPane.getSelectedIndex()) {

			createLRRMResultTab(1);
		} else 	if (1 == tabbedPane.getSelectedIndex()) {

			createJRRMResultTab();
		} else if (2 == tabbedPane.getSelectedIndex()) {

			createWeightTab();
		} else
			return;
	}
	
	
	
	
	
	
}
