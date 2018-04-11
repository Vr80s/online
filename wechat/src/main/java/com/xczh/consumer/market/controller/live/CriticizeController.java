package com.xczh.consumer.market.controller.live;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.SLEmojiFilter;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.service.CriticizeService;
import com.xczhihui.bxg.online.api.vo.CriticizeVo;

@Controller
@RequestMapping(value = "/xczh/criticize")
public class CriticizeController {


	@Autowired
	private AppBrowserService appBrowserService; 
	
	@Autowired
	private CriticizeService criticizeService; 
	
	@Autowired
	private OnlineWebService  onlineWebService;
	/**
	 * 添加评论
	 */
	@RequestMapping(value="saveCriticize")
	@ResponseBody
	public ResponseObject saveCriticize(HttpServletRequest req,
			HttpServletResponse res,CriticizeVo criticize)
			throws Exception {
		
		OnlineUser  ou = appBrowserService.getOnlineUserByReq(req);
		if(ou == null){
			return ResponseObject.newSuccessResponseObject("登录失效");
		}
		//过滤字符串中的Emoji表情
//		String content = SLEmojiFilter.filterEmoji(criticize.getContent());
//		if("".equals(content)){
//			return ResponseObject.newErrorResponseObject("暂不支持添加表情");
//		}else{
//			criticize.setContent(content);
//		}
		if(StringUtils.isNotBlank(criticize.getContent())){
			String content = SLEmojiFilter.emojiConvert1(criticize.getContent());
			criticize.setContent(content);
		}
		
		criticize.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		criticize.setCreatePerson(ou.getId());  //创建人id
		if(criticize.getContent().length()>200){
			return ResponseObject.newErrorResponseObject("字符太长");
		}else{
			/**
			 * 这里判断下此用户有没有购买过此视频
			 */
			Boolean isBuy = onlineWebService.getLiveUserCourseAndIsFree(criticize);
			criticize.setIsBuy(isBuy);
			
			criticizeService.saveNewCriticize(criticize);
			return ResponseObject.newSuccessResponseObject("评论成功");
		}
	}
	/**
	 * 得到此视频下的所有评论
	 */
	@RequestMapping("getCriticizeList")
	@ResponseBody
	public ResponseObject getVideoCriticize(HttpServletRequest req,
			HttpServletResponse res,@RequestParam(required=false)String userId,
			@RequestParam(required=false)Integer courseId,
			@RequestParam(required=false)Integer pageSize,
			@RequestParam(required=false)Integer pageNumber
			)throws Exception {
	
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		
		Map<String,Object> map = criticizeService.getUserOrCourseCriticize(userId,courseId,pageNumber, 
				pageSize,user!= null ? user.getUserId() :null);
		
		return ResponseObject.newSuccessResponseObject(map);
		
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
    public ResponseObject updatePraise(HttpServletRequest request,
    		@RequestParam("criticizeId")String criticizeId,
    		@RequestParam("praise")Boolean praise) {
        //获取当前登录用户信息
        OnlineUser user = appBrowserService.getOnlineUserByReq(request);
        if(user!=null) {
            Map<String, Object> returnMap = criticizeService.updatePraise(praise, criticizeId, user.getLoginName());
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
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("saveReply")
	@ResponseBody
    public ResponseObject saveReply(HttpServletRequest request,
    		@RequestParam("content")String content, 
    		@RequestParam("criticizeId")String criticizeId,
    		@RequestParam(required=false)Integer collectionId) throws UnsupportedEncodingException {
        //获取当前登录用户信息
        OnlineUser user = appBrowserService.getOnlineUserByReq(request);
		if(StringUtils.isNotBlank(content)){
		    content = SLEmojiFilter.emojiConvert1(content);
		}
        if(user!=null) {
        	/**
        	 * 这个是讲师id
        	 */
        	if(content.length()>200){
    			return ResponseObject.newErrorResponseObject("字符太长");
    		}else{
    			criticizeService.saveReply(content,user.getId(),criticizeId,collectionId);
    		}
            return ResponseObject.newSuccessResponseObject("回复成功！");
        }else{
            return ResponseObject.newErrorResponseObject("用户未登录！");
        }
    }
}
