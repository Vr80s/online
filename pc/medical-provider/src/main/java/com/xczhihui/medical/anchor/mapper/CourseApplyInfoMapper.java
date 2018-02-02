package com.xczhihui.medical.anchor.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.vo.CourseApplyInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-01-19
 */
public interface CourseApplyInfoMapper extends BaseMapper<CourseApplyInfo> {

    /**
     * Description：分页获取主播申请课程列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 11:30 2018/1/19 0019
     **/
    List<CourseApplyInfoVO> selectCourseApplyPage(@Param("page") Page<CourseApplyInfoVO> page, @Param("userId") String userId, @Param("courseForm") Integer courseForm, @Param("multimediaType") Integer multimediaType, @Param("title") String title);

    /**
     * Description：分页获取主播申请专辑列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 11:41 2018/1/19 0019
     **/
    List<CourseApplyInfoVO> selectCollectionApplyPage(@Param("page") Page<CourseApplyInfoVO> page, @Param("userId") String userId, @Param("multimediaType") Integer multimediaType, @Param("title") String title);

    List<CourseApplyInfoVO> selectLiveApplyPage(Page<CourseApplyInfoVO> page, String userId, String title);

    /**
     * Description：上/下架课程
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 10:29 2018/2/1 0001
     **/
    int updateSaleState(@Param("userId") String userId, @Param("courseApplyId") String courseApplyId, @Param("state") Integer state);
}