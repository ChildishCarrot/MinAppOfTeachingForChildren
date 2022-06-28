package com.gdut.minsappofteachingforchildren.service;

import com.gdut.minsappofteachingforchildren.entity.Courses;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 大橙子
* @description 针对表【t_courses】的数据库操作Service
* @createDate 2022-04-03 19:34:56
*/
public interface CoursesService extends IService<Courses> {
    List<Courses> getAllCoursesIncludedDeleted();

    List<Courses> getDelectedCourses();
}
