package com.aizone.blockchain.constants;

/**
 * 加密算法用到的常量
 * @since 24-6-6
 */
public interface EncryptConstants {
	/**
	 * 椭圆曲线密钥生成算法，ECDSA
	 */
	String KEY_GEN_ALGORITHM = "ECDSA";

	/**
	 * 椭圆曲线（EC）域参数设定
	 */
	String EC_PARAM_SPEC = "secp256k1";

	/**
	 * 签名算法
	 */
	String SIGN_ALGORITHM = "SHA1withECDSA";
}
