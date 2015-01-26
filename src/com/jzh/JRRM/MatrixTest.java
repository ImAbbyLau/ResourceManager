package com.jzh.JRRM;

import Jama.*;

public class MatrixTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] a = new double[][] {
				{1, 9, 9},
				{0.1111, 1, 5},
				{0.1111, 0.2, 1}
		};
		Matrix m = new Matrix(a);
		EigenvalueDecomposition mEig = m.eig();
		double[] real = mEig.getRealEigenvalues();
		double[] image = mEig.getImagEigenvalues();
		for(int i = 0; i < real.length; i++) {
			System.out.println(real[i] + " + i" + image[i]);
		}
		double cr = (real[0]-3)/2.0/0.36;
		System.out.println(cr);
	}

}
