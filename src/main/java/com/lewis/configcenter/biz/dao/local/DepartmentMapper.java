package com.lewis.configcenter.biz.dao.local;

import com.lewis.configcenter.biz.model.entity.DepartmentDO;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface DepartmentMapper {

    List<DepartmentDO> list();

    int insert(DepartmentDO departmentDO);

    int update(DepartmentDO departmentDO);
}
