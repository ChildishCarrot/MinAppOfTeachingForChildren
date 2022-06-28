package com.gdut.minsappofteachingforchildren.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * 共享资源类，包含视频，pdf，图片等文件，并不是保存朋友圈的图片
 * @TableName t_file
 */
@TableName(value ="t_file")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class myFile implements Serializable {
    /**
     * 文件唯一性ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID，用户上传时需要带上自己的唯一性ID
     */
    @NotNull
    private Long uid;

    /**
     * 上传后的文件名称
     */
    private String localAddress;

    /**
     * 文件后缀，即保存在哪一个文件夹中
     */
    private String suffix;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}