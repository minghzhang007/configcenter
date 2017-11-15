package com.lewis.configcenter.biz.service.impl;

import com.lewis.configcenter.biz.dao.local.ApplicationMapper;
import com.lewis.configcenter.biz.model.entity.ApplicationDO;
import com.lewis.configcenter.biz.model.queryobject.ApplicationQO;
import com.lewis.configcenter.biz.service.ApplicationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    private ApplicationMapper applicationMapper;

    @Override
    public boolean add(ApplicationDO applicationDO) {
        return applicationMapper.insert(applicationDO) == 1;
    }

    @Override
    public boolean update(ApplicationDO applicationDO) {
        return applicationMapper.update(applicationDO) == 1;
    }

    @Override
    public ApplicationDO queryOne(ApplicationQO qo) {
        return applicationMapper.queryOne(qo);
    }

    @Override
    public List<ApplicationDO> list(ApplicationQO qo) {
        return applicationMapper.list(qo);
    }
}
