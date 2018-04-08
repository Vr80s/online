package com.xczhihui.barrier.service.impl;

import com.xczhihui.barrier.service.BarrierService;
import com.xczhihui.barrier.vo.BarrierVo;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Barrier;
import com.xczhihui.bxg.online.common.domain.BarrierStrategy;
import com.xczhihui.bxg.online.common.domain.Chapter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.barrier.dao.BarrierDao;
import com.xczhihui.cloudClass.vo.CourseVo;

@Service
public class BarrierServiceImpl extends OnlineBaseServiceImpl implements BarrierService {

	@Autowired
    private BarrierDao barrierDao;
	
	@Override
	public Page<BarrierVo> findBarrierPage(BarrierVo barrierVo, Integer pageNumber, Integer pageSize) {
		Page<BarrierVo> page = barrierDao.findBarrierPage(barrierVo, pageNumber, pageSize);
		return page;
	}
	
    @Override
	public Page<CourseVo> findCoursePage(CourseVo courseVo,  int pageNumber, int pageSize) {
    	Page<CourseVo> page = barrierDao.findCloudClassCoursePage(courseVo, pageNumber, pageSize);
    	return page;
	
	}
	
	@Override
	public void addBarrier(BarrierVo barrierVo) {
		
//		if (barrierDao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject
//				("select count(id) from oe_barrier where course_id=? and `status`=1", Integer.class,barrierVo.getCourseId()) > 0) {
//			throw new RuntimeException("关卡已被启用，不能再删除、修改上级！");
//		}
		
		int status = 0;
		String parentId = "".equals(barrierVo.getParentId()) ? null : barrierVo.getParentId();
		String barrierId = null;
		
		Barrier old = dao.findOneEntitiyByProperty(Barrier.class, "courseId", barrierVo.getCourseId());
		if (old != null) {
			status = old.getStatus();
		}
		
		Barrier barrier = new Barrier();
//		barrier.setId(UUID.randomUUID().toString().replace("-", ""));
		barrier.setCourseId(barrierVo.getCourseId());
		barrier.setName(barrierVo.getName());
		barrier.setTotalScore(barrierVo.getTotalScore());
		barrier.setLimitTime(barrierVo.getLimitTime());
		barrier.setPassScorePercent(barrierVo.getPassScorePercent());
		barrier.setParentId("".equals(barrierVo.getParentId()) ? null : barrierVo.getParentId());
		barrier.setKpointId(barrierVo.getKpointId());
		barrier.setDelete(false);
		barrier.setCreatePerson(barrierVo.getCreatePerson());
		barrier.setCreateTime(new Date());
		barrier.setStatus(status);
		//基础信息保存
		dao.save(barrier);
		barrierId=barrier.getId();
		//保存题目信息
		for(int i=0;i<barrierVo.getStrategyCnt().length;i++){
			BarrierStrategy barrierStrategy = new BarrierStrategy();
			barrierStrategy.setBarrierId(barrier.getId());
			barrierStrategy.setQuestionType(i+"");//0单选、1多选、2判断
			barrierStrategy.setQuestionSum(Integer.parseInt(barrierVo.getStrategyCnt()[i]));
			barrierStrategy.setTotalScore(Integer.parseInt(barrierVo.getStrategyTotalScore()[i]));
			barrierStrategy.setQustionScore(Integer.parseInt(barrierVo.getStrategyScore()[i]));
			barrierStrategy.setDelete(false);
			barrierStrategy.setCreatePerson(barrierVo.getCreatePerson());
			barrierStrategy.setCreateTime(new Date());
			dao.save(barrierStrategy);
		}

		if(barrierVo.getKpointIds() != null )
		{
			String[] kpoints = barrierVo.getKpointIds().split(",");
			for(int i = 0;i<kpoints.length;i++){
				Chapter chapter = dao.findOneEntitiyByProperty(Chapter.class,"id",kpoints[i]);
				chapter.setBarrierId(barrier.getId());
				dao.update(chapter);
			}
		}
		
		if (status == 1) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("course_id", barrier.getCourseId());
			String statusSql = parentId != null 
				? " ifnull((select barrier_status from oe_barrier_user where user_id=t3.id and barrier_id='"+parentId+"' limit 0,1),0) " : "1";
			String sql = "insert into oe_barrier_user (id,user_id,barrier_id,course_id,lock_status) "
					+ " select replace(uuid(),'-',''),t3.id,'"+barrierId+"','"+barrier.getCourseId()+"', "
					+ statusSql
					+ " from apply_r_grade_course t1,oe_apply t2,oe_user t3 "
					+ " where t1.apply_id=t2.id and t2.user_id=t3.id and t1.course_id=:course_id ";
			dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
		}
	}

	@Override
	public void updateBarrier(BarrierVo barrierVo) {

		Barrier barrier = dao.findOneEntitiyByProperty(Barrier.class, "id", barrierVo.getId());
		String voParentId = barrierVo.getParentId()==null?"": barrierVo.getParentId();
		String parentId = barrier.getParentId()==null?"": barrier.getParentId();
		
		if (!voParentId.equals(parentId)) {
			if (barrierDao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject
					("select count(id) from oe_barrier where course_id=? and `status`=1", Integer.class,barrier.getCourseId()) > 0) {
				throw new RuntimeException("关卡已被启用，不能再删除、修改上级！");
			}
		}
		
		barrier.setCourseId(barrierVo.getCourseId());
		barrier.setName(barrierVo.getName());
		barrier.setTotalScore(barrierVo.getTotalScore());
		barrier.setLimitTime(barrierVo.getLimitTime());
		barrier.setPassScorePercent(barrierVo.getPassScorePercent());
		barrier.setParentId("".equals(barrierVo.getParentId()) ? null : barrierVo.getParentId());
		barrier.setKpointId(barrierVo.getKpointId());

		//基础信息保存
		dao.save(barrier);
		
		//保存题目信息
		for(int i=0;i<barrierVo.getStrategyCnt().length;i++){
			String hql = "from BarrierStrategy where barrierId = ? and questionType = ? and isDelete = 0";
			BarrierStrategy barrierStrategy = dao.findByHQLOne(hql, new Object[]{barrierVo.getId(),i+""});
			
			barrierStrategy.setBarrierId(barrier.getId());
			barrierStrategy.setQuestionType(i+"");//0单选、1多选、2判断
			barrierStrategy.setQuestionSum(Integer.parseInt(barrierVo.getStrategyCnt()[i]));
			barrierStrategy.setTotalScore(Integer.parseInt(barrierVo.getStrategyTotalScore()[i]));
			barrierStrategy.setQustionScore(Integer.parseInt(barrierVo.getStrategyScore()[i]));
			barrierStrategy.setDelete(false);
			barrierStrategy.setCreatePerson(barrierVo.getCreatePerson());
			barrierStrategy.setCreateTime(new Date());
			dao.update(barrierStrategy);
		}
		String[] kpoints ;
		if(barrierVo.getOldKpointIds() != null){
			//先清空旧的知识点
			kpoints = barrierVo.getOldKpointIds().split(",");
			for(int i = 0;i<kpoints.length;i++){
				Chapter chapter = dao.findOneEntitiyByProperty(Chapter.class,"id",kpoints[i]);
				chapter.setBarrierId(null);
				dao.update(chapter);
			}
		}
		
		if(barrierVo.getKpointIds() != null){
				
			//再更新新的知识点
			kpoints = barrierVo.getKpointIds().split(",");
			for(int i = 0;i<kpoints.length;i++){
				Chapter chapter = dao.findOneEntitiyByProperty(Chapter.class,"id",kpoints[i]);
				chapter.setBarrierId(barrier.getId());
				dao.update(chapter);
			}
		}
	}
	
	@Override
	public BarrierVo getBarrierDetail(BarrierVo barrierVo) {
//		System.out.println("ID："+barrierVo.getId());
		Barrier barrier = dao.findOneEntitiyByProperty(Barrier.class, "id", barrierVo.getId());
		
		barrierVo.setName(barrier.getName());
		barrierVo.setTotalScore(barrier.getTotalScore());
		barrierVo.setLimitTime(barrier.getLimitTime());
		barrierVo.setPassScorePercent(barrier.getPassScorePercent());
		barrierVo.setParentId(barrier.getParentId());
		barrierVo.setKpointId(barrier.getKpointId());
		String hql = "from BarrierStrategy where barrierId = ? and isDelete = 0 order by questionType asc";
		List<BarrierStrategy> list = dao.findByHQL(hql, new Object[]{barrierVo.getId()});
		
		String [] strategyCnt = new String[3];
		String [] strategyTotalScore = new String[3];
		String [] strategyScore = new String[3];
		String [] strategyQuestionType  = new String[3];
		for(int i=0;i<list.size();i++){
			strategyCnt[i] = list.get(i).getQuestionSum()+"";
			strategyTotalScore[i] = list.get(i).getTotalScore()+"";
			strategyScore[i] = list.get(i).getQustionScore()+"";
			strategyQuestionType[i] = list.get(i).getQuestionType()+"";
		}
		
		barrierVo.setStrategyCnt(strategyCnt);
		barrierVo.setStrategyTotalScore(strategyTotalScore);
		barrierVo.setStrategyScore(strategyScore);
		barrierVo.setStrategyQuestionType(strategyQuestionType);
		
		//获取到所有本关卡下的知识点
		String sql = "select group_concat(oc.id) ids from oe_chapter oc where oc.barrier_id = ? and oc.is_delete = 0";
		barrierVo.setKpointIds(dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql, new Object[]{barrierVo.getId()}, String.class));
		//获取到所有本关卡下的知识点名字
		sql = "select group_concat(oc.name) names from oe_chapter oc where oc.barrier_id = ?  and oc.is_delete = 0 ";
		barrierVo.setKpointNames(dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql, new Object[]{barrierVo.getId()}, String.class));
		return barrierVo;
	}

	@Override
	public void deletes(String[] ids) {
		// TODO Auto-generated method stub
		for(String id:ids){
			String hqlPre="from Barrier where isDelete=0 and id = ?";
			Barrier barrier= dao.findByHQLOne(hqlPre,new Object[] {id});
			
			if (barrierDao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject
					("select count(id) from oe_barrier where course_id=? and `status`=1", Integer.class,barrier.getCourseId()) > 0) {
				throw new RuntimeException("关卡已被启用，不能再删除、修改上级！");
			}
			
            if(barrier !=null){
            	barrier.setDelete(true);
                dao.update(barrier);
            }
            //清空掉已绑定该关卡的知识点
            Map<String,String> paramMap = new HashMap<>();
            String sql = " update oe_chapter set barrier_id = null where barrier_id = :barrierId ";
            paramMap.put("barrierId", id);
            dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
        }
	}
	
	@Override
	public List getQuestions(String kpointIds) {
		StringBuffer sql = new StringBuffer("SELECT " +
											"	oq.question_type questionType, " +
											"	count(1) cnt " +
											"FROM " +
											"	oe_question oq,oe_question_kpoint oqk " +
											"WHERE oq.is_delete = 0 " +
											"AND oq.`status` = 1 " + 
											"and oq.id = oqk.question_id ");
		Map<String,Object> paramMap=new HashMap<String,Object>();
		
		if( kpointIds != null && kpointIds != ""){
			sql.append(" and oqk.kpoint_id in( ");
			String[] kpointId = kpointIds.split(","); 
			for(int i = 0;i<kpointId.length;i++){
				if(i!=0){
                    sql.append(",");
				}
                sql.append("'"+kpointId[i]+"'");
			}
		    sql.append(" ) ");
		}else{
			sql.append(" and 1 = 2 ");//如果为空就什么也查不出来
		}
		
		sql.append("GROUP BY oq.question_type ");
//		System.out.println("所有的问题数量："+sql.toString());
		return dao.getNamedParameterJdbcTemplate().queryForList(sql.toString(),paramMap);
	}

	@Override
	public List getBarriersSelect(String courseId,String id) {
		String sql = "select ob.id,ob.`name` from oe_barrier ob where ob.course_id = :courseId and ob.is_delete = 0 ";
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("courseId", courseId);
		if(!"".equals(id) && id != null){
			sql += " and id <> :barrier ";
			paramMap.put("barrier", id);
		}
		return dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
	}

	@Override
	public void update_UseBarrier(String courseId) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("course_id", courseId);
		
		dao.getNamedParameterJdbcTemplate()
			.update("update oe_barrier set `status`=1 where course_id = :course_id",paramMap);
		
		//为已购买此课程的用户添加关卡
		String insertSql = "insert into oe_barrier_user (id,user_id,barrier_id,course_id,lock_status) "
				+ " select replace(uuid(),'-',''),t3.id,t0.id,t0.course_id,if(t0.parent_id is null or t0.parent_id='',1,0) "
				+ "   from oe_barrier t0,apply_r_grade_course t1,oe_apply t2,oe_user t3 "
				+ " where t0.course_id=t1.course_id and t1.apply_id=t2.id and t2.user_id=t3.id and t1.course_id=:course_id "
				+ "   and not exists (select 1 from oe_barrier_user t4 where t0.id=t4.barrier_id and t4.user_id=t3.id) ";
		dao.getNamedParameterJdbcTemplate().update(insertSql, paramMap);
	}
}
