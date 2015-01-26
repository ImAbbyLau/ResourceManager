package com.zac.bean;

public class MissionBean {    //MissionBean类定义任务类型的一些信息
	private int missionID;                               //任务编号
	private String missionTitle;                      //任务名称
	
	private double missionBandWidth;        //任务带宽要求
	private double missionReliability;           //任务可靠性
	private double missionResponseTime;   //任务响应时间
	
	private int missionPriority;                       //任务子优先级
	
	/*
	 * 下面定义get和set函数用于获取和设置各属性
	 */
	//任务编号
	public int getMissionID () {
		return this.missionID;
	}
	public void setMissionID (int missionID) {
		this.missionID = missionID;
	}
	//任务名称
	public String getMissionTitle () {
		return this.missionTitle;
	}
	public void setMissionTitle (String missionTitle) {
		this.missionTitle = missionTitle;
	}	
	//任务带宽要求
	public double getMissionBandWidth () {
		return this.missionBandWidth;
	}
	public void setMissionBandWidth (double missionBandWidth) {
		this.missionBandWidth = missionBandWidth;
	}
	//任务可靠性
	public double getMissionReliability () {
		return this.missionReliability;
	}
	public void setMissionReliability (double missionReliability) {
		this.missionReliability = missionReliability;
	}
	//任务响应时间
	public double getMissionResponseTime () {
		return this.missionResponseTime;
	}
	public void setMissionResponseTime (double missionResponseTime) {
		this.missionResponseTime = missionResponseTime;
	}
	//任务子优先级
	public int getMissionPriority () {
		return this.missionPriority;
	}
	public void setMissionPriority (int missionPriority) {
		this.missionPriority = missionPriority;
	}	
}