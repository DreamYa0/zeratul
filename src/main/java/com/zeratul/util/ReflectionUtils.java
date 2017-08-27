package com.zeratul.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author dreamyao
 * @version 1.0.0
 * @title
 * @Date 2017/8/27 15:35
 */
public final class ReflectionUtils {

    private ReflectionUtils(){}

    /**
     * 从基类中获取被测接口的Class对象
     * @param testInstance 测试类对象
     * @return             接口的Class对象
     */
    public static Class<?> getInterfaceClass(Object testInstance) {
        Type superType = testInstance.getClass().getGenericSuperclass();
        if (superType instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) superType).getActualTypeArguments()[0];
        }
        throw new RuntimeException(testInstance.getClass().getName() + "基类<T>缺少类定义T");
    }
}
