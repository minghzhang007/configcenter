package com.lewis.configcenter.biz.service.impl;

import com.lewis.configcenter.biz.dao.local.AppItemMapper;
import com.lewis.configcenter.biz.model.constants.Constants;
import com.lewis.configcenter.biz.model.constants.PublishStatusEnum;
import com.lewis.configcenter.biz.model.entity.AppItemDO;
import com.lewis.configcenter.biz.model.queryobject.AppItemQO;
import com.lewis.configcenter.biz.service.AppItemService;
import com.lewis.configcenter.common.component.page.PageList;
import com.lewis.configcenter.common.component.page.PageTemplate;
import com.lewis.configcenter.common.component.transactional.TransactionalComponent;
import com.lewis.configcenter.common.zookeeper.ZkComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
@Service
public class AppItemServiceImpl implements AppItemService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Resource
    private AppItemMapper appItemMapper;

    @Resource
    private TransactionalComponent transactionalComponent;

    @Resource
    private ZkComponent zkComponent;

    @Override
    public boolean add(AppItemDO appItemDO) {
        boolean result;
        try {
            result = transactionalComponent.masterCall(() -> {
                boolean insertDBResult = appItemMapper.insert(appItemDO) == 1;
                String path = getPath(appItemDO);
                boolean addZKNodeResult = zkComponent.createPersistentData(path, appItemDO.getValue());
                return insertDBResult && addZKNodeResult;
            });
        } catch (Exception e) {
            result = false;
            logger.error("添加配置项异常：ex:{}-{}-{}", e, e.getMessage(), e.getStackTrace());
        }
        return result;
    }

    private String getPath(AppItemDO appItemDO) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/").append(Constants.ROOT_NODE)
                .append("/").append(appItemDO.getAppName())
                .append("/").append(appItemDO.getEnvName())
                .append("/").append(appItemDO.getDictKey());
        return stringBuilder.toString();
    }

    @Override
    public boolean update(AppItemDO appItemDO) {
        boolean result;
        try {
            result = transactionalComponent.masterCall(() -> {
                boolean updateDBResult = appItemMapper.update(appItemDO) == 1;
                boolean updateZKNodeResult = zkComponent.setData(getPath(appItemDO), appItemDO.getValue());
                return updateDBResult && updateZKNodeResult;
            });
        } catch (Exception e) {
            result = false;
            logger.error("修改配置项异常：ex:{}-{}-{}", e, e.getMessage(), e.getStackTrace());
        }
        return result;
    }

    @Override
    public PageList<AppItemDO> pageList(AppItemQO appItemQO) {
        PageTemplate<AppItemDO> pageTemplate = () -> appItemMapper.list(appItemQO);
        return pageTemplate.getItemsByPage(appItemQO);
    }

    @Override
    public boolean publish(AppItemQO appItemQO) {
        appItemQO.setPublishStatus(PublishStatusEnum.PUBLISH_NO.getCode());
        List<AppItemDO> list = appItemMapper.list(appItemQO);
        return false;
    }
}
