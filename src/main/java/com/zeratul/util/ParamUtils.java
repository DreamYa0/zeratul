package com.zeratul.util;

import java.util.Properties;

/**
 * @author dreamyao
 * @version 1.0.0
 * @title
 * @Date 2017/8/27 21:59
 */
public final class ParamUtils {

    private ParamUtils(){}

    public static boolean haveProjectName(Properties properties) {
        if (properties.containsKey("projectName")) {
            return true;
        }
        return false;
    }

    public static boolean haveRunner(Properties properties) {
        if (properties.containsKey("runner")) {
            return true;
        }
        return false;
    }

    public static boolean haveEnv(Properties properties) {
        if (properties.containsKey("env")) {
            return true;
        }
        return false;
    }
}
