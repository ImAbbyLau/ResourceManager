package com.jzh.JRRM;


public class WeightBean {    

	//����Ϊҵ��ĸ���QoS����
	private double BandWidth; 
	private double Delay;
	private double Jitter;
	private double PacketLoss; 
	private int TrafficType;





	//����
	public int getTrafficType () {
		return this.TrafficType;
	}
	public void setTrafficType (int TrafficType) {
		this.TrafficType = TrafficType;
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

}