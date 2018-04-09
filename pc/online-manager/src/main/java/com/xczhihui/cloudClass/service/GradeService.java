package com.xczhihui.cloudClass.service;

import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Grade;
import com.xczhihui.cloudClass.vo.CourseVo;
import com.xczhihui.cloudClass.vo.GradeDetailVo;
import com.xczhihui.cloudClass.vo.GradeVo;

/**
 * GradeService:班级业务层接口类
 * 
 * @author Rongcai Kang
 */
public interface GradeService {

	/**
	 * 获取全部职业课班级信息
	 * 
	 * @param
	 * @return
	 */
	public Page<GradeVo> findGradeList(GradeDetailVo gradeVo,
			Integer pageNumber, Integer pageSize);

	/**
	 * 获取全部班级微课信息
	 * 
	 * @param
	 * @return
	 */
	public Page<GradeVo> findMicroGradeList(GradeDetailVo gradeVo,
			Integer pageNumber, Integer pageSize);

	/**
	 * 添加一个班级信息
	 * 
	 * @param grade
	 *            班级对象
	 * @return
	 */
	public void addGrade(Grade grade);

	public void saveTeachers(String gradeId, String courseId, String userName,
			List<String> roleTypes);

	/**
	 * 修改编辑信息
	 * 
	 * @param
	 * @return
	 */
	public void updateGrade(Grade grade);

	public void deleteGrades(String[] ids);

	/**
	 * 返回所有教师
	 * 
	 * @return
	 */
	public List<Map<String, Object>> lectereListByGradeIdAndRoleType(
			String gradeId, int roleType, String courseId);

	/**
	 * 获取全部课程信息
	 * 
	 * @param
	 * @return
	 */
	public List<CourseVo> findCourseList(Integer pageNumber, Integer pageSize);

	public Map<String, Object> findMenuByGradeId(String gradeId);

	public List<GradeDetailVo> getGradeById(Integer gradeId);

	/**
	 * 根据班级ID号，将对应的班级禁用
	 * 
	 * @param gradeId
	 *            班级id
	 * @return
	 */
	public int updateGradeStatus(Integer gradeId, Integer isGradeStatus);

	public Grade findById(Integer id);

	/**
	 * 根据ID查找当前对象排号之前的最接近它的那个班级对象
	 * 
	 * @param id
	 * @return
	 */
	public int updatePreSortEntity(Integer id);

	/**
	 * 根据ID查找当前对象排号之之后的最接近它的那个班级对象
	 * 
	 * @param id
	 * @return
	 */
	public int updateNextSortEntity(Integer id);

	/**
	 * 根据名字查找班级对象
	 * 
	 * @param String
	 *            name
	 * @return Grade
	 */
	public List<Grade> getByName(String name);

	/**
	 * 获取到班级
	 * 
	 * @return
	 */
	public List<Grade> getGradeByCourseId(String courseId);

	/**
	 * 验证某个班级是否可以新建学习计划
	 * 
	 * @param courseId
	 * @return
	 */
	public boolean checkBuildPlan(String courseId);
}
