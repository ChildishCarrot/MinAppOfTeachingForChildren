package com.gdut.minsappofteachingforchildren.controller;

import com.gdut.minsappofteachingforchildren.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Api(value = "视频", tags = "视频管理服务")
@Slf4j
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public String upload(HttpServletRequest request) {
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multiRequest.getFile("file");
        return videoService.saveFile(file);
    }

    @ApiOperation("文件下载")
    @GetMapping("download")
    public ResponseEntity<byte[]> download(HttpServletRequest request, @RequestParam("file") String fileName) throws IOException {
        //下载文件路径
//        String path="E:/upload";
        String path = request.getServletContext().getRealPath("/uploadFiles/");
        //构建将要下载的文件对象
        File file = new File(path + File.separator + fileName);
        //设置响应头
        HttpHeaders headers=new HttpHeaders();
        //下载显示的文件名，解决中文名称乱码问题
        String downloadFileName=new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
        //通知浏览器以下载方式(attachment)打开文件
        headers.setContentDispositionFormData("attachment",downloadFileName);
        //定义以二进制流数据(最常见的文件下载)的形式下载返回文件数据
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //使用spring mvc框架的ResponseEntity对象封装返回下载数据
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);
    }


}
