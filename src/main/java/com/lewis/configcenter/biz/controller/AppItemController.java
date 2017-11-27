package com.lewis.configcenter.biz.controller;

import com.lewis.configcenter.biz.dao.local.ApplicationMapper;
import com.lewis.configcenter.biz.dao.local.EnvironmentMapper;
import com.lewis.configcenter.biz.model.constants.PublishStatusEnum;
import com.lewis.configcenter.biz.model.entity.*;
import com.lewis.configcenter.biz.model.queryobject.AppItemQO;
import com.lewis.configcenter.biz.model.vo.AppItemVO;
import com.lewis.configcenter.biz.model.vo.ApplicationVO;
import com.lewis.configcenter.biz.service.AppItemService;
import com.lewis.configcenter.common.component.page.PageList;
import com.lewis.configcenter.common.component.page.Paginator;
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

/**
 * @author zhangminghua
 */
@Controller
@RequestMapping("/appItem")
public class AppItemController {

    @Resource
    private AppItemService appItemService;

    @Resource
    private EnvironmentMapper environmentMapper;

    @Resource
    private ApplicationMapper applicationMapper;

    @GetMapping("/toQuery")
    public String toQuery(@Json ApplicationDO applicationDO, Model model) {
        model.addAttribute("app", applicationDO);
        model.addAttribute("appString", JsonUtils.toString(applicationDO));
        return "appItem/appItemList";
    }

    @GetMapping("/query")
    @ResponseJson
    public PageList<AppItemDO> pageList(@Json AppItemQO appItemQO) {
        PageList<AppItemDO> appItemDOPageList = appItemService.pageList(appItemQO);
        return appItemDOPageList;
    }

    @GetMapping("/edit")
    public String editor(@Json AppItemDO appItemDO, @Json ApplicationDO applicationDO, Model model) {
        List<EnvironmentDO> environments = environmentMapper.list();
        List<ApplicationDO> applicationDOS = applicationMapper.list(null);
        if (StringUtils.isNotBlank(appItemDO.getAppName())) {
            model.addAttribute("appItem", JsonUtils.toString(appItemDO));

            List<RadioModel> departs = environments.stream().map(item -> {
                RadioModel selectModel = new RadioModel();
                selectModel.setKey(item.getEnvName());
                selectModel.setValue(item.getEnvDesc());
                if (Objects.equals(item.getEnvName(), appItemDO.getEnvName())) {
                    selectModel.setSelected(item.getEnvName());
                }
                return selectModel;
            }).collect(Collectors.toList());
            List<RadioModel> apps = applicationDOS.stream().map(item -> {
                RadioModel radioModel = new RadioModel();
                radioModel.setKey(item.getAppName());
                radioModel.setValue(item.getAppDesc());
                if (Objects.equals(item.getAppName(), appItemDO.getEnvName())) {
                    radioModel.setSelected(item.getAppName());
                }
                return radioModel;
            }).collect(Collectors.toList());
            model.addAttribute("apps", JsonUtils.toString(apps));
            model.addAttribute("envs", JsonUtils.toString(departs));
        } else {
            List<RadioModel> envs = environments.stream().map(item -> {
                RadioModel selectModel = new RadioModel();
                selectModel.setKey(item.getEnvName());
                selectModel.setValue(item.getEnvDesc());
                return selectModel;
            }).collect(Collectors.toList());
            List<RadioModel> apps = applicationDOS.stream().map(item -> {
                RadioModel radioModel = new RadioModel();
                radioModel.setKey(item.getAppName());
                radioModel.setValue(item.getAppDesc());
                return radioModel;
            }).collect(Collectors.toList());
            model.addAttribute("apps", JsonUtils.toString(apps));
            model.addAttribute("appItem", JsonUtils.toString(appItemDO));
            model.addAttribute("envs", JsonUtils.toString(envs));
        }
        return "appItem/appItemEdit";
    }

    @GetMapping("/add")
    @ResponseJson
    public ResultMsg add(@Json AppItemVO appItemVO) {
        List<RadioModel> envs = appItemVO.getEnvs();
        for (RadioModel env : envs) {
            if (StringUtils.isNotEmpty(env.getSelected())) {
                appItemVO.setEnvName(env.getKey());
                appItemVO.setEnvDesc(env.getValue());
                break;
            }
        }
        BaseEntityHelper.setDefaultValue(appItemVO);
        appItemVO.setPublishStatus(PublishStatusEnum.PUBLISH_NO.getCode());
        boolean result = appItemService.add(appItemVO);
        return new ResultMsg(result, MsgConstant.getAddMsg(result));
    }

    @GetMapping("/update")
    @ResponseJson
    public ResultMsg update(@Json AppItemVO appItemVO) {
        boolean result = appItemService.update(appItemVO);
        return new ResultMsg(result, MsgConstant.getUpdateMsg(result));
    }

    @GetMapping("/publish")
    @ResponseJson
    public ResultMsg publish(@Json AppItemQO appItemQO) {
        boolean result = appItemService.publish(appItemQO);
        return new ResultMsg(result, MsgConstant.getPublishMsg(result));
    }
}
