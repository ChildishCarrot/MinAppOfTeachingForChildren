package com.gdut.minsappofteachingforchildren.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdut.minsappofteachingforchildren.entity.Courses;
import com.gdut.minsappofteachingforchildren.vo.Result;
import com.gdut.minsappofteachingforchildren.service.CoursesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Api(value = "课程", tags = "课程管理服务")
@Slf4j
@RestController
@RequestMapping("/courses")
public class CoursesController {

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private RedisTemplate redisTemplate;

//    @Autowired
//    private JedisPool jedisPool;

    /**
     * 利用mybatisplus进行多列搜索，广泛搜索用户输入的关键词
     * @param search    传入搜索的关键词
     * @return
     */
    @ApiOperation("搜索功能")
    @ApiImplicitParam(name = "search",value = "搜索关键词",paramType = "query",dataType = "String",required = true)
    @GetMapping("/select/{search}")
    public Result<List<Courses>> search(@PathVariable("search") String search){
        QueryWrapper<Courses> querywrapper = new QueryWrapper<>();
        querywrapper.like("author",search)
                    .or()
                    .like("name",search)
                    .or()
                    .like("text",search)
                    .or()
                    .like("type",search);
        List<Courses> list = coursesService.list(querywrapper);
        return new Result<List<Courses>>("200","查找成功",list);
    }

    /**
     * 三种情况
     * 1、判断Redis中有没有对应课程的点赞记录，没有就新增一
     * 2、正常加一
     * 3、当点赞数等于Long.MAX_VALUE时，直接返回
     * @param id 课程编号cid
     */
    @ApiOperation("课程点赞功能")
    @ApiImplicitParam(name = "cid",value = "课程唯一ID",paramType = "query",dataType = "String",required = true)
    @GetMapping("/like/{id}")
    public synchronized void LikeCourse(@PathVariable("id") String id){
        String prefix = "cid-";
        if (redisTemplate.opsForHash().get("courses_like_number",prefix+id) == null){
            redisTemplate.opsForHash().put("courses_like_number",prefix+id,"1");
            return;
        }
        if ((Long)redisTemplate.opsForHash().get("courses_like_number",prefix+id) == Long.MAX_VALUE){
            return;
        }
        redisTemplate.opsForHash().increment("courses_like_number",prefix+id,1);
    }

    /**
    @GetMapping("/like/{id}")
    @Transactional(rollbackFor = Exception.class)
    public synchronized void LikeCourse(@PathVariable("id") String id) throws Exception {
        String prefix = "cid-";
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String likeNum = jedis.get(prefix + id);
            if(likeNum == null){
                jedis.set(prefix+id,"1");
                return;
            }
            if(Long.parseLong(likeNum) == Long.MAX_VALUE){
                return;
            }
            jedis.incr(prefix+id);
        }catch (Exception e){

        }
    }
    */

    /**
     * 三种情况
     * 1、判断Redis中有没有对应课程的点赞记录，没有就不管
     * 2、正常减一
     * 3、当点赞数等于1时，直接删除对应记录
     * @param id 课程编号cid
     */
    @ApiOperation("课程取消点赞功能")
    @ApiImplicitParam(name = "cid",value = "课程唯一ID",paramType = "query",dataType = "String",required = true)
    @GetMapping("/unlike/{id}")
    public synchronized void unLikeCourse(@PathVariable("id") String id){
        String prefix = "cid-";
        if (redisTemplate.opsForHash().get("courses_like_number",prefix+id) == null){
            return;
        }
        if ((Integer) redisTemplate.opsForHash().get("courses_like_number",prefix+id) == 1){
            redisTemplate.opsForHash().delete("courses_like_number",prefix+id);
            return;
        }
        redisTemplate.opsForHash().increment("courses_like_number",prefix+id,-1);
    }

    /**
    @GetMapping("/unlike/{id}")
    @Transactional(rollbackFor = Exception.class)
    public synchronized void unLikeCourse(@PathVariable("id") String id) throws Exception {
        String prefix = "cid-";
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String likeNum = jedis.get(prefix + id);
            if(likeNum == null){
                return;
            }
            if(Long.parseLong(likeNum) == 0){
                jedis.del(prefix+id);
                return;
            }
            jedis.decr(prefix+id);
        }catch (Exception e){

        }
    }
    */

    @ApiOperation("查找所有的课程")
    @GetMapping("/findAll")
    public Result<List<Courses>> findAll(){
        List<Courses> result = coursesService.list(null);
        if(result.size() != 0){
            return new Result<List<Courses>>("200","查询成功",result);
        }else{
            return new Result<List<Courses>>("400","没有对应数据");
        }
    }

    @ApiOperation("根据年龄范围获取对应的课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "minage", value = "课程最小适宜年龄", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "maxage", value = "课程最大适宜年龄", paramType = "query", dataType = "int", required = true)
    })
    @GetMapping("/selectByAge/{minage}/{maxage}")
    public Result<List<Courses>> selectByAge(@PathVariable("minage") int minage, @PathVariable("maxage") int maxage){
        if(minage < 0 || maxage < 0 || minage >= maxage){
            return new Result<List<Courses>>("500","参数错误");
        }else{
            //mybatisplus条件构造器
            QueryWrapper<Courses> querywrapper = new QueryWrapper<>();
            // ge: =>; le: <=
            querywrapper.ge("minage",minage)
                        .le("maxage",maxage);
            List<Courses> result = coursesService.list(querywrapper);

            if(result.size() != 0){
                return new Result<List<Courses>>("200","查询成功",result);
            }else{
                return new Result<List<Courses>>("400","没有对应数据");
            }
        }
    }

    @ApiOperation("根据作者获取对应的课程")
    @ApiImplicitParam(name = "Author", value = "课程作者", paramType = "query", dataType = "String", required = true)
    @GetMapping("/selectByAuthor/{Author}")
    public Result<List<Courses>> selectByAuthor(@PathVariable("Author") String Author){

        QueryWrapper<Courses> querywrapper = new QueryWrapper<>();
        querywrapper.like("author",Author);                     //模糊查询，相当于 author = %Author%
        List<Courses> result = coursesService.list(querywrapper);

        if(result.size() != 0){
            return new Result<List<Courses>>("200","查询成功",result);
        }else{
            return new Result<List<Courses>>("400","没有对应数据");
        }
    }

    @ApiOperation("根据类型获取对应的课程")
    @ApiImplicitParam(name = "type", value = "课程类型", paramType = "query", dataType = "String", required = true)
    @GetMapping("/selectByType/{type}")
    public Result<List<Courses>> selectByType(@PathVariable("type") String type){

        QueryWrapper<Courses> querywrapper = new QueryWrapper<>();
        querywrapper.like("type",type);                         //模糊查询，相当于 type = %type%
        List<Courses> result = coursesService.list(querywrapper);

        if(result.size() != 0){
            return new Result<List<Courses>>("200","查询成功",result);
        }else{
            return new Result<List<Courses>>("400","没有对应数据");
        }
    }

    @ApiOperation("根据课程名称获取对应的课程")
    @ApiImplicitParam(name = "name", value = "课程名称", paramType = "query", dataType = "String", required = true)
    @GetMapping("/selectByName/{name}")
    public Result<List<Courses>> selectByName(@PathVariable("name") String name){
        QueryWrapper<Courses> querywrapper = new QueryWrapper<>();
        querywrapper.like("name",name);                         //模糊查询，相当于 name = %name%
        List<Courses> result = coursesService.list(querywrapper);

        if(result.size() != 0){
            return new Result<List<Courses>>("200","查询成功",result);
        }else{
            return new Result<List<Courses>>("400","没有对应数据");
        }
    }

    @ApiOperation("根据课程号获取对应的课程和对应的点赞数")
    @ApiImplicitParam(name = "id", value = "课程唯一性ID", paramType = "query", dataType = "String", required = true)
    @GetMapping("/selectById/{id}")
    public Result<Map<String, Object>> selectById(@PathVariable("id") String id){
        Long cid = Long.parseLong(id);
        Courses result = coursesService.getById(cid);
        if (result != null){
            //
            String likeNumber = (String)redisTemplate.opsForHash().get("courses_like_number","cid-"+cid);
            Map<String, Object> resultMap = new HashMap<>();
            Map<String, Object> likeNumberMap = new HashMap<>();
            likeNumberMap.put("likeNumber",likeNumber == null?0:Integer.parseInt(likeNumber));
            likeNumberMap.put("cid",cid);
            //
            resultMap.put("course",result);
            resultMap.put("course_like",likeNumberMap);
            return new Result<Map<String, Object>>("200","查询成功",resultMap);
        }else{
            return new Result<Map<String, Object>>("400","没有对应数据");
        }
    }

    @ApiOperation("根据课程号删除对应课程")
    @ApiImplicitParam(name = "id", value = "课程唯一性ID", paramType = "query", dataType = "String", required = true)
    @GetMapping("/removeById/{id}")
    public Result removeById(@PathVariable("id") String id){
        Long cid = Long.parseLong(id);
        boolean result1 = coursesService.removeById(cid);
        redisTemplate.opsForHash().delete("courses_like_number", "cid-" + cid);
        if(result1 == true){
            return new Result("200","删除成功");
        }else{
            return new Result("400","删除失败");
        }
    }

    @ApiOperation("获取所有课程包括已经被删除的（仅限管理员调用接口）")
    @GetMapping("/getAllCoursesIncludedDeleted")
    public Result<List<Courses>> getAllCoursesIncludedDeleted(){
        List<Courses> result = coursesService.getAllCoursesIncludedDeleted();
        if(result != null){
            return new Result<List<Courses>>("200","查询成功",result);
        }else{
            return new Result<List<Courses>>("400","没有对应数据");
        }
    }

    @ApiOperation("获取被删除了的课程")
    @GetMapping("/getDelectedCourses")
    public Result<List<Courses>> getDelectedCourses(){
        List<Courses> result = coursesService.getDelectedCourses();
        if(result != null){
            return new Result<List<Courses>>("200","查询成功",result);
        }else{
            return new Result<List<Courses>>("400","没有对应数据");
        }
    }

    @ApiOperation("新增课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "课程号，无需输入，后端自动生成", paramType = "body", dataType = "long", required = false),
            @ApiImplicitParam(name = "uid", value = "上传者的用户ID", paramType = "body", dataType = "int", required = true),
            @ApiImplicitParam(name = "type", value = "课程类型", paramType = "body", dataType = "String", required = true),
            @ApiImplicitParam(name = "text", value = "课程内容,不超过2w字", paramType = "body", dataType = "String", required = true),
            @ApiImplicitParam(name = "name", value = "课程名称（由于诗歌可能会有重名，暂不设置名称唯一性）", paramType = "body", dataType = "String", required = true),
            @ApiImplicitParam(name = "author", value = "课程作者", paramType = "body", dataType = "String", required = true),
            @ApiImplicitParam(name = "maxage", value = "课程适合人群的最大年龄", paramType = "body", dataType = "int", required = true),
            @ApiImplicitParam(name = "mixage", value = "课程适合人群的最小年龄", paramType = "body", dataType = "int", required = true),
    })
    @PostMapping("/create")
    public Result create(@RequestBody @Valid Courses courses){
        boolean result = coursesService.save(courses);
        if(result){
            return new Result("200", "新增成功");
        }else{
            return new Result("400", "新增失败");
        }
    }

    @ApiOperation("修改课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "课程号，无需输入，后端自动生成", paramType = "body", dataType = "long", required = false),
            @ApiImplicitParam(name = "uid", value = "上传者的用户ID", paramType = "body", dataType = "int", required = true),
            @ApiImplicitParam(name = "type", value = "课程类型", paramType = "body", dataType = "String", required = true),
            @ApiImplicitParam(name = "text", value = "课程内容,不超过2w字", paramType = "body", dataType = "String", required = true),
            @ApiImplicitParam(name = "name", value = "课程名称（由于诗歌可能会有重名，暂不设置名称唯一性）", paramType = "body", dataType = "String", required = true),
            @ApiImplicitParam(name = "author", value = "课程作者", paramType = "body", dataType = "String", required = true),
            @ApiImplicitParam(name = "maxage", value = "课程适合人群的最大年龄", paramType = "body", dataType = "int", required = true),
            @ApiImplicitParam(name = "mixage", value = "课程适合人群的最小年龄", paramType = "body", dataType = "int", required = true),
            @ApiImplicitParam(name = "version", value = "课程版本，为分布式锁，修改时必须传入，如果失败时可能是版本问题需要再次修改", paramType = "body", dataType = "int", required = true)
    })
    @PostMapping("/update")
    public Result update(@RequestBody @Valid Courses courses){
        boolean result = coursesService.updateById(courses);
        if(result){
            boolean result2 = coursesService.updateById(courses);
            if (result2){
                return new Result("200", "更新成功");
            }else {
                return new Result("500", "更新失败");
            }
        }
        return new Result("500", "更新失败");
    }

    @ApiOperation("获取搜索热词（在Redis中）")
    @GetMapping("/getHotSearch")
    public Result<Set> getHotSearch(){
        Set courses = redisTemplate.opsForZSet().reverseRange("course", 0, 9);
        return new Result<Set>("200","查找成功",courses);
    }

}
