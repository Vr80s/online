package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.AskAnswerVo;
import com.xczhihui.bxg.online.web.vo.AskQuestionAndAnswerVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 回答相关
 * @author Haicheng Jiang
 */
public interface AskAnswerService {
	/**
	 * 添加问题的官方回答
	 * @param askAnswerVo
	 */
	public void addOfficialAnswer(AskAnswerVo askAnswerVo,OnlineUser u);
	/**
	 * 查询问题的官方回答
	 * @param question_id
	 * @return
	 */
	public Map<String, Object> findOfficialAnswer(String question_id, String menu_id, String userId);
	/**
	 * 修改问题的官方回答
	 * @param id
	 * @param content
	 */
	public void updateOfficialAnswer(String id,String content);
	/**
	 * 查询回答列表
	 * @param vo
	 * @param order
	 * @param sort
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<AskAnswerVo> findAnswers(OnlineUser u,AskAnswerVo vo,String order,String sort,Integer pageNumber,Integer pageSize);
	/**
	 * 查询精彩回答
	 * @param question_id
	 * @return
	 */
	public List<AskAnswerVo> findNiceAnswers(OnlineUser u,String question_id);
	/**
	 * 查询周回答排行榜
	 * @param sum
	 * @return
	 */
	public List<Map<String, Object>> findAnswersWeekRankingList(Integer sum);
	/**
	 * 新增回答
	 * @param vo
	 */
	public void addAnswer(AskAnswerVo vo,OnlineUser u);
	/**
	 * 采纳/取消采纳为最佳答案
	 * @param answer_id
	 * @param userId
	 * @return 
	 */
	public boolean addAcceptAnswer(String answer_id, String userId);
	/**
	 * 点赞回答
	 * @param answer_id
	 * @param create_person
	 */
	public Map<String, Object> addPraiseAnswer(String answer_id, String loginName);
	/**
	 * 收藏/取消收藏
	 * @param question_id
	 * @param create_person
	 * @return
	 */
	public boolean addCollection(String question_id, String create_person,String userId);

	/**
	 * 删除回答信息
	 * @param answerId 回答信息的id号
	 * @return
	 */
	public String deleteAnswerById(HttpServletRequest request,OnlineUser u,String  answerId);


	/**
	 * 查找点赞数最多的回答以及回答的问题信息
	 * 重点：为院校项目准备的接口  不要随便改动
	 * @return
	 */
	public  Page<AskQuestionAndAnswerVo>   findMaxPraiseAnswer(Integer pageNumber, Integer pageSize);
}
