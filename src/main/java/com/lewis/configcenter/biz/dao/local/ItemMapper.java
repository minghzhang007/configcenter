package com.lewis.configcenter.biz.dao.local;

import com.lewis.configcenter.biz.model.entity.ItemDO;
import com.lewis.configcenter.biz.model.queryobject.ItemQO;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface ItemMapper {

    List<ItemDO> list(ItemQO qo);

    ItemDO queryOne(ItemQO qo);

    int insert(ItemDO appItemDO);

    int update(ItemDO appItemDO);
}
