package com.lewis.configcenter.biz.model.dto;

import com.lewis.configcenter.common.zookeeper.DResource;
import com.lewis.configcenter.common.zookeeper.ZkRegister;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhangminghua
 */
@Component
@Data
@NoArgsConstructor
public class SwitchConfig implements InitializingBean {

    @Resource
    private ZkRegister zkRegister;

    @DResource(appName = "snailreader", dictKey = "batchInsert")
    private String batchInsertNumber;

    @DResource(appName = "snailreader", dictKey = "batchQuery")
    private String batchQuery;

    @DResource(appName = "snailreader", dictKey = "useCache")
    private String useCache;

    @Override
    public void afterPropertiesSet() throws Exception {
        zkRegister.register(this);
    }

}
