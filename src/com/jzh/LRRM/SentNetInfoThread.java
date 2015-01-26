package com.jzh.LRRM;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class SentNetInfoThread extends Thread {
	//Socket s;
	int interval; // interval in milli second
	String filepath;
	public String strLineNet;
	public static String inputaddress;
	//private static boolean stop =false;
	SentNetInfoThread(int interval,String path) {
		//this.s = s;
		this.interval = interval * 1000;
		this.filepath = path;
	}
	
	public void run() {
		// sleep for a period of time and then send  message
		//Thread thread = Thread.currentThread();
		//while (sni == thread) {
		System.out.println("������RRM��������IP��ַ: ");
		Scanner sca=new Scanner(System.in);  
		inputaddress=sca.nextLine();
		sca.close();
		// send NETINFO
		try{
			for(int n=1;; n++) {
				strLineNet=new GetNetInfo().readFile(filepath,n);
				if(strLineNet == null) {
					//stop = true;
					break;
				}
				


				
				
				
				//��÷��������׽ӿ�//
				Socket socket = new Socket(inputaddress,7777);
				
				InetAddress addr = socket.getLocalAddress(); //��ȡ�׽������ӵı��ص�ַ
				String strLineIP = addr.getHostAddress();
				
				if(n == 1) {
					System.out.println("local host : "+addr);
				}
				
				String strLine ="1"+":"+strLineIP+":"+strLineNet;
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
				String receive;
				receive=bufReader.readLine();
				if(receive!=null) {
					System.out.println(receive);
				} else {
					System.out.println("�ַ�������ʧ�ܣ�");
				}
				
				socket.close();
				Thread.sleep(interval);   		  
			}
		} catch (Exception e) {
            // possibly lost connection
            e.printStackTrace();
		}
	}
}
