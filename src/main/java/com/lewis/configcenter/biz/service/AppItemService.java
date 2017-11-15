package com.lewis.configcenter.biz.service;

import com.lewis.configcenter.biz.model.entity.AppItemDO;
import com.lewis.configcenter.biz.model.queryobject.AppItemQO;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface AppItemService {

    boolean add(AppItemDO appItemDO);

    boolean update(AppItemDO appItemDO);

    List<AppItemDO> list(AppItemQO appItemQO);
}
