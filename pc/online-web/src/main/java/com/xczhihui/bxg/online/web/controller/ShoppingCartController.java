package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.web.service.ShoppingCartService;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * 购物车模块控制层代码
 * @Author Fudong.Sun【】
 * @Date 2017/2/20 16:07
 */
@Controller
@RequestMapping(value = "/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService service;

    /**
     * 查询我的购物车中课程数量
     * @param req
     * @return
     */
    @RequestMapping(value = "/findCourseNum" )
    @ResponseBody
    public ResponseObject findCourseNum(HttpServletRequest req) {
        BxgUser user = UserLoginUtil.getLoginUser(req);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("请登录！");
        }
        return ResponseObject.newSuccessResponseObject(service.findCourseNum(user.getId()));
    }

    /**
     * 将课程加入购物车
     * @param req
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject join(HttpServletRequest req, Integer courseId) {
        BxgUser user = UserLoginUtil.getLoginUser(req);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("请登录！");
        }
        return service.addCart(user,courseId);
    }

    /**
     * 将课程批量加入购物车
     * @param req
     * @param courseIds
     * @param rule_id
     * @return
     */
    @RequestMapping(value = "/batchJoin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject batchJoin(HttpServletRequest req, String courseIds,String rule_id) {
        BxgUser user = UserLoginUtil.getLoginUser(req);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("请登录！");
        }
        String[] ids = courseIds==null? new String[]{} : courseIds.split(",");
        return service.addCourseToCart(user.getId(),ids,rule_id);
    }

    /**
     * 我的购物车列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/lists" )
    @ResponseBody
    public ResponseObject lists(HttpServletRequest req) {
        BxgUser user = UserLoginUtil.getLoginUser(req);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("请登录！");
        }
        return ResponseObject.newSuccessResponseObject(service.lists(user.getId()));
    }
    /**
     * 删除我的购物车中的课程
     * @param req
     * @param idStrs
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject delete(HttpServletRequest req,String idStrs) {
        BxgUser user = UserLoginUtil.getLoginUser(req);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("请登录！");
        }
        if(StringUtils.isBlank(idStrs)){
            return ResponseObject.newErrorResponseObject("参数错误!");
        }else {
            Set<String> ids = new HashSet<>();
            String[] idArr = idStrs.split(",");
            for (String id : idArr) {
                ids.add(id);
            }
            service.delete(user.getId(), ids);
        }
        return ResponseObject.newSuccessResponseObject("删除成功！");
    }

}
