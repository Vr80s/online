package com.xczhihui.bxg.online.manager.cloudClass.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.StudentStory;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;

/**
 * 学员故事DAO
 * 
 * @author yxd
 *
 */

@Repository("studentStoryDao")
public class StudentStoryDao extends HibernateDao<StudentStory>{
	public Page<StudentStory> findCloudClassCoursePage(StudentStory studentStory, int pageNumber, int pageSize){
		Map<String,Object> paramMap=new HashMap<String,Object>();
		StringBuilder sql =new StringBuilder("select * from student_story where is_delete=0");
	 	if(studentStory.getName() != null){
	 		paramMap.put("name", "%"+studentStory.getName()+"%");
	 		sql.append(" and name like :name ");
	 	}
	 	if(studentStory.getMenuId() != null){
	 		paramMap.put("menuId", studentStory.getMenuId());
	 		sql.append(" and menu_id = :menuId ");
	 	}
	 	if(studentStory.getCourseTypeId() != null){
	 		paramMap.put("courseTypeId", studentStory.getCourseTypeId());
	 		sql.append(" and course_type_id = :courseTypeId ");
	 	}
	 	
	 	sql.append(" order by create_time desc ");
		
	    return this.findPageBySQL(sql.toString(), paramMap, StudentStory.class, pageNumber, pageSize);
	}
}
