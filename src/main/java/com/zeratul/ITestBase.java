package com.zeratul;

import java.util.Map;

/**
 * 通用测试模版
 * @author dreamyao
 * @version 1.0
 * Created by dreamyao on 2017/7/2.
 */
public interface ITestBase {

    /**
     * 初始化SQL
     */
    void initDb();

    /**
     * 此方法在所以测试方法运行前先运行，用于在接口请求前对入参的修改、编辑、删除
     * 提高框架的灵活行、可用行
     * @param context
     */
    void beforeTest(Map<String, Object> context);
}
