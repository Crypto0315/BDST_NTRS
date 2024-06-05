package com.aizone.blockchain.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * RocksDB 配置参数
 * @since 24-6-6
 */
@Configuration
@ConfigurationProperties(prefix = "rocksdb")
public class RocksDbProperties {

	private String dataDir;

	public String getDataDir() {
		return dataDir;
	}

	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}
}
