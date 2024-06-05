package com.aizone.blockchain.wallet;

import com.aizone.blockchain.encrypt.WalletUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 钱包账户
 *  
 * @since 24-6-6
 */
public class Account implements Serializable {

    /**
     * 钱包私钥
     */
    protected String privateKey;

    /**
     * 钱包公钥
     */
    protected double[][] publicKey;
    protected Long n;
    protected Long m;
    protected Long d;
    protected Double q;
    protected double[][] A;
    protected double[][] f;

    /**
     * 钱包地址
     */
    protected String address;
    /**
     * 账户余额
     */
    protected BigDecimal balance;
    /**
     * 账户锁定状态
     */
    protected boolean locked = false;

    public Account() {
    }

    public Account(String privateKey, double[][] publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.address = WalletUtils.generateAddress(publicKey);
        this.balance = BigDecimal.ZERO;
    }

    public Account(String privateKey, double[][] publicKey, BigDecimal balance) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.address = WalletUtils.generateAddress(publicKey);
        this.balance = balance;
    }

    public Account(double[][] publicKey,Long n, Long m,Long d,Double q,double[][] A,double[][] f) {
        this.publicKey = publicKey;
        this.address = WalletUtils.generateAddress(publicKey);
        this.balance = BigDecimal.ZERO;
        this.n = n;
        this.m = m;
        this.d = d;
        this.q = q;
        this.A = A;
        this.f = f;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public double[][] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(double[][] publicKey) {
        this.publicKey = publicKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Long getN() {
        return n;
    }

    public void setN(Long n) {
        this.n = n;
    }

    public Long getM() {
        return m;
    }

    public void setM(Long m) {
        this.m = m;
    }

    public Long getD() {
        return d;
    }

    public void setD(Long d) {
        this.d = d;
    }

    public Double getQ() {
        return q;
    }

    public void setQ(Double q) {
        this.q = q;
    }

    public double[][] getA() {
        return A;
    }

    public void setA(double[][] a) {
        A = a;
    }

    public double[][] getF() {
        return f;
    }

    public void setF(double[][] f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "Account{" +
                "privateKey='" + privateKey + '\'' +
                ", publicKey=" + WalletUtils.encodeObjectToBase58(publicKey) +
                ", address='" + address + '\'' +
                ", balance=" + balance +
                '}';
    }
}
