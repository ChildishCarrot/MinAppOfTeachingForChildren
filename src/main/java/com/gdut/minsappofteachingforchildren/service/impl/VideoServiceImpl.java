package com.gdut.minsappofteachingforchildren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdut.minsappofteachingforchildren.entity.Video;
import com.gdut.minsappofteachingforchildren.service.VideoService;
import com.gdut.minsappofteachingforchildren.mapper.VideoMapper;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
* @author 大橙子
* @description 针对表【t_video】的数据库操作Service实现
* @createDate 2022-05-01 20:06:36
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService{

    private String getSavePath() {
        // 这里需要注意的是ApplicationHome是属于SpringBoot的类
        // 获取项目下resources/static/img路径
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());

        // 保存目录位置根据项目需求可随意更改
        return applicationHome.getDir().getParentFile()
                .getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\img";
    }

    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空！";
        }
        // 给文件重命名
        String fileName = UUID.randomUUID() + "." + file.getContentType()
                .substring(file.getContentType().lastIndexOf("/") + 1);
        try {
            // 获取保存路径

            String path = getSavePath();
//            String path = ResourceUtils.getURL("classpath:").getPath() + "/static/img";
            File files = new File(path, fileName);
            File parentFile = files.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdir();
            }
            file.transferTo(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName; // 返回重命名后的文件名
    }

}




