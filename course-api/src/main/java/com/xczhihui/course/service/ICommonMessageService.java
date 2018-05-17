package com.xczhihui.course.service;

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
}
