package com.lewis.configcenter.biz.service;

import com.lewis.configcenter.biz.model.entity.EnvironmentDO;
import com.lewis.configcenter.common.component.page.PageList;
import com.lewis.configcenter.common.component.page.Paginator;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface EnvironmentService {

    boolean add(EnvironmentDO environmentDO);

    boolean delete(Long id);

    PageList<EnvironmentDO> pageList(Paginator paginator);
}
