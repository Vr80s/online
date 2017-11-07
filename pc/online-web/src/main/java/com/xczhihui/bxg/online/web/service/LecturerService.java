package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.online.web.vo.LecturVo;

import java.util.List;

/**
 *   LecturerService:讲师业务层接口类
 * * @author Rongcai Kang
 */
public interface LecturerService {

    /**
     * 根据课程编号，查找课程实例
     * @param lecturerId 课程的ID编号
     * @return  课程对象
     */
    public List<LecturVo> findLecturerById(Integer  lecturerId);

    /**
     * 根据课程查询所有讲师
     * @param courseId
     * @return
     */
    public List<LecturVo> listByCourseId(String courseId);

}
