package com.gdut.minsappofteachingforchildren.entity;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result <T>{

    private String code;
    private String message;
    private T data;

    public Result(String code, String message){
        this.code = code;
        this.message = message;
        this.data = null;
    }

}
