package com.jzh.RRM;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class RRMServerThread extends Thread {
	//终端套接端口//
	private Socket TerminalSocket;
	//终端输入流//
	private InputStream TerminalInput;
	//终端输出流//
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

			//接收终端的数据//
			BufferedReader bufReader = new BufferedReader(
					new InputStreamReader(TerminalInput));
			
			String TerminalRequest = bufReader.readLine();
			System.out.println(TerminalRequest);
			
		
			
			//获得JRRM端套接口//
			Socket socket = new Socket(RRMServer.JRRMaddress,6666);
			
			//InetAddress addr = socket.getLocalAddress(); //获取套接字连接的本地地址
			//String strLineIP = addr.getHostAddress();
			
			//获得JRRM输出流//
			OutputStream output = socket.getOutputStream();
			//获得JRRM输入流//
			InputStream input = socket.getInputStream();
			
			//输出数据到JRRM
			PrintWriter printWriter = new PrintWriter(output,true);
			printWriter.println(TerminalRequest);
			
			
			//接收JRRM的数据
			BufferedReader bufReader1 = new BufferedReader(new InputStreamReader(input));
			
			String Size3 = bufReader1.readLine();
			
			System.out.println(Size3);
			
			//返回给LRRM的数据
			
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