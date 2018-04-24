package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.utils.VhallUtil;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.model.CourseApplyResource;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.medical.anchor.vo.CourseApplyInfoVO;
import com.xczhihui.medical.anchor.vo.CourseApplyResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * 主播工作台课程控制层
 * @author yuruixin
 */
@RestController
@RequestMapping(value = "/anchor/course")
public class CourseApplyController extends AbstractController {

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
        OnlineUser user = getOnlineUser(request);
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
        OnlineUser user = getOnlineUser(request);
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectLiveApplyPage(page,user.getId(),title));
    }

    /**
     * Description：获取所有资源列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:51 2018/1/19 0019
     **/
    @RequestMapping(value = "/getAllCourseResources",method= RequestMethod.GET)
    public ResponseObject getAllCourseResources(HttpServletRequest request,Integer multimediaType){
        OnlineUser user = getOnlineUser(request);
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectAllCourseResources(user.getId(),multimediaType));
    }

    @RequestMapping(value = "/getAllCourses",method= RequestMethod.GET)
    public ResponseObject getAllCourses(HttpServletRequest request,Integer multimediaType){
        OnlineUser user = getOnlineUser(request);
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectAllCourses(user.getId(),multimediaType));
    }

    @RequestMapping(value = "/getCourseApplyById",method= RequestMethod.GET)
    public ResponseObject getCourseApplyById(HttpServletRequest request,Integer caiId){
        OnlineUser user = getOnlineUser(request);
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectCourseApplyById(user.getId(),caiId));
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
        OnlineUser user = getOnlineUser(request);
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectCourseResourcePage(page,user.getId()));
    }

    /**
     * Description：获取资源播放代码
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 10:18 2018/2/1 0001
     **/
    @RequestMapping(value = "/getCourseResourcePlayer",method= RequestMethod.GET)
    public ResponseObject getCourseResource(HttpServletRequest request,Integer resourceId){
        OnlineUser user = getOnlineUser(request);
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectCourseResourcePlayerById(user.getId(),resourceId));
    }

    /**
     * Description：新增课程申请
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:58 2018/1/19 0019
     **/
    @RequestMapping(value = "/saveCourseApply",method= RequestMethod.POST)
    public ResponseObject saveCourseApply(HttpServletRequest request, @RequestBody CourseApplyInfo courseApplyInfo){
        OnlineUser user = getOnlineUser(request);
        courseApplyInfo.setUserId(user.getId());
        courseApplyService.saveCourseApply(courseApplyInfo);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    /**
     * Description：更新课程申请信息
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/2/4 0004 下午 7:35
     **/
    @RequestMapping(value = "/updateCourseApply",method= RequestMethod.POST)
    public ResponseObject updateCourseApply(HttpServletRequest request, @RequestBody CourseApplyInfo courseApplyInfo){
        OnlineUser user = getOnlineUser(request);
        courseApplyInfo.setUserId(user.getId());
        courseApplyService.updateCourseApply(courseApplyInfo);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    @RequestMapping(value = "/updateCollectionApply",method= RequestMethod.POST)
    public ResponseObject updateCollectionApply(HttpServletRequest request, @RequestBody CourseApplyInfo courseApplyInfo){
        OnlineUser user = getOnlineUser(request);
        courseApplyInfo.setUserId(user.getId());
        courseApplyService.updateCollectionApply(courseApplyInfo);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    /**
     * Description：新增课程专辑申请
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:58 2018/1/19 0019
     **/
    @RequestMapping(value = "/saveCollectionApply",method= RequestMethod.POST)
    public ResponseObject saveCollectionApply(HttpServletRequest request, @RequestBody CourseApplyInfo courseApplyInfo){
        OnlineUser user = getOnlineUser(request);
        courseApplyInfo.setUserId(user.getId());
        courseApplyService.saveCollectionApply(courseApplyInfo);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    /**
     * Description：新增资源
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:58 2018/1/19 0019
     **/
    @RequestMapping(value = "/saveCourseResource",method= RequestMethod.POST)
    public ResponseObject saveCourseResource(HttpServletRequest request, @RequestBody CourseApplyResource courseApplyResource){
        OnlineUser user = getOnlineUser(request);
        courseApplyResource.setUserId(user.getId());
        courseApplyService.saveCourseApplyResource(courseApplyResource);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    /**
     * Description：删除资源
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/2/3 0003 下午 9:12
     **/
    @RequestMapping(value = "/deleteCourseResource",method= RequestMethod.POST)
    public ResponseObject deleteCourseResource(HttpServletRequest request, String resourceId){
        OnlineUser user = getOnlineUser(request);
        courseApplyService.deleteCourseApplyResource(user.getId(),resourceId);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * Description：删除课程申请
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/2/5 0005 下午 2:15
     **/
    @RequestMapping(value = "/deleteCourseApplyById")
    public ResponseObject deleteCourseApplyById(HttpServletRequest request, Integer caiId){
        OnlineUser user = getOnlineUser(request);
        courseApplyService.deleteCourseApplyById(user.getId(),caiId);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * Description：获取微吼直播路径
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 9:18 2018/1/23 0023
     **/
    @RequestMapping(value = "getWebinarUrl")
    public ResponseObject getWebinarUrl(String webinarId) {
        ResponseObject responseObj = new ResponseObject();
        String url = VhallUtil.getWebinarUrl(webinarId);
        responseObj.setSuccess(true);
        responseObj.setResultObject(url);
        return responseObj;
    }

    /**
     * Description：
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 9:45 2018/2/1 0001
     **/
    @RequestMapping(value = "getVhallInfo")
    public ResponseObject getVhallInfo(HttpServletRequest request) {
        ResponseObject responseObj = new ResponseObject();
        Map vhallInfo = new HashMap();
        OnlineUser user = getOnlineUser(request);
        vhallInfo.put("vhallAccount","v"+user.getVhallId());
        vhallInfo.put("password",user.getVhallPass());
        responseObj.setSuccess(true);
        responseObj.setResultObject(vhallInfo);
        return responseObj;
    }

    /**
     * Description：上/下架课程
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 9:18 2018/1/23 0023
     **/
    @RequestMapping(value = "changeSaleState", method = RequestMethod.POST)
    public ResponseObject changeSaleState(HttpServletRequest request,String courseApplyId,Integer state) {
        ResponseObject responseObj = new ResponseObject();
        OnlineUser user = getOnlineUser(request);
        courseApplyService.updateSaleState(user.getId(),courseApplyId,state);
        responseObj.setSuccess(true);
        if(state==1){
            responseObj.setResultObject("上架成功");
        }else{
            responseObj.setResultObject("下架成功");
        }
        return responseObj;
    }

    /**
     * Description：获取嘉宾直播路径
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/11 0011 下午 4:10
     **/
    @RequestMapping(value = "getWebinarGuestUrl")
    public ResponseObject getWebinarGuestUrl(String webinarId) {
        ResponseObject responseObj = new ResponseObject();
        String url = VhallUtil.getWebinarGuestUrl(webinarId);
        responseObj.setSuccess(true);
        responseObj.setResultObject(url);
        return responseObj;
    }
}
