package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.AskMyAskDao;
import com.xczhihui.bxg.online.web.service.AskMyAskService;
import com.xczhihui.bxg.online.web.vo.AskQuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 我的问答相关
 * @author Haicheng Jiang
 */
@Service
public class AskMyAskServiceImpl implements AskMyAskService {
	
	@Autowired
	private AskMyAskDao dao;

	@Override
	public Page<AskQuestionVo> findMyQuestions(OnlineUser u, String status, Integer pageNumber,
			Integer pageSize) {
		return dao.findMyQuestions(u, status, pageNumber, pageSize);
	}

	@Override
	public Page<AskQuestionVo> findMyAnswers(Boolean accepted,OnlineUser u, Integer pageNumber, Integer pageSize) {
		return dao.findMyAnswers(accepted,u, pageNumber, pageSize);
	}

	@Override
	public Page<AskQuestionVo> findMyCollections(OnlineUser u, String status, Integer pageNumber,
			Integer pageSize) {
		return dao.findMyCollections(u, status, pageNumber, pageSize);
	}


	/**
	 * 我的提问 未双元准备接口，不要随意改动
	 * @param loginName
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@Override
    public  Page<AskQuestionVo> findMyQuestionInfo(String loginName, String status, Integer pageNumber, Integer pageSize){
		return  dao.findMyQuestionInfo(loginName,status,pageNumber,pageSize);
	}
}
