package com.gdut.minsappofteachingforchildren.controller;

import cn.hutool.core.io.file.FileReader;
import com.gdut.minsappofteachingforchildren.vo.Result;
import com.gdut.minsappofteachingforchildren.common.Format;
import com.gdut.minsappofteachingforchildren.entity.myFile;
import com.gdut.minsappofteachingforchildren.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Api(value = "视频", tags = "视频管理服务")
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${dns}")
    private String dns;

    @ApiOperation("图片上传")
    @PostMapping("/uploadfiles")
    public Result uploadFiles(@RequestPart("file") MultipartFile[] file, HttpServletRequest req) throws IOException {
        System.out.println(file.length);
        return null;
    }

    @ApiOperation("图片上传")
    @PostMapping("/upload")
    public Result<String> upload(@RequestPart("file") MultipartFile file, HttpServletRequest req) throws IOException {
        //存储路径
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String path = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath()
                        +File.separator+"src"
                        +File.separator+"main"
                        +File.separator+"resources"
                        +File.separator+"static"
                        +File.separator;

        if(!file.isEmpty()){
            //用UUID重构文件名
            String filename = UUID.randomUUID()+"-"+file.getOriginalFilename();

            myFile myFile = new myFile();
            myFile.setLocalAddress(filename);
            //判断文件格式
            String format = filename.substring(filename.lastIndexOf(".")+1,filename.length());
            if(format.equals(Format._MP4.getValue())){
                file.transferTo(new File(path+Format._MP4.getFormat()+File.separator+filename));
                myFile.setSuffix(Format._MP4.getFormat());
                if (fileService.save(myFile)) {
                    return new Result("200","上传成功",dns+Format._MP4.getFormat()+File.separator+filename);
                }
            }
            if(format.equals(Format._MOV.getValue())){
                file.transferTo(new File(path+Format._MOV.getFormat()+File.separator+filename));
                myFile.setSuffix(Format._MOV.getFormat());
                if (fileService.save(myFile)) {
                    return new Result("200","上传成功",dns+Format._MOV.getFormat()+File.separator+filename);
                }
            }
            if(format.equals(Format._M4V.getValue())){
                file.transferTo(new File(path+Format._M4V.getFormat()+File.separator+filename));
                myFile.setSuffix(Format._M4V.getFormat());
                if (fileService.save(myFile)) {
                    return new Result("200","上传成功",dns+Format._M4V.getFormat()+File.separator+filename);
                }
            }
            if(format.equals(Format._3GP.getValue())){
                file.transferTo(new File(path+Format._3GP.getFormat()+File.separator+filename));
                myFile.setSuffix(Format._3GP.getFormat());
                if (fileService.save(myFile)) {
                    return new Result("200","上传成功",dns+Format._3GP.getFormat()+File.separator+filename);
                }
            }
            if(format.equals(Format._AVI.getValue())){
                file.transferTo(new File(path+Format._AVI.getFormat()+File.separator+filename));
                myFile.setSuffix(Format._AVI.getFormat());
                if (fileService.save(myFile)) {
                    return new Result("200","上传成功",dns+Format._AVI.getFormat()+File.separator+filename);
                }
            }
            if(format.equals(Format._M3U8.getValue())){
                file.transferTo(new File(path+Format._M3U8.getFormat()+File.separator+filename));
                myFile.setSuffix(Format._M3U8.getFormat());
                if (fileService.save(myFile)) {
                    return new Result("200","上传成功",dns+Format._M3U8.getFormat()+File.separator+filename);
                }
            }
            if(format.equals(Format._WEBM.getValue())){
                file.transferTo(new File(path+Format._WEBM.getFormat()+File.separator+filename));
                myFile.setSuffix(Format._WEBM.getFormat());
                if (fileService.save(myFile)) {
                    return new Result("200","上传成功",dns+Format._WEBM.getFormat()+File.separator+filename);
                }
            }
            if(format.equals(Format._JPG.getValue())){
                file.transferTo(new File(path+Format._JPG.getFormat()+File.separator+filename));
                myFile.setSuffix(Format._JPG.getFormat());
                if (fileService.save(myFile)) {
                    return new Result("200","上传成功",dns+Format._JPG.getFormat()+File.separator+filename);
                }
            }
            if(format.equals(Format._PNG.getValue())){
                file.transferTo(new File(path+Format._PNG.getFormat()+File.separator+filename));
                myFile.setSuffix(Format._PNG.getFormat());
                if (fileService.save(myFile)) {
                    return new Result("200","上传成功",dns+Format._PNG.getFormat()+File.separator+filename);
                }
            }
            if(format.equals(Format._SVG.getValue())){
                file.transferTo(new File(path+Format._SVG.getFormat()+File.separator+filename));
                myFile.setSuffix(Format._SVG.getFormat());
                if (fileService.save(myFile)) {
                    return new Result("200","上传成功",dns+Format._SVG.getFormat()+File.separator+filename);
                }
            }
            if(format.equals(Format._WEBP.getValue())){
                file.transferTo(new File(path+Format._WEBP.getFormat()+File.separator+filename));
                myFile.setSuffix(Format._WEBP.getFormat());
                if (fileService.save(myFile)) {
                    return new Result("200","上传成功",dns+Format._WEBP.getFormat()+File.separator+filename);
                }
            }
            if(format.equals(Format._GIF.getValue())){
                file.transferTo(new File(path+Format._GIF.getFormat()+File.separator+filename));
                myFile.setSuffix(Format._GIF.getFormat());
                if (fileService.save(myFile)) {
                    return new Result("200","上传成功",dns+Format._GIF.getFormat()+File.separator+filename);
                }
            }
        }
        return new Result("500","上传失败");
    }

    /**
     * 传入文件对应的ID删除对应的文件，若static文件中没有或者数据库中没有对应记录都会报错
     * @param id
     * @return Result包装类
     */
    @ApiOperation("删除文件")
    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable("id") String id){
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String path = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath()
                +File.separator+"src"
                +File.separator+"main"
                +File.separator+"resources"
                +File.separator+"static"
                +File.separator;

        Long vid = Long.parseLong(id);
        myFile myFile = fileService.getById(vid);
        if (myFile != null) {
            File file = new File(path + myFile.getSuffix() + File.separator + myFile.getLocalAddress());
            if (file.exists()) {
                if (file.delete() && fileService.removeById(vid)) {
                    return new Result("200", "删除本地文件和记录成功");
                }
            } else {
                if (fileService.removeById(vid)) {
                    return new Result("200", "本地没有该文件，删除该记录");
                }
            }
        }
        return new Result("500","删除失败");
    }

    /**
     *
     * @param id
     * @return
     */
    @ApiOperation("获取文件")
    @GetMapping("/download/{id}")
    public Result download(@PathVariable("id") String id){
        Long vid = Long.parseLong(id);
        myFile myFile = fileService.getById(vid);
        if (myFile != null){
            return new Result("200","获取成功",dns + myFile.getSuffix() + File.separator+myFile.getLocalAddress());
        }else{
            return new Result("500","获取失败");
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @ApiOperation("获取文本文件")
    @GetMapping("/getText/{id}")
    public Result getText(@PathVariable("id") String id) {
        //存储路径
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String path = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath()
                        +File.separator+"src"
                        +File.separator+"main"
                        +File.separator+"resources"
                        +File.separator+"static"
                        +File.separator+"txt"
                        +File.separator;

        Long vid = Long.parseLong(id);
        myFile myFile = fileService.getById(vid);

        FileReader fileReader = new FileReader(path+myFile.getSuffix()+File.separator+myFile.getLocalAddress());
        String result = fileReader.readString();
        return new Result("200","获取成功",result);
    }
/*
//    基于事务机制实现删除功能：当项目中没有该文件或者数据库中没有该记录是，删除动作回滚。但目前业务实现中没有错误产生，文件不存在会返回null或者false，没有产生异常，所以没有必要强行捕捉异常
//    来使用事务的回滚
    @ApiOperation("删除文件")
    @GetMapping("/delete/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result delete(@PathVariable("id") String id){

        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String path = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath()
                +File.separator+"src"
                +File.separator+"main"
                +File.separator+"resources"
                +File.separator+"static"
                +File.separator;

        Long vid = Long.parseLong(id);
        try{
            myFile myFile = fileService.getById(vid);
            File file = new File(path + myFile.getSuffix() + File.separator + myFile.getLocalAddress());
            if(!file.exists() || myFile == null){
                throw new Exception();
            }else{
                file.delete();
                fileService.removeById(vid);
            }
        }catch (Exception e){
            return new Result("500","删除失败");
        }
        return new Result("200","删除成功");
    }
*/
}

