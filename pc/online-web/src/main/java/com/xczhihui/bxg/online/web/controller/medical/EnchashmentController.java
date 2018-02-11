package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.api.po.EnchashmentApplyInfo;
import com.xczhihui.bxg.online.api.service.EnchashmentService;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.enums.OrderFrom;
import com.xczhihui.bxg.online.common.enums.VCodeType;
import com.xczhihui.bxg.online.common.utils.RedissonUtil;
import com.xczhihui.bxg.online.web.service.VerificationCodeService;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;


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
    @Autowired
    private RedissonUtil redissonUtil;

    /**
     * Description：结算
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 8:14 2018/1/27 0027
     **/
    @RequestMapping(value = "/settlement")
    public ResponseObject settlement(HttpServletRequest request,Integer amount){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        // 获得锁对象实例
        RLock redissonLock = redissonUtil.getRedisson().getLock("saveSettlement"+user.getId());

        boolean resl = false;
        try {
            //等待3秒 有效期8秒
            resl = redissonLock.tryLock(3, 8, TimeUnit.SECONDS);
            if(resl){
                enchashmentService.saveSettlement(user.getId(),amount, OrderFrom.PC);
            }
        }catch (RuntimeException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("网络错误，请重试");
        }finally {
            if(resl){
                redissonLock.unlock();
            }else{
                throw new RuntimeException("网络错误，请重试");
            }
        }

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
        // 获得锁对象实例
        RLock redissonLock = redissonUtil.getRedisson().getLock("saveEnchashmentApplyInfo"+user.getId());

        boolean resl = false;
        try {
            //等待3秒 有效期8秒
            resl = redissonLock.tryLock(3, 8, TimeUnit.SECONDS);
            if(resl){
                verificationCodeService.checkCode(user.getLoginName(),code);
                enchashmentService.saveEnchashmentApplyInfo(user.getId(),amount,bankCardId,OrderFrom.PC);
            }
        }catch (RuntimeException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("网络错误，请重试");
        }finally {
            if(resl){
                redissonLock.unlock();
            }else{
                throw new RuntimeException("网络错误，请重试");
            }
        }

        return ResponseObject.newSuccessResponseObject("结算成功！");
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
