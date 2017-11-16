package com.lewis.configcenter.biz.service.impl;

import com.lewis.configcenter.biz.dao.local.DepartmentMapper;
import com.lewis.configcenter.biz.model.entity.BaseEntityHelper;
import com.lewis.configcenter.biz.model.entity.DepartmentDO;
import com.lewis.configcenter.biz.service.DepartmentService;
import com.lewis.configcenter.common.component.page.PageList;
import com.lewis.configcenter.common.component.page.PageTemplate;
import com.lewis.configcenter.common.component.page.Paginator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public boolean add(DepartmentDO departmentDO) {
        BaseEntityHelper.setDefaultValue(departmentDO);
        return departmentMapper.insert(departmentDO) == 1;
    }

    @Override
    public boolean delete(Long id) {
        return departmentMapper.delete(id) == 1;
    }

    @Override
    public PageList<DepartmentDO> pageList(Paginator paginator) {
        PageTemplate<DepartmentDO> pageTemplate = () -> departmentMapper.list();
        return pageTemplate.getItemsByPage(paginator);
    }

    @Override
    public boolean update(DepartmentDO departmentDO) {
        return departmentMapper.update(departmentDO) == 1;
    }
}
