package com.lewis.configcenter.biz.controller;

import com.lewis.configcenter.biz.dao.local.AppMapper;
import com.lewis.configcenter.biz.dao.local.EnvironmentMapper;
import com.lewis.configcenter.biz.model.dto.ReleaseDTO;
import com.lewis.configcenter.biz.model.dto.SwitchConfig;
import com.lewis.configcenter.biz.model.entity.AppDO;
import com.lewis.configcenter.biz.model.entity.BaseEntityHelper;
import com.lewis.configcenter.biz.model.entity.ItemDO;
import com.lewis.configcenter.biz.model.queryobject.ItemQO;
import com.lewis.configcenter.biz.model.vo.ItemDTO;
import com.lewis.configcenter.biz.model.vo.PublishVO;
import com.lewis.configcenter.biz.service.ItemService;
import com.lewis.configcenter.biz.service.PublishService;
import com.lewis.configcenter.common.component.page.PageList;
import com.lewis.configcenter.common.core.Json;
import com.lewis.configcenter.common.core.ResponseJson;
import com.lewis.configcenter.common.model.MsgConstant;
import com.lewis.configcenter.common.model.ResultMsg;
import com.lewis.configcenter.common.util.JsonUtils;
import com.lewis.configcenter.common.util.TimeTokenUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangminghua
 */
@Controller
@RequestMapping("/appItem")
public class AppItemController {

    @Resource
    private ItemService appItemService;

    @Resource
    private PublishService publishService;

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
        return "appItem/itemList";
    }

    @GetMapping("/query")
    @ResponseJson
    public PageList<ItemDTO> pageList(@Json ItemQO appItemQO) {
        PageList<ItemDTO> appItemDOPageList = appItemService.pageList(appItemQO);
        return appItemDOPageList;
    }

    @GetMapping("/edit")
    public String editor(@Json ItemDTO appItemDO, @Json AppDO applicationDO, Model model) {
        if (appItemDO.getItem() != null) {
            model.addAttribute("appItem", JsonUtils.toString(appItemDO.getItem()));
        } else {
            model.addAttribute("appItem", JsonUtils.toString(new ItemDO()));
        }
        return "appItem/itemEdit";
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
    public ResultMsg update(@Json ItemDO appItemVO) {
        boolean result = appItemService.update(appItemVO);
        return new ResultMsg(result, MsgConstant.getUpdateMsg(result));
    }

    @GetMapping("/delete")
    @ResponseJson
    public ResultMsg delete(@Json ItemDO itemDO) {
        boolean result = appItemService.delete(itemDO);
        return new ResultMsg(result, MsgConstant.getDeleteMsg(result));
    }

    @GetMapping("/toPublish")
    public String toPublish() {

        return "appItem/itemPublish";
    }

    @GetMapping("/changes")
    @ResponseJson
    public ReleaseDTO changes(@Json AppDO appDO) {
        ReleaseDTO dto = new ReleaseDTO();
        List<ItemDTO> changes = appItemService.changes(appDO);
        dto.setItemDtos(changes);
        dto.setReleaseName("Release-" + TimeTokenUtils.createTimeToken());
        return dto;
    }

    @GetMapping("/publish")
    @ResponseJson
    public ResultMsg publish(@Json PublishVO publishVO) {
        boolean result = publishService.publish(publishVO);
        return new ResultMsg(result, MsgConstant.getPublishMsg(result));
    }

}
