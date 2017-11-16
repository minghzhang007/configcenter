package com.lewis.configcenter.common.config;



import com.lewis.configcenter.common.core.JsonArgumentsResolver;
import com.lewis.configcenter.common.core.ResponseJsonHandlerMethodReturnValueHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.List;

@Component
public class WebConfigure extends WebMvcConfigurerAdapter {

  /*  @Resource
    private LoginInterceptor loginInterceptor;

    @Resource
    private PrivilegeInterceptor privilegeInterceptor;*/

    @Resource
    private ResponseJsonHandlerMethodReturnValueHandler jsonHandlerMethodReturnValueHandler;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new JsonArgumentsResolver());
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
        returnValueHandlers.add(jsonHandlerMethodReturnValueHandler);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        /*registry.addInterceptor(loginInterceptor);
        registry.addInterceptor(privilegeInterceptor);*/
    }

}