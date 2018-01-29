package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.api.service.OrderPayService;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.enums.Payment;
import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.bxg.online.web.service.OrderService;
import com.xczhihui.bxg.online.web.vo.OrderVo;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * 订单控制层类
 *
 * @author 康荣彩
 * @create 2016-08-30 21:04
 */
@Controller
@RequestMapping(value = "/web")
public class OrderController {

    @Autowired
    private OrderService  orderService;
    @Autowired
    private OrderPayService orderPayService;
    @Autowired
    private CourseService courseService;
    private Object lock = new Object();

    @Value("${online.web.url}")
    private String weburl;

    /**
     * 提交订单的时候，生成订单
     * @param ids
     * @param request
     */
    @RequestMapping(value = "/{ids}/{orderNo}/saveOrder",method= RequestMethod.GET)
    public  ModelAndView saveOrder( @PathVariable String ids,@PathVariable String orderNo,HttpServletRequest request,RedirectAttributes attr ) throws IOException {
        ModelAndView mav=new ModelAndView();
        //如果session中不存在此订单号，直接跳转到我的订单页面
        if(request.getSession().getAttribute(orderNo)==null){
            mav.setViewName("redirect:/web/html/myStudyCenter.html");
            return mav;
        }
        String[] params=request.getSession().getAttribute(orderNo).toString().split("#");
        if( !params[1].equals(ids)){
            throw new RuntimeException("您的订单与所买课程不符合!");
        }
        OnlineUser u =  (OnlineUser)request.getSession().getAttribute("_user_");
        Map mapValues=null;

        if( u != null){
                //是否已经生成此课程待支付订单 如果未生成:则生成订单,并且限时免费课购买成功
               Map<String,Object> result=orderService.findOrderByCourseId(ids, u.getId(),orderNo);
               if("false".equals(result.get("isBuy").toString())) {
                   mapValues= orderService.saveOrder(orderNo, ids, request);
                   //限时免费课或总支付为0元，购买成功
                   if(orderService.findCourseIsFree(ids) || Double.valueOf(mapValues.get("actualPay").toString()) <= 0){
                       synchronized (lock) {
                           String transaction_id="activity"+ UUID.randomUUID().toString().replaceAll("-", "").substring(0,22);
                           orderPayService.addPaySuccess(mapValues.get("orderNo").toString(), Payment.OTHERPAY, transaction_id);
//                           orderService.addPaySuccess(mapValues.get("orderNo").toString(), 0, transaction_id);
                           //为购买用户发送购买成功的消息通知
                           String path = request.getContextPath();
                           String basePath =weburl;
                           orderService.savePurchaseNotice(basePath, mapValues.get("orderNo").toString());
                       }
                       mav.setViewName("redirect:/web/html/myStudyCenter.html");
                   } else { //付费课程只是成成
                       mav.setViewName("PayOrder");
                       mav.addObject("orderNo", mapValues.get("orderNo"));
                       mav.addObject("actualPay", String.format("%.2f", Double.valueOf(mapValues.get("actualPay").toString())));
                       mav.addObject("courseName", mapValues.get("courseName"));
                       mav.addObject("orderId", orderService.findOrderByOrderNo(mapValues.get("orderNo").toString()).getId());
                   }
               }else{
                   mav.setViewName("PayOrder");
                   mav.addObject("orderNo", result.get("order_no").toString());

                   String orderNo12=result.get("order_no").toString();
                   mav.addObject("orderId", orderService.findOrderByOrderNo(orderNo12).getId());
                   mav.addObject("actualPay", String.format("%.2f", Double.valueOf(result.get("actualPay").toString())));
                   mav.addObject("courseName", result.get("courseName").toString());
               }

        }
            return mav;
    }

    /**
     * 根据订单号查找订单
     * @param orderNo
     * @param request
     */
    @RequestMapping(value = "/{orderNo}/findOrderByOrderNo",method= RequestMethod.GET)
    public  ModelAndView findOrderByOrderNo(@PathVariable String orderNo,HttpServletRequest request,String orderId){
        OrderVo orderVo = orderService.findOrderByOrderId(orderId);
        ModelAndView mav=new ModelAndView("PayOrder");
        if(orderVo != null){
            mav.addObject("orderNo",orderVo.getOrder_no());
            mav.addObject("actualPay",orderVo.getActual_pay());
            mav.addObject("courseName",orderVo.getCourse_name());
        }
        return  mav;
    }


    /**
     * 获取用户全部订单信息
     * @param  orderStatus 订单支付状态
     * @param  timeQuantum 订单时间段
     * @param  pageNumber 当前页
     * @param  pageSize 每页条数
     * @return 所有订单信息
     */
    @RequestMapping(value = "/getMyAllOrder",method= RequestMethod.GET)
    @ResponseBody
    public ResponseObject getMyAllOrder(Integer  orderStatus,Integer timeQuantum,Integer pageNumber, Integer pageSize,HttpServletRequest request){
       return ResponseObject.newSuccessResponseObject(orderService.getMyAllOrder(orderStatus,timeQuantum, pageNumber,  pageSize,request));
    }
    /**
     * 返回当前订单支付状态
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "/getOrderStatus",method= RequestMethod.GET)
    @ResponseBody
    public ResponseObject getOrderStatus(String orderNo){
        return ResponseObject.newSuccessResponseObject(orderService.getOrderStatus(orderNo));
    }
    /**
     * 检查订单是否已支付
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "/checkOrder")
    @ResponseBody
    public ResponseObject checkOrder(String orderNo,HttpServletRequest req){
        BxgUser user = UserLoginUtil.getLoginUser(req);
    	if (user == null) {
			return ResponseObject.newErrorResponseObject("请登录！");
		}
        return ResponseObject.newSuccessResponseObject(orderService.checkOrder(orderNo));
    }

    /**
     * 取消订单或删除订单
     * @param type:0删除订单 1:取消订单
     * @param orderNo
     */
    @RequestMapping(value = "/updateOrderStatu", method= RequestMethod.POST)
    @ResponseBody
    public ResponseObject  updateOrderStatu(Integer type,String orderNo,HttpServletRequest request){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("请登录！");
        }
        orderService.updateOrderStatu(type,orderNo,user);
        return  ResponseObject.newSuccessResponseObject("操作成功!");
    }



    /**
     *查询目前购买的课程能参与的活动,以及每种活动下的购买课程信息
     * @param ids 购买课程的id数组
     * @return
     */
    @RequestMapping(value = "/findActivityOrder", method= RequestMethod.GET)
    @ResponseBody
    public ResponseObject  findActivityOrder(HttpServletRequest request,String ids){
        if(StringUtils.isBlank(ids)){
            return ResponseObject.newErrorResponseObject("参数错误!");
        }
        return   ResponseObject.newSuccessResponseObject(orderService.findActivityOrder(request,ids));
    }

    /**
     * 获取用户购买课程所享受的所有活动，以及每种活动下能参与的课程
     * @param ids
     * @return
     */
    @RequestMapping(value = "/findActivityCourse", method= RequestMethod.GET)
    @ResponseBody
    public ResponseObject  findActivityCourse(String ids){
        if(StringUtils.isBlank(ids)){
            return ResponseObject.newErrorResponseObject("参数错误!");
        }
        return ResponseObject.newSuccessResponseObject(orderService.findActivityCourse(ids));
    }

    /**
     * 根据课程id查询课程
     * @param ids  课程id号
     * @return 返回对应的课程对象
     */
    @RequestMapping(value = "/findInactiveOrder", method= RequestMethod.GET)
    @ResponseBody
    public ResponseObject  findInactiveOrder(String  ids,String orderNo,HttpServletRequest request){
        if(StringUtils.isBlank(ids)){
            return ResponseObject.newErrorResponseObject("参数错误!");
        }
        return ResponseObject.newSuccessResponseObject(orderService.findNotActivityOrder(ids, orderNo, request));
    }
    
    
    /** 
     * Description：查询用户消费记录
     * @param request
     * @return
     * @return ResponseObject
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    @RequestMapping(value = "/consumptionList", method= RequestMethod.GET)
    @ResponseBody
    public ResponseObject consumptionList(HttpServletRequest request,Integer pageNumber, Integer pageSize){
    	BxgUser user = UserLoginUtil.getLoginUser(request);
    	if (user == null) {
			return ResponseObject.newErrorResponseObject("请登录！");
		}
    	return ResponseObject.newSuccessResponseObject(orderService.consumptionList(user.getId(),pageNumber, pageSize));
    }

}
