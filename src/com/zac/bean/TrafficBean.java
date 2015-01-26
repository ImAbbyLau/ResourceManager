package com.zac.bean;

public class TrafficBean {    //TrafficBean类定义业务类型的一些信息
	private int  trafficID;                       //业务编号
	private String trafficTitle;               //业务名称
	
	//以下为业务的各种QoS参数
	private double latency;                  //等待时间
	private double jitter;                      //抖动
	private double packetLossRatio;   //丢包率
	
	//根据QoS参数计算出的业务子优先级
	private int trafficPriority;
	
	/*
	 * 下面定义get和set函数用于获取和设置各属性
	 */
	//业务编号
	public int getTrafficID () {
		return this.trafficID;
	}
	public void setTrafficID (int trafficID) {
		this.trafficID = trafficID;
	}
	//业务名称
	public String getTrafficTitle () {
		return this.trafficTitle;
	}
	public void setTrafficTitle (String trafficTitle) {
		this.trafficTitle = trafficTitle;
	}
	//等待时间
	public double getLatency () {
		return this.latency;
	}
	public void setLatency (double latency) {
		this.latency = latency;
	}
	//抖动
	public double getJitter () {
		return this.jitter;
	}
	public void setJitter (double jitter) {
		this.jitter = jitter;
	}	
	//丢包率
	public double getPacketLossRatio () {
		return this.packetLossRatio;
	}
	public void setPacketLossRatio (double packetLossRatio) {
		this.packetLossRatio = packetLossRatio;
	}
	//业务子优先级
	public int getTrafficPriority () {
		return this.trafficPriority;
	}
	public void setTrafficPriority (int trafficPriority) {
		this.trafficPriority = trafficPriority;
	}
}