package com.xczhihui.course.service.impl;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.CourseDescription;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xczhihui.course.dao.CourseDescriptionDao;
import com.xczhihui.course.service.CourseDescriptionService;
import com.xczhihui.course.vo.CourseDescriptionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseDescriptionServiceImpl extends OnlineBaseServiceImpl implements CourseDescriptionService {

	@Autowired
    private CourseDescriptionDao courseDescriptionDao;
	
	@Override
	public List<Map<String,Object>> getDesList(CourseDescriptionVo courseDescriptionVo) {
		List<Map<String,Object>> listCd = courseDescriptionDao.getDesList(courseDescriptionVo);
		return listCd;
	}
	/**
	 * 新增的故事  预览状态，未删除，标题、内容全部为空
	 */
	@Override
	public void addCourseDescription(CourseDescriptionVo courseDescriptionVo) {
		String sql="select ifnull(max(sort),0) from oe_course_description t where t.course_id= '"+courseDescriptionVo.getCourseId()+"'";
//		Map<String,Object> paramMap=new HashMap<String,Object>();
//		paramMap.put("courseId", courseDescriptionVo.getCourseId());
		int sort = dao.queryForInt(sql, null) + 1;

		CourseDescription courseDescription = new CourseDescription();
		courseDescription.setCourseTitle(courseDescriptionVo.getCourseTitle());
		courseDescription.setCourseTitlePreview(courseDescriptionVo.getCourseTitlePreview());
		courseDescription.setCreateTime(new Date());
		courseDescription.setCourseId(courseDescriptionVo.getCourseId());
		courseDescription.setPreview(courseDescriptionVo.getPreview());
		courseDescription.setStatus(1);//一直为1 目前没有用用这个字段
		courseDescription.setSort(sort);   //排序字段
		courseDescription.setCourseContent("");
		courseDescription.setCreatePerson(courseDescriptionVo.getCreatePerson());
		
		dao.save(courseDescription);
	}

	@Override
	public void updateCourseDescription(CourseDescriptionVo courseDescriptionVo) {
		CourseDescription courseDescription = dao.findOneEntitiyByProperty(CourseDescription.class, "id", courseDescriptionVo.getId());
		courseDescription.setCourseTitle(courseDescriptionVo.getCourseTitle());
		courseDescription.setStatus(courseDescriptionVo.getStatus());
		courseDescription.setCourseContent(courseDescriptionVo.getCourseContent());
		courseDescription.setCourseContentPreview(courseDescriptionVo.getCourseContent());
		courseDescription.setPreview(false);
		dao.update(courseDescription);
	}
	
	@Override
	public void updateTestCourseDescription(CourseDescriptionVo courseDescriptionVo) {
		CourseDescription courseDescription = dao.findOneEntitiyByProperty(CourseDescription.class, "id", courseDescriptionVo.getId());
		courseDescription.setCourseTitle(courseDescriptionVo.getCourseTitle());
		courseDescription.setStatus(courseDescriptionVo.getStatus());
		courseDescription.setCourseContentPreview(courseDescriptionVo.getCourseContentPreview());
		dao.update(courseDescription);
	}

	@Override
	public void deletes(String[] ids) {
		// TODO Auto-generated method stub
		for(String id:ids){
			String hqlPre="from CourseDescription where isDelete=0 and id = ?";
			CourseDescription courseDescription= dao.findByHQLOne(hqlPre,new Object[] {id});
            if(courseDescription !=null){
            	courseDescription.setDelete(true);
                dao.update(courseDescription);
            }
        }
	}
}
