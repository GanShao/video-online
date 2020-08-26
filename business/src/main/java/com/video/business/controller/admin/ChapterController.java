package com.video.business.controller.admin;


import com.video.server.dto.ChapterDto;
import com.video.server.service.ChapterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//如果返回的是json数据用RestController（主要针对是接口），如果返回的是界面用Controller
@RestController
@RequestMapping("/admin")
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @RequestMapping("/chapter")
    public List<ChapterDto> chapter(){

        return chapterService.query();
    }
}
