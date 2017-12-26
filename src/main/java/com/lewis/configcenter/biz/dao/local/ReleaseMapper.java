package com.lewis.configcenter.biz.dao.local;

import com.lewis.configcenter.biz.model.entity.ReleaseDO;
import com.lewis.configcenter.biz.model.queryobject.ReleaseQO;

import java.util.List;

public interface ReleaseMapper {

    List<ReleaseDO> list(ReleaseQO qo);

    int insert(ReleaseDO releaseDO);

    int delete(Long id);
}
