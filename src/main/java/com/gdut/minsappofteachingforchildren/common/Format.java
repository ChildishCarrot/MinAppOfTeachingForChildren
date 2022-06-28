package com.gdut.minsappofteachingforchildren.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum Format {
    //支持的视频格式
    _MP4("mp4", "video"),
    _MOV("mov", "video"),
    _M4V("m4v", "video"),
    _3GP("3gp", "video"),
    _AVI("avi", "video"),
    _M3U8("m3u8", "video"),
    _WEBM("webm", "video"),
    //支持的图片格式
    _JPG("jpg", "img"),
    _PNG("png", "img"),
    _SVG("svg", "img"),
    _WEBP("webp", "img"),
    _GIF("gif", "img");

    Format(String value, String format) {
        this.value = value;
        this.format = format;
    }
    //标记数据库存的值是value
    private final String value;
    @EnumValue
    private final String format;
}