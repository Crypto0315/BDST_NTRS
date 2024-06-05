package com.aizone.blockchain.web.vo;


import com.aizone.blockchain.wallet.Account;

/**
 * account VO
 *  
 * @since 24-6-6
 */
public class AccountVo extends Account {

    private String privateKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String toString() {
        return "AccountVo{" +
                "address='" + getAddress() + '\'' +
                "privateKey='" + getPrivateKey() + '\'' +
                "balance='" + getBalance() + '\'' +
                '}';
    }
}
