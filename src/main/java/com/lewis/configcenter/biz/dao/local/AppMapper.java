package com.lewis.configcenter.biz.dao.local;

import com.lewis.configcenter.biz.model.entity.AppDO;
import com.lewis.configcenter.biz.model.queryobject.AppQO;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface AppMapper {
    List<AppDO> list(AppQO qo);

    AppDO queryOne(AppQO qo);

    int insert(AppDO applicationDO);

    int update(AppDO applicationDO);

    int delete(Long id);
}
