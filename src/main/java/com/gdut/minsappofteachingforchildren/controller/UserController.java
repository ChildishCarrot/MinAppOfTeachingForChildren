package com.gdut.minsappofteachingforchildren.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdut.minsappofteachingforchildren.entity.Friend;
import com.gdut.minsappofteachingforchildren.entity.User;
import com.gdut.minsappofteachingforchildren.service.FriendService;
import com.gdut.minsappofteachingforchildren.service.UserService;
import com.gdut.minsappofteachingforchildren.vo.FriendVo;
import com.gdut.minsappofteachingforchildren.vo.RespBean;
import com.gdut.minsappofteachingforchildren.vo.RespBeanEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "用户", tags = "用户管理")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendsService;

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("/getUserById/{userId}")
    public RespBean getUserById(@PathVariable String userId){
        if(userService.getById(userId)!=null){
            User user = userService.getById(userId);
            user.setOpenId(null);
            return RespBean.success(user);
        }
        return RespBean.error(RespBeanEnum.USER_GETINFO_ERROR);

    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PostMapping("/update")
    public RespBean update(@ApiParam("不要有openId") @RequestBody User user){
        if(userService.updateById(user)){
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.USER_UPDATE_ERROR);

    }

    /**
     * 添加好友
     * @param userId1
     * @param userId2
     * @return
     */
    @PostMapping("/addFriend/{userId1}/{userId2}")
    public RespBean addFriend(@PathVariable("userId1") String userId1,@PathVariable("userId2") String userId2){
        Friend friend = new Friend();
        friend.setUserId1(userId1);
        friend.setUserId2(userId2);
        if(friendsService.save(friend)){
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.ADD_FRIEND_ERROR);
    }

    /**
     * 删除好友
     * @param userId1
     * @param userId2
     * @return
     */
    @DeleteMapping("/deleteFriend/{userId1}/{userId2}")
    public RespBean deleteFriend(@PathVariable("userId1") long userId1,@PathVariable("userId2") long userId2){
        Friend friend = friendsService.getOne(new QueryWrapper<Friend>().eq("user_id1",userId1).eq("user_id2",userId2));
        if(friendsService.removeById(friend)){
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.DELETE_FRIEND_ERROR);
    }

    /**
     * 获取好友列表
     * @param userId
     * @return
     */
    @GetMapping("/getFriends/{userId}")
    public RespBean getFriends(@PathVariable("userId") String userId){

        List<String> friends = friendsService.getFriends(userId);
        List<FriendVo> friendVos = new ArrayList<>();
        //将得到的friends转换为vo
        for (String friend : friends) {
            User user = userService.getById(friend);
            FriendVo friendVo = new FriendVo();
            friendVo.setUserId(user.getUserId());
            friendVo.setUserAvatar(user.getUserAvatar());
            friendVo.setUserNickname(user.getUserNickname());
            friendVos.add(friendVo);
        }
        return RespBean.success(friendVos);
    }


}
