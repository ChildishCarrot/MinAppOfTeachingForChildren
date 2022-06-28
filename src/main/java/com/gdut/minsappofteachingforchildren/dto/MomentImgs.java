package com.gdut.minsappofteachingforchildren.dto;

import com.gdut.minsappofteachingforchildren.entity.Moment;
import com.gdut.minsappofteachingforchildren.entity.MomentImg;
import lombok.Data;

import java.util.List;

@Data
public class MomentImgs extends Moment{

    private List<String> imgSrcs;

}
