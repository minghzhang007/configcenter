package com.lewis.configcenter.biz.controller;

import com.lewis.configcenter.biz.dao.local.AppMapper;
import com.lewis.configcenter.biz.dao.local.EnvironmentMapper;
import com.lewis.configcenter.biz.model.dto.SwitchConfig;
import com.lewis.configcenter.biz.model.entity.ItemDO;
import com.lewis.configcenter.biz.model.entity.AppDO;
import com.lewis.configcenter.biz.model.entity.BaseEntityHelper;
import com.lewis.configcenter.biz.model.entity.EnvironmentDO;
import com.lewis.configcenter.biz.model.queryobject.ItemQO;
import com.lewis.configcenter.biz.model.vo.ItemVO;
import com.lewis.configcenter.biz.service.ItemService;
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
import java.util.stream.Collectors;

/**
 * @author zhangminghua
 */
@Controller
@RequestMapping("/appItem")
public class AppItemController {

    @Resource
    private ItemService appItemService;

    @Resource
    private EnvironmentMapper environmentMapper;

    @Resource
    private AppMapper applicationMapper;

    @Resource
    private SwitchConfig switchConfig;

    @GetMapping("/toQuery")
    public String toQuery(@Json AppDO applicationDO, Model model) {
        System.out.println(switchConfig);
        model.addAttribute("app", applicationDO);
        model.addAttribute("appString", JsonUtils.toString(applicationDO));
        return "appItem/appItemList";
    }

    @GetMapping("/query")
    @ResponseJson
    public PageList<ItemDO> pageList(@Json ItemQO appItemQO) {
        PageList<ItemDO> appItemDOPageList = appItemService.pageList(appItemQO);
        return appItemDOPageList;
    }

    @GetMapping("/edit")
    public String editor(@Json ItemDO appItemDO, @Json AppDO applicationDO, Model model) {
        List<EnvironmentDO> environments = environmentMapper.list();
        List<AppDO> applicationDOS = applicationMapper.list(null);
        if (StringUtils.isNotBlank(appItemDO.getAppName())) {
            model.addAttribute("appItem", JsonUtils.toString(appItemDO));

            List<RadioModel> departs = environments.stream().map(item -> {
                RadioModel selectModel = new RadioModel();
                selectModel.setKey(item.getEnvName());
                selectModel.setValue(item.getEnvDesc());
                /*if (Objects.equals(item.getEnvName(), appItemDO.getEnvName())) {
                    selectModel.setSelected(item.getEnvName());
                }*/
                return selectModel;
            }).collect(Collectors.toList());
            List<RadioModel> apps = applicationDOS.stream().map(item -> {
                RadioModel radioModel = new RadioModel();
                radioModel.setKey(item.getAppId());
                radioModel.setValue(item.getAppName());
                /*if (Objects.equals(item.getAppName(), appItemDO.getEnvName())) {
                    radioModel.setSelected(item.getAppName());
                }*/
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
                radioModel.setKey(item.getAppId());
                radioModel.setValue(item.getAppName());
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
    public ResultMsg add(@Json ItemDO appItemVO) {
       // List<RadioModel> envs = appItemVO.getEnvs();
        /*for (RadioModel env : envs) {
            if (StringUtils.isNotEmpty(env.getSelected())) {
                appItemVO.setEnvName(env.getKey());
                appItemVO.setEnvDesc(env.getValue());
                break;
            }
        }*/
        BaseEntityHelper.setDefaultValue(appItemVO);
        //appItemVO.setPublishStatus(PublishStatusEnum.PUBLISH_NO.getCode());
        boolean result = appItemService.add(appItemVO);
        return new ResultMsg(result, MsgConstant.getAddMsg(result));
    }

    @GetMapping("/update")
    @ResponseJson
    public ResultMsg update(@Json ItemVO appItemVO) {
        boolean result = appItemService.update(appItemVO);
        return new ResultMsg(result, MsgConstant.getUpdateMsg(result));
    }

    @GetMapping("/publish")
    @ResponseJson
    public ResultMsg publish(@Json ItemQO appItemQO) {
        boolean result = appItemService.publish(appItemQO);
        return new ResultMsg(result, MsgConstant.getPublishMsg(result));
    }
}
