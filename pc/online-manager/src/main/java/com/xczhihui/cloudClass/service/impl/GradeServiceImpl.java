package com.xczhihui.cloudClass.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Grade;
import com.xczhihui.cloudClass.service.GradeService;
import com.xczhihui.cloudClass.vo.CourseVo;
import com.xczhihui.cloudClass.vo.GradeDetailVo;
import com.xczhihui.cloudClass.vo.GradeVo;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.xczhihui.support.shiro.ManagerUserUtil;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *   GradeServiceImpl:班级业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class GradeServiceImpl extends OnlineBaseServiceImpl implements GradeService {

    /**
     * 获取全部班级信息
     * @param
     * @return
     */
    @Override
    public Page<GradeVo> findGradeList(GradeDetailVo gradeVo, Integer pageNumber, Integer pageSize) {
        Map<String,Object> paramMap=new HashMap<String,Object>();
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        StringBuilder sql=new StringBuilder() ;
        sql.append(" select * from(");
        sql.append("select concat(concat(concat(concat(concat(om.name,'-'),st.name),'-'),''),oc.grade_name) as courseName,og.name as name,og.student_amount as studentAmount,oc.grade_name as courseNameTmp,om.name as menuName,st.name as scoreTypeName,ifnull(og.student_count,0) as student_count,\n" );
        sql.append("(select group_concat(ol.name) from oe_lecturer ol where role_type=1 and is_delete=0 and ol.id in (select lecturer_id from course_r_lecturer where course_id=og.course_id and is_delete=0 ))role_type1,\n" );
        sql.append("(select group_concat(ol.name) from oe_lecturer ol where role_type=2 and is_delete=0 and ol.id in (select lecturer_id from course_r_lecturer where course_id=og.course_id and is_delete=0 ))role_type2,\n" );
        sql.append("(select group_concat(ol.name) from oe_lecturer ol where role_type=3 and is_delete=0 and ol.id in (select lecturer_id from course_r_lecturer where course_id=og.course_id and is_delete=0 ))role_type3,\n" );
        sql.append("og.curriculum_time as curriculumTime,og.stop_time as stopTime,og.status as status,og.id as id,case WHEN og.curriculum_time>SYSDATE() THEN  0 WHEN og.curriculum_time<SYSDATE() THEN  1 END  as otcStatus,\n");
        sql.append("oc.id as courseId,oc.class_Template as classTemplate,og.sort as sort,om.id as menuId,oc.course_type_id,oc.courseType,st.id as scoreTypeId,og.work_day_sum workDaySum,og.rest_day_sum restDaySum,og.qqno,og.default_student_count \n");
        sql.append("from oe_grade og " );						//职业课程
        sql.append("join oe_course oc on oc.id=og.course_id and oc.course_type = 0 join oe_menu om on om.id=oc.menu_id\n" );
        sql.append("join score_type st on st.id=oc.course_type_id \n" );
        sql.append(" where 1=1 and og.is_delete=0 and oc.is_delete=0 and st.is_delete=0 ");
        sql.append(") ds where 1=1 ");
        
        if(StringUtils.isNotEmpty(gradeVo.getName()))
        {
            sql.append(" and (ds.name like :sname or ds.role_type1 like :sname or ds.role_type2 like :sname or ds.role_type2 like :sname  ) ");
            paramMap.put("sname","%"+gradeVo.getName()+"%");
        }
        if(StringUtils.isNotEmpty(gradeVo.getMenuId())&&!"-1".equals(gradeVo.getMenuId()))
        {
            sql.append(" and ds.menuId=:menuId");
            paramMap.put("menuId",gradeVo.getMenuId());
        }
        if(StringUtils.isNotEmpty(gradeVo.getScoreTypeId())&&!"-1".equals(gradeVo.getScoreTypeId()))
        {
            sql.append(" and ds.course_type_id=:courseTypeId");
            paramMap.put("courseTypeId",gradeVo.getScoreTypeId());
        }
        if(StringUtils.isNotEmpty(gradeVo.getTeachMethodId())&&!"-1".equals(gradeVo.getTeachMethodId()))
        {
            sql.append(" and ds.courseType=:teachMethodId");
            paramMap.put("teachMethodId",gradeVo.getTeachMethodId());
        }
        if(gradeVo.getCourse_id()!=null&&gradeVo.getCourse_id()!=-1)
        {
            sql.append(" and ds.courseId=:courseId");
            paramMap.put("courseId",gradeVo.getCourse_id());
        }
        if(gradeVo.getGradeStatus()!=null&&gradeVo.getGradeStatus()!=-1)
        {
            sql.append(" and ds.otcStatus=:gradeStatus");
            paramMap.put("gradeStatus",gradeVo.getGradeStatus());
        }
        if(gradeVo.getCurriculumTime()!=null){
            sql.append(" and ds.curriculumTime >= :startTime ");
            paramMap.put("startTime",gradeVo.getCurriculumTime());
        }
        if(gradeVo.getStopTime()!=null){
            sql.append(" and ds.stopTime <= :stopTime ");
            paramMap.put("stopTime",gradeVo.getStopTime());
        }
        sql.append(" order by ds.curriculumTime desc");
//        System.out.println("查询班级信息："+sql.toString());
        Page<GradeVo>  page = dao.findPageBySQL(sql.toString(),paramMap,GradeVo.class, pageNumber, pageSize);
        for(int i = 0;i<page.getItems().size();i++){
        	//总天数 减休息日  实际学习计划天数 应该等于 模板配置的天数 如果小于 且不为0 那么需要进行追加操作
        	String sqlTemp = "select COUNT(1) - sum(IFNULL(rest_has,0)) from oe_plan op where op.grade_id = ? ";
        	page.getItems().get(i).setHasPlan(dao.queryForInt(sqlTemp, new Object[]{page.getItems().get(i).getId()}));

        	sqlTemp = "select max(day) from oe_plan_template t where t.course_id = ? ";
//        	page.getItems().get(i).setDefaultStudentCount(dao.queryForInt(sqlTemp, new Object[]{page.getItems().get(i).getCourseId()})); //2017.08.09 yuruixin
        }
        return page;
    }

    /**
     * 获取全部班级信息
     * @param
     * @return
     */
    @Override
    public Page<GradeVo> findMicroGradeList(GradeDetailVo gradeVo, Integer pageNumber, Integer pageSize) {
    	Map<String,Object> paramMap=new HashMap<String,Object>();
    	pageNumber = pageNumber == null ? 1 : pageNumber;
    	pageSize = pageSize == null ? 20 : pageSize;
    	StringBuilder sql=new StringBuilder() ;
    	sql.append(" select * from(");
    	sql.append("select concat(concat(concat(concat(concat(om.name,'-'),st.name),'-'),tm.name),oc.grade_name) as courseName,og.name as name,og.student_amount as studentAmount,oc.grade_name as courseNameTmp,om.name as menuName,st.name as scoreTypeName,tm.name as teachMethodName,ifnull(og.student_count,0) as student_count,\n" );
    	sql.append("(select group_concat(ol.name) from oe_lecturer ol where role_type=1 and is_delete=0 and ol.id in (select lecturer_id from course_r_lecturer where course_id=og.course_id and is_delete=0 ))role_type1,\n" );
        sql.append("(select group_concat(ol.name) from oe_lecturer ol where role_type=2 and is_delete=0 and ol.id in (select lecturer_id from course_r_lecturer where course_id=og.course_id and is_delete=0 ))role_type2,\n" );
        sql.append("(select group_concat(ol.name) from oe_lecturer ol where role_type=3 and is_delete=0 and ol.id in (select lecturer_id from course_r_lecturer where course_id=og.course_id and is_delete=0 ))role_type3,\n" );
    	sql.append("og.curriculum_time as curriculumTime,og.stop_time as stopTime,og.status as status,og.id as id,case WHEN og.curriculum_time>SYSDATE() THEN  0 WHEN og.curriculum_time<SYSDATE() THEN  1 END  as otcStatus,\n");
    	sql.append("oc.id as courseId,oc.class_Template as classTemplate,og.sort as sort,om.id as menuId,oc.course_type_id,oc.courseType,st.id as scoreTypeId,tm.id as teachMethodId,ifnull(og.work_day_sum,0) workDaySum,ifnull(og.rest_day_sum,0) restDaySum,og.qqno \n");
    	sql.append("from oe_grade og " );						//微课
    	sql.append("join oe_course oc on oc.id=og.course_id and oc.course_type = 1 join oe_menu om on om.id=oc.menu_id\n" );
    	sql.append("join score_type st on st.id=oc.course_type_id left join teach_method tm on tm.id=oc.courseType\n" );
    	sql.append(" where 1=1 and og.is_delete=0 and oc.is_delete=0 and st.is_delete=0 and tm.is_delete=0  ");
    	sql.append(") ds where 1=1 ");
    	
    	if(StringUtils.isNotEmpty(gradeVo.getName()))
    	{
    		sql.append(" and (ds.name like :sname or ds.role_type1 like :sname or ds.role_type2 like :sname or ds.role_type2 like :sname  ) ");
    		paramMap.put("sname","%"+gradeVo.getName()+"%");
    	}
    	if(StringUtils.isNotEmpty(gradeVo.getMenuId())&&!"-1".equals(gradeVo.getMenuId()))
    	{
    		sql.append(" and ds.menuId=:menuId");
    		paramMap.put("menuId",gradeVo.getMenuId());
    	}
    	if(StringUtils.isNotEmpty(gradeVo.getScoreTypeId())&&!"-1".equals(gradeVo.getScoreTypeId()))
    	{
    		sql.append(" and ds.course_type_id=:courseTypeId");
    		paramMap.put("courseTypeId",gradeVo.getScoreTypeId());
    	}
//    	if(StringUtils.isNotEmpty(gradeVo.getTeachMethodId())&&!"-1".equals(gradeVo.getTeachMethodId()))
//    	{
//    		sql.append(" and ds.courseType=:teachMethodId");
//    		paramMap.put("teachMethodId",gradeVo.getTeachMethodId());
//    	}
    	if(gradeVo.getCourse_id()!=null&&gradeVo.getCourse_id()!=-1)
    	{
    		sql.append(" and ds.courseId=:courseId");
    		paramMap.put("courseId",gradeVo.getCourse_id());
    	}
//    	if(gradeVo.getGradeStatus()!=null&&gradeVo.getGradeStatus()!=-1)
//    	{
//    		sql.append(" and ds.otcStatus=:gradeStatus");
//    		paramMap.put("gradeStatus",gradeVo.getGradeStatus());
//    	}
//    	if(gradeVo.getCurriculumTime()!=null){
//    		sql.append(" and ds.curriculumTime >= :startTime ");
//    		paramMap.put("startTime",gradeVo.getCurriculumTime());
//    	}
//    	if(gradeVo.getStopTime()!=null){
//    		sql.append(" and ds.stopTime <= :stopTime ");
//    		paramMap.put("stopTime",gradeVo.getStopTime());
//    	}
    	sql.append(" order by ds.curriculumTime desc");
//    	System.out.println("查询班级信息："+sql.toString());
    	Page<GradeVo>  page = dao.findPageBySQL(sql.toString(),paramMap,GradeVo.class, pageNumber, pageSize);
    	return page;
    }


    /**
     * 添加一个班级信息
     * @param grade  班级对象
     *
     * @return
     */
    @Override
    public void addGrade(Grade grade) {
         grade.setCreatePerson(ManagerUserUtil.getUsername());
         grade.setCreateTime(new Date());
         grade.setSeat(0);
         grade.setSort(0);
         grade.setCourseId(grade.getCourseId().toString());
         dao.save(grade);
    }

    @Override
    public void saveTeachers(String gradeId,String courseId,String userName,List<String> roleTypes){
        Map<String,Object> param=new HashMap<String,Object>();
        String sql="update grade_r_lecturer set is_delete=1 where grade_id=:gradeId ";
        param.put("gradeId", gradeId);
        dao.getNamedParameterJdbcTemplate().update(sql, param);
        if(roleTypes!=null&&roleTypes.size()>0)
        {
            for(String roleType1:roleTypes)
            {
                Map<String,Object> roleType1Param=new HashMap<String,Object>();
                roleType1Param.put("gradeId",gradeId);
                roleType1Param.put("courseId",courseId);
                roleType1Param.put("lecturerId",roleType1);
                roleType1Param.put("createPerson",userName);
                roleType1Param.put("createTime",new Date());
                roleType1Param.put("id", UUID.randomUUID().toString().replace("-",""));
                String insertRoleType1="insert into grade_r_lecturer(id,grade_id,course_id,lecturer_id,is_delete,create_person,create_time) values(:id,:gradeId,:courseId,:lecturerId,0,:createPerson,:createTime) ";
                dao.getNamedParameterJdbcTemplate().update(insertRoleType1, roleType1Param);
            }
        }
    }

    /**
     * 修改编辑信息
     * @param
     * @return
     */
    @Override
    public void updateGrade(Grade grade) {
        dao.update(grade);
    }

    @Override
    public void deleteGrades(String[] ids) {
    	
        if (ids.length>0){
            StringBuffer sb = new StringBuffer( "update oe_grade set is_delete=1 where is_delete=0 and  id in('");
            StringBuffer sqlDeGRL = new StringBuffer( "update grade_r_lecturer set is_delete=1 where  grade_id in('");
            
            for(int i = 0; i < ids.length; i++){
                if(i!=(ids.length-1)){
                    sb. append(ids[i]+"','");
                    sqlDeGRL.append(ids[i]+"','");
                }else{
                    sb. append(ids[i]+"')");
                    sqlDeGRL.append(ids[i]+"')");
                }
            }
            dao.getNamedParameterJdbcTemplate().update(sb.toString(), new HashMap<String, Object>());
            dao.getNamedParameterJdbcTemplate().update(sqlDeGRL.toString(), new HashMap<String, Object>());
            
        }

    }

    @Override
    public List<Map<String, Object>> lectereListByGradeIdAndRoleType(String gradeId,int roleType,String courseId) {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("gradeId",gradeId);
        params.put("roleType", roleType);
        params.put("courseId", courseId);
        StringBuilder sql=new StringBuilder();
//        sql.append("select ds.*,exists(select * from oe_lecturer ol where role_type=:roleType and is_delete=0 and ol.id in (select lecturer_id from grade_r_lecturer where grade_id=:gradeId and lecturer_id=ds.id and is_delete=0 )) as status  from (");
//        sql.append("select id,name  from oe_lecturer ol where  role_type=:roleType and is_delete=0  )ds");
        sql.append("SELECT " +
	        		"	ol.id, " +
	        		"	ol. name, " +
	        		"	CASE " +
	        		"WHEN grl.lecturer_id > 0 THEN " +
	        		"	1 " +
	        		"ELSE " +
	        		"	0 " +
	        		"END status " +
	        		"FROM " +
	        		"	oe_lecturer ol " +
	        		"LEFT JOIN grade_r_lecturer grl ON ( " +
	        		"	ol.id = grl.lecturer_id " +
	        		"	AND grl.grade_id = :gradeId " +
	        		"	AND grl.is_delete = 0 " +
	        		") " +
	        		"JOIN oe_course oc ON ( " +
	        		"	ol.menu_id = oc.menu_id " +
	        		"	AND oc.id = :courseId " +
	        		") " +
	        		"WHERE " +
	        		"	ol.role_type = :roleType " +
	        		" AND ol.is_delete = 0 ");

        return  dao.getNamedParameterJdbcTemplate().queryForList(sql.toString(),params);
    }

    /**
     * 获取全部课程信息
     * @param
     * @return
     */
    @Override
    public List<CourseVo> findCourseList(Integer pageNumber, Integer pageSize) {
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        String sql="select cou.id,cou.grade_name as courseName  from  oe_course cou  where 1=1 and cou.is_delete=0  order by cou.sort  ";
        Page<CourseVo>  page = dao.findPageBySQL(sql, new HashMap<String, Object>(),CourseVo.class, pageNumber, pageSize);
        return page.getItems();
    }

    @Override
    public Map<String, Object> findMenuByGradeId(String gradeId) {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("gradeId",gradeId);
        String sql="select om.name\n" +
                "from oe_grade og left join oe_course oc on oc.id=og.course_id left join oe_menu om on om.id=oc.menu_id\n" +
                "where og.id=:gradeId and oc.is_delete=0 group by name\n";
        return  dao.getNamedParameterJdbcTemplate().queryForMap(sql.toString(), params);
    }


    /**
     * 根据班级ID号，查找对应的班级对象
     * @param gradeId 班级id
     * @return Example 分页列表
     */
    @Override
    public List<GradeDetailVo> getGradeById( Integer gradeId) {
        List<GradeDetailVo> grades=null;
        Map<String, Object> paramMap = new HashMap<String, Object>();

        if(gradeId != null){
            String hql="select gr.*,cou.grade_name as courseName from oe_grade gr join oe_course cou where gr.course_id=cou.id and  gr.is_delete=0 and gr.id= :id";
            paramMap.put("id",gradeId);
            grades= dao.findEntitiesByJdbc(GradeDetailVo.class,hql,paramMap);
        }
        return grades;
    }

    /**
     * 根据班级ID号，将对应的班级禁用
     * @param gradeId 班级id
     * @return
     */
    @Override
    public int updateGradeStatus(Integer gradeId, Integer isGradeStatus){
        String sql = String.format("update oe_grade set grade_status=%d where id =:id", new Object[]{ isGradeStatus});
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", gradeId);
        return dao.getNamedParameterJdbcTemplate().update(sql, paramSource);
    }

    @Override
    public Grade findById(Integer id) {
        return dao.findOneEntitiyByProperty(Grade.class,"id",id);
    }

    /**
     * 根据ID查找当前对象排号之前的最接近它的那个班级对象
     * @param id
     * @return
     */
    @Override
    public int updatePreSortEntity(Integer id){
          int flag=0;
          //查出小于等于当前排号的所有班级信息，然后倒排，那么查出来的第一条数据就是当前数据，第二条就是排在他前面的班级对象
          String sql="select * from oe_grade gra where gra.sort <= (select sort from oe_grade where id= :id) ORDER BY gra.sort desc";
          MapSqlParameterSource paramSource = new MapSqlParameterSource();
          paramSource.addValue("id", id);
          List<Grade> grades = dao.getNamedParameterJdbcTemplate().query(sql,paramSource, BeanPropertyRowMapper.newInstance(Grade.class));
          int curretSort=grades.get(0).getSort();
          if(grades.size()>1){
              //修改当前班级对象的排号为他之前排号的对象sort值
               Grade grade = grades.get(0);
               grade.setSort(grades.get(1).getSort());
               dao.update(grade);

              //修改当前对象排号前一位对象的sort值为当前班级对象的sort值
               Grade grade1 = grades.get(1);
               grade1.setSort(curretSort);
               dao.update(grade1);
              flag=1;
          }
        return  flag;
    }

    /**
     * 根据ID查找当前对象排号之后的最接近它的那个班级对象
     * @param id
     * @return
     */
    @Override
    public int updateNextSortEntity(Integer id) {
        int flag=0;
        //查出小于等于当前排号的所有班级信息，然后倒排，那么查出来的第一条数据就是当前数据，第二条就是排在他前面的班级对象
        String sql="select * from oe_grade gra where gra.sort >= (select sort from oe_grade where id= :id) ORDER BY gra.sort ";
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);
        List<Grade> grades = dao.getNamedParameterJdbcTemplate().query(sql,paramSource, BeanPropertyRowMapper.newInstance(Grade.class));
        int curretSort=grades.get(0).getSort();
        if(grades.size()>1){
            //修改当前班级对象的排号为他之后排号的对象sort值
            Grade grade = grades.get(0);
            grade.setSort(grades.get(1).getSort());
            dao.update(grade);

            //修改当前对象排号前一位对象的sort值为当前班级对象的sort值
            Grade grade1 = grades.get(1);
            grade1.setSort(curretSort);
            dao.update(grade1);
            flag=1;
        }
        return  flag;
    }

    @Override
    public List<Grade> getByName(String name){
    	return dao.findEntitiesByProperty(Grade.class,"name",name);
    	
    }


	@Override
	public List<Grade> getGradeByCourseId(String courseId) {
		return dao.findEntitiesByProperty(Grade.class, "courseId", courseId);
	}

	@Override
	public boolean checkBuildPlan(String courseId) {
		String sql = "select count(1) from oe_plan_template opt where opt.course_id = ?";
		return dao.queryForInt(sql, new Object[]{courseId}) > 0;
	}
}
