package com.zac.bean;

public class ResultBean {
	private int resultID;          //结果编号
	
	private String trafficTitleofResult;     //业务名称
	private String userTitleofResult;        //用户名称
	private String missionTitleofResult;  //任务名称
	
	private int twoDevImp;         //二维评估重要性程度值
	private int threeDevImp;      //三维评估重要性程度值
	private int MADMDevImp;   //MADM评估重要性程度排名
	private double MADMValue;  //MADM评估重要性程度值
	
	//结果编号
	public int getResultID () {
		return this.resultID;
	}
	
	public void setResultID (int resultID) {
		this.resultID = resultID;
	}
	
	//业务名称
	public String getTrafficTitleofResult () {
		return this.trafficTitleofResult;
	}
	
	public void setTrafficTitleofResult (String trafficTitleofResult) {
		this.trafficTitleofResult = trafficTitleofResult;
	}
	
	//用户名称
	public String getUserTitleofResult () {
		return this.userTitleofResult;
	}	
	
	public void setUserTitleofResult (String userTitleofResult) {
		this.userTitleofResult = userTitleofResult;
	}
	
	//任务名称
	public String getMissionTitleofResult () {
		return this.missionTitleofResult;
	}
	
	public void setMissionTitleofResult (String missionTitleofResult) {
		this.missionTitleofResult = missionTitleofResult;
	}
	
	//二维评估重要性程度值
	public int getTwoDevImp () {
		return this.twoDevImp;
	}
	
	public void setTwoDevImp (int twoDevImp) {
		this.twoDevImp = twoDevImp;
	}
	
	//三维评估重要性程度值
	public int getThreeDevImp () {
		return this.threeDevImp;
	}
	
	public void setThreeDevImp (int threeDevImp) {
		this.threeDevImp = threeDevImp;
	}
	
	//MADM评估重要性程度排名
	public int getMADMDevImp () {
		return this.MADMDevImp;
	}
	
	public void setMADMDevImp (int MADMDevImp) {
		this.MADMDevImp = MADMDevImp;
	}
	
	//MADM评估重要性程度值
	public double getMADMValue () {
		return this.MADMValue;
	}
	
	public void setMADMValue (double MADMValue) {
		this.MADMValue = MADMValue;
	}
	
}