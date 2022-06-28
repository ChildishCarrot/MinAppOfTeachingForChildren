package com.gdut.minsappofteachingforchildren.service;

import com.gdut.minsappofteachingforchildren.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 大橙子
* @description 针对表【t_video】的数据库操作Service
* @createDate 2022-05-01 20:06:36
*/
public interface VideoService extends IService<Video> {

    public String saveFile(MultipartFile file);
}
