package com.zeratul.util;

import java.io.InputStream;

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
}
