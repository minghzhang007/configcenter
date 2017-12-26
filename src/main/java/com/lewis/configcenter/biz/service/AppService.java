package com.lewis.configcenter.biz.service;

import com.lewis.configcenter.biz.model.entity.AppDO;
import com.lewis.configcenter.biz.model.queryobject.AppQO;
import com.lewis.configcenter.common.component.page.PageList;
import com.lewis.configcenter.common.model.SelectModel;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface AppService {

    boolean add(AppDO applicationDO);

    boolean update(AppDO applicationDO);

    AppDO queryOne(AppQO qo);

    PageList<AppDO> pageList(AppQO qo);

    List<SelectModel> queryAppIds();
}
