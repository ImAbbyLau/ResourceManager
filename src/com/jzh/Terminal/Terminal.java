package com.jzh.Terminal;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;



class Terminal extends Thread {
	
	public static void main(String[] args)throws IOException {
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
		
		
		
		
		
		/*
		while(Util.tj) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		
	}
	
	static String submit (TerRequestBean r) {
		
		
		System.out.println(r.getLRRMIP());
		String inputaddress=r.getLRRMIP();
		int TrafficType=r.getTrafficType();
		int TrafficImportance=r.getTrafficImportance();
		int Preference=r.getPreference();
		String VisibleNet=r.getVisibleNet();
		double BandWidth=r.getBandWidth();
		double Delay=r.getDelay();
		double Jitter=r.getJitter();
		double PacketLoss=r.getPacketLoss();
		TerRequestBean qst = new TerRequestBean();
		qst.setTrafficType(TrafficType);
		qst.setTrafficImportance(TrafficImportance);
		qst.setPreference(Preference);
		qst.setVisibleNet(VisibleNet);
		qst.setBandWidth(BandWidth);
		qst.setDelay(Delay);
		qst.setJitter(Jitter);
		qst.setPacketLoss(PacketLoss);
		
		
		
		
		int RequestID = TerminalSqlRequest.save(r);
		System.out.println(RequestID);
		
		
		
		
		try {
		Socket socket = new Socket(inputaddress,7788);
		InetAddress addr = socket.getLocalAddress(); //��ȡ�׽������ӵı��ص�ַ
		String TerminalIP = addr.getHostAddress();
		
		String strLine =3+":"+TerminalIP+":"+TrafficType+":"+TrafficImportance+":"+Preference+":"+VisibleNet+":"+BandWidth+":"+Delay+":"+Jitter+":"+PacketLoss;
		System.out.println(strLine);
	    
		//��÷����������//
		OutputStream output = socket.getOutputStream();
		//��÷�����������//
		InputStream input = socket.getInputStream();
		
		//������ݵ�������
		PrintWriter printWriter = new PrintWriter(output,true);
		printWriter.println(strLine);
		
	
		
		//���շ�����������
		BufferedReader bufReader = new BufferedReader(new InputStreamReader(input));
		String Answer = null;
		Answer = bufReader.readLine();
		//System.out.println(Answer);
		String values[] = Answer.split(":");
		String operationID = values[0];
		TerRequestBean ans = new TerRequestBean();
		ans.setLRRMIP(values[2]);
		ans.setRequestId(RequestID);
		ans.setNetType(Integer.valueOf(values[3]));
		ans.setNetId(Integer.valueOf(values[4]));
		
		
		
		socket.close();
		
		
		
		
		if(operationID.equals("4")){
			TerminalSqlRequest.update(ans);
			return "1";
		} else if(operationID.equals("5")){
			return "2";
		}else {
			return "3";
		}
		
		
		
		
		
		
		
		} catch (Exception e) {
          // possibly lost connection
          e.printStackTrace();
          //break;
		}
		return null;		
		
	}
	
	
	
	static void InitGlobalFont(Font font) {  //����ȫ������
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