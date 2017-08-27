package com.zeratul.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zeratul.config.EnvConfig;
import com.zeratul.config.TestEnv;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

/**
 * @author dreamyao
 * @version 1.0.0
 * @title dubbo服务的获取以及缓存
 * @Date 2017/8/27 17:10
 */
public class DubboService {

    private ApplicationConfig application;
    private RegistryConfig registry;
    private TestEnv env = TestEnv.test;
    private Cache<String, Object> dubboServiceCache = CacheBuilder.newBuilder().build();

    public DubboService(TestEnv env) {
        this.env = env;
        ZookeeperConfig config = new ZookeeperConfig();
        String host = config.getZkHosts().getProperty(env.name());
        if (host != null) {
            initDubbo(host);
        } else {
            throw new RuntimeException("----------------初始化Dubbo环境失败！----------------");
        }
    }

    public DubboService(String host) {
        if (host != null) {
            initDubbo(host);
        }
    }

    /**
     * 获取远程服务代理
     *
     * @param clazz   接口Class对象
     * @param version 服务版本
     * @param <T>     T
     * @return        对应服务
     */
    public <T> T getService(Class<T> clazz, String... version) {
        EnvConfig config = new EnvConfig();
        try {
            if (config.getHostDomain() != null) {
                String url = "dubbo://" + config.getHostDomain() + "/"
                        + clazz.getName();
                T t = getServiceByUrl(clazz, url, version);
                checkService(t, clazz);
                return t;
            }
        } catch (Exception e) {
            throw new RuntimeException("--------------获取dubbo服务异常！--------------", e);
        }
        return getService(clazz, env, version);
    }

    /**
     * 先从缓存中获取服务，如果本地缓存没有此服务则从远程注册中心获取
     *
     * @param clazz   接口Class对象
     * @param env     对应的测试环境
     * @param version 服务版本
     * @param <T>     T
     * @return        对应服务
     */
    @SuppressWarnings("unchecked")
    private <T> T getService(Class<T> clazz, TestEnv env, String... version) {
        String key = clazz.getName() + "_" + env.name();
        if (version != null && version.length > 0) {
            key = key + "_" + version[0];
        }
        Object object;
        try {
            object = dubboServiceCache.get(key, () -> getRemoteService(clazz, version));
        } catch (ExecutionException e) {
            throw new RuntimeException("--------------获取dubbo服务异常！--------------", e);
        }
        return (T) object;
    }

    /**
     * 从远程注册中心获取服务
     *
     * @param clazz   接口Class对象
     * @param version 服务版本
     * @param <T>     T
     * @return        对应服务
     */
    private <T> T getRemoteService(Class<T> clazz, String... version) {
        // 引用远程服务
        ReferenceConfig<T> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        if (version != null && version.length != 0) {
            reference.setVersion(version[0]);
        }
        reference.setTimeout(10000);
        reference.setInterface(clazz);
        return reference.get();
    }

    /**
     * 检测获取的服务是否存在
     *
     * @param t
     * @param clazz 接口Class对象
     * @param <T>   T
     * @throws Exception
     */
    private <T> void checkService(T t, Class<T> clazz) throws Exception {
        Method method = clazz.getMethods()[0];
        Class[] parameterTypes = method.getParameterTypes();
        Object[] params = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Object o1 = parameterTypes[i].newInstance();
            params[i] = o1;
        }
        method.invoke(t, params);
    }

    /**
     * 获取点对点直连的service
     * @param clazz   接口Class对象
     * @param url     URL地址
     * @param version 服务版本
     * @param <T>     T
     * @return        对象
     */
    private  <T> T getServiceByUrl(Class<T> clazz, String url, String... version) {
        ReferenceConfig<T> reference = new ReferenceConfig<T>();
        reference.setApplication(application);
        reference.setUrl(url);
        reference.setInterface(clazz);
        reference.setTimeout(10000);
        if (version != null && version.length != 0) {
            reference.setVersion(version[0]);
        }
        return reference.get();
    }

    private void initDubbo(String host) {
        if (host == null || "".equals(host)) {
            throw new RuntimeException("------------Zookeeper地址为空！------------");
        }
        application = new ApplicationConfig();
        application.setName("zeratul_config");
        // 连接注册中心配置
        registry = new RegistryConfig();
        registry.setAddress("zookeeper://" + host);
        registry.setUsername("zeratul");
    }
}
