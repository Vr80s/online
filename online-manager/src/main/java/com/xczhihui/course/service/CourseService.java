package com.xczhihui.course.service;

import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.CourseVo;
import com.xczhihui.course.vo.LecturerVo;
import com.xczhihui.course.vo.MenuVo;

public interface CourseService {

    /**
     * 获得一级菜单
     *
     * @return List<CourseVo>一级菜单集合
     */
    List<Menu> getfirstMenus(Integer type);

    /**
     * 获得二级菜单
     *
     * @return List<CourseVo>二级菜单集合
     */
    List<MenuVo> getsecoundMenus(String firstMenuNumber);

    /**
     * 获得主讲
     *
     * @return List<LecturerVo>二级菜单集合
     */
    List<LecturerVo> getLecturers();

    List<CourseVo> findByMenuId(String menuId, String courseTypeId);

    List<CourseVo> list(String courseType);

    /**
     * 新增课程
     *
     * @return void
     */
    void addCourse(CourseVo courseVo);

    /**
     * 查询对应学科下所属课程
     *
     * @param menuId
     * @return
     */
    List<CourseVo> listByMenuId(String menuId);

    /**
     * 修改课程
     *
     * @return void
     */
    void updateCourse(CourseVo courseVo);

    void checkName(Integer id, String courseName, Integer applyCourseId);

    /**
     * 修改课程
     *
     * @return void
     */
    void updateRecImgPath(CourseVo courseVo);

    /**
     * 新增课程 根据课程ID号，查找对应的课程对象
     *
     * @param courseId
     * @return List<CourseVo>
     */
    List<CourseVo> findCourseById(Integer courseId);

    /**
     * 根据条件分页获取课程信息。
     *
     * @param groups
     * @param pageVo
     * @return
     */
    Page<CourseVo> findCoursePage(CourseVo courseVo, int pageNumber,
                                  int pageSize);

    /**
     * 根据条件分页获取推荐课程信息。
     *
     * @param groups
     * @param pageVo
     * @return
     */
    Page<CourseVo> findCourseRecPage(CourseVo courseVo, int pageNumber,
                                     int pageSize);

    /**
     * 根据菜单编号，查找对应的课程列表
     *
     * @param number     菜单编号
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize   每页显示多少行，默认20
     * @return Example 分页列表
     */
    List<CourseLecturVo> getCourseAndLecturerlist(Integer number,
                                                  Integer courseType, Integer pageNumber, Integer pageSize);

    /**
     * 根据课程ID号，查找对应的课程对象
     *
     * @param name     暂时没用
     * @param courseId 课程id
     * @return Example 分页列表
     */
    CourseVo getCourseById(Integer courseId);

    /**
     * 修改状态(禁用or启用)
     *
     * @param Integer id
     * @return
     */
    void updateStatus(Integer id);

    /**
     * 逻辑删除
     *
     * @param Integer id
     * @return
     */
    void deleteCourseById(Integer id);

    /**
     * 上移
     *
     * @param Integer id
     * @return
     */
    void updateSortUp(Integer id);

    /**
     * 下移
     *
     * @param Integer id
     * @return
     */
    void updateSortDown(Integer id);

    /**
     * 上移
     *
     * @param Integer id
     * @return
     */
    void updateSortUpRec(Integer id);

    /**
     * 上移
     *
     * @param Integer id
     * @return
     */
    void updateCitySortUp(Integer id);

    /**
     * 下移
     *
     * @param Integer id
     * @return
     */
    void updateCitySortDown(Integer id);

    /**
     * 下移
     *
     * @param Integer id
     * @return
     */
    void updateSortDownRec(Integer id);

    /**
     * 删除
     *
     * @param Integer id
     * @return
     */
    void deletes(String[] ids);

    /**
     * 更新是否推荐
     *
     * @param Integer id
     * @return
     */
    boolean updateRec(String[] ids, int isRecommend);

    /**
     * 更新城市是否推荐
     *
     * @param Integer id
     * @return
     */
    boolean updateCityRec(String[] ids, int isRecommend);

    /**
     * 查询出授课方式的下拉值
     *
     * @return
     */
    List<TeachMethod> getTeachMethod();

    /**
     * 查询出课程
     *
     * @return
     */
    List<Course> getCourse();

    /**
     * 查询出课程类别的下拉值
     *
     * @return
     */
    List<ScoreType> getScoreType();

    /**
     * 增加课程详情
     *
     * @param courseId
     * @param smallImgPath
     * @param detailImgPath
     * @param courseDetail
     * @param courseOutline
     * @param commonProblem
     * @param lecturerDescription
     */
    void updateCourseDetail(String courseId, String smallImgPath,
                            String detailImgPath, String courseDetail, String courseOutline,
                            String commonProblem, String lecturerDescription);

    /**
     * 获得课程详情
     *
     * @param courseId
     * @return
     */
    Map<String, String> getCourseDetail(String courseId);

    /**
     * 添加预览
     *
     * @param courseId
     */
    void addPreview(String courseId, String smallImgPath,
                    String detailImgPath, String courseDetail, String courseOutline,
                    String commonProblem);

    /**
     * 根据名字查找课程
     *
     * @param String name
     */
    List<Course> findByName(String name);

    /**
     * 设置课程描述是否在前台展示
     *
     * @param courseVo
     */
    void updateDescriptionShow(CourseVo courseVo);

    List<CourseVo> getCourselist(String search);

    /**
     * 同步课程视频信息
     *
     * @param id
     */
    String updateCourseVideoInfo(String id);

    /**
     * 同步CC分类
     *
     * @return
     */
    void updateCategoryInfo(String courseId) throws Exception;

    Object lectereListByCourseIdAndRoleType(int roleType, String courseId);

    void saveTeachers(String gradeId, String courseId, String name,
                      List<String> roleTypes);

    List<Grade> getGrade(String gradeIds);

    Course findOpenCourseById(Integer id, String version);

    void updateSentById(Integer id);

    Course findOpenCourseById(Integer id);

    void updateSortUpForReal(Integer id);

    void updateSortDownForReal(Integer id);

    Course getPublicCourseById(Integer courseId);

    void deleteCourseByExamineId(Integer id, boolean b);

    /**
     * 同步课程视频信息（无章节知识点版）
     *
     * @param id
     */
    String updateCourseVideo(String id);

    void addCourseCity(String city);

    /**
     * Description：根据id获取线下课
     *
     * @param cityId
     * @return OffLineCity
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    OffLineCity findCourseCityByName(Integer cityId);

    /**
     * Description：根据城市名判断是否存在此城市
     *
     * @param city
     * @return Boolean
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    Boolean findCourseCityByName(String city);

    /**
     * Description：当禁用或者启用时，如果这个城市是最后一个。就直接删除吧,查询不出来。
     * 也就禁用了。在删除线下课时，如果判断得到这个城市是最后一个的话，就也删除这个
     *
     * @param city
     * @return void
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    void deleteCourseCityByName(String city);

    /**
     * 获取所有的城市管理数据 Description：
     *
     * @return List<OffLineCity>
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    Page<OffLineCity> getCourseCityList(OffLineCity searchVo,
                                        Integer pageNumber, Integer pageSize);

    void updateCourseCityStatus(Integer id);

    void deleteCourseCityStatus(Integer id);

    void updateCourseCity(OffLineCity offLineCity);

    Course findCourseInfoById(Integer id);

    /**
     * Description：设置推荐值 creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>
     * email: wangyishuai@ixincheng.com
     * @Date: 2018/3/9 14:19
     **/
    void updateRecommendSort(Integer id, Integer recommendSort,
                             String recommendTime);

    /**
     * Description：过了推荐时效的推荐值恢复到默认值 creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>
     * email: wangyishuai@ixincheng.com
     * @Date: 2018/3/14 18:00
     **/
    void updateDefaultSort();

    /**
     * 设置默认学习人数
     *
     * @param id
     * @param recommendSort
     */
    void updatedefaultStudent(Integer id, Integer recommendSort);

    /**
     * 更改回放状态
     *
     * @param courseId
     * @param i
     */
    void updatePlaybackState(Integer courseId, int i);

    /**
     * 课程简单信息列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<CourseVo> listSimpleCourse(int pageNumber,
                                    int pageSize);
}