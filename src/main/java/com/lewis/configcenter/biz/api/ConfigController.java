package com.lewis.configcenter.biz.api;

import com.google.common.base.Objects;
import com.lewis.configcenter.biz.model.entity.ReleaseDO;
import com.lewis.configcenter.biz.service.PublishService;
import com.lewis.configcenter.common.util.JsonUtils;
import com.lewis.configcenter.configcore.model.ConfigDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author lewis0077
 */
@RequestMapping("/config")
@RestController
public class ConfigController {

    @Resource
    private PublishService publishService;


    @GetMapping(value = "/{appId}")
    public ConfigDTO queryConfig(@PathVariable String appId,
                                 @RequestParam(value = "releaseKey", defaultValue = "-1") String clientSideReleaseKey,
                                 @RequestParam(value = "ip", required = false) String clientIp,
                                 HttpServletRequest request, HttpServletResponse response) {


        ReleaseDO releaseDO = publishService.queryLastRelease(appId);
        if (releaseDO != null) {
            if (Objects.equal(clientSideReleaseKey, releaseDO.getReleaseKey())) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                return null;
            } else {
                ConfigDTO configDTO = new ConfigDTO();
                if (releaseDO != null) {
                    configDTO.setReleaseKey(releaseDO.getReleaseKey());
                    Map map = JsonUtils.toBean(releaseDO.getConfigurations(), Map.class);
                    configDTO.setConfigurations(map);
                }
                configDTO.setAppId(appId);
                return configDTO;
            }
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }


}
