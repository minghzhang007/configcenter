package com.lewis.configcenter.biz.dao.local;

import com.lewis.configcenter.biz.model.entity.ReleaseHistoryDO;

/**
 * Created by lewis0077 on 2018/1/17.
 */
public interface ReleaseHistoryMapper {

    ReleaseHistoryDO queryLaststRelease(String appId);

    int insert(ReleaseHistoryDO releaseHistoryDO);
}
