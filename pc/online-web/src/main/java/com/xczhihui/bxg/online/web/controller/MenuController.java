package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

/**
 * 菜单控制层实现类
 * @author Rongcai Kang
 */
@RestController
@RequestMapping(value = "/menu")
public class MenuController {

     @Autowired
     private MenuService  service;


     /**
      * 查询全部菜单列表
      * @param pageNumber 当前是第几页，默认1
      * @param pageSize 每页显示多少行，默认20
      * @return
      */
     @RequestMapping(value = "/getAllMenu")
     public ResponseObject getAllMenu(String type) throws InvocationTargetException, IllegalAccessException {
          return ResponseObject.newSuccessResponseObject(service.getAllMenu(null,null,type));
     }




}
