package com.xczhihui.bxg.online.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.OrderService;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;

/**
 * 订单控制层类
 *
 * @author 康荣彩
 * @create 2016-08-30 21:04
 */
@Controller
@RequestMapping(value = "/web")
public class OrderController extends AbstractController {

    @Autowired
    private OrderService orderService;

    @Value("${web.url}")
    private String weburl;

    /**
     * 获取用户全部订单信息
     *
     * @param orderStatus 订单支付状态
     * @param timeQuantum 订单时间段
     * @param pageNumber  当前页
     * @param pageSize    每页条数
     * @return 所有订单信息
     */
    @RequestMapping(value = "/getMyAllOrder", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getMyAllOrder(Integer orderStatus, Integer timeQuantum, Integer pageNumber, Integer pageSize) {
        OnlineUser currentUser = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(orderService.getMyAllOrder(orderStatus, timeQuantum, pageNumber, pageSize, currentUser.getId()));
    }

    /**
     * 返回当前订单支付状态
     *
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "/getOrderStatus", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getOrderStatus(String orderNo) {
        return ResponseObject.newSuccessResponseObject(orderService.getOrderStatus(orderNo));
    }

    /**
     * 返回当前订单支付状态
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/getOrderStatusById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getOrderStatusById(String orderId) {
        return ResponseObject.newSuccessResponseObject(orderService.getOrderStatusById(orderId));
    }

    /**
     * 检查订单是否已支付
     *
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "/checkOrder")
    @ResponseBody
    public ResponseObject checkOrder(String orderNo) {
        return ResponseObject.newSuccessResponseObject(orderService.checkOrder(orderNo));
    }

    /**
     * 取消订单或删除订单
     *
     * @param type:0删除订单 1:取消订单
     * @param orderNo
     */
    @RequestMapping(value = "/updateOrderStatu", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateOrderStatu(Integer type, String orderNo) {
        OnlineUser user = getCurrentUser();
        orderService.updateOrderStatu(type, orderNo, user);
        return ResponseObject.newSuccessResponseObject("操作成功!");
    }


    /**
     * 查询目前购买的课程能参与的活动,以及每种活动下的购买课程信息
     *
     * @param ids 购买课程的id数组
     * @return
     */
    @RequestMapping(value = "/findActivityOrder", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject findActivityOrder(HttpServletRequest request, String ids) {
        if (StringUtils.isBlank(ids)) {
            return ResponseObject.newErrorResponseObject("参数错误!");
        }
        return ResponseObject.newSuccessResponseObject(orderService.findActivityOrder(request, ids));
    }

    /**
     * 获取用户购买课程所享受的所有活动，以及每种活动下能参与的课程
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/findActivityCourse", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject findActivityCourse(String ids) {
        if (StringUtils.isBlank(ids)) {
            return ResponseObject.newErrorResponseObject("参数错误!");
        }
        return ResponseObject.newSuccessResponseObject(orderService.findActivityCourse(ids));
    }

    /**
     * 根据课程id查询课程
     *
     * @param ids 课程id号
     * @return 返回对应的课程对象
     */
    @RequestMapping(value = "/findInactiveOrder", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject findInactiveOrder(String ids, String orderNo, HttpServletRequest request) {
        if (StringUtils.isBlank(ids)) {
            return ResponseObject.newErrorResponseObject("参数错误!");
        }
        return ResponseObject.newSuccessResponseObject(orderService.findNotActivityOrder(ids, orderNo, request));
    }


    /**
     * Description：查询用户消费记录
     *
     * @return ResponseObject
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    @RequestMapping(value = "/consumptionList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject consumptionList(Integer pageNumber, Integer pageSize) {
        BxgUser user = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(orderService.consumptionList(user.getId(), pageNumber, pageSize));
    }

}
