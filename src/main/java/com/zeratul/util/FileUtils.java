package com.zeratul.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author dreamyao
 * @version 1.0.0
 * @title
 * @Date 2017/8/27 17:34
 */
public final class FileUtils {

    private FileUtils(){}

    /**
     * 用于获取打包中的resources文件
     *
     * @param filePath 文件路径
     * @return         输入流
     */
    public static InputStream getFileInputStream(String filePath) {
        try {
            ClassLoader loader = FileUtils.class.getClassLoader();
            return loader.getResourceAsStream(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从文件路径加载配置文件
     * @param filePath 文件路径
     * @return         Properties集合
     */
    public static Properties getProperties(String filePath) {
        return getProperties(getFileInputStream(filePath));
    }

    /**
     * 从输入流中加载配置文件
     * @param stream 输入流
     * @return       Properties集合
     */
    public static Properties getProperties(InputStream stream) {
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
