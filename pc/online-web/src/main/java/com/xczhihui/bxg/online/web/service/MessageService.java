package com.xczhihui.bxg.online.web.service;

import java.util.Map;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.MessageShortVo;
import com.xczhihui.common.util.bean.Page;

/**
 * 意见反馈
 *
 * @author majian
 * @date 2016-8-11 18:48:49
 */
public interface MessageService {

    Page<MessageShortVo> findMessagePage(OnlineUser u, Integer type, Integer pageSize, Integer pageNumber);

    /**
     * 根据ID删除
     *
     * @param id
     */
    void deleteById(String id, String userId);

    /**
     * 修改读取状态
     *
     * @param userId
     */
    void updateReadStatus(String userId, Integer type);

    /**
     * 将某条消息标志已读状态
     *
     * @param id     消息id
     * @param userId
     */
    void updateReadStatusById(String id, String userId);

    /**
     * 获取未读消息总数
     *
     * @return
     */
    Map<String, Object> findMessageCount(String userId);

    /**
     * 获取最新公告
     */
    Map<String, Object> findNewestNotice(OnlineUser user);
}
