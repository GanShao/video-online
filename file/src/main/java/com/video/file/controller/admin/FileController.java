package com.video.file.controller.admin;

import com.video.server.dto.PageDto;
import com.video.server.dto.ResponseDto;
import com.video.server.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/file")
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);
    public static final String BUSINESS_NAME = "文件";

    @Resource
    private FileService fileService;

    /**
     * 列表查询
     */
    @PostMapping("/query")
    public ResponseDto query(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        fileService.query(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
}
