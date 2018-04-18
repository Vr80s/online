package com.xczhihui.wechat.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xczhihui.wechat.course.model.Criticize;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
public interface ICriticizeService extends IService<Criticize> {

    /**
     * Description：获取课程评价列表
     *              courseId：课程id
     *              userId：当前访问用户id
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/18 0018 上午 10:24
     **/
    Map<String,Object> getCourseCriticizes(Page<Criticize> page, Integer courseId,String userId) throws UnsupportedEncodingException;

    /**
     * Description：获取主播评价列表
     *              anchorUserId：主播id
     *              userId：当前访问用户id
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/18 0018 上午 10:24
     **/
    Map<String,Object> getAnchorCriticizes(Page<Criticize> page, String anchorUserId,String userId) throws UnsupportedEncodingException;

    /**
     * Description：新增评价
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/18 0018 下午 3:17
     **/
    void saveCriticize(String createPerson,String userId,Integer courseId,String content,Float overallLevel,Float deductiveLevel,Float contentLevel,String criticizeLable) throws UnsupportedEncodingException;

    /**
     * Description：新增回复
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/18 0018 下午 3:18
     **/
    void saveReply(String userId, String content, String criticizeId) throws UnsupportedEncodingException;

    Map<String, Object> updatePraise(Boolean isPraise, String criticizeId, String userId);
}
