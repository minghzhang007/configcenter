package com.lewis.configcenter.biz.service.impl;

import com.lewis.configcenter.biz.dao.local.AppItemMapper;
import com.lewis.configcenter.biz.model.constants.PublishStatusEnum;
import com.lewis.configcenter.biz.model.entity.AppItemDO;
import com.lewis.configcenter.biz.model.queryobject.AppItemQO;
import com.lewis.configcenter.biz.service.AppItemService;
import com.lewis.configcenter.common.component.page.PageList;
import com.lewis.configcenter.common.component.page.PageTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
@Service
public class AppItemServiceImpl implements AppItemService {

    @Resource
    private AppItemMapper appItemMapper;

    @Override
    public boolean add(AppItemDO appItemDO) {
        return appItemMapper.insert(appItemDO) == 1;
    }

    @Override
    public boolean update(AppItemDO appItemDO) {
        return appItemMapper.update(appItemDO) == 1;
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
