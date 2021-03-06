package com.gdut.minsappofteachingforchildren.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//公共返回对象
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    private long code;
    private String message;
    private Object obj;

    //成功返回结果
    public static com.gdut.minsappofteachingforchildren.vo.RespBean success(){
        return new com.gdut.minsappofteachingforchildren.vo.RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessage(),null);
    }
    //成功返回结果
    public static com.gdut.minsappofteachingforchildren.vo.RespBean success(Object obj){
        return new com.gdut.minsappofteachingforchildren.vo.RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessage(),obj);
    }

    //失败返回结果
    public static com.gdut.minsappofteachingforchildren.vo.RespBean error(RespBeanEnum respBeanEnum){
        return new com.gdut.minsappofteachingforchildren.vo.RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(),null);
    }
    //失败返回结果
    public static com.gdut.minsappofteachingforchildren.vo.RespBean error(RespBeanEnum respBeanEnum, Object obj){
        return new com.gdut.minsappofteachingforchildren.vo.RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(),obj);
    }
}
