package com.gdut.minsappofteachingforchildren.controller;

import com.gdut.minsappofteachingforchildren.entity.IsLogin;
import com.gdut.minsappofteachingforchildren.entity.User;
import com.gdut.minsappofteachingforchildren.entity.WXSessionModel;
import com.gdut.minsappofteachingforchildren.service.UserService;
import com.gdut.minsappofteachingforchildren.utils.HttpClientUtil;
import com.gdut.minsappofteachingforchildren.utils.JWTUtils;
import com.gdut.minsappofteachingforchildren.utils.JsonUtils;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    @Transactional
    @ResponseBody
    @PostMapping("/wxLogin/{code}")
    public IsLogin Login(@PathVariable String code,@ApiParam("用户，只需要头像路径和昵称") @RequestBody User user) {
        System.out.println("0");
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> param = new HashMap<String, String>();
        param.put("appid", "wxd6bec73f90610e60");
        param.put("secret", "efc701d9e33718b596dfccc1125f4baf");
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");
        System.out.println(param);
        String wxResult = HttpClientUtil.doGet(url, param);
        System.out.println(wxResult);
        WXSessionModel wxSessionModel = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);
        String openid = wxSessionModel.getOpenid();
        user.setOpenId(openid);
        System.out.println(user);
        System.out.println(user.toString()+","+openid);
//        return user.getUserAvatar();
        return new IsLogin().isTrue(user, openid, userService);

    }
}
