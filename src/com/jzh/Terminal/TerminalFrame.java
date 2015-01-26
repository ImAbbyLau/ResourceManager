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
					
					//����beautyeye Look&Feel
					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
					BeautyEyeLNFHelper.launchBeautyEyeLNF();
					//BeautyEyeLNFHelper.translucencyAtFrameInactive = false; //�رմ����ڲ��ʱ�İ�͸��Ч��
					//UIManager.setLookAndFeel("org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelWin");
					//UIManager.setLookAndFeel("org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelCross");					
					UIManager.put("RootPane.setupButtonVisible", false);
					
					
					InitGlobalFont(new Font("΢���ź�", Font.PLAIN, 15));  //ͳһ�������壬����ĳЩL&F����Ч
									
					
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
        setLocation(WindowUtil.getLocation(WindowUtil.getSize()));// ���ô�����ʾλ��
        setSize(WindowUtil.getSize());// ���ô����С
		
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
		mnNewMenu.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		menuBar.add(mnNewMenu);
		
		JMenuItem menuItem_1 = new JMenuItem("\u7EC8\u7AEF\u4E1A\u52A1\u8BF7\u6C42\u63D0\u4EA4");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				panel.removeAll();// �Ƴ���������е���������
				panel.add(new SubmitPanel(0),  //����0��ʾĬ����ʾ��һ��Tab
						BorderLayout.CENTER);// ��ҵ����������ӵ����������
				SwingUtilities.updateComponentTreeUI(panel);// ˢ����������е�����		
					
			}
		});
		menuItem_1.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		mnNewMenu.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("\u4E1A\u52A1\u8BF7\u6C42\u7ED3\u679C\u67E5\u8BE2");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				panel.removeAll();// �Ƴ���������е���������
				panel.add(new SubmitPanel(1),  //����0��ʾĬ����ʾ��һ��Tab
						BorderLayout.CENTER);// ��ҵ����������ӵ����������
				SwingUtilities.updateComponentTreeUI(panel);// ˢ����������е�����		
					
			}
		});
		menuItem_2.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		mnNewMenu.add(menuItem_2);
		
		JMenu menu = new JMenu("\u5E2E\u52A9");
		menu.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		menuBar.add(menu);
		
		JMenuItem MenuItemguanyu = new JMenuItem("\u5173\u4E8E");
		MenuItemguanyu.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		menu.add(MenuItemguanyu);
		
		
		
	}

	
	protected static void InitGlobalFont(Font font) {  //����ȫ������
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

