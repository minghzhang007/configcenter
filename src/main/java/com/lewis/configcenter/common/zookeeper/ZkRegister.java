package com.lewis.configcenter.common.zookeeper;

import com.lewis.configcenter.biz.model.constants.Constants;
import com.lewis.configcenter.common.core.App;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * @author zhangminghua
 */
@Component
public class ZkRegister {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Resource
    private App app;

    @Resource
    private DResourceHolder dResourceHolder;

    @Resource
    private ZkComponent zkComponent;

    public void register(Object obj) {
        String env = app.activeProfile();
        Class<?> clazzType = obj.getClass();
        Field[] declaredFields = clazzType.getDeclaredFields();
        if (ArrayUtils.isNotEmpty(declaredFields)) {
            Arrays.asList(declaredFields).stream().filter(item -> item.getAnnotation(DResource.class) != null).forEach(field -> {
                DResource dResource = field.getAnnotation(DResource.class);
                String appName = dResource.appName();
                String dictKey = dResource.dictKey();
                String path = getPath(appName, env, dictKey);
                zkComponent.addNodeListener(path);
                String value = zkComponent.getData(path);
                System.out.println("dictKey:" + dictKey + ", value:" + value);
                try {
                    if (Modifier.isPrivate(field.getModifiers())) {
                        field.setAccessible(true);
                    }
                    field.set(obj, value);
                    dResourceHolder.getDictKeyClassMapping().put(dictKey, clazzType);
                    dResourceHolder.getDictKeyObjectMapping().put(dictKey, obj);
                } catch (IllegalAccessException e) {
                    logger.error("ZKRegister register occur ex:{}-{}-{}-{}", e, e.getCause(), e.getMessage(), e.getStackTrace());
                }
            });
        }
    }


    private String getPath(String appName, String env, String dictKey) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/").append(Constants.ROOT_NODE)
                .append("/").append(appName)
                .append("/").append(env)
                .append("/").append(dictKey);
        return stringBuilder.toString();
    }
}
