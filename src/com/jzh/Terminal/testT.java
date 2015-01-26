package com.jzh.Terminal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;




import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class testT extends JFrame {

	private static JPanel contentPane;
	private static JTextField textField_LRRMIP;
	private static JTextField textField_Delay;
	private static JTextField textField_Jitter;
	private static JTextField textField_PacketLoss;
	private static JTextField textField_BandWidth;
	private static JTextField textField_TrafficImportance;
	private static JTextField textField_VisibleNet;
	private static JComboBox comboBox_TrafficType;
	private static JComboBox comboBox_Preference;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TerRequestBean r = new TerRequestBean();
					testT frame = new testT();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public testT() {
		setResizable(false);
		setTitle("\u65B0\u589E\u4E1A\u52A1\u8BF7\u6C42");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 515);		
		setLocation(Util.getLocation(getSize()) );
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel BasicInfoPanel = new JPanel();
		BasicInfoPanel.setBounds(0, 0, 714, 210);
		contentPane.add(BasicInfoPanel);
		BasicInfoPanel.setLayout(null);
		
		JLabel label_LRRMIP = new JLabel("LRRM\u5730\u5740:");
		label_LRRMIP.setToolTipText("\u5982192.168.1.1");
		label_LRRMIP.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label_LRRMIP.setBounds(40, 56, 100, 30);
		BasicInfoPanel.add(label_LRRMIP);
		
		textField_LRRMIP = new JTextField();
		textField_LRRMIP.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_LRRMIP.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_LRRMIP.setBounds(170, 56, 120, 30);
		BasicInfoPanel.add(textField_LRRMIP);
		textField_LRRMIP.setColumns(10);
		
		JLabel titleLbl_Traffic = new JLabel("\u2014\u4E1A\u52A1\u4FE1\u606F\u2014");
		titleLbl_Traffic.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		titleLbl_Traffic.setBounds(10, 11, 100, 30);
		BasicInfoPanel.add(titleLbl_Traffic);
		
		JLabel label_TrafficImoprtance = new JLabel("\u4E1A\u52A1\u91CD\u8981\u6027:");
		label_TrafficImoprtance.setToolTipText("\u5982192.168.1.1");
		label_TrafficImoprtance.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label_TrafficImoprtance.setBounds(40, 117, 120, 30);
		BasicInfoPanel.add(label_TrafficImoprtance);
		
		textField_TrafficImportance = new JTextField();
		textField_TrafficImportance.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_TrafficImportance.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_TrafficImportance.setColumns(10);
		textField_TrafficImportance.setBounds(170, 117, 120, 30);
		BasicInfoPanel.add(textField_TrafficImportance);
		
		JLabel label_TrafficType = new JLabel("\u4E1A\u52A1\u7C7B\u578B:");
		label_TrafficType.setToolTipText("\u5982192.168.1.1");
		label_TrafficType.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label_TrafficType.setBounds(386, 56, 99, 30);
		BasicInfoPanel.add(label_TrafficType);
		
		JLabel label_Preference = new JLabel("\u504F\u597D\u7F51\u7EDC\u7C7B\u578B:");
		label_Preference.setToolTipText("\u5982192.168.1.1");
		label_Preference.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label_Preference.setBounds(386, 117, 99, 30);
		BasicInfoPanel.add(label_Preference);
		
		JLabel label_VisibleNet = new JLabel("\u53EF\u7528\u7F51\u7EDC:");
		label_VisibleNet.setToolTipText("\u5982192.168.1.1");
		label_VisibleNet.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label_VisibleNet.setBounds(40, 169, 120, 30);
		BasicInfoPanel.add(label_VisibleNet);
		
		textField_VisibleNet = new JTextField();
		textField_VisibleNet.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_VisibleNet.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_VisibleNet.setColumns(10);
		textField_VisibleNet.setBounds(170, 169, 120, 30);
		BasicInfoPanel.add(textField_VisibleNet);
		
		comboBox_TrafficType = new JComboBox();
		comboBox_TrafficType.setModel(new DefaultComboBoxModel(new String[] {"                 1", "                 2", "                 3", "                 4"}));
		comboBox_TrafficType.setBounds(503, 56, 120, 27);
		BasicInfoPanel.add(comboBox_TrafficType);
		
		comboBox_Preference = new JComboBox();
		comboBox_Preference.setModel(new DefaultComboBoxModel(new String[] {"                 1", "                 2", "                 3", "                 4", "                 5", "                 6"}));
		comboBox_Preference.setBounds(503, 117, 120, 27);
		BasicInfoPanel.add(comboBox_Preference);
		
		JPanel QosParameterPanel = new JPanel();
		QosParameterPanel.setBounds(0, 210, 714, 170);
		contentPane.add(QosParameterPanel);
		QosParameterPanel.setLayout(null);
		
		JLabel titleLbl_QoS = new JLabel("\u2014\u4E1A\u52A1QoS\u9700\u6C42\u53C2\u6570\u2014");
		titleLbl_QoS.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		titleLbl_QoS.setBounds(10, 4, 158, 30);
		QosParameterPanel.add(titleLbl_QoS);
		
		JLabel label_Delay = new JLabel("\u65F6   \u5EF6:");
		label_Delay.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label_Delay.setBounds(424, 45, 49, 30);
		QosParameterPanel.add(label_Delay);
		
		textField_Delay = new JTextField();
		textField_Delay.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_Delay.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_Delay.setColumns(10);
		textField_Delay.setBounds(507, 45, 120, 30);
		QosParameterPanel.add(textField_Delay);
		
		JLabel label_Jitter = new JLabel("\u6296   \u52A8:");
		label_Jitter.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label_Jitter.setBounds(40, 105, 49, 30);
		QosParameterPanel.add(label_Jitter);
		
		textField_Jitter = new JTextField();
		textField_Jitter.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_Jitter.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_Jitter.setColumns(10);
		textField_Jitter.setBounds(100, 105, 120, 30);
		QosParameterPanel.add(textField_Jitter);
		
		JLabel label_PacketLoss = new JLabel("\u4E22\u5305\u7387:");
		label_PacketLoss.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label_PacketLoss.setBounds(424, 105, 49, 30);
		QosParameterPanel.add(label_PacketLoss);
		
		textField_PacketLoss = new JTextField();
		textField_PacketLoss.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_PacketLoss.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_PacketLoss.setColumns(10);
		textField_PacketLoss.setBounds(507, 105, 120, 30);
		QosParameterPanel.add(textField_PacketLoss);
		
		JLabel lblMs = new JLabel("ms");
		lblMs.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblMs.setBounds(637, 45, 34, 30);
		QosParameterPanel.add(lblMs);
		
		JLabel label = new JLabel("%");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label.setBounds(637, 105, 34, 30);
		QosParameterPanel.add(label);
		
		JLabel label_BandWidth = new JLabel("\u5E26   \u5BBD:");
		label_BandWidth.setBounds(40, 45, 85, 30);
		QosParameterPanel.add(label_BandWidth);
		label_BandWidth.setToolTipText("\u5982192.168.1.1");
		label_BandWidth.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		
		textField_BandWidth = new JTextField();
		textField_BandWidth.setBounds(100, 45, 120, 30);
		QosParameterPanel.add(textField_BandWidth);
		textField_BandWidth.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_BandWidth.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textField_BandWidth.setColumns(10);
		
		JLabel lblKbps = new JLabel("kbps");
		lblKbps.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblKbps.setBounds(230, 45, 40, 30);
		QosParameterPanel.add(lblKbps);
		
		JLabel label_4 = new JLabel("ms");
		label_4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label_4.setBounds(230, 105, 34, 30);
		QosParameterPanel.add(label_4);
		
		/*
		JButton prioComputeButton = new JButton("\u8BC4\u4F30");
		prioComputeButton.setBounds(44, 45, 65, 25);
		SubPrioritypanel.add(prioComputeButton);
		*/
		
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setBounds(357, 378, 357, 109);
		contentPane.add(ButtonPanel);
		
		JButton saveButton = new JButton("\u63D0\u4EA4");
		saveButton.setBounds(92, 44, 65, 25);
		// 处理“提交”按钮单击事件
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				do_submit_action(e);
				
			}
		});
		ButtonPanel.setLayout(null);
		saveButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		ButtonPanel.add(saveButton);
		
		JButton emptyButton = new JButton("\u6E05\u7A7A");
		emptyButton.setBounds(167, 44, 65, 25);
		emptyButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		// 处理“清空”按钮单击事件
		emptyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_LRRMIP.setText("");
				comboBox_TrafficType.setSelectedIndex(-1);
				textField_TrafficImportance.setText("");
				comboBox_Preference.setSelectedIndex(-1);
				textField_VisibleNet.setText("");
				textField_BandWidth.setText("");
				textField_Delay.setText("");
				textField_Jitter.setText("");
				textField_PacketLoss.setText("");
			}
		});
		ButtonPanel.add(emptyButton);
		
		JButton backButton = new JButton("\u8FD4\u56DE");
		backButton.setBounds(242, 44, 65, 25);
		// 处理“返回”按钮单击事件
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		backButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		ButtonPanel.add(backButton);
	}
	
	static void do_submit_action(ActionEvent e) {
		System.out.println("12345");
		String IP = textField_LRRMIP.getText().trim();
		if (IP.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "cdscdvdfIP地址不能为空！", "", JOptionPane.WARNING_MESSAGE);
		    return;
		}
		
		//String str[] = strLineNet.split(":");
		String[] IP1 = IP.split("\\.");
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
		if ((Integer.valueOf(PacketLoss) > 100 || Integer.valueOf(PacketLoss) < 0 )) {
		    JOptionPane.showMessageDialog(null, "业务丢包率必须在0到100之间！", "", JOptionPane.WARNING_MESSAGE);
		    return;
		}
		
		
		
		
		TerRequestBean r = new TerRequestBean();
		
		r.setLRRMIP(textField_LRRMIP.getText());
		r.setTrafficType(Integer.valueOf(((String)comboBox_TrafficType.getSelectedItem()).trim()));
		int test =Integer.valueOf(((String)comboBox_TrafficType.getSelectedItem()).trim());
		System.out.println("iiiiii" + ((String)comboBox_TrafficType.getSelectedItem()).trim());
		r.setTrafficImportance(Integer.valueOf(textField_TrafficImportance.getText()));
		r.setPreference(Integer.valueOf(((String)comboBox_Preference.getSelectedItem()).trim()));
		r.setVisibleNet(textField_VisibleNet.getText());
		r.setBandWidth(Double.valueOf(textField_BandWidth.getText()));
		r.setDelay(Double.valueOf(textField_Delay.getText()));
		r.setJitter(Double.valueOf(textField_Jitter.getText()));
		r.setPacketLoss(Double.valueOf(textField_PacketLoss.getText()));
		
		
		String Answer = Terminal.submit(r);
		
		
		if (Answer.equals("1")){
			JOptionPane.showMessageDialog(null, "任务提交成功！", "", JOptionPane.WARNING_MESSAGE);
		} else if(Answer.equals("2")) {
			JOptionPane.showMessageDialog(null, "提交任务失败！", "", JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "任务提交失败！", "", JOptionPane.WARNING_MESSAGE);
		}
	}
}