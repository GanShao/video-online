package com.video.file.controller.admin;


import com.oracle.tools.packager.Log;
import com.video.server.dto.ResponseDto;
import com.video.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequestMapping("/admin")
@RestController
public class UploadController {
    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    public static final String BUSINESS_NAME = "文件上传";

    @Value("${file.path}")
    private String FILE_PATH;

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile file) throws IOException {
        LOG.info("上传文件开始：{}", file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        //上传文件保存到本地
        String fileName = file.getOriginalFilename();
        String key = UuidUtil.getShortUuid();
        String fullPath = FILE_PATH + "fileImage/" + key + "-" + fileName;
        File dest = new File(fullPath);
        file.transferTo(dest);
        Log.info(dest.getAbsolutePath());

        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(FILE_DOMAIN + "fileImage/" + key + "-" + fileName);
        return responseDto;
    }
}
