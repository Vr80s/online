package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.ShareOrderVo;
import com.xczhihui.bxg.online.web.vo.ShareUserVo;
import com.xczhihui.bxg.online.web.vo.SubsidiesVo;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Map;

/**
 * 分销管理模块业务接口
 * @Author Fudong.Sun【】
 * @Date 2016/12/8 16:10
 */
public interface ShareManageService {
    /**
     * 查询用户各等级学费补贴
     * @param userId  补贴对象
     * @return
     */
    public SubsidiesVo findSubsidies(String userId);

    /**
     * 根据条件查询分享订单
     * @param userId
     * @param searchCase
     * @param level
     * @param startTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<ShareOrderVo> findShareOrders(String userId, Integer searchCase,String searchContent, Integer level, String startTime, String endTime, Integer pageNumber, Integer pageSize);



    /**
     * 查询用户各级别用户人数
     * @return
     */
    public Map<String, Object> findUserCount(HttpServletRequest request);


    /**
     * 获取当前登录用户的所有一级分享用户
     * @param searchCase 0:按照昵称搜索  1:按照用户名搜索
     * @param searchContent 搜索内容
     * @param startTime  搜索开始时间
     * @param endTime  结束时间
     * @param pageNumber 当前页面
     * @param pageSize  每页条数
     * @param request  当前登录用户
     * @return
     */
    public Page<ShareUserVo> findOneLevelUser(Integer searchCase,String searchContent,String startTime,String endTime,Integer pageNumber, Integer pageSize,HttpServletRequest request) throws ParseException;

    /**
     * 保存分享好友关系
     * @param req
     * @param onlineUser 1，注册时为新用户，2，购买时为当前登录用户
     * @return
     */
    public OnlineUser saveShareRelation(HttpServletRequest req, OnlineUser onlineUser);

    /**
     * 检查是否修改登录用户的分享关系
     * @param req
     * @return
     */
    public Map<String,Object> updateCheckShareRelation(HttpServletRequest req);
}
