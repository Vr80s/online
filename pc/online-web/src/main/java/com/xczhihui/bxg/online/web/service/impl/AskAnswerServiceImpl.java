package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.AskAnswerDao;
import com.xczhihui.bxg.online.web.dao.UserCenterDao;
import com.xczhihui.bxg.online.web.service.AskAnswerService;
import com.xczhihui.bxg.online.web.vo.AskAnswerVo;
import com.xczhihui.bxg.online.web.vo.AskQuestionAndAnswerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 回答相关
 * @author Haicheng Jiang
 */
@Service
public class AskAnswerServiceImpl implements AskAnswerService {
	
	@Autowired
	private AskAnswerDao dao;

	@Autowired
	private UserCenterDao userDao;

	@Override
	public void addOfficialAnswer(AskAnswerVo askAnswerVo,OnlineUser u) {
		//u =  userDao.get(u.getId(),OnlineUser.class);
		askAnswerVo.setCreate_head_img(u.getSmallHeadPhoto());
		dao.addOfficialAnswer(askAnswerVo);
	}

	@Override
	public Map<String,Object> findOfficialAnswer(String question_id,String menu_id,String userId) {
		Map<String,Object> returnMap = new HashMap<>();
		AskAnswerVo askAnswerVo = dao.findOfficialAnswer(question_id);
		if(userId.equals("")){
			returnMap.put("hasRight",false);
		}else{
			List<Map<String, Object>> list = dao.findMenuIdByUser(userId);
			if(list!=null && list.size()>0){
				Integer menuId =list.get(0).get("menu_id") != null ? (int)list.get(0).get("menu_id") : -1;
				//String menuId = String.valueOf((int) list.get(0).get("menu_id"));
				if(menu_id.equalsIgnoreCase(String.valueOf(menuId))){
					returnMap.put("hasRight",true);
				}else{
					returnMap.put("hasRight",false);
				}
			}
		}
		returnMap.put("askAnswer",askAnswerVo);
		return returnMap;
	}

	@Override
	public void updateOfficialAnswer(String id, String content) {
		dao.updateOfficialAnswer(id,content);
	}

	@Override
	public Page<AskAnswerVo> findAnswers(OnlineUser u,AskAnswerVo vo, String order, String sort, Integer pageNumber,
			Integer pageSize) {
		return dao.findAnswers(u,vo, order, sort, pageNumber, pageSize);
	}

	@Override
	public void addAnswer(AskAnswerVo vo,OnlineUser u) {
		u =  userDao.get(u.getId(),OnlineUser.class);
		vo.setCreate_head_img(u.getSmallHeadPhoto());
		dao.addAnswer(vo,u);
	}

	@Override
	public List<AskAnswerVo> findNiceAnswers(OnlineUser u,String question_id) {
		return dao.findNiceAnswers(u,question_id);
	}

	@Override
	public boolean addAcceptAnswer(String answer_id, String userId) {
		return dao.acceptAnswer(answer_id, userId);
	}

	@Override
	public List<Map<String, Object>> findAnswersWeekRankingList(Integer sum) {
		return dao.findAnswersWeekRankingList(sum);
	}

	@Override
	public Map<String, Object> addPraiseAnswer(String answer_id, String loginName) {
		return dao.praiseAnswer(answer_id, loginName);
	}
	
	@Override
	public boolean addCollection(String question_id, String create_person,String userId) {
		return dao.collection(question_id, create_person,userId);
	}

	/**
	 * 删除回答信息
	 * @param answerId 回答信息的id号
	 * @return
	 */
	@Override
    public String deleteAnswerById(HttpServletRequest request, OnlineUser u, String  answerId){
		return  dao.deleteAnswerById(request,u,answerId,"");
	}

	/**
	 * 查找点赞数最多的回答以及回答的问题信息
	 * 重点：为院校项目准备的接口  不要随便改动
	 * @return
	 */
	@Override
    public  Page<AskQuestionAndAnswerVo>   findMaxPraiseAnswer(Integer pageNumber, Integer pageSize){
		 return dao.findMaxPraiseAnswer(pageNumber,pageSize);
	}
}
