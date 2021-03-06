package com.xczhihui.course.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xczhihui.course.model.Criticize;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
public interface ICriticizeService extends IService<Criticize> {

    /**
     * Description：获取课程评价列表
     * courseId：课程id
     * userId：当前访问用户id
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/18 0018 上午 10:24
     **/
    Map<String, Object> getCourseCriticizes(Page<Criticize> page, Integer courseId, String userId) throws UnsupportedEncodingException;

    /**
     * Description：获取主播评价列表
     * anchorUserId：主播id
     * userId：当前访问用户id
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/18 0018 上午 10:24
     **/
    Map<String, Object> getAnchorCriticizes(Page<Criticize> page, String anchorUserId, String userId) throws UnsupportedEncodingException;

    /**
     * Description：新增评价
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/18 0018 下午 3:17
     **/
    void saveCriticize(String createPerson, String userId, Integer courseId, String content, Float overallLevel, Float deductiveLevel, Float contentLevel, String criticizeLable) throws UnsupportedEncodingException;

    /**
     * Description：新增回复
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/18 0018 下午 3:18
     **/
    void saveReply(String userId, String content, String criticizeId) throws UnsupportedEncodingException;

    Map<String, Object> updatePraise(Boolean isPraise, String criticizeId, String userId);


    /**
     * 判断有没有学习过这个课程：大于0 学习过，小于0 没有学习过
     *
     * @param userId
     * @param courseId
     * @return
     */
    Integer hasCourse(String userId, Integer courseId);

    /**
     * 查看课程评论中要显示的各种统计数据：list长度为 7
     * size:0  节目内容  1 主播演绎  2.很赞 3 干货很多 4超值推荐 5喜欢 6买对了
     *
     * @param collection
     * @param courseId
     * @return
     */
    List<Integer> selectPcCourseCommentMeanCount(Boolean collection, Integer courseId);

    /**
     * 查看用户评论中要显示的各种统计数据：list长度为 7
     * size:0  节目内容  1 主播演绎  2.很赞 3 干货很多 4超值推荐 5喜欢 6买对了
     *
     * @param userId
     * @return
     */
    List<Double> selectPcUserCommentMeanCount(String userId);

    /**
     * 查看课程的list标签 共有多少
     *
     * @param courseId
     * @return
     */
    List<Integer> selectMobileCourseCommentMeanCount(Integer courseId);

    /**
     * 查看用户的list标签共有多少
     *
     * @param userId
     * @return
     */
    List<Integer> selectMobileUserCommentMeanCount(String userId);
}
