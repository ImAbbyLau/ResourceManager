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
		tabbedPane.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		add(tabbedPane);
				
		/*
		 * ҵ�����͹����ǩҳ
		 */		
		JPanel TrafficPanel = new JPanel();
		tabbedPane.addTab("\u4E1A\u52A1\u5C5E\u6027\u7BA1\u7406", null, TrafficPanel, null);
		tabbedPane.setEnabledAt(0, true);
		TrafficPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPaneofTraf = new JScrollPane();
		TrafficPanel.add(scrollPaneofTraf, BorderLayout.CENTER);
		
		//ˢ��ҵ����Ϣ���
		TrafficManagerPanel.refreshTrafficTable();
		
		JPanel ButtonPanelofTraf = new JPanel();
		TrafficPanel.add(ButtonPanelofTraf, BorderLayout.SOUTH);
		
		JButton addTrafButton = new JButton("\u65B0\u589E");		
		addTrafButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		//������������ť�����¼�
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
		//����ˢ�¡���ť�����¼�
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) TrafficTable.getModel();// ��ñ��ģ��
	            model.setRowCount(0);// ��ձ���е�����
	            model.setColumnIdentifiers(new Object[] { "ҵ����", "ҵ������", "�ȴ�ʱ��", "����", "������", "ҵ�������ȼ�"});
	            List<TrafficBean> results = TrafficOp.queryAll();// ������ݿ��б���ȫ������
	            for (TrafficBean tempTraf : results) {// �����ݼ��ص����ģ����
	                model.addRow(new Object[] { tempTraf.getTrafficID(), tempTraf.getTrafficTitle(), tempTraf.getLatency(), tempTraf.getJitter(), tempTraf.getPacketLossRatio(),
	                		tempTraf.getTrafficPriority() });
	            }
	            TrafficTable.setModel(model);// Ӧ�ñ��ģ��
			}
		});
		ButtonPanelofTraf.add(refreshButton);*/
	
		JButton modifyTrafButton = new JButton("\u4FEE\u6539");
		modifyTrafButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		//�����޸ġ���ť�����¼�
		modifyTrafButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int selectRow = TrafficTable.getSelectedRow();
			        if (selectRow < 0) {
			            JOptionPane.showMessageDialog(null, "��ѡ����Ҫ�޸ĵ�ҵ�����ͣ�", "", JOptionPane.WARNING_MESSAGE);
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
		deleteTrafButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		//����ɾ������ť�����¼�
		deleteTrafButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = TrafficTable.getSelectedRow();
				if (selectRow < 0) {
		            JOptionPane.showMessageDialog(null, "��ѡ����Ҫɾ����ҵ�����ͣ�", "", JOptionPane.WARNING_MESSAGE);
		            return;
		        } else {
		        	final TrafficBean traf = new TrafficBean();
		        	traf.setTrafficID((Integer) TrafficTable.getValueAt(selectRow, 0));
		        	TrafficOp.delete(traf);
		        	
		        	//ˢ��ҵ����Ϣ���
		        	refreshTrafficTable();
		        }
			}
		});
		ButtonPanelofTraf.add(deleteTrafButton);
		
		/*
		 * �û����͹����ǩҳ
		 */
		JPanel UserPanel = new JPanel();
		tabbedPane.addTab("\u7528\u6237\u5C5E\u6027\u7BA1\u7406", null, UserPanel, null);
		UserPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPaneofUser = new JScrollPane();
		UserPanel.add(scrollPaneofUser, BorderLayout.CENTER);
		
		//ˢ���û���Ϣ���
		refreshUserTable();
		
		JPanel ButtonPanelofUser = new JPanel();
		UserPanel.add(ButtonPanelofUser, BorderLayout.SOUTH);
		
		JButton addUserButton = new JButton("\u65B0\u589E");
		addUserButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		//������������ť�����¼�
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
		modifyUserButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		//�����޸ġ���ť�����¼�
		modifyUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = UserTable.getSelectedRow();
		        if (selectRow < 0) {
		            JOptionPane.showMessageDialog(null, "��ѡ����Ҫ�޸ĵ��û����ͣ�", "", JOptionPane.WARNING_MESSAGE);
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
		deleteUserButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		//����ɾ������ť�����¼�
		deleteUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = UserTable.getSelectedRow();
				if (selectRow < 0) {
		            JOptionPane.showMessageDialog(null, "��ѡ����Ҫɾ�����û����ͣ�", "", JOptionPane.WARNING_MESSAGE);
		            return;
		        } else {
		        	final UserBean user = new UserBean();
		        	user.setUserID((Integer) UserTable.getValueAt(selectRow, 0));
		        	UserOp.delete(user);
		        	
		        	//ˢ���û���Ϣ���
		    		refreshUserTable();
		        }
			}
		});
		ButtonPanelofUser.add(deleteUserButton);

		/*
		 * �������͹����ǩҳ
		 */
		JPanel MissionPanel = new JPanel();
		tabbedPane.addTab("\u4EFB\u52A1\u5C5E\u6027\u7BA1\u7406", null, MissionPanel, null);
		MissionPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPaneofMission = new JScrollPane();
		MissionPanel.add(scrollPaneofMission, BorderLayout.CENTER);
		
		//ˢ��������Ϣ���
		refreshMissionTable();
		
		JPanel ButtonPanelofMission = new JPanel();
		MissionPanel.add(ButtonPanelofMission, BorderLayout.SOUTH);
		
		/*
		JButton refreshButton3 = new JButton("\u5237\u65B0");
		//����ˢ�¡���ť�����¼�
		refreshButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) MissionTable.getModel();// ��ñ��ģ��
	            model.setRowCount(0);// ��ձ���е�����
	            model.setColumnIdentifiers(new Object[] { "������", "��������", "����Ҫ��", "�ɿ���", "��Ӧʱ��", "���������ȼ�"});
	            List<MissionBean> results = MissionOp.queryAll();// ������ݿ��б���ȫ������
	            for (MissionBean tempMission : results) {// �����ݼ��ص����ģ����
	                model.addRow(new Object[] { tempMission.getMissionID(), tempMission.getMissionTitle(), tempMission.getMissionBandWidth(), 
	                		tempMission.getMissionReliability(), tempMission.getMissionResponseTime(), tempMission.getMissionPriority() });
	            }
	            MissionTable.setModel(model);// Ӧ�ñ��ģ��				
			}
		});
		ButtonPanelofMission.add(refreshButton3);*/
		
		JButton addMissionButton = new JButton("\u65B0\u589E");
		addMissionButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		//������������ť�����¼�
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
		modifyMissionButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		//�����޸ġ���ť�����¼�
		modifyMissionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = MissionTable.getSelectedRow();
		        if (selectRow < 0) {
		            JOptionPane.showMessageDialog(null, "��ѡ����Ҫ�޸ĵ�ҵ�����ͣ�", "", JOptionPane.WARNING_MESSAGE);
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
		deleteMissionButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		//����ɾ������ť�����¼�
		deleteMissionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = MissionTable.getSelectedRow();
				if (selectRow < 0) {
		            JOptionPane.showMessageDialog(null, "��ѡ����Ҫɾ�����������ͣ�", "", JOptionPane.WARNING_MESSAGE);
		            return;
		        } else {
		        	final MissionBean mission = new MissionBean();
		        	mission.setMissionID((Integer) MissionTable.getValueAt(selectRow, 0));
		        	MissionOp.delete(mission);
		        	
		        	//ˢ��������Ϣ���
		    		refreshMissionTable();
		        }
			}
		});
		ButtonPanelofMission.add(deleteMissionButton);
		
		//����TabĬ��ѡ����
		tabbedPane.setSelectedIndex(selectedTab);
	}
	
	static void refreshTrafficTable () {
		DefaultTableModel model;
		model =new DefaultTableModel();
		TrafficTable = new JTable(model) {
			public boolean isCellEditable(int row, int column){
                       return false;
                       }//��������༭
		};
		
		TrafficTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TrafficTable.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		TrafficTable.setRowHeight(30);// ���ñ���߶�
        JTableHeader header = TrafficTable.getTableHeader();// ��ñ�ͷ����
        header.setFont(new Font("΢���ź�", Font.PLAIN, 15));// ���ñ�ͷ����
        header.setPreferredSize(new Dimension(header.getWidth(), 30));// ���ñ�ͷ�߶�        
        
        /*
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();// �����Ⱦ��
        renderer.setHorizontalAlignment(SwingConstants.CENTER);// ���ñ�ͷ���ݾ�����ʾ
        */
        
        //DefaultTableModel model = (DefaultTableModel) TrafficTable.getModel();// ��ñ��ģ��
        model.setRowCount(0);// ��ձ���е�����
        model.setColumnIdentifiers(new Object[] { "ҵ����", "ҵ������", "�ȴ�ʱ��", "����", "������", "ҵ�������ȼ�"});
        List<TrafficBean> results = TrafficOp.queryAll();// ������ݿ��б���ȫ������
        for (TrafficBean traf : results) {// �����ݼ��ص����ģ����
            model.addRow(new Object[] { traf.getTrafficID(), traf.getTrafficTitle(), traf.getLatency(), traf.getJitter(), traf.getPacketLossRatio(),
            		traf.getTrafficPriority() });
        }
        
        //���ñ�����ݾ���
        /*
        DefaultTableCellRenderer tempRenderer = new DefaultTableCellRenderer();// ����table���ݾ���
        // tempRenderer.setHorizontalAlignment(JLabel.CENTER);
        tempRenderer.setHorizontalAlignment(SwingConstants.CENTER);// �����Ͼ�����һ��
        TrafficTable.setDefaultRenderer(Object.class, tempRenderer);
        */
        
        
        TrafficTable.setModel(model);// Ӧ�ñ��ģ��
		
		scrollPaneofTraf.setViewportView(TrafficTable);
	}
	
	static void refreshUserTable () {
		DefaultTableModel model;
		model =new DefaultTableModel();
		UserTable = new JTable(model) {
			public boolean isCellEditable(int row, int column){
                       return false;
                       }//��������༭
		};
		
		//UserTable = new JTable();
		UserTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		UserTable.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		UserTable.setRowHeight(30);// ���ñ���߶�
        JTableHeader header = UserTable.getTableHeader();// ��ñ�ͷ����
        header.setFont(new Font("΢���ź�", Font.PLAIN, 15));// ���ñ�ͷ����
        header.setPreferredSize(new Dimension(header.getWidth(), 30));// ���ñ�ͷ�߶�
        
        /*
        DefaultTableCellRenderer renderer2 = (DefaultTableCellRenderer) header2.getDefaultRenderer();// �����Ⱦ��
        renderer2.setHorizontalAlignment(SwingConstants.CENTER);// ���ñ�ͷ���ݾ�����ʾ
        */
        
        //DefaultTableModel model = (DefaultTableModel) UserTable.getModel();// ��ñ��ģ��
        model.setRowCount(0);// ��ձ���е�����
        model.setColumnIdentifiers(new Object[] { "�û����", "�û�����", "�û�����", "��ȫ���Եȼ�",  "�û������ȼ�"});
        List<UserBean> results = UserOp.queryAll();// ������ݿ��б���ȫ������
        for (UserBean user : results) {// �����ݼ��ص����ģ����
            model.addRow(new Object[] { user.getUserID(), user.getUserTitle(), user.getUserClassification(), user.getUserSecurityPolicy(), user.getUserPriority(),
            		 });
        }
        
        //���ñ�����ݾ���
        /*
        UserTable.setDefaultRenderer(Object.class, tempRenderer);
        */
        
        UserTable.setModel(model);// Ӧ�ñ��ģ��
        
        scrollPaneofUser.setViewportView(UserTable);
	}
	
	static void refreshMissionTable () {
		DefaultTableModel model;
		model =new DefaultTableModel();
		MissionTable = new JTable(model) {
			public boolean isCellEditable(int row, int column){
                       return false;
                       }//��������༭
		};
		
		//MissionTable = new JTable();
		MissionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		MissionTable.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		MissionTable.setRowHeight(30);// ���ñ���߶�
        JTableHeader header = MissionTable.getTableHeader();// ��ñ�ͷ����
        header.setFont(new Font("΢���ź�", Font.PLAIN, 15));// ���ñ�ͷ����
        header.setPreferredSize(new Dimension(header.getWidth(), 30));// ���ñ�ͷ�߶�
        
        /*
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();// �����Ⱦ��
        renderer3.setHorizontalAlignment(SwingConstants.CENTER);// ���ñ�ͷ���ݾ�����ʾ
        */
        
        //DefaultTableModel model = (DefaultTableModel) MissionTable.getModel();// ��ñ��ģ��
        model.setRowCount(0);// ��ձ���е�����
        model.setColumnIdentifiers(new Object[] { "������", "��������", "����Ҫ��", "�ɿ���",  "��Ӧʱ��", "���������ȼ�"});
        List<MissionBean> results = MissionOp.queryAll();// ������ݿ��б���ȫ������
        for (MissionBean mission : results) {// �����ݼ��ص����ģ����
            model.addRow(new Object[] { mission.getMissionID(), mission.getMissionTitle(), mission.getMissionBandWidth(), mission.getMissionReliability(), 
            		mission.getMissionResponseTime(), mission.getMissionPriority() });
        }
        
        //���ñ�����ݾ���
        /*
        MissionTable.setDefaultRenderer(Object.class, tempRenderer);
        */
        
        MissionTable.setModel(model);// Ӧ�ñ��ģ��
        
        scrollPaneofMission.setViewportView(MissionTable);
	}

}
