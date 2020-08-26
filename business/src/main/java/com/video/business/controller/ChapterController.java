package com.video.business.controller;


import com.video.server.domain.Chapter;
import com.video.server.service.ChapterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//如果返回的是json数据用RestController（主要针对是接口），如果返回的是界面用Controller
@RestController
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @RequestMapping("/chapter")
    public List<Chapter> chapter(){

        return chapterService.query();
    }
}
