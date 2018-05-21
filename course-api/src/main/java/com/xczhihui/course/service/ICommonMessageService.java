package com.xczhihui.course.service;

import java.util.List;

import com.xczhihui.course.model.Message;
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
     * @param userId 用户id
     * @return
     */
    List<Message> list(int page, String userId);

    /**
     * 获取用户的消息维未读数量
     *
     * @param userId 用户id
     * @return 未读数量
     */
    int countUnReadCntByUserId(String userId);
}
