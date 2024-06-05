package com.aizone.blockchain.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 系统全局配置
 * @since 24-6-6
 */
@Configuration
@ConfigurationProperties(prefix = "settings")
public class Settings {

    /**
     * 是否启用节点发现
     */
    private boolean nodeDiscover;

    /**
     * 是否自动挖矿
     */
    private boolean autoMining;

    public boolean isNodeDiscover() {
        return nodeDiscover;
    }

    public void setNodeDiscover(boolean nodeDiscover) {
        this.nodeDiscover = nodeDiscover;
    }

    public boolean isAutoMining() {
        return autoMining;
    }

    public void setAutoMining(boolean autoMining) {
        this.autoMining = autoMining;
    }
}
