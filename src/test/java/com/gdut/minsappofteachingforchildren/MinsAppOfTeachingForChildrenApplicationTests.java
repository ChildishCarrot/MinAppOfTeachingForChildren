package com.gdut.minsappofteachingforchildren;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdut.minsappofteachingforchildren.entity.Courses;
import com.gdut.minsappofteachingforchildren.service.CoursesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

@SpringBootTest
class MinsAppOfTeachingForChildrenApplicationTests {

    @Autowired
    private CoursesService coursesService;

    @Test
    public void test1(){
        QueryWrapper<Courses> querywrapper = new QueryWrapper<>();
        querywrapper.ge("minage",3)
                    .le("maxage",18);
        //条件查询
        List<Courses> result = coursesService.list(querywrapper);
        System.out.println(result);
    }

    @Test
    public void test2(){
        Courses course = new Courses();
        course.setText("....");
        course.setName("《字母歌》");
        course.setType("音乐");
        course.setMinage(3);
        course.setMaxage(6);
        //新增
        boolean result = coursesService.save(course);
        System.out.println(result);
    }

    @Test
    public void test3(){
        boolean result = coursesService.removeById("1515637005508177924");
        System.out.println(result);
    }
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Test
    void test4(){

    }
}
