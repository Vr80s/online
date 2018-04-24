package com.xczhihui.bxg.common.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.common.support.service.DictionaryService;
import com.xczhihui.bxg.common.util.bean.DictionaryVo;
/**
 * 数据字典，取下拉列表、把value翻译成name
 * @author Haicheng Jiang
 */
@Controller
@RestController
public class DictionaryController extends AbstractController{
	@Autowired
	private DictionaryService service;
	
	@RequestMapping(value = "/dictionary/list/{parentValue}",method=RequestMethod.GET)
	@ResponseBody
	public List<DictionaryVo> list(@PathVariable("parentValue") String parentValue){
		return service.list(parentValue);
	}
	
	@RequestMapping(value = "/dictionary/name/{parentValue}/{value}",method=RequestMethod.GET)
	@ResponseBody
	public String name(@PathVariable("parentValue") String parentValue,@PathVariable("value") String value){
		return service.name(parentValue, value);
	}
	
}
