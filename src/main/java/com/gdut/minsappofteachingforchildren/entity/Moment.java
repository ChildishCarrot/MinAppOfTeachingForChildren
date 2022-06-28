package com.gdut.minsappofteachingforchildren.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 * @author zhoubin
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = false)
public class Moment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 该朋友圈发表的用户的id
     */
    private String userId;

    /**
     * 内容
     */
    private String content;

    /**
     * 发表时间
     */
    private Date time;

    /**
     * 点赞数量
     */
    private int likeNum;


}
