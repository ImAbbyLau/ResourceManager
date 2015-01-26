package com.jzh.LRRM;
import java.io.*;

import java.net.Socket;


public class LRRMServerThread extends Thread {
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
	
	public LRRMServerThread(Socket socket, int n)throws IOException {
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
			
			/*
			//Map<String, String> tempMap = new HashMap<String, String>();
			String[] keys = TerminalRequest.replaceAll("^\\{", "").replaceAll(":[0-9a-zA-Z\\.]+\\}$", "").split(":[0-9a-zA-Z\\.]+\\}\\{");
			String[] values = TerminalRequest.replaceAll("^\\{[0-9]+:", "").replaceAll("\\}$", "").split("\\}\\{[0-9a-zA-Z]+:");
			System.out.println(keys.length);
			
			//System.out.println("--keys=["+keys+"], keys length = "+keys.length);
			//System.out.println("--values=["+values+"], values length = "+values.length);
			if(null!=keys&&null!=values&&keys.length==values.length&&keys.length==3) {
				for(int i=0;i<keys.length;i++) {
					
					//System.out.println("--keys["+i+"] = ["+keys[i]+"]");
					//System.out.println("--values["+i+"] = ["+values[i]+"]");
					//tempMap.put(keys[i], values[i]);
				    
					if("00".equals(keys[i])) {
						Address= values[i];
						System.out.println("Address="+values[i]);
					}
					if("01".equals(keys[i])) {
						operationID= values[i];
						System.out.println("operationID="+values[i]);
					}
					if("02".equals(keys[i])) {
						Size= values[i];
						System.out.println("Size="+values[i]);
					}
					
					
				}
			} else {
				System.out.println("字符串["+TerminalRequest+"]解析失败！");
			}
			*/
			
			//获得RRM端套接口//
			Socket socket = new Socket(SentNetInfoThread.inputaddress,7777);
			
			//InetAddress addr = socket.getLocalAddress(); //获取套接字连接的本地地址
			//String strLineIP = addr.getHostAddress();
			
			//获得RRM输出流//
			OutputStream output = socket.getOutputStream();
			//获得RRM输入流//
			InputStream input = socket.getInputStream();
			
			//输出数据到RRM
			PrintWriter printWriter = new PrintWriter(output,true);
			printWriter.println(TerminalRequest);
			
			
			//接收RRM的数据
			BufferedReader bufReader1 = new BufferedReader(new InputStreamReader(input));
			
			String Size3 = bufReader1.readLine();
			
			System.out.println(Size3);
			
			//返回给终端的数据
			
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