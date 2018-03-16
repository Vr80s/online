package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.enums.BankCardType;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.medical.anchor.service.IAssetService;
import com.xczhihui.medical.anchor.service.IUserBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 主播工作台资产控制层
 * @author yuruixin
 */
@RestController
@RequestMapping(value = "/anchor/asset")
public class AssetController extends AbstractController{

    @Autowired
    private IAssetService assetService;
    @Autowired
    private IUserBankService userBankService;
    @Autowired
    private UserCoinService userCoinService;

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
        OnlineUser user = getOnlineUser(request);
        return ResponseObject.newSuccessResponseObject(assetService.getCoinTransactionPage(page,user.getId()));
    }

    /**
     * Description：获取人民币交易记录
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 11:05 2018/2/2 0002
     **/
    @RequestMapping(value = "/getRmbTransactionList",method= RequestMethod.GET)
    public ResponseObject getRmbTransactionList(HttpServletRequest request,Integer current, Integer size){
        Page<Map> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        OnlineUser user = getOnlineUser(request);
        return ResponseObject.newSuccessResponseObject(assetService.getRmbTransactionPage(page,user.getId()));
    }

    /**
     * Description：获取银行卡列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 12:00 2018/2/2 0002
     **/
    @RequestMapping(value = "/getBankCardList",method= RequestMethod.GET)
    public ResponseObject getBankCardList(HttpServletRequest request,boolean complate){
        OnlineUser user = getOnlineUser(request);
        return ResponseObject.newSuccessResponseObject(userBankService.selectUserBankByUserId(user.getId(),complate));
    }

    @RequestMapping(value = "/getPhoneNumber",method= RequestMethod.GET)
    public ResponseObject getPhoneNumber(HttpServletRequest request){
        OnlineUser user = getOnlineUser(request);
        return ResponseObject.newSuccessResponseObject(user.getLoginName());
    }

    /**
     * Description：保存银行卡
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 11:54 2018/2/2 0002
     **/
    @RequestMapping(value = "/saveBankCard")
    public ResponseObject saveBankCard(HttpServletRequest request,String acctName,String acctPan,String certId,String tel){
        OnlineUser user = getOnlineUser(request);
        userBankService.addUserBank(user.getId(),acctName,acctPan,certId,tel);
        return ResponseObject.newSuccessResponseObject("新增银行卡成功！");
    }

    @RequestMapping(value = "/deleteBankCard")
    public ResponseObject deleteBankCard(HttpServletRequest request,Integer id){
        OnlineUser user = getOnlineUser(request);
        userBankService.deleteBankCard(user.getId(),id);
        return ResponseObject.newSuccessResponseObject("移除银行卡成功！");
    }

    @RequestMapping(value = "/setDefaultBankCard")
    public ResponseObject setDefaultBankCard(HttpServletRequest request,Integer id){
        OnlineUser user = getOnlineUser(request);
        userBankService.updateDefault(user.getId(),id);
        return ResponseObject.newSuccessResponseObject("设置默认成功！");
    }

    /**
     * Description：获取用户的熊猫币余额 人民币余额 绑定银行卡数量
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/2/10 0010 下午 4:06
     **/
    @RequestMapping(value = "/getBaseAssetInfo")
    public ResponseObject getBaseAssetInfo(HttpServletRequest request){
        OnlineUser user = getOnlineUser(request);
        int bankCount = userBankService.getBankCount(user.getId());
        String coinBalance = userCoinService.getSettlementBalanceByUserId(user.getId());
        String rmb = userCoinService.getEnchashmentBalanceByUserId(user.getId());
        Map<String,Object> map = new HashMap<>();
        map.put("bankCount",bankCount);
        map.put("rmb",rmb);
        map.put("coinBalance",coinBalance);
        return ResponseObject.newSuccessResponseObject(map);
    }

    @RequestMapping(value = "/getBankList")
    public ResponseObject getBankList(HttpServletRequest request){
        List<Map> bankList = BankCardType.getBankCardList();
        return ResponseObject.newSuccessResponseObject(bankList);
    }


}
