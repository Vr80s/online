package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.BarrierDao;
import com.xczhihui.bxg.online.web.service.BarrierService;
import com.xczhihui.bxg.online.web.vo.BarrierQuestionVo;
import com.xczhihui.bxg.online.web.vo.BarrierVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 关卡试卷业务层实现类
 * @author 康荣彩
 * @create 2016-11-02 19:21
 */
@Service
public class BarrierServiceImpl  extends OnlineBaseServiceImpl implements BarrierService {

    @Autowired
    private BarrierDao dao;
    /**
     * 获取关卡基本信息
     * @param id 关卡id
     * @return
     */
    @Override
    public BarrierVo getBarrierBasicInfo(String id, Integer examStatu, HttpServletRequest request){
        //获取当前登录用户信息
        OnlineUser u = (OnlineUser) UserLoginUtil.getLoginUser(request);
        return   dao.getBarrierBasicInfo(id, examStatu, u);
    }

    /**
     * 创建/获取当前用户考试卷 姜海成
     * @param
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> createUserTestPaper(String userId, String barrierId){
    	String sql = null;
    	String record_id = null;
    	Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("barrier_id", barrierId);
		paramMap.put("user_id", userId);
		
		//查询课程id
		String courseId = dao.getNamedParameterJdbcTemplate()
				.queryForObject("select course_id from oe_barrier where id=:barrier_id", paramMap, String.class);
		
		//判断是否有未提交的考试记录
		sql = "select * from oe_barrier_record o "
				+ " where o.barrier_id=:barrier_id and o.user_id=:user_id order by create_time desc limit 0,1 ";
		List<Map<String, Object>> oldstrategies = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		
		//没有考试记录，写；全部提交但未通过 ，写
		if (oldstrategies.size() <= 0 || 
				(oldstrategies.get(0).get("submit_time") != null && Integer.valueOf(oldstrategies.get(0).get("result").toString()) == 0)) {
			record_id = this.addBarrierPaper(barrierId, userId,courseId);
		} else {
			record_id = oldstrategies.get(0).get("id").toString();
		}
		
		sql ="select id,barrier_record_id as record_id,question_head,options,question_type,options_picture,question_score,my_answer "
				+ " from oe_barrier_question where barrier_record_id='"+record_id+"' ";
		return dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
    }

	/**
	 * 获得试卷
	 * @param userId
	 * @param barrierId
	 * @return
	 */
	@Override
    public List<BarrierQuestionVo> getCurrentPaper(String userId, String barrierId, Integer examStatu){
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("barrier_id", barrierId);
		paramMap.put("user_id", userId);
		String tj=examStatu ==1 ? " and  b.result=1" : " and b.result=0 and b.submit_time is not  null ";
		//获取当前关卡所对应的最新试卷
		String  recordSql=" select b.id from oe_barrier_record b where b.user_id=:user_id " +
				" and b.barrier_id=:barrier_id "+tj +" order by  b.submit_time desc  limit 1";
		List<Map<String, Object>> barrierRecord = dao.getNamedParameterJdbcTemplate().queryForList(recordSql, paramMap);
		if(barrierRecord.size()<=0){
			throw new RuntimeException("用户："+userId+"没有提交的试卷!");
		}
		paramMap.put("recoreId", barrierRecord.get(0).get("id").toString());
		String sql ="select bq.id,bq.question_head, bq.options, bq.question_type, bq.options_picture, bq.question_score,bq.answer,"
				+ "	 bq.solution,bq.is_right, bq.my_answer my_answer  from oe_barrier_question bq where  bq.user_id=:user_id and"
				+ "	 bq.barrier_record_id=:recoreId   order by bq.question_type, bq.create_time ";
		List<BarrierQuestionVo> userTestPapers=  dao.findEntitiesByJdbc(BarrierQuestionVo.class, sql, paramMap);
		return  userTestPapers;
	}

	/**
     * 保存我的回答 姜海成
     * @param
     */
    @Override
    public void updateQuestionById(String questionId, String answer, String userId){
    	String sql = "";
    	Map<String,Object> paramMap = new HashMap<>();
    	paramMap.put("question_id",questionId);
    	paramMap.put("answer",answer);
    	paramMap.put("user_id",userId);
        
    	//获得当前题目
    	sql = "select * from oe_barrier_question where id=:question_id and user_id=:user_id";
    	List<Map<String, Object>> questions = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
    	Map<String, Object> question = questions.get(0);



		//更新结果
    	int result = 0;
    	int score = 0;
    	if (question.get("answer").toString().equals(answer)) {
    		result = 1;
    		score = Integer.valueOf(question.get("question_score").toString());
		}
    	sql ="update oe_barrier_question set is_right="+result+",result_score="+score+", "
    			+ " my_answer=:answer where id=:question_id and user_id=:user_id";
    	dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
    }
    	
    /**
     * 生成试卷 姜海成
     * @param barrierId 需要创的关卡id
     * @param userId
     * @return
     */
	private String addBarrierPaper(String barrierId, String userId,String courseId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("barrier_id", barrierId);
		paramMap.put("user_id", userId);
		
		String sql = "";
		
		sql = "select u.barrier_status from oe_barrier bc,oe_barrier bp,oe_barrier_user u "
				+ " where bc.parent_id=bp.id and bp.id=u.barrier_id and u.user_id=:user_id and bc.id=:barrier_id ";
		List<Map<String, Object>> parent = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		if (parent.size() > 0 && !"1".equals(parent.get(0).get("barrier_status").toString())) {
			throw new RuntimeException("上一个关卡没有通关不可闯此关");
		}
		
		//写入闯关记录
		String id = UUID.randomUUID().toString().replace("-", "");
		sql = "insert into oe_barrier_record (id,user_id,course_id,barrier_id) "
				+ " values ('"+id+"','"+userId+"','"+courseId+"','"+barrierId+"') ";
		dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
		
		//写入闯关试题
		sql = "select * from oe_barrier_strategy o where o.barrier_id = :barrier_id ";
		List<Map<String, Object>> strategies = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		for (Map<String, Object> strategie : strategies) {
			int sum = Integer.valueOf(strategie.get("question_sum").toString());
			if (sum > 0) {
				int score = Integer.valueOf(strategie.get("qustion_score").toString());
				String bsql = "insert into oe_barrier_question (id,user_id,barrier_record_id,question_type,question_head,`options`,"
						+ " options_picture,difficulty,answer,solution,is_right,question_score,result_score) "
							+ " select replace(uuid(),'-',''),'"+userId+"','"+id+"',t4.question_type,t4.question_head,"
								+ " t4.`options`,t4.options_picture,t4.difficulty,t4.answer,t4.solution,0,"+score+",0 "
							+ " from oe_barrier t1,oe_chapter t2,oe_question_kpoint t3,oe_question t4 "
							+ " where t1.id=t2.barrier_id and t2.id=t3.kpoint_id and t3.question_id=t4.id "
								+ " and t1.id='"+barrierId+"' and t4.question_type= "+strategie.get("question_type").toString()
								+ " order by rand() limit 0,"+sum+" ";
				dao.getNamedParameterJdbcTemplate().update(bsql, paramMap);
			}
		}
		
		return id;
	}

	/**
	 * 提交闯关试卷 姜海成
	 */
	@Override
	public Map<String, Object> addSubmitPaper(String userId, String recordId) throws Exception {
		String sql = "";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("record_id", recordId);
		paramMap.put("user_id", userId);
        //存放排名和总通关人数
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rank",0);
		resultMap.put("number_pass",0);
		//当前考试记录
		sql = "select t2.score, t1.course_id, t1.id as barrier_id,t2.submit_time,t1.total_score,t1.pass_score_percent,t1.limit_time,t2.create_time "
				+ " from oe_barrier t1,oe_barrier_record t2 "
				+ " where t2.barrier_id=t1.id and t2.id=:record_id";
		List<Map<String, Object>> records = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		Map<String, Object> record = records.get(0);
		paramMap.put("barrier_id", record.get("barrier_id").toString());
		//如果已提交直接返回考试记录（结果）
		if (record.get("submit_time") != null) {
			sql = "select * from oe_barrier_record where id=:record_id";
			records = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
			return records.get(0);
		}
		
		//考试得分
		sql = "select sum(question_score) as sum from oe_barrier_question o where o.is_right=1 and o.barrier_record_id=:record_id";
		List<Map<String, Object>> scoreObj = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		int score = (scoreObj.size() <= 0 || scoreObj.get(0).get("sum") == null)
				? 0 : Integer.valueOf(scoreObj.get(0).get("sum").toString());
		resultMap.put("score",score);
		//通关标准
		int total = Integer.valueOf(record.get("total_score").toString());
		int percent = Integer.valueOf(record.get("pass_score_percent").toString());
		int limit = Integer.valueOf(record.get("limit_time").toString());
		
		//获得考试开始时间、提交时间
		Date createTime = (Date)record.get("create_time");
		Date currentTime = new Date();
		
		//耗时，分钟
		long useTime = (currentTime.getTime() - createTime.getTime()) / 1000 / 60;
		
		//闯关结果
		int result = (useTime <= limit && score >=percent) ? 1 : 0;

		//如果闯关成功，本关卡设置为通关，解锁本关卡下面的关卡
		if (result == 1) {
			sql = "update oe_barrier_user set lock_status=1,barrier_status=1, barrier_count=barrier_count+1 where user_id=:user_id and barrier_id=:barrier_id";
			dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
			sql = "update oe_barrier_user c,oe_barrier p set c.lock_status=1 where c.barrier_id=p.id and c.user_id=:user_id and p.parent_id=:barrier_id";
			dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
			//修改用户排名信息以及已通关总人数
			resultMap= updateUserRank(record,paramMap,resultMap);
		}else{
			sql = "update oe_barrier_user set barrier_count=barrier_count+1  where user_id=:user_id and barrier_id=:barrier_id";
			dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
		}
		
		sql = "update oe_barrier_record set score="+score+",result="+result+",submit_time=now(),use_time="+useTime+",number_pass="+resultMap.get("number_pass")+",rank="+resultMap.get("rank")+" where id=:record_id";
		dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
		
		//返回闯关记录
		sql = "select * from oe_barrier_record where id=:record_id";
		records = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		record = records.get(0);

		return record;
		
	}

	/**
	 * 修改用户排名信息以及已通关总人数
	 * @param record
	 */
	public  Map<String, Object>   updateUserRank(	Map<String, Object> record,Map<String, Object> paramMap,Map<String, Object> resultMap ){
		     Integer rank=0;
		     Integer number_pass=0;
		     paramMap.put("courseId",record.get("course_id"));
			//获取当前用户所在班级，以及此版已通关人数、用户排名
			String sqlgrade="select  g.grade_id  from  apply_r_grade_course g,oe_apply a,oe_user u where g.apply_id=a.id " +
					"and a.user_id=u.id  and g.is_payment=2 and grade_id >0  and u.id=:user_id and g.course_id=:courseId limit 1";
			List<Map<String, Object>> grades = dao.getNamedParameterJdbcTemplate().queryForList(sqlgrade, paramMap);
			if(grades.size()>0){
				rank++;
				number_pass++;
				paramMap.put("gradeId", grades.get(0).get("grade_id"));
				String sqlcount=" select br.score  from oe_barrier_record br,apply_r_grade_course g,oe_apply a" +
						" where br.user_id = a.user_id and a.id=g.apply_id and  br.course_id=:courseId  and br.barrier_id=:barrier_id " +
						" and br.result=1 and g.grade_id=:gradeId  group by br.user_id,g.apply_id  order by br.score desc  " ;
				List<BarrierVo> userScores = dao.findEntitiesByJdbc(BarrierVo.class, sqlcount, paramMap);
				number_pass+=userScores.size();
				if(userScores.size()>0){
					for (int i=0;i<userScores.size();i++){
						if( userScores.get(i).getScore() > Integer.valueOf(resultMap.get("score").toString())){
							rank++;
						}

					}
				}
                resultMap.put("rank",rank);
				resultMap.put("number_pass",number_pass);
			}
		return resultMap;
	}

	/**
	 * 获取最新一次闯关关卡基本信息
	 * @param id 关卡id
	 * @return
	 */
	@Override
    public BarrierVo  getNewBarrierBasicInfo(String id, HttpServletRequest request){
		 //获取当前登录用户信息
		 OnlineUser u = (OnlineUser) UserLoginUtil.getLoginUser(request);
		 return  dao.getNewBarrierBasicInfo(id,u);
	}
}
