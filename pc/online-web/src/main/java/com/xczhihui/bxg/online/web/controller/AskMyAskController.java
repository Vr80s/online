package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.AskMyAskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 我的回答相关
 * @author Haicheng Jiang
 */
@RestController
@RequestMapping(value = "/ask/my")
public class AskMyAskController {

	@Autowired
	private AskMyAskService service;
	
	/**
	 * 我的提问
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/findMyQuestions")
	public ResponseObject findMyQuestions(String status, Integer pageNumber,Integer pageSize,HttpSession s) {
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
		return ResponseObject.newSuccessResponseObject(service.findMyQuestions(u, status, pageNumber, pageSize));
	}
	/**
	 * 我的回答
	 * @param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/findMyAnswers")
	public ResponseObject findMyAnswers(Boolean accepted, Integer pageNumber, Integer pageSize,HttpSession s) {
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
		accepted = accepted == null ? false : accepted;
		return ResponseObject.newSuccessResponseObject(service.findMyAnswers(accepted, u, pageNumber, pageSize));
	}
	/**
	 * 我的收藏
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/findMyCollections")
	public ResponseObject findMyCollections(String status, Integer pageNumber,Integer pageSize,HttpSession s) {
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
		return ResponseObject.newSuccessResponseObject(service.findMyCollections(u, status, pageNumber, pageSize));
	}

	/**
	 * 我的提问 未双元准备接口，不要随意改动
	 * @param loginName
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/findMyQuestionInfo")
	public ResponseObject findMyQuestionInfo(String loginName,String status,Integer pageNumber,Integer pageSize){
		return ResponseObject.newSuccessResponseObject(service.findMyQuestionInfo(loginName, status, pageNumber, pageSize));
	}
}
