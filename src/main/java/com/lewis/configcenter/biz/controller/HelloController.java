package com.lewis.configcenter.biz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/11/14.
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/name")
    @ResponseBody
    public String name(String name) {
        return "hello !" + name;
    }
}
