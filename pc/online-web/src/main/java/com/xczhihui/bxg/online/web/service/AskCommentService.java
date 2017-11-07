package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.AskCommentVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 评论/回复相关
 * @author Haicheng Jiang
 */
public interface AskCommentService {
	/**
	 * 评论/回复列表
	 * @param question_id
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<AskCommentVo> findComments(OnlineUser u,String question_id,Integer pageNumber,Integer pageSize);
	/**
	 * 新增评论/回复
	 * @param vo
	 */
	public void addComment(AskCommentVo vo);
	/**
	 * 删除评论/回复
	 * @param comment_id
	 */
	public void deleteComment(HttpServletRequest request,OnlineUser u,String comment_id);
	/**
	 * 点赞评论
	 * @param comment_id
	 */
	public Map<String, Object> addPraiseComment(OnlineUser u,String comment_id);
}
