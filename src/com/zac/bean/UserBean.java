package com.zac.bean;

public class UserBean {    //UserBean�ඨ���û����͵�һЩ��Ϣ
	private int userID;                     //�û����
	private String userTitle;            //�û�����
	
	private int userClassification;   //�û�����
	private int userSecurityPolicy;  //�û���ȫ���Եȼ�
	
	private int userPriority;             //�û������ȼ�
	
	/*
	 * ���涨��get��set�������ڻ�ȡ�����ø�����
	 */
	//�û����
	public int getUserID () {
		return this.userID;
	}
	public void setUserID (int userID) {
		this.userID = userID;
	}
	//�û�����
	public String getUserTitle () {
		return this.userTitle;
	}
	public void setUserTitle (String userTitle) {
		this.userTitle = userTitle;
	}
	//�û�����
	public int getUserClassification () {
		return this.userClassification;
	}
	public void setUserClassification (int userClassification) {
		this.userClassification = userClassification;
	}	
	//�û���ȫ���Եȼ�
	public int getUserSecurityPolicy () {
		return this.userSecurityPolicy;
	}
	public void setUserSecurityPolicy (int userSecurityPolicy) {
		this.userSecurityPolicy = userSecurityPolicy;
	}
	//�û������ȼ�
	public int getUserPriority () {
		return this.userPriority;
	}
	public void setUserPriority (int userPriority) {
		this.userPriority = userPriority;
	}
}
