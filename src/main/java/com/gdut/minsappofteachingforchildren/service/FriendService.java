package com.gdut.minsappofteachingforchildren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdut.minsappofteachingforchildren.entity.Friend;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 * 螺杆菌帅的一
 * @author zhoubin
 *
 */
public interface FriendService extends IService<Friend> {

    List<String> getFriends(String userId);

}
