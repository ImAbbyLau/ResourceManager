package com.jzh.RRM;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class RRMServerThread extends Thread {
	//�ն��׽Ӷ˿�//
	private Socket TerminalSocket;
	//�ն�������//
	private InputStream TerminalInput;
	//�ն������//
	private OutputStream TerminalOutput;
	
	//private String SubnetID;
	//private String Address;
	//private String operationID;
	//private String Size;
	
	public RRMServerThread(Socket socket, int n)throws IOException {
		TerminalSocket = socket;
		TerminalInput = TerminalSocket.getInputStream();
		TerminalOutput = TerminalSocket.getOutputStream();
		System.out.println("The " + n + " LRRM is linked!");
		this.start();
	}
	
	public void run() {
		try {

			//�����ն˵�����//
			BufferedReader bufReader = new BufferedReader(
					new InputStreamReader(TerminalInput));
			
			String TerminalRequest = bufReader.readLine();
			System.out.println(TerminalRequest);
			
		
			
			//���JRRM���׽ӿ�//
			Socket socket = new Socket(RRMServer.JRRMaddress,6666);
			
			//InetAddress addr = socket.getLocalAddress(); //��ȡ�׽������ӵı��ص�ַ
			//String strLineIP = addr.getHostAddress();
			
			//���JRRM�����//
			OutputStream output = socket.getOutputStream();
			//���JRRM������//
			InputStream input = socket.getInputStream();
			
			//������ݵ�JRRM
			PrintWriter printWriter = new PrintWriter(output,true);
			printWriter.println(TerminalRequest);
			
			
			//����JRRM������
			BufferedReader bufReader1 = new BufferedReader(new InputStreamReader(input));
			
			String Size3 = bufReader1.readLine();
			
			System.out.println(Size3);
			
			//���ظ�LRRM������
			
			PrintWriter printWriter1 = new PrintWriter(TerminalOutput,true);
			
			
			printWriter1.println(Size3);
			
			
			
			
			
			
			
			
			TerminalInput.close();
			TerminalOutput.close();
			TerminalSocket.close();
			System.out.println("This socket is closed!");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
 }