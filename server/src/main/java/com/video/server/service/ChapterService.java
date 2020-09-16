package com.video.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.video.server.util.CopyUtil;
import com.video.server.util.UuidUtil;
import com.video.server.domain.Chapter;
import com.video.server.domain.ChapterExample;
import com.video.server.dto.ChapterDto;
import com.video.server.dto.PageDto;
import com.video.server.mapper.ChapterMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    /**
     * 大章列表查询
     * @param pageDto
     */
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

    /**
     * 大章列表保存方法
     * @param chapterDto
     */
    public void save(ChapterDto chapterDto){
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        if(StringUtils.isEmpty(chapter.getId())){
            this.insert(chapter);
        }else {
            this.update(chapter);
        }
    }

    private void insert(Chapter chapter){
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }

    private void update(Chapter chapter){
        chapterMapper.updateByPrimaryKey(chapter);
    }

    public void delete(String id){
        chapterMapper.deleteByPrimaryKey(id);
    }
}
