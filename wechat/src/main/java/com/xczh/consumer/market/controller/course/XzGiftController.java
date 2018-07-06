package com.xczh.consumer.market.controller.course;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.ClientType;
import com.xczhihui.common.util.enums.OrderFrom;
import com.xczhihui.online.api.service.GiftService;


/**
 * Description：礼物相关控制层
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 下午 5:19 2018/1/22 0022
 **/
@Controller
@RequestMapping(value = "/xczh/gift")
public class XzGiftController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(XzGiftController.class);
    @Autowired()
    @Qualifier("giftServiceImpl")
    private GiftService remoteGiftService;

    /**
     * 礼物榜单（直播间）
     *
     * @return
     * @throws SQLException
     */
    @ResponseBody
    @RequestMapping(value = "/rankingList")
    public ResponseObject rankingList(@RequestParam(value = "liveId") String liveId,
                                      @RequestParam(value = "pageNumber") Integer current,
                                      @RequestParam(value = "pageSize") Integer size) throws SQLException {
        return ResponseObject.newSuccessResponseObject(remoteGiftService.getRankingListByLiveId(liveId, current, size));
    }

    /**
     * 礼物列表
     *
     * @param req
     * @param res
     * @return
     * @throws SQLException
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ResponseObject list() throws SQLException {
        return ResponseObject.newSuccessResponseObject(remoteGiftService.getGift());
    }

    /**
     * Description：赠送礼物接口（做礼物赠送余额扣减和检验）
     *
     * @return ResponseObject
     * @throws IOException
     * @throws SmackException
     * @throws XMPPException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @author name：liutao <br>email: gvmtar@gmail.com
     **/
    @ResponseBody
    @RequestMapping(value = "/sendGift")
    public ResponseObject sendGift(String giftId,String liveId,Integer count, String receiverId,
            @Account String accountId) throws SQLException, XMPPException, SmackException, IOException, IllegalAccessException, InvocationTargetException {

        Map<String, Object> map = null;
        map = remoteGiftService.addGiftStatement(accountId,
                receiverId, giftId,
                ClientType.getClientType(HeaderInterceptor.getClientTypeCode()), count, liveId);

        return ResponseObject.newSuccessResponseObject(map);
    }
}
