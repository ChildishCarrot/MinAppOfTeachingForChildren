package com.gdut.minsappofteachingforchildren.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.gdut.minsappofteachingforchildren.dto.MomentImgs;
import com.gdut.minsappofteachingforchildren.entity.*;
import com.gdut.minsappofteachingforchildren.service.*;
import com.gdut.minsappofteachingforchildren.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Api(value = "朋友圈", tags = "朋友圈管理")
@Slf4j
@RestController
@RequestMapping("/moment")
public class MomentController {

    @Autowired
    private UserService userService;
    @Autowired
    private MomentService momentService;
    @Autowired
    private MomentCommentService momentCommentService;
    @Autowired
    private MomentImgService momentImgService;
    @Autowired
    private MomentLikesService momentLikesService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private RedisTemplate redisTemplate;




    /**
     * 发布朋友圈
     * @param momentImgs
     * @return
     */
    @PostMapping("/publish")
    public RespBean publishMoment(@ApiParam("只需要内容，图片路径（String数组）和发布者id") @RequestBody MomentImgs momentImgs){
        Moment moment = new Moment();
        moment.setContent(momentImgs.getContent());
        moment.setUserId(momentImgs.getUserId());
        moment.setLikeNum(0);
        List<String> imgList = momentImgs.getImgSrcs(); //发布朋友圈的图片的链接列表，在此之前图片应已上传
        //如果朋友圈发布成功，就将该朋友圈的图片路径存入MomentImg表内
        if(momentService.save(moment)){
            List<MomentImg> list = new ArrayList<>();
            for(int i=0;i<imgList.size();i++){
                MomentImg img = new MomentImg();
                img.setMomentId(moment.getId());
                img.setImgSrc(imgList.get(i));
                list.add(img);
            }
            momentImgService.saveBatch(list);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.MOMENT_PUBLISH_ERROR);
    }

    /**
     * 查询某用户发布的朋友圈
     */
    @GetMapping("/getByUserId/{userId}/{pageIndex}/{number}")
    public RespBean getMomentByUserId(@PathVariable("userId") String userId,
                                      @ApiParam("第几页")@PathVariable("pageIndex") int pageIndex,
                                      @ApiParam("一页查询几个") @PathVariable("number") int number){
        QueryWrapper<Moment> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).orderByDesc("time"); //按时间降序排序
        Page<Moment> page = new Page<>(pageIndex,number);  //从pageIndex开始查询number个
//        List<Moment> list = momentService.list(queryWrapper);

        IPage<Moment> iPage= momentService.page(page,queryWrapper);

        return RespBean.success(momentList2MomentVoList(iPage.getRecords()));
    }

    /**
     * 查询某用户好友的朋友圈
     */
    @GetMapping("/getFriendsMomentByUserId/{userId}/{pageIndex}/{number}")
    public RespBean getFriendsMomentByUserId(@PathVariable("userId") String userId,
                                             @ApiParam("第几页，下标从1开始") @PathVariable("pageIndex") int pageIndex,
                                             @ApiParam("一页查询几个") @PathVariable("number") int number){

        List<String> friendIds = friendService.getFriends(userId);
        QueryWrapper<Moment> queryWrapper= new QueryWrapper<>();
        queryWrapper.orderByDesc("time");  //按时间降序排序
        for (String friendId : friendIds) {
            queryWrapper.eq("user_id",friendId).or();  //所有好友的都查
        }
        Page<Moment> page = new Page<>(pageIndex,number);  //从pageIndex开始查询number个
        IPage<Moment> iPage= momentService.page(page,queryWrapper);
        return RespBean.success(momentList2MomentVoList(iPage.getRecords()));
    }

    /**
     * 按时间分页查询朋友圈，一般用于查询最新的
     * @param pageIndex
     * @param number
     * @return
     */
    @GetMapping("/getLatestMoment/{pageIndex}/{number}")
    public RespBean getLatestMoments(@ApiParam("第几页，下标从1开始")@PathVariable("pageIndex")int pageIndex,
                                     @ApiParam("一页查询几个")@PathVariable("number")int number ){
        QueryWrapper<Moment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("time");
        Page<Moment> page = new Page<>(pageIndex,number);  //从pageIndex开始查询number个;
        IPage<Moment> iPage= momentService.page(page,queryWrapper);
        return RespBean.success(momentList2MomentVoList(iPage.getRecords()));
    }

    /**
     * 获取最近一周/月/年的热度最高的帖子（按点赞数量排名）
     * @param pageIndex
     * @param number
     * @param span
     * @return
     */
    @GetMapping("/getRecentHotMoments/{pageIndex}/{number}/{span}")
    public RespBean getRecentHotMoments(@ApiParam("第几页，下标从1开始")@PathVariable("pageIndex")int pageIndex,
                                        @ApiParam("一页查询几个")@PathVariable("number")int number,
                                        @ApiParam("最近一周/月/年，周传'W',月'M',年'Y'")@PathVariable("span")String span){

        Date date = new Date();
        Long l = 0L;
        if(span.equals("W")){
            l = date.getTime()-604800000L; //减去一周
        }
        else if(span.equals("M")){
            l = date.getTime()-2592000000L;    //减去一个月
        }
        else if(span.equals("Y")){
            l = date.getTime()-31536000000L;  //减去一年
        }
        if(l==0L){
            return RespBean.error(RespBeanEnum.SPAN_ERROR);
        }

        Date spanAgoTime = new Date(l);
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(spanAgoTime);
        QueryWrapper<Moment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("like_num");
        queryWrapper.ge("time",format);
        Page<Moment> page = new Page<>(pageIndex,number);  //从pageIndex开始查询number个;
        IPage<Moment> iPage= momentService.page(page,queryWrapper);


        return RespBean.success(momentList2MomentVoList(iPage.getRecords()));


    }

    /**
     * 删除朋友圈
     * @param momentId
     * @return
     */
    @GetMapping("/deleteByMomentId/{momentId}")
    public RespBean deleteByMomentId(@ApiParam("朋友圈id")@PathVariable("momentId") String momentId){
        if(momentService.removeById(momentId)){
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.MOMENT_DELETE_ERROR);

    }

    /**
     * 发表评论
     * @param momentComment
     * @return
     */
    @PostMapping("/publishComment")
    public RespBean publishComment(@ApiParam("只需要内容，父id(父id表示")@RequestBody MomentComment momentComment){
        if(momentComment.getFatherId()==""){
            momentComment.setFatherId(null);
        }
        if(momentCommentService.save(momentComment)){
            User user = userService.getById(momentComment.getUserId());
            UserVo userVo = new UserVo();
            userVo.setUserAvatar(user.getUserAvatar());
            userVo.setUserId(user.getUserId());
            userVo.setUserNickname(user.getUserAvatar());
            return RespBean.success(userVo);
        }
        return RespBean.error(RespBeanEnum.MOMENT_COMMENT_PUBLISH_ERROR);
    }

    /**
     * 根据朋友圈id查询某条朋友圈的评论
     * @param momentId
     * @return
     */
    @GetMapping("/getCommentByMomentId/{momentId}")
    public RespBean getCommentByMomentId(@ApiParam("朋友圈id")@PathVariable("momentId") String momentId){
        List<MomentComment> list =  momentCommentService.list(new QueryWrapper<MomentComment>().eq("moment_id",momentId));
        List<MomentCommentVo> voList = new ArrayList<>();
        for (MomentComment momentComment : list) {
            MomentCommentVo vo = new MomentCommentVo();
            vo.setId(momentComment.getId());
            vo.setFatherId(momentComment.getFatherId());
            vo.setContent(momentComment.getContent());
            vo.setMomentId(momentComment.getMomentId());
            vo.setTime(momentComment.getTime());
            vo.setUserId(momentComment.getUserId());
            vo.setUserAvatar(userService.getById(momentComment.getUserId()).getUserAvatar());
            vo.setUserNickname(userService.getById(momentComment.getUserId()).getUserNickname());
            voList.add(vo);
        }

        return RespBean.success(voList);
    }


    /**
     * 朋友圈点赞/取消点赞
     * @param momentId
     * @param userId
     * @return
     */
    @PostMapping("/like/{momentId}/{userId}")
    public RespBean like(@PathVariable("momentId") String momentId, @PathVariable("userId") String userId){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        List<String> userLikeMoments;
        MomentLikes like = momentLikesService.getOne(new QueryWrapper<MomentLikes>().eq("user_id",userId).eq("moment_id",momentId));
        //取得该用户点赞的朋友圈
        if(valueOperations.get("userLikeMoment:user:"+userId)!=null){
            userLikeMoments = (List<String>)valueOperations.get("userLikeMoment:user:"+userId);
        }else{
            userLikeMoments = new ArrayList<>();
        }
        //如果该用户已经点赞过了，就取消点赞
        if( like!=null){
            valueOperations.decrement("momentLikeNum:"+momentId); //将该朋友圈点赞数+1

            //在数据库中移除点赞关系
            momentLikesService.removeById(like);

            //在redis中移除点赞关系
            userLikeMoments.remove(momentId);
            valueOperations.set("userLikeMoment:"+"user:"+userId,userLikeMoments);
            return RespBean.success();
        }
        //否则执行点赞
        like = new MomentLikes();
        like.setUserId(userId);
        like.setMomentId(momentId);
        momentLikesService.save(like);
        userLikeMoments.add(momentId);
        valueOperations.set("userLikeMoment:"+"user:"+userId,userLikeMoments);
        valueOperations.increment("momentLikeNum:"+momentId);  //将该朋友圈点赞数-1
        return RespBean.success();


    }

    /**
     * 获取一条朋友圈的点赞数量
     * @param momentId
     * @return
     */
    private int getLikesNum(String momentId){
        //如果redis里有就去redis查
        if (redisTemplate.opsForValue().get("momentLikeNum:"+momentId)!=null){
            int likeNum = (int)redisTemplate.opsForValue().get("momentLikeNum:"+momentId);
            return likeNum;
        }
        //如果redis没有就查数据库,并将数据添加到redis上
        int likeNum = momentService.getById(momentId).getLikeNum();
        redisTemplate.opsForValue().set("momentLikeNum:"+momentId,likeNum);
        return likeNum;

    }


    /**
     * 将moment转为momentvo
     * @param moments
     * @return
     */
    private List<MomentVo> momentList2MomentVoList(List<Moment> moments){
        List<MomentVo> voList= new ArrayList<>();
        for (Moment moment : moments) {
            MomentVo momentVo = new MomentVo();
            momentVo.setId(moment.getId());
            momentVo.setUserId(moment.getUserId());
            momentVo.setUserNickname(userService.getById(momentVo.getUserId()).getUserNickname());
            momentVo.setUserAvatar(userService.getById(momentVo.getUserId()).getUserAvatar());
            momentVo.setTime(moment.getTime());
            momentVo.setContent(moment.getContent());
            momentVo.setLikeNum(moment.getLikeNum());
            boolean isLike = momentLikesService.getOne(new QueryWrapper<MomentLikes>().eq("moment_id",momentVo.getId()).eq("user_id",momentVo.getUserId()))!=null;
            momentVo.setLike(isLike);
            momentVo.setLikeNum(getLikesNum(moment.getId()));
            List<MomentImg> imgs = momentImgService.list(new QueryWrapper<MomentImg>().eq("moment_id", momentVo.getId()));
            List<String> imgsrcs = new ArrayList<>();
            for (MomentImg img : imgs) {
                imgsrcs.add(img.getImgSrc());
            }
            momentVo.setPictures(imgsrcs);
            voList.add(momentVo);
        }
        return voList;
    }


}
