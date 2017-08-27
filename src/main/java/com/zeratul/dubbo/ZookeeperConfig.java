package com.zeratul.dubbo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.zeratul.util.Constants.ZOOKEEPER_FILE_PATH;
import static com.zeratul.util.FileUtils.getFileInputStream;

/**
 * @author dreamyao
 * @version 1.0.0
 * @title
 * @Date 2017/8/27 17:22
 */
public final class ZookeeperConfig {

    protected Properties getZkHosts(){
        InputStream stream = getFileInputStream(ZOOKEEPER_FILE_PATH);
        Properties zkHosts = new Properties();
        try {
            zkHosts.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zkHosts;
    }
}
