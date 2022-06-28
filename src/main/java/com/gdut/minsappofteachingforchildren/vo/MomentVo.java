package com.gdut.minsappofteachingforchildren.vo;

import com.gdut.minsappofteachingforchildren.entity.Moment;
import lombok.Data;

import java.util.List;

@Data
public class MomentVo extends Moment {
    int likeNum;
    boolean isLike;
    String userAvatar;
    String userNickname;
    List<String> pictures;
}
