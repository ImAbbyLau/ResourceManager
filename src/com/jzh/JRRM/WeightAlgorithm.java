package com.jzh.JRRM;
import Jama.*;
import java.math.BigDecimal;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
		public class WeightAlgorithm {
			public static double[][] judgementMatrix ;
			public static double[] getWeightVector () {
			int i, j;
			double[] rowProduct = new double[] {1,1,1,1};
			double[] pow = new double[4];
			double powsum = 0;
			double[] weight = new double[4];
			double[] weightVector = new double[5];
			
			//一致性检验
			Matrix m = new Matrix(judgementMatrix);
			EigenvalueDecomposition mEig = m.eig();
			double[] real = mEig.getRealEigenvalues();
			double[] image = mEig.getImagEigenvalues();
			/*for(int s = 0; s < real.length; s++) {
				System.out.println(real[s] + " + s" + image[s]);
			}
			*/
			double cr = (real[0]-4)/3.0/0.89;
			System.out.println(cr);
		
			

			//计算行积的4次幂
			for(i = 0; i < 4; i++) {
				for(j = 0; j < 4; j++) {
					rowProduct[i] *=judgementMatrix[i][j];
					
				}

				pow[i] = Math.pow(rowProduct[i], 0.25) ;

			}
			//归一化并计算业务权重
			for(i = 0; i < 4; i++) {
			powsum +=pow[i];
			}
			for(i = 0; i < 4; i++) {
			weight[i] = pow[i]/powsum;
			BigDecimal   b   =   new   BigDecimal(weight[i]);
			weightVector[i] =  b.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();
			weightVector[4] =cr;
			//System.out.println(weightVector[i]);
			/*
			WeightBean res = new WeightBean();
			res.setTrafficType(type);
			res.setDelay(weightVector[0]);
			res.setJitter(weightVector[1]);
			res.setPacketLoss(weightVector[2]);
			res.setBandWidth(weightVector[3]);
			JRRMSqlWeight.saveOrUpdate(res);
			 */
			}
			return weightVector;
			}

		

		}	