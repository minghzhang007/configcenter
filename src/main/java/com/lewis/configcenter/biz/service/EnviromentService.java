package com.lewis.configcenter.biz.service;

import com.lewis.configcenter.biz.model.entity.EnvironmentDO;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface EnviromentService {

    boolean add(EnvironmentDO environmentDO);

    boolean update(EnvironmentDO environmentDO);

    List<EnvironmentDO> list();
}
