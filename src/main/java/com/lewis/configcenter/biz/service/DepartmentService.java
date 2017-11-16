package com.lewis.configcenter.biz.service;

import com.lewis.configcenter.biz.model.entity.DepartmentDO;
import com.lewis.configcenter.common.component.page.PageList;
import com.lewis.configcenter.common.component.page.Paginator;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface DepartmentService {

    PageList<DepartmentDO> pageList(Paginator paginator);

    boolean update(DepartmentDO departmentDO);

    boolean add(DepartmentDO departmentDO);

    boolean delete(Long id);
}
