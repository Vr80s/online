package com.xczh.consumer.market.controller.school;

import com.xczh.consumer.market.service.HotSearchService;
import com.xczh.consumer.market.utils.ResponseObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 热门搜索控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>默认关键字与热门搜索
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@Controller
@RequestMapping("/bxg/bunch")
public class MobileHotSearchController {

	@Autowired
	private HotSearchService hotSearchService;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileHotSearchController.class);

	/**
	 * 听课
	 */
	@RequestMapping("hotSearch")
	@ResponseBody
	public ResponseObject hotSearchList(HttpServletRequest req,
									 HttpServletResponse res)
			throws Exception {

		Map<String, Object> mapAll = new HashMap<String, Object>();
		//听课banner
		mapAll.put("defaultSearch",hotSearchService.SearchList(1));

		mapAll.put("hotSearch",hotSearchService.SearchList(2));


		return ResponseObject.newSuccessResponseObject(mapAll);
	}

	
}
