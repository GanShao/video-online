package com.video.file.controller.admin;


import com.oracle.tools.packager.Log;
import com.video.server.dto.FileDto;
import com.video.server.dto.ResponseDto;
import com.video.server.service.FileService;
import com.video.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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

    @Resource
    private FileService fileService;

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile file) throws IOException {
        LOG.info("上传文件开始：{}", file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        //上传文件保存到本地
        String fileName = file.getOriginalFilename();//文件名
        String suffix = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();//文件后缀名
        String key = UuidUtil.getShortUuid();//8位短UUID
        //统一文件名为8位ID+后缀名
        String path = "fileImage/" + key + "-" + suffix;//相对路径
        String fullPath = FILE_PATH + path;//全路径
        File dest = new File(fullPath);
        file.transferTo(dest);
        Log.info(dest.getAbsolutePath());

        LOG.info("保存文件记录开始");
        FileDto fileDto = new FileDto();
        fileDto.setName(fileName);
        fileDto.setPath(path);
        fileDto.setSize(Math.toIntExact(file.getSize()));
        fileDto.setSuffix(suffix);
        fileDto.setUse("");
        fileService.save(fileDto);


        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(FILE_DOMAIN + path);
        return responseDto;
    }
}
