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

    /**
     * Description：保存课程申请
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 10:27 2018/2/1 0001
     **/
    void saveCourseApply(CourseApplyInfo courseApplyInfo);

    /**
     * Description：保存合辑课程申请
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 10:27 2018/2/1 0001
     **/
    void saveCollectionApply(CourseApplyInfo courseApplyInfo);

    /**
     * Description：获取所有资源
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 10:26 2018/2/1 0001
     **/
    List<CourseApplyResourceVO> selectAllCourseResources(String id, Integer multimediaType);

    Page<CourseApplyResourceVO> selectCourseResourcePage(Page<CourseApplyResourceVO> page, String id);

    /**
     * Description：保存资源
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 10:26 2018/2/1 0001
     **/
    void saveCourseApplyResource(CourseApplyResource courseApplyResource);

    /**
     * Description：获取资源播放代码
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 10:26 2018/2/1 0001
     **/
    String selectCourseResourcePlayerById(String id, Integer resourceId);

    /**
     * Description：上/下架
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 10:25 2018/2/1 0001
     **/
    void updateSaleState(String id, String courseApplyId, Integer state);

    /**
     * Description：定时更新课程时间
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 2:19 2018/2/2 0002
     **/
    void updateCourseApplyResource();

    void deleteCourseApplyResource(String id, String resourceId);

    List<CourseApplyInfoVO> selectAllCourses(String id, Integer multimediaType);

    CourseApplyInfo selectCourseApplyById(String id, Integer caiId);

    void updateCourseApply(CourseApplyInfo courseApplyInfo);

    void deleteCourseApplyById(String userId, Integer caiId);
}
