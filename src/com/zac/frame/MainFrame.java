package com.zac.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;

import javax.swing.JMenuItem;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import java.awt.FlowLayout;

import javax.swing.JTree;
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;

import java.awt.Component;
import java.util.Enumeration;


import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.jzh.JRRM.JRRMPanel;
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
import com.zac.util.WindowUtil;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	private static JPanel rightPanel;
	
	//JTreeҶ�ӽڵ�
	private static DefaultMutableTreeNode leafnode11;
	private static DefaultMutableTreeNode leafnode12;
	private static DefaultMutableTreeNode leafnode13;
	
	private static DefaultMutableTreeNode leafnode21;
	private static DefaultMutableTreeNode leafnode22;
	private static DefaultMutableTreeNode leafnode23;

	private static DefaultMutableTreeNode leafnode31;
	private static DefaultMutableTreeNode leafnode32;
	private static DefaultMutableTreeNode leafnode33;
	
	private static DefaultMutableTreeNode leafnode41;
	private static DefaultMutableTreeNode leafnode42;
	private static DefaultMutableTreeNode leafnode43;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					//����Nimbus Look&Feel
					//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					/*
					UIManager.setLookAndFeel(new NimbusLookAndFeel() {   //��дgetDefaults()����������UI��Ĭ������
						@Override  
						public UIDefaults getDefaults () {
							UIDefaults ret = super.getDefaults ();   
							ret.put("defaultFont", new Font("΢���ź�", Font.PLAIN, 15));   
							return ret;   
							}   
						});  */
					
					
					//����beautyeye Look&Feel
					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
					BeautyEyeLNFHelper.launchBeautyEyeLNF();
					//BeautyEyeLNFHelper.translucencyAtFrameInactive = false; //�رմ����ڲ��ʱ�İ�͸��Ч��
					//UIManager.setLookAndFeel("org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelWin");
					//UIManager.setLookAndFeel("org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelCross");					
					UIManager.put("RootPane.setupButtonVisible", false);
					InitGlobalFont(new Font("΢���ź�", Font.PLAIN, 15));  //ͳһ�������壬����ĳЩL&F����Ч
					
								
					//����Metal Look&Feel
					//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					//UIManager.put("swing.boldMetal", Boolean.FALSE);
									
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setResizable(false);
		
		setTitle("����ҵ����Ҫ�Ե���Դ����ϵͳ������ˣ�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 600, 400);
		
        setLocation(WindowUtil.getLocation(WindowUtil.getSize()));// ���ô�����ʾλ��
        setSize(WindowUtil.getSize());// ���ô����С
		
        final JLabel homePage = new JLabel("");
		homePage.setIcon(new ImageIcon(MainFrame.class.getResource("/img/homepage_manager.png")));
		homePage.setHorizontalAlignment(SwingConstants.CENTER);
        
        rightPanel = new JPanel();
        
		JMenuBar mainMenuBar = new JMenuBar();
		mainMenuBar.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		setJMenuBar(mainMenuBar);
		
		JMenu menu1 = new JMenu("\u4E1A\u52A1\u91CD\u8981\u6027\u7BA1\u7406");
		menu1.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		mainMenuBar.add(menu1);
		
		JMenu menu11 = new JMenu("\u4E1A\u52A1\u91CD\u8981\u6027\u7BA1\u7406");
		menu11.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		menu1.add(menu11);
		
		JMenuItem menuItem111 = new JMenuItem("\u4E1A\u52A1\u5C5E\u6027\u7BA1\u7406");
		menuItem111.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new TrafficManagerPanel(0),  //����0��ʾĬ����ʾ��һ��Tab
						BorderLayout.CENTER);// ��ҵ����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����				
			}
		});
		menuItem111.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		menu11.add(menuItem111);
		
		JMenuItem menuItem112 = new JMenuItem("\u7528\u6237\u5C5E\u6027\u7BA1\u7406");
		menuItem112.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new TrafficManagerPanel(1),  //����0��ʾĬ����ʾ��һ��Tab
						BorderLayout.CENTER);// ��ҵ����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			}
		});
		menuItem112.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		menu11.add(menuItem112);
		
		JMenuItem menuItem113 = new JMenuItem("\u4EFB\u52A1\u5C5E\u6027\u7BA1\u7406");
		menuItem113.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new TrafficManagerPanel(2),  //����0��ʾĬ����ʾ��һ��Tab
						BorderLayout.CENTER);// ��ҵ����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			}
		});
		menuItem113.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		menu11.add(menuItem113);
		
		JMenu menu12 = new JMenu("\u4E1A\u52A1\u91CD\u8981\u6027\u8BC4\u4F30");
		menu1.add(menu12);
		menu12.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		
		JMenuItem menuItem121 = new JMenuItem("\u57FA\u4E8E\u8868\u7684\u91CD\u8981\u6027\u8BC4\u4F30");
		menuItem121.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new ImpEvaPanel(0),  //����0��ʾĬ����ʾ��һ��Tab
						BorderLayout.CENTER);// ��ҵ����Ҫ�����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			}
		});
		menuItem121.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		menu12.add(menuItem121);
		
		JMenuItem menuItem122 = new JMenuItem("\u57FA\u4E8EMADM\u7684\u8BC4\u4F30");
		menuItem122.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new ImpEvaPanel(1),  //����2��ʾĬ����ʾ������Tab
						BorderLayout.CENTER);// ��ҵ����Ҫ�����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			}
		});
		menuItem122.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		menu12.add(menuItem122);
		
		JMenuItem menuItem123 = new JMenuItem("\u91CD\u8981\u6027\u8BC4\u4F30\u7ED3\u679C\u603B\u89C8");
		menuItem123.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new ImpEvaPanel(2),  //����2��ʾĬ����ʾ������Tab
						BorderLayout.CENTER);// ��ҵ����Ҫ�����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			}
		});
		menuItem123.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		menu12.add(menuItem123);
		
		JMenu menu2 = new JMenu("\u8D44\u6E90\u7BA1\u7406");
		menu2.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		mainMenuBar.add(menu2);
		
		JMenuItem menuItem21 = new JMenuItem("LRRM\u4FE1\u606F\u67E5\u8BE2");
		menuItem21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new JRRMPanel(0),  //����0��ʾĬ����ʾ��һ��Tab
						BorderLayout.CENTER);// ��ҵ����Ҫ�����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			}
		});
		menu2.add(menuItem21);
		
		JMenuItem menuItem22 = new JMenuItem("\u8BC4\u4F30\u8BF7\u6C42\u67E5\u8BE2");
		menuItem22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new JRRMPanel(1),  //����1��ʾĬ����ʾ�ڶ���Tab
						BorderLayout.CENTER);// ��ҵ����Ҫ�����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			}
		});
		menu2.add(menuItem22);
		
		JMenuItem menuItem23 = new JMenuItem("\u8BC4\u4F30\u8FC7\u7A0B\u67E5\u8BE2");
		menuItem23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new JRRMPanel(2),  //����2��ʾĬ����ʾ������Tab
						BorderLayout.CENTER);// ��ҵ����Ҫ�����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			}
		});
		menu2.add(menuItem23);
		
		JMenu menu3 = new JMenu("\u7B56\u7565\u7BA1\u7406");
		mainMenuBar.add(menu3);
		menu3.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		
		JMenu menu4 = new JMenu("\u5E2E\u52A9");
		menu4.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		mainMenuBar.add(menu4);
		
		JMenuItem menuItemX = new JMenuItem("\u5173\u4E8E");
		menuItemX.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		menu4.add(menuItemX);		
		
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(153, 180, 209), 2));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//final JPanel rightPanel = new JPanel();
		
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new LineBorder(Color.GRAY));
		contentPane.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPanel = new JPanel();
		topPanel.add(buttonPanel, BorderLayout.WEST);
		
		JButton trafficManagerKeyButton = new JButton();
		trafficManagerKeyButton.setHorizontalAlignment(SwingConstants.LEFT);
		trafficManagerKeyButton.setIcon(new ImageIcon(MainFrame.class.getResource("/img/logo1.png")));
		//����ҵ�������ť�����¼�
		trafficManagerKeyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new TrafficManagerPanel(0),  //����0��ʾĬ����ʾ��һ��Tab
						BorderLayout.CENTER);// ��ҵ����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			}
		});
		trafficManagerKeyButton.setPreferredSize(new Dimension(175, 50));
		trafficManagerKeyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		trafficManagerKeyButton.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		trafficManagerKeyButton.setText("\u4E1A\u52A1\u91CD\u8981\u6027\u7BA1\u7406");
		
		JButton trafficEvaluateKeyButton = new JButton();
		trafficEvaluateKeyButton.setIcon(new ImageIcon(MainFrame.class.getResource("/img/logo2.png")));
		trafficEvaluateKeyButton.setHorizontalAlignment(SwingConstants.LEFT);
		//����ҵ����Ҫ����������ť�����¼�
		trafficEvaluateKeyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new ImpEvaPanel(0),  //����0��ʾĬ����ʾ��һ��Tab
						BorderLayout.CENTER);// ��ҵ����Ҫ�����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			}
		});
		trafficEvaluateKeyButton.setPreferredSize(new Dimension(175, 50));
		
				trafficEvaluateKeyButton.setText("\u4E1A\u52A1\u91CD\u8981\u6027\u8BC4\u4F30");
				trafficEvaluateKeyButton.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		
		JButton sourceManagerKeyButton2 = new JButton();
		sourceManagerKeyButton2.setIcon(new ImageIcon(MainFrame.class.getResource("/img/logo4.png")));
		sourceManagerKeyButton2.setHorizontalAlignment(SwingConstants.LEFT);
		sourceManagerKeyButton2.setPreferredSize(new Dimension(125, 50));
		sourceManagerKeyButton2.setText("\u7B56\u7565\u7BA1\u7406");
		sourceManagerKeyButton2.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label = new JLabel("");
		label.setPreferredSize(new Dimension(195, 50));
		buttonPanel.add(label);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(MainFrame.class.getResource("/img/logo_main2.png")));
		
		JButton homeButton = new JButton();
		homeButton.setToolTipText("");
		homeButton.setHorizontalAlignment(SwingConstants.LEFT);
		homeButton.setIcon(new ImageIcon(MainFrame.class.getResource("/img/logo0.png")));
		//������ҳ����ť�����¼�
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(homePage, BorderLayout.CENTER);
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			}
		});
		homeButton.setText("\u9996\u9875");
		homeButton.setPreferredSize(new Dimension(95, 50));
		homeButton.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		buttonPanel.add(homeButton);
		buttonPanel.add(trafficManagerKeyButton);
		buttonPanel.add(trafficEvaluateKeyButton);
		
		JButton sourceManagerKeyButton1 = new JButton();
		//������Դ������ť�����¼�
		sourceManagerKeyButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new JRRMPanel(0),  //����0��ʾ��һ��Tab 
						BorderLayout.CENTER);
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			}
		});
		sourceManagerKeyButton1.setIcon(new ImageIcon(MainFrame.class.getResource("/img/logo3.png")));
		sourceManagerKeyButton1.setHorizontalAlignment(SwingConstants.LEFT);
		sourceManagerKeyButton1.setPreferredSize(new Dimension(125, 50));
		sourceManagerKeyButton1.setText("\u8D44\u6E90\u7BA1\u7406");
		sourceManagerKeyButton1.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		buttonPanel.add(sourceManagerKeyButton1);
		buttonPanel.add(sourceManagerKeyButton2);
		
		JButton exitKeyButton = new JButton();		
		exitKeyButton.setIcon(new ImageIcon(MainFrame.class.getResource("/img/logo5.png")));
		exitKeyButton.setHorizontalAlignment(SwingConstants.LEFT);
		//�����˳�����ť�����¼�
		exitKeyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitKeyButton.setPreferredSize(new Dimension(95, 50));
		exitKeyButton.setText("\u9000\u51FA");
		exitKeyButton.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		buttonPanel.add(exitKeyButton);
		
		JScrollPane leftScrollPane = new JScrollPane();
		leftScrollPane.setPreferredSize(new Dimension(205, 0));
		leftScrollPane.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		contentPane.add(leftScrollPane, BorderLayout.WEST);
		
		//����JTree�ṹ
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("��Դ����");
		DefaultMutableTreeNode node1;
		DefaultMutableTreeNode node2;
		DefaultMutableTreeNode node3;
		DefaultMutableTreeNode node4;
		
		node1 = new DefaultMutableTreeNode("ҵ����Ҫ�Թ���");
		node2 = new DefaultMutableTreeNode("ҵ����Ҫ������");
		node3 = new DefaultMutableTreeNode("��Դ����");
		node4 = new DefaultMutableTreeNode("���Թ���");
		
		root.add(node1);
		root.add(node2);
		root.add(node3);
		root.add(node4);
		
		leafnode11 = new DefaultMutableTreeNode("ҵ�����Թ���");
		leafnode12 = new DefaultMutableTreeNode("�û����Թ���");
		leafnode13 = new DefaultMutableTreeNode("�������Թ���");
		
		leafnode21 = new DefaultMutableTreeNode("���ڱ����Ҫ������");
		leafnode22 = new DefaultMutableTreeNode("����MADM������");
		leafnode23 = new DefaultMutableTreeNode("��Ҫ�������������");

		leafnode31 = new DefaultMutableTreeNode("LRRM��Ϣ��ѯ");
		leafnode32 = new DefaultMutableTreeNode("���������ѯ");
		leafnode33 = new DefaultMutableTreeNode("�������̲�ѯ");
		
		leafnode41 = new DefaultMutableTreeNode("����һ");
		leafnode42 = new DefaultMutableTreeNode("���ö�");
		leafnode43 = new DefaultMutableTreeNode("������");
		
		
		node1.add(leafnode11);
		node1.add(leafnode12);
		node1.add(leafnode13);

		node2.add(leafnode21);
		node2.add(leafnode22);
		node2.add(leafnode23);
		
		node3.add(leafnode31);
		node3.add(leafnode32);
		node3.add(leafnode33);
		
		node4.add(leafnode41);
		node4.add(leafnode42);
		node4.add(leafnode43);
		
		//���ýڵ�ͼ��
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon((new ImageIcon(MainFrame.class.getResource("/img/treeleaf.png"))));
		renderer.setClosedIcon((new ImageIcon(MainFrame.class.getResource("/img/treenode_close.png"))));
		renderer.setOpenIcon(new ImageIcon(MainFrame.class.getResource("/img/treenode_open.png")));
		
		
		JTree tree = new JTree(root);
		tree.setRootVisible(false);		
		tree.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		tree.setEditable(false);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);  //���ýڵ�ѡ��ʽΪһ��ֻ��ѡ��һ���ڵ�
		
		tree.setCellRenderer(renderer);
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				doActionofSelectNode(node);
			}
		});
		
		expandAll(tree, new TreePath(root), true);//������Ĭ��ȫ��չ��
		leftScrollPane.setViewportView(tree);//Ӧ��JTree������������
				
		contentPane.add(rightPanel, BorderLayout.CENTER);
		rightPanel.setLayout(new BorderLayout(0, 0));
		
		rightPanel.add(homePage, BorderLayout.CENTER);
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
	
	protected static void expandAll(JTree tree, TreePath parent, boolean expand) {  //����JTree�ڵ�Ĭ��ȫ��չ��		  
	     TreeNode node = (TreeNode) parent.getLastPathComponent();  	  
	         if (node.getChildCount() > 0) {  	  
	              for (Enumeration e = node.children(); e.hasMoreElements(); ) {  	  
	                    TreeNode n = (TreeNode) e.nextElement();  	  
	                     TreePath path = parent.pathByAddingChild(n);  	  
	                      expandAll(tree, path, expand);  	  
	                }  	  
	          }  	  
	      if (expand) {  	  
	         tree.expandPath(parent);  	  
	      } else {  	  
	          tree.collapsePath(parent);  	  
	     }  	  
	} 
	
	//�������ڵ�ѡ�����¼�
	protected static void doActionofSelectNode (DefaultMutableTreeNode node) {
		  if (node.equals(leafnode11)) {  //Ĭ����ʾ"ҵ�����"Tab
			  
			  rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new TrafficManagerPanel(0),  //����0��ʾĬ����ʾ��һ��Tab
						BorderLayout.CENTER);// ��ҵ����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
				
		  } else if (node.equals(leafnode12)) {  //Ĭ����ʾ"�û�����"Tab
			  
			  rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new TrafficManagerPanel(1),  //����1��ʾĬ����ʾ�ڶ���Tab
						BorderLayout.CENTER);// ��ҵ����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
				
		  } else if (node.equals(leafnode13)) {  //Ĭ����ʾ"�������"Tab
			  
			  rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new TrafficManagerPanel(2),  //����2��ʾĬ����ʾ������Tab
						BorderLayout.CENTER);// ��ҵ����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
				
		  } else if (node.equals(leafnode21)) {  //Ĭ����ʾ"���ڱ����Ҫ������"Tab
			  
			  rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new ImpEvaPanel(0),  //����0��ʾĬ����ʾ��һ��Tab
						BorderLayout.CENTER);// ��ҵ����Ҫ�����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			  
		  } else if (node.equals(leafnode22)) {  //Ĭ����ʾ"��Ҫ�������������"Tab
			  
			  rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new ImpEvaPanel(1),  //����1��ʾĬ����ʾ�ڶ���Tab
						BorderLayout.CENTER);// ��ҵ����Ҫ�����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			  
		  } else if (node.equals(leafnode23)) {  //Ĭ����ʾ"��Ҫ�������������"Tab
			  
			  rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new ImpEvaPanel(2),  //����2��ʾĬ����ʾ������Tab
						BorderLayout.CENTER);// ��ҵ����Ҫ�����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			  
		  } else if (node.equals(leafnode31)) {  //Ĭ����ʾ"��Ҫ�������������"Tab
			  
			  rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new JRRMPanel(0),  //����0��ʾĬ����ʾ��һ��Tab
						BorderLayout.CENTER);// ��ҵ����Ҫ�����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			  
		  } else if (node.equals(leafnode32)) {  //Ĭ����ʾ"��Ҫ�������������"Tab
			  
			  rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new JRRMPanel(1),  //����1��ʾĬ����ʾ�ڶ���Tab
						BorderLayout.CENTER);// ��ҵ����Ҫ�����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			  
		  } else if (node.equals(leafnode33)) {  //Ĭ����ʾ"��Ҫ�������������"Tab
			  
			  rightPanel.removeAll();// �Ƴ���������е���������
				rightPanel.add(new JRRMPanel(2),  //����2��ʾĬ����ʾ������Tab
						BorderLayout.CENTER);// ��ҵ����Ҫ�����������ӵ����������
				SwingUtilities.updateComponentTreeUI(rightPanel);// ˢ����������е�����
			  
		  }
	}
}
