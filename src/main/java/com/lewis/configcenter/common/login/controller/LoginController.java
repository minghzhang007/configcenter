package com.lewis.configcenter.common.login.controller;


import com.lewis.configcenter.common.login.LoginUserInfo;
import com.lewis.configcenter.common.login.LoginUserInfoHolder;
import com.lewis.configcenter.common.login.NoLogin;
import com.lewis.configcenter.common.login.service.LoginService;
import com.lewis.configcenter.common.login.service.OpenIdService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@NoLogin
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private OpenIdService openIdService;

    @RequestMapping("/login")
    public String login(@RequestParam(value = "redirect", defaultValue = "/") String redirect) throws Exception {
        String redirectUrl = openIdService.getRedirectUrl(redirect);
        System.out.println("redirectUrl: "+redirect);
        return "redirect:" + redirectUrl;
    }


    /**
     * openId登录页面回调
     */
    @RequestMapping("/login/openid/callback")
    public String openIdCallback(@RequestParam("openid.sreg.email") String email,
                                 @RequestParam(value = "redirect", defaultValue = "/") String redirect,
                                 @RequestParam Map<String, String> callbackParams,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        if (!openIdService.validateCallbackUrl(callbackParams)) {
            return "redirect:/login.do";
        }

        LoginUserInfo userInfo = new LoginUserInfo();
        userInfo.setEmail(email);
        userInfo.setFullName(callbackParams.get("openid.sreg.fullname"));
        userInfo.setNickName(callbackParams.get("openid.sreg.nickname"));
        LoginUserInfoHolder.addUser(userInfo);
        request.getSession().setAttribute("currentUser", userInfo);
        request.getSession().setMaxInactiveInterval(1800);
        loginService.setLoginCookie(email, response);
        System.out.println("redirect:"+redirect);
        return "redirect:" + redirect;
    }

    /**
     * 登出
     */
    @RequestMapping("/login/out")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String email = loginService.validateLoginCookie(request, response);
        loginService.clearLoginCookie(request, response);
        String userName = email.substring(0, email.indexOf('@'));
        String privilegeCacheKey = "loginAccountPrivileges_" + userName;
        request.getSession().removeAttribute("currentUser");
        request.getSession().removeAttribute(privilegeCacheKey);
        return "redirect:" + "index";
    }

    @GetMapping({"/index", "login/index", ""})
    public String index() {
        return "index";
    }

    @GetMapping("/login/noPrivilege")
    public String noPrivilege() {
        return "noPrivilege";
    }

}
