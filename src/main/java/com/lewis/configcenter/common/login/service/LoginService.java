package com.lewis.configcenter.common.login.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {

    /**
     * 设置登录Cookie
     *
     * @param accountUserName
     * @param response
     */
    void setLoginCookie(String accountUserName, HttpServletResponse response);

    /**
     * 验证登录cookie，通过则返回登录cookie（账号userName），不通过则返回null
     *
     * @param request
     * @param response
     * @return
     */
    String validateLoginCookie(HttpServletRequest request, HttpServletResponse response);

    /**
     * 清除登录cookie
     *
     * @param request
     * @param response
     */
    void clearLoginCookie(HttpServletRequest request, HttpServletResponse response);
}
