package com.jzh.JRRM;


public class ResourceBean {    //Resource�ඨ��ҵ�����͵�һЩ��Ϣ
	private String  Address;                       //IP��ַ
	private int NetworkType;               //��������
	private int NetworkID;					//������
	//����Ϊҵ��ĸ���QoS����
	private double BandWidth; 
	private double Delay;
	private double Jitter;
	private double PacketLoss; 
	private double LoadRate; //

	

	public ResourceBean(String address, int networkType, int networkID,double loadRate,
			double bandWidth, double delay, double jitter, double packetLoss
			) {
		super();
		Address = address;
		NetworkType = networkType;
		NetworkID = networkID;
		BandWidth = bandWidth;
		Delay = delay;
		Jitter = jitter;
		PacketLoss = packetLoss;
		LoadRate = loadRate;
	}
	
	public ResourceBean() {
		super();
		// TODO Auto-generated constructor stub
	}



	/*
	 * ���涨��get��set�������ڻ�ȡ�����ø�����
	 */
	//������
	/*public int getSubnetID () {
		return this.SubnetID;
	}
	public void setSubnetID (int SubnetID) {
		this.SubnetID = SubnetID;
	}
	*/
	//IP��ַ
	public String getAddress () {
		return this.Address;
	}
	public void setAddress (String Address) {
		this.Address = Address;
	}
	public int getNetworkType () {
		return this.NetworkType;
	}
	public void setNetworkType (int NetworkType) {
		this.NetworkType = NetworkType;
	}
	public int getNetworkID () {
		return this.NetworkID;
	}
	public void setNetworkID (int NetworkID) {
		this.NetworkID = NetworkID;
	}
	//����
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
	public double getLoadRate () {
		return this.LoadRate;
	}
	public void setLoadRate (double LoadRate) {
		this.LoadRate = LoadRate;
	}
}