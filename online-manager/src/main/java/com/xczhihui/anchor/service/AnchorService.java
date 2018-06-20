package com.xczhihui.anchor.service;

import java.util.List;

import com.xczhihui.anchor.vo.AnchorIncomeVO;
import com.xczhihui.bxg.online.common.domain.CourseAnchor;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.vo.LineCourseApplyStudentVO;

public interface AnchorService {

    /**
     * 根据条件分页获取课程信息。
     *
     * @return
     */
    Page<CourseAnchor> findCourseAnchorPage(CourseAnchor courseAnchor,
                                            int pageNumber, int pageSize);

    CourseAnchor findCourseAnchorById(Integer id);

    CourseAnchor findCourseAnchorByUserId(String userId);

    void updateCourseAnchor(CourseAnchor courseAnchor);

    void updatePermissions(Integer id);

    Page<AnchorIncomeVO> findCourseAnchorIncomePage(CourseAnchor searchVo,
                                                    int currentPage, int pageSize);

    /**
     * 上移
     *
     * @param Integer id
     * @return
     */
    void updateSortUpRec(Integer id);

    /**
     * 下移
     *
     * @param Integer id
     * @return
     */
    void updateSortDownRec(Integer id);

    void updateRec(String[] ids, int isRec);

    Page<CourseAnchor> findCourseAnchorRecPage(CourseAnchor searchVo,
                                               int currentPage, int pageSize);

    /**
     * 查询线下课学员列表
     *
     * @param courseName 课程名称
     * @param anchorName 主播名称
     * @param page       当前页
     * @param size       分页大小
     * @return
     */
    Page<LineCourseApplyStudentVO> list(String courseName, String anchorName, int page, int size);

    /**
     * 线下课上课状态标识更新
     *
     * @param id      报名信息id
     * @param learned 是否上课
     */
    void updateLearned(String id, boolean learned);

    /**
     * 主播类型
     *
     * @param type type
     * @return
     */
    List<CourseAnchor> list(Integer type);

    CourseAnchor findByUserId(String userId);
}
