package com.xczhihui.medical.anchor.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.model.CourseApplyResource;
import com.xczhihui.medical.anchor.vo.CourseApplyInfoVO;
import com.xczhihui.medical.anchor.vo.CourseApplyResourceVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-01-19
 */
public interface ICourseApplyService extends IService<CourseApplyInfo> {

    Page<CourseApplyInfoVO> selectCourseApplyPage(Page<CourseApplyInfoVO> page, String userId, Integer courseForm, Integer multimediaType, String title);

    Page<CourseApplyInfoVO> selectCollectionApplyPage(Page<CourseApplyInfoVO> page, String userId, Integer multimediaType, String title);

    Page<CourseApplyInfoVO> selectLiveApplyPage(Page<CourseApplyInfoVO> page, String userId, String title);

    void saveCourseApply(CourseApplyInfo courseApplyInfo);

    void saveCollectionApply(CourseApplyInfo courseApplyInfo);

    List<CourseApplyResourceVO> selectAllCourseResources(String id);

    Page<CourseApplyResourceVO> selectCourseResourcePage(Page<CourseApplyResourceVO> page, String id);

    void saveCourseApplyResource(CourseApplyResource courseApplyResource);
}
