package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.utils.OnlineConfig;
import com.xczhihui.bxg.online.common.utils.cc.config.Config;
import com.xczhihui.bxg.online.common.utils.cc.util.APIServiceFunction;
import com.xczhihui.bxg.online.web.base.utils.VhallUtil;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.model.CourseApplyResource;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.medical.anchor.vo.CourseApplyInfoVO;
import com.xczhihui.medical.anchor.vo.CourseApplyResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 主播工作台课程控制层
 * @author yuruixin
 */
@RestController
@RequestMapping(value = "/anchor/course")
public class CourseApplyController {

    @Autowired
    private ICourseApplyService courseApplyService;

    /**
     * Description：分页获取课程申请列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:42 2018/1/19 0019
     **/
    @RequestMapping(value = "/getCourseApplyList",method= RequestMethod.GET)
    public ResponseObject getCourseApplyList(HttpServletRequest request,Integer current, Integer size, Integer courseForm, Integer multimediaType, String title){
        Page<CourseApplyInfoVO> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if(user==null){
            return ResponseObject.newErrorResponseObject("未登录");
        }
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectCourseApplyPage(page,user.getId(),courseForm,multimediaType,title));
    }

    /**
     * Description：分页获取专辑列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:50 2018/1/19 0019
     **/
    @RequestMapping(value = "/getCollectionApplyList",method= RequestMethod.GET)
    public ResponseObject getCollectionApplyList(HttpServletRequest request,Integer current, Integer size, Integer multimediaType, String title){
        Page<CourseApplyInfoVO> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectCollectionApplyPage(page,user.getId(),multimediaType,title));
    }

    /**
     * Description：分页获取直播列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:51 2018/1/19 0019
     **/
    @RequestMapping(value = "/getLiveApplyList",method= RequestMethod.GET)
    public ResponseObject getLiveApplyList(HttpServletRequest request,Integer current, Integer size, String title){
        Page<CourseApplyInfoVO> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectLiveApplyPage(page,user.getId(),title));
    }

    /**
     * Description：获取所有资源列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:51 2018/1/19 0019
     **/
    @RequestMapping(value = "/getAllCourseResources",method= RequestMethod.GET)
    public ResponseObject getAllCourseResources(HttpServletRequest request){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectAllCourseResources(user.getId()));
    }

    /**
     * Description：分页获取资源列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:51 2018/1/19 0019
     **/
    @RequestMapping(value = "/getCourseResourceList",method= RequestMethod.GET)
    public ResponseObject getCourseResourceList(HttpServletRequest request,Integer current, Integer size){
        Page<CourseApplyResourceVO> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectCourseResourcePage(page,user.getId()));
    }

    /**
     * Description：新增课程申请
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:58 2018/1/19 0019
     **/
    @RequestMapping(value = "/saveCourseApply",method= RequestMethod.POST)
    public ResponseObject saveCourseApply(HttpServletRequest request, @RequestBody CourseApplyInfo courseApplyInfo){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        courseApplyInfo.setUserId(user.getId());
        courseApplyService.saveCourseApply(courseApplyInfo);
        return ResponseObject.newSuccessResponseObject("课程新增申请发起成功！");
    }

    /**
     * Description：新增课程专辑申请
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:58 2018/1/19 0019
     **/
    @RequestMapping(value = "/saveCollectionApply",method= RequestMethod.POST)
    public ResponseObject saveCollectionApply(HttpServletRequest request, @RequestBody CourseApplyInfo courseApplyInfo){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        courseApplyInfo.setUserId(user.getId());
        courseApplyService.saveCollectionApply(courseApplyInfo);
        return ResponseObject.newSuccessResponseObject("课程新增申请发起成功！");
    }

    /**
     * Description：新增资源
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:58 2018/1/19 0019
     **/
    @RequestMapping(value = "/saveCourseResource",method= RequestMethod.POST)
    public ResponseObject saveCourseResource(HttpServletRequest request, @RequestBody CourseApplyResource courseApplyResource){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        courseApplyResource.setUserId(user.getId());
        courseApplyService.saveCourseApplyResource(courseApplyResource);
        return ResponseObject.newSuccessResponseObject("课程新增申请发起成功！");
    }

    /**
     * Description：获取微吼直播路径
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 9:18 2018/1/23 0023
     **/
    @RequestMapping(value = "getWebinarUrl", method = RequestMethod.POST)
    public ResponseObject getWebinarUrl(String webinarId) {
        ResponseObject responseObj = new ResponseObject();
        String url = VhallUtil.getWebinarUrl(webinarId);
        responseObj.setSuccess(true);
        responseObj.setResultObject(url);
        return responseObj;
    }
}
