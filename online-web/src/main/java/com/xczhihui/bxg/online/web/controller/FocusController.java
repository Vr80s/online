package com.xczhihui.bxg.online.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.course.service.IFocusService;
import com.xczhihui.course.vo.FocusVo;


/** 
 * ClassName: GiftController.java <br>
 * Description: 礼物打赏接口<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
@RestController
@RequestMapping(value = "/focus")
public class FocusController extends AbstractController{

	@Autowired
	private IFocusService focusService;
	
	@Autowired
	private UserService service;

	/**
	 * Description:获取主播信息接口
	 * @return
	 * @return ResponseObject
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@RequestMapping(value = "/getFocusAndFans")
	public ResponseObject getFocusAndFans(String userId) {
		
		return ResponseObject.newSuccessResponseObject(focusService.selectFocusAndFansCount(userId));
	}
	
	/**
	 * Description： 取消/增加   关注
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("updateFocus")
	@ResponseBody
	public ResponseObject updateFocus(HttpServletRequest request,
			@RequestParam("lecturerId") String lecturerId,
			@RequestParam("type") Integer type)
			throws Exception {
		/**
		 * 判断用户是否已经关注了这个主播
		 */
		BxgUser loginUser = getCurrentUser();
	    if(loginUser==null){
	    	return ResponseObject.newErrorResponseObject("登录失效");
	    }
	    OnlineUser o = service.findUserById(lecturerId);
		if(null == o){	
			return ResponseObject.newErrorResponseObject("讲师信息有误");
		}
		String lockId = lecturerId+loginUser.getId();
		focusService.updateFocus(lockId,lecturerId,loginUser.getId(),type);
		
        Map<String,Object> map = new HashMap<String,Object>();
        /*
         * 这里建议获取下粉丝数和关注数
         */
        List<Integer> listff = focusService.selectFocusAndFansCount(lecturerId);
        map.put("fansCount", listff.get(0));           //粉丝总数
        map.put("focusCount", listff.get(1));           //关注总数
		return ResponseObject.newSuccessResponseObject(map);
	}

	/**
	 * Description:关注的主播（我的关注）
	 * @return ResponseObject
	 * @author wangyishuai
	 **/
	@RequestMapping(value = "/myFocus")
	@ResponseBody
	public ResponseObject getHostInfoById(HttpServletRequest request) {
		try {
			OnlineUser u =  getCurrentUser();
			if(u==null) {
                return ResponseObject.newErrorResponseObject("用户未登录");
            }
			List<FocusVo> list = focusService.selectFocusList(u.getId());
			return ResponseObject.newSuccessResponseObject(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("获取数据失败");
		}
	}
	
	
}