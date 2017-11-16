package com.lewis.configcenter.common.login.service.impl;


import com.lewis.configcenter.common.component.NkvComponent;
import com.lewis.configcenter.common.login.LoginCacheEnum;
import com.lewis.configcenter.common.login.LoginConstants;
import com.lewis.configcenter.common.login.LoginCookie;
import com.lewis.configcenter.common.login.service.LoginService;
import com.lewis.configcenter.common.util.CookieUtil;
import com.lewis.configcenter.common.util.SignatureUtils;
import com.lewis.configcenter.common.util.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private NkvComponent nkvComponent;

    @Override
    public void setLoginCookie(String accountUserName, HttpServletResponse response) {
        String key = UUID.randomUUID() + "-" + System.currentTimeMillis(); // 登录缓存key，也是cookie值
        String sign = SignatureUtils.generateLoginSignature(key + "\n" + accountUserName);
        String base64 = Base64.encodeBase64String(StringUtil.stringToBytes(accountUserName + "\n" + sign, "UTF-8"));
        nkvComponent.put(LoginCacheEnum.LOGIN_COOKIE.make(key), new LoginCookie(base64), LoginConstants.LOGIN_COOKIE_EXPIRE);
        nkvComponent.put(LoginCacheEnum.LOGIN_SINGLE.make(accountUserName), key, LoginConstants.LOGIN_COOKIE_EXPIRE); // 禁止重复登录
        CookieUtil.addLoginCookie(response, key);
    }

    @Override
    public String validateLoginCookie(HttpServletRequest request, HttpServletResponse response) {
        try {
            String key = CookieUtil.getLoginCookie(request);
            LoginCookie loginCookie = nkvComponent.get(LoginCacheEnum.LOGIN_COOKIE.make(key), LoginCookie.class);
            if (loginCookie != null && loginCookie.isValid()) {
                String accountUserName_sign = StringUtil.bytesToString(Base64.decodeBase64(loginCookie.getBase64()), "UTF-8");
                int lastLineIndex = accountUserName_sign.lastIndexOf("\n");
                String accountUserName = accountUserName_sign.substring(0, lastLineIndex);
                String sign = accountUserName_sign.substring(lastLineIndex + 1);

                // 检查是否是重复登录
                String singleKey = nkvComponent.get(LoginCacheEnum.LOGIN_SINGLE.make(accountUserName), String.class);
                if (!StringUtils.equals(key, singleKey)) { // 如果是重复登录，则登出
                    clearLoginCookie(request, response);
                    return null;
                }

                if (StringUtils.equals(sign, SignatureUtils.generateLoginSignature(key + "\n" + accountUserName))) {
                    if (loginCookie.needSave()) {
                        nkvComponent.touch(LoginCacheEnum.LOGIN_COOKIE.make(key), LoginConstants.LOGIN_COOKIE_EXPIRE); // 每次都设置半小时
                    }
                    return accountUserName;
                }
            }
        } catch (Exception e) {
            //
        }
        return null;
    }

    @Override
    public void clearLoginCookie(HttpServletRequest request, HttpServletResponse response) {
        String key = CookieUtil.getLoginCookie(request);
        nkvComponent.remove(LoginCacheEnum.LOGIN_COOKIE.make(key));
        CookieUtil.delLoginCookie(response);
    }
}
