package com.zac.algorithm;

import com.zac.bean.*;

public class tableBasedImportance {
	
	public static int twoDev (UserBean user, TrafficBean traffic) {
		int i = user.getUserPriority(), j = traffic.getTrafficPriority();
		int twoDevImportance;
		
		//��ά����Ҫ���㷨
		twoDevImportance = (i + j - 1) * (i + j - 2) / 2 + i;  		
		return twoDevImportance;
	}
	
	public static int twoDev (int i, int j) {
		int twoDevImportance;
		
		//��ά����Ҫ���㷨
		twoDevImportance = (i + j - 1) * (i + j - 2) / 2 + i;  		
		return twoDevImportance;
	}
	
	public static int threeDev (UserBean user, TrafficBean traffic, MissionBean mission) {
		int i = user.getUserPriority(), j = traffic.getTrafficPriority(), k = mission.getMissionPriority();
		int threeDevImportance, p = i + j + k;

		//��ά����Ҫ���㷨
		threeDevImportance = (p - 1) * (p - 2) * (p - 3) / 6 + (2 * p - i - 2) * (i - 1) / 2 + j;
		return threeDevImportance;
	}
	
	public static int threeDev (int i, int j, int k) {
		int threeDevImportance, p = i + j + k;

		//��ά����Ҫ���㷨
		threeDevImportance = (p - 1) * (p - 2) * (p - 3) / 6 + (2 * p - i - 2) * (i - 1) / 2 + j;
		return threeDevImportance;
	}
	
	
	public static void main (String[] args) {
		int i, j;
		UserBean user = new UserBean();
		TrafficBean traffic = new TrafficBean();
		MissionBean mission = new MissionBean();
		
		//��֤��ά���㷨
		for (i = 1; i <= 4; i++) {
			for (j = 1; j <= 4; j++) {
				user.setUserPriority(i);
				traffic.setTrafficPriority(j);
				//mission.setMissionPriority(k);
				System.out.print(twoDev(user, traffic) + " ");
			}
			System.out.println();
		}
		
		//��֤��ά���㷨
		System.out.println("��ά���㷨��");
		user.setUserPriority(2);
		traffic.setTrafficPriority(1);
		mission.setMissionPriority(1);		
		System.out.println(threeDev(user, traffic, mission));
		
		user.setUserPriority(1);
		traffic.setTrafficPriority(2);
		mission.setMissionPriority(1);		
		System.out.println(threeDev(user, traffic, mission));
		
		user.setUserPriority(1);
		traffic.setTrafficPriority(1);
		mission.setMissionPriority(2);		
		System.out.println(threeDev(user, traffic, mission));
		
		user.setUserPriority(1);
		traffic.setTrafficPriority(1);
		mission.setMissionPriority(1);		
		System.out.println(threeDev(user, traffic, mission));
	}
	
}