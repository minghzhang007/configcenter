package com.lewis.configcenter.biz.service;

import com.lewis.configcenter.biz.model.entity.ItemDO;
import com.lewis.configcenter.biz.model.queryobject.ItemQO;
import com.lewis.configcenter.common.component.page.PageList;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface ItemService {

    boolean add(ItemDO appItemDO);

    boolean update(ItemDO appItemDO);

    PageList<ItemDO> pageList(ItemQO appItemQO);

    boolean publish(ItemQO appItemQO);
}
