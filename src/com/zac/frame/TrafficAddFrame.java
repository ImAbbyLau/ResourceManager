package com.zac.frame;

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


import com.zac.bean.TrafficBean;
import com.zac.sql.TrafficOp;
import com.zac.util.WindowUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.Font;

public class TrafficAddFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldID;
	private JTextField textFieldTitle;
	private JTextField textFieldLatency;
	private JTextField textFieldJitter;
	private JTextField textFieldLossRatio;
	private JTextField textFieldPrio;
	private JTable TrafficTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JTable TrafficTable = new JTable();
					TrafficAddFrame frame = new TrafficAddFrame();
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
	public TrafficAddFrame() {
		setResizable(false);
		setTitle("\u65B0\u589E\u4E1A\u52A1\u7C7B\u578B");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 370);		
		setLocation(WindowUtil.getLocation(getSize()) );
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel BasicInfoPanel = new JPanel();
		BasicInfoPanel.setBounds(0, 0, 574, 82);
		contentPane.add(BasicInfoPanel);
		BasicInfoPanel.setLayout(null);
		
		JLabel IDLabel = new JLabel("\u4E1A\u52A1\u7F16\u53F7:");
		IDLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		IDLabel.setBounds(40, 45, 75, 30);
		BasicInfoPanel.add(IDLabel);
		
		textFieldID = new JTextField();
		textFieldID.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textFieldID.setBounds(115, 45, 120, 30);
		BasicInfoPanel.add(textFieldID);
		textFieldID.setColumns(10);
		
		JLabel titleLabel = new JLabel("\u4E1A\u52A1\u7C7B\u578B:");
		titleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		titleLabel.setBounds(300, 45, 75, 30);
		BasicInfoPanel.add(titleLabel);
		
		textFieldTitle = new JTextField();
		textFieldTitle.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textFieldTitle.setBounds(375, 45, 120, 30);
		BasicInfoPanel.add(textFieldTitle);
		textFieldTitle.setColumns(10);
		
		JLabel titleLbl1 = new JLabel("\u2014\u57FA\u672C\u4FE1\u606F\u2014");
		titleLbl1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		titleLbl1.setBounds(10, 10, 100, 30);
		BasicInfoPanel.add(titleLbl1);
		
		JPanel QosParameterPanel = new JPanel();
		QosParameterPanel.setBounds(0, 81, 574, 146);
		contentPane.add(QosParameterPanel);
		QosParameterPanel.setLayout(null);
		
		JLabel titleLbl2 = new JLabel("\u2014QoS\u53C2\u6570\u2014");
		titleLbl2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		titleLbl2.setBounds(10, 10, 100, 30);
		QosParameterPanel.add(titleLbl2);
		
		JLabel latencyLabel = new JLabel("\u7B49\u5F85\u65F6\u5EF6:");
		latencyLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		latencyLabel.setBounds(40, 45, 75, 30);
		QosParameterPanel.add(latencyLabel);
		
		textFieldLatency = new JTextField();
		textFieldLatency.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textFieldLatency.setColumns(10);
		textFieldLatency.setBounds(115, 45, 120, 30);
		QosParameterPanel.add(textFieldLatency);
		
		JLabel jitterLabel = new JLabel("\u6296       \u52A8:");
		jitterLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		jitterLabel.setBounds(40, 105, 72, 30);
		QosParameterPanel.add(jitterLabel);
		
		textFieldJitter = new JTextField();
		textFieldJitter.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textFieldJitter.setColumns(10);
		textFieldJitter.setBounds(115, 105, 120, 30);
		QosParameterPanel.add(textFieldJitter);
		
		JLabel lossratioLabel = new JLabel("\u4E22  \u5305  \u7387:");
		lossratioLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lossratioLabel.setBounds(300, 105, 75, 30);
		QosParameterPanel.add(lossratioLabel);
		
		textFieldLossRatio = new JTextField();
		textFieldLossRatio.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textFieldLossRatio.setColumns(10);
		textFieldLossRatio.setBounds(375, 105, 120, 30);
		QosParameterPanel.add(textFieldLossRatio);
		
		JPanel SubPrioritypanel = new JPanel();
		SubPrioritypanel.setBounds(0, 226, 284, 116);
		contentPane.add(SubPrioritypanel);
		SubPrioritypanel.setLayout(null);
		
		JLabel titleLbl3 = new JLabel("\u2014\u4E1A\u52A1\u5B50\u4F18\u5148\u7EA7\u2014");
		titleLbl3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		titleLbl3.setBounds(10, 10, 130, 30);
		SubPrioritypanel.add(titleLbl3);
		
		textFieldPrio = new JTextField();
		textFieldPrio.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textFieldPrio.setColumns(10);
		textFieldPrio.setBounds(115, 50, 120, 30);
		SubPrioritypanel.add(textFieldPrio);
		
		JLabel label = new JLabel("\u5B50\u4F18\u5148\u7EA7:");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label.setBounds(40, 50, 75, 30);
		SubPrioritypanel.add(label);
		
		/*
		JButton prioComputeButton = new JButton("\u8BC4\u4F30");
		prioComputeButton.setBounds(44, 45, 65, 25);
		SubPrioritypanel.add(prioComputeButton);
		*/
		
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setBounds(284, 226, 290, 116);
		contentPane.add(ButtonPanel);
		ButtonPanel.setLayout(null);
		
		JButton saveButton = new JButton("\u4FDD\u5B58");
		saveButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		// 处理“保存”按钮单击事件
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_savebutton_actionPerformed(arg0);
			}
		});
		saveButton.setBounds(15, 50, 65, 25);
		ButtonPanel.add(saveButton);
		
		JButton emptyButton = new JButton("\u6E05\u7A7A");
		emptyButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		// 处理“清空”按钮单击事件
		emptyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldID.setText("");
				textFieldTitle.setText("");
				textFieldLatency.setText("");
				textFieldJitter.setText("");
				textFieldLossRatio.setText("");
				textFieldPrio.setText("");
			}
		});
		emptyButton.setBounds(95, 50, 65, 25);
		ButtonPanel.add(emptyButton);
		
		JButton backButton = new JButton("\u8FD4\u56DE");
		backButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		// 处理“返回”按钮单击事件
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		backButton.setBounds(175, 50, 65, 25);
		ButtonPanel.add(backButton);
	}
	
	// 处理“保存”按钮单击事件
	protected void do_savebutton_actionPerformed(ActionEvent arg0) {
		String trafficID = textFieldID.getText().trim();
		if (trafficID.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "业务编号不能为空！", "", JOptionPane.WARNING_MESSAGE);
		    return;
		}

		String trafficTitle = textFieldTitle.getText().trim();
		if (trafficTitle.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "业务类型不能为空！", "", JOptionPane.WARNING_MESSAGE);
		    return;
		}

		String latency = textFieldLatency.getText().trim();
		if (latency.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "业务等待时延参数不能为空！", "", JOptionPane.WARNING_MESSAGE);
		    return;
		}

		String jitter = textFieldJitter.getText().trim();
		if (jitter.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "业务抖动参数不能为空！", "", JOptionPane.WARNING_MESSAGE);
		    return;
		}

		String loss = textFieldLossRatio.getText().trim();
		if (loss.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "业务丢包率参数不能为空！", "", JOptionPane.WARNING_MESSAGE);
		    return;
		}

		String subPriority = textFieldPrio.getText().trim();
		if (subPriority.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "业务子优先级不能为空，请点击评估按钮对业务子优先级进行评估！", "", JOptionPane.WARNING_MESSAGE);
		    return;
		}

		TrafficBean tempTraf = new TrafficBean();
		tempTraf.setTrafficID(Integer.parseInt(trafficID));
		tempTraf.setTrafficTitle(trafficTitle);
		tempTraf.setLatency(Double.parseDouble(latency));
		tempTraf.setJitter(Double.parseDouble(jitter));
		tempTraf.setPacketLossRatio(Double.parseDouble(loss));
		tempTraf.setTrafficPriority(Integer.parseInt(subPriority));

		int result = TrafficOp.save(tempTraf);
		
		if (result >= 0) {
		    TrafficManagerPanel.refreshTrafficTable();
		    JOptionPane.showMessageDialog(null, "业务类型增加成功！");
		    return;
		} else {
		    JOptionPane.showMessageDialog(null, "业务类型增加失败！");
		    return;
		}
	}	
}
