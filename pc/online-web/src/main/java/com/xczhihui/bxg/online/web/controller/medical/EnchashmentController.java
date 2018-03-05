package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.api.service.EnchashmentService;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.enums.OrderFrom;
import com.xczhihui.bxg.online.common.enums.VCodeType;
import com.xczhihui.bxg.online.web.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;


/**
 * 主播工作台结算控制层
 * @author yuruixin
 */
@RestController
@RequestMapping(value = "/anchor")
public class EnchashmentController {

    @Autowired
    private EnchashmentService enchashmentService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * Description：结算
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 8:14 2018/1/27 0027
     **/
    @RequestMapping(value = "/settlement")
    public ResponseObject settlement(HttpServletRequest request,Integer amount){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        enchashmentService.saveSettlement(user.getId(),amount, OrderFrom.PC);

        return ResponseObject.newSuccessResponseObject("结算成功！");
    }
    
    /** 
     * Description：提现
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 8:32 2018/1/30 0030
     **/
    @RequestMapping(value = "/enchashment")
    public ResponseObject enchashment(HttpServletRequest request, BigDecimal amount,Integer bankCardId,String code){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if(user==null){
            return ResponseObject.newErrorResponseObject("未登录");
        }
        verificationCodeService.checkCode(user.getLoginName(),code);
        enchashmentService.saveEnchashmentApplyInfo(user.getId(),amount,bankCardId,OrderFrom.PC);

        return ResponseObject.newSuccessResponseObject("提现成功！");
    }

    /**
     * Description：发送验证码
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:18 2018/2/2 0002
     **/
    @RequestMapping(value = "sendVerificationCode")
    public ResponseObject sendVerificationCode(HttpServletRequest req) {
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(req);
        if(user==null){
            return ResponseObject.newErrorResponseObject("未登录");
        }
        return ResponseObject.newSuccessResponseObject(verificationCodeService.addMessage(user.getLoginName(), VCodeType.ENCHASHMENT.getCode()+""));
    }
}
