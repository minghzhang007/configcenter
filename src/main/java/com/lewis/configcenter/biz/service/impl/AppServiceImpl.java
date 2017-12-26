package com.lewis.configcenter.biz.service.impl;

import com.lewis.configcenter.biz.dao.local.AppMapper;
import com.lewis.configcenter.biz.model.entity.AppDO;
import com.lewis.configcenter.biz.model.queryobject.AppQO;
import com.lewis.configcenter.biz.service.AppService;
import com.lewis.configcenter.common.component.page.PageList;
import com.lewis.configcenter.common.component.page.PageTemplate;
import com.lewis.configcenter.common.model.SelectModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/11/15.
 */
@Service
public class AppServiceImpl implements AppService {

    @Resource
    private AppMapper applicationMapper;

    @Override
    public boolean add(AppDO applicationDO) {
        return applicationMapper.insert(applicationDO) == 1;
    }

    @Override
    public boolean update(AppDO applicationDO) {
        return applicationMapper.update(applicationDO) == 1;
    }

    @Override
    public AppDO queryOne(AppQO qo) {
        return applicationMapper.queryOne(qo);
    }

    @Override
    public PageList<AppDO> pageList(AppQO qo) {
        PageTemplate<AppDO> pageTemplate = () -> applicationMapper.list(qo);
        return pageTemplate.getItemsByPage(qo);
    }

    @Override
    public List<SelectModel> queryAppIds() {
        List<AppDO> list = applicationMapper.list(new AppQO());
        return list.stream().map(item -> {
            SelectModel selectModel = new SelectModel();
            selectModel.setKey(item.getAppId());
            selectModel.setValue(item.getAppName());
            return selectModel;
        }).collect(Collectors.toList());
    }
}
