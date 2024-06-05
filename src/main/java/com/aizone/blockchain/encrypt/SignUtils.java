package com.aizone.blockchain.encrypt;

import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import ntrsSignature.NTRSSignature;
import java.util.Map;

/**
 * 签名工具类
 * @since 24-6-6
 */
public class SignUtils {

    /**
     * 使用环签名进行签名
     * @return
     */
    public static Object[] ntrsSign(Map<String, Object> step, double[][] PK1, String privateKey) throws Exception {
        MWNumericArray PK2 = new MWNumericArray(PK1, MWClassID.DOUBLE);
        double[][] PK = (double[][]) PK2.toDoubleArray();
        //double[][] SK = WalletUtils.decodeBase58ToObject(privateKey);
        MWNumericArray SK1 = new MWNumericArray(WalletUtils.decodeBase58ToObject(privateKey), MWClassID.DOUBLE);
        double[][] SK = (double[][]) SK1.toDoubleArray();

        //调用密钥生成方法
        NTRSSignature ntrsSignature = new NTRSSignature();
        /**
         * 调用签名算法   PK  SK
         * function [B, theta, z, c, C, d0, Lpk] = NTRSSign(n, m, d, q, A, f, N, Lpk, PK, SK)
         */
        Object[] ntrsSign = ntrsSignature.NTRSSign(7, step.get("n"), step.get("m"),step.get("d") ,step.get("q") ,step.get("A") ,step.get("f") , PK, SK);
        return ntrsSign;
    }


    /**
     * 验证签名是否有效
     * @return
     */
    public static String ntrsVerify(Map<String, Object> step,Object[] sign) throws Exception {
        Object B1 = sign[0];
        Object theta1 = sign[1];
        Object z1 = sign[2];
        Object c1 = sign[3];
        Object C1 = sign[4];
        Object d01 = sign[5];
        Object Lpk1 = sign[6];
        //调用方法
        NTRSSignature ntrsSignature = new NTRSSignature();
        /**
         * 调用验证算法，看验证是否通过
         * function [result] = NTRSVerify(n, m, d, q, A, f, B, theta, Lpk, z, c, C, d0)
         */
        MWNumericArray B2 = new MWNumericArray(B1, MWClassID.DOUBLE);
        double[][] B = (double[][]) B2.toDoubleArray();

        MWNumericArray theta2 = new MWNumericArray(theta1, MWClassID.DOUBLE);
        double[][] theta = (double[][]) theta2.toDoubleArray();

        MWNumericArray z2 = new MWNumericArray(z1, MWClassID.DOUBLE);
        double[][] z = (double[][]) z2.toDoubleArray();

        MWNumericArray c2 = new MWNumericArray(c1, MWClassID.DOUBLE);
        double[][] c = (double[][]) c2.toDoubleArray();

        MWNumericArray C2 = new MWNumericArray(C1, MWClassID.DOUBLE);
        double[][] C = (double[][]) C2.toDoubleArray();

        MWNumericArray d02 = new MWNumericArray(d01, MWClassID.DOUBLE);
        double[][] d0 = (double[][]) d02.toDoubleArray();

        MWNumericArray Lpk2 = new MWNumericArray(Lpk1, MWClassID.DOUBLE);
        double[][] Lpk = (double[][]) Lpk2.toDoubleArray();
        Object[] objects3 = ntrsSignature.NTRSVerify(1, step.get("n"), step.get("m"),step.get("d") ,step.get("q") ,step.get("A") ,step.get("f"), B, Lpk,theta,  z, c, C, d0);
        return objects3[0].toString();
    }
}
