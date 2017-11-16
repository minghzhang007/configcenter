package com.lewis.configcenter.biz.controller;

import com.lewis.configcenter.biz.model.entity.DepartmentDO;
import com.lewis.configcenter.biz.model.entity.EnvironmentDO;
import com.lewis.configcenter.biz.service.DepartmentService;
import com.lewis.configcenter.biz.service.EnvironmentService;
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
@RequestMapping("/depart")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @GetMapping("/toQuery")
    public String toQuery() {

        return "depart/departList";
    }

    @GetMapping("/query")
    @ResponseJson
    public PageList<DepartmentDO> queryPage(@Json Paginator paginator) {

        return departmentService.pageList(paginator);
    }

    @GetMapping("/edit")
    public String editor(@Json DepartmentDO departmentDO, Model model) {
        model.addAttribute("depart", JsonUtils.toString(departmentDO));
        return "depart/departEdit";
    }

    @GetMapping("/add")
    @ResponseJson
    public ResultMsg add(@Json DepartmentDO departmentDO) {
        boolean result = departmentService.add(departmentDO);
        return new ResultMsg(result, MsgConstant.getAddMsg(result));
    }

    @GetMapping("/update")
    @ResponseJson
    public ResultMsg update(@Json DepartmentDO departmentDO) {
        boolean result = departmentService.update(departmentDO);
        return new ResultMsg(result, MsgConstant.getUpdateMsg(result));
    }

}
