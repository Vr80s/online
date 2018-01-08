package com.xczhihui.bxg.online.manager.cloudClass.service;

import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Grade;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.common.domain.TeachMethod;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseLecturVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.LecturerVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.MenuVo;

public interface CourseService {
	
	/**
	 * 获得一级菜单
	 * 
	 *@return List<CourseVo>一级菜单集合
	 */
	public List<Menu> getfirstMenus(Integer type);
	
	/**
	 * 获得二级菜单
	 * 
	 *@return List<CourseVo>二级菜单集合
	 */
	public List<MenuVo> getsecoundMenus(String firstMenuNumber);
	
	/**
	 * 获得主讲
	 * 
	 *@return List<LecturerVo>二级菜单集合
	 */
	public List<LecturerVo> getLecturers();

	public List<CourseVo> findByMenuId(String menuId,String courseTypeId);
	public List<CourseVo> list(String courseType);
	/**
	 * 新增课程
	 * 
	 *@return void
	 */
	public void addCourse(CourseVo courseVo);

	/**
	 * 查询对应学科下所属课程
	 * @param menuId
	 * @return
	 */
	public List<CourseVo> listByMenuId(String menuId);
	
	/**
	 * 修改课程
	 * 
	 *@return void
	 */
	public void updateCourse(CourseVo courseVo);
	
	/**
	 * 修改课程
	 * 
	 *@return void
	 */
	public void updateRecImgPath(CourseVo courseVo);
	
	
	/**
	 * 新增课程
	 * 根据课程ID号，查找对应的课程对象
	 * @param courseId
	 *@return List<CourseVo>
	 */
	public List<CourseVo> findCourseById(Integer courseId);
	
	/**
	 * 根据条件分页获取课程信息。
	 * 
	 * @param groups
	 * @param pageVo
	 * @return
	 */
    public Page<CourseVo> findCoursePage(CourseVo courseVo,  int pageNumber, int pageSize);
    
    /**
     * 根据条件分页获取推荐课程信息。
     * 
     * @param groups
     * @param pageVo
     * @return
     */
    public Page<CourseVo> findCourseRecPage(CourseVo courseVo,  int pageNumber, int pageSize);
	
	
	/**
     * 根据菜单编号，查找对应的课程列表
     * @param number 菜单编号
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize 每页显示多少行，默认20
     * @return Example 分页列表
     */
    public List<CourseLecturVo> getCourseAndLecturerlist(Integer number,Integer courseType,Integer pageNumber,Integer pageSize);


    /**
     * 根据课程ID号，查找对应的课程对象
     * @param name 暂时没用
     * @param courseId 课程id
     * @return Example 分页列表
     */
    public CourseVo getCourseById(Integer courseId);
    
    /**
	 * 修改状态(禁用or启用)
	 * @param Integer id
	 * @return
	 */
    public void updateStatus(Integer id);
   
    /**
	 * 逻辑删除
	 * @param Integer id
	 * @return
	 */
    public void deleteCourseById (Integer id);
    
    /**
	 * 上移
	 * @param Integer id
	 * @return
	 */
    public void updateSortUp (Integer id);
    
    /**
   	 * 下移
   	 * @param Integer id
   	 * @return
   	 */
     public void updateSortDown (Integer id);
     
     /**
      * 上移
      * @param Integer id
      * @return
      */
     public void updateSortUpRec (Integer id);
     
     /**
      * 下移
      * @param Integer id
      * @return
      */
     public void updateSortDownRec (Integer id);
       
    /**
	 * 删除
	 * @param Integer id
	 * @return
	 */
     public void deletes(String[] ids);
     /**
      * 更新是否推荐
      * @param Integer id
      * @return
      */
     public boolean updateRec(String[] ids,int isRecommend);

     /**
      * 查询出授课方式的下拉值
      * @return
      */
	public List<TeachMethod> getTeachMethod();

	/**
	 * 查询出课程
	 * @return
	 */
	public List<Course> getCourse();
	
	/**
	 * 查询出课程类别的下拉值
	 * @return
	 */
	public List<ScoreType> getScoreType();
	
	/**
	 * 增加课程详情
	 * @param courseId
	 * @param smallImgPath
	 * @param detailImgPath
	 * @param courseDetail
	 * @param courseOutline
	 * @param commonProblem
	 */
	public void updateCourseDetail(String courseId, String smallImgPath, String detailImgPath, String courseDetail,
			String courseOutline, String commonProblem);
	/**
	 * 获得课程详情
	 * @param courseId
	 * @return
	 */
	public Map<String,String> getCourseDetail(String courseId);
	/**
	 * 添加预览
	 * @param courseId
	 */
	public void addPreview(String courseId, String smallImgPath, String detailImgPath, String courseDetail,
			String courseOutline, String commonProblem);
	
	/**
	 * 根据名字查找课程
	 * @param String name
	 */
	public List<Course> findByName(String name);
	
	/**
	 * 设置课程描述是否在前台展示
	 * @param courseVo
	 */
	public void updateDescriptionShow(CourseVo courseVo);

	List<CourseVo> getCourselist(String search);
	/**
	 * 同步课程视频信息
	 * @param id
	 */
	public String updateCourseVideoInfo(String id);
	/**
	 * 同步CC分类
	 * @return
	 */
	public void updateCategoryInfo(String courseId) throws Exception;

	public Object lectereListByCourseIdAndRoleType(int roleType, String courseId);

	public void saveTeachers(String gradeId, String courseId, String name,
			List<String> roleTypes);

	List<Grade> getGrade(String gradeIds);

	public Course findOpenCourseById(Integer id, String version);

	public void updateSentById(Integer id);


	Course findOpenCourseById(Integer id);

	void updateSortUpForReal(Integer id);

	void updateSortDownForReal(Integer id);

	public Course getPublicCourseById(Integer courseId);

	public void deleteCourseByExamineId(String id, boolean b);

	/**
	 * 同步课程视频信息（无章节知识点版）
	 * @param id
	 */
	public String updateCourseVideo(String id);
}
