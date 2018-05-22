package com.xczhihui.course.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.Message;
import com.xczhihui.course.model.Notice;
import com.xczhihui.course.params.BaseMessage;

/**
 * @author hejiwei
 */
public interface ICommonMessageService {

    /**
     * 消息
     *
     * @param message 消息
     */
    void saveMessage(BaseMessage message);

    /**
     * 查看用户消息列表
     *
     * @param page   分页参数
     * @param size   分页条数
     * @param userId 用户id
     * @return
     */
    Page<Message> list(int page, int size, String userId);

    /**
     * 获取用户的消息未读数量
     *
     * @param userId 用户id
     * @return 未读数量
     */
    int countUnReadCntByUserId(String userId);

    /**
     * 删除通过用户id与消息id
     *
     * @param messageId 消息id
     * @param userId    用户id
     */
    void deleteById(String messageId, String userId);

    /**
     * 更新消息已读状态 messageId 为空时更新将该用户的所有消息标识为已读
     *
     * @param messageId 消息id
     * @param userId    用户id
     */
    void updateReadStatus(String messageId, String userId);

    /**
     * 公告
     *
     * @return
     */
    Notice getNewestNotice();

    /**
     * 处理老的消息数据的结构
     */
    void updateMessage();
}
