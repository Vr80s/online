package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.medical.anchor.service.ICourseOrderService;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单控制层
 * @author zhuwenbao
 */
@RestController
@RequestMapping("/medical/order/course")
public class CourseOrderController {

    @Autowired
    private ICourseOrderService courseOrderService;

    /**
     * 获取课程订单列表
     * @param current 当前页
     * @param size 每页显示的数据条数
     * @param gradeName 课程名
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseObject list(HttpServletRequest request, Integer current, Integer size,
                               String gradeName, String startTime, String endTime,
                               Integer courseForm, Integer multimediaType){

        Page<UserCoinIncreaseVO> page = new Page<>();
        page.setCurrent(current != null && current > 0 ? current : 1);
        page.setSize(size != null && size > 0 ? size : 10);

        Page<UserCoinIncreaseVO> result = courseOrderService.list(this.getCurrentUserId(request), page,
                gradeName, startTime, endTime, courseForm, multimediaType);

        return ResponseObject.newSuccessResponseObject(result);
    }

    /**
     * 获取用户id
     */
    private String getCurrentUserId(HttpServletRequest request){
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if (loginUser == null) {
            throw new RuntimeException("用户未登录");
        }
        return loginUser.getId();
    }


}
