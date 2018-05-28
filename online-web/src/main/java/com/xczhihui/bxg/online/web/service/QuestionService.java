package com.xczhihui.bxg.online.web.service;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.bxg.online.web.vo.QuestionVo;

import java.util.List;

/**
 * 课程问题及回答业务层接口类
 *
 * @author 康荣彩
 * @create 2016-08-29 16:44
 */
public interface QuestionService {

    /**
     * 根据课程id 查找到课程对应的问题
     * @param courseId
     * @return
     */
    public List<QuestionVo> getQuestionList(Integer courseId);

    /**
     * 根据问题ID  查找到对应的回答信息
     * @param pid
     * @return
     */
    public QuestionVo getQuestionByPid(Integer courseId,String pid);
}
