package com.lewis.configcenter.common.component.page;

import com.github.pagehelper.PageHelper;

import java.util.List;

@FunctionalInterface
public interface PageTemplate<T> {

    default PageList<T> getItemsByPage(Paginator paginator) {
        PageHelper.startPage(paginator.getCurrentPage(), paginator.getPageSize());
        List<T> items = queryItems();
        PageList<T> pageList = PageUtil.getPageList(paginator, items);
        PageHelper.clearPage();
        return pageList;
    }


    List<T> queryItems();
}
