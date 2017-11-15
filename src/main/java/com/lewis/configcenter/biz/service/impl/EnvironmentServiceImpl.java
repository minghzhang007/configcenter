package com.lewis.configcenter.biz.service.impl;

import com.lewis.configcenter.biz.dao.local.EnviromentMapper;
import com.lewis.configcenter.biz.model.entity.EnvironmentDO;
import com.lewis.configcenter.biz.service.EnviromentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
@Service
public class EnvironmentServiceImpl implements EnviromentService {

    @Resource
    private EnviromentMapper enviromentMapper;

    @Override
    public boolean add(EnvironmentDO environmentDO) {
        return enviromentMapper.insert(environmentDO) == 1;
    }

    @Override
    public boolean update(EnvironmentDO environmentDO) {
        return enviromentMapper.update(environmentDO) == 1;
    }

    @Override
    public List<EnvironmentDO> list() {

        return enviromentMapper.list();
    }
}
