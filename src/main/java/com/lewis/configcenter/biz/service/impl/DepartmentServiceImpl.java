package com.lewis.configcenter.biz.service.impl;

import com.lewis.configcenter.biz.dao.local.DepartmentMapper;
import com.lewis.configcenter.biz.model.entity.DepartmentDO;
import com.lewis.configcenter.biz.service.DepartmentService;
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
        return departmentMapper.insert(departmentDO) == 1;
    }

    @Override
    public boolean delete(Long id) {
        return departmentMapper.delete(id) == 0;
    }

    @Override
    public List<DepartmentDO> list() {
        return departmentMapper.list();
    }
}
