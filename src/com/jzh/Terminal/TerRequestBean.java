package com.jzh.Terminal;


public class TerRequestBean {    //Resource类定义业务类型的一些信息
	private String  LRRMIP;//连接申请的IP地址
	private double  BandWidth;//带宽
	private double  Delay;
	private double  Jitter;
	private double  PacketLoss;
	private int  TrafficImportance;
	private int  TrafficType;
	private int  Preference;
	private String  VisibleNet;
	private int RequestId;
	private int  NetType;
	private int  NetId;
	
	public String getLRRMIP () {
		return this.LRRMIP;
	}
	public void setLRRMIP (String LRRMIP) {
		this.LRRMIP = LRRMIP;
	}
	
	public double getBandWidth () {
		return this.BandWidth;
	}
	public void setBandWidth (double BandWidth) {
		this.BandWidth = BandWidth;
	}
	public double getDelay () {
		return this.Delay;
	}
	public void setDelay (double Delay) {
		this.Delay = Delay;
	}
	public double getJitter () {
		return this.Jitter;
	}
	public void setJitter (double Jitter) {
		this.Jitter = Jitter;
	}
	public double getPacketLoss () {
		return this.PacketLoss;
	}
	public void setPacketLoss (double PacketLoss) {
		this.PacketLoss = PacketLoss;
	}
	public int getTrafficImportance () {
		return this.TrafficImportance;
	}
	public void setTrafficImportance (int TrafficImportance) {
		this.TrafficImportance = TrafficImportance;
	}
	public int getTrafficType () {
		return this.TrafficType;
	}
	public void setTrafficType (int TrafficType) {
		this.TrafficType = TrafficType;
	}
	public int getPreference () {
		return this.Preference;
	}
	public void setPreference (int Preference) {
		this.Preference = Preference;
	}
	public String getVisibleNet () {
		return this.VisibleNet;
	}
	public void setVisibleNet (String VisibleNet) {
		this.VisibleNet = VisibleNet;
	}
	public int getRequestId () {
		return this.RequestId;
	}
	public void setRequestId (int RequestId) {
		this.RequestId = RequestId;
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
	
}
