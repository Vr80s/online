package com.xczhihui.bxg.online.web.controller;/**
 * Created by admin on 2016/8/30.
 */

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.Apply;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.ApplyService;
import com.xczhihui.bxg.online.web.service.UserService;

import org.jivesoftware.smackx.pubsub.GetItemsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 学员信息控制层类
 *
 * @author 康荣彩
 * @create 2016-08-30 21:04
 */
@RestController
@RequestMapping(value = "online/apply")
public class ApplyController {



    @Autowired
    private UserService userService;
    @Autowired
    private ApplyService applyService;
    /**
     * 保存学员信息，并且对中间表生成数据
     * @return ResponseObject
     */
    @RequestMapping(value = "/saveApply",method= RequestMethod.GET)
    public ResponseObject saveApply(HttpServletRequest request,Integer courseId,  Integer gradeId){

        //根据当前登录用户去查找用户最新报名状态
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
        OnlineUser  user=userService.isAlive(loginUser.getLoginName());
         return ResponseObject.newSuccessResponseObject(applyService.saveApply(user,courseId,gradeId));
    }


    /**
     * 验证登录用户是否是老学员
     * @param realName 用户真实姓名
     * @param idCardNumber 身份证号
     * @return
     */
    @RequestMapping(value = "/isOldUser",method= RequestMethod.GET)
    public ResponseObject isOldUser(String realName, String idCardNumber,HttpServletRequest request,String lot_no) throws Exception {
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if (loginUser == null){
            throw new RuntimeException("请登录！");
        }
        return ResponseObject.newSuccessResponseObject(applyService.updateUserOrcheckUser(realName, idCardNumber,lot_no,loginUser));
    }

    /** 
     * Description：获取用户个人信息
     * @param request
     * @param lot_no
     * @return
     * @throws Exception
     * @return ResponseObject
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    @RequestMapping(value = "/getUserApplyInfo",method= RequestMethod.GET)
    public ResponseObject getUserApplyInfo(HttpServletRequest request,String lot_no) throws Exception {
    	OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
    	if (loginUser == null){
    		throw new RuntimeException("请登录！");
    	}
    	return ResponseObject.newSuccessResponseObject(applyService.getUserApply(loginUser.getId()));
    }
    
    /**，并且对中间表生成数据
     * @return ResponseObject
     */
    @RequestMapping(value = "/saveOrUpdateApply")
    public ResponseObject saveOrUpdateApply(HttpServletRequest request,Apply apply){
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
        applyService.saveApply(apply, loginUser);
		return ResponseObject.newSuccessResponseObject("保存成功");
    }
}
