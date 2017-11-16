package com.lewis.configcenter.biz.dao.local;

import com.lewis.configcenter.biz.model.entity.EnvironmentDO;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface EnvironmentMapper {

    List<EnvironmentDO> list();

    int insert(EnvironmentDO environmentDO);

    int delete(Long id);

}
