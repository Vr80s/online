package com.xczhihui.course.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.bean.ShareInfoVo;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.vo.CollectionCoursesVo;
import com.xczhihui.course.vo.CourseLecturVo;

public interface ICourseService {

    Page<CourseLecturVo> selectCoursePage(Page<CourseLecturVo> page);


    /**
     * Description:通过课程id查找课程详细
     *
     * @param courseId
     * @return CourseLecturVo
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    CourseLecturVo selectCourseDetailsById(String userId, Integer courseId);


    /**
     * Description：学习中心中  我的课程及以结束课程
     *
     * @return List<CourseLecturVo>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    List<Map<String, Object>> selectLearningCourseListByUserId(Integer pageSize, String userId);

    /**
     * Description：查询用户已购买课程的数量
     *
     * @return Integer
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    Integer selectMyFreeCourseListCount(String userId);

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


    List<CollectionCoursesVo> selectCoursesByCollectionId(Integer collectionId);

    /**
     * Description：查找此主播最近一次的课程
     *
     * @param lecturerId
     * @return CourseLecturVo
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    CourseLecturVo selectLecturerRecentCourse(String lecturerId);

    CourseLecturVo selectLecturerRecentCourse(String lecturerId, boolean onlyFreee);

    /**
     * Description：根据主播id得到主播的所有课程，按照发布时间排序
     *
     * @param page
     * @param lecturerId
     * @return Page<CourseLecturVo>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    Page<CourseLecturVo> selectLecturerAllCourse(Page<CourseLecturVo> page, String lecturerId);

    Page<CourseLecturVo> selectLecturerAllCourseByType(Page<CourseLecturVo> page,
                                                       String lecturerId, Integer type, boolean onlyFree);

    /**
     * Description：查找用户控制台的课程数据
     *
     * @param id
     * @return List<CourseLecturVo>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    List<CourseLecturVo> selectUserConsoleCourse(String id);

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


    Page<CourseLecturVo> selectAppCourseApplyPage(Page<CourseLecturVo> page,
                                                  String id, Integer courseFrom, Integer multimediaType);


    CourseLecturVo selectCourseMiddleDetailsById(String userId, Integer courseId);

    /**
     * Description：查看主播控制台的---》直播间的分页
     *
     * @param page
     * @param id
     * @return List<CourseLecturVo>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    List<CourseLecturVo> selectUserConsoleCourseLiveByPage(Page<CourseLecturVo> page, String id);

    /**
     * Description：查询听课列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/2/28 20:52
     **/
    List<CourseLecturVo> listenCourseList();

    List<CourseLecturVo> listenCourseList(boolean onlyFree);


    /**
     * Description：用户当前课程状态   User current course status. 用户登录了
     *
     * @param courseId
     * @param id
     * @return CourseLecturVo
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    CourseLecturVo selectUserCurrentCourseStatus(Integer courseId,
                                                 String id);

    /**
     * Description：课程状态   User current course status. 用户没有登录
     *
     * @param courseId
     * @return CourseLecturVo
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    CourseLecturVo selectCurrentCourseStatus(Integer courseId);


    String selectCourseDescription(Integer type, String typeId);

    /**
     * 我的课程类别查询  type 1 查询我的课程  2 查询已结束课程
     *
     * @param num
     * @param pageSize
     * @param id
     * @param type
     * @return
     */
    Page<CourseLecturVo> myCourseType(Page<CourseLecturVo> page, String id, Integer type);

    /**
     * Description：获取直播状态列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/5/10 14:33
     **/
    List<Map<String, Object>> getLiveStatusList();

    /**
     * 是否付费
     * Description：
     * creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/5/10 14:42
     **/
    List<Map<String, Object>> getPayStatusList();


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
    CourseLecturVo selectCourseStatusDeleteUserLecturerId(Integer courseId);

    String getLiveCourseUrl4Wechat(String userId, String courseId);

    /**
     * 查询主播的线下课
     *
     * @param anchorId 主播id
     * @return
     */
    Page<Map<String, Object>> selectOfflineCourseByAnchorId(String anchorId);


    /**
     * 查看用户的课程类型数量
     *
     * @param userId
     * @param type
     * @return
     */
    Integer selectLiveCountByUserIdAndType(String userId, Integer type);

    List<Map<String, Object>> list(Integer type, String userId);


    ShareInfoVo selectShareInfoByType(Integer type, String id);

    Course findSimpleInfoById(int id);

    /**
     * 随机查询推荐课程
     *
     * @param page 分页参数查询多少条
     * @param type 等于null时，查所有。  1 直播  2 视频音频  3 线下课
     * @return
     */
    List<CourseLecturVo> selectCourseByLearndCount(Page<CourseLecturVo> page, Integer type);

    /**
     * 获取所有上架课程
     *
     * @return
     */
    List<Course> getAllCourseByStatus();

    /**
     * 查看医师的直播间的课程信息
     *
     * @param lecturerId
     * @param userId
     * @param onlyFreee
     * @return
     */
    List<Map<String, Object>> doctorCourseList(String lecturerId, String userId, boolean onlyFreee);

    /**
     * 查询师承课程
     *
     * @param userId userId
     * @param page   page
     * @return
     */
    List<CourseLecturVo> selectTeachingCoursesByUserId(Page<CourseLecturVo> page, String lecturerId, String userId);

    boolean selectQualification4TeachingCourse(String userId, Integer courseId);


    /**
     * 查看医师直播间的最近一次直播课
     *
     * @param userId
     * @param onlyFreee
     * @return
     */
    CourseLecturVo selectDoctorLiveRoomRecentCourse(String userId, boolean onlyFreee);

    /**
     * @param @param recordId
     * @param @param status    参数
     * @return void    返回类型
     * @throws
     * @Title: updatePlayBackStatusByRecordId
     * @Description: 通过回放id 更改回放状态
     * @author yangxuan
     */
    void updatePlayBackStatusByRecordId(String recordId, Integer status);

    /**
     * 更改回放状态，并且发送回放状态IM消息
     *
     * @param recordId
     * @param status   参数
     * @return void    返回类型
     */
    void updatePlayBackStatusAndSendVahllYunMessageByRecordId(String recordId, Integer status) throws Exception;

    /**
     * 更新课程直播状态
     *
     * @param event
     * @param roomId
     * @return
     */
    Integer updateCourseLiveStatus(String event, String roomId, String clientType);


    /**
     * @param channelId
     * @param 参数
     * @return Integer    返回类型
     * @throws
     * @Title: updateCourseLiveStatus
     * @Description: 通过渠道id更改直播中的课程状况
     * @author yangxuan
     */
    Integer updateCourseLiveCase(String channelId);

    /**
     * 查询视频直播的直播状态
     *
     * @param directId 视频id
     * @return
     */
    Integer findLiveStatusByDirectId(String directId);

    Integer getCourseLivePushStreamStatus(Integer courseId);


    /**
     * <p>Title: createTherapyLive</p>
     * <p>Description:审核通过创建诊疗直播信息 </p>
     *
     * @param id
     */
    Integer createTherapyLive(Integer id, Integer clientType, String accountId) throws Exception;


    /**
     * 通过课程id查询课程
     *
     * @param courseId 课程id
     * @return
     */
    Course selectById(Integer courseId);

    void deleteCourseMessage(Integer courseId);

    /**
     * 直播中的课程
     *
     * @return
     */
    List<Course> listLiving();

    String getHostCollectionUpdateDateText(Integer collectionId);

    /**
     * 获取渠道id
     *
     * @param inavId
     * @return
     */
    String selectChannelIdByInavId(String inavId);


	/**  
	 * <p>Title: selectRichDetailes</p>  
	 * <p>Description: </p>  
	 * @param typeId
	 * @return  
	 */ 
	Map<String,Object> selectRichDetailes(Integer typeId);
}
