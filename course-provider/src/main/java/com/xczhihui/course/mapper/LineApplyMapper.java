package com.xczhihui.course.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.LineApply;
import com.xczhihui.course.vo.LineCourseApplyStudentVO;

/**
 * @author hejiwei
 */
public interface LineApplyMapper extends BaseMapper<LineApply> {

    /**
     * 通过用户id与课程id查询线下课报名信息
     *
     * @param userId   用户id
     * @param courseId 课程信息
     * @return 课程报名信息
     */
    @Select("SELECT *" +
            " FROM oe_line_apply ola " +
            " where ola.user_id = #{userId} and ola.course_id = #{courseId} and ola.is_delete = 0")
    LineApply findLineApplyByUserIdAndCourseId(@Param("userId") String userId, @Param("courseId") Integer courseId);

    /**
     * 通过用户id查询最新一条报名信息
     *
     * @param userId 用户id
     * @return 课程报名信息
     */
    @Select({"SELECT ola.real_name as realName, ola.sex, ola.mobile, ola.wechat_no as wechatNo" +
            " FROM oe_line_apply ola " +
            " where ola.user_id = #{userId} and ola.is_delete = 0" +
            " order by create_time desc" +
            " limit 1"})
    Map<String, Object> newestApplyInfoByUserId(@Param("userId") String userId);

    /**
     * 查询线下课程的学员列表
     *
     * @param page     分页参数
     * @param courseId 课程id
     * @param anchorId 主播id
     * @return
     */
    @Select({"SELECT la.real_name as realName, la.sex, la.mobile, la.wechat_no as wechatNo, la.learned, ac.create_time as createTime, la.id, oc.grade_name as courseName" +
            " FROM oe_line_apply la join apply_r_grade_course ac on la.course_id = ac.course_id, oe_course oc" +
            " WHERE oc.id = la.course_id AND oc.`user_lecturer_id` = #{anchorId} AND (#{courseId} is null OR la.course_id = #{courseId})" +
            " ORDER BY la.create_time desc"})
    List<LineCourseApplyStudentVO> listLineApplyStudent(Page<LineCourseApplyStudentVO> page, @Param("courseId") Integer courseId, @Param("anchorId") String anchorId);

    /**
     * 更新学员上课状态
     *
     * @param id       id
     * @param learned  是否上课
     * @param anchorId 主播id
     * @return
     */
    @Update({"update oe_line_apply set learned = #{learned} where id = #{id} and anchor_id = #{anchorId}"})
    int updateLearned(@Param("id") String id, @Param("learned") boolean learned, @Param("anchorId") String anchorId);
}