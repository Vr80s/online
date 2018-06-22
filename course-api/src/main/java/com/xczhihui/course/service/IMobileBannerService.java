package com.xczhihui.course.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.course.vo.QueryConditionVo;

public interface IMobileBannerService {

    List<MobileBanner> selectMobileBannerPage(Integer type, boolean onlyFree, String source);

    /**
     * Description：app端banner列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/5/10 11:25
     **/
    public List<MobileBanner> selectMobileBannerPage(Integer type, String source);

    /**
     * Description：增加点击次数
     * creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/5/10 11:25
     **/
    void addClickNum(String id);

    /**
     * 推荐页面 课程集合
     *
     * @param menuList     启用的课程分类列表
     * @param pageSizeUp   精品课程、最新课程等获取几条数据
     * @param pageSizeDown 分类课程获取获取几条数据
     * @return
     */
    public List<Map<String, Object>> recommendCourseList(List<MenuVo> menuList, Integer pageSizeUp, Integer pageSizeDown);

    public List<Map<String, Object>> recommendCourseList(List<MenuVo> menuList, Integer pageSizeUp, Integer pageSizeDown, boolean isFree);

    /**
     * 推荐页面 课程集合
     *
     * @param menuList 启用的课程分类列表
     * @param pageSize 所有的课程类别获取同样的条数
     * @return
     */
    public List<Map<String, Object>> recommendCourseList(List<MenuVo> menuList, Integer pageSize);

    /**
     * 线下培训班 课程集合
     *
     * @param cityList
     * @param pageSizeUp
     * @param pageSizeDown
     * @return
     */
    public List<Map<String, Object>> realCourseList(List<OfflineCity> cityList, Integer pageSizeUp, Integer pageSizeDown);

    public List<Map<String, Object>> realCourseList(List<OfflineCity> cityList, Integer pageSizeUp, Integer pageSizeDown, boolean onlyFree);

    /**
     * 直播  课程集合
     *
     * @param pageSizeUp
     * @param pageSizeDown
     * @return
     */
    public List<Map<String, Object>> liveCourseList(Integer pageSizeUp, Integer pageSizeDown);
    public List<Map<String, Object>> liveCourseList(Integer pageSizeUp, Integer pageSizeDown, boolean onlyFree);

    /**
     * 直播  课程集合
     *
     * @param pageSize
     * @return
     */
    public List<Map<String, Object>> liveCourseList(Integer pageSize);

    /**
     * 检索列表  带有关键字的查找
     *
     * @param queryConditionVo
     * @return
     */
    public List<CourseLecturVo> searchQueryKeyCourseList(QueryConditionVo queryConditionVo);


    /**
     * 检索列表 没有带关键字的查找
     *
     * @param queryConditionVo
     * @return
     */
    public List<CourseLecturVo> searchCourseList(QueryConditionVo queryConditionVo);

    public Page<CourseLecturVo> searchQueryKeyCourseList(Page<CourseLecturVo> page, QueryConditionVo queryConditionVo);

    Page<CourseLecturVo> searchQueryKeyCourseList(Page<CourseLecturVo> page, QueryConditionVo queryConditionVo, boolean onlyFree);

    public Page<CourseLecturVo> searchCourseList(Page<CourseLecturVo> page, QueryConditionVo queryConditionVo);

    Page<CourseLecturVo> searchCourseList(Page<CourseLecturVo> page, QueryConditionVo queryConditionVo, boolean onlyFree);

    /**
     * pc端首页查询
     *
     * @param value
     * @return
     */
    public Map<String, Object> selectPcIndex(Integer value);

    /**
     * 下架课程推荐页面
     *
     * @param page
     * @param courseId
     * @return
     */
    public List<CourseLecturVo> selectUnshelveRecommenCourse(Integer pageSize);

}
