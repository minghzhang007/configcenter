package com.lewis.configcenter.biz.service.impl;

import com.lewis.configcenter.biz.dao.local.CommitMapper;
import com.lewis.configcenter.biz.dao.local.ItemMapper;
import com.lewis.configcenter.biz.model.constants.Constants;
import com.lewis.configcenter.biz.model.constants.PublishStatusEnum;
import com.lewis.configcenter.biz.model.constants.StatusEnum;
import com.lewis.configcenter.biz.model.entity.CommitDO;
import com.lewis.configcenter.biz.model.entity.ItemDO;
import com.lewis.configcenter.biz.model.queryobject.ItemQO;
import com.lewis.configcenter.biz.service.ItemService;
import com.lewis.configcenter.biz.util.ConfigChangeSetBuilder;
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
public class ItemServiceImpl implements ItemService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Resource
    private ItemMapper appItemMapper;

    @Resource
    private CommitMapper commitMapper;

    @Resource
    private TransactionalComponent transactionalComponent;

    @Resource
    private ZkComponent zkComponent;

    @Override
    public boolean add(ItemDO appItemDO) {
        boolean result;
        try {
            result = transactionalComponent.masterCall(() -> {
                boolean insertDBResult = appItemMapper.insert(appItemDO) == 1;
                String path = getPath(appItemDO);
                boolean addZKNodeResult = zkComponent.createPersistentData(path, appItemDO.getValue());

                ConfigChangeSetBuilder builder = new ConfigChangeSetBuilder();
                builder.createItem(appItemDO);
                CommitDO commit = new CommitDO();
                commit.setAppId(appItemDO.getAppId());
                commit.setCreator(appItemDO.getCreator());
                commit.setCreateTime(appItemDO.getCreateTime());
                commit.setModifier(appItemDO.getModifier());
                commit.setUpdateTime(appItemDO.getUpdateTime());
                commit.setStatus(StatusEnum.NORMAL.getCode());
                String changeSet = builder.build();
                commit.setChangeSets(changeSet);

                commitMapper.insert(commit);
                System.out.println(commit);
                return insertDBResult && addZKNodeResult;
            });
        } catch (Exception e) {
            result = false;
            logger.error("添加配置项异常：ex:{}-{}-{}", e, e.getMessage(), e.getStackTrace());
        }
        return result;
    }

    private String getPath(ItemDO appItemDO) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/").append(Constants.ROOT_NODE)
                .append("/").append(appItemDO.getAppName())
                .append("/").append(appItemDO.getDictKey());
        return stringBuilder.toString();
    }

    @Override
    public boolean update(ItemDO appItemDO) {
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
    public PageList<ItemDO> pageList(ItemQO appItemQO) {
        PageTemplate<ItemDO> pageTemplate = () -> appItemMapper.list(appItemQO);
        return pageTemplate.getItemsByPage(appItemQO);
    }

    @Override
    public boolean publish(ItemQO appItemQO) {
        appItemQO.setPublishStatus(PublishStatusEnum.PUBLISH_NO.getCode());
        List<ItemDO> list = appItemMapper.list(appItemQO);
        return false;
    }
}
