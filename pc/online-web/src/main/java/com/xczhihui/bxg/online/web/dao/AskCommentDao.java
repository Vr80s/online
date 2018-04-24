package com.xczhihui.bxg.online.web.dao;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.web.vo.AskCommentVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 博问答，评论/回复相关
 * @author Haicheng Jiang
 */
@Repository
public class AskCommentDao extends SimpleHibernateDao {
	/**
	 * 查询评论/回复列表
	 * @param answer_id
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public  Page<AskCommentVo> findComments(OnlineUser u,String answer_id,Integer pageNumber,Integer pageSize){
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 5 : pageSize;
		String praise = u != null ? "find_in_set('"+u.getLoginName()+"',praise_login_names) > 0 and praise_login_names is not null" : "false";
		String delete = u != null ? "user_id='"+u.getId()+"'" : "false";
		String sql = "select *,"+praise+" as praise,"+delete+" as delete_button from oe_ask_comment where answer_id=:answer_id and is_delete=0 order by create_time ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("answer_id", answer_id);
		return this.findPageBySQL(sql,paramMap, AskCommentVo.class, pageNumber, pageSize);
	}
	/**
	 * 新增评论/回复
	 * @param cv
	 */
	public void addComment(AskCommentVo cv){
		cv.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		String sql = "insert into oe_ask_comment (id,answer_id,content,create_nick_name,"
				+ "create_head_img,create_person,target_person,target_nike_name,user_id,target_user_id) "
				+ "values (:id,:answer_id,:content,:create_nick_name,:create_head_img,"
				+ ":create_person,:target_person,:target_nike_name,:user_id,:target_user_id) ";
		this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(cv));

		//增加被评论问题的评论数
		sql = "update oe_ask_answer  set comment_sum=(comment_sum+1)  where   id = ?";
		this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, cv.getAnswer_id());
	}
	/**
	 * 删除评论/回复
	 * @param comment_id
	 */
	public void deleteComment(HttpServletRequest request,OnlineUser u,String comment_id){
		String sql = "select create_person,answer_id,user_id from oe_ask_comment where id = ? and is_delete =0";
		List<Map<String, Object>> check = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql, comment_id);
		if (check == null || check.size() <= 0) {
			throw new RuntimeException(String.format("不存在"));
		}
		User user= (User) request.getSession().getAttribute("_adminUser_");
		if(user == null){ //非管理员
			if(!check.get(0).get("user_id").toString().equals(u.getId())){
				throw new RuntimeException("您不是此评论的评论者，无权删除！");
			}
			sql = "delete from  oe_ask_comment  where user_id=? and id = ? and is_delete=0";
			this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, u.getId(),comment_id);
		}else{
			sql = " UPDATE oe_ask_comment set is_delete =1   where   id = ?  and is_delete=0";
			this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,comment_id);
		}

		//减少被评论问题的评论数
		sql = "update oe_ask_answer  set comment_sum=(comment_sum-1)  where   id = ?";
		this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, check.get(0).get("answer_id"));

	}
	
	/**
	 * 点赞评论
	 * @param comment_id
	 */
	public Map<String, Object> praiseComment(OnlineUser u,String comment_id) {
		String sql = "select praise_login_names,praise_sum from oe_ask_comment where id = ? and is_delete = 0 ";
		List<AskCommentVo> as = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,
				BeanPropertyRowMapper.newInstance(AskCommentVo.class), comment_id);
		if (as == null || as.size() <= 0) {
			throw new RuntimeException("根据参数comment_id:“"+comment_id+"”找不到信息！");
		}
		
		boolean b = false;
		int sum = as.get(0).getPraise_sum();
		String praise_login_names = as.get(0).getPraise_login_names();
		
		if (praise_login_names != null && praise_login_names.contains(u.getLoginName())) {
			if (praise_login_names.endsWith(","+u.getLoginName())) {
				praise_login_names = praise_login_names.replace(","+u.getLoginName(), "");
			} else if(praise_login_names.equals(u.getLoginName())) {
				praise_login_names = null;
			} else {
				praise_login_names = praise_login_names.replace(u.getLoginName()+",", "");
			}
			sum --;
		} else {
			if (StringUtils.hasText(praise_login_names)) {
				praise_login_names += (","+u.getLoginName());
			} else {
				praise_login_names = u.getLoginName();
			}
			sum ++;
			b = true;
		}
		
		this.getNamedParameterJdbcTemplate().getJdbcOperations().
			update("update oe_ask_comment set praise_sum=?,praise_login_names = ? where id = ?", sum,praise_login_names,comment_id);

		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("praise", b);
		mp.put("sum", sum);
		return mp;
	}
	
}
