package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.bean.WxcpBinner;
import com.xczh.consumer.market.service.OLBinnerServiceI;
import com.xczh.consumer.market.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/bxg/binner")
public class OLBinnerController {

	@Autowired
	private OLBinnerServiceI wxcpBinnerService;
	
	/***
	 * 轮播图列表
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@ResponseBody
	public ResponseObject binnerList(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		List<WxcpBinner> list = wxcpBinnerService.binnerList();
		return ResponseObject.newSuccessResponseObject(list);
	}
	
	/**
	 * 轮播图点击次数
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping("click")
	public void binnerClick(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		String binner_id = req.getParameter("binner_id");
		if(!"".equals(binner_id) && binner_id!=null){
			ExecutorService exectors = Executors.newSingleThreadExecutor();
			exectors.execute(new Runnable(){
				@Override
				public void run() {
					try {
						wxcpBinnerService.binnerClick(binner_id);
					} catch (Exception e) {
						exectors.shutdown();
						e.printStackTrace();
					}
				}
			});
			exectors.shutdown();
		}
	}
}
