package com.lewis.configcenter.common.login.interceptor;


import com.lewis.configcenter.common.login.LoginUserInfo;
import com.lewis.configcenter.common.login.NoLogin;
import com.lewis.configcenter.common.login.service.LoginService;
import com.lewis.configcenter.common.util.http.HttpUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

  /*  @Resource
    private LoginService loginService;

    @Resource
    private MoneyUserService moneyUserService;

    @Resource
    private MoneyUserMapper moneyUserMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;

        // 不需要登录
        if (method.getBeanType().getAnnotation(NoLogin.class) != null ||
                method.getMethod().getAnnotation(NoLogin.class) != null) {
            return true;
        }

        String email = loginService.validateLoginCookie(request, response);
        if (email != null) {
            //已登录
            String userName = email.substring(0, email.indexOf('@'));
            String privilegeCacheKey = "loginAccountPrivileges_" + userName;
            Object privileges = request.getSession().getAttribute(privilegeCacheKey);
            if (privileges == null) {
                Set<PrivilegeEnum> privilegeEnums = moneyUserService.queryPrivileges(userName);
                request.setAttribute(privilegeCacheKey, privilegeEnums);
                request.getSession().setAttribute(privilegeCacheKey, privilegeEnums);
            }
            Object currentUser = request.getSession().getAttribute("currentUser");
            if (currentUser == null) {
                PrivilegeQueryObject queryObject = new PrivilegeQueryObject();
                queryObject.setUserName(userName);
                List<MoneyUser> moneyUsers = moneyUserMapper.queryUsers(queryObject);
                if (CollectionUtils.isNotEmpty(moneyUsers)) {
                    LoginUserInfo userInfo = new LoginUserInfo();
                    userInfo.setEmail(email);
                    userInfo.setFullName(moneyUsers.get(0).getNickName());
                    userInfo.setNickName(moneyUsers.get(0).getNickName());
                    request.getSession().setMaxInactiveInterval(1800);
                    request.getSession().setAttribute("currentUser", userInfo);
                }
            }
            return true;
        } else {
            if (HttpUtils.isAjaxRequest(request)) {
                HttpUtils.sendResponse(response, MineTypeConstants.APPLICATION_JSON, "UTF-8", new JsonResponse(ResponseCode.NEED_LOGIN).toString());
            } else {
                String redirectUrl = request.getContextPath() + "/login?redirect=" + URLEncoder.encode(HttpUtils.getRequestUrl(request), "UTF-8");
                response.sendRedirect(redirectUrl);
            }
        }
        return false;

    }
*/

}
