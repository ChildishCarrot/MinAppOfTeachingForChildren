package com.gdut.minsappofteachingforchildren.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdut.minsappofteachingforchildren.entity.Courses;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author 大橙子
* @description 针对表【t_courses】的数据库操作Mapper
* @createDate 2022-04-03 19:34:56
* @Entity .entity.Courses
*/
@Repository
@Mapper
public interface CoursesMapper extends BaseMapper<Courses> {
    List<Courses> getAllCoursesIncludedDeleted();

    List<Courses> getDelectedCourses();
}




