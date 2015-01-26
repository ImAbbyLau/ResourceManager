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


import com.zac.bean.UserBean;
import com.zac.sql.UserOp;
import com.zac.util.WindowUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.Font;

public class UserAddFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldID;
	private JTextField textFieldTitle;
	private JTextField textFieldClass;
	private JTextField textFieldSecurityPolicy;
	private JTextField textFieldPrio;
	private JTable UserTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JTable UserTable = new JTable();
					UserAddFrame frame = new UserAddFrame();
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
	public UserAddFrame() {
		setResizable(false);
		setTitle("\u65B0\u589E\u7528\u6237\u7C7B\u578B");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 370);		
		setLocation(WindowUtil.getLocation(getSize()) );
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel BasicInfoPanel = new JPanel();
		BasicInfoPanel.setBounds(0, 0, 574, 101);
		contentPane.add(BasicInfoPanel);
		BasicInfoPanel.setLayout(null);
		
		JLabel IDLabel = new JLabel("\u7528\u6237\u7F16\u53F7:");
		IDLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		IDLabel.setBounds(40, 45, 75, 30);
		BasicInfoPanel.add(IDLabel);
		
		textFieldID = new JTextField();
		textFieldID.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textFieldID.setBounds(115, 45, 120, 30);
		BasicInfoPanel.add(textFieldID);
		textFieldID.setColumns(10);
		
		JLabel titleLabel = new JLabel("\u7528\u6237\u540D\u79F0:");
		titleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		titleLabel.setBounds(300, 45, 75, 30);
		BasicInfoPanel.add(titleLabel);
		
		textFieldTitle = new JTextField();
		textFieldTitle.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textFieldTitle.setBounds(400, 45, 120, 30);
		BasicInfoPanel.add(textFieldTitle);
		textFieldTitle.setColumns(10);
		
		JLabel titleLbl1 = new JLabel("\u2014\u57FA\u672C\u4FE1\u606F\u2014");
		titleLbl1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		titleLbl1.setBounds(10, 10, 100, 30);
		BasicInfoPanel.add(titleLbl1);
		
		JPanel QosParameterPanel = new JPanel();
		QosParameterPanel.setBounds(0, 100, 574, 127);
		contentPane.add(QosParameterPanel);
		QosParameterPanel.setLayout(null);
		
		JLabel titleLbl2 = new JLabel("\u2014\u7528\u6237\u53C2\u6570\u2014");
		titleLbl2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		titleLbl2.setBounds(10, 10, 100, 30);
		QosParameterPanel.add(titleLbl2);
		
		JLabel classLabel = new JLabel("\u7528\u6237\u7C7B\u522B:");
		classLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		classLabel.setBounds(40, 65, 75, 30);
		QosParameterPanel.add(classLabel);
		
		textFieldClass = new JTextField();
		textFieldClass.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textFieldClass.setColumns(10);
		textFieldClass.setBounds(115, 65, 120, 30);
		QosParameterPanel.add(textFieldClass);
		
		JLabel securitypolicyLabel = new JLabel("\u5B89\u5168\u7B56\u7565\u7EA7\u522B:");
		securitypolicyLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		securitypolicyLabel.setBounds(300, 65, 100, 30);
		QosParameterPanel.add(securitypolicyLabel);
		
		textFieldSecurityPolicy = new JTextField();
		textFieldSecurityPolicy.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		textFieldSecurityPolicy.setColumns(10);
		textFieldSecurityPolicy.setBounds(400, 65, 120, 30);
		QosParameterPanel.add(textFieldSecurityPolicy);
		
		JPanel SubPrioritypanel = new JPanel();
		SubPrioritypanel.setBounds(0, 226, 284, 116);
		contentPane.add(SubPrioritypanel);
		SubPrioritypanel.setLayout(null);
		
		JLabel titleLbl3 = new JLabel("\u2014\u7528\u6237\u5B50\u4F18\u5148\u7EA7\u2014");
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
				textFieldClass.setText("");
				textFieldSecurityPolicy.setText("");
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
	
	//处理“保存”按钮单击事件
	protected void do_savebutton_actionPerformed(ActionEvent arg0) {
		String userID = textFieldID.getText().trim();
        if (userID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "用户编号不能为空！", "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String userTitle = textFieldTitle.getText().trim();
        if (userTitle.isEmpty()) {
            JOptionPane.showMessageDialog(null, "用户名称不能为空！", "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String userClass = textFieldClass.getText().trim();
        if (userClass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "用户级别参数项不能为空！", "", JOptionPane.WARNING_MESSAGE);
            return;
        }		        
        
        String userSecurityPolicy = textFieldSecurityPolicy.getText().trim();
        if (userSecurityPolicy.isEmpty()) {
            JOptionPane.showMessageDialog(null, "用户安全策略类别参数项不能为空！", "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String subPriority = textFieldPrio.getText().trim();
        if (subPriority.isEmpty()) {
            JOptionPane.showMessageDialog(null, "用户子优先级不能为空，请点击评估按钮对业务子优先级进行评估！", "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        UserBean user = new UserBean();
        user.setUserID(Integer.parseInt(userID));
        user.setUserTitle(userTitle);
        user.setUserClassification(Integer.parseInt(userClass));
        user.setUserSecurityPolicy(Integer.parseInt(userSecurityPolicy));
        user.setUserPriority(Integer.parseInt(subPriority));
        
        int result = UserOp.save(user);
        if (result >= 0) {           
        	TrafficManagerPanel.refreshUserTable();
		    JOptionPane.showMessageDialog(null, "用户类型增加成功！");
            return;
        } else {
            JOptionPane.showMessageDialog(null, "用户类型增加失败！");
            return;
        }
	}
}
