package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.MessageShortVo;

import java.util.Map;

/**
 * 意见反馈
 * @author majian
 * @date 2016-8-11 18:48:49
 */
public interface MessageService {

    /**
     * 意见反馈
     * @param userId
     * @param title 标题
     * @param describe 描述
     */
    public void addFeedBack(String userId,String title,String describe);

    /**
     * 发送不同类型消息
     * @param msg_id
     * @param userId
     * @param type
     * @param context
     * @param is_online 网站顶部公告通知标识（1：在线，0：下线）
     * @param planId 串讲课的id
     */
    public void addMessage(String msg_id, String userId,Integer type,String context,Integer is_online,String planId);

    public Page<MessageShortVo> findMessagePage(OnlineUser u,Integer  type, Integer pageSize, Integer pageNumber);

    /**
     * 根据ID删除
     * @param id
     */
    public void deleteById(String id,String userId);

    /**
     * 修改读取状态
     * @param userId
     */
    public void updateReadStatus(String userId,Integer type);

    /**
     * 将某条消息标志已读状态
     *
     * @param id     消息id
     * @param userId
     */
    public void updateReadStatusById(String id, String userId);


    /**
     * 获取未读消息总数
     * @return
     */
    public  Map<String, Object>  findMessageCount(String userId);

    /**
     * 获取最新公告
     */
    public Map<String, Object> findNewestNotice(OnlineUser user);

    /**
     * 发送消息
     * @param messageShortVo
     */
    public void saveMessage(MessageShortVo messageShortVo);
}
