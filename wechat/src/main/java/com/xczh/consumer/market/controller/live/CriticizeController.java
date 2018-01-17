package com.xczh.consumer.market.controller.live;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.service.CriticizeService;
import com.xczhihui.bxg.online.api.vo.CriticizeVo;
import com.xczhihui.bxg.online.common.domain.Criticize;

@Controller
@RequestMapping(value = "/bxg/criticize")
public class CriticizeController {


	@Autowired
	private AppBrowserService appBrowserService; 
	
//	@Autowired
//	private criticizeService criticizeService;
	@Autowired
	private CriticizeService criticizeService; 
	/**
	 * 添加评论
	 */
	@RequestMapping(value="saveCriticize")
	@ResponseBody
	public ResponseObject saveCriticize(HttpServletRequest req,
			HttpServletResponse res,CriticizeVo criticize)
			throws Exception {
		
		OnlineUser  ou = appBrowserService.getOnlineUserByReq(req);
		//criticize.setCreateTime(new Date());
		criticize.setCreatePerson(ou.getId());
		if(criticize.getContent().length()>5000){
			return ResponseObject.newErrorResponseObject("抱歉评论内容过长");
		}else{
			criticizeService.saveNewCriticize(criticize);
			return ResponseObject.newSuccessResponseObject("评论添加成功");
		}
	}
	/**
	 * 得到此视频下的所有评论
	 */
	@RequestMapping("getVideoCriticize")
	@ResponseBody
	public ResponseObject getVideoCriticize(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		
		int pageNumber =Integer.parseInt(req.getParameter("pageNumber"));
		
		String pageSizeStr = req.getParameter("pageSize");
		int pageSize = 20;
		if(StringUtils.isNotBlank(pageSizeStr)){
			pageSize = Integer.parseInt(req.getParameter("pageSize"));
		}
		String userId = req.getParameter("userId");
		String courseId = req.getParameter("courseId");
		Page<Criticize> pageList  = criticizeService.getUserCriticize(userId,courseId,pageNumber, pageSize);
		return ResponseObject.newSuccessResponseObject(pageList);
	}
    /**
     * 点赞、取消点赞
     * @param request
     * @param isPraise
     * @param criticizeId
     * @return
     */
    @RequestMapping("updatePraise")
	@ResponseBody
    public ResponseObject updatePraise(HttpServletRequest request,Boolean isPraise, String criticizeId) {
        //获取当前登陆用户信息
        OnlineUser user = (OnlineUser) appBrowserService.getOnlineUserByReq(request);
        if(user!=null) {
            Map<String, Object> returnMap = criticizeService.updatePraise(true, criticizeId, user.getLoginName());
            return ResponseObject.newSuccessResponseObject(returnMap);
        }else{
            return ResponseObject.newErrorResponseObject("用户未登录！");
        }
    }
    /**
     * Description：增加回复
     * @param request
     * @param content
     * @param criticizeId
     * @return
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("saveReply")
	@ResponseBody
    public ResponseObject saveReply(HttpServletRequest request,String content, String criticizeId) {
        //获取当前登陆用户信息
        OnlineUser user = (OnlineUser) appBrowserService.getOnlineUserByReq(request);
        if(user!=null) {
            criticizeService.saveReply(content, criticizeId, user.getId());
            return ResponseObject.newSuccessResponseObject("回复成功！");
        }else{
            return ResponseObject.newErrorResponseObject("用户未登录！");
        }
    }
}
