package com.gdut.minsappofteachingforchildren.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdut.minsappofteachingforchildren.entity.Courses;
import com.gdut.minsappofteachingforchildren.mapper.CoursesMapper;
import com.gdut.minsappofteachingforchildren.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 大橙子
* @description 针对表【t_courses】的数据库操作Service实现
* @createDate 2022-04-03 19:34:56
*/
@Service
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses> implements CoursesService {

    @Autowired
    private CoursesMapper coursesMapper;

    public List<Courses> getAllCoursesIncludedDeleted(){
        return coursesMapper.getAllCoursesIncludedDeleted();
    }

    public List<Courses> getDelectedCourses(){
        return coursesMapper.getDelectedCourses();
    }
}




