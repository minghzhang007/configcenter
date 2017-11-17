package com.lewis.configcenter.biz.controller;

import com.lewis.configcenter.biz.model.entity.ApplicationDO;
import com.lewis.configcenter.biz.model.entity.DepartmentDO;
import com.lewis.configcenter.biz.model.queryobject.ApplicationQO;
import com.lewis.configcenter.biz.service.ApplicationService;
import com.lewis.configcenter.biz.service.DepartmentService;
import com.lewis.configcenter.common.component.page.PageList;
import com.lewis.configcenter.common.component.page.Paginator;
import com.lewis.configcenter.common.core.Json;
import com.lewis.configcenter.common.core.ResponseJson;
import com.lewis.configcenter.common.model.MsgConstant;
import com.lewis.configcenter.common.model.ResultMsg;
import com.lewis.configcenter.common.util.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/app")
public class ApplicationController {

    @Resource
    private ApplicationService applicationService;

    @GetMapping("/toQuery")
    public String toQuery() {

        return "app/appList";
    }

    @GetMapping("/query")
    @ResponseJson
    public PageList<ApplicationDO> queryPage(@Json ApplicationQO qo) {

        return applicationService.pageList(qo);
    }

    @GetMapping("/edit")
    public String editor(@Json ApplicationDO applicationDO, Model model) {
        model.addAttribute("app", JsonUtils.toString(applicationDO));
        return "app/appEdit";
    }

    @GetMapping("/add")
    @ResponseJson
    public ResultMsg add(@Json ApplicationDO applicationDO) {
        boolean result = applicationService.add(applicationDO);
        return new ResultMsg(result, MsgConstant.getAddMsg(result));
    }

    @GetMapping("/update")
    @ResponseJson
    public ResultMsg update(@Json ApplicationDO applicationDO) {
        boolean result = applicationService.update(applicationDO);
        return new ResultMsg(result, MsgConstant.getUpdateMsg(result));
    }

}
