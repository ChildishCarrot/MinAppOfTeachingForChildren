package com.gdut.minsappofteachingforchildren.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

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
public class MomentComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 发表的用户的id
     */
    private String userId;

    /**
     * 归属朋友圈的id
     */
    private String momentId;

    /**
     * 父评论的id，如果为顶级评论则置0
     */
    private String fatherId;

    /**
     * 内容
     */
    private String content;

    /**
     * 发表时间
     */
    private Date time;


}
