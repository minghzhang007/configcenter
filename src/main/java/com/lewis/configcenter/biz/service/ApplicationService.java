package com.lewis.configcenter.biz.service;

import com.lewis.configcenter.biz.model.entity.ApplicationDO;
import com.lewis.configcenter.biz.model.queryobject.ApplicationQO;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface ApplicationService {

    boolean add(ApplicationDO applicationDO);

    boolean update(ApplicationDO applicationDO);

    ApplicationDO queryOne(ApplicationQO qo);

    List<ApplicationDO> list(ApplicationQO qo);
}
