package com.aizone.blockchain.encrypt;

import com.aizone.blockchain.utils.ByteUtils;
import com.aizone.blockchain.wallet.Account;
import com.google.common.base.Optional;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import ntrsSignature.NTRSSignature;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * 钱包工具类
 * @since 24-6-6
 */
public class WalletUtils {

    /**
     * 加密字符集合
     */
    private static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    public static Map<String, Object[]> generateKeyGen(List<Object> setup) throws Exception {
        Map<String, Object[]> map = new HashMap<>();
        //调用密钥生成方法
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
        //Object[] ntrsSetup = ntrsSignature.NTRSSetup(6);
		/*Object[] ntrsSetup = ntrsSignature.NTRSSetup(6);
		Long n =  ((MWNumericArray) ntrsSetup[0]).getLong();
		Long m =  ((MWNumericArray) ntrsSetup[1]).getLong();
		Long d =  ((MWNumericArray) ntrsSetup[2]).getLong();
		Double q =  ((MWNumericArray) ntrsSetup[3]).getDouble();
		double[][] A = generateMatrix(n, m, d, q);
		//double[][] A = (double[][]) ((MWNumericArray) objects[4]).toDoubleArray();
		double[][] f = (double[][]) ((MWNumericArray) ntrsSetup[5]).toDoubleArray();*/

        Long n = (Long) setup.get(0);
        Long m = (Long) setup.get(1);
        Long d = (Long) setup.get(2);
        Double q = (Double) setup.get(3);
        double[][] A = (double[][]) setup.get(4);
        double[][] f = (double[][]) setup.get(5);

        /**
         * 生成公私钥  sk  pk
         * function [SK, PK] = NTRSKeyGen(n, m, d, q, A, f)
         */
        Object[] ntrsKeyGen = ntrsSignature.NTRSKeyGen(2, n, m, d, q, A, f);
        map.put("ntrsKeyGen",ntrsKeyGen);
        return map;
    }

    /**
     * 环签名算法参数定义
     * @return
     * @throws Exception
     */
    public static List<Object> generateSetup() throws Exception {

        List<Object> list = new ArrayList<>();

        //调用密钥生成方法
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
        //Object[] ntrsSetup = ntrsSignature.NTRSSetup(6);
        Object[] ntrsSetup = ntrsSignature.NTRSSetup(6);
        Long n =  ((MWNumericArray) ntrsSetup[0]).getLong();
        Long m =  ((MWNumericArray) ntrsSetup[1]).getLong();
        Long d =  ((MWNumericArray) ntrsSetup[2]).getLong();
        Double q =  ((MWNumericArray) ntrsSetup[3]).getDouble();
        double[][] A = generateMatrix(n, m, d, q);
        //double[][] A = (double[][]) ((MWNumericArray) objects[4]).toDoubleArray();
        double[][] f = (double[][]) ((MWNumericArray) ntrsSetup[5]).toDoubleArray();
        list.add(n);
        list.add(m);
        list.add(d);
        list.add(q);
        list.add(A);
        list.add(f);
        return list;
    }

    public static Map<String, Object> setStep(Optional<Account> sender) {
        Map<String, Object> ntrsSetup = new HashMap<>();
        ntrsSetup.put("n", sender.get().getN());
        ntrsSetup.put("m", sender.get().getM());
        ntrsSetup.put("d", sender.get().getD());
        ntrsSetup.put("q", sender.get().getQ());
        ntrsSetup.put("A", sender.get().getA());
        ntrsSetup.put("f", sender.get().getF());
        return ntrsSetup;
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


    // 将对象编码为 Base58 字符串
    public static String encodeObjectToBase58(Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            byte[] bytes = bos.toByteArray();
            return Base58.encode(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 将 Base58 字符串解码为对象
    public static double[][] decodeBase58ToObject(String base58String) {
        byte[] bytes = Base58.decode(base58String);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object obj = ois.readObject();
            if (obj instanceof double[][]) {
                return (double[][]) obj;
            } else {
                System.err.println("Decoded object is not a double[][] array.");
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将 byte[] 公钥转成字符串
     * @param publicKey
     * @return
     */
    public static String publicKeyEncode(byte[] publicKey) {
        return Base58.encode(publicKey);
    }

    /**
     * 将字符串转成 byte[]
     * @param publicKey
     * @return
     */
    public static byte[] publicKeyDecode(String publicKey) {
        return Base58.decode(publicKey);
    }

    /**
     * 根据公钥生成钱包地址
     * @param publicKeyObject
     * @return
     */
    public static String generateAddress(double[][] publicKeyObject) {
        byte[] publicKeyBytes = convertToBytes(publicKeyObject);
        //1. 计算公钥的 SHA-256 哈希值
        byte[] sha256Bytes = HashUtils.sha256(publicKeyBytes);
        //2. 取上一步结果，计算 RIPEMD-160 哈希值
        RIPEMD160Digest digest = new RIPEMD160Digest();
        digest.update(sha256Bytes, 0, sha256Bytes.length);
        byte[] ripemd160Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(ripemd160Bytes, 0);
        //3. 取上一步结果，前面加入地址版本号（主网版本号“0x00”）
        byte[] networkID = new BigInteger("00", 16).toByteArray();
        byte[] extendedRipemd160Bytes = ByteUtils.add(networkID, ripemd160Bytes);
        //4. 取上一步结果，计算 SHA-256 哈希值
        byte[] oneceSha256Bytes = HashUtils.sha256(extendedRipemd160Bytes);
        //5. 取上一步结果，再计算一下 SHA-256 哈希值
        byte[] twiceSha256Bytes = HashUtils.sha256(oneceSha256Bytes);
        //6. 取上一步结果的前4个字节（8位十六进制）
        byte[] checksum = new byte[4];
        System.arraycopy(twiceSha256Bytes, 0, checksum, 0, 4);
        //7. 把这4个字节加在第5步的结果后面，作为校验
        byte[] binaryAddressBytes = ByteUtils.add(extendedRipemd160Bytes, checksum);
        //8. 把结果用 Base58 编码算法进行一次编码
        return Base58.encode(binaryAddressBytes);
    }

    private static byte[] convertToBytes(double[][] publicKey) {
        // Calculate the total number of bytes needed
        int totalBytes = 0;
        for (double[] row : publicKey) {
            totalBytes += row.length;
        }

        // Create a byte array of the required length
        byte[] publicKeyBytes = new byte[totalBytes];

        // Copy the double values into the byte array
        int index = 0;
        for (double[] row : publicKey) {
            for (double value : row) {
                publicKeyBytes[index++] = (byte) value;
            }
        }

        return publicKeyBytes;
    }


    /**
     * 验证地址是否合法
     * @param address
     * @return
     */
    public static boolean verifyAddress(String address) {

        if (address.length() < 26 || address.length() > 35) {
            return false;
        }
        byte[] decoded = decodeBase58To25Bytes(address);
        if (null == decoded) {
            return false;
        }
        // 验证校验码
        byte[] hash1 = HashUtils.sha256(Arrays.copyOfRange(decoded, 0, 21));
        byte[] hash2 = HashUtils.sha256(hash1);

        return Arrays.equals(Arrays.copyOfRange(hash2, 0, 4), Arrays.copyOfRange(decoded, 21, 25));
    }

    /**
     * 使用 Base58 把地址解码成 25 字节
     * @param input
     * @return
     */
    private static byte[] decodeBase58To25Bytes(String input) {

        BigInteger num = BigInteger.ZERO;
        for (char t : input.toCharArray()) {
            int p = ALPHABET.indexOf(t);
            if (p == -1) {
                return null;
            }
            num = num.multiply(BigInteger.valueOf(58)).add(BigInteger.valueOf(p));
        }

        byte[] result = new byte[25];
        byte[] numBytes = num.toByteArray();
        System.arraycopy(numBytes, 0, result, result.length - numBytes.length, numBytes.length);
        return result;
    }
}
