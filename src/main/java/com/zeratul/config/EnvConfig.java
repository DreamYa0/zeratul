package com.zeratul.config;

import com.zeratul.util.FileUtils;
import lombok.Data;

import java.util.Arrays;
import java.util.Properties;

import static com.zeratul.util.Constants.TEST_CONF_FILE_PATH;
import static com.zeratul.util.ParamUtils.haveEnv;
import static com.zeratul.util.ParamUtils.haveRunner;
import static com.zeratul.util.ParamUtils.haveProjectName;

/**
 * @author dreamyao
 * @version 1.0.0
 * @title
 * @Date 2017/8/27 18:02
 */
@Data
public class EnvConfig {

    private String hostDomain = "";
    private TestEnv env = TestEnv.test;
    private String projectName = "";
    private String runner = "";
    private String envStr = "";

    public EnvConfig(){
        initConfig();
    }

    private void initConfig() {
        Properties properties = FileUtils.getProperties(TEST_CONF_FILE_PATH);
        if (haveProjectName(properties)) {
            projectName = properties.getProperty("projectName");
        }
        if (haveRunner(properties)) {
            runner = properties.getProperty("runner");
        }
        if (haveEnv(properties)) {
            envStr = properties.getProperty("env");
            if (Arrays.toString(TestEnv.values()).contains(envStr)) {
                env = TestEnv.valueOf(properties.getProperty("testmode"));
            } else {
                env = TestEnv.test;
            }
        }
    }
}
