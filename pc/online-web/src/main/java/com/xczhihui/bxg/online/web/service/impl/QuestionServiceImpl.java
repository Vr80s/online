package com.xczhihui.bxg.online.web.service.impl;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.web.dao.QuestionDao;
import com.xczhihui.bxg.online.web.service.QuestionService;
import com.xczhihui.bxg.online.web.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 课程对应问题及回答信息业务层的实现类
 *
 * @author 康荣彩
 * @create 2016-08-29 16:46
 */
@Service
public class QuestionServiceImpl  extends OnlineBaseServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    /**
     * 根据课程id 查找到课程对应的问题
     * @param courseId
     * @return
     */
    public List<QuestionVo> getQuestionList(Integer courseId) {
         //获取到当前课程下的所有问题信息
         List<QuestionVo> questionVos= questionDao.getQuestionList(courseId);
         //然后根据问题找到对应的回答信息
        if (!CollectionUtils.isEmpty(questionVos)) {
            for (QuestionVo questionVo : questionVos) {
                  QuestionVo  answers =  this.getQuestionByPid(questionVo.getCourseId(),questionVo.getId());
                  if (answers != null)
                      questionVo.setAnswers(answers.getQuestionName());
            }
        }
        return questionVos;
    }

    /**
     * 根据问题ID  查找到对应的回答信息
     * @param pid
     * @return
     */
    public QuestionVo getQuestionByPid(Integer courseId,String pid) {
        return questionDao.getQuestionByPid(courseId,pid);
    }
}
