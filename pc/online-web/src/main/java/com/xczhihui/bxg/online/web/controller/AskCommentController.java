package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.AskCommentService;
import com.xczhihui.bxg.online.web.vo.AskCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 回答相关
 * 
 * @author Haicheng Jiang
 */
@RestController
@RequestMapping(value = "/ask/comment")
public class AskCommentController {

	@Autowired
	private AskCommentService service;
	/**
	 * 查找评论/回复
	 * @param question_id
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/findComments")
	public ResponseObject findComments(String answer_id,Integer pageNumber,Integer pageSize,HttpSession s) {
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
		return ResponseObject.newSuccessResponseObject(service.findComments(u,answer_id, pageNumber, pageSize));
	}
	/**
	 * 新增评论/回复
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/addComment")
	public ResponseObject addAnswer(AskCommentVo vo,HttpSession s) {
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
		vo.setCreate_person(u.getLoginName());
		vo.setCreate_head_img(u.getSmallHeadPhoto());
		vo.setCreate_nick_name(u.getName());
		vo.setUser_id(u.getId());
		service.addComment(vo);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}
	/**
	 * 删除评论/回复
	 * @param comment_id
	 * @return
	 */
	@RequestMapping(value = "/deleteComment")
	public ResponseObject deleteComment(String comment_id,HttpServletRequest request) {
		//获取当前登陆用户信息
		OnlineUser u = (OnlineUser) UserLoginUtil.getLoginUser(request);
		service.deleteComment(request,u,comment_id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}
	/**
	 * 点赞评论
	 * @param comment_id
	 * @return
	 */
	@RequestMapping(value = "/praiseComment")
	public ResponseObject praiseComment(String comment_id,HttpSession s) {
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
		return ResponseObject.newSuccessResponseObject(service.addPraiseComment(u,comment_id));
	}

}
