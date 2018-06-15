package com.xczhihui.bxg.online.web.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.MessageDao;
import com.xczhihui.bxg.online.web.service.MessageService;
import com.xczhihui.bxg.online.web.vo.MessageVo;
import com.xczhihui.common.util.bean.Page;

/**
 * Created by 1 on 2016/8/11.
 */
@Service
public class MessageServiceImpl extends OnlineBaseServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    /**
     * 查询系统消息页面
     *
     * @param u          当前登录用户
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @Override
    public Page<MessageVo> findMessagePage(OnlineUser u, Integer type, Integer pageSize, Integer pageNumber) {
        return messageDao.getMessageList(u, type, pageNumber, pageSize);
    }

    @Override
    public void deleteById(String id, String userId) {
        messageDao.deleteById(id, userId);
    }

    @Override
    public void updateReadStatus(String userId, Integer type) {
        messageDao.updateReadStatus(userId, type);
    }

    /**
     * 将某条消息标志已读状态
     *
     * @param id     消息id
     * @param userId
     */
    @Override
    public void updateReadStatusById(String id, String userId) {
        messageDao.updateReadStatusById(id, userId);
    }

    /**
     * 获取未读消息总数
     *
     * @return
     */
    @Override
    public Map<String, Object> findMessageCount(String userId) {
        return messageDao.findMessageCount(userId);
    }

    /**
     * 获取最新公告
     */
    @Override
    public Map<String, Object> findNewestNotice(OnlineUser user) {
        return messageDao.findNewestNotice(user);
    }
}

