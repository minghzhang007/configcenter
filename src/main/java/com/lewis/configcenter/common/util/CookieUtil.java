package com.lewis.configcenter.common.util;

import com.lewis.configcenter.common.login.LoginConstants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    /**
     * 获取cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String defaultValue) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookieName == null) {
            return defaultValue;
        } else {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookieName, cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return defaultValue;
    }

    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, Integer age) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(age != null ? age : -1);
        //cookie.setDomain(Constants.COOKIE_DOMAIN);
        response.addCookie(cookie);
    }

    public static void delCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        //cookie.setDomain(Constants.COOKIE_DOMAIN);
        response.addCookie(cookie);
    }

    public static String getLoginCookie(HttpServletRequest request) {
        return getCookieValue(request, LoginConstants.LOGIN_COOKIE_NAME, "");
    }

    public static void addLoginCookie(HttpServletResponse response, String cookieValue) {
        addCookie(response, LoginConstants.LOGIN_COOKIE_NAME, cookieValue, null);
    }

    public static void delLoginCookie(HttpServletResponse response) {
        delCookie(response, LoginConstants.LOGIN_COOKIE_NAME);
    }

}
