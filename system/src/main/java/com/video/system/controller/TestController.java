package com.video.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//如果返回的是json数据用RestController（主要针对是接口），如果返回的是界面用Controller
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "success";
    }
}
