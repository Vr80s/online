package com.xczhihui.course.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.LineApply;
import com.xczhihui.course.vo.LineCourseApplyStudentVO;

/**
 * @author hejiwei
 */
public interface ILineApplyService {

    /**
     * 查询用户最新一次线下课报名信息
     *
     * @param userId 用户id
     * @return 线下课报名信息
     */
    Map<String, Object> findLineApplyByUserId(String userId);

    /**
     * 判断用户对该课程是否已经提交报名信息
     *
     * @param courseId 课程Id
     * @param userId   用户id
     * @return 是否已经填写报名信息
     */
    boolean submitted(String userId, Integer courseId);

    /**
     * 保存或更新用户的线下课报名信息
     *
     * @param lockId    用户id
     * @param lineApply 报名信息
     */
    void saveOrUpdate(String lockId, LineApply lineApply);

    /**
     * 查询线下课报名的学员
     *
     * @param page     分页参数
     * @param courseId 课程id（可选）
     * @param anchorId 主播id
     * @return
     */
    Page<LineCourseApplyStudentVO> listLineApplyStudent(Page<LineCourseApplyStudentVO> page, Integer courseId, String anchorId);

    /**
     * 更新上课状态
     *
     * @param id       课程id
     * @param learned  是否上课
     * @param anchorId 主播id
     * @return
     */
    boolean updateLearned(String id, boolean learned, String anchorId);

    /**
     * 查询课程的报名信息
     *
     * @param courseId 课程id
     * @param userId 用户id
     * @return
     */
    LineCourseApplyStudentVO findByCourseIdAndUserId(Integer courseId, String userId);
}
