package com.gdut.minsappofteachingforchildren.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

//公共返回对象枚举
@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    //通用
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),
    //token验证模块
    TOKEN_ERROR(401,"token信息有误或没有权限!"),


    //朋友圈模块5005xxx
    MOMENT_PUBLISH_ERROR(500500,"朋友圈发布失败"),
    MOMENT_DELETE_ERROR(500501,"朋友圈删除失败"),
    MOMENT_COMMENT_PUBLISH_ERROR(500502,"朋友圈评论发布失败"),
    MOMENT_LIKE_ERROR(500503,"朋友圈点赞失败"),
    SPAN_ERROR(500504,"代表时间间隔的字符串传错了"),

    //用户5003xx
    USER_UPDATE_ERROR(500300,"用户信息修改失败"),
    ADD_FRIEND_ERROR(500301,"添加好友失败"),
    DELETE_FRIEND_ERROR(500302,"删除好友失败"),
    USER_GETINFO_ERROR(500303,"获取用户信息失败"),
    ;
    private final Integer code;
    private final String message;
}
