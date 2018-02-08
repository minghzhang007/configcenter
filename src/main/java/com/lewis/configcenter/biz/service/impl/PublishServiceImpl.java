package com.lewis.configcenter.biz.service.impl;

import com.google.common.collect.Maps;
import com.lewis.configcenter.biz.dao.local.CommitMapper;
import com.lewis.configcenter.biz.dao.local.ItemMapper;
import com.lewis.configcenter.biz.dao.local.ReleaseHistoryMapper;
import com.lewis.configcenter.biz.dao.local.ReleaseMapper;
import com.lewis.configcenter.biz.model.constants.ReleaseTypeEnum;
import com.lewis.configcenter.biz.model.constants.StatusEnum;
import com.lewis.configcenter.biz.model.entity.ItemDO;
import com.lewis.configcenter.biz.model.entity.ReleaseDO;
import com.lewis.configcenter.biz.model.entity.ReleaseHistoryDO;
import com.lewis.configcenter.biz.model.queryobject.ItemQO;
import com.lewis.configcenter.biz.model.vo.PublishVO;
import com.lewis.configcenter.biz.service.PublishService;
import com.lewis.configcenter.common.util.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PublishServiceImpl implements PublishService {

    @Resource
    private ItemMapper appItemMapper;

    @Resource
    private CommitMapper commitMapper;

    @Resource
    private ReleaseMapper releaseMapper;

    @Resource
    private ReleaseHistoryMapper releaseHistoryMapper;

    @Override
    public ReleaseDO queryLastRelease(String appId) {
        return releaseMapper.queryLastRelease(appId);
    }

    @Override
    public boolean publish(PublishVO publishVO) {
        //1、新增ReleaseHistory记录、新增Release记录、
        ReleaseDO lastRelease = releaseMapper.queryLastRelease(publishVO.getAppId());
        Long previousReleaseId = lastRelease == null ? 0 : lastRelease.getId();

        Map<String, String> configurations = getConfigurationsForAppId(publishVO);

        ReleaseDO releaseDO = createRelease(publishVO, configurations);
        releaseMapper.insert(releaseDO);
        releaseDO = releaseMapper.queryLastRelease(publishVO.getAppId());
        Long releaseId = releaseDO.getId();
        ReleaseHistoryDO releaseHistoryDO = createReleaseHistory(previousReleaseId, releaseId, publishVO.getAppId());
        releaseHistoryMapper.insert(releaseHistoryDO);

        //2、通知各个客户端
        return true;
    }

    private ReleaseHistoryDO createReleaseHistory(Long previousReleaseId, Long releaseId, String appId) {
        ReleaseHistoryDO releaseHistoryDO = new ReleaseHistoryDO();
        releaseHistoryDO.setAppId(appId);
        releaseHistoryDO.setReleaseId(releaseId);
        releaseHistoryDO.setPreviousReleaseId(previousReleaseId);
        releaseHistoryDO.setType(ReleaseTypeEnum.NORMAL.getCode());
        releaseHistoryDO.setStatus(StatusEnum.NORMAL.getCode());
        long now = System.currentTimeMillis();
        releaseHistoryDO.setCreateTime(now);
        releaseHistoryDO.setUpdateTime(now);
        //todo
        releaseHistoryDO.setCreator("zhangminghua01");
        releaseHistoryDO.setModifier("zhangminghua01");
        return releaseHistoryDO;
    }

    private ReleaseDO createRelease(PublishVO publishVO, Map<String, String> configurationMap) {
        ReleaseDO releaseDO = new ReleaseDO();
        releaseDO.setAppId(publishVO.getAppId());
        releaseDO.setComment(publishVO.getComment());
        releaseDO.setConfigurations(JsonUtils.toString(configurationMap));
        releaseDO.setReleaseKey(UUID.randomUUID().toString());
        releaseDO.setName(publishVO.getReleaseName());
        releaseDO.setStatus(StatusEnum.NORMAL.getCode());
        long now = System.currentTimeMillis();
        releaseDO.setCreateTime(now);
        releaseDO.setUpdateTime(now);
        //todo
        releaseDO.setCreator("zhangminghua01");
        releaseDO.setModifier("zhangminghua01");
        return releaseDO;
    }

    private Map<String, String> getConfigurationsForAppId(PublishVO publishVO) {
        Map<String, String> configurations = Maps.newHashMap();
        ItemQO itemQO = new ItemQO();
        itemQO.setAppId(publishVO.getAppId());
        List<ItemDO> itemDOS = appItemMapper.list(itemQO);
        if (CollectionUtils.isNotEmpty(itemDOS)) {
            itemDOS.forEach(item -> configurations.put(item.getDictKey(), item.getValue()));
        }
        return configurations;
    }
}
