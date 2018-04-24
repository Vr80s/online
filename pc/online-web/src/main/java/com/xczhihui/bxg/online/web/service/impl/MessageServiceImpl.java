package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Message;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.Constant;
import com.xczhihui.bxg.online.web.dao.MessageDao;
import com.xczhihui.bxg.online.web.service.MessageService;
import com.xczhihui.bxg.online.web.vo.MessageShortVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.Date;

import java.util.Map;

/**
 * Created by 1 on 2016/8/11.
 */
@Service
public class MessageServiceImpl extends OnlineBaseServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public void addFeedBack(String userId, String title, String describe) {
        Message message = new Message();
        message.setTitle(title);
//        message.setContext("<font color=\"#2cb82c\">意见反馈：</font>"+describe);
        message.setContext(describe);
        message.setUserId(userId);
        message.setType(2);
        message.setStatus((short) 1);
        message.setCreateTime(new Date());
        message.setReadstatus((short) 0);
        message.setAnswerStatus((short) 0);
        dao.save(message);
    }

    @Override
    public void addMessage(String msg_id, String userId, Integer type, String context,Integer is_online,String planId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",msg_id);
        params.addValue("context",context);
        params.addValue("userId",userId);
        params.addValue("type",type);
        params.addValue("createTime",new Date());
        params.addValue("status",Constant._STATUS_EN);
        params.addValue("is_online",is_online);
        String sql;
        if(planId==null){
            sql = "insert into oe_message (id,context,user_id,type,create_time,status,is_online) values (:id,:context,:userId,:type,:createTime,:status,:is_online)";
        }else {
            params.addValue("planId", planId);
            sql = "insert into oe_message (id,context,user_id,type,create_time,status,is_online,plan_id) values (:id,:context,:userId,:type,:createTime,:status,:is_online,:planId)";
        }
        dao.getNamedParameterJdbcTemplate().update(sql,params);
    }

    /**
     * 查询系统消息页面
     * @param u 当前登录用户
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @Override
    public Page<MessageShortVo> findMessagePage(OnlineUser u,Integer  type, Integer pageSize, Integer pageNumber) {
        return  messageDao.getMessageList(u,type, pageNumber, pageSize);
    }

    @Override
    public void deleteById(String id,String userId) {
        messageDao.deleteById(id,userId);
    }

    @Override
    public void updateReadStatus(String userId,Integer type) {
        messageDao.updateReadStatus(userId,type);
    }

    /**
     * 将某条消息标志已读状态
     *
     * @param id     消息id
     * @param userId
     */
    @Override
    public void updateReadStatusById(String id, String userId) {
            messageDao.updateReadStatusById(id,userId);
    }

    /**
     * 获取未读消息总数
     * @return
     */
    @Override
    public  Map<String, Object>  findMessageCount(String userId){
        return messageDao.findMessageCount(userId);
    }

    /**
     * 获取最新公告
     */
    @Override
    public Map<String, Object> findNewestNotice(OnlineUser user){
        return  messageDao.findNewestNotice(user);
    }

    /**
     * 发送消息
     * @param messageShortVo
     */
    @Override
    public void saveMessage(MessageShortVo messageShortVo){
         messageDao.saveMessage(messageShortVo);
    }
}

