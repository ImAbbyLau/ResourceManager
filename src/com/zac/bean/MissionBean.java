package com.zac.bean;

public class MissionBean {    //MissionBean�ඨ���������͵�һЩ��Ϣ
	private int missionID;                               //������
	private String missionTitle;                      //��������
	
	private double missionBandWidth;        //�������Ҫ��
	private double missionReliability;           //����ɿ���
	private double missionResponseTime;   //������Ӧʱ��
	
	private int missionPriority;                       //���������ȼ�
	
	/*
	 * ���涨��get��set�������ڻ�ȡ�����ø�����
	 */
	//������
	public int getMissionID () {
		return this.missionID;
	}
	public void setMissionID (int missionID) {
		this.missionID = missionID;
	}
	//��������
	public String getMissionTitle () {
		return this.missionTitle;
	}
	public void setMissionTitle (String missionTitle) {
		this.missionTitle = missionTitle;
	}	
	//�������Ҫ��
	public double getMissionBandWidth () {
		return this.missionBandWidth;
	}
	public void setMissionBandWidth (double missionBandWidth) {
		this.missionBandWidth = missionBandWidth;
	}
	//����ɿ���
	public double getMissionReliability () {
		return this.missionReliability;
	}
	public void setMissionReliability (double missionReliability) {
		this.missionReliability = missionReliability;
	}
	//������Ӧʱ��
	public double getMissionResponseTime () {
		return this.missionResponseTime;
	}
	public void setMissionResponseTime (double missionResponseTime) {
		this.missionResponseTime = missionResponseTime;
	}
	//���������ȼ�
	public int getMissionPriority () {
		return this.missionPriority;
	}
	public void setMissionPriority (int missionPriority) {
		this.missionPriority = missionPriority;
	}	
}