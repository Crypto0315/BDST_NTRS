package com.aizone.blockchain.mine.pow;

import com.aizone.blockchain.core.Block;
import com.aizone.blockchain.encrypt.HashUtils;
import com.aizone.blockchain.utils.ByteUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

/**
 * 工作量证明实现
 *  
 */
public class ProofOfWork {

    /**
     * 难度目标位
     */
    public static final int TARGET_BITS = 12;
    /**
     * 区块
     */
    private Block block;
    /**
     * 难度目标值
     */
    private BigInteger target;


    /**
     * <p>创建新的工作量证明，设定难度目标值</p>
     * <p>对1进行移位运算，将1向左移动 (256 - TARGET_BITS) 位，得到我们的难度目标值</p>
     * @param block
     * @return
     */
    public static ProofOfWork newProofOfWork(Block block) {
        BigInteger targetValue = BigInteger.valueOf(1).shiftLeft((256 - TARGET_BITS));
        return new ProofOfWork(block, targetValue);
    }

    private ProofOfWork(Block block, BigInteger target) {
        this.block = block;
        this.target = target;
    }

    /**
     * 运行工作量证明，开始挖矿，找到小于难度目标值的Hash
     * @return
     */
    public PowResult run() {
        long nonce = 0;
        String shaHex = "";
        while (nonce < Long.MAX_VALUE) {
            byte[] data = this.prepareData(nonce);
            shaHex = HashUtils.sha256Hex(data);
            if (new BigInteger(shaHex, 16).compareTo(this.target) == -1) {
                break;
            } else {
                nonce++;
            }
        }
        return new PowResult(nonce, shaHex, this.target);
    }

    /**
     * 验证区块是否有效
     * @return
     */
    public boolean validate() {
        byte[] data = this.prepareData(this.getBlock().getHeader().getNonce());
        return new BigInteger(HashUtils.sha256Hex(data), 16).compareTo(this.target) == -1;
    }

    /**
     * 准备数据
     * <p>
     * 注意：在准备区块数据时，一定要从原始数据类型转化为byte[]，不能直接从字符串进行转换
     * @param nonce
     * @return
     */
    private byte[] prepareData(long nonce) {
        byte[] prevBlockHashBytes = {};
        if (StringUtils.isNotBlank(this.getBlock().getHeader().getPreviousHash())) {
            prevBlockHashBytes = new BigInteger(this.getBlock().getHeader().getPreviousHash(), 16).toByteArray();
        }

        return ByteUtils.merge(
                prevBlockHashBytes,
                ByteUtils.toBytes(this.getBlock().getHeader().getTimestamp()),
                ByteUtils.toBytes(TARGET_BITS),
                ByteUtils.toBytes(nonce)
        );
    }


    public Block getBlock() {
        return block;
    }

    public static BigInteger getTarget() {
        return BigInteger.valueOf(1).shiftLeft((256 - TARGET_BITS));
    }

}
