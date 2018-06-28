package com.xczhihui.message.service;

import java.lang.reflect.InvocationTargetException;

import com.xczhihui.bxg.online.common.domain.Message;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.message.vo.FeedBackVo;
import com.xczhihui.message.vo.MessageVo;

/**
 * 问题反馈
 *
 * @author duanqh
 */
public interface FeedbackService {

    /**
     * 删除or启用
     *
     * @param id 编号
     */
    void updateStatus(String id);

    /**
     * 添加反馈消息
     *
     * @param vo 消息对象
     */
    void addContext(FeedBackVo vo);

    /**
     * 查看指定意见的反馈
     *
     * @param feedId 意见反馈编号
     * @return 消息对象
     */
    Message findFeekBackByFeedId(String feedId);

    /**
     * 查询所有反馈消息
     *
     * @param vo         条件查询对象
     * @param pageNumber 页码
     * @param pageSize   页大小
     * @return 分页对象
     */
    Page<MessageVo> findPageMessages(
            MessageVo vo, int pageNumber, int pageSize)
            throws InvocationTargetException, IllegalAccessException;

}
