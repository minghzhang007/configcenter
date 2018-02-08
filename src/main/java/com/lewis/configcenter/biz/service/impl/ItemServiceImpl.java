package com.lewis.configcenter.biz.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lewis.configcenter.biz.dao.local.CommitMapper;
import com.lewis.configcenter.biz.dao.local.ItemMapper;
import com.lewis.configcenter.biz.dao.local.ReleaseMapper;
import com.lewis.configcenter.biz.model.constants.Constants;
import com.lewis.configcenter.biz.model.constants.StatusEnum;
import com.lewis.configcenter.biz.model.entity.AppDO;
import com.lewis.configcenter.biz.model.entity.CommitDO;
import com.lewis.configcenter.biz.model.entity.ItemDO;
import com.lewis.configcenter.biz.model.entity.ReleaseDO;
import com.lewis.configcenter.biz.model.queryobject.ItemQO;
import com.lewis.configcenter.biz.model.vo.ItemDTO;
import com.lewis.configcenter.biz.service.ItemService;
import com.lewis.configcenter.biz.util.ConfigChangeSetBuilder;
import com.lewis.configcenter.common.component.page.PageList;
import com.lewis.configcenter.common.component.page.PageTemplate;
import com.lewis.configcenter.common.component.transactional.TransactionalComponent;
import com.lewis.configcenter.common.util.JsonUtils;
import com.lewis.configcenter.common.util.ListUtil;
import com.lewis.configcenter.common.util.StringUtil;
import com.lewis.configcenter.common.zookeeper.ZkComponent;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private ReleaseMapper releaseMapper;

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
                .append("/").append(appItemDO.getAppId())
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
    public PageList<ItemDTO> pageList(ItemQO appItemQO) {
        PageTemplate<ItemDO> pageTemplate = () -> appItemMapper.list(appItemQO);
        PageList<ItemDO> itemsByPage = pageTemplate.getItemsByPage(appItemQO);
        Collection<ItemDO> data = itemsByPage.getData();
        List<ItemDTO> list = convert(data, appItemQO.getAppId());
        return new PageList<>(list, itemsByPage.getPaginator());
    }

    @Override
    public List<ItemDTO> changes(AppDO appDO) {
        ItemQO appItemQO = new ItemQO();
        appItemQO.setAppId(appDO.getAppId());
        List<ItemDO> itemDOS = appItemMapper.list(appItemQO);
        List<ItemDTO> list = convert(itemDOS, appDO.getAppId());
        return list.stream().filter(item -> item.isNew() || item.isModified() || item.isDeleted()).collect(Collectors.toList());
    }

    @Override
    public boolean delete(ItemDO itemDO) {
        int deleteCount = appItemMapper.delete(itemDO.getId());
        return deleteCount == 1;
    }

    private List<ItemDTO> convert(Collection<ItemDO> data, String appId) {
        if (CollectionUtils.isEmpty(data)) {
            return Lists.newArrayList();
        }

        ReleaseDO releaseDO = releaseMapper.queryLastRelease(appId);
        Map<String, String> releaseItems = Maps.newHashMap();
        if (releaseDO != null) {
            releaseItems = JsonUtils.toBean(releaseDO.getConfigurations(), Map.class);
        }
        Map<String, String> finalReleaseItems = releaseItems;
        List<ItemDTO> collect = data.stream().map(itemDO -> transform(itemDO, finalReleaseItems)).collect(Collectors.toList());
        List<ItemDTO> deleteItems = createDeleteItems(data, releaseItems);
        collect.addAll(deleteItems);
        return collect;
    }

    private List<ItemDTO> createDeleteItems(Collection<ItemDO> data, Map<String, String> releaseItems) {
        List<ItemDTO> list = Lists.newLinkedList();
        Map<String, ItemDO> dictKeyMapping = ListUtil.collectToMap(data, item -> item.getDictKey(), item -> item);
        releaseItems.forEach((key, value) -> {
            ItemDO itemDO = dictKeyMapping.get(key);
            if (itemDO == null) {
                ItemDTO deletedItem = new ItemDTO();
                deletedItem.setDeleted(true);
                itemDO = new ItemDO();
                itemDO.setDictKey(key);
                itemDO.setValue(value);
                deletedItem.setItem(itemDO);
                deletedItem.setOldValue(value);
                deletedItem.setNewValue(null);
                deletedItem.setModified(false);
                list.add(deletedItem);
            }
        });
        return list;
    }

    private ItemDTO transform(ItemDO itemDO, Map<String, String> releaseItems) {
        ItemDTO dto = new ItemDTO();
        dto.setItem(itemDO);
        String newValue = itemDO.getValue();
        String oldValue = releaseItems.get(itemDO.getDictKey());
        if (!StringUtil.isEmpty(itemDO.getDictKey()) && (oldValue == null || !newValue.equals(oldValue))) {
            dto.setOldValue(oldValue == null ? null : oldValue);
            dto.setNewValue(newValue);
            if (dto.getOldValue() == null) {
                dto.setNew(true);
            } else {
                dto.setModified(true);
            }
        }
        return dto;
    }

}
