package com.zeratul.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static com.zeratul.util.Constants.DATA_BASE_FILE_PATH;
import static com.zeratul.util.FileUtils.getFileInputStream;

public class SqlConfig {

    private static Cache<TestEnv,Map<String, String>> configsCache = CacheBuilder.newBuilder().build();

    /**
     * 获取配置文件信息
     * @param mode 指定测试环境
     * @return
     */
    public static Map<String, String> getConfig(TestEnv mode) {
        Map<String, String> result = null;
        try {
            result = configsCache.get(mode, () -> init(mode));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 初始化
     * @param mode
     */
    private static Map<String,String> init(TestEnv mode) {
        Properties prop = new Properties();
        InputStream in = getFileInputStream(DATA_BASE_FILE_PATH);
        String key = mode.name();
        Map<String, String> thisConfig = null;
        try {
            prop.load(in);
            thisConfig = Maps.newHashMap();
            if (prop.containsKey(key)) {
                JSONObject jObj = JSON.parseObject(prop.getProperty(key));
                Iterator<String> it = jObj.keySet().iterator();
                while (it.hasNext()) {
                    String k = it.next();
                    thisConfig.put(k, jObj.getString(k));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thisConfig;
    }
}
