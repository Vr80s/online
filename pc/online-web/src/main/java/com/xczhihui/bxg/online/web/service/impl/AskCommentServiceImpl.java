package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.AskCommentDao;
import com.xczhihui.bxg.online.web.service.AskCommentService;
import com.xczhihui.bxg.online.web.vo.AskCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 评论/回复相关
 * @author Haicheng Jiang
 */
@Service
public class AskCommentServiceImpl implements AskCommentService {
	@Autowired
	private AskCommentDao dao;

	@Override
	public Page<AskCommentVo> findComments(OnlineUser u,String answer_id, Integer pageNumber, Integer pageSize) {
		return dao.findComments(u,answer_id, pageNumber, pageSize);
	}

	@Override
	public void addComment(AskCommentVo vo) {
		dao.addComment(vo);
	}

	@Override
	public Map<String, Object> addPraiseComment(OnlineUser u,String comment_id) {
		return dao.praiseComment(u,comment_id);
	}

	@Override
	public void deleteComment(OnlineUser u, String comment_id, User user) {
		dao.deleteComment(u,comment_id,user);
	}
}
