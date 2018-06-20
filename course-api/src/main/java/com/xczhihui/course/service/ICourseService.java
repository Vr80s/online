package com.xczhihui.course.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.vo.CourseLecturVo;

public interface ICourseService {

    public Page<CourseLecturVo> selectCoursePage(Page<CourseLecturVo> page);


    /**
     * Description:通过课程id查找课程详细
     *
     * @param courseId
     * @return CourseLecturVo
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public CourseLecturVo selectCourseDetailsById(Integer courseId);


    /**
     * Description：学习中心中的课程
     *
     * @return List<CourseLecturVo>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public List<CourseLecturVo> selectLearningCourseListByUserId(Integer pageSize, String userId);

    /**
     * Description：查询用户已购买课程的数量
     *
     * @return Integer
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public Integer selectMyFreeCourseListCount(String userId);

    /**
     * Description：分页查询用于已购买课程列表
     *
     * @param page
     * @param userId
     * @return Page<CourseLecturVo>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    Page<CourseLecturVo> selectMyPurchasedCourseList(Page<CourseLecturVo> page,
                                                     String userId);


    List<CourseLecturVo> selectCoursesByCollectionId(Integer collectionId);

    /**
     * Description：查找此主播最近一次的课程
     *
     * @param lecturerId
     * @return CourseLecturVo
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public CourseLecturVo selectLecturerRecentCourse(String lecturerId);

    CourseLecturVo selectLecturerRecentCourse(String lecturerId, boolean onlyFreee);

    /**
     * Description：根据主播id得到主播的所有课程，按照发布时间排序
     *
     * @param page
     * @param lecturerId
     * @return Page<CourseLecturVo>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public Page<CourseLecturVo> selectLecturerAllCourse(Page<CourseLecturVo> page, String lecturerId);

    Page<CourseLecturVo> selectLecturerAllCourse(Page<CourseLecturVo> page, 
    		String lecturerId,Integer type, boolean onlyFree);

    /**
     * Description：查找用户控制台的课程数据
     *
     * @param id
     * @return List<CourseLecturVo>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public List<CourseLecturVo> selectUserConsoleCourse(String id);

    /**
     * Description：猜你喜欢接口，传递一个分页参数，随机取出这些数据
     *
     * @param page
     * @param menuId
     * @return Page<CourseLecturVo>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    Page<CourseLecturVo> selectMenuTypeAndRandCourse(Page<CourseLecturVo> page,
                                                     Integer menuId);


    public Page<CourseLecturVo> selectAppCourseApplyPage(Page<CourseLecturVo> page,
                                                         String id, Integer courseFrom, Integer multimediaType);


    public CourseLecturVo selectCourseMiddleDetailsById(Integer courseId);

    /**
     * Description：查看主播控制台的---》直播间的分页
     *
     * @param page
     * @param id
     * @return List<CourseLecturVo>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public List<CourseLecturVo> selectUserConsoleCourseLiveByPage(Page<CourseLecturVo> page, String id);

    /**
     * Description：查询听课列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/2/28 20:52
     **/
    public List<CourseLecturVo> listenCourseList();

    List<CourseLecturVo> listenCourseList(boolean onlyFree);

    /**
     * Description：查询直播课程列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/3/1 10:49
     **/
    public List<CourseLecturVo> findLiveListInfo();

    /**
     * Description：用户当前课程状态   User current course status. 用户登录了
     *
     * @param courseId
     * @param id
     * @return CourseLecturVo
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public CourseLecturVo selectUserCurrentCourseStatus(Integer courseId,
                                                        String id);

    /**
     * Description：课程状态   User current course status. 用户没有登录
     *
     * @param courseId
     * @return CourseLecturVo
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public CourseLecturVo selectCurrentCourseStatus(Integer courseId);


    public String selectCourseDescription(Integer type, String typeId);

    /**
     * 我的课程类别查询  type 1 查询我的课程  2 查询已结束课程
     *
     * @param num
     * @param pageSize
     * @param id
     * @param type
     * @return
     */
    public Page<CourseLecturVo> myCourseType(Page<CourseLecturVo> page, String id, Integer type);

    /**
     * Description：获取直播状态列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/5/10 14:33
     **/
    public List<Map<String, Object>> getLiveStatusList();

    /**
     * 是否付费
     * Description：
     * creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/5/10 14:42
     **/
    public List<Map<String, Object>> getPayStatusList();


    /**
     * Description：pc 课程详情页面中的 推荐课程。先取推荐值最高的10个课程，然后在10个里面在随机取两个
     *
     * @param page
     * @param menuId
     * @return Page<CourseLecturVo>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    List<CourseLecturVo> selectRecommendSortAndRandCourse(
            Page<CourseLecturVo> page);

    /**
     * 查询课程列表通过ids
     *
     * @param ids ids
     * @return
     */
    List<Course> findByIds(List<Integer> ids);

    /**
     * 某个课程的同类课程
     *
     * @param courseId 课程id
     * @param menuId   分类id
     * @return
     */
    List<Map<String, Object>> findByMenuIdExcludeId(Integer menuId, Integer courseId);

    /**
     * 查看这个课程的 StatusDeleteUserLecturerId
     *
     * @param i
     * @return
     */
    public CourseLecturVo selectCourseStatusDeleteUserLecturerId(Integer courseId);

    String getLiveCourseUrl4Wechat(String userId, String courseId);

    /**
     * 查询主播的线下课
     *
     * @param anchorId 主播id
     * @return
     */
    Page<Map<String, Object>> selectOfflineCourseByAnchorId(String anchorId);

}
