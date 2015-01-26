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
	//得到业务属性参数
	private static List<TrafficBean> trafResults;
	private static List<UserBean> userResults;
	private static List<MissionBean> missionResults;
	
	private static int numOfTraf;
	private static int numOfUser;
	private static int numOfMission;
	private static int numOfService;
	
	//构建模糊判断矩阵
	public static double[][] fuzzyJudgementMatrix = new double[][] {
			{0.5, 0.3, 0.1},
			{0.7, 0.5, 0.7},
			{0.9, 0.3, 0.5}
	};  //默认典型值
	
	/**
	 * 计算模糊判断矩阵的满意一致性指标
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
	 * 判断模糊判断矩阵是否满意一致性指标
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
	 * 对不符合完全一致性的模糊判决矩阵的修正
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
	 * 计算总权重向量（一致性修正后）
	 * 
	 * @param dimension
	 * @return weightVector
	 */
	private static double[] getWeightVector (int dimension) {
		int i, j;
		double[] rowSum = new double[3];
		double[] weightVector = new double[3];
		
		//计算行和
		for(i = 0; i < dimension; i++) {
			rowSum[i] = 0.0f;
			for(j = 0; j < dimension; j++) {
				rowSum[i] +=fuzzyJudgementMatrix[i][j];
			}
		}

		//利用通用公式计算权重向量
		for(i = 0; i < dimension; i++) {
			weightVector[i] = (rowSum[i] + (dimension / 2) - 1) / (dimension * (dimension - 1));
		}
		
		return weightVector;
	}
	
	/**
	 * 从数据获取业务信息，存入static变量
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
	 * 计算业务属性值
	 *  
	 * @return trafAttValue
	 */
	private static double[] getTrafAttValue () {
		double[] trafAttValue = new double[numOfTraf]; //业务属性值数组
		
		//划分实时业务和非实时业务
		List<TrafficBean> realTraf = new LinkedList<TrafficBean>(); //实时业务
		List<TrafficBean> nonRealTraf = new LinkedList<TrafficBean>(); //非实时业务
		boolean[] isReal = new boolean[numOfTraf] ; //实时或非实时业务标记数组，对于等待时延大于等于1000ms且抖动大于等于100ms的标记为非实时业务
		
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
		
		//计算实时业务属性值
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
		
		//计算非实时业务属性值
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
		
		//归一化实时业务与非实时业务
		double realWeight = 1.0; //实时业务权重
		double nonRealWeight = 0.5; //非实时业务权重
		for(int i = 0, j = 0, k = 0; i < numOfTraf; i++) {
			if(true == isReal[i]) {
				trafAttValue[i] = realWeight * realTrafAttValue[j] / maxOfRealValue;
				j++;
			} else {
				trafAttValue[i] = nonRealWeight * minOfNonRealValue / nonRealTrafAttValue[k];
				k++;
			}
		}
		//返回属性值	
		return trafAttValue;
	}
	
	/**
	 * 计算用户属性值
	 *  
	 * @return userAttValue
	 */
	private static double[] getUserAttValue () {
		double[] userAttValue = new double[numOfUser]; //用户属性值数组
		
		//计算用户属性值
		for(int i = 0; i < numOfUser; i++) {
			userAttValue[i] = 1.0 / (userResults.get(i).getUserClassification() * userResults.get(i).getUserSecurityPolicy());
		}
		//返回用户属性值
		return userAttValue;
	}
	
	/**
	 * 计算任务属性值
	 *  
	 * @return missionAttValue
	 */
	private static double[] getMissionAttValue () {
		double[] missionAttValue = new double[numOfMission]; //用户属性值数组
		
		double[] weight = new double[] {0.25, 0.25, 0.25, 0.25}; //任务属性权重向量，这里采用同权值
		
		double[][] decisionMatrixOfMission = new double[numOfMission][4]; //任务属性决策矩阵
		for(int i = 0; i < numOfMission; i++) {
			decisionMatrixOfMission[i][0] = missionResults.get(i).getMissionPriority(); //任务类别，使用任务子优先级赋值
			decisionMatrixOfMission[i][1] = missionResults.get(i).getMissionBandWidth(); //任务带宽需求
			decisionMatrixOfMission[i][2] = missionResults.get(i).getMissionReliability(); //任务可靠性等级
			decisionMatrixOfMission[i][3] = missionResults.get(i).getMissionResponseTime(); //任务响应时间要求
		}
		
		//决策矩阵
		double[] normalizationParam = new double[4];
		boolean[] isBenefitAttribute = new boolean[] {false, true, true, false}; //标记效益型属性或成本型属性
		//计算规范化所需的最大或最小参数值
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
		
		//计算归一化决策矩阵
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < numOfMission; i++) {
				if(true == isBenefitAttribute[j]) {
					decisionMatrixOfMission[i][j] = decisionMatrixOfMission[i][j] / normalizationParam[j];
				} else {
					decisionMatrixOfMission[i][j] = normalizationParam[j] / decisionMatrixOfMission[i][j];
				}
			}
		}
		
		//计算任务属性值
		for(int i = 0; i < numOfMission; i++) {
			missionAttValue[i] = 0.0;
			for(int j = 0; j < 4; j++) {
				missionAttValue[i] += decisionMatrixOfMission[i][j] * weight[j];
			}
		}
		//返回任务属性值
		return missionAttValue;
	}
	
	/**
	 * 构建决策矩阵
	 * 
	 * @return decisionMatrix
	 */
	private static double[][] getDecisionMatrix () {
		double[][] decisionMatrix = new double[numOfService][3]; //决策矩阵
		
		double[] trafAttValue = getTrafAttValue();
		double[] userAttValue = getUserAttValue();
		double[] missionAttValue = getMissionAttValue();
		
		//获取决策矩阵
		for(int i = 0; i < numOfTraf; i++) {
			for(int j = 0; j < numOfUser; j++) {
				for(int  k = 0; k < numOfMission; k++) {
					decisionMatrix[i * numOfUser * numOfMission + j * numOfMission + k][0] = trafAttValue[i];
					decisionMatrix[i * numOfUser * numOfMission + j * numOfMission + k][1] = userAttValue[j];
					decisionMatrix[i * numOfUser * numOfMission + j * numOfMission + k][2] = missionAttValue[k];
				}
			}
		}
		
		//归一化决策矩阵
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
		
		//返回决策矩阵
		return decisionMatrix;
	}
	
	/**
	 * 计算综合属性值
	 * 
	 * @return madmImportanceOfValue
	 */
	public static double[] getMADMImportanceOfValue () {
		double[] madmImportanceOfValue =  new double[numOfService]; //MADM重要性评估结果数组（数值结果）
		
		double[][] decisionMatrix = getDecisionMatrix(); //获取决策矩阵
		double[] weighVector = getWeightVector(3); //获取权重向量（一致性修正后）
		
		//计算综合属性值
		for(int i = 0; i < numOfService; i++) {
			madmImportanceOfValue[i] = 0.0;
			for(int j = 0; j < 3; j++) {
				madmImportanceOfValue[i] += decisionMatrix[i][j] * weighVector[j];
			}
		}		
		
		return madmImportanceOfValue;
	}
	
	/**
	 * 计算综合排序结果
	 * 
	 * @return madmImportance
	 */
	public static int[] getMADMImportance () {
		getInfo(); //从数据库获取业务信息
		int[] madmImportance = new int[numOfService]; //MADM重要性评估结果数组（排序结果）
		
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
		//测试模糊判断矩阵的一致性
//		 double[][] matrix = new double[][] {
//			{0.5, 0.417, 0.833, 0.583},
//			{0.583, 0.5, 0.917, 0.667},
//			{0.167, 0.083, 0.5, 0.25},
//			{0.417, 0.333, 0.75, 0.5}
//		 };  //默认典型值
		double[][] matrix = new double[][] {
				{0.5, 0.8, 0.7},
				{0.2, 0.5, 0.8},
				{0.3, 0.2, 0.5}
		};
		System.out.println("判断矩阵：");
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("满意一致性指标 \u03c1=" + consistencyChecking(matrix));
		if(isConsistency(matrix)) {
			System.out.println("\u03c1 <= 0.2,该判断矩阵达到满意一致性要求！");
		} else {
			System.out.println("\u03c1 > 0.2,该判断矩阵未达到满意一致性要求！");
		}

		/*
		//if(!isConsistency(matrix)) {
			matrix = adjustMatrix(matrix);
			int n =matrix.length;
			//System.out.println("不符合一致性，修正后：");
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++)
					System.out.print(matrix[i][j] + " ");
				System.out.println();
			}	
		//}
		
		
		
		
		int index = 0;
		System.out.println("评估结果值：");
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
		
		System.out.println("业务属性值：");
		index = 0;
		for (TrafficBean tempTraf : trafResults) {
			System.out.println(tempTraf.getTrafficTitle() + "|" +   getTrafAttValue()[index]);
			index++;
		}
		System.out.println();
		
		System.out.println("用户属性值：");
		index = 0;
		for (UserBean tempUser : userResults) {
			System.out.println(tempUser.getUserTitle() + "|" +   getUserAttValue()[index]);
			index++;
		}
		System.out.println();
		
		System.out.println("任务属性值：");		
		index = 0;
		for (MissionBean tempMission : missionResults) {
			System.out.println(tempMission.getMissionTitle() + "|" +   getMissionAttValue()[index]);
			index++;
		}		
		System.out.println();
		
		System.out.println("归一化的决策矩阵：");
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
	private double value; //重要性值
	private int initialNumber; //原始顺序
	private int orderNumber; //排序序号，重要性值越大序号越小
	
	//构造函数
	ImpValue (double value, int initialNumber, int orderNumber) {
		this.value = value;
		this.initialNumber = initialNumber;
		this.orderNumber = orderNumber;
	}
	//无参构造函数
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

//根据值的比较器
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

//根据初始序号的比较器
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

//根据排序号的比较器
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