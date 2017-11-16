package com.lewis.configcenter.biz.service.impl;

import com.lewis.configcenter.biz.dao.local.EnvironmentMapper;
import com.lewis.configcenter.biz.model.entity.BaseEntityHelper;
import com.lewis.configcenter.biz.model.entity.EnvironmentDO;
import com.lewis.configcenter.biz.service.EnvironmentService;
import com.lewis.configcenter.common.component.page.PageList;
import com.lewis.configcenter.common.component.page.PageTemplate;
import com.lewis.configcenter.common.component.page.Paginator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/11/15.
 */
@Service
public class EnvironmentServiceImpl implements EnvironmentService {

    @Resource
    private EnvironmentMapper environmentMapper;

    @Override
    public boolean add(EnvironmentDO environmentDO) {
        BaseEntityHelper.setDefaultValue(environmentDO);
        return environmentMapper.insert(environmentDO) == 1;
    }

    @Override
    public boolean delete(Long id) {
        return environmentMapper.delete(id) == 1;
    }

    @Override
    public PageList<EnvironmentDO> pageList(Paginator paginator) {
        PageTemplate<EnvironmentDO> pageTemplate = () -> environmentMapper.list();
        return pageTemplate.getItemsByPage(paginator);
    }


}
