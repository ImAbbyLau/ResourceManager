package com.zac.algorithm;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.zac.bean.*;
import com.zac.sql.*;

public class madmBasedImportance {
	//�õ�ҵ�����Բ���
	private static List<TrafficBean> trafResults;
	private static List<UserBean> userResults;
	private static List<MissionBean> missionResults;
	
	private static int numOfTraf;
	private static int numOfUser;
	private static int numOfMission;
	private static int numOfService;
	
	//����ģ���жϾ���
	public static double[][] fuzzyJudgementMatrix = new double[][] {
			{0.5, 0.3, 0.1},
			{0.7, 0.5, 0.7},
			{0.9, 0.3, 0.5}
	};  //Ĭ�ϵ���ֵ
	
	/**
	 * ����ģ���жϾ��������һ����ָ��
	 * 
	 * @param fuzzyJudgementMatrix
	 * @return p
	 */
	private static double consistencyChecking(double[][] fuzzyJudgementMatrix) {
		int n = fuzzyJudgementMatrix.length;
		double ratio = 2.0 / (n * (n - 1) * (n - 2));
		double p = 0.0;
		for(int i = 0; i < n - 1; i++) {
			for(int j = i + 1; j < n; j++) {
				for(int k = 0; k < n; k++) {
					if(k != i && k!= j) {
						p += Math.abs(fuzzyJudgementMatrix[i][j] - (fuzzyJudgementMatrix[i][k] + fuzzyJudgementMatrix[k][j] - 0.5));
					}
				}
			}
		}
		p *= ratio;
		return p;
	}
	
	/**
	 * �ж�ģ���жϾ����Ƿ�����һ����ָ��
	 * 
	 * @param fuzzyJudgementMatrix
	 * @return boolean result
	 */
	public static boolean isConsistency(double[][] fuzzyJudgementMatrix) {
		if(consistencyChecking(fuzzyJudgementMatrix) <= 0.2)
			return true;
		else
			return false;
	}
	
	/**
	 * �Բ�������ȫһ���Ե�ģ���о����������
	 * 
	 * @param fuzzyJudgementMatrix
	 * @return double[][] fjm
	 */
	private static double[][] adjustMatrix(double[][] fuzzyJudgementMatrix) {
		int n = fuzzyJudgementMatrix.length;
		double[] r = new double[n];
		Arrays.fill(r, 0.0);
		for(int i = 0; i < n; i++) {
			for(int k = 0; k < n; k++) {
				r[i] += fuzzyJudgementMatrix[i][k];
			}
		}
		
		double[][] fjm = new double[n][n];
		for(int i = 0; i < n; i++) {
			Arrays.fill(fjm[i], 0.0);
		}
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				fjm[i][j] = (r[i] - r[j]) / (2.0 * (n - 1)) + 0.5;
			}
		}
		
		return fjm;
	}
	
	/**
	 * ������Ȩ��������һ����������
	 * 
	 * @param dimension
	 * @return weightVector
	 */
	private static double[] getWeightVector (int dimension) {
		int i, j;
		double[] rowSum = new double[3];
		double[] weightVector = new double[3];
		
		//�����к�
		for(i = 0; i < dimension; i++) {
			rowSum[i] = 0.0f;
			for(j = 0; j < dimension; j++) {
				rowSum[i] +=fuzzyJudgementMatrix[i][j];
			}
		}

		//����ͨ�ù�ʽ����Ȩ������
		for(i = 0; i < dimension; i++) {
			weightVector[i] = (rowSum[i] + (dimension / 2) - 1) / (dimension * (dimension - 1));
		}
		
		return weightVector;
	}
	
	/**
	 * �����ݻ�ȡҵ����Ϣ������static����
	 * 
	 * @return null
	 */
	private static void getInfo () {
		trafResults = TrafficOp.queryAll();
		userResults = UserOp.queryAll();
		missionResults = MissionOp.queryAll();
		
		numOfTraf = trafResults.size();
		numOfUser = userResults.size();
		numOfMission = missionResults.size();
		numOfService = numOfTraf * numOfUser * numOfMission;
	}
	
	/**
	 * ����ҵ������ֵ
	 *  
	 * @return trafAttValue
	 */
	private static double[] getTrafAttValue () {
		double[] trafAttValue = new double[numOfTraf]; //ҵ������ֵ����
		
		//����ʵʱҵ��ͷ�ʵʱҵ��
		List<TrafficBean> realTraf = new LinkedList<TrafficBean>(); //ʵʱҵ��
		List<TrafficBean> nonRealTraf = new LinkedList<TrafficBean>(); //��ʵʱҵ��
		boolean[] isReal = new boolean[numOfTraf] ; //ʵʱ���ʵʱҵ�������飬���ڵȴ�ʱ�Ӵ��ڵ���1000ms�Ҷ������ڵ���100ms�ı��Ϊ��ʵʱҵ��
		
		for(int i = 0; i < numOfTraf; i++) {
			isReal[i] = true;
			if(trafResults.get(i).getLatency() >= 1000.0 && trafResults.get(i).getJitter() >= 100.0) {
				isReal[i] = false;
				}
			if(true == isReal[i]) {
				realTraf.add(trafResults.get(i));
			} else {
				nonRealTraf.add(trafResults.get(i));
			}
		}
		
		//����ʵʱҵ������ֵ
		double[] realTrafAttValue = new double[realTraf.size()];
		for(int i = 0; i < realTraf.size(); i++) {
			realTrafAttValue[i] = -Math.log(realTraf.get(i).getPacketLossRatio()) / (realTraf.get(i).getJitter() * realTraf.get(i).getLatency());
		}
		double maxOfRealValue = realTrafAttValue[0];
		for(int i = 1; i < realTraf.size(); i++) {
			if(realTrafAttValue[i] > maxOfRealValue) {
				maxOfRealValue = realTrafAttValue[i];
			}
		}
		
		//�����ʵʱҵ������ֵ
		int[] nonRealTrafAttValue = new int[nonRealTraf.size()];
		for(int i = 0; i < nonRealTraf.size(); i++) {
			nonRealTrafAttValue[i] = nonRealTraf.get(i).getTrafficPriority();
		}
		int minOfNonRealValue = nonRealTrafAttValue[0];
		for(int i = 0; i < nonRealTraf.size(); i++) {
			if(nonRealTrafAttValue[i] < minOfNonRealValue) {
				minOfNonRealValue = nonRealTrafAttValue[i];
			}
		}
		
		//��һ��ʵʱҵ�����ʵʱҵ��
		double realWeight = 1.0; //ʵʱҵ��Ȩ��
		double nonRealWeight = 0.5; //��ʵʱҵ��Ȩ��
		for(int i = 0, j = 0, k = 0; i < numOfTraf; i++) {
			if(true == isReal[i]) {
				trafAttValue[i] = realWeight * realTrafAttValue[j] / maxOfRealValue;
				j++;
			} else {
				trafAttValue[i] = nonRealWeight * minOfNonRealValue / nonRealTrafAttValue[k];
				k++;
			}
		}
		//��������ֵ	
		return trafAttValue;
	}
	
	/**
	 * �����û�����ֵ
	 *  
	 * @return userAttValue
	 */
	private static double[] getUserAttValue () {
		double[] userAttValue = new double[numOfUser]; //�û�����ֵ����
		
		//�����û�����ֵ
		for(int i = 0; i < numOfUser; i++) {
			userAttValue[i] = 1.0 / (userResults.get(i).getUserClassification() * userResults.get(i).getUserSecurityPolicy());
		}
		//�����û�����ֵ
		return userAttValue;
	}
	
	/**
	 * ������������ֵ
	 *  
	 * @return missionAttValue
	 */
	private static double[] getMissionAttValue () {
		double[] missionAttValue = new double[numOfMission]; //�û�����ֵ����
		
		double[] weight = new double[] {0.25, 0.25, 0.25, 0.25}; //��������Ȩ���������������ͬȨֵ
		
		double[][] decisionMatrixOfMission = new double[numOfMission][4]; //�������Ծ��߾���
		for(int i = 0; i < numOfMission; i++) {
			decisionMatrixOfMission[i][0] = missionResults.get(i).getMissionPriority(); //�������ʹ�����������ȼ���ֵ
			decisionMatrixOfMission[i][1] = missionResults.get(i).getMissionBandWidth(); //�����������
			decisionMatrixOfMission[i][2] = missionResults.get(i).getMissionReliability(); //����ɿ��Եȼ�
			decisionMatrixOfMission[i][3] = missionResults.get(i).getMissionResponseTime(); //������Ӧʱ��Ҫ��
		}
		
		//���߾���
		double[] normalizationParam = new double[4];
		boolean[] isBenefitAttribute = new boolean[] {false, true, true, false}; //���Ч�������Ի�ɱ�������
		//����淶�������������С����ֵ
		for(int j = 0; j < 4; j++) {
			normalizationParam[j] = decisionMatrixOfMission[0][j];
			if(true == isBenefitAttribute[j]) {
				for(int i = 1; i < numOfMission; i++) {
					if(decisionMatrixOfMission[i][j] > normalizationParam[j]) {
						normalizationParam[j] = decisionMatrixOfMission[i][j];
					}
				}
			} else {
				for(int i = 1; i < numOfMission; i++) {
					if(decisionMatrixOfMission[i][j] < normalizationParam[j]) {
						normalizationParam[j] = decisionMatrixOfMission[i][j];
					}
				}
			}
		}
		
		//�����һ�����߾���
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < numOfMission; i++) {
				if(true == isBenefitAttribute[j]) {
					decisionMatrixOfMission[i][j] = decisionMatrixOfMission[i][j] / normalizationParam[j];
				} else {
					decisionMatrixOfMission[i][j] = normalizationParam[j] / decisionMatrixOfMission[i][j];
				}
			}
		}
		
		//������������ֵ
		for(int i = 0; i < numOfMission; i++) {
			missionAttValue[i] = 0.0;
			for(int j = 0; j < 4; j++) {
				missionAttValue[i] += decisionMatrixOfMission[i][j] * weight[j];
			}
		}
		//������������ֵ
		return missionAttValue;
	}
	
	/**
	 * �������߾���
	 * 
	 * @return decisionMatrix
	 */
	private static double[][] getDecisionMatrix () {
		double[][] decisionMatrix = new double[numOfService][3]; //���߾���
		
		double[] trafAttValue = getTrafAttValue();
		double[] userAttValue = getUserAttValue();
		double[] missionAttValue = getMissionAttValue();
		
		//��ȡ���߾���
		for(int i = 0; i < numOfTraf; i++) {
			for(int j = 0; j < numOfUser; j++) {
				for(int  k = 0; k < numOfMission; k++) {
					decisionMatrix[i * numOfUser * numOfMission + j * numOfMission + k][0] = trafAttValue[i];
					decisionMatrix[i * numOfUser * numOfMission + j * numOfMission + k][1] = userAttValue[j];
					decisionMatrix[i * numOfUser * numOfMission + j * numOfMission + k][2] = missionAttValue[k];
				}
			}
		}
		
		//��һ�����߾���
		double[] maxOfAtt = new double[3];
		for(int j = 0; j < 3; j++) {
			maxOfAtt[j] = 0.0;
			for(int i = 0; i < numOfService; i++) {
				if(decisionMatrix[i][j] > maxOfAtt[j]) {
					maxOfAtt[j] = decisionMatrix[i][j];
				}
			}
		}
		
		for(int j = 0; j < 3; j++) {
			for(int i = 0; i < numOfService; i++) {
				decisionMatrix[i][j] = decisionMatrix[i][j] / maxOfAtt[j];
			}
		}
		
		//���ؾ��߾���
		return decisionMatrix;
	}
	
	/**
	 * �����ۺ�����ֵ
	 * 
	 * @return madmImportanceOfValue
	 */
	public static double[] getMADMImportanceOfValue () {
		double[] madmImportanceOfValue =  new double[numOfService]; //MADM��Ҫ������������飨��ֵ�����
		
		double[][] decisionMatrix = getDecisionMatrix(); //��ȡ���߾���
		double[] weighVector = getWeightVector(3); //��ȡȨ��������һ����������
		
		//�����ۺ�����ֵ
		for(int i = 0; i < numOfService; i++) {
			madmImportanceOfValue[i] = 0.0;
			for(int j = 0; j < 3; j++) {
				madmImportanceOfValue[i] += decisionMatrix[i][j] * weighVector[j];
			}
		}		
		
		return madmImportanceOfValue;
	}
	
	/**
	 * �����ۺ�������
	 * 
	 * @return madmImportance
	 */
	public static int[] getMADMImportance () {
		getInfo(); //�����ݿ��ȡҵ����Ϣ
		int[] madmImportance = new int[numOfService]; //MADM��Ҫ������������飨��������
		
		double[] value = getMADMImportanceOfValue();
		
		
		double[] valueSort = value.clone();
		Arrays.sort(valueSort);
		
		List<ImpValue> imp = new LinkedList<ImpValue>();
		int index = 1;
		for(double data : value) {
			imp.add(new ImpValue(data, index++, 0));
		}
		Collections.sort(imp, new ComparatorOfValue());		
		for(int i =0; i < numOfService; i++) {
			imp.get(i).setOrderNumber(numOfService - i);
		}
		Collections.sort(imp, new ComparatorOfInitNum());
		for(int i =0; i < numOfService; i++) {
			madmImportance[i] = imp.get(i).getOrderNumber();
		}
		
		/*
		for(int i = 0; i < numOfService; i++) {
			madmImportance[i] = (int) Math.round(value[i] * 64);
		}
		*/
		
		return madmImportance;
	}
	
	public static void main (String[] args) {
		getInfo();
		//����ģ���жϾ����һ����
//		 double[][] matrix = new double[][] {
//			{0.5, 0.417, 0.833, 0.583},
//			{0.583, 0.5, 0.917, 0.667},
//			{0.167, 0.083, 0.5, 0.25},
//			{0.417, 0.333, 0.75, 0.5}
//		 };  //Ĭ�ϵ���ֵ
		double[][] matrix = new double[][] {
				{0.5, 0.8, 0.7},
				{0.2, 0.5, 0.8},
				{0.3, 0.2, 0.5}
		};
		System.out.println("�жϾ���");
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("����һ����ָ�� \u03c1=" + consistencyChecking(matrix));
		if(isConsistency(matrix)) {
			System.out.println("\u03c1 <= 0.2,���жϾ���ﵽ����һ����Ҫ��");
		} else {
			System.out.println("\u03c1 > 0.2,���жϾ���δ�ﵽ����һ����Ҫ��");
		}

		/*
		//if(!isConsistency(matrix)) {
			matrix = adjustMatrix(matrix);
			int n =matrix.length;
			//System.out.println("������һ���ԣ�������");
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++)
					System.out.print(matrix[i][j] + " ");
				System.out.println();
			}	
		//}
		
		
		
		
		int index = 0;
		System.out.println("�������ֵ��");
		for (TrafficBean tempTraf : trafResults) {
			for (UserBean tempUser : userResults) {
				for (MissionBean tempMission : missionResults) {
					System.out.println(tempTraf.getTrafficTitle() + "|" +  tempUser.getUserTitle() + "|" +   tempMission.getMissionTitle()
							+ "|" +   getMADMImportanceOfValue()[index] + "|" +   getMADMImportance()[index]);
					index++;
				}
			}
		}		
		System.out.println();
		
		System.out.println("ҵ������ֵ��");
		index = 0;
		for (TrafficBean tempTraf : trafResults) {
			System.out.println(tempTraf.getTrafficTitle() + "|" +   getTrafAttValue()[index]);
			index++;
		}
		System.out.println();
		
		System.out.println("�û�����ֵ��");
		index = 0;
		for (UserBean tempUser : userResults) {
			System.out.println(tempUser.getUserTitle() + "|" +   getUserAttValue()[index]);
			index++;
		}
		System.out.println();
		
		System.out.println("��������ֵ��");		
		index = 0;
		for (MissionBean tempMission : missionResults) {
			System.out.println(tempMission.getMissionTitle() + "|" +   getMissionAttValue()[index]);
			index++;
		}		
		System.out.println();
		
		System.out.println("��һ���ľ��߾���");
		double[][] arr= getDecisionMatrix();
		for(int i = 0; i < numOfService; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.print(arr[i][j] + "|");
			}
			System.out.println();
		}
		
		*/
	}
	
}


class ImpValue {
	private double value; //��Ҫ��ֵ
	private int initialNumber; //ԭʼ˳��
	private int orderNumber; //������ţ���Ҫ��ֵԽ�����ԽС
	
	//���캯��
	ImpValue (double value, int initialNumber, int orderNumber) {
		this.value = value;
		this.initialNumber = initialNumber;
		this.orderNumber = orderNumber;
	}
	//�޲ι��캯��
	ImpValue () {	
	}
	
	double getValue () {
		return this.value;
	}
	
	void setValue(double value) {
		this.value = value;
	}

	int getInitialNumber () {
		return this.initialNumber;
	}
	
	void setInitialNumber (int initialNumber) {
		this.initialNumber = initialNumber;
	}
	
	int getOrderNumber () {
		return this.orderNumber;
	}
	
	void setOrderNumber (int orderNumber) {
		this.orderNumber = orderNumber;
	}
}

//����ֵ�ıȽ���
class ComparatorOfValue implements Comparator<ImpValue> {
	@Override
	public int compare(ImpValue i1, ImpValue i2) {
		if(i1.getValue() == i2.getValue()) {
			return 0;
			} else if(i1.getValue() > i2.getValue()) {
				return 1;
				} else return -1;
	}
}

//���ݳ�ʼ��ŵıȽ���
class ComparatorOfInitNum implements Comparator<ImpValue> {
	@Override
	public int compare(ImpValue i1, ImpValue i2) {
		if(i1.getInitialNumber() == i2.getInitialNumber()) {
			return 0;
			} else if(i1.getInitialNumber() > i2.getInitialNumber()) {
				return 1;
				} else return -1;
	}
}

//��������ŵıȽ���
class ComparatorOfOrderNum implements Comparator<ImpValue> {
	@Override
	public int compare(ImpValue i1, ImpValue i2) {
		if(i1.getOrderNumber() == i2.getOrderNumber()) {
			return 0;
			} else if(i1.getOrderNumber() > i2.getOrderNumber()) {
				return 1;
				} else return -1;
	}
}