package com.gdut.minsappofteachingforchildren.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;

public class JWTUtils {

    private static final String SING="Carrot";



    //    生成token
    public static String getToken(String userId,String userName){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,10);//失效的时间
        String token = JWT.create()
                .withIssuer("Carrot")
                .withClaim("userId",userId)
                .withClaim("userName", userName)//payload
                .withExpiresAt(instance.getTime())//指定失效时间
                .sign(Algorithm.HMAC256(SING));//签名
        System.out.println(token);
        return token;
    }
    //验证token合法性
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }



    //获得token里面的信息
public static DecodedJWT getTokenInfo(String token){
        DecodedJWT verify=JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return verify;
}
}

