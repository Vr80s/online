package com.xczhihui.bxg.online.web.controller.medical;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.OrderFrom;
import com.xczhihui.common.util.enums.OrderStatus;
import com.xczhihui.course.exception.OrderException;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.model.Order;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IOrderService;
import com.xczhihui.medical.anchor.service.ICourseOrderService;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;
import com.xczhihui.online.api.service.UserCoinService;
import com.xczhihui.online.api.service.XmbBuyCouserService;

/**
 * 订单控制层
 *
 * @author zhuwenbao
 */
@Controller
@RequestMapping("order")
public class CourseOrderController extends AbstractController {

    @Autowired
    private ICourseOrderService courseOrderService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private XmbBuyCouserService xmbBuyCouserService;

    @Value("${rate}")
    private Integer rate;

    /**
     * 获取课程订单列表
     *
     * @param current   当前页
     * @param size      每页显示的数据条数
     * @param gradeName 课程名
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseObject list(HttpServletRequest request, Integer current, Integer size,
                               String gradeName, String startTime, String endTime,
                               Integer courseForm, Integer multimediaType) {

        Page<UserCoinIncreaseVO> page = new Page<>();
        page.setCurrent(current != null && current > 0 ? current : 1);
        page.setSize(size != null && size > 0 ? size : 10);

        Page<UserCoinIncreaseVO> result = courseOrderService.list(getUserId(), page,
                gradeName, startTime, endTime, courseForm, multimediaType);

        return ResponseObject.newSuccessResponseObject(result);
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public ResponseObject createOrder(@PathVariable int id) {
        Order order = orderService.createOrder(getUserId(), id, OrderFrom.PC.getCode());
        return ResponseObject.newSuccessResponseObject(order.getId());
    }
    
    
    /**
     * 创建订单，并且重定向到支付页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/create/{id}", method = RequestMethod.GET)
    public ModelAndView createOrderRedirectPay(@PathVariable int id) {
        Order order = orderService.createOrder(getUserId(), id, OrderFrom.PC.getCode());
        order.setActualPay(order.getActualPay() * 10);
        return new ModelAndView("redirect:/order/pay?orderId=" + order.getId());
    }

    @RequestMapping(value = "pay", method = RequestMethod.GET)
    public ModelAndView pay(@RequestParam String orderId) {
        ModelAndView modelAndView = new ModelAndView("pay/pay");
        Order order = orderService.getOrderNo4PayByOrderId(orderId);
        if (order == null) {
            throw new OrderException("订单不存在,id:" + orderId);
        }
        String balance = userCoinService.getBalanceByUserId(getUserId());
        //换算成熊猫币
        order.setActualPay(order.getActualPay() * rate);
        modelAndView.addObject("order", order);
        modelAndView.addObject("courses", courseService.findByIds(order.getCourseIds()));
        modelAndView.addObject("balance", balance);
        modelAndView.addObject("needRechargeCoin", Double.compare(order.getActualPay(), Double.valueOf(balance)) > 0);
        modelAndView.addObject("url", "/order/pay/success?orderId" + orderId);
        return modelAndView;
    }

    /**
     * 熊猫币支付
     *
     * @param orderNo 订单号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pay/{orderNo}", method = RequestMethod.POST)
    public ResponseObject payCourseOrderByXmb(@PathVariable String orderNo) {
        xmbBuyCouserService.xmbBuyCourseLock(orderNo);
        return ResponseObject.newSuccessResponseObject("支付成功");
    }

    @RequestMapping(value = "pay/success", method = RequestMethod.GET)
    public ModelAndView paySuccess(@RequestParam String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order.getOrderStatus() != OrderStatus.PAID.getCode()) {
            throw new OrderException("订单状态异常 id: " + orderId);
        }
        String balance = userCoinService.getBalanceByUserId(getUserId());
        List<Course> courses = courseService.findByIds(order.getCourseIds());
        ModelAndView modelAndView = new ModelAndView("/pay/success");
        modelAndView.addObject("order", order);
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("balance", balance);
        Course course = courses.get(0);
        modelAndView.addObject("recommendCourses", courseService.findByMenuIdExcludeId(course.getMenuId(), course.getId()));
        return modelAndView;
    }
}
