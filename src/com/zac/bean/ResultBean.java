package com.zac.bean;

public class ResultBean {
	private int resultID;          //������
	
	private String trafficTitleofResult;     //ҵ������
	private String userTitleofResult;        //�û�����
	private String missionTitleofResult;  //��������
	
	private int twoDevImp;         //��ά������Ҫ�Գ̶�ֵ
	private int threeDevImp;      //��ά������Ҫ�Գ̶�ֵ
	private int MADMDevImp;   //MADM������Ҫ�Գ̶�����
	private double MADMValue;  //MADM������Ҫ�Գ̶�ֵ
	
	//������
	public int getResultID () {
		return this.resultID;
	}
	
	public void setResultID (int resultID) {
		this.resultID = resultID;
	}
	
	//ҵ������
	public String getTrafficTitleofResult () {
		return this.trafficTitleofResult;
	}
	
	public void setTrafficTitleofResult (String trafficTitleofResult) {
		this.trafficTitleofResult = trafficTitleofResult;
	}
	
	//�û�����
	public String getUserTitleofResult () {
		return this.userTitleofResult;
	}	
	
	public void setUserTitleofResult (String userTitleofResult) {
		this.userTitleofResult = userTitleofResult;
	}
	
	//��������
	public String getMissionTitleofResult () {
		return this.missionTitleofResult;
	}
	
	public void setMissionTitleofResult (String missionTitleofResult) {
		this.missionTitleofResult = missionTitleofResult;
	}
	
	//��ά������Ҫ�Գ̶�ֵ
	public int getTwoDevImp () {
		return this.twoDevImp;
	}
	
	public void setTwoDevImp (int twoDevImp) {
		this.twoDevImp = twoDevImp;
	}
	
	//��ά������Ҫ�Գ̶�ֵ
	public int getThreeDevImp () {
		return this.threeDevImp;
	}
	
	public void setThreeDevImp (int threeDevImp) {
		this.threeDevImp = threeDevImp;
	}
	
	//MADM������Ҫ�Գ̶�����
	public int getMADMDevImp () {
		return this.MADMDevImp;
	}
	
	public void setMADMDevImp (int MADMDevImp) {
		this.MADMDevImp = MADMDevImp;
	}
	
	//MADM������Ҫ�Գ̶�ֵ
	public double getMADMValue () {
		return this.MADMValue;
	}
	
	public void setMADMValue (double MADMValue) {
		this.MADMValue = MADMValue;
	}
	
}