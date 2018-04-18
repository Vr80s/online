package com.xczh.consumer.market.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.service.MenuService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.MenuVo;

/**
 * @author liutao
 * @create 2017-09-18 0:13
 **/
@Controller
@RequestMapping("/bxg/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("list")
    @ResponseBody
    public ResponseObject list(HttpServletRequest req, HttpServletResponse res) {
        try {
        	String type = req.getParameter("type");
        	List<MenuVo> list = new ArrayList<MenuVo>();
        	if(type == null || !"1".equals(type)){
        		MenuVo mv = new MenuVo();
            	mv.setId(0);
            	mv.setName("全部");
            	list.add(mv);
        	}
        	list.addAll(menuService.list());
			return ResponseObject.newSuccessResponseObject(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return ResponseObject.newErrorResponseObject("错误");
		}
    }
    @RequestMapping("offlineCity")
    @ResponseBody
    public ResponseObject offlineCity(HttpServletRequest req, HttpServletResponse res) {
        try {
			return ResponseObject.newSuccessResponseObject(menuService.offlineCity());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return ResponseObject.newErrorResponseObject("错误");
		}
    }
}
