package com.aizone.blockchain.core;

import com.aizone.blockchain.encrypt.HashUtils;
import com.aizone.blockchain.encrypt.WalletUtils;
import com.aizone.blockchain.enums.TransactionStatusEnum;
import com.aizone.blockchain.utils.SerializeUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 交易对象
 * @since 24-6-6
 */
public class Transaction {

    /**
     * 付款人地址
     */
    private String sender;

    /**
     * 付款人环签名
     */
    private Object[] ntrsSign;

    /**
     * 验证环签名必要参数
     */
    private Map<String,Object> ntrsStep;
    /**
     * 收款人地址
     */
    private String recipient;
    /**
     * 收款人公钥
     */
    private Object publicKey;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 交易时间戳
     */
    private Long timestamp;
    /**
     * 交易 Hash 值
     */
    private String txHash;
    /**
     * 交易状态
     */
    private TransactionStatusEnum status = TransactionStatusEnum.SUCCESS;
    /**
     * 交易错误信息
     */
    private String errorMessage;
    /**
     * 附加数据
     */
    private Serializable data;

    public Transaction(String sender, String recipient, BigDecimal amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.timestamp = System.currentTimeMillis();
    }

    public Transaction() {
        this.timestamp = System.currentTimeMillis();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Object getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(Object publicKey) {
        this.publicKey = publicKey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public Serializable getData() {
        return data;
    }

    public void setData(Serializable data) {
        this.data = data;
    }

    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 计算交易信息的Hash值
     * @return
     */
    public String hash() {
        // 使用序列化的方式对Transaction对象进行深度复制
        byte[] serializeBytes = SerializeUtils.serialize(this);
        Transaction copyTx = (Transaction) SerializeUtils.unSerialize(serializeBytes);
        return HashUtils.sha256Hex(SerializeUtils.serialize(copyTx));
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", publicKey='" + WalletUtils.encodeObjectToBase58(publicKey) + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", txHash=" + txHash +
                ", status=" + status.getName() +
                ", data=" + data +
                '}';
    }

    public Object[] getNtrsSign() {
        return ntrsSign;
    }

    public void setNtrsSign(Object[] ntrsSign) {
        this.ntrsSign = ntrsSign;
    }

    public Map<String, Object> getNtrsStep() {
        return ntrsStep;
    }

    public void setNtrsStep(Map<String, Object> ntrsStep) {
        this.ntrsStep = ntrsStep;
    }
}
