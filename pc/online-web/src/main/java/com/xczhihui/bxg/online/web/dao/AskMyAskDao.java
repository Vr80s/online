package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.AskAnswerVo;
import com.xczhihui.bxg.online.web.vo.AskQuestionVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 博问答，我的问答相关
 * @author Haicheng Jiang
 */
@Repository
public class AskMyAskDao extends SimpleHibernateDao {
	/**
	 * 我的提问
	 * @param  u
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public  Page<AskQuestionVo> findMyQuestions(OnlineUser u,String status,Integer pageNumber,Integer pageSize){
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 5 : pageSize;
		
		status =  (!StringUtils.hasText(status) || !"2".equals(status)) ? " and status != '2' " : " and status = '2' ";
		String sql = "select * from oe_ask_question where user_id = :userId "+status+" and is_delete = 0 order by create_time desc ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", u.getId());
		
		Page<AskQuestionVo> qs = this.findPageBySQL(sql, params, AskQuestionVo.class, pageNumber, pageSize);
		this.setAskAnswerVo(qs,u.getLoginName());
		
		return qs;
	}
	/**
	 * 我的回答
	 * @param u
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public  Page<AskQuestionVo> findMyAnswers(Boolean accepted,OnlineUser u,Integer pageNumber,Integer pageSize){
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 5 : pageSize;
		String sql = "select distinct t1.* from oe_ask_question t1,oe_ask_answer t2 where t2.question_id = t1.id "
				+ " and t2.user_id = :userId and t2.accepted=:accepted and t1.is_delete = 0   order by create_time desc ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", u.getId());
		params.put("accepted", accepted);
		
		Page<AskQuestionVo> qs = this.findPageBySQL(sql, params, AskQuestionVo.class, pageNumber, pageSize);
		this.setAskAnswerVo(qs,u.getLoginName());
		
		return qs;
	}
	/**
	 * 我的收藏
	 * @param u
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public  Page<AskQuestionVo> findMyCollections(OnlineUser u,String status,Integer pageNumber,Integer pageSize){
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 5 : pageSize;
		
		status =  (!StringUtils.hasText(status) || !"2".equals(status)) ? " and t1.status != '2' " : " and t1.status = '2' ";
		String sql = "select t1.* from oe_ask_question t1,oe_ask_collection t2 where t1.id = t2.question_id and "
				+ " t2.user_id = :userId "+status+" and t1.is_delete = 0 order by create_time desc ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", u.getId());
		
		Page<AskQuestionVo> qs = this.findPageBySQL(sql, params, AskQuestionVo.class, pageNumber, pageSize);
		this.setAskAnswerVo(qs,u.getLoginName());
		
		return qs;
	}
	
	public void setAskAnswerVo(Page<AskQuestionVo> qs,String loginName){
		for (AskQuestionVo q : qs.getItems()) {
			String praise = qs != null ? "find_in_set('"+loginName+"',praise_login_names) > 0 and praise_login_names is not null" : "false";
			String sql = "select *,"+praise+" as praise from oe_ask_answer where question_id = ? and is_delete = 0  and  answer_type =false  order by praise_sum desc  ";
			List<AskAnswerVo> as = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,
					BeanPropertyRowMapper.newInstance(AskAnswerVo.class), q.getId());
			if (as != null && as.size() > 0) {
				q.setAskAnswerVo(as.get(0));
			}
		}
	}

	/**
	 * 我的提问 未双元准备接口，不要随意改动
	 * @param loginName
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public  Page<AskQuestionVo> findMyQuestionInfo(String loginName,String status,Integer pageNumber,Integer pageSize){
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 5 : pageSize;

		status =  (!StringUtils.hasText(status) || !"2".equals(status)) ? " and status != '2' " : " and status = '2' ";
		String sql = "select * from oe_ask_question where create_person = :loginName "+status+" and is_delete = 0 order by create_time desc ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);

		Page<AskQuestionVo> qs = this.findPageBySQL(sql, params, AskQuestionVo.class, pageNumber, pageSize);
		this.setAskAnswerVo(qs,loginName);

		return qs;
	}
}
