package com.zac.bean;

public class UserBean {    //UserBean类定义用户类型的一些信息
	private int userID;                     //用户编号
	private String userTitle;            //用户名称
	
	private int userClassification;   //用户级别
	private int userSecurityPolicy;  //用户安全策略等级
	
	private int userPriority;             //用户子优先级
	
	/*
	 * 下面定义get和set函数用于获取和设置各属性
	 */
	//用户编号
	public int getUserID () {
		return this.userID;
	}
	public void setUserID (int userID) {
		this.userID = userID;
	}
	//用户名称
	public String getUserTitle () {
		return this.userTitle;
	}
	public void setUserTitle (String userTitle) {
		this.userTitle = userTitle;
	}
	//用户类型
	public int getUserClassification () {
		return this.userClassification;
	}
	public void setUserClassification (int userClassification) {
		this.userClassification = userClassification;
	}	
	//用户安全策略等级
	public int getUserSecurityPolicy () {
		return this.userSecurityPolicy;
	}
	public void setUserSecurityPolicy (int userSecurityPolicy) {
		this.userSecurityPolicy = userSecurityPolicy;
	}
	//用户子优先级
	public int getUserPriority () {
		return this.userPriority;
	}
	public void setUserPriority (int userPriority) {
		this.userPriority = userPriority;
	}
}
