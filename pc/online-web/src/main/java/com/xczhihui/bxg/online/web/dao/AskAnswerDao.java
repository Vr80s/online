package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.web.vo.AskAnswerVo;
import com.xczhihui.bxg.online.web.vo.AskQuestionAndAnswerVo;
import com.xczhihui.bxg.online.web.vo.AskQuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 博问答，回答相关
 * 
 * @author Haicheng Jiang
 */
@Repository
public class AskAnswerDao extends SimpleHibernateDao {

	@Autowired
	private  ASKQuestionListDao  questionListDao;

	/**
	 * 添加问题的官方回答
	 * @param askAnswerVo
	 */
	public void addOfficialAnswer(AskAnswerVo askAnswerVo) {

		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", askAnswerVo.getQuestion_id());

		askAnswerVo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		askAnswerVo.setAnswer_type(true);
		askAnswerVo.setCreate_nick_name("官方回复");
		String sql="";
        if(askAnswerVo.getMethodType()==0){ //添加官方回复
			//更新问题的回答数
			String updateSql ="update oe_ask_question set answer_sum=(answer_sum+1) where id =:id";
			this.getNamedParameterJdbcTemplate().update(updateSql,paramMap);
			sql = "insert into oe_ask_answer (id,question_id,create_time,content,"
					+ "answer_type,create_person,create_nick_name,user_id) "
					+ "values (:id,:question_id,now(),:content,:answer_type,:create_person,:create_nick_name,:user_id)";
			this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(askAnswerVo));
		}else{ //编辑官方回复
			paramMap.put("content", askAnswerVo.getContent());
			sql = " update oe_ask_answer set content =:content  where id =:id and answer_type=1";
			this.getNamedParameterJdbcTemplate().update(sql,paramMap);
		}



	}
	/**
	 * 查询问题的官方回答
	 * @param question_id
	 * @return
	 */
	public AskAnswerVo findOfficialAnswer(String question_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,content,text,create_nick_name,");
		sql.append(" create_head_img ");
		sql.append(" from oe_ask_answer where question_id=:id and answer_type=1");
		sql.append(" order by create_time desc limit 1");
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", question_id);
		List<AskAnswerVo> list = this.findEntitiesByJdbc(AskAnswerVo.class,sql.toString(),paramMap);
		return list.size()>0? list.get(0) : null;
	}

	/**
	 * 根据用户查询学科权限id
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> findMenuIdByUser(String userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id,menu_id ");
		sql.append(" from oe_user");
		sql.append(" where id =:userId");
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("userId",userId);
		return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(),paramMap);
	}
	/**
	 * 修改问题的官方回答
	 * @param id
	 * @param content
	 */
	public void updateOfficialAnswer(String id,String content) {
		StringBuffer sql = new StringBuffer();
		Map<String,Object> paramMap = new HashMap<>();
		sql.append(" update oe_ask_answer set content =:content  where id =:id and answer_type=1");
		paramMap.put("id", id);
		paramMap.put("content", content);
		this.getNamedParameterJdbcTemplate().update(sql.toString(), paramMap);
	}
	/**
	 * 查询回答列表
	 * 
	 * @param an
	 * @param order
	 * @param sort
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<AskAnswerVo> findAnswers(OnlineUser u,AskAnswerVo an, String order, String sort, Integer pageNumber,
			Integer pageSize) {
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 5 : pageSize;

		String praise = u != null
				? "find_in_set('" + u.getLoginName() + "',a.praise_login_names) > 0 and a.praise_login_names is not null"
				: "false";
		String creator = u != null ? "a.user_id='" + u.getId() + "'" : "false";
		//投诉状态
//		String accused = u != null ? "find_in_set(a.id,ac.ac.target_id) > 0 and ac.target_id is not null"
//				:"false";
		String sql = "select  a.id,ac.target_id as accused,ac.`status` as handleStatus,a.question_id,a.text,a.content,a.comment_sum,a.praise_sum,a.praise_login_names,a.accepted,a.copyright,a.create_nick_name,a.create_head_img," +
				     " a.create_person,a.create_time,a.is_delete, ac.create_person as accusePerson," + praise + " as praise,"+creator+" as creator  from oe_ask_answer a  left join  oe_ask_accuse ac   on a.id = ac.target_id  where "+
				     " a.is_delete=0  and  answer_type=false  ";
		String and = "";
		Map<String, Object> params = new HashMap<String, Object>();

		if (StringUtils.hasText(an.getQuestion_id())) {
			and += (" and a.question_id=:question_id ");
			params.put("question_id", an.getQuestion_id());
		}

		sql += and;
		sql += (" order by handleStatus asc ");

		Page<AskAnswerVo> rs = this.findPageBySQL("select z.* from ("+ sql+") as z   group by z.id  order by " + order + " " + sort, params, AskAnswerVo.class, pageNumber, pageSize);
		for (AskAnswerVo askAnswerVo :rs.getItems())
		{
			askAnswerVo.setAccused(askAnswerVo.getAccused()!=null && askAnswerVo.getHandleStatus() == 0 ?true :false);
		}

		return rs;
	}

	/**
	 * 查询精彩回答列表
	 * 
	 * @param question_id
	 * @return
	 */
	public List<AskAnswerVo> findNiceAnswers(OnlineUser u,String question_id) {
		String praise = u != null
				? "find_in_set('" + u.getLoginName() + "',a.praise_login_names) > 0 and a.praise_login_names is not null"
				: "false";
		String creator = u != null ? "a.user_id='" + u.getId() + "'" : "false";

		//投诉状态
//		String accused = u != null ? "find_in_set('" + u.getLoginName() +"',ac.create_person) > 0 and ac.create_person is not null"
//				:"false";
		String sql = "select z.*  from ( select a.id,ac.target_id as accused,ac.`status` as handleStatus,a.question_id,a.text,a.content,a.comment_sum,a.praise_sum,a.praise_login_names,a.accepted,a.copyright,a.create_nick_name," +
				    " a.create_head_img,a.create_person,a.create_time,a.is_delete," + praise + " as praise," + creator + " as creator " +
				    " from oe_ask_answer a  left join  oe_ask_accuse ac   on a.id = ac.target_id  where a.question_id = ?  and  	( a.accepted > 0 or a.praise_sum > 0 or a.comment_sum >0)  " +
				    " and a.is_delete=0  and  answer_type=false  order by handleStatus asc  ) as z  group by z.id  order by z.accepted desc, z.praise_sum desc,z.comment_sum desc limit 0,5 ";

		List<AskAnswerVo> askAnswerVos= this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,
				BeanPropertyRowMapper.newInstance(AskAnswerVo.class), question_id);
		for (AskAnswerVo askAnswerVo :askAnswerVos)
		{
			askAnswerVo.setAccused(askAnswerVo.getAccused()!=null && askAnswerVo.getHandleStatus() ==0 ?true :false);
		}
		return  askAnswerVos;
	}

	/**
	 * 查询周回答排行榜
	 * 
	 * @param sum
	 * @return
	 */
	public List<Map<String, Object>> findAnswersWeekRankingList(Integer sum) {

		List<Map<String, Object>> rs = new ArrayList<Map<String, Object>>();

		sum = sum == null ? 5 : sum;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1); // 解决周日会出现 并到下一周的情况
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String md = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 00:00:00";

		String sql = "select t1.login_name,t1.name as nike_name,t1.small_head_photo,count(1) as answer_sum from oe_user t1,oe_ask_answer t2 "
				+ "where t1.id=t2.user_id and t2.create_time>= ? and t1.is_delete=0 and t1.status=0   and  t2.answer_type=false  "
				+ "group by t1.id,t1.name,t1.small_head_photo order by answer_sum desc limit 0,?";
		List<Map<String, Object>> vs = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql, md,
				sum);
		for (Map<String, Object> v : vs) {
			rs.add(v);
		}

		return rs;
	}

	/**
	 * 新增回答
	 * 
	 * @param an
	 */
	public void addAnswer(AskAnswerVo an,OnlineUser u) {
	    an.setUser_id(u.getId());
		an.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		String sql = "insert into oe_ask_answer (id,question_id,content,text,copyright,create_nick_name,create_head_img,create_person,user_id) "
				+ " values (:id,:question_id,:content,:text,:copyright,:create_nick_name,:create_head_img,:create_person,:user_id) ";
		this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(an));

		List<AskQuestionVo> questions = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(
				"select status from oe_ask_question where id = ? and  is_delete=0 ",
				BeanPropertyRowMapper.newInstance(AskQuestionVo.class), an.getQuestion_id());

		AskQuestionVo question = questions.get(0);

		if("0".equals(question.getStatus())){
				this.getNamedParameterJdbcTemplate().getJdbcOperations().update(
				"update oe_ask_question set answer_sum=(answer_sum+1),status = ? where id = ?", "1",an.getQuestion_id());
		}else {
				this.getNamedParameterJdbcTemplate().getJdbcOperations().update(
						"update oe_ask_question set answer_sum=(answer_sum+1) where id = ?", an.getQuestion_id());
		}
	}

	/**
	 * 采纳/取消采纳为最佳答案
	 * 
	 * @param answer_id
	 * @param userId
	 * @return
	 */
	public boolean acceptAnswer(String answer_id, String userId) {
		String sql = "select a.id,a.accepted,a.question_id from oe_ask_answer a left join oe_ask_question q on  a.question_id = q.id  where a.id = ? and  q.user_id = ? and a.is_delete=0 and q.is_delete=0 ";
		List<AskAnswerVo> check = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,
				BeanPropertyRowMapper.newInstance(AskAnswerVo.class), answer_id, userId);
		if (check.size() <= 0) {
			throw new RuntimeException("您不是此问题的主人！");
		}

		String status = "1";
		boolean b = false;
		if (!check.get(0).getAccepted()) {

			sql = "select create_nick_name from oe_ask_answer where question_id = ? and id != ? and accepted = true  and is_delete=0";
			List<AskAnswerVo> check2 = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,
					BeanPropertyRowMapper.newInstance(AskAnswerVo.class), check.get(0).getQuestion_id(),
					check.get(0).getId());

			if (check2.size() > 0) {
				throw new RuntimeException(String.format("您已经采纳了“%s”的回答！", check2.get(0).getCreate_nick_name()));
			}

			b = true;
			status = "2";
		}

		this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update("update oe_ask_answer set accepted = ? where id = ? and is_delete=0", b, answer_id);

		this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update("update oe_ask_question set status = ? where id = ? and is_delete=0", status, check.get(0).getQuestion_id());

		return b;
	}

	/**
	 * 点赞回答
	 * 
	 * @param answer_id
	 * @param create_person
	 */
	public Map<String, Object> praiseAnswer(String answer_id, String create_person) {
		String sql = "select praise_login_names,praise_sum from oe_ask_answer where id = ? and is_delete=0 ";
		List<AskAnswerVo> as = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,
				BeanPropertyRowMapper.newInstance(AskAnswerVo.class), answer_id);

		boolean b = false;
		int sum = as.get(0).getPraise_sum();
		String praise_login_names = as.get(0).getPraise_login_names();

		if (praise_login_names != null && praise_login_names.contains(create_person)) {
			if (praise_login_names.endsWith("," + create_person)) {
				praise_login_names = praise_login_names.replace("," + create_person, "");
			} else if (praise_login_names.equals(create_person)) {
				praise_login_names = null;
			} else {
				praise_login_names = praise_login_names.replace(create_person + ",", "");
			}
			sum--;
		} else {
			if (StringUtils.hasText(praise_login_names)) {
				praise_login_names += ("," + create_person);
			} else {
				praise_login_names = create_person;
			}
			sum++;
			b = true;
		}
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("sum",sum);
		paramMap.put("praise_login_names",praise_login_names);
		paramMap.put("answer_id",answer_id);
		this.getNamedParameterJdbcTemplate().update("update oe_ask_answer set praise_sum=:sum,praise_login_names =:praise_login_names where id =:answer_id and is_delete=0",paramMap);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("praise", b);
		mp.put("sum", sum);
		
		return mp;
	}

	/**
	 * 收藏/取消收藏
	 * 
	 * @param question_id
	 * @param create_person
	 * @return
	 */
	public boolean collection(String question_id, String create_person,String userId) {
		String checkSql = "select * from oe_ask_collection where question_id=? and create_person=?";
		List<Map<String, Object>> check = this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForList(checkSql, question_id, create_person);
		if (check == null || check.size() <= 0) {
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			this.getNamedParameterJdbcTemplate().getJdbcOperations().update(
					"insert into oe_ask_collection (id,question_id,create_person,user_id) values (?,?,?,?)", id, question_id,
					create_person,userId);
			return true;
		} else {
			this.getNamedParameterJdbcTemplate().getJdbcOperations().update("delete from oe_ask_collection where id=? and create_person =?",
					check.get(0).get("id").toString(),create_person);
			return false;
		}
	}

	/**
	 * 删除回答信息
	 * 
	 * @param answerId
	 *            回答信息的id号
	 * @param questionId
	 *            问题信息的id号
	 * @param user
	 * @return
	 */
	public String deleteAnswerById(OnlineUser u, String answerId, String questionId, User user) {

		//查看当前用户是管理员否,不为空是管理员，否则，普通用户

		String deleteSql = "";
		// 删除某条回答信息及次回答下的所有评论信息
		if (!StringUtils.hasText(questionId)) {
			String sql = "select user_id, create_person,question_id,accepted from oe_ask_answer where id = ?  and is_delete=0";
			List<Map<String, Object>> check = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql,
					answerId);
			if (check == null || check.size() <= 0) {
				throw new RuntimeException(String.format("不存在"));
			}
            if(user == null){ //非管理员 物理删除
				if (!check.get(0).get("user_id").toString().equals(u.getId())) {
					throw new RuntimeException("您不是此信息的回答者，无权删除！");
				}
				//1、 删除此回答下的所有评论信息
				deleteSql = "delete from     oe_ask_comment   where   answer_id =?  ";
				this.getNamedParameterJdbcTemplate().getJdbcOperations().update(deleteSql, answerId);
				//2、删除回答信息前，先修改问题下的回答数
				updateQuestionById(check);
				//3、删除回答信息
				deleteSql = "delete from   oe_ask_answer   where  id =?  ";
				this.getNamedParameterJdbcTemplate().getJdbcOperations().update(deleteSql, answerId);

				//删除投诉表中对应投诉信息
				String updateSql ="delete from   oe_ask_accuse  where  target_id =?  and  target_type=1";
				this.getNamedParameterJdbcTemplate().getJdbcOperations().update(updateSql,answerId);
			}else{  //管理员逻辑删除
				//1、 删除此回答下的所有评论信息
				deleteSql = "update    oe_ask_comment set is_delete=1   where   answer_id =?  ";
				this.getNamedParameterJdbcTemplate().getJdbcOperations().update(deleteSql, answerId);
				//2、删除回答信息前，先修改问题下的回答数
				updateQuestionById(check);
				//3、删除回答信息
				deleteSql = "update  oe_ask_answer  set is_delete=1  where  id =?  ";
				this.getNamedParameterJdbcTemplate().getJdbcOperations().update(deleteSql, answerId);

				//改变投诉表中此问题投诉信息状态
				String updateSql ="update  oe_ask_accuse set status=1    where  target_id =?  and  target_type=1";
				this.getNamedParameterJdbcTemplate().getJdbcOperations().update(updateSql,answerId);
			}



		} else { // 删除某问题下的所有回答以及回答下的所有评论

			// 1、查询此问题下的所有回答信息,然后循环删除所有回答及回答信息下面的评论
			String querySql = "select *  from oe_ask_answer where question_id = ?  and  is_delete=0 ";
			List<AskAnswerVo> answerVos = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(querySql,
					BeanPropertyRowMapper.newInstance(AskAnswerVo.class), questionId);
			if ( answerVos.size()> 0) {
				// 2、根据回答id，删除所对应的所有评论信息，接着再删除所有回答信息
				if (!CollectionUtils.isEmpty(answerVos) && answerVos.size() > 0) {
					for (AskAnswerVo as : answerVos) {
						if(user == null) { //非管理员 物理删除
							// 1、删除此回答下的所有评论信息
							deleteSql = "delete from  oe_ask_comment    where   answer_id =?  ";
							this.getNamedParameterJdbcTemplate().getJdbcOperations().update(deleteSql, as.getId());
							// 2、删除回答信息
							deleteSql = "delete from    oe_ask_answer where  id =?  ";
							this.getNamedParameterJdbcTemplate().getJdbcOperations().update(deleteSql, as.getId());

							//改变投诉表中此问题投诉信息状态
							String updateSql ="delete from  oe_ask_accuse  where  target_id =?  and  target_type=1";
							this.getNamedParameterJdbcTemplate().getJdbcOperations().update(updateSql,as.getId());
						}else{ //管理员删除   逻辑删除
							// 1、删除此回答下的所有评论信息
							deleteSql = "update    oe_ask_comment set is_delete=1    where   answer_id =?  ";
							this.getNamedParameterJdbcTemplate().getJdbcOperations().update(deleteSql, as.getId());
							// 2、删除回答信息
							deleteSql = "update    oe_ask_answer set is_delete=1    where  id =?  ";
							this.getNamedParameterJdbcTemplate().getJdbcOperations().update(deleteSql, as.getId());

							//改变投诉表中此问题投诉信息状态
							String updateSql ="update  oe_ask_accuse set status=1    where  target_id =?  and  target_type=1";
							this.getNamedParameterJdbcTemplate().getJdbcOperations().update(updateSql,as.getId());
						}

					}
				}

			}

		}

		return "操作成功";

	}


	/**
	 * 修改问题的回答数
	 * @param check
	 *            问题信息的id号
	 * @return
	 */
	public void updateQuestionById(List<Map<String, Object>> check) {
		//查看被删除的回答是否是被采纳的回答信息，如果被采纳，删除得改变问题的采纳状态
		String querySql="";
		if("true".equals(check.get(0).get("accepted").toString())){
			 querySql = "update  oe_ask_question   set answer_sum = (answer_sum - 1), status=0  where id = ? ";
		}else{
			 querySql = "update  oe_ask_question   set answer_sum = (answer_sum - 1)  where id = ? ";
		}
		this.getNamedParameterJdbcTemplate().getJdbcOperations().update(querySql, check.get(0).get("question_id").toString());
	}


	/**
	 * 查找点赞数最多的回答以及回答的问题信息
	 * 重点：为院校项目准备的接口  不要随便改动
	 * @return
	 */
	public  Page<AskQuestionAndAnswerVo>   findMaxPraiseAnswer(Integer pageNumber, Integer pageSize){
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 20 : pageSize;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String  sql="select a.praise_sum as praiseSum,a.create_nick_name as createNickName ,a.content,q.title,q.id as questionId from oe_ask_answer a ,oe_ask_question q  where a.question_id = q.id  order by a.praise_sum   desc  ";
		Page<AskQuestionAndAnswerVo>  page = this.findPageBySQL(sql,paramMap, AskQuestionAndAnswerVo.class, pageNumber, pageSize);
		return  page;
	}

	/**
	 * 根据回答id获取对应的问题
	 * @param answerId
	 * @return
	 */
	public Map<String, Object>  findQuestionByAnswerId(String answerId){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("answerId",answerId);
		String  sql="select q.id ,q.title,a.user_id from oe_ask_answer a,oe_ask_question q where a.question_id=q.id  and a.id=:answerId";
		List<Map<String, Object>> questions= this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		return  questions.size() > 0 ? questions.get(0): null;
	}

	/**
	 * 获取被管理员删除的回答信息
	 */
	public  Map<String, Object>  findDeleteAccuseAnswer(String answerId){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("answerId", answerId);
		String  sql =" select q.id, q.title,an.user_id,a.user_id userId,u.name,  case a.accuse_type  when 0 then '广告营销等垃圾信息' when 1 then '抄袭内容' " +
				     " when 2 then '辱骂等不文明言语的人身攻击' when 3 then '色情或反动的违法信息'  else a.content END AS text " +
				     " from  oe_ask_answer an ,oe_ask_question q, oe_ask_accuse  a,oe_user u  where an.id=a.target_id and an.question_id=q.id and an.user_id=u.id and a.target_type=1 " +
				     " and an.id=:answerId ";
		List<Map<String, Object>> courses= this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
		return  courses.size() > 0 ? courses.get(0): null;
	}
}
