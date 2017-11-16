package com.lewis.configcenter.common.core;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Component
public class ResponseJsonHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    @Resource
    private CustomeHttpMessageConverter httpMessageConverter;

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(ResponseJson.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
        MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON, Collections.singletonMap("charset", "utf-8"));
        httpMessageConverter.write(returnValue, mediaType, outputMessage);
    }
}
