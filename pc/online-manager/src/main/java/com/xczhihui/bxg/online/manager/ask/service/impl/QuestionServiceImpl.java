package com.xczhihui.bxg.online.manager.ask.service.impl;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.ask.dao.QuestionDao;
import com.xczhihui.bxg.online.manager.ask.service.QuestionService;
import com.xczhihui.bxg.online.manager.ask.vo.QuestionVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 问答管理业务层的实现类
 *
 * @author 王高伟
 * @create 2016-10-13 18:00:46
 */
@Service
public class QuestionServiceImpl  extends OnlineBaseServiceImpl implements QuestionService {

	@Autowired
    private QuestionDao questionDao;
	
	@Override
	public Page<QuestionVo> findQuestionPage(QuestionVo questionVo, Integer pageNumber, Integer pageSize) {
        Page<QuestionVo> page = questionDao.findQuestionPage(questionVo, pageNumber, pageSize);
        return page;
	}

	@Override
	public boolean checkQuestionStatus(QuestionVo questionVo) {
		// TODO Auto-generated method stub
		return questionDao.checkQuestionStatus(questionVo);
	}

}
