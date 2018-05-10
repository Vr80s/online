package com.xczhihui.bxg.online.web.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.OpenCourseVo;

/**
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
     * @param user
     */
    public Map<String, Object> getOpenCourseById(Integer courseId, OnlineUser user);

    /**
     * 跳转到直播页面
     * @param courseId
     * @param roomId
     * @param planId
     * @param request
     * @return
     */
    public ModelAndView livepage(String courseId,HttpServletRequest request,HttpServletResponse response);

	public List<OpenCourseVo> getOpenCourse(Integer num, String id);
}
