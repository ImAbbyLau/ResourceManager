package com.jzh.JRRM;
import java.io.*;
import java.net.Socket;

import com.jzh.Terminal.TerRequestBean;
import com.jzh.Terminal.TerminalSqlRequest;


public class JRRMServerThread extends Thread {
	//LRRM套接端口//
	private Socket LRRMSocket;
	//LRRM输入流//
	private InputStream LRRMInput;
	//LRRM输出流//
	private OutputStream LRRMOutput;
	
	//private String SubnetID;
	private String Address;
	private String operationID;
	private String NetworkType;
	private String NetworkID;
	
	private String[] BandWidth = new String[4]; 
	private String[] Delay = new String[4];
	private String[] Jitter = new String[4];
	private String[] PacketLoss = new String[4];

	
	private String TrafficImportance;
	private String TrafficType;
	private String Preference;
	private String VisibleNet;
	private String LoadRate;
	
	
	
	public JRRMServerThread(Socket socket, int n)throws IOException {
		LRRMSocket = socket;
		LRRMInput = LRRMSocket.getInputStream();
		LRRMOutput = LRRMSocket.getOutputStream();
		System.out.println("The " + n + " client is linked!");
		this.start();
	}

	public void run() {
		try {
			//接收LRRM的输入//
			BufferedReader bufReader = new BufferedReader(
					new InputStreamReader(LRRMInput));
			//构造Resource对象
			ResourceBean[] res = new ResourceBean[4];

			
			RequestBean rsq = new RequestBean();
			
			//按行读取输入内存//
	
			
			String strLine = bufReader.readLine();
			
			//System.out.println(strLine);
			
			//解析LRRM送来的网络信息字符串//
			//Map<String, String> tempMap = new HashMap<String, String>();
			/*String[] keys = strLine.replaceAll("^\\{", "").replaceAll(":[0-9a-zA-Z\\.]+\\}$", "").split(":[0-9a-zA-Z\\.]+\\}\\{");
			String[] values = strLine.replaceAll("^\\{[0-9]+:", "").replaceAll("\\}$", "").split("\\}\\{[0-9a-zA-Z]+:");
			System.out.println(keys.length);

			//System.out.println("--keys=["+keys+"], keys length = "+keys.length);
			//System.out.println("--values=["+values+"], values length = "+values.length);
			if(null!=keys&&null!=values&&keys.length==values.length&&keys.length==4) {
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
						SubnetID= values[i];
						System.out.println("SubnetID="+values[i]);
					}
					if("03".equals(keys[i])) {
						 Size= values[i];
						System.out.println("Size="+values[i]);
					}
					
				}
				*/
				String values[] = strLine.split(":");
				
				operationID = values[0];
				
				

			
			
			
			
				//将数据存入res对象
				if(operationID.equals("1")) {
				
					
					Address = values[1];
					NetworkType = values[2];
					NetworkID = values[3];
					LoadRate = values[4];
					
					for(int i = 0; i < BandWidth.length; i++) {
						BandWidth[i] = values[(i + 1) * 4 + 1];
						Delay[i] = values[(i + 1) * 4 + 2];
						Jitter[i] = values[(i + 1) * 4 + 3];
						PacketLoss[i] = values[(i + 1) * 4 + 4];
						
						res[i] = new ResourceBean(Address,Integer.parseInt(NetworkType) ,Integer.parseInt(NetworkID),
								Double.parseDouble(LoadRate),Double.parseDouble(BandWidth[i]),Double.parseDouble(Delay[i]),Double.parseDouble(Jitter[i]),
								Double.parseDouble(PacketLoss[i]));
					}
					
//					//会话类
//					BandWidth_1 = values[5];
//					Delay_1 = values[6];
//					Jitter_1 = values[7];
//					PacketLoss_1 = values[8];
//					//流类
//					BandWidth_2 = values[9];
//					Delay_2 = values[10];
//					Jitter_2 = values[11];
//					PacketLoss_2 = values[12];
//					//交互类
//					BandWidth_3 = values[13];
//					Delay_3 = values[14];
//					Jitter_3 = values[15];
//					PacketLoss_3 = values[16];
//					//背景类
//					BandWidth_4 = values[17];
//					Delay_4 = values[18];
//					Jitter_4 = values[19];
//					PacketLoss_4 = values[20];
					
					
					
					
					/*
					res.setAddress(Address); //存入IPAddress
					res.setNetworkType(Integer.parseInt(NetworkType));;
					res.setNetworkID(Integer.parseInt(NetworkID));;
					res.setBandWidth(Double.parseDouble(BandWidth_1));//存入BandWidth
					res.setDelay(Double.parseDouble(Delay_1));//存入Delay
					res.setJitter(Double.parseDouble(Jitter_1));//存入Jitter
					res.setPacketLoss(Double.parseDouble(PacketLoss_1));//存入PacketLoss
					res.setLoadRate(Double.parseDouble(LoadRate));//存入LoadRate
					*/
					PrintWriter printWriter = new PrintWriter(LRRMOutput,true);
					if(-1 == JRRMSqlResource.saveOrUpdate(res)) {					
						printWriter.println("Insert to database failed!");
					} else {
						printWriter.println("Insert to database successful!");
					}
				}
				 
				if(operationID.equals("3")) {
					//res.setSubnetID(Integer.parseInt(SubnetID));//存入SubnetID
						
						Address = values[1];
						TrafficType = values[2];
						TrafficImportance = values[3];
						Preference = values[4];
						VisibleNet = values[5];
						BandWidth[0] = values[6];
						Delay[0] = values[7];
						Jitter[0] = values[8];
						PacketLoss[0] = values[9];
						rsq.setAddress(Address); //存入IPAddress\
						rsq.setTrafficType(Integer.parseInt(TrafficType));
						rsq.setTrafficImportance(Integer.parseInt(TrafficImportance));
						rsq.setPreference(Integer.parseInt(Preference));
						rsq.setVisibleNet(VisibleNet);
						rsq.setBandWidth(Double.parseDouble(BandWidth[0]));//存入Size
						rsq.setDelay(Double.parseDouble(Delay[0]));
						rsq.setJitter(Double.parseDouble(Jitter[0]));
						rsq.setPacketLoss(Double.parseDouble(PacketLoss[0]));
						
						int PackageID = JRRMSqlRequest.count(rsq);
						rsq.setPackageID(PackageID);//存入PackageID
						
						
						
						//评估//
						
						
						NetworkEvaluation n = new NetworkEvaluation(rsq);
						String answer = n.Search();
						//System.out.println(answer);
						String valuesans[] = answer.split(":");
						String operationID = valuesans[0];
						RequestBean ans = new RequestBean();
						ans.setLRRMIP(valuesans[2]);
						ans.setPackageID(PackageID);
						ans.setAddress(Address);
						ans.setNetType(Integer.valueOf(valuesans[3]));
						ans.setNetId(Integer.valueOf(valuesans[4]));
						
						
						//
						PrintWriter printWriter = new PrintWriter(LRRMOutput, true);
						
						
						if(-1 == JRRMSqlRequest.save(rsq)) {
							printWriter.println("1");
							
							
						} else {
							JRRMSqlRequest.update(ans);
							printWriter.println(answer);
						}
					}
				
			//} else {
			//	System.out.println("字符串["+strLine+"]解析失败！");
			//}

			
				LRRMInput.close();
				LRRMOutput.close();
				LRRMSocket.close();
				System.out.println("This socket is closed!");
			} catch(IOException e) {
				e.printStackTrace();
			}
	}
 }