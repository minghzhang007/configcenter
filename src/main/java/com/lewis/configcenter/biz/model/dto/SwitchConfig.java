package com.lewis.configcenter.biz.model.dto;

import com.lewis.configcenter.common.core.App;
import com.lewis.configcenter.common.zookeeper.DResource;
import com.lewis.configcenter.common.zookeeper.DResourceHolder;
import com.lewis.configcenter.common.zookeeper.ZkComponent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author zhangminghua
 */
@Component
public class SwitchConfig implements InitializingBean {

    @Resource
    private App app;

    @Resource
    private DResourceHolder dResourceHolder;

    @Resource
    private ZkComponent zkComponent;

    @DResource(appName = "snailreader", dictKey = "batchInsert")
    private String batchInsertNumber;

    @DResource(appName = "snailreader", dictKey = "batchQuery")
    private String batchQuery;

    @DResource(appName = "snailreader", dictKey = "useCache")
    private String useCache;

    @Override
    public void afterPropertiesSet() throws Exception {
        //todo 将标注了 DResource注解的全部都 订阅到注册中心去
        String env = app.activeProfile();
        Field[] declaredFields = this.getClass().getDeclaredFields();
        if (ArrayUtils.isNotEmpty(declaredFields)) {
            Arrays.asList(declaredFields).stream().filter(item -> item.getAnnotation(DResource.class) != null).forEach(item -> {
                DResource dResource = item.getAnnotation(DResource.class);
                String appName = dResource.appName();
                String dictKey = dResource.dictKey();
                String path = getPath(appName, env, dictKey);
                zkComponent.addNodeListener(path);
                String value = zkComponent.getData(path);
                System.out.println("dictKey:" + dictKey + ", value:" + value);
                try {
                    item.set(this, value);
                    dResourceHolder.getDictKeyClassMapping().put(dictKey, this.getClass());
                    dResourceHolder.getDictKeyObjectMapping().put(dictKey, this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private String getPath(String appName, String env, String dictKey) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/configcenter/").append(appName).append("/").append(env).append("/").append(dictKey);
        return stringBuilder.toString();
    }

    public String getBatchInsertNumber() {
        return batchInsertNumber;
    }

    public String getBatchQuery() {
        return batchQuery;
    }

    public String getUseCache() {
        return useCache;
    }

    @Override
    public String toString() {
        return "SwitchConfig{" +
                "batchInsertNumber='" + batchInsertNumber + '\'' +
                ", batchQuery='" + batchQuery + '\'' +
                ", useCache='" + useCache + '\'' +
                '}';
    }
}
