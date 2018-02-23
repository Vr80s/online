package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.medical.anchor.service.IGiftOrderService;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订单控制层
 * @author zhuwenbao
 */
@RestController
@RequestMapping("/medical/order/gift")
public class GiftOrderController {

    @Autowired
    private IGiftOrderService giftOrderService;

    /**
     * 获取礼物订单列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseObject list(HttpServletRequest request, Integer current, Integer size){

        Page<UserCoinIncreaseVO> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        Page<UserCoinIncreaseVO> result = giftOrderService.list(this.getCurrentUserId(request), page);

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
