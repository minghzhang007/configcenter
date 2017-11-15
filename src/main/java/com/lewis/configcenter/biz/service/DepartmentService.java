package com.lewis.configcenter.biz.service;

import com.lewis.configcenter.biz.model.entity.DepartmentDO;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface DepartmentService {

    boolean add(DepartmentDO departmentDO);

    boolean update(DepartmentDO departmentDO);

    List<DepartmentDO> list();
}
