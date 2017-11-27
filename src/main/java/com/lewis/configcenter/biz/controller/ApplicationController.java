package com.lewis.configcenter.biz.controller;

import com.lewis.configcenter.biz.dao.local.DepartmentMapper;
import com.lewis.configcenter.biz.model.entity.ApplicationDO;
import com.lewis.configcenter.biz.model.entity.BaseEntityHelper;
import com.lewis.configcenter.biz.model.entity.DepartmentDO;
import com.lewis.configcenter.biz.model.queryobject.ApplicationQO;
import com.lewis.configcenter.biz.model.vo.ApplicationVO;
import com.lewis.configcenter.biz.service.ApplicationService;
import com.lewis.configcenter.common.component.page.PageList;
import com.lewis.configcenter.common.core.Json;
import com.lewis.configcenter.common.core.ResponseJson;
import com.lewis.configcenter.common.model.MsgConstant;
import com.lewis.configcenter.common.model.RadioModel;
import com.lewis.configcenter.common.model.ResultMsg;
import com.lewis.configcenter.common.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app")
public class ApplicationController {

    @Resource
    private ApplicationService applicationService;

    @Resource
    private DepartmentMapper departmentMapper;

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
    public String editor(@Json ApplicationVO applicationVO, Model model) {
        if (StringUtils.isNotBlank(applicationVO.getAppName())) {
            model.addAttribute("app", JsonUtils.toString(applicationVO));
            List<DepartmentDO> departments = departmentMapper.list();
            List<RadioModel> departs = departments.stream().map(item -> {
                RadioModel selectModel = new RadioModel();
                selectModel.setKey(item.getDepartName());
                selectModel.setValue(item.getDepartDesc());
                if (Objects.equals(item.getDepartName(), applicationVO.getDepartName())) {
                    selectModel.setSelected(item.getDepartName());
                }
                return selectModel;
            }).collect(Collectors.toList());
            model.addAttribute("departs", JsonUtils.toString(departs));
        } else {
            List<DepartmentDO> departments = departmentMapper.list();
            List<RadioModel> departs = departments.stream().map(item -> {
                RadioModel selectModel = new RadioModel();
                selectModel.setKey(item.getDepartName());
                selectModel.setValue(item.getDepartDesc());
                return selectModel;
            }).collect(Collectors.toList());
            model.addAttribute("app", JsonUtils.toString(applicationVO));
            model.addAttribute("departs", JsonUtils.toString(departs));
        }
        return "app/appEdit";
    }

    @GetMapping("/add")
    @ResponseJson
    public ResultMsg add(@Json ApplicationVO applicationDO) {
        List<RadioModel> departs = applicationDO.getDeparts();
        for (RadioModel depart : departs) {
            if (StringUtils.isNotEmpty(depart.getSelected())) {
                applicationDO.setDepartName(depart.getKey());
                applicationDO.setDepartDesc(depart.getValue());
                break;
            }
        }
        BaseEntityHelper.setDefaultValue(applicationDO);
        boolean result = applicationService.add(applicationDO);
        return new ResultMsg(result, MsgConstant.getAddMsg(result));
    }

    @GetMapping("/update")
    @ResponseJson
    public ResultMsg update(@Json ApplicationVO applicationDO) {
        boolean result = applicationService.update(applicationDO);
        return new ResultMsg(result, MsgConstant.getUpdateMsg(result));
    }

}
