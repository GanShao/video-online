package com.video.server.service;

import com.video.server.domain.Test;
import com.video.server.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;
    public List<Test> query(){
        return testMapper.query();
    };
}
