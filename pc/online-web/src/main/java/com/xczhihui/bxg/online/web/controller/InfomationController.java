package com.xczhihui.bxg.online.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.service.InfomationService;

/**
 * 动态资讯相关
 * 
 * @author Haicheng Jiang
 */
@RestController
@RequestMapping(value = "/home/infomation")
public class InfomationController {
	
	@Autowired
	private InfomationService service;
	/**
	 * 查询资讯列表
	 * @param sum 数量
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ResponseObject list(Integer sum) {
		return ResponseObject.newSuccessResponseObject(service.list(sum));
	}
	/**
	 * 点击数量加1
	 * @param id 资讯id
	 * @return
	 */
	@RequestMapping(value = "/updateClickCount",method=RequestMethod.POST)
	public ResponseObject updateClickCount(Integer id) {
		service.updateClickCount(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}
}
