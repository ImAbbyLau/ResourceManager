package com.zac.frame;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JTable;
import java.awt.Font;
import java.util.List;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;


import com.zac.bean.*;
import com.zac.sql.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TrafficManagerPanel extends JPanel {
	private static JTable TrafficTable;
	private static JTable UserTable;
	private static JTable MissionTable;

	private static JScrollPane scrollPaneofTraf;
	private static JScrollPane scrollPaneofUser;
	private static JScrollPane scrollPaneofMission;
	/**
	 * Create the panel.
	 */
	public TrafficManagerPanel(int selectedTab) {
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		add(tabbedPane);
				
		/*
		 * 业务类型管理标签页
		 */		
		JPanel TrafficPanel = new JPanel();
		tabbedPane.addTab("\u4E1A\u52A1\u5C5E\u6027\u7BA1\u7406", null, TrafficPanel, null);
		tabbedPane.setEnabledAt(0, true);
		TrafficPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPaneofTraf = new JScrollPane();
		TrafficPanel.add(scrollPaneofTraf, BorderLayout.CENTER);
		
		//刷新业务信息表格
		TrafficManagerPanel.refreshTrafficTable();
		
		JPanel ButtonPanelofTraf = new JPanel();
		TrafficPanel.add(ButtonPanelofTraf, BorderLayout.SOUTH);
		
		JButton addTrafButton = new JButton("\u65B0\u589E");		
		addTrafButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		//处理“新增”按钮单击事件
		addTrafButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                try {
		                    TrafficAddFrame frame = new TrafficAddFrame();
		                    frame.setVisible(true);
		                } catch (Exception e) {
		                    e.printStackTrace();
		                }
		            }
		        });
			}
		});
		ButtonPanelofTraf.add(addTrafButton);
		
		/*
		JButton refreshButton = new JButton("\u5237\u65B0");
		//处理“刷新”按钮单击事件
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) TrafficTable.getModel();// 获得表格模型
	            model.setRowCount(0);// 清空表格中的数据
	            model.setColumnIdentifiers(new Object[] { "业务编号", "业务类型", "等待时延", "抖动", "丢包率", "业务子优先级"});
	            List<TrafficBean> results = TrafficOp.queryAll();// 获得数据库中表格的全部数据
	            for (TrafficBean tempTraf : results) {// 将数据加载到表格模型中
	                model.addRow(new Object[] { tempTraf.getTrafficID(), tempTraf.getTrafficTitle(), tempTraf.getLatency(), tempTraf.getJitter(), tempTraf.getPacketLossRatio(),
	                		tempTraf.getTrafficPriority() });
	            }
	            TrafficTable.setModel(model);// 应用表格模型
			}
		});
		ButtonPanelofTraf.add(refreshButton);*/
	
		JButton modifyTrafButton = new JButton("\u4FEE\u6539");
		modifyTrafButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		//处理“修改”按钮单击事件
		modifyTrafButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int selectRow = TrafficTable.getSelectedRow();
			        if (selectRow < 0) {
			            JOptionPane.showMessageDialog(null, "请选择需要修改的业务类型！", "", JOptionPane.WARNING_MESSAGE);
			            return;
			        } else {//Double.parseDouble
			            final TrafficBean traf = new TrafficBean();
			            //traf = TrafficOp.query((Integer) TrafficTable.getValueAt(selectRow, 0));
			            
			            traf.setTrafficID((Integer) TrafficTable.getValueAt(selectRow, 0));
			            traf.setTrafficTitle((String) TrafficTable.getValueAt(selectRow, 1));
			            traf.setLatency((Double) TrafficTable.getValueAt(selectRow, 2));
			            traf.setJitter((Double) TrafficTable.getValueAt(selectRow, 3));
			            traf.setPacketLossRatio((Double) TrafficTable.getValueAt(selectRow, 4));
			            traf.setTrafficPriority((Integer) TrafficTable.getValueAt(selectRow, 5));
			            
			            EventQueue.invokeLater(new Runnable() {
			                @Override
			                public void run() {
			                    try {
			                        TrafficModifyFrame frame = new TrafficModifyFrame(traf);
			                        frame.setVisible(true);
			                    } catch (Exception e) {
			                        e.printStackTrace();
			                    }
			                }
			            });
			        }
			}
		});
		ButtonPanelofTraf.add(modifyTrafButton);
		
		JButton deleteTrafButton = new JButton("\u5220\u9664");
		deleteTrafButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		//处理“删除”按钮单击事件
		deleteTrafButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = TrafficTable.getSelectedRow();
				if (selectRow < 0) {
		            JOptionPane.showMessageDialog(null, "请选择需要删除的业务类型！", "", JOptionPane.WARNING_MESSAGE);
		            return;
		        } else {
		        	final TrafficBean traf = new TrafficBean();
		        	traf.setTrafficID((Integer) TrafficTable.getValueAt(selectRow, 0));
		        	TrafficOp.delete(traf);
		        	
		        	//刷新业务信息表格
		        	refreshTrafficTable();
		        }
			}
		});
		ButtonPanelofTraf.add(deleteTrafButton);
		
		/*
		 * 用户类型管理标签页
		 */
		JPanel UserPanel = new JPanel();
		tabbedPane.addTab("\u7528\u6237\u5C5E\u6027\u7BA1\u7406", null, UserPanel, null);
		UserPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPaneofUser = new JScrollPane();
		UserPanel.add(scrollPaneofUser, BorderLayout.CENTER);
		
		//刷新用户信息表格
		refreshUserTable();
		
		JPanel ButtonPanelofUser = new JPanel();
		UserPanel.add(ButtonPanelofUser, BorderLayout.SOUTH);
		
		JButton addUserButton = new JButton("\u65B0\u589E");
		addUserButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		//处理“新增”按钮单击事件
		addUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                try {
		                    UserAddFrame frame = new UserAddFrame();
		                    frame.setVisible(true);
		                } catch (Exception e) {
		                    e.printStackTrace();
		                }
		            }
		        });
			}
		});
		ButtonPanelofUser.add(addUserButton);
		
		JButton modifyUserButton = new JButton("\u4FEE\u6539");
		modifyUserButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		//处理“修改”按钮单击事件
		modifyUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = UserTable.getSelectedRow();
		        if (selectRow < 0) {
		            JOptionPane.showMessageDialog(null, "请选择需要修改的用户类型！", "", JOptionPane.WARNING_MESSAGE);
		            return;
		        } else {//Double.parseDouble
		            final UserBean user = new UserBean();
		            //traf = TrafficOp.query((Integer) TrafficTable.getValueAt(selectRow, 0));
		            
		            user.setUserID((Integer) UserTable.getValueAt(selectRow, 0));
		            user.setUserTitle((String) UserTable.getValueAt(selectRow, 1));
		            user.setUserClassification((Integer) UserTable.getValueAt(selectRow, 2));
		            user.setUserSecurityPolicy((Integer) UserTable.getValueAt(selectRow, 3));		      
		            user.setUserPriority((Integer) UserTable.getValueAt(selectRow, 4));
		            
		            EventQueue.invokeLater(new Runnable() {
		                @Override
		                public void run() {
		                    try {
		                    	UserModifyFrame frame = new UserModifyFrame(user);
		                        frame.setVisible(true);
		                    } catch (Exception e) {
		                        e.printStackTrace();
		                    }
		                }
		            });
		        }
			}
		});
		ButtonPanelofUser.add(modifyUserButton);
		
		JButton deleteUserButton = new JButton("\u5220\u9664");
		deleteUserButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		//处理“删除”按钮单击事件
		deleteUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = UserTable.getSelectedRow();
				if (selectRow < 0) {
		            JOptionPane.showMessageDialog(null, "请选择需要删除的用户类型！", "", JOptionPane.WARNING_MESSAGE);
		            return;
		        } else {
		        	final UserBean user = new UserBean();
		        	user.setUserID((Integer) UserTable.getValueAt(selectRow, 0));
		        	UserOp.delete(user);
		        	
		        	//刷新用户信息表格
		    		refreshUserTable();
		        }
			}
		});
		ButtonPanelofUser.add(deleteUserButton);

		/*
		 * 任务类型管理标签页
		 */
		JPanel MissionPanel = new JPanel();
		tabbedPane.addTab("\u4EFB\u52A1\u5C5E\u6027\u7BA1\u7406", null, MissionPanel, null);
		MissionPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPaneofMission = new JScrollPane();
		MissionPanel.add(scrollPaneofMission, BorderLayout.CENTER);
		
		//刷新任务信息表格
		refreshMissionTable();
		
		JPanel ButtonPanelofMission = new JPanel();
		MissionPanel.add(ButtonPanelofMission, BorderLayout.SOUTH);
		
		/*
		JButton refreshButton3 = new JButton("\u5237\u65B0");
		//处理“刷新”按钮单击事件
		refreshButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) MissionTable.getModel();// 获得表格模型
	            model.setRowCount(0);// 清空表格中的数据
	            model.setColumnIdentifiers(new Object[] { "任务编号", "任务名称", "带宽要求", "可靠性", "响应时间", "任务子优先级"});
	            List<MissionBean> results = MissionOp.queryAll();// 获得数据库中表格的全部数据
	            for (MissionBean tempMission : results) {// 将数据加载到表格模型中
	                model.addRow(new Object[] { tempMission.getMissionID(), tempMission.getMissionTitle(), tempMission.getMissionBandWidth(), 
	                		tempMission.getMissionReliability(), tempMission.getMissionResponseTime(), tempMission.getMissionPriority() });
	            }
	            MissionTable.setModel(model);// 应用表格模型				
			}
		});
		ButtonPanelofMission.add(refreshButton3);*/
		
		JButton addMissionButton = new JButton("\u65B0\u589E");
		addMissionButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		//处理“新增”按钮单击事件
		addMissionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                try {
		                    MissionAddFrame frame = new MissionAddFrame();
		                    frame.setVisible(true);
		                } catch (Exception e) {
		                    e.printStackTrace();
		                }
		            }
		        });
			}
		});
		ButtonPanelofMission.add(addMissionButton);
		
		JButton modifyMissionButton = new JButton("\u4FEE\u6539");
		modifyMissionButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		//处理“修改”按钮单击事件
		modifyMissionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = MissionTable.getSelectedRow();
		        if (selectRow < 0) {
		            JOptionPane.showMessageDialog(null, "请选择需要修改的业务类型！", "", JOptionPane.WARNING_MESSAGE);
		            return;
		        } else {//Double.parseDouble
		            final MissionBean mission = new MissionBean();
		            //traf = TrafficOp.query((Integer) TrafficTable.getValueAt(selectRow, 0));
		            
		            mission.setMissionID((Integer) MissionTable.getValueAt(selectRow, 0));
		            mission.setMissionTitle((String) MissionTable.getValueAt(selectRow, 1));
		            mission.setMissionBandWidth((Double) MissionTable.getValueAt(selectRow, 2));
		            mission.setMissionReliability((Double) MissionTable.getValueAt(selectRow, 3));
		            mission.setMissionResponseTime((Double) MissionTable.getValueAt(selectRow, 4));
		            mission.setMissionPriority((Integer) MissionTable.getValueAt(selectRow, 5));
		            
		            EventQueue.invokeLater(new Runnable() {
		                @Override
		                public void run() {
		                    try {
		                        MissionModifyFrame frame = new MissionModifyFrame(mission);
		                        frame.setVisible(true);
		                    } catch (Exception e) {
		                        e.printStackTrace();
		                    }
		                }
		            });
		        }
			}
		});
		ButtonPanelofMission.add(modifyMissionButton);
		
		JButton deleteMissionButton = new JButton("\u5220\u9664");
		deleteMissionButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		//处理“删除”按钮单击事件
		deleteMissionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = MissionTable.getSelectedRow();
				if (selectRow < 0) {
		            JOptionPane.showMessageDialog(null, "请选择需要删除的任务类型！", "", JOptionPane.WARNING_MESSAGE);
		            return;
		        } else {
		        	final MissionBean mission = new MissionBean();
		        	mission.setMissionID((Integer) MissionTable.getValueAt(selectRow, 0));
		        	MissionOp.delete(mission);
		        	
		        	//刷新任务信息表格
		    		refreshMissionTable();
		        }
			}
		});
		ButtonPanelofMission.add(deleteMissionButton);
		
		//设置Tab默认选择项
		tabbedPane.setSelectedIndex(selectedTab);
	}
	
	static void refreshTrafficTable () {
		DefaultTableModel model;
		model =new DefaultTableModel();
		TrafficTable = new JTable(model) {
			public boolean isCellEditable(int row, int column){
                       return false;
                       }//表格不允许被编辑
		};
		
		TrafficTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TrafficTable.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		TrafficTable.setRowHeight(30);// 设置表体高度
        JTableHeader header = TrafficTable.getTableHeader();// 获得表头对象
        header.setFont(new Font("微软雅黑", Font.PLAIN, 15));// 设置表头字体
        header.setPreferredSize(new Dimension(header.getWidth(), 30));// 设置表头高度        
        
        /*
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();// 获得渲染器
        renderer.setHorizontalAlignment(SwingConstants.CENTER);// 设置表头内容居中显示
        */
        
        //DefaultTableModel model = (DefaultTableModel) TrafficTable.getModel();// 获得表格模型
        model.setRowCount(0);// 清空表格中的数据
        model.setColumnIdentifiers(new Object[] { "业务编号", "业务类型", "等待时延", "抖动", "丢包率", "业务子优先级"});
        List<TrafficBean> results = TrafficOp.queryAll();// 获得数据库中表格的全部数据
        for (TrafficBean traf : results) {// 将数据加载到表格模型中
            model.addRow(new Object[] { traf.getTrafficID(), traf.getTrafficTitle(), traf.getLatency(), traf.getJitter(), traf.getPacketLossRatio(),
            		traf.getTrafficPriority() });
        }
        
        //设置表格内容居中
        /*
        DefaultTableCellRenderer tempRenderer = new DefaultTableCellRenderer();// 设置table内容居中
        // tempRenderer.setHorizontalAlignment(JLabel.CENTER);
        tempRenderer.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
        TrafficTable.setDefaultRenderer(Object.class, tempRenderer);
        */
        
        
        TrafficTable.setModel(model);// 应用表格模型
		
		scrollPaneofTraf.setViewportView(TrafficTable);
	}
	
	static void refreshUserTable () {
		DefaultTableModel model;
		model =new DefaultTableModel();
		UserTable = new JTable(model) {
			public boolean isCellEditable(int row, int column){
                       return false;
                       }//表格不允许被编辑
		};
		
		//UserTable = new JTable();
		UserTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		UserTable.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		UserTable.setRowHeight(30);// 设置表体高度
        JTableHeader header = UserTable.getTableHeader();// 获得表头对象
        header.setFont(new Font("微软雅黑", Font.PLAIN, 15));// 设置表头字体
        header.setPreferredSize(new Dimension(header.getWidth(), 30));// 设置表头高度
        
        /*
        DefaultTableCellRenderer renderer2 = (DefaultTableCellRenderer) header2.getDefaultRenderer();// 获得渲染器
        renderer2.setHorizontalAlignment(SwingConstants.CENTER);// 设置表头内容居中显示
        */
        
        //DefaultTableModel model = (DefaultTableModel) UserTable.getModel();// 获得表格模型
        model.setRowCount(0);// 清空表格中的数据
        model.setColumnIdentifiers(new Object[] { "用户编号", "用户名称", "用户级别", "安全策略等级",  "用户子优先级"});
        List<UserBean> results = UserOp.queryAll();// 获得数据库中表格的全部数据
        for (UserBean user : results) {// 将数据加载到表格模型中
            model.addRow(new Object[] { user.getUserID(), user.getUserTitle(), user.getUserClassification(), user.getUserSecurityPolicy(), user.getUserPriority(),
            		 });
        }
        
        //设置表格内容居中
        /*
        UserTable.setDefaultRenderer(Object.class, tempRenderer);
        */
        
        UserTable.setModel(model);// 应用表格模型
        
        scrollPaneofUser.setViewportView(UserTable);
	}
	
	static void refreshMissionTable () {
		DefaultTableModel model;
		model =new DefaultTableModel();
		MissionTable = new JTable(model) {
			public boolean isCellEditable(int row, int column){
                       return false;
                       }//表格不允许被编辑
		};
		
		//MissionTable = new JTable();
		MissionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		MissionTable.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		MissionTable.setRowHeight(30);// 设置表体高度
        JTableHeader header = MissionTable.getTableHeader();// 获得表头对象
        header.setFont(new Font("微软雅黑", Font.PLAIN, 15));// 设置表头字体
        header.setPreferredSize(new Dimension(header.getWidth(), 30));// 设置表头高度
        
        /*
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();// 获得渲染器
        renderer3.setHorizontalAlignment(SwingConstants.CENTER);// 设置表头内容居中显示
        */
        
        //DefaultTableModel model = (DefaultTableModel) MissionTable.getModel();// 获得表格模型
        model.setRowCount(0);// 清空表格中的数据
        model.setColumnIdentifiers(new Object[] { "任务编号", "任务名称", "带宽要求", "可靠性",  "响应时间", "任务子优先级"});
        List<MissionBean> results = MissionOp.queryAll();// 获得数据库中表格的全部数据
        for (MissionBean mission : results) {// 将数据加载到表格模型中
            model.addRow(new Object[] { mission.getMissionID(), mission.getMissionTitle(), mission.getMissionBandWidth(), mission.getMissionReliability(), 
            		mission.getMissionResponseTime(), mission.getMissionPriority() });
        }
        
        //设置表格内容居中
        /*
        MissionTable.setDefaultRenderer(Object.class, tempRenderer);
        */
        
        MissionTable.setModel(model);// 应用表格模型
        
        scrollPaneofMission.setViewportView(MissionTable);
	}

}
