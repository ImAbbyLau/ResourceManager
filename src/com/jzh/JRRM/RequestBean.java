package com.jzh.JRRM;


public class RequestBean {    //Resource�ඨ��ҵ�����͵�һЩ��Ϣ
	private String  Address;                       //IP��ַ
	private int PackageID;               //ҵ����
	private int TrafficType;
	private int TrafficImportance;
	private int Preference;
	private String  VisibleNet;
	
	//����Ϊҵ��ĸ���QoS����
	private double BandWidth; 
	private double Delay;
	private double Jitter;
	private double PacketLoss;
	private int  NetType;
	private int  NetId;
	private String  LRRMIP;
	//


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
	
	public int getPackageID () {
		return this.PackageID;
	}
	public void setPackageID (int PackageID) {
		this.PackageID = PackageID;
	}
	public int getTrafficType () {
		return this.TrafficType;
	}
	public void setTrafficType (int TrafficType) {
		this.TrafficType = TrafficType;
	}
	public int getTrafficImportance () {
		return this.TrafficImportance;
	}
	public void setTrafficImportance (int TrafficImportance) {
		this.TrafficImportance = TrafficImportance;
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
	//Qos����
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
	public String getLRRMIP () {
		return this.LRRMIP;
	}
	public void setLRRMIP (String LRRMIP) {
		this.LRRMIP = LRRMIP;
	}
	

}