package com.lewis.configcenter.biz.controller;

import com.lewis.configcenter.biz.model.entity.EnvironmentDO;
import com.lewis.configcenter.biz.service.EnvironmentService;
import com.lewis.configcenter.common.core.Json;
import com.lewis.configcenter.common.core.ResponseJson;
import com.lewis.configcenter.common.component.page.PageList;
import com.lewis.configcenter.common.component.page.Paginator;
import com.lewis.configcenter.common.model.MsgConstant;
import com.lewis.configcenter.common.model.ResultMsg;
import com.lewis.configcenter.common.util.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/env")
public class EnvironmentController {

    @Resource
    private EnvironmentService environmentService;

    @GetMapping("/toQuery")
    public String toQuery() {

        return "env/envList";
    }

    @GetMapping("/query")
    @ResponseJson
    public PageList<EnvironmentDO> queryPage(@Json Paginator paginator) {

        return environmentService.pageList(paginator);
    }

    @GetMapping("/edit")
    public String editor(@Json EnvironmentDO environmentDO, Model model) {
        model.addAttribute("env", JsonUtils.toString(environmentDO));
        return "env/envEdit";
    }

    @GetMapping("/add")
    @ResponseJson
    public ResultMsg add(@Json EnvironmentDO environmentDO) {
        boolean result = environmentService.add(environmentDO);
        return new ResultMsg(result, MsgConstant.getAddMsg(result));
    }

    @GetMapping("/update")
    @ResponseJson
    public ResultMsg update(@Json EnvironmentDO environmentDO) {
        boolean result = environmentService.update(environmentDO);
        return new ResultMsg(result, MsgConstant.getUpdateMsg(result));
    }


}
