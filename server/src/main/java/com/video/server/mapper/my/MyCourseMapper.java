package com.video.server.mapper.my;

import com.video.server.dto.SortDto;
import org.apache.ibatis.annotations.Param;

public interface MyCourseMapper {
//    List<CourseDto> list(@Param("pageDto") CoursePageDto pageDto);
    int updateTime(@Param("courseId") String courseId);

    int updateSort(SortDto sortDto);

    int moveSortsBackward(SortDto sortDto);

    int moveSortsForward(SortDto sortDto);
}
