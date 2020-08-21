package com.video.system.controller;

import com.video.system.domain.Test;
import com.video.system.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//如果返回的是json数据用RestController（主要针对是接口），如果返回的是界面用Controller
@RestController
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping("/test")
    public List<Test> test(){

        return testService.query();
    }
}
