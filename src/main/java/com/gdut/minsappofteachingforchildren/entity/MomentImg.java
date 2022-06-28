package com.gdut.minsappofteachingforchildren.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class MomentImg implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;

    /**
     * 归属家长圈的id
     */
    private String momentId;

    /**
     * 归属图片路径/链接
     */
    private String imgSrc;


}
