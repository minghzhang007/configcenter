package com.lewis.configcenter.biz.service.impl;

import com.lewis.configcenter.biz.dao.local.AppItemMapper;
import com.lewis.configcenter.biz.model.entity.AppItemDO;
import com.lewis.configcenter.biz.model.queryobject.AppItemQO;
import com.lewis.configcenter.biz.service.AppItemService;
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
    public List<AppItemDO> list(AppItemQO appItemQO) {
        return appItemMapper.list(appItemQO);
    }
}
