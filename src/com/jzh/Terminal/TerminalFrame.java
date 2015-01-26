package com.jzh.Terminal;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.zac.util.WindowUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Dimension;
import java.util.Enumeration;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class TerminalFrame extends JFrame {
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					//设置beautyeye Look&Feel
					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
					BeautyEyeLNFHelper.launchBeautyEyeLNF();
					//BeautyEyeLNFHelper.translucencyAtFrameInactive = false; //关闭窗口在不活动时的半透明效果
					//UIManager.setLookAndFeel("org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelWin");
					//UIManager.setLookAndFeel("org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelCross");					
					UIManager.put("RootPane.setupButtonVisible", false);
					
					
					InitGlobalFont(new Font("微软雅黑", Font.PLAIN, 15));  //统一设置字体，对于某些L&F不生效
									
					
					TerminalFrame frame = new TerminalFrame();
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
	public TerminalFrame() {
		//setSize(new Dimension(1050, 700));
		setTitle("\u7EC8\u7AEF\u7BA1\u7406\u7CFB\u7EDF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 1050, 700);
        setLocation(WindowUtil.getLocation(WindowUtil.getSize()));// 设置窗体显示位置
        setSize(WindowUtil.getSize());// 设置窗体大小
		
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(153, 180, 209), 2));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		final JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("\u7EC8\u7AEF\u7BA1\u7406");
		mnNewMenu.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuBar.add(mnNewMenu);
		
		JMenuItem menuItem_1 = new JMenuItem("\u7EC8\u7AEF\u4E1A\u52A1\u8BF7\u6C42\u63D0\u4EA4");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				panel.removeAll();// 移除内容面板中的所有内容
				panel.add(new SubmitPanel(0),  //参数0表示默认显示第一个Tab
						BorderLayout.CENTER);// 将业务管理面版添加到内容面板中
				SwingUtilities.updateComponentTreeUI(panel);// 刷新内容面板中的内容		
					
			}
		});
		menuItem_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		mnNewMenu.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("\u4E1A\u52A1\u8BF7\u6C42\u7ED3\u679C\u67E5\u8BE2");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				panel.removeAll();// 移除内容面板中的所有内容
				panel.add(new SubmitPanel(1),  //参数0表示默认显示第一个Tab
						BorderLayout.CENTER);// 将业务管理面版添加到内容面板中
				SwingUtilities.updateComponentTreeUI(panel);// 刷新内容面板中的内容		
					
			}
		});
		menuItem_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		mnNewMenu.add(menuItem_2);
		
		JMenu menu = new JMenu("\u5E2E\u52A9");
		menu.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuBar.add(menu);
		
		JMenuItem MenuItemguanyu = new JMenuItem("\u5173\u4E8E");
		MenuItemguanyu.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menu.add(MenuItemguanyu);
		
		
		
	}

	
	protected static void InitGlobalFont(Font font) {  //设置全局字体
		  FontUIResource fontRes = new FontUIResource(font);  
		  for (Enumeration<Object> keys = UIManager.getDefaults().keys();  
				  keys.hasMoreElements(); ) {  
			  Object key = keys.nextElement();  
			  Object value = UIManager.get(key);  
			  if (value instanceof FontUIResource) {
				  UIManager.put(key, fontRes);  
			   }
			  }  
	}
}

