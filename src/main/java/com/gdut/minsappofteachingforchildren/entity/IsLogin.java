package com.gdut.minsappofteachingforchildren.entity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdut.minsappofteachingforchildren.service.UserService;
import com.gdut.minsappofteachingforchildren.utils.JWTUtils;
import com.gdut.minsappofteachingforchildren.vo.UserVo;
import org.springframework.stereotype.Component;

@Component
public class IsLogin {

    private Integer code;

    private UserVo userVo;

    private String token;

    public void setUserVo(UserVo userVo){
        this.userVo = userVo;
    }

    public UserVo getUserVo(){
        return this.userVo;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public IsLogin isTrue(User user, String openid, UserService userService) {
        User user1 = userService.getOne(new QueryWrapper<User>().eq("open_id", openid));
        IsLogin isLogin = new IsLogin();
        isLogin.setCode(500);
        if (user1 != null) {
            userService.updateById(user1);
            isLogin.setCode(200);//老用户
            String jwt = JWTUtils.getToken(user1.getUserId(),user1.getUserNickname());
//            String jwt = JwtUtil.generateToken(userId);
            isLogin.setToken(jwt);
            UserVo userVo = new UserVo();
            userVo.setUserId(user1.getUserId());
            userVo.setUserAvatar(user1.getUserAvatar());
            userVo.setUserNickname(user1.getUserNickname());
            isLogin.setUserVo(userVo);

        }else {
            userService.save(user);
            isLogin.setCode(300);//新用户
            String jwt = JWTUtils.getToken(user.getUserId(),user.getUserNickname());
//            String jwt = JwtUtil.generateToken(userId);
            isLogin.setToken(jwt);
            UserVo userVo = new UserVo();
            userVo.setUserId(user.getUserId());
            userVo.setUserAvatar(user.getUserAvatar());
            userVo.setUserNickname(user.getUserNickname());
            isLogin.setUserVo(userVo);

        }
        return isLogin;
    }






}
