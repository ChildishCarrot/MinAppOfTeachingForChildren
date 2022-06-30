package com.gdut.minsappofteachingforchildren.dto;

import com.gdut.minsappofteachingforchildren.entity.Moment;
import com.gdut.minsappofteachingforchildren.entity.MomentImg;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class MomentImgs extends Moment{

    private List<String> imgSrcs;

}
