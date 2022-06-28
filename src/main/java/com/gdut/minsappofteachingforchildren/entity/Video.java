package com.gdut.minsappofteachingforchildren.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_video
 */
@TableName(value ="t_video")
@Data
public class Video implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private String localAddress;

    /**
     * 
     */
    private String suffix;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Video other = (Video) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLocalAddress() == null ? other.getLocalAddress() == null : this.getLocalAddress().equals(other.getLocalAddress()))
            && (this.getSuffix() == null ? other.getSuffix() == null : this.getSuffix().equals(other.getSuffix()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLocalAddress() == null) ? 0 : getLocalAddress().hashCode());
        result = prime * result + ((getSuffix() == null) ? 0 : getSuffix().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", localAddress=").append(localAddress);
        sb.append(", suffix=").append(suffix);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}