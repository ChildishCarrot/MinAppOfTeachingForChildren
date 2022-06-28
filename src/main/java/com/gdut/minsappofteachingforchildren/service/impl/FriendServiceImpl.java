package com.gdut.minsappofteachingforchildren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdut.minsappofteachingforchildren.entity.Friend;
import com.gdut.minsappofteachingforchildren.mapper.FriendMapper;
import com.gdut.minsappofteachingforchildren.service.FriendService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * 螺杆菌帅的一
 * @author zhoubin
 *
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

    /**
     * 获取该用户所有好友的id
     * @param userId
     * @return
     */
    @Override
    public List<String> getFriends(String userId) {
        List<Friend> friends1 = this.list(new QueryWrapper<Friend>().eq("user_id1",userId));
        List<Friend> friends2 = this.list(new QueryWrapper<Friend>().eq("user_id2",userId));
        List<String> friends = new ArrayList<>();
        //如果在userid=userid1，说明userid2为他的好友的id，将userid2加入列表
        for (Friend friend : friends1) {
            friends.add(friend.getUserId2());
        }
        //如果在userid=userid2，说明userid1为他的好友的id，将userid1加入列表
        for (Friend friend : friends2) {
            friends.add(friend.getUserId1());
        }
        return friends;
    }
}
