package com.zeratul.base;

import com.zeratul.param.ExcelParam;
import com.zeratul.param.HandleExcel;
import com.zeratul.param.TestNGParam;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.*;

import static com.zeratul.util.Constants.TEST_ONLY;

/**
 * 通用数据驱动基类
 * @author dreamyao
 * @version 1.0.0
 * Created by dreamyao on 2017/7/2.
 */
public class TestBase {

    /**
     * 数据驱动
     *
     * @param method
     * @return
     * @throws Exception
     */
    @DataProvider(name = "excel")
    public Iterator<Object[]> dataProvider(Method method) throws Exception {
        String className = this.getClass().getSimpleName();
        String resource = this.getClass().getResource("").getPath();
        String xls = resource + className + ".xls";
        HandleExcel excel = new HandleExcel();
        List<Map<String, Object>> list = excel.readDataByRow(xls, method.getName());
        Set<Object[]> set = new LinkedHashSet<>();
        ExcelParam param = new ExcelParam();
        TestNGParam ngParam = new TestNGParam();
        boolean hasTestOnly = list.stream().filter(map -> map.get(TEST_ONLY) != null).anyMatch(newMap -> param.isValueTrue(newMap.get(TEST_ONLY)));
        for (Map<String, Object> map : list) {
            if (hasTestOnly) {
                if (!param.isValueTrue(map.get(TEST_ONLY))) {
                    continue;
                }
            }
            // 注入默认值，否则执行时参数不匹配
            set.add(ngParam.injectResultAndParameters(map, method));
        }
        if (set.size() == 0) {
            System.err.println("------------------没有测试用例！------------------");
        }
        return set.iterator();
    }
}
