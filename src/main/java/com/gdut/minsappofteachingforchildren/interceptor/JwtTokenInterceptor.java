package com.gdut.minsappofteachingforchildren.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.gdut.minsappofteachingforchildren.utils.JWTUtils;
import com.gdut.minsappofteachingforchildren.utils.JsonUtils;
import com.gdut.minsappofteachingforchildren.vo.RespBean;
import com.gdut.minsappofteachingforchildren.vo.RespBeanEnum;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


import java.io.IOException;

import java.util.Map;


@Component
public class JwtTokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在拦截器中，如果请求为OPTIONS请求，则返回true，表示可以正常访问，然后就会收到真正的GET/POST请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            System.out.println("OPTIONS请求，放行");
            return true;
        }
        System.out.println("===================进入拦截器=======================");
        System.out.println(request.getHeader("Authorization"));

        try {
            Algorithm algorithm = Algorithm.HMAC256("Carrot");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Carrot") //匹配指定的token发布者 auth0
                    .build();
            DecodedJWT jwt = verifier.verify(request.getHeader("Authorization")); //解码JWT ，verifier 可复用

        }catch (JWTVerificationException e){
            //无效的签名/声明
            System.out.println("无效的签名");
            e.printStackTrace();

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset='utf-8");
            PrintWriter out = response.getWriter();
            out.print(JsonUtils.objectToJson(RespBean.error(RespBeanEnum.TOKEN_ERROR)));

            return false;
        }
        return true;








    }






}
