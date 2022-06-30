package com.gdut.minsappofteachingforchildren.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdut.minsappofteachingforchildren.entity.Moment;
import com.gdut.minsappofteachingforchildren.entity.MomentLikes;
import com.gdut.minsappofteachingforchildren.service.MomentLikesService;
import com.gdut.minsappofteachingforchildren.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Configuration
@EnableScheduling
public class StaticScheduleTask {
    @Autowired
    private MomentService momentService;
    @Autowired
    private RedisTemplate redisTemplate;


    @Scheduled(cron = "0/59 * * * * ?") //定时任务，每隔59秒执行一次
    private void configureTasks(){
        //将redis中的点赞数量同步到mysql
        Set<String> keys = redisTemplate.keys("momentLikeNum:*");
        if(keys!=null){
            for(String key : keys){
                String id = key.substring(14);
                Moment moment = momentService.getById(id);
                if(moment!=null){
                    moment.setLikeNum((int)redisTemplate.opsForValue().get(key));
                    momentService.updateById(moment);
                }

            }
        }

    }
}
