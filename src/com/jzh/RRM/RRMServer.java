package com.jzh.RRM;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class RRMServer extends Thread {
	public static String JRRMaddress;
	
	public void run() {
		int n = 0;
	
		System.out.println("RRM Waiting...");
		try {			
			ServerSocket serverSocket = new ServerSocket(7777);
			Socket clientSocket = null;
			System.out.println("������JRRM��������IP��ַ: ");
			Scanner sca=new Scanner(System.in);  
			JRRMaddress = sca.nextLine();
			sca.close();
			//�����ͻ���//
			while(true) {
				
				clientSocket = serverSocket.accept();
				
				n++;
				new RRMServerThread(clientSocket, n);
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}