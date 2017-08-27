package com.zeratul.param;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author dreamyao
 * @version 1.0.0
 * @title
 * @Date 2017/8/27 14:59
 */
public class TestNGParam {

    /**
     * 把参数和结果注入到测试函数，先注入默认值，防止执行时参数不匹配
     *
     * @param map
     * @param testMethod
     * @return
     * @throws Exception
     */
    public Object[] injectResultAndParameters(Map<String, Object> map, Method testMethod) throws Exception {
        int parameters = testMethod.getGenericParameterTypes().length;
        Object[] objects;
        if (parameters > 1) {
            objects = new Object[parameters];
            objects[0] = map;
            // 第一个参数是Map<String,Object> context，不处理；第二个参数是Object result
            for (int i = 1; i < parameters; i++) {
                objects[i] = null;
            }
        } else {
            objects = new Object[]{map};
        }
        return objects;
    }
}
