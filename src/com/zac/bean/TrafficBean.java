package com.zac.bean;

public class TrafficBean {    //TrafficBean�ඨ��ҵ�����͵�һЩ��Ϣ
	private int  trafficID;                       //ҵ����
	private String trafficTitle;               //ҵ������
	
	//����Ϊҵ��ĸ���QoS����
	private double latency;                  //�ȴ�ʱ��
	private double jitter;                      //����
	private double packetLossRatio;   //������
	
	//����QoS�����������ҵ�������ȼ�
	private int trafficPriority;
	
	/*
	 * ���涨��get��set�������ڻ�ȡ�����ø�����
	 */
	//ҵ����
	public int getTrafficID () {
		return this.trafficID;
	}
	public void setTrafficID (int trafficID) {
		this.trafficID = trafficID;
	}
	//ҵ������
	public String getTrafficTitle () {
		return this.trafficTitle;
	}
	public void setTrafficTitle (String trafficTitle) {
		this.trafficTitle = trafficTitle;
	}
	//�ȴ�ʱ��
	public double getLatency () {
		return this.latency;
	}
	public void setLatency (double latency) {
		this.latency = latency;
	}
	//����
	public double getJitter () {
		return this.jitter;
	}
	public void setJitter (double jitter) {
		this.jitter = jitter;
	}	
	//������
	public double getPacketLossRatio () {
		return this.packetLossRatio;
	}
	public void setPacketLossRatio (double packetLossRatio) {
		this.packetLossRatio = packetLossRatio;
	}
	//ҵ�������ȼ�
	public int getTrafficPriority () {
		return this.trafficPriority;
	}
	public void setTrafficPriority (int trafficPriority) {
		this.trafficPriority = trafficPriority;
	}
}