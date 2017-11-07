package com.xczhihui.bxg.online.web.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.OpenCourseVo;

/**
 *   LecturerService:讲师业务层接口类
 * * @author Rongcai Kang
 */
public interface LiveService {

    /**
     * 首页获取公开直播课
     * @param  num:条数
     * @return
     */
    public List<OpenCourseVo> getOpenCourse(Integer num);

    /**
     * 获取首页直播
     * @param num
     * @return
     */
    OpenCourseVo getIndexLive();

    /**
     * 修改直播课程的浏览数
     *
     * @param courseId 鲜花数
     */
    public int updateBrowseSum(Integer courseId);

    /**
     * 获取直播课程信息，根据课程id查询课程
     * @param courseId 课程id号
     */
    public Map<String, Object> getOpenCourseById(Integer courseId,String planId);

    /**
     * 修改鲜花数
     * @param courseId 课程ID号
     */
    public Map<String,Object> updateFlowersNumber(Integer courseId,OnlineUser u);

    /**
     * 更新在线人数
     * @param courseId  课程id
     * @param personNumber 当前在线人数
     */
    public void  saveOnUserCount(Integer courseId,Integer personNumber);

    /**
     * 检测每个用户送花时间距离上次送花时间的差距是否是30秒
     * @param directId
     * @return true:可以送花，false：不可以送花
     */
    public Boolean checkTime(String directId,OnlineUser u);

    /**
     * 获取一周的课程表数据
     * @param currentTime 前端传过来的时间
     */
    public  List<List<OpenCourseVo>>   getCourseTimetable(long currentTime);
    
    /**
     * 跳转到直播页面
     * @param courseId
     * @param roomId
     * @param planId
     * @param request
     * @return
     */
    public ModelAndView livepage(String courseId,String roomId,String planId,HttpServletRequest request,HttpServletResponse response);

	public List<OpenCourseVo> getOpenCourse(Integer num, String id);
}
