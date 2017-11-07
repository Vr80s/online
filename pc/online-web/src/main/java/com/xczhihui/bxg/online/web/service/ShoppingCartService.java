package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.util.bean.ResponseObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 购物车模块业务层接口
 * @Author Fudong.Sun【】
 * @Date 2017/2/20 16:12
 */
public interface ShoppingCartService {

    /**
     * 查询我的购物车中课程数量
     * @param userId
     * @return
     */
    public Integer findCourseNum(String userId);
    /**
     * 课程加入购物车
     * @param user
     * @param courseId
     * @return
     */
    public ResponseObject addCart(BxgUser user,Integer courseId);

    /**
     * 我的购物车课程列表
     * @param userId
     * @return
     */
    public List<Map<String,Object>> lists(String userId);

    /**
     * 删除我的购物车中的课程
     * @param userId
     * @param ids
     * @return
     */
    public Integer delete(String userId,Set<String> ids);

    /**
     * 课程批量加入购物车
     * @param userId 购买课程用户id
     * @param courseIds 加入购物车课程id数组
     * @param rule_id 活动id号
     */
    public ResponseObject addCourseToCart(String userId, String[] courseIds,String rule_id);
}
