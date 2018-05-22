package com.xczhihui.bxg.online.web.service;

import java.util.Map;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.MessageVo;
import com.xczhihui.common.util.bean.Page;

public interface MessageService {

    Page<MessageVo> findMessagePage(OnlineUser u, Integer type, Integer pageSize, Integer pageNumber);

    /**
     * 根据ID删除
     *
     * @param id     id
     * @param userId 用户id
     */
    void deleteById(String id, String userId);

    /**
     * 修改读取状态
     *
     * @param userId 用户id
     * @param type   消息类型
     */
    void updateReadStatus(String userId, Integer type);

    /**
     * 将某条消息标志已读状态
     *
     * @param id     消息id
     * @param userId 用户id
     */
    void updateReadStatusById(String id, String userId);

    /**
     * 获取未读消息总数
     *
     * @param userId 用户id
     * @return
     */
    Map<String, Object> findMessageCount(String userId);

    /**
     * 获取最新公告
     */
    Map<String, Object> findNewestNotice(OnlineUser user);
}
