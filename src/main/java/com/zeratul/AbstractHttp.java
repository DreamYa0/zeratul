package com.zeratul;

import com.zeratul.base.TestBase;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;

/**
 * 通用http测试基类
 * @author dreamyao
 * @version 1.0
 * Created by dreamyao on 2017/7/2.
 */
public abstract class AbstractHttp extends TestBase implements IHookable, ITestBase {

    @BeforeClass(alwaysRun = true)
    public void initEnv() {

    }
    @Override
    public void initDb() {

    }

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {

    }
}
