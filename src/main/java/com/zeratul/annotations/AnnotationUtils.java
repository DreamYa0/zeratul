package com.zeratul.annotations;

import java.lang.reflect.Method;

/**
 * @author dreamyao
 * @version 1.0.0
 * @title
 * @Date 2017/8/27 15:36
 */
public final class AnnotationUtils {

    private AnnotationUtils(){}

    /**
     * 判断测试方法上是否有@Ignore注解
     * @param method 测试方法
     * @return       是否有
     */
    public static boolean isIgnoreAnnotation(Method method) {
        Ignore ignore = method.getDeclaredAnnotation(Ignore.class);
        if (ignore == null) {
            return false;
        }
        return true;
    }
}
