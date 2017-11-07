package com.xczhihui.bxg.online.manager.mobile.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.CoursePreview;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.common.domain.TeachMethod;
import com.xczhihui.bxg.online.manager.cloudClass.service.LecturerService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.LecturerVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.MenuVo;
import com.xczhihui.bxg.online.manager.mobile.dao.MobileCourseDao;
import com.xczhihui.bxg.online.manager.mobile.service.MobileCourseService;

@Service("mobileCourseService")
public class MobileCourseServiceImpl extends OnlineBaseServiceImpl implements MobileCourseService{
	@Autowired
	private LecturerService lecturerService;
	@Autowired
	private MobileCourseDao mobileCourseDao;
	@Override
	public List<Menu> getfirstMenus() {
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String,Object>();
		String sql="SELECT id,name FROM oe_menu where is_delete = 0 and name <> '全部' and status=1 and yun_status = 1";
		dao.findEntitiesByJdbc(MenuVo.class, sql, params);
	    return dao.findEntitiesByJdbc(Menu.class, sql, params);
	}

	@Override
	public List<ScoreType> getScoreType() {
		// TODO Auto-generated method stub
		return mobileCourseDao.getScoreType();
	}
	@Override
	public Page<CourseVo> findCoursePage(CourseVo courseVo,  int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		Page<CourseVo> page = mobileCourseDao.findMobileCoursePage(courseVo, pageNumber, pageSize);
    	return page;
	}

	@Override
	public List<CourseVo> findCourseById(Integer id) {
		// TODO Auto-generated method stub
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("courseId", id);
		String sql = "SELECT oc.id as id,oc.grade_name as courseName,oc.class_template as classTemplate,om.name as xMenuName,st.name as scoreTypeName,"
				+ "tm.name as teachMethodName,oc.course_length as courseLength,oc.learnd_count as learndCount,oc.grade_qq gradeQQ,oc.default_student_count defaultStudentCount,"
				+ "oc.create_time as createTime,oc.status as status ,oc.is_free as isFree,oc.original_cost as originalCost,"
				+ "oc.current_price as currentPrice,oc.description as description ,oc.cloud_classroom as cloudClassroom ,"
				+ "oc.menu_id as menuId,oc.course_type_id as courseTypeId,oc.courseType as courseType,oc.qqno,oc.grade_student_sum as classRatedNum FROM oe_course oc "
				+ "LEFT JOIN oe_menu om ON om.id = oc.menu_id LEFT JOIN score_type st ON st.id = oc.course_type_id "
				+ "LEFT JOIN teach_method tm ON tm.id = oc.courseType WHERE oc.id = :courseId";

		List<CourseVo> courseVoList=dao.findEntitiesByJdbc(CourseVo.class, sql, paramMap);

		sql = "select sum(IFNULL(t.default_student_count,0)) from oe_grade t where t.course_id = ?";
		courseVoList.get(0).setLearndCount(mobileCourseDao.queryForInt(sql, new Object[]{id}));//累计默认报名人数
		return courseVoList;
	}

	@Override
	public Map<String, String> getCourseDetail(String courseId) {
		// TODO Auto-generated method stub
		String selectSqlByCourse="select course_id,img_url as smallImgPath,description as courseDetail from oe_course_mobile where course_id=:courseId";
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("courseId", courseId);
		List<Course> temp=mobileCourseDao.findEntitiesByJdbc(Course.class, selectSqlByCourse, paramMap);
		if (temp != null&&temp.size()>0) {
			Map<String, String> retn = new HashMap<String, String>();
			retn.put("detailImgPath",temp.get(0).getSmallImgPath());
			retn.put("smallImgPath", temp.get(0).getSmallImgPath());
			retn.put("courseDetail", temp.get(0).getCourseDetail());
			return retn;
		}
		return null;
	}

	@Override
	public void updateCourseDetail(String courseId, String smallImgPath, Object object, String courseDetail,
			String courseOutline, String commonProblem) {
		// TODO Auto-generated method stub
		String selectSqlByCourse="select course_id from oe_course_mobile where course_id=:courseId";
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("courseId", courseId);
		List<CourseVo> temp=mobileCourseDao.findEntitiesByJdbc(CourseVo.class, selectSqlByCourse, paramMap);
		if(temp.size()>0){
			//System.out.println("进入更新");
			mobileCourseDao.getNamedParameterJdbcTemplate().getJdbcOperations().update("update  oe_course_mobile set " +
					"img_url=?, " +
					"description=? where course_id=?" ,new Object[]{smallImgPath,courseDetail,courseId});
			//更新
		}else{
			mobileCourseDao.getNamedParameterJdbcTemplate().getJdbcOperations().update("INSERT INTO oe_course_mobile ( " +
					"	id, " +
					"	course_id, "+ 
					"img_url, " +
					"description )" +
					" VALUES (REPLACE(UUID(),'-',''), ?, ?, ?)",new Object[]{courseId,smallImgPath,courseDetail});
		}
	
	}

	@Override
	public void addPreview(String courseId, String smallImgPath, Object object, String courseDetail,
			String courseOutline, String commonProblem) {
		// TODO Auto-generated method stub
		mobileCourseDao.getNamedParameterJdbcTemplate().getJdbcOperations().update("INSERT INTO oe_course_mobile ( " +
																					"	id, " +
																					"	course_id, "+ 
																					"img_url, " +
																					"description )" +
																					" VALUES (REPLACE(UUID(),'-',''), ?, ?, ?)",new Object[]{courseId,smallImgPath,courseDetail});
		
		}

	
		
	

	

	
}
