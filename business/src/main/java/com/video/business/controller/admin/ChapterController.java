package com.video.business.controller.admin;


import com.video.server.dto.ChapterDto;
import com.video.server.dto.PageDto;
import com.video.server.service.ChapterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//如果返回的是json数据用RestController（主要针对是接口），如果返回的是界面用Controller
@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {
    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);

    @Resource
    private ChapterService chapterService;

    @RequestMapping("/queryChapterPage")
    public PageDto queryChapterPage(@RequestBody  PageDto pageDto) {
        //log日志输出，使用占位符{}
        LOG.info("pageDto:{}",pageDto);

        chapterService.queryChapterPage(pageDto);
        return pageDto;
    }

    @RequestMapping("/save")
    public ChapterDto save(@RequestBody ChapterDto chapterDto) {
        //log日志输出，使用占位符{}
        LOG.info("chapterDto:{}",chapterDto);

        chapterService.save(chapterDto);
        return chapterDto;
    }
}
