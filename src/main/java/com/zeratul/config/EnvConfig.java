package com.zeratul.config;

import com.zeratul.util.FileUtils;
import lombok.Data;

import java.util.Properties;

import static com.zeratul.util.Constants.TEST_CONF_FILE_PATH;
import static com.zeratul.util.ParamUtils.haveEnv;
import static com.zeratul.util.ParamUtils.haveProjectName;
import static com.zeratul.util.ParamUtils.haveRunner;

/**
 * @author dreamyao
 * @version 1.0.0
 */
@Data
public class EnvConfig {

    private String hostDomain = "";
    private TestEnv env;
    private String projectName = "";
    private String runner = "";
    private String envStr = "";
    private static final EnvConfig INSTANCE = new EnvConfig();

    private EnvConfig() {
        initConfig();
    }

    public static EnvConfig newInstance() {
        return INSTANCE;
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
            if (envStr.equals(TestEnv.dev.name())) {
                env = TestEnv.dev;
            } else if (envStr.equals(TestEnv.stage.name())) {
                env = TestEnv.stage;
            } else {
                env = TestEnv.test;
            }
        }
    }
}
