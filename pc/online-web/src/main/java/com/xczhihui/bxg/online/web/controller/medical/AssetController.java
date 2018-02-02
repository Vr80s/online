package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.utils.VhallUtil;
import com.xczhihui.bxg.online.web.service.VerificationCodeService;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.model.CourseApplyResource;
import com.xczhihui.medical.anchor.service.IAssetService;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.medical.anchor.service.IUserBankService;
import com.xczhihui.medical.anchor.vo.CourseApplyInfoVO;
import com.xczhihui.medical.anchor.vo.CourseApplyResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * 主播工作台资产控制层
 * @author yuruixin
 */
@RestController
@RequestMapping(value = "/anchor/asset")
public class AssetController {

    @Autowired
    private IAssetService assetService;
    @Autowired
    private IUserBankService userBankService;

    /**
     * Description：获取熊猫币交易记录
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 10:06 2018/2/2 0002
     **/
    @RequestMapping(value = "/getCoinTransactionList",method= RequestMethod.GET)
    public ResponseObject getCoinTransactionList(HttpServletRequest request,Integer current, Integer size){
        Page<Map> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if(user==null){
            return ResponseObject.newErrorResponseObject("未登录");
        }
        return ResponseObject.newSuccessResponseObject(assetService.getCoinTransactionPage(page,user.getId()));
    }

    /**
     * Description：获取人民币余额
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 11:05 2018/2/2 0002
     **/
    @RequestMapping(value = "/getRmbTransactionList",method= RequestMethod.GET)
    public ResponseObject getRmbTransactionList(HttpServletRequest request,Integer current, Integer size){
        Page<Map> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if(user==null){
            return ResponseObject.newErrorResponseObject("未登录");
        }
        return ResponseObject.newSuccessResponseObject(assetService.getRmbTransactionPage(page,user.getId()));
    }

    /**
     * Description：获取银行卡列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 12:00 2018/2/2 0002
     **/
    @RequestMapping(value = "/getBankCardList",method= RequestMethod.GET)
    public ResponseObject getBankCardList(HttpServletRequest request){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if(user==null){
            return ResponseObject.newErrorResponseObject("未登录");
        }
        return ResponseObject.newSuccessResponseObject(userBankService.selectUserBankByUserId(user.getId()));
    }

    /**
     * Description：保存银行卡
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 11:54 2018/2/2 0002
     **/
    @RequestMapping(value = "/saveBankCard")
    public ResponseObject saveBankCard(HttpServletRequest request,String acctName,String acctPan,String certId,String tel){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if(user==null){
            return ResponseObject.newErrorResponseObject("未登录");
        }
        userBankService.addUserBank(user.getId(),acctName,acctPan,certId,tel);
        return ResponseObject.newSuccessResponseObject("新增银行卡成功！");
    }

}
