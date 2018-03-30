package com.xczh.consumer.market.controller.live;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.xczhihui.bxg.online.common.domain.Criticize;
import com.xczhihui.bxg.online.common.domain.Reply;

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
		Page<Criticize> pageList  = criticizeService.getUserOrCourseCriticize(userId,courseId,pageNumber, 
				pageSize,user!= null ? user.getUserId() :null);
		
		List<Criticize> list1  = new ArrayList<Criticize>();
		for (Criticize criticize : pageList.getItems()) {
			/*
			 * 评论的内容要转化，回复的内容也需要转化
			 */
			if(StringUtils.isNotBlank(criticize.getContent())){
				criticize.setContent(SLEmojiFilter.emojiRecovery2(criticize.getContent()));
			}
			if(criticize.getReply()!=null && criticize.getReply().size()>0){
				List<Reply> replyList = criticize.getReply();
				String replyContent = replyList.get(0).getReplyContent();
				if(StringUtils.isNotBlank(replyContent)){
					replyList.get(0).setReplyContent(SLEmojiFilter.emojiRecovery2(replyList.get(0).getReplyContent()));
				}
				criticize.setReply(replyList);
			}
			list1.add(criticize);
		}
		/**
		 * 这里判断用户发表的评论中是否包含发表心心了，什么的如果包含的话就不返回了
		 * 		并且判断这个用户有没有购买过这个课程
		 */
		Map<String,Object> map = new HashMap<String,Object>();
		if(user!=null && courseId!=null){
			Integer cv = criticizeService.findUserFirstStars(courseId,user.getId());
			map.put("commentCode", cv);
		}else{
			map.put("commentCode", 0);
		}
		map.put("items", list1);
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
