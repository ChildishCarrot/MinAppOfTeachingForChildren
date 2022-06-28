package com.gdut.minsappofteachingforchildren.controller;

import com.gdut.minsappofteachingforchildren.entity.IsLogin;
import com.gdut.minsappofteachingforchildren.entity.User;
import com.gdut.minsappofteachingforchildren.entity.WXSessionModel;
import com.gdut.minsappofteachingforchildren.service.UserService;
import com.gdut.minsappofteachingforchildren.utils.HttpClientUtil;
import com.gdut.minsappofteachingforchildren.utils.JWTUtils;
import com.gdut.minsappofteachingforchildren.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;

    /**
     * 测试页面跳转
     * @return
     */
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello1";
    }

    @ResponseBody
    @GetMapping("/index")
    public String index(){
        return "hello index";
    }

    @ResponseBody
    @GetMapping("/update")
//    @Secured({"ROLE_sale","ROLE_manager"})
//    @PreAuthorize("hasRole('ROLE_admin')")
    public String update(){
        return "hello update";
    }

    @GetMapping("/test")
    @ResponseBody
    public String testJWT(){
        return "ok";
    }



}
