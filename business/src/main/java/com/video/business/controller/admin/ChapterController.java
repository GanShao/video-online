package com.video.business.controller.admin;


import com.video.server.dto.ChapterDto;
import com.video.server.dto.PageDto;
import com.video.server.dto.ResponseDto;
import com.video.server.service.ChapterService;
import com.video.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//如果返回的是json数据用RestController（主要针对是接口），如果返回的是界面用Controller
@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {
    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);
    public static final String BUSINESS_NAME = "大章";

    @Resource
    private ChapterService chapterService;

    @PostMapping("/query")
    public ResponseDto query(@RequestBody PageDto pageDto) {
        //log日志输出，使用占位符{}
        LOG.info("pageDto:{}", pageDto);

        ResponseDto responseDto = new ResponseDto();
        chapterService.query(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody ChapterDto chapterDto) {
        //log日志输出，使用占位符{}
        LOG.info("chapterDto:{}", chapterDto);

        //保存校验
        ValidatorUtil.require(chapterDto.getName(), "名称");
        ValidatorUtil.require(chapterDto.getCourseId(), "课程ID");
        ValidatorUtil.length(chapterDto.getCourseId(), "课程ID", 1, 8);

        ResponseDto responseDto = new ResponseDto();
        chapterService.save(chapterDto);
        responseDto.setContent(chapterDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        //log日志输出，使用占位符{}
        LOG.info("id:{}", id);

        ResponseDto responseDto = new ResponseDto();
        chapterService.delete(id);
        return responseDto;
    }
}
