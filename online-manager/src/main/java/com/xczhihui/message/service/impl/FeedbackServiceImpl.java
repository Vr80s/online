package com.xczhihui.message.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Message;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.enums.MessageTypeEnum;
import com.xczhihui.course.enums.RouteTypeEnum;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.util.TextStyleUtil;
import com.xczhihui.message.dao.FeedBackDao;
import com.xczhihui.message.service.FeedbackService;
import com.xczhihui.message.vo.FeedBackVo;
import com.xczhihui.message.vo.MessageVo;
import com.xczhihui.support.shiro.ManagerUserUtil;

/**
 * 问题反馈service
 *
 * @author duanqh
 */
@Service
public class FeedbackServiceImpl extends OnlineBaseServiceImpl implements
        FeedbackService {

    @Autowired
    private ICommonMessageService commonMessageService;

    /**
     * 意见反馈Dao层
     */
    @Autowired
    public FeedBackDao dao;

    @SuppressWarnings("unchecked")
    @Override
    public Page<MessageVo> findPageMessages(
            MessageVo vo, int pageNumber, int pageSize)
            throws InvocationTargetException, IllegalAccessException {
        Page<Message> page = this.dao.findPageMessages(vo, "answerStatus", pageNumber, pageSize);

        List<Message> items = page.getItems();
        List<MessageVo> itemsVo = new ArrayList<MessageVo>();
        for (Message q : items) {
            MessageVo dest = new MessageVo();
            BeanUtils.copyProperties(dest, q);
            if (q.getId() != null) {
                String replyText = dest.getReplyText();
                if (StringUtils.isNotBlank(replyText)) {
                    replyText = replyText.replace("<font color=\"#2cb82c\">意见反馈：</font>", "");
                    if (replyText != null) {
                        replyText = replyText.replace(TextStyleUtil.LEFT_TAG + "意见反馈：" + TextStyleUtil.RIGHT_TAG, "");
                    }
                    dest.setReplyText(replyText);
                }
            }
            if (StringUtils.isBlank(dest.getUserName())) {
                dest.setUserName("游客");
            }
            itemsVo.add(dest);
        }

        Page<MessageVo> pageVo = new Page<MessageVo>(itemsVo, page.getTotalCount(), page.getPageSize(),
                page.getCurrentPage());
        return pageVo;
    }

    @Override
    public void updateStatus(String id) {
        Message m = dao.get(id, Message.class);
        if (m.isDelete()) {
            m.setDelete(false);
        } else {
            m.setDelete(true);
        }
        dao.update(m);
    }

    @Override
    public void addContext(FeedBackVo vo) {
        Message message = new Message();
        message.setLastTime(new Date());
        message.setContext(TextStyleUtil.LEFT_TAG + "意见反馈：" + TextStyleUtil.RIGHT_TAG
                + vo.getReplytext());
        message.setPid(vo.getId());
        message.setUserId(vo.getUserId());
        message.setType(0);
        message.setStatus((short) 1);
        message.setTitle(vo.getTitle());
        message.setCreateTime(new Date());
        message.setCreatePerson(ManagerUserUtil.getName());
        message.setReadstatus((short) 0);
        dao.save(message);

        String context = message.getContext();
        BaseMessage.Builder builder = new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal());
        if (StringUtils.isNotBlank(vo.getUserId())) {
            builder = builder.buildAppPush(TextStyleUtil.clearStyle(context));
        }
        commonMessageService
                .saveMessage(builder.build(vo.getUserId(), RouteTypeEnum.NONE, ManagerUserUtil.getId()));
        Message feekMessage = dao.get(vo.getId(), Message.class);
        feekMessage.setAnswerStatus((short) 1);
        feekMessage.setLastTime(new Date());
        feekMessage.setReplyText(vo.getReplytext());
        dao.update(feekMessage);
    }

    @Override
    public Message findFeekBackByFeedId(String feedId) {
        return dao.findFeekBackByFeedId(feedId);
    }

}
