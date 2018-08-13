package com.xczhihui.medical.anchor.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.vo.CourseApplyInfoVO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-01-19
 */
public interface CourseApplyInfoMapper extends BaseMapper<CourseApplyInfo> {

    /**
     * Description：分页获取主播申请课程列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 11:30 2018/1/19 0019
     **/
    List<CourseApplyInfoVO> selectCourseApplyPage(@Param("page") Page<CourseApplyInfoVO> page, @Param("userId") String userId, @Param("courseForm") Integer courseForm, @Param("multimediaType") Integer multimediaType, @Param("title") String title, @Param("teaching") int teaching);

    /**
     * Description：分页获取主播申请专辑列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 11:41 2018/1/19 0019
     **/
    List<CourseApplyInfoVO> selectCollectionApplyPage(@Param("page") Page<CourseApplyInfoVO> page, @Param("userId") String userId, @Param("multimediaType") Integer multimediaType, @Param("title") String title);

    List<CourseApplyInfoVO> selectLiveApplyPage(Page<CourseApplyInfoVO> page, @Param("userId") String userId, @Param("title") String title);

    /**
     * Description：上/下架课程
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 10:29 2018/2/1 0001
     **/
    int updateSaleState(@Param("userId") String userId, @Param("courseApplyId") String courseApplyId, @Param("state") Integer state);

    List<Map> selectCoinTransactionPage(@Param("page") Page<Map> page, @Param("userId") String userId);

    List<Map> selectRmbTransactionPage(@Param("page") Page<Map> page, @Param("userId") String userId);

    List<CourseApplyInfoVO> selectAllCourses(@Param("userId") String userId, @Param("multimediaType") Integer multimediaType);

    CourseApplyInfo selectCourseApplyById(@Param("userId") String userId, @Param("caiId") Integer caiId);

    void deleteCourseApplyById(Integer id);

    Integer selectCourseApplyForValidate(@Param("title") String title, @Param("oldApplyInfoId") Integer oldApplyInfoId);

    Integer selectCourseForValidate(@Param("title") String title, @Param("oldApplyInfoIds") List<Integer> oldApplyInfoIds);

    List<CourseApplyInfo> selectCourseApplyByCollectionId(@Param("id") Integer id);

    List<CourseApplyInfo> selectCollectionApplyByCourseApplyId(Integer id);

    String selectCourseStastusByApplyId(Integer id);

    Integer getParentIdByChildId(Integer id);

    Integer getIsStatusChange(@Param("userId") String userId, @Param("courseApplyId") String courseApplyId, @Param("state") Integer state);

    @Select("SELECT COUNT(ct.id) FROM `course_teaching` ct WHERE ct.`deleted`=0 AND ct.`course_id`=#{courseId}")
    int getCourseTeachingCountByCourseId(@Param("courseId") Integer courseId);

    @Update("UPDATE `oe_course` oc SET oc.`is_record` = #{record}" +
            " WHERE oc.`user_lecturer_id` = #{userId} AND oc.`id`= #{courseId}")
    void saveCourseRecordStatus(@Param("courseId") int courseId, @Param("userId") String userId, @Param("record") int record);
}