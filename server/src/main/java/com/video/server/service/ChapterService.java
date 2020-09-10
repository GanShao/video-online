package com.video.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.video.server.Util.CopyUtil;
import com.video.server.Util.UuidUtil;
import com.video.server.domain.Chapter;
import com.video.server.domain.ChapterExample;
import com.video.server.dto.ChapterDto;
import com.video.server.dto.PageDto;
import com.video.server.mapper.ChapterMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    public void queryChapterPage(PageDto pageDto){
        //插件分页语句规则：调用startPage方法之后，对执行的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());//页码从1开始
        ChapterExample chapterExample = new ChapterExample();

        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo(chapterList);
        pageDto.setTotal(pageInfo.getTotal());

        //使用自己封装的 BeanUtils.copyProperties()
        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, ChapterDto.class);
        pageDto.setList(chapterDtoList);
    }

    public void save(ChapterDto chapterDto){
        chapterDto.setId(UuidUtil.getShortUuid());
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        chapterMapper.insert(chapter);
    }
}
