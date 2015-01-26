package com.zac.frame;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.GridLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


import com.zac.algorithm.madmBasedImportance;
import com.zac.algorithm.tableBasedImportance;
import com.zac.bean.*;
import com.zac.sql.*;
import com.zac.util.JTableFit;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class ImpEvaPanel extends JPanel {
	private JTabbedPane tabbedPane;
	private static JScrollPane scrollPaneResult;
	private static JScrollPane scrollPaneAtt;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();

	//任务类型信息标签
	JLabel missionInfo1 = new JLabel("");
	JLabel missionInfo2 = new JLabel("");
	JLabel missionInfo3 = new JLabel("");
	JLabel missionInfo4 = new JLabel("");
	
	//用户类型信息标签
	JLabel userInfo1 = new JLabel("");
	JLabel userInfo2 = new JLabel("");
	JLabel userInfo3 = new JLabel("");
	
	//业务类型信息标签
	JLabel trafficInfo1 = new JLabel("");
	JLabel trafficInfo2 = new JLabel("");
	JLabel trafficInfo3 = new JLabel("");
	JLabel trafficInfo4 = new JLabel("");
	
	JRadioButton twoRadioButton = new JRadioButton("\u4E8C\u7EF4\u91CD\u8981\u6027\u8868\u8BC4\u4F30\u7B97\u6CD5(\u6839\u636E\u7528\u6237\u7C7B\u578B\u548C\u4E1A\u52A1\u7C7B\u578B)");

	// 获得数据库中表格的全部数据
	private List<TrafficBean> trafResults = TrafficOp.queryAll();
	private List<UserBean> userResults = UserOp.queryAll();
	private List<MissionBean> missionResults = MissionOp.queryAll();
	private static JTable ResultTable;
	private static JTable AttTable;
	
	//
	private JLabel label32;
	private JLabel label42;
	private JLabel label43;
	
	private JComboBox comboBox23;
	private JComboBox comboBox24;
	private JComboBox comboBox34;
	
	/**
	 * Create the panel.
	 */
	public ImpEvaPanel(int tabSelect) {		
		setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				do_setTabChanged(e);
			}
		});
		
		tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		add(tabbedPane);
		
		JPanel tabledEvaPanel = new JPanel();
		tabbedPane.addTab("\u57FA\u4E8E\u91CD\u8981\u6027\u8868\u7684\u8BC4\u4F30", null, tabledEvaPanel, null);
		tabledEvaPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel tempPanel = new JPanel();
		tabledEvaPanel.add(tempPanel, BorderLayout.CENTER);
		tempPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel infoPanel = new JPanel();
		tempPanel.add(infoPanel, BorderLayout.CENTER);
		infoPanel.setLayout(new GridLayout(1, 3, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "\u4EFB\u52A1\u7C7B\u578B", TitledBorder.LEADING, 
				TitledBorder.TOP, new Font("微软雅黑", Font.PLAIN, 15), null));
		infoPanel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel labelPanel_1 = new JPanel();
		panel_1.add(labelPanel_1);
		labelPanel_1.setLayout(new GridLayout(8, 1, 0, 0));
		
		JLabel mission = new JLabel("\u4EFB\u52A1\u7C7B\u578B\uFF1A");
		mission.setHorizontalAlignment(SwingConstants.CENTER);
		mission.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		labelPanel_1.add(mission);
		
		JLabel mission1 = new JLabel("\u4F18  \u5148  \u7EA7\uFF1A");
		mission1.setHorizontalAlignment(SwingConstants.CENTER);
		mission1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		labelPanel_1.add(mission1);
		
		JLabel mission2 = new JLabel("\u53EF  \u9760  \u6027\uFF1A");
		mission2.setHorizontalAlignment(SwingConstants.CENTER);
		mission2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		labelPanel_1.add(mission2);
		
		JLabel mission3 = new JLabel("\u5E26\u5BBD\u9700\u6C42\uFF1A");
		mission3.setHorizontalAlignment(SwingConstants.CENTER);
		mission3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		labelPanel_1.add(mission3);
		
		JLabel mission4 = new JLabel("\u54CD\u5E94\u65F6\u95F4\uFF1A");
		mission4.setHorizontalAlignment(SwingConstants.CENTER);
		mission4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		labelPanel_1.add(mission4);
		
		JPanel labelPanel_2 = new JPanel();
		labelPanel_2.setBorder(new EmptyBorder(0, 0, 0, 10));
		panel_1.add(labelPanel_2);
		labelPanel_2.setLayout(new GridLayout(8, 1, 0, 0));
		
		JComboBox missionComboBox = new JComboBox();
		missionComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		missionComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				do_setMissionLabel(e);
			}
		});
		//设置任务类型下拉表格项
		List<String> missionTitle = new ArrayList<String>(1);
		missionTitle.add("请选择...");
		for (MissionBean tempMission : missionResults) {
			missionTitle.add(tempMission.getMissionTitle());
		}		
		missionComboBox.setModel(new DefaultComboBoxModel((String[])missionTitle.toArray(new String[missionTitle.size()])));		
		labelPanel_2.add(missionComboBox);
		
		missionInfo1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			
		//JLabel missionInfo1 = new JLabel("");
		missionInfo1.setHorizontalAlignment(SwingConstants.LEFT);
		labelPanel_2.add(missionInfo1);	
		missionInfo2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		//JLabel missionInfo2 = new JLabel("");
		missionInfo2.setHorizontalAlignment(SwingConstants.LEFT);
		labelPanel_2.add(missionInfo2);	
		missionInfo3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		//JLabel missionInfo3 = new JLabel("");
		missionInfo3.setHorizontalAlignment(SwingConstants.LEFT);
		labelPanel_2.add(missionInfo3);
		missionInfo4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		//JLabel missionInfo4 = new JLabel("");
		missionInfo4.setHorizontalAlignment(SwingConstants.LEFT);
		labelPanel_2.add(missionInfo4);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "\u7528\u6237\u7C7B\u578B", 
				TitledBorder.LEADING, TitledBorder.TOP, new Font("微软雅黑", Font.PLAIN, 15), null));
		infoPanel.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel labelPanel_3 = new JPanel();
		panel_2.add(labelPanel_3);
		labelPanel_3.setLayout(new GridLayout(8, 1, 0, 0));
		
		JLabel user = new JLabel("\u7528\u6237\u7C7B\u578B\uFF1A");
		user.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		user.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel_3.add(user);
		
		JLabel user1 = new JLabel("\u4F18  \u5148  \u7EA7\uFF1A");
		user1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		user1.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel_3.add(user1);
		
		JLabel user2 = new JLabel("\u7C7B        \u522B\uFF1A");
		user2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		user2.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel_3.add(user2);
		
		JLabel user3 = new JLabel("\u5B89\u5168\u7B56\u7565\uFF1A");
		user3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		user3.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel_3.add(user3);
		
		JPanel labelPanel_4 = new JPanel();
		labelPanel_4.setBorder(new EmptyBorder(0, 0, 0, 10));
		panel_2.add(labelPanel_4);
		labelPanel_4.setLayout(new GridLayout(8, 1, 0, 0));
		
		JComboBox userComboBox = new JComboBox();
		userComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		userComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				do_setUserLabel(e);
			}
		});
		//设置用户类型下拉表格项
		List<String> userTitle = new ArrayList<String>(1);
		userTitle.add("请选择...");
		for (UserBean tempUser : userResults) {
			userTitle.add(tempUser.getUserTitle());
		}		
		userComboBox.setModel(new DefaultComboBoxModel((String[])userTitle.toArray(new String[userTitle.size()])));		
		labelPanel_4.add(userComboBox);
		
		userInfo1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		//JLabel userInfo1 = new JLabel("");
		userInfo1.setHorizontalAlignment(SwingConstants.LEFT);
		labelPanel_4.add(userInfo1);
		userInfo2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		//JLabel userInfo2 = new JLabel("");
		userInfo2.setHorizontalAlignment(SwingConstants.LEFT);
		labelPanel_4.add(userInfo2);
		userInfo3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		//JLabel userInfo3 = new JLabel("");
		userInfo3.setHorizontalAlignment(SwingConstants.LEFT);
		labelPanel_4.add(userInfo3);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "\u4E1A\u52A1\u7C7B\u578B", 
				TitledBorder.LEADING, TitledBorder.TOP, new Font("微软雅黑", Font.PLAIN, 15), null));
		infoPanel.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel labelPanel_5 = new JPanel();
		panel_3.add(labelPanel_5);
		labelPanel_5.setLayout(new GridLayout(8, 1, 0, 0));
		
		JLabel traffic = new JLabel("\u4E1A\u52A1\u7C7B\u578B\uFF1A");
		traffic.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		traffic.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel_5.add(traffic);
		
		JLabel traffic1 = new JLabel("\u4F18  \u5148  \u7EA7\uFF1A");
		traffic1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		traffic1.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel_5.add(traffic1);
		
		JLabel traffic2 = new JLabel("\u6296        \u52A8\uFF1A");
		traffic2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		traffic2.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel_5.add(traffic2);
		
		JLabel traffic3 = new JLabel("\u4E22  \u5305  \u7387\uFF1A");
		traffic3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		traffic3.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel_5.add(traffic3);
		
		JLabel traffic4 = new JLabel("\u65F6        \u5EF6\uFF1A");
		traffic4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		traffic4.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel_5.add(traffic4);
		
		JPanel labelPanel_6 = new JPanel();
		labelPanel_6.setBorder(new EmptyBorder(0, 0, 0, 10));
		panel_3.add(labelPanel_6);
		labelPanel_6.setLayout(new GridLayout(8, 1, 0, 0));
		
		JComboBox trafficComboBox = new JComboBox();
		trafficComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		trafficComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				do_setTrafficLabel(e);
			}
		});
		//设置业务类型下拉表格项
		List<String> trafficTitle = new ArrayList<String>(1);
		trafficTitle.add("请选择...");
		for (TrafficBean tempTraffic : trafResults) {
			trafficTitle.add(tempTraffic.getTrafficTitle());
		}		
		trafficComboBox.setModel(new DefaultComboBoxModel((String[])trafficTitle.toArray(new String[trafficTitle.size()])));
		labelPanel_6.add(trafficComboBox);
		
		trafficInfo1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		//JLabel trafficInfo1 = new JLabel("");
		trafficInfo1.setHorizontalAlignment(SwingConstants.LEFT);
		labelPanel_6.add(trafficInfo1);
		trafficInfo2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		//JLabel trafficInfo2 = new JLabel("");
		trafficInfo2.setHorizontalAlignment(SwingConstants.LEFT);
		labelPanel_6.add(trafficInfo2);
		trafficInfo3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		//JLabel trafficInfo3 = new JLabel("");
		trafficInfo3.setHorizontalAlignment(SwingConstants.LEFT);
		labelPanel_6.add(trafficInfo3);
		trafficInfo4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		//JLabel trafficInfo4 = new JLabel("");
		trafficInfo4.setHorizontalAlignment(SwingConstants.LEFT);
		labelPanel_6.add(trafficInfo4);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		tempPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
		buttonPanel.add(leftPanel);
		leftPanel.setLayout(new GridLayout(5, 0, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("");
		leftPanel.add(lblNewLabel_1);
		twoRadioButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		//JRadioButton twoRadioButton = new JRadioButton("\u4E8C\u7EF4\u91CD\u8981\u6027\u8868\u8BC4\u4F30\u7B97\u6CD5\uFF08\u6839\u636E\u7528\u6237\u7C7B\u578B\u548C\u4E1A\u52A1\u7C7B\u578B\uFF09");
		twoRadioButton.setSelected(true);
		leftPanel.add(twoRadioButton);
		buttonGroup.add(twoRadioButton);
		
		JLabel lblNewLabel = new JLabel("");
		leftPanel.add(lblNewLabel);
		
		JRadioButton threeRadioButton = new JRadioButton("\u4E09\u7EF4\u91CD\u8981\u6027\u8868\u8BC4\u4F30\u7B97\u6CD5");
		threeRadioButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		leftPanel.add(threeRadioButton);
		buttonGroup.add(threeRadioButton);
		
		JLabel lblNewLabel_2 = new JLabel("");
		leftPanel.add(lblNewLabel_2);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setBorder(new EmptyBorder(40, 100, 40, 10));
		buttonPanel.add(rightPanel);
		
		JButton evaButton = new JButton("\u5F00\u59CB\u8BC4\u4F30");
		evaButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		evaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_evaluateButton_actionPerformed(e);
			}
		});
		rightPanel.setLayout(new BorderLayout(0, 0));
		rightPanel.add(evaButton);
		
		JPanel emptyPane1 = new JPanel();
		tabledEvaPanel.add(emptyPane1, BorderLayout.SOUTH);
		
		JPanel emptyPane2 = new JPanel();
		tabledEvaPanel.add(emptyPane2, BorderLayout.NORTH);
		
		JPanel emptyPane3 = new JPanel();
		tabledEvaPanel.add(emptyPane3, BorderLayout.EAST);
		
		JPanel emptyPane4 = new JPanel();
		tabledEvaPanel.add(emptyPane4, BorderLayout.WEST);
		
		JPanel MAMDEvaPanel = new JPanel();
		tabbedPane.addTab("\u57FA\u4E8EMADM\u7684\u8BC4\u4F30", null, MAMDEvaPanel, null);				
		MAMDEvaPanel.setLayout(new BorderLayout(5, 5));
		
		JPanel attPanel = new JPanel();
		attPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "MADM属性列表", TitledBorder.LEADING, 
				TitledBorder.TOP, new Font("微软雅黑", Font.PLAIN, 15), null));
		MAMDEvaPanel.add(attPanel, BorderLayout.CENTER);
		attPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPaneAtt = new JScrollPane();
		attPanel.add(scrollPaneAtt);
		
		JPanel bottomPanel = new JPanel();
		MAMDEvaPanel.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel1 = new JPanel();
		panel1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "MADM判断矩阵(0.1-0.9标度:标度越大表示此行属性较此列属于越重要★0.5-同等重要)", 
				TitledBorder.LEADING, TitledBorder.TOP, new Font("微软雅黑", Font.PLAIN, 15), null));
		bottomPanel.add(panel1, BorderLayout.CENTER);
		panel1.setLayout(new GridLayout(4, 4, 0, 0));
		
		JPanel panel11 = new JPanel();
		panel1.add(panel11);
		panel11.setLayout(new BorderLayout(0, 0));
		
		JPanel panel12 = new JPanel();
		panel1.add(panel12);
		panel12.setLayout(new BorderLayout(0, 0));
		
		JLabel trafAttLabel = new JLabel("\u4E1A\u52A1\u5C5E\u6027");
		trafAttLabel.setHorizontalAlignment(SwingConstants.CENTER);
		trafAttLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel12.add(trafAttLabel);
		
		JPanel panel13 = new JPanel();
		panel1.add(panel13);
		panel13.setLayout(new BorderLayout(0, 0));
		
		JLabel userAttLabel = new JLabel("\u7528\u6237\u5C5E\u6027");
		userAttLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userAttLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel13.add(userAttLabel, BorderLayout.CENTER);
		
		JPanel panel14 = new JPanel();
		panel1.add(panel14);
		panel14.setLayout(new BorderLayout(0, 0));
		
		JLabel missionAttLabel = new JLabel("\u4EFB\u52A1\u5C5E\u6027");
		missionAttLabel.setHorizontalAlignment(SwingConstants.CENTER);
		missionAttLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel14.add(missionAttLabel, BorderLayout.CENTER);
		
		JPanel panel21 = new JPanel();
		panel1.add(panel21);
		panel21.setLayout(new BorderLayout(0, 0));
		
		JLabel trafAttLabel_1 = new JLabel("\u4E1A\u52A1\u5C5E\u6027");
		trafAttLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		trafAttLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel21.add(trafAttLabel_1, BorderLayout.NORTH);
		
		JPanel panel22 = new JPanel();
		panel1.add(panel22);
		panel22.setLayout(new BorderLayout(0, 0));
		
		JLabel label22 = new JLabel("0.5");
		label22.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label22.setHorizontalAlignment(SwingConstants.CENTER);
		panel22.add(label22, BorderLayout.CENTER);
		
		JPanel panel23 = new JPanel();
		panel23.setBorder(new EmptyBorder(0, 55, 0, 40));
		panel1.add(panel23);
		panel23.setLayout(new BorderLayout(0, 0));
		
		comboBox23 = new JComboBox();
		comboBox23.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				do_setLabel(e, label32);
			}
		});
		comboBox23.setModel(new DefaultComboBoxModel(new String[] {"    0.1", "    0.2", "    0.3", "    0.4", "    0.5", "    0.6", "    0.7", "    0.8", "    0.9"}));
		comboBox23.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel23.add(comboBox23, BorderLayout.CENTER);
		
		JPanel panel24 = new JPanel();
		panel24.setBorder(new EmptyBorder(0, 55, 0, 40));
		panel1.add(panel24);
		panel24.setLayout(new BorderLayout(0, 0));
		
		comboBox24 = new JComboBox();
		comboBox24.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				do_setLabel(e, label42);
			}
		});
		comboBox24.setModel(new DefaultComboBoxModel(new String[] {"    0.1", "    0.2", "    0.3", "    0.4", "    0.5", "    0.6", "    0.7", "    0.8", "    0.9"}));
		comboBox24.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel24.add(comboBox24, BorderLayout.CENTER);
		
		JPanel panel31 = new JPanel();
		panel1.add(panel31);
		panel31.setLayout(new BorderLayout(0, 0));
		
		JLabel userAttLabel_1 = new JLabel("\u7528\u6237\u5C5E\u6027");
		userAttLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		userAttLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel31.add(userAttLabel_1, BorderLayout.NORTH);
		
		JPanel panel32 = new JPanel();
		panel1.add(panel32);
		panel32.setLayout(new BorderLayout(0, 0));
		
		label32 = new JLabel("");
		label32.setText(String.valueOf(1 - Double.valueOf((comboBox23.getSelectedItem()).toString().trim())));
		label32.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label32.setHorizontalAlignment(SwingConstants.CENTER);
		panel32.add(label32, BorderLayout.CENTER);
		
		JPanel panel33 = new JPanel();
		panel1.add(panel33);
		panel33.setLayout(new BorderLayout(0, 0));
		
		JLabel label33 = new JLabel("0.5");
		label33.setHorizontalAlignment(SwingConstants.CENTER);
		label33.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel33.add(label33, BorderLayout.CENTER);
		
		JPanel panel34 = new JPanel();
		panel34.setBorder(new EmptyBorder(0, 55, 0, 40));
		panel1.add(panel34);
		panel34.setLayout(new BorderLayout(0, 0));
		
		comboBox34 = new JComboBox();
		comboBox34.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				do_setLabel(e, label43);
			}
		});
		comboBox34.setModel(new DefaultComboBoxModel(new String[] {"    0.1", "    0.2", "    0.3", "    0.4", "    0.5", "    0.6", "    0.7", "    0.8", "    0.9"}));
		comboBox34.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel34.add(comboBox34, BorderLayout.CENTER);
		
		JPanel panel41 = new JPanel();
		panel1.add(panel41);
		panel41.setLayout(new BorderLayout(0, 0));
		
		JLabel missionAttLabel_1 = new JLabel("\u4EFB\u52A1\u5C5E\u6027");
		missionAttLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		missionAttLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel41.add(missionAttLabel_1, BorderLayout.NORTH);
		
		JPanel panel42 = new JPanel();
		panel1.add(panel42);
		panel42.setLayout(new BorderLayout(0, 0));
		
		label42 = new JLabel("");
		label42.setText(String.valueOf(1 - Double.valueOf((comboBox23.getSelectedItem()).toString().trim())));
		label42.setHorizontalAlignment(SwingConstants.CENTER);
		label42.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel42.add(label42, BorderLayout.CENTER);
		
		JPanel panel43 = new JPanel();
		panel1.add(panel43);
		panel43.setLayout(new BorderLayout(0, 0));
		
		label43 = new JLabel("");
		label43.setText(String.valueOf(1 - Double.valueOf((comboBox23.getSelectedItem()).toString().trim())));
		label43.setHorizontalAlignment(SwingConstants.CENTER);
		label43.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel43.add(label43, BorderLayout.CENTER);
		
		JPanel panel44 = new JPanel();
		panel1.add(panel44);
		panel44.setLayout(new BorderLayout(0, 0));
		
		JLabel label44 = new JLabel("0.5");
		label44.setHorizontalAlignment(SwingConstants.CENTER);
		label44.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel44.add(label44, BorderLayout.CENTER);
		
		//模糊判断矩阵下拉框默认选择值
		do_defaultButton_actionPerformed(new ActionEvent(new Object(), 0, ""));  //参数无作用
		
		JPanel panel2 = new JPanel();
		bottomPanel.add(panel2, BorderLayout.EAST);
		
		JButton madmEvaButton = new JButton("\u5F00\u59CB\u8BC4\u4F30");
		madmEvaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_madmEvaButton_actionPerformed(e);
			}
		});
		panel2.setLayout(new GridLayout(2, 1, 0, 0));
		
		JButton defaultButton = new JButton("\u6062\u590D\u9ED8\u8BA4");
		defaultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_defaultButton_actionPerformed(e);
			}
		});
		defaultButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel2.add(defaultButton);
		madmEvaButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel2.add(madmEvaButton);
		
		JPanel resultPanel = new JPanel();
		tabbedPane.addTab("\u91CD\u8981\u6027\u8BC4\u4F30\u7ED3\u679C\u603B\u89C8", null, resultPanel, null);
		resultPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPaneResult = new JScrollPane();
		resultPanel.add(scrollPaneResult);
		
		if (1 == tabSelect) {		
			createMADMTab();
		} else if (2 == tabSelect) {
			createResultTab();
		}
		
		//设置Tab默认选择项
		tabbedPane.setSelectedIndex(tabSelect);
	}
	
	
	//处理标签页改变事件
	private void do_setTabChanged(ChangeEvent e) {
		if (2 == tabbedPane.getSelectedIndex()) {
			createResultTab();
		} else if (1 == tabbedPane.getSelectedIndex()) {
			createMADMTab();
		}
	}
	
	//处理任务类型下拉列表框选择项改变处理事件
	private void do_setMissionLabel(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			MissionBean mission = new MissionBean();
			for (MissionBean tempMission : missionResults) {
				if(tempMission.getMissionTitle().equals((String)e.getItem())) {
					mission = tempMission;
					break;
				}
			}
			missionInfo1.setText(String.valueOf(mission.getMissionPriority()));
			missionInfo2.setText(String.valueOf(mission.getMissionReliability()));
			missionInfo3.setText(">=" + String.valueOf(mission.getMissionBandWidth()) + "bps");
			missionInfo4.setText("<=" + String.valueOf(mission.getMissionResponseTime()) + "ms");
		}
	}
	
	//处理用户类型下拉列表框选择项改变处理事件
	private void do_setUserLabel(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			UserBean user = new UserBean();
			for (UserBean tempUser : userResults) {
				if(tempUser.getUserTitle().equals((String)e.getItem())) {
					user = tempUser;
					break;
				}
			}
			userInfo1.setText(String.valueOf(user.getUserPriority()));
			userInfo2.setText(String.valueOf(user.getUserClassification() + "类"));
			userInfo3.setText(String.valueOf(user.getUserSecurityPolicy() + "级"));
		}
	}
	
	//处理业务类型下拉列表框选择项改变处理事件
	private void do_setTrafficLabel(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			TrafficBean traffic = new TrafficBean();
			for (TrafficBean tempTraffic : trafResults) {
				if(tempTraffic.getTrafficTitle().equals((String)e.getItem())) {
					traffic = tempTraffic;
					break;
				}
			}
			trafficInfo1.setText(String.valueOf(traffic.getTrafficPriority()));
			trafficInfo2.setText("<=" + String.valueOf(traffic.getJitter()) + "ms");
			trafficInfo3.setText("<=" + String.valueOf(traffic.getPacketLossRatio() * 100) + "%");
			trafficInfo4.setText("<=" + String.valueOf(traffic.getLatency()) + "ms");
		}
	}
	
	//处理“开始评估”按钮单击事件
	private void do_evaluateButton_actionPerformed(ActionEvent e) {
		if(userInfo1.getText() == "") {
			JOptionPane.showMessageDialog(null, "      请选择用户类型！", "", JOptionPane.WARNING_MESSAGE);
            return;
		}
		if(missionInfo1.getText() == "") {
			JOptionPane.showMessageDialog(null, "      请选择任务类型！", "", JOptionPane.WARNING_MESSAGE);
            return;
		}
		if(trafficInfo1.getText() == "") {
			JOptionPane.showMessageDialog(null, "      请选择业务类型！", "", JOptionPane.WARNING_MESSAGE);
            return;
		}
		//获取用户、任务、业务子优先级
		int userPriority = Integer.parseInt(userInfo1.getText());
		int missionPriority = Integer.parseInt(missionInfo1.getText());
		int trafficPriority = Integer.parseInt(trafficInfo1.getText());
		
		//业务重要性程度值
		int Importance; 
		
		Boolean twoIsSelected = twoRadioButton.isSelected();
		if(twoIsSelected == true) {
			Importance = tableBasedImportance.twoDev(userPriority, trafficPriority);
			JOptionPane.showMessageDialog(null, "二维重要性表评估得到" + "重要性程度值为：" + String.valueOf(Importance) + "        ");
		} else {
			Importance = tableBasedImportance.threeDev(userPriority, trafficPriority, missionPriority);
			JOptionPane.showMessageDialog(null, "三维重要性表评估得到" + "重要性程度值为：" + String.valueOf(Importance) + "        ");
		}
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
		
		ResultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ResultTable.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		ResultTable.setRowHeight(30);// 设置表体高度
	    JTableHeader header = ResultTable.getTableHeader();// 获得表头对象
	    header.setFont(new Font("微软雅黑", Font.PLAIN, 15));// 设置表头字体
	    header.setPreferredSize(new Dimension(header.getWidth(), 30));// 设置表头高度        
	    
	    
	    //DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();// 获得渲染器
	    //renderer.setHorizontalAlignment(SwingConstants.CENTER);// 设置表头内容居中显示
	    
	    
	    //DefaultTableModel model = (DefaultTableModel) TrafficTable.getModel();// 获得表格模型
	    model.setRowCount(0);// 清空表格中的数据
	    model.setColumnIdentifiers(new Object[] { "编号", "业务类型", "用户类型", "任务类型", "二维评估结果", "三维评估结果", "MADM结果"});
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
	    
	    
	    EvaResultOp.updateTableBased();//更新基于表的评估结果数据库
	    List<ResultBean> results = EvaResultOp.queryAll();// 获得数据库中表格的全部数据
	    for (ResultBean tempRes : results) {// 将数据加载到表格模型中
	        model.addRow(new Object[] { tempRes.getResultID(), tempRes.getTrafficTitleofResult(), tempRes.getUserTitleofResult(), tempRes.getMissionTitleofResult(), 
	        		tempRes.getTwoDevImp(), tempRes.getThreeDevImp(), tempRes.getMADMDevImp() });
	       // model.addRow(new Object[] { tempRes.getResultID(), tempRes.getTrafficTitleofResult(), tempRes.getUserTitleofResult(), tempRes.getMissionTitleofResult(), 
	        		//tempRes.getTwoDevImp(), tempRes.getThreeDevImp() });
	    }       
	    
	    //JTable表头排序
	    /*
	    RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);  
	    ResultTable.setRowSorter(sorter);
	    */
	    
	    //表头列宽随内容变化
	    //JTableFit.FitTableColumns(ResultTable);
	
	    //设置表格内容居中
	    /*
	    DefaultTableCellRenderer tempRenderer = new DefaultTableCellRenderer();// 设置table内容居中
	    // tempRenderer.setHorizontalAlignment(JLabel.CENTER);
	    tempRenderer.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
	    ResultTable.setDefaultRenderer(Object.class, tempRenderer);
	    */
	    
	    ResultTable.setModel(model);// 应用表格模型		
		
		scrollPaneResult.setViewportView(ResultTable);	
	}
	
	//创建MADM评估页面表格
	private static void createMADMTab() {
		
		DefaultTableModel model;
		model =new DefaultTableModel();
	
		AttTable = new JTable(model) {
			public boolean isCellEditable(int row, int column){
	                   return false;
	                   }//表格不允许被编辑
		};
		AttTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		AttTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		AttTable.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		AttTable.setRowHeight(30);// 设置表体高度
	    JTableHeader header = AttTable.getTableHeader();// 获得表头对象
	    header.setFont(new Font("微软雅黑", Font.PLAIN, 15));// 设置表头字体
	    //header.setPreferredSize(new Dimension(header.getWidth(), 30));// 设置表头高度
	    header.setPreferredSize(new Dimension(100000, 30));// 设置表头高度  
  
	    /*
	    DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();// 获得渲染器
	    renderer.setHorizontalAlignment(SwingConstants.CENTER);// 设置表头内容居中显示
	    */
	    
	    model.setRowCount(0);// 清空表格中的数据
	    model.setColumnIdentifiers(new Object[] { "编号", "业务类型", "用户类型", "任务类型", 
	    		"等待时延", "时延抖动", "丢包率", "用户级别", "安全策略", "带宽需求", "可靠性", "响应时间" });
	    
	    int count = 1;
		List<TrafficBean> trafResults = TrafficOp.queryAll();
		List<UserBean> userResults = UserOp.queryAll();
		List<MissionBean> missionResults = MissionOp.queryAll();
	    for (TrafficBean traf : trafResults) {
			for (UserBean user : userResults) {
				for (MissionBean mission : missionResults) {
					model.addRow(new Object[] { count++, traf.getTrafficTitle(), user.getUserTitle(), mission.getMissionTitle(),
							traf.getLatency(), traf.getJitter(), traf.getPacketLossRatio(), user.getUserClassification(),
							user.getUserSecurityPolicy(), mission.getMissionBandWidth(), mission.getMissionReliability(),
							mission.getMissionResponseTime() });
				}
			}
	    }
	    
	    //JTableFit.FitTableColumns(AttTable);
	    
	    //设置表格内容居中
	    /*
	    DefaultTableCellRenderer tempRenderer = new DefaultTableCellRenderer();// 设置table内容居中
	    // tempRenderer.setHorizontalAlignment(JLabel.CENTER);
	    tempRenderer.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
	    AttTable.setDefaultRenderer(Object.class, tempRenderer);       
	    */
	    
	    AttTable.setModel(model);// 应用表格模型		
		
		scrollPaneAtt.setViewportView(AttTable);
	}
	
	//处理判断矩阵下拉框选择项改变事件
	private void do_setLabel(ItemEvent e, JLabel jlbl) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			double doubleValue = Double.valueOf(((String)e.getItem()).trim()) * 10;
			long longValue = 10 - Math.round(doubleValue);
			jlbl.setText(String.valueOf(longValue / 10.0));
		}
	}
	
	private void do_madmEvaButton_actionPerformed(ActionEvent e) {
		double[][] jm = new double[3][3];
		for(int i = 0; i < 3; i++) {
			jm[i][i] = 0.5;
		}
		jm[1][0] = Math.round(Double.valueOf(label32.getText()) * 10) / 10.0;
		jm[2][0] = Math.round(Double.valueOf(label42.getText()) * 10) / 10.0;
		jm[2][1] = Math.round(Double.valueOf(label43.getText()) * 10) / 10.0;
		
		jm[0][1] = Double.valueOf(((String)comboBox23.getSelectedItem()).trim());
		jm[0][2] = Double.valueOf(((String)comboBox24.getSelectedItem()).trim());
		jm[1][2] = Double.valueOf(((String)comboBox34.getSelectedItem()).trim());
		
		if(!madmBasedImportance.isConsistency(jm)) {
			JOptionPane.showMessageDialog(null, "模糊判断矩阵不是满意一致性矩阵，请调整后或使用缺省值再点击评估按钮！", "", JOptionPane.WARNING_MESSAGE);
		} else {	
			madmBasedImportance.fuzzyJudgementMatrix = jm;
			
			/*
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					System.out.print(madmBasedImportance.judgementMatrix[i][j] + " ");	
				}
				System.out.println();
			}
			*/
			
			if(-1 == EvaResultOp.updateAll()) {
				JOptionPane.showMessageDialog(null, "MADM评估完成，请到业务重要性评估结果总览页面查看评估结果！", "", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private void do_defaultButton_actionPerformed(ActionEvent e) {
		//模糊判断矩阵下拉框默认选择值
		comboBox23.setSelectedIndex(2);
		comboBox24.setSelectedIndex(1);
		comboBox34.setSelectedIndex(3);
	}
}
