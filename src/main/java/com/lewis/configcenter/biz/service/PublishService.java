package com.lewis.configcenter.biz.service;

import com.lewis.configcenter.biz.model.entity.ReleaseDO;
import com.lewis.configcenter.biz.model.vo.PublishVO;

public interface PublishService {
    ReleaseDO queryLastRelease(String appId);

    boolean publish(PublishVO publishVO);
}
