package com.zeratul.util;

import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author dreamyao
 * @version 1.0.0
 * @title TestNG框架相关处理工具类
 * @Date 2017/8/27 15:37
 */
public final class TestNGUtils {

    private TestNGUtils() {}

    /**
     * 从TestNG测试结果上下文中获取测试方法
     * @param testResult TestNG测试结果上下文
     * @return           测试方法
     */
    public static Method getTestMethod(ITestResult testResult) {
        return testResult.getMethod().getConstructorOrMethod().getMethod();
    }

    /**
     * 从测试结果上下文中获取入参
     * @param testResult TestNG测试结果上下文
     * @return           入参
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getParameters(ITestResult testResult) {
        Object[] params = testResult.getParameters();
        Map<String,Object> param = null;
        if (params.length > 0) {
            param = (Map<String, Object>) params[0];
        }
        return param;
    }
}
