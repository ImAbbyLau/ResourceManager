package com.jzh.JRRM;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jzh.Terminal.TerminalSqlRequest;

public class NetworkEvaluation {
	private String[] NetWorkType;
	private String[] NetworkID;
	RequestBean rsq;
	int RequestTraffictype;
	int RequestTrafficImportance;
	int Preference;
	ArrayList<ResourceBean> list = new ArrayList<ResourceBean>(); 
	
	public NetworkEvaluation(RequestBean rsq) {
		this.rsq = rsq;
	}
	
	public String Search() {
		String LRRMIP = rsq.getAddress();
		//System.out.println(LRRMIP);
		String[] VisibleNets = rsq.getVisibleNet().split(",");
		RequestTraffictype = rsq.getTrafficType();
		RequestTrafficImportance = rsq.getTrafficImportance();
		Preference = rsq.getPreference();
		NetWorkType = new String[VisibleNets.length];
		NetworkID = new String[VisibleNets.length];
		for (int i = 0; i < VisibleNets.length; i++) {
			String[] TrafficMessage = VisibleNets[i].split("-");
			NetWorkType[i] = TrafficMessage[0];
			NetworkID[i] = TrafficMessage[1];
			ResourceBean res = JRRMSqlResource.SearchVisibleNet(RequestTraffictype,NetWorkType[i], NetworkID[i]);
			if (res==null){
	
				return "5"+":0:0:0:0";
			}
			list.add(res);
		}
		int j=list.size()+1;
		double[][] EVAINT = new double[j][4];
	
		EVAINT[0][0] = rsq.getDelay();
		EVAINT[0][1] = rsq.getJitter();
		EVAINT[0][2] = rsq.getPacketLoss();
		EVAINT[0][3] = rsq.getBandWidth();
		for(int k=1;k<j;k++){
		
			
			EVAINT[k][0] = list.get(k-1).getDelay();
			EVAINT[k][1] = list.get(k-1).getJitter();
			EVAINT[k][2] = list.get(k-1).getPacketLoss();
			EVAINT[k][3] = list.get(k-1).getBandWidth();
		}
		//double[] bandwidth= new double[j-1];
		//for(int k=0;k<j;k++){
		//	bandwidth[k]=EVAINT[0][0]-EVAINT[k][0];
		//}
		//if(bandwidth[k]<0)	{
			
		//}
		
		//矩阵的无量纲化//
		double[] min = new double[4];
		double[] max = new double[4];
		
		for(int k=0;k<4;k++){
			min[k]= EVAINT[0][k];
			max[k]= EVAINT[0][k];
			for(int i=1;i<j;i++){
				if(max[k]<EVAINT[i][k]){
					max[k] = EVAINT[i][k];
				}
				if(min[k]>EVAINT[i][k]){
					min[k] = EVAINT[i][k];
				}
			}
		}
		
			
		
		//改进的灰色关联度算法处理//
		double[][] EVATEMP =  new double[j][4];
		for(int i=0;i<j;i++){
			EVATEMP[i][3]= (EVAINT[i][3]-min[3])/(max[3]-min[3]);
		}
		for(int k=0;k<3;k++){
			for(int i=0;i<j;i++){
				EVATEMP[i][k]= (max[k]-EVAINT[i][k])/(max[k]-min[k]);
			}
		}	
		
		double[][] EVATEMP2 = new double[j-1][4];
		for(int k=0;k<4;k++){
			for(int i=1;i<j;i++){
				EVATEMP2[i-1][k] = EVATEMP[i][k]-EVATEMP[0][k];
			}
		}
		
		//计算最小和最大值//
		double min1=Math.abs(EVATEMP2[0][0]), max1=Math.abs(EVATEMP2[0][0]) ; 
		for(int i=0;i<j-1;i++){
			for(int k=0;k<4;k++){
				if(max1 <Math.abs(EVATEMP[i][k])){
					max1 = Math.abs(EVATEMP[i][k]);
				}
				if(min1 > Math.abs(EVATEMP[i][k])){
					min1 = Math.abs(EVATEMP[i][k]);
				}
			}
		}
		System.out.println(max1+"最大和最小"+min1);
		//读取权重数据库
		List<WeightBean> results = JRRMSqlWeight.queryAll();
		WeightBean wb = results.get(RequestTraffictype-1);
		double [] weight =new double [4];
		weight[0] = wb.getDelay();
		weight[1] = wb.getJitter();
		weight[2] = wb.getPacketLoss();
		weight[3] = wb.getBandWidth();
		System.out.println(weight[3]);
		
		//计算评估值//
		double[][] EVA = new double[j-1][4];	
		double [] EVAPOINT = new double [j-1];
		for(int i=0;i<j-1;i++){
			for(int k=0;k<4;k++){
			EVA[i][k] = (EVATEMP2[i][k]+max1*0.5)/(min1+max1*0.5);
			}
		}
		
		for(int i=0;i<j-1;i++){
				EVAPOINT[i] = EVA[i][0]*weight[0]+EVA[i][1]*weight[1]+EVA[i][2]*weight[2]+EVA[i][3]*weight[3];
				System.out.println(EVA[i][0]);
				System.out.println("第"+i+"个网络权重是"+EVAPOINT[i]);
		}
		
		
		
		
		
		
		
		
		
		//计算排序值
		Map<Integer, Double> netPrio = new HashMap<Integer, Double>();
		for(int i = 0; i < j - 1; i++) {
			netPrio.put(i + 1, EVAPOINT[i]);
		}
		List<Map.Entry<Integer, Double>> netPrioLst =
			    new ArrayList<Map.Entry<Integer, Double>>(netPrio.entrySet());

		Collections.sort(netPrioLst, new Comparator<Map.Entry<Integer, Double>>() {   
		    public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {      
		        if(o1.getValue() == o2.getValue()) {
		        	return 0;
		        } else if(o1.getValue() > o2.getValue()) {
		        	return -1;
		        } else {
		        	return 1;
		        }
		    }
		});
		
		
		
		
		
		
		
		
		
		
		//通过优先级找到对应的网络类型和ID//
		String[] NetIP = new String[netPrioLst.size()];
		int[] NetType = new int[netPrioLst.size()];
		int[] NetID = new int[netPrioLst.size()];
		int[] Prio = new int[netPrioLst.size()];
		double[] LoadRate = new double[netPrioLst.size()];
		String answer;
		
		ArrayList<PriorityBean> priolist = new ArrayList<PriorityBean>(netPrioLst.size()); 
		
		for(int i=0;i<netPrioLst.size();i++){
			Prio[i] = netPrioLst.get(i).getKey();
			NetIP[i] = list.get(Prio[i]-1).getAddress();
			NetType[i] = list.get(Prio[i]-1).getNetworkType();
			NetID[i] = list.get(Prio[i]-1).getNetworkID();
			LoadRate[i] = list.get(Prio[i]-1).getLoadRate();
			priolist.add(new PriorityBean(EVAPOINT[i],NetType[i], NetID[i], NetIP[i], LoadRate[i]));
		}
		
		
		int A=5,B=30;
		int access=-1;
		if (RequestTrafficImportance>=B){
			for(int f=priolist.size()-1;f>=0;f--){
				if(LoadRate[f]>70||EVAPOINT[f]<1){
					priolist.remove(f);
				}
			}//去掉重负载
			for(int g=0;g<priolist.size();g++){
				if(LoadRate[g]<40){
					access = g;
					break;
				}
			}
			if (access==-1){
				for(int h=0;h<priolist.size();h++){	
					if(Preference==NetType[h]){
						access = h;	
						break; 	
					}
				}
			}
			if (access==-1){
				access = 0;	
			}			
		} else if (RequestTrafficImportance<=A){
			for(int g=0;g<priolist.size();g++){
				if(Preference==NetType[g]){
					access = g;
					break;
				} 
			}
			if (access==-1){
					access = 0;
			}
		}else {
			for(int f=priolist.size()-1;f>-1;f--){
				if(LoadRate[f]>70||EVAPOINT[f]<1){
					priolist.remove(f);
				}
			for(int g=0;g<priolist.size();g++){
				PriorityBean pb = priolist.get(g);
				System.out.println(priolist.get(g));
			}
			}//去掉重负载
			for(int g=0;g<priolist.size();g++){
				if(Preference==NetType[g]){
					access = g;
					break;
				} 
			}
			if (access==-1){
				for(int h=0;h<priolist.size();h++){	
					if(LoadRate[h]<40){	
						access = h;
						break; 	
					} 
					
				}
			}
			if (access==-1){
				access = 0;
			}

		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		System.out.println("77777777777777777777777  "+access);

		answer = "4"+":"+LRRMIP+":"+priolist.get(access).getNetIP()+":"+priolist.get(access).getNetType()+":"+priolist.get(access).getNetId();
	
	return answer;
	}
		




		
		

	
	
	
}
