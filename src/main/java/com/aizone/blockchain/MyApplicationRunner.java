package com.aizone.blockchain;


import com.aizone.blockchain.conf.RocksDbProperties;
import com.aizone.blockchain.wallet.Account;
import com.aizone.blockchain.wallet.Personal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @since 24-6-6
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    static Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);

    @Autowired
    private Personal personal;

    @Autowired
    private RocksDbProperties rocksDbProperties;

    @Override
    public void run(ApplicationArguments arguments) throws Exception
    {

        // 首次运行，执行一些初始化的工作
        File lockFile = new File(System.getProperty("user.dir")+"/"+rocksDbProperties.getDataDir()+"/blockchain.lock");
        if (!lockFile.exists()) {
            lockFile.createNewFile();
            // 创建默认钱包地址（挖矿地址）
            Account coinBase = personal.createCoinBase();
            logger.info("Create coinbase address : " + coinBase.getAddress());
        }

    }

}
