package com.aizone.blockchain;

import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import ntrsSignature.NTRSSignature;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import static com.aizone.blockchain.encrypt.WalletUtils.generateAddress;

/**
 * 临时测试文件，测试各种其他测试代码
 *  
 * @since 24-6-6
 */
public class TempTest {


	@Test
	public void run() throws MWException {
		/*Long n =4L;
		Long m =6L;
		Long d =128L;
		Double q = 8380417.0;*/
		//double[][] A = generateMatrix(n, m, d, q);
		//double[][] f = constructIrreduciblePolynomial(d);
		//调用方法
		NTRSSignature ntrsSignature = new NTRSSignature();
		/**
		 * 设置运行参数
		 * n = 4;
		 * m = 6;
		 * d = 128;
		 * q = 8380417;
		 *
		 * % Randomly sample a matrix A=[A0||In] (Note: Abar = A)
		 * % Pick A0, we store the 4×2-dimensional polynomial vector in a n*(m-n)d matrix.
		 * A = randi([-(q-1)/2,(q-1)/2], n, d*m);
		 * f = zeros(1,d+1);
		 */
		Object[] objects = ntrsSignature.NTRSSetup(6);
		Long n =  ((MWNumericArray) objects[0]).getLong();
		Long m =  ((MWNumericArray) objects[1]).getLong();
		Long d =  ((MWNumericArray) objects[2]).getLong();
		Double q =  ((MWNumericArray) objects[3]).getDouble();
		double[][] A = generateMatrix(n, m, d, q);
		//double[][] A = (double[][]) ((MWNumericArray) objects[4]).toDoubleArray();
		double[][] f = (double[][]) ((MWNumericArray) objects[5]).toDoubleArray();


		/**
		 * 生成公私钥  sk  pk
		 * function [SK, PK] = NTRSKeyGen(n, m, d, q, A, f)
		 */
		Object[] objects1 = ntrsSignature.NTRSKeyGen(2, n, m, d, q, A, f);
		//Object[] objects1 = ntrsSignature.NTRSKeyGen(2, n, m, d, q,A,f);
		double[][] SK = (double[][]) ((MWNumericArray) objects1[0]).toDoubleArray();
		double[][] PK = (double[][]) ((MWNumericArray) objects1[1]).toDoubleArray();
//		Object SK = objects1[0];
//		Object PK =  objects1[1];
		System.out.println("SK=="+ Arrays.deepToString(SK));
		System.out.println("PK=="+Arrays.deepToString(PK));


		/**
		 * 调用签名算法   PK  SK
		 * function [B, theta, z, c, C, d0, Lpk] = NTRSSign(n, m, d, q, A, f, N, Lpk, PK, SK)
		 */
//		Object[] objects2 = ntrsSignature.NTRSSign(7, objects[0], objects[1], objects[2], objects[3], A, objects[5], PK, SK);
		Object[] objects2 = ntrsSignature.NTRSSign(7, n, m, d, q, A, f, PK, SK);
		double[][] B = (double[][]) ((MWNumericArray) objects2[0]).toDoubleArray();
		double[][] theta = (double[][]) ((MWNumericArray) objects2[1]).toDoubleArray();
		double[][] z = (double[][]) ((MWNumericArray) objects2[2]).toDoubleArray();
		double[][] c = (double[][]) ((MWNumericArray) objects2[3]).toDoubleArray();
		double[][] C = (double[][]) ((MWNumericArray) objects2[4]).toDoubleArray();
		double[][] d0 = (double[][]) ((MWNumericArray) objects2[5]).toDoubleArray();
		double[][] Lpk = (double[][]) ((MWNumericArray) objects2[6]).toDoubleArray();
		//System.out.println("B=="+objects2[0]);

		/**
		 * 调用验证算法，看验证是否通过
		 * function [result] = NTRSVerify(n, m, d, q, A, f, B, theta, Lpk, z, c, C, d0)
		 */
		Object[] objects3 = ntrsSignature.NTRSVerify(1, n, m, d, q, A, f, B,Lpk, theta, z, c, C, d0);
		//Object[] objects3 = ntrsSignature.NTRSVerify(1, n, objects[1], objects[2], objects[3], objects[4], objects[5], objects2[0], objects2[6], objects2[1], objects2[2], objects2[3], objects2[4], objects2[5]);
		System.out.println("result=="+objects3[0]);

	}

	public static double[][] generateMatrix(Long n, Long d, Long m, Double q) {
		int nInt = n.intValue();
		int dInt = d.intValue();
		int mInt = m.intValue();
		int qInt = q.intValue();

		double[][] A = new double[nInt][dInt * mInt];
		Random rand = new Random();
		int range = (qInt - 1) / 2;
		for (int i = 0; i < nInt; i++) {
			for (int j = 0; j < dInt * mInt; j++) {
				A[i][j] = rand.nextInt(range * 2 + 1) - range;
			}
		}
		return A;
	}

	public static double[][] constructIrreduciblePolynomial(Long d) {
		int dInt = d.intValue();

		// Create a 2D array to represent the polynomial
		double[][] f = new double[dInt + 1][2];

		// Set the coefficients for the terms x^0 and x^d
		f[0][0] = 0; // Power of the variable for the constant term (x^0)
		f[0][1] = 1; // Coefficient value for the constant term (x^0)
		f[dInt][0] = dInt; // Power of the variable for the x^d term
		f[dInt][1] = 1; // Coefficient value for the x^d term

		return f;
	}
}
