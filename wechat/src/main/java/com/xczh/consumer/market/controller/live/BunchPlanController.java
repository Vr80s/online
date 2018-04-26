package com.xczh.consumer.market.controller.live;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.*;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.MenuVo;

/**
 * 点播控制器 ClassName: BunchPlanController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@Controller
@RequestMapping("/bxg/bunch")
public class BunchPlanController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BunchPlanController.class);



    @Value("${gift.im.room.postfix}")
    private String postfix;
    @Value("${gift.im.boshService}")
    private String boshService;//im服务地址
    @Value("${gift.im.host}")
    private String host;

    // 新增的课程列表页
    @RequestMapping("categorylist")
    @ResponseBody
    public ResponseObject categoryXCList(HttpServletRequest req, HttpServletResponse res, Map<String, String> params)
            throws Exception {

    	LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    // 新增点播课程list
    @RequestMapping("list")
    @ResponseBody
    public ResponseObject courseXCList(HttpServletRequest req,
                                       HttpServletResponse res, Map<String, String> params)
            throws Exception {
        
    	LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /***
     * 课程详细信息
     * @param req
     * @param res
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping("detail")
    @ResponseBody
    @Transactional
    public ResponseObject courseDetail(HttpServletRequest req,
                                       HttpServletResponse res)
            throws Exception {
    	
    	
    	LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
    }


    /**
     * 线下培训班
     */
    @RequestMapping("offLineClass")
    @ResponseBody
    public ResponseObject offLineClass(HttpServletRequest req,
                                       HttpServletResponse res, Map<String, String> params)
            throws Exception {

    	LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * 线下培训班列表
     */
    @RequestMapping("offLineClassList")
    @ResponseBody
    public ResponseObject offLineClassList(HttpServletRequest req,
                                           HttpServletResponse res, Map<String, String> params)
            throws Exception {
  
    	LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * 线下培训班详情
     */
    @RequestMapping("offLineClassItem")
    @ResponseBody
    public ResponseObject offLineClassItem(HttpServletRequest req,
                                           HttpServletResponse res, Integer id)
            throws Exception {

    	LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
    }


   
}
