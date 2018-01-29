package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.api.service.EnchashmentService;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.enums.OrderFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * 主播工作台课程控制层
 * @author yuruixin
 */
@RestController
@RequestMapping(value = "/anchor/enchashment")
public class EnchashmentController {

    @Autowired
    private EnchashmentService enchashmentService;

    /**
     * Description：结算
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 8:14 2018/1/27 0027
     **/
    @RequestMapping(value = "/settlement",method= RequestMethod.GET)
    public ResponseObject getCourseApplyList(HttpServletRequest request,Integer amount){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        enchashmentService.saveSettlement(user.getId(),amount, OrderFrom.PC);
        return ResponseObject.newSuccessResponseObject("结算成功！");
    }

}
