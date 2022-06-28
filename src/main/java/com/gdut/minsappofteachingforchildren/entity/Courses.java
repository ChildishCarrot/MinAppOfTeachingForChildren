package com.gdut.minsappofteachingforchildren.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * @TableName t_courses
 */
@TableName(value ="t_courses")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Courses implements Serializable {

    /**
     * 课程号，有mybatisplus自动生成唯一性ID，新增课程时无需设置（雪花算法）
     */
    @TableId(type = IdType.AUTO)
    private Long cid;

    /**
     * 用户ID，用户上传时需要带上自己的唯一性ID
     */
    @NotNull
    private Long uid;

    /**
     * 课程类型
      */
    @NotNull
    private String type;

    /**
     * 课程内容
     */
    @NotBlank
    private String text;

    /**
     * 课程名称
     */
    @NotNull
    private String name;

    /**
     * 作者，一般指上传者的名称（官方课程的作者一般没有）
     */
    @NotNull
    private String author;

    /**
     * 课程适合人群的最小年龄
     */
    @Max(value = 100)
    @Min(value = 0)
    private int maxage;

    /**
     * 课程适合人群的最大年龄
     */
    @Max(value = 100)
    @Min(value = 0)
    private int minage;

    @TableLogic
    private Integer isdeleted = 0;

    @Version
    private Integer version = 1;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}