package com.xczhihui.bxg.online.web.service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.AskQuestionVo;

/**
 * 我的问答相关
 * 
 * @author Haicheng Jiang
 */
public interface AskMyAskService {
	/**
	 * 我的提问
	 * @param userId
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<AskQuestionVo> findMyQuestions(OnlineUser u, String status, Integer pageNumber,
			Integer pageSize);
	/**
	 * 我的回答
	 * @param accepted
	 * @param u
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<AskQuestionVo> findMyAnswers(Boolean accepted,OnlineUser u, Integer pageNumber, Integer pageSize);
	/**
	 * 我的收藏
	 * @param u
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<AskQuestionVo> findMyCollections(OnlineUser u, String status, Integer pageNumber,
			Integer pageSize);


	/**
	 * 我的提问 未双元准备接口，不要随意改动
	 * @param loginName
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public  Page<AskQuestionVo> findMyQuestionInfo(String loginName,String status,Integer pageNumber,Integer pageSize);
}
