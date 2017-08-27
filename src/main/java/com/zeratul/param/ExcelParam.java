package com.zeratul.param;

import com.zeratul.util.Constants;

import java.util.Map;

import static com.zeratul.util.Constants.TEST_ONLY;

/**
 * @author dreamyao
 * @version 1.0.0
 * @title
 * @Date 2017/8/27 14:47
 */
public class ExcelParam {

    /**
     * 判断excel中是否有testOnly字段
     * @param param
     * @return
     */
    public boolean isTestOnlyParam(Map<String, Object> param) {
        if (param.get(TEST_ONLY) == null || "".equals(param.get(TEST_ONLY))) {
            return false;
        }
        return true;
    }

    /**
     * 只有 1 或 Y 返回true
     *
     * @param value
     * @return
     */
    public boolean isValueTrue(Object value) {
        if (value == null) {
            return false;
        }
        return "1".equalsIgnoreCase(value.toString()) || Constants.EXCEL_YES.equalsIgnoreCase(value.toString());
    }
}
