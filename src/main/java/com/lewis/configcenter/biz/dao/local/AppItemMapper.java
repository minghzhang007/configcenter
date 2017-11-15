package com.lewis.configcenter.biz.dao.local;

import com.lewis.configcenter.biz.model.entity.AppItemDO;
import com.lewis.configcenter.biz.model.queryobject.AppItemQO;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface AppItemMapper {

    List<AppItemDO> list(AppItemQO qo);

    AppItemDO queryOne(AppItemQO qo);

    int insert(AppItemDO appItemDO);

    int update(AppItemDO appItemDO);
}
