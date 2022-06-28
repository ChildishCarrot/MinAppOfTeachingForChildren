package com.gdut.minsappofteachingforchildren.vo;

import com.gdut.minsappofteachingforchildren.entity.MomentComment;
import lombok.Data;

@Data
public class MomentCommentVo extends MomentComment {
    private String userNickname;
    private String userAvatar;

}
