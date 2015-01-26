package com.jzh.JRRM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
	
	Test.Search();
}





public static void Search(){
	int j=5;
	
	double[][] EVAINT = {{800,50,40,3},{2000,100,10,15},{3000,50,20,10},{1000,40,30,1},{1500,20,20,5}};
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
	
	double[][] EVATEMP =  new double[j][4];
	for(int i=0;i<j;i++){
		EVATEMP[i][0]= (EVAINT[i][0]-min[0])/(max[0]-min[0]);
	}
	for(int k=1;k<4;k++){
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
	
	//最小最大值//
	double min1=Math.abs(EVATEMP2[0][0]), max1=Math.abs(EVATEMP2[0][0]) ; 
	for(int i=0;i<j-1;i++){
		for(int k=0;k<4;k++){
			if(max1 <Math.abs(EVATEMP2[i][k])){
				max1 = Math.abs(EVATEMP2[i][k]);
			}
			if(min1 > Math.abs(EVATEMP2[i][k])){
				min1 = Math.abs(EVATEMP2[i][k]);
			}
		}
	}
	
	//计算评估值//
	double[][] EVA = new double[j-1][4];	
	double [] EVAPOINT = new double [j-1];
	for(int i=0;i<j-1;i++){
		for(int k=0;k<4;k++){
		EVA[i][k] = (EVATEMP2[i][k]+max1*0.5)/(min1+max1*0.5);
		EVAPOINT[i] = EVAPOINT[i]+EVA[i][k];
		}
		System.out.println(EVAPOINT[i]);

	}
	
	//计算排序值
	Map<Integer, Double> netPrio = new HashMap<Integer, Double>();
	for(int i=0;i<j-1;i++) {
		netPrio.put(i+1, EVAPOINT[i]);
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
	
	int[] prio = new int[netPrioLst.size()];
	for(int i = 0; i < netPrioLst.size(); i++) {
		prio[i] = netPrioLst.get(i).getKey();
		System.out.println(prio[i]);
	}
}
}