package com.gdut.minsappofteachingforchildren.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 * 螺杆菌帅的一
 * @author zhoubin
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private String userId;


    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 用户头像存储路径
     */
    private String userAvatar;

    /**
     * 身份证
     */
    @TableField("IDCard")
    private String idcard;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 是否有权限直播
     */
    private Integer live;

    private String openId;


}
