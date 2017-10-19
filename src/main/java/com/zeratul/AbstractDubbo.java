package com.zeratul;

import com.zeratul.base.TestBase;
import com.zeratul.dubbo.DubboService;
import com.zeratul.exception.ParameterException;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;

import java.util.Map;
import java.util.Optional;

import static com.zeratul.annotations.AnnotationUtils.isIgnoreAnnotation;
import static com.zeratul.util.ReflectionUtils.getInterfaceClass;
import static com.zeratul.util.TestngUtils.getParameters;
import static com.zeratul.util.TestngUtils.getTestMethod;
import static java.util.Optional.ofNullable;

/**
 * 通用dubbo接口测试基类
 * @author dreamyao
 * @version 1.0
 *          Created by dreamyao on 2017/7/2.
 */
public abstract class AbstractDubbo<T> extends TestBase implements IHookable, ITestBase {

    private DubboService dubboService;

    @BeforeClass(alwaysRun = true)
    public void initEnv() {
        dubboService = new DubboService();
    }

    /**
     * 测试方法拦截器、所有测试方法在执行开始、执行中、执行完成都会在此方法中完成
     * @param callBack
     * @param testResult
     */
    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        // 如果测试方法上有@Ignore注解，则跳过测试框架直接执行测试方法
        if (isIgnoreAnnotation(getTestMethod(testResult))) {
            callBack.runTestMethod(testResult);
        }
    }

    @SuppressWarnings("unchecked")
    private void prepareTest(IHookCallBack callBack, ITestResult testResult) {
        Optional<Map<String, Object>> paramOptional = ofNullable(getParameters(testResult));
        Map<String, Object> param;
        if (paramOptional.isPresent()) {
            param = paramOptional.get();
        } else {
            throw new ParameterException("---------------获取测试入参失败！---------------");
        }
        Class<T> clazz = (Class<T>) getInterfaceClass(this);
    }
}
