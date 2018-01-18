package com.xczh.consumer.market.controller.live;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineWatchHistoryService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.wxpay.entity.OeWatchHistory;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.wechat.course.model.WatchHistory;
import com.xczhihui.wechat.course.service.IWatchHistoryService;
import com.xczhihui.wechat.course.vo.WatchHistoryVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bxg/history")
public class WatchHistoryController {

	@Autowired
	public OnlineWatchHistoryService onlineWatchHistoryService;
	
	@Autowired
	public IWatchHistoryService watchHistoryServiceImpl;

	@Autowired
	private AppBrowserService appBrowserService;

	@RequestMapping("list")
	@ResponseBody
	public ResponseObject getWatchHistoryList(HttpServletRequest req,
											  HttpServletResponse res, Map<String, String> params) {
		if(null == req.getParameter("pageNumber") || null == req.getParameter("pageSize")||null == req.getParameter("type")){
			return ResponseObject.newErrorResponseObject("缺少参数");
		}
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req, params);
		String appUniqueId = req.getParameter("appUniqueId");
		String ouStrId = "";
		if(ou==null){
			ouStrId = appUniqueId;
		}else{
			ouStrId = ou.getId();
		}
		int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize"));
		String type = req.getParameter("type");
		System.err.println("pageNumber:"+pageNumber+"=============="+"pageSize:"+pageSize);
		try {
		List<OeWatchHistory> list = onlineWatchHistoryService.getOeWatchHistotyList(pageNumber,
					pageSize,ouStrId, type,ou);
		System.err.println("type:"+type+",list.size():"+list.size());
		  return ResponseObject.newSuccessResponseObject(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  return ResponseObject.newErrorResponseObject("查询有误");
		}
	}
    /**
     * Description：添加观看记录
     * @param req
     * @param res
     * @param params
     * @return
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	@RequestMapping("add")
	@ResponseBody
	public ResponseObject add1(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params) {
		try {
			String courseId = req.getParameter("course_id");
			String type = req.getParameter("type");
			String appUniqueId = req.getParameter("appUniqueId");
			
			if(null == courseId && null == type){
				return ResponseObject.newErrorResponseObject("缺少参数");
			}
			OnlineUser ou = appBrowserService.getOnlineUserByReq(req, params);
			String ouStrId = "";
			if(ou==null){
				ouStrId = appUniqueId;
			   //return ResponseObject.newErrorResponseObject("获取用户信息异常");
			}else{
				ouStrId = ou.getId();
			}
			Integer isWatchHistory = onlineWatchHistoryService.findOnlineWatchHistory(ouStrId, courseId);
			if(isWatchHistory>0){
				onlineWatchHistoryService.updateOnlineWatchHistory(ouStrId,courseId);
				return ResponseObject.newSuccessResponseObject("保存成功");
			}else{
				onlineWatchHistoryService.saveOnlineWatchHistory1(ouStrId,courseId,type);
				return ResponseObject.newSuccessResponseObject("保存成功");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("保存失败");
		}
		//return null;
	}
	/**
	 * Description：清空观看记录
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@RequestMapping("empty")
	@ResponseBody
	public ResponseObject empty(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params) {
		try {
			if(null == req.getParameter("type")){
				return ResponseObject.newErrorResponseObject("缺少参数");
			}
			OnlineUser ou = appBrowserService.getOnlineUserByReq(req, params);
			String appUniqueId = req.getParameter("appUniqueId");
			String ouStrId = "";
			if(ou==null){
				ouStrId = appUniqueId;
			}else{
				ouStrId = ou.getId();
			}
			String type = req.getParameter("type");
			onlineWatchHistoryService.deleteOnlineWatchHistoryByUserIdAndType(ouStrId,type);
			return ResponseObject.newSuccessResponseObject("清空成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("清空有误");
		}
	}
	/**
     * Description：增加观看记录
     * @param req
     * @param res
     * @param params
     * @return
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	@RequestMapping("add2")
	@ResponseBody
	public ResponseObject add2(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params) {
		try {
			String courseId = req.getParameter("course_id");
			if(null == courseId){
				return ResponseObject.newErrorResponseObject("缺少参数");
			}
			OnlineUser ou = appBrowserService.getOnlineUserByReq(req, params);
			if(ou==null){
			   return ResponseObject.newErrorResponseObject("获取用户信息异常");
			}
			//params.put("user_id", ou.getId());
			WatchHistory target = new WatchHistory();
			target.setCourseId(Integer.parseInt(courseId));
			target.setUserId(ou.getId());
			watchHistoryServiceImpl.add(target);
			return ResponseObject.newSuccessResponseObject("保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("保存失败");
		}
	}

	@RequestMapping("list2")
	@ResponseBody
	public ResponseObject list2(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params) {
		try {
			Page<WatchHistoryVO> page = new Page<>();
		    page.setCurrent(1);
		    page.setSize(7);
		    OnlineUser ou = appBrowserService.getOnlineUserByReq(req, params);
			if(ou==null){
			   return ResponseObject.newErrorResponseObject("获取用户信息异常");
			}
			return ResponseObject.newSuccessResponseObject(watchHistoryServiceImpl.selectWatchHistory(page, ou.getId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("保存失败");
		}
	}
	
	@RequestMapping("empty2")
	@ResponseBody
	public ResponseObject empty2(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params) {
		try {
			OnlineUser ou = appBrowserService.getOnlineUserByReq(req, params);
			if(ou==null){
			   return ResponseObject.newErrorResponseObject("获取用户信息异常");
			}
			
			watchHistoryServiceImpl.deleteBatch(ou.getUserId());
			return ResponseObject.newSuccessResponseObject("清空成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("保存失败");
		}
	}
	
}
