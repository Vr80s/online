package com.xczhihui.ask.service.impl;

/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.ask.vo.QuestionVo;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.ask.dao.QuestionDao;
import com.xczhihui.ask.service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 问答管理业务层的实现类
 *
 * @author 王高伟
 * @create 2016-10-13 18:00:46
 */
@Service
public class QuestionServiceImpl extends OnlineBaseServiceImpl implements
		QuestionService {

	@Autowired
	private QuestionDao questionDao;

	@Override
	public Page<QuestionVo> findQuestionPage(QuestionVo questionVo,
			Integer pageNumber, Integer pageSize) {
		Page<QuestionVo> page = questionDao.findQuestionPage(questionVo,
				pageNumber, pageSize);
		return page;
	}

	@Override
	public boolean checkQuestionStatus(QuestionVo questionVo) {
		// TODO Auto-generated method stub
		return questionDao.checkQuestionStatus(questionVo);
	}

}
