package com.lewis.configcenter.biz.dao.local;

import com.lewis.configcenter.biz.model.entity.ApplicationDO;
import com.lewis.configcenter.biz.model.queryobject.ApplicationQO;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface ApplicationMapper {
    List<ApplicationDO> list(ApplicationQO qo);

    ApplicationDO queryOne(ApplicationQO qo);

    int insert(ApplicationDO applicationDO);

    int update(ApplicationDO applicationDO);
}
