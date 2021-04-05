package com.video.file.controller.admin;


import com.oracle.tools.packager.Log;
import com.video.server.dto.FileDto;
import com.video.server.dto.ResponseDto;
import com.video.server.enums.FileUseEnum;
import com.video.server.service.FileService;
import com.video.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    public ResponseDto upload(@RequestParam MultipartFile file, String use) throws IOException {
        LOG.info("上传文件开始");
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        //上传文件保存到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        String fileName = file.getOriginalFilename();//文件名
        String suffix = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();//文件后缀名
        //如果文件夹不存在则创建
        String dir = useEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }

        String key = UuidUtil.getShortUuid();//8位短UUID
        //统一文件名为8位ID+后缀名  File.separator根据不同的操作系统生成不同斜杠
        String path = dir + File.separator + key + "." + suffix;//相对路径
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
        fileDto.setUse(use);
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        fileDto.setPath(FILE_DOMAIN + path);
        responseDto.setContent(fileDto);
        return responseDto;
    }

    @GetMapping("/merge")
    public ResponseDto merge() throws Exception{
        File newFile = new File(FILE_PATH+ "/course/test123.mp4");
        FileOutputStream outputStream = new FileOutputStream(newFile,true);
        FileInputStream fileInputStream = null;//分片文件
        byte[] byt = new byte[10*1024*1024];
        int len;

        try{
            //读取第一个分片
            fileInputStream = new FileInputStream(new File(FILE_PATH+ "/course/test1.mp4"));
            while ((len = fileInputStream.read(byt)) != -1) {
                outputStream.write(byt, 0, len);
            }

            //读取第二个分片
            fileInputStream = new FileInputStream(new File(FILE_PATH+ "/course/test2.mp4"));
            while ((len = fileInputStream.read(byt)) != -1) {
                outputStream.write(byt, 0, len);
            }

        }catch (IOException e) {
            LOG.error("分片合并异常", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                outputStream.close();
                LOG.info("IO流关闭");
            } catch (Exception e) {
                LOG.error("IO流关闭", e);
            }
        }
        LOG.info("合并分片结束");

        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
