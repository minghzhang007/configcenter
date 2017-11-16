package com.lewis.configcenter.common.login.service;

import java.util.Map;

/**
 * openId登录
 */
public interface OpenIdService {

    /**
     * 获取跳转地址
     */
    String getRedirectUrl(String redirect) throws Exception;

    /**
     * 验证数据
     */
    boolean validateCallbackUrl(Map<String, String> callbackParams);
}
