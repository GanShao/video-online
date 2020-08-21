package com.video.system.service;

import com.video.system.domain.Test;
import com.video.system.mapper.TestMapper;
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
