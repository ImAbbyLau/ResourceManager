package com.jzh.JRRM;


public class PriorityBean {   

          
	private int  NetType;
	private int  NetId;
	private String  NetIP;
	private double PacketLoss;
	private double EvaPoint;
	public PriorityBean(double evapoint,int netType, int netId, String netIP, double packetLoss) {
		super();
		EvaPoint = evapoint;
		NetType = netType;
		NetId = netId;
		NetIP = netIP;
		PacketLoss = packetLoss;
	}
	public double getEvaPoint () {
		return this.EvaPoint;
	}
	public void setEvaPoint (double EvaPoint) {
		this.EvaPoint = EvaPoint;
	}	
	public double getPacketLoss () {
		return this.PacketLoss;
	}
	public void setPacketLoss (double PacketLoss) {
		this.PacketLoss = PacketLoss;
	}
	public int getNetType () {
		return this.NetType;
	}
	public void setNetType (int NetType) {
		this.NetType = NetType;
	}	
	public int getNetId () {
		return this.NetId;
	}
	public void setNetId (int NetId) {
		this.NetId = NetId;
	}
	public String getNetIP () {
		return this.NetIP;
	}
	public void setNetIP (String NetIP) {
		this.NetIP = NetIP;
	}
	

}