package com.xczhihui.bxg.online.web.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xczhihui.bxg.online.common.domain.GiftStatement;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.ClientType;
import com.xczhihui.common.util.enums.OrderFrom;
import com.xczhihui.common.util.enums.Payment;
import com.xczhihui.common.util.enums.VhallCustomMessageType;
import com.xczhihui.common.util.vhallyun.MessageService;
import com.xczhihui.online.api.service.GiftService;


/**
 * ClassName: GiftController.java <br>
 * Description: 礼物打赏接口<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
@RestController
@RequestMapping(value = "/gift")
public class GiftController extends AbstractController {

    @Autowired
    private GiftService giftService;

    /**
     * Description：赠送礼物接口（做礼物赠送余额扣减和检验）
     *
     * @return ResponseObject
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    @RequestMapping(value = "/getGift")
    public ResponseObject getGift() {
        return ResponseObject.newSuccessResponseObject(giftService.getGift());
    }

    /**
     * Description：赠送礼物接口（做礼物赠送余额扣减和检验）
     *
     * @param giftStatement
     * @return ResponseObject
     * @throws IOException
     * @throws SmackException
     * @throws XMPPException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    @RequestMapping(value = "/sendGift")
    public ResponseObject sendGift(GiftStatement giftStatement,
                                   HttpServletRequest request) throws XMPPException, SmackException, IOException, IllegalAccessException, InvocationTargetException, InterruptedException {
        Map<String, Object> map = new HashMap<String, Object>();
        OnlineUser u = getCurrentUser();
        if (u != null) {
            giftStatement.setGiver(u.getId());
            giftStatement.setClientType(ClientType.PC.getCode());
            giftStatement.setPayType(Payment.COINPAY.getCode());
            map = giftService.addGiftStatement(u.getId(), giftStatement.getReceiver(),
                    giftStatement.getGiftId(), ClientType.PC, giftStatement.getCount(), giftStatement.getLiveId());
        }
        return ResponseObject.newSuccessResponseObject(map);
    }

    /**
     * Description：接收到的礼物
     *
     * @return ResponseObject
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    @RequestMapping(value = "/receivedGift")
    @ResponseBody
    public ResponseObject receivedGift(HttpServletRequest request, Integer pageNumber, Integer pageSize) throws Exception {
        OnlineUser u = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(giftService.getReceivedGift(u.getId(), pageNumber, pageSize));
    }

    /**
     * Description：接收到的打赏
     *
     * @return ResponseObject
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    @RequestMapping(value = "/receivedReward")
    @ResponseBody
    public ResponseObject receivedReward(HttpServletRequest request, Integer pageNumber, Integer pageSize) throws Exception {
        OnlineUser u = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(giftService.getReceivedReward(u.getId(), pageNumber, pageSize));
    }

    /**
     * Description：分页获取直播课程列表
     *
     * @return ResponseObject
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    @RequestMapping(value = "/getLiveCourseByUserId")
    @ResponseBody
    public ResponseObject getLiveCourseByUserId(HttpServletRequest request, Integer pageNumber, Integer pageSize) throws Exception {
        OnlineUser u = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(giftService.getLiveCourseByUserId(u.getId(), pageNumber, pageSize));
    }

    /**
     * Description：获取直播课程对应的课程报名情况
     *
     * @return ResponseObject
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    @RequestMapping(value = "/getLiveCourseUsersById")
    @ResponseBody
    public ResponseObject getLiveCourseUsersById(HttpServletRequest request, String id, Integer pageNumber, Integer pageSize) throws Exception {
        OnlineUser u = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(giftService.getLiveCourseUsersById(id, u.getId(), pageNumber, pageSize));
    }


    /**
     * Description：接收到的礼物
     *
     * @return ResponseObject
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    @RequestMapping(value = "/getRankingListByLiveId")
    @ResponseBody
    public ResponseObject getRankingListByLiveId(HttpServletRequest request,
                                                 String liveId,
                                                 Integer pageNumber, Integer pageSize) throws Exception {
        return ResponseObject.newSuccessResponseObject(giftService.getRankingListByLiveId(liveId, pageNumber, pageSize));
    }

    
    /**
     * Description：赠送礼物接口（做礼物赠送余额扣减和检验）
     *
     * @param giftStatement
     * @return ResponseObject
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @throws Exception 
     **/
    @RequestMapping(value = "/vhallSendGift")
    public ResponseObject customSendGift(GiftStatement giftStatement,String channel_id,
                                   HttpServletRequest request) throws Exception {
        
    	Map<String, Object> mapRanking = new HashMap<String, Object>();
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
        OnlineUser u = getCurrentUser();
        if (u != null) {
            giftStatement.setGiver(u.getId());
            giftStatement.setClientType(ClientType.PC.getCode());
            giftStatement.setPayType(Payment.COINPAY.getCode());
            map = giftService.addGiftStatement(u.getId(), giftStatement.getReceiver(),
                    giftStatement.getGiftId(), ClientType.PC, giftStatement.getCount(), giftStatement.getLiveId());
            
        }
        mapRanking.putAll(map);
        
        //删除排行榜  -- 因为加上排行榜字符太长
        map.remove("ranking");
        
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("type",VhallCustomMessageType.GIFT_MESSAGE.getCode());
        jsonObject.put("message",map);
        //后台把这个消息广播出去
        MessageService.sendMessage(MessageService.CustomBroadcast,jsonObject.toJSONString(),channel_id);
       
        return ResponseObject.newSuccessResponseObject(mapRanking);
    }

}