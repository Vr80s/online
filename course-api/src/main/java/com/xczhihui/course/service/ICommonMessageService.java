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
     * @param page
     * @param userId
     * @return
     */
    List<Message> list(int page, String userId);
}
