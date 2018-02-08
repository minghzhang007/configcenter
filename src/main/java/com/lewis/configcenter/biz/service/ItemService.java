package com.lewis.configcenter.biz.service;

import com.lewis.configcenter.biz.model.entity.AppDO;
import com.lewis.configcenter.biz.model.entity.ItemDO;
import com.lewis.configcenter.biz.model.queryobject.ItemQO;
import com.lewis.configcenter.biz.model.vo.ItemDTO;
import com.lewis.configcenter.biz.model.vo.PublishVO;
import com.lewis.configcenter.common.component.page.PageList;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface ItemService {

    boolean add(ItemDO appItemDO);

    boolean update(ItemDO appItemDO);

    PageList<ItemDTO> pageList(ItemQO appItemQO);

    List<ItemDTO> changes(AppDO appDO);

    boolean delete(ItemDO itemDO);

}
