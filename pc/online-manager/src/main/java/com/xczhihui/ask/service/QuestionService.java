package com.xczhihui.ask.service;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.ask.vo.QuestionVo;
import com.xczhihui.bxg.common.util.bean.Page;

/**
 * 问答管理业务层接口类
 *
 * @author 王高伟
 * @create 2016-10-13 17:55:50
 */
public interface QuestionService {

	/**
	 * 	分页查找问题列表
	 * @param questionVo
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	 public Page<QuestionVo> findQuestionPage(QuestionVo questionVo, Integer pageNumber, Integer pageSize);
	 
	 /**
	  * 检查问题的状态是否已经解决
	  * @param questionVo
	  * @return
	  */
	 public boolean checkQuestionStatus(QuestionVo questionVo);
}
