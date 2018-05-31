package com.xczh.consumer.market.controller.live;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.online.api.service.CommonApiService;
import com.xczhihui.online.api.service.GiftService;
import com.xczhihui.online.api.service.UserCoinService;
import com.xczhihui.online.api.vo.ReceivedGift;
import com.xczhihui.online.api.vo.RechargeRecord;

/**
 * 用户调用这个接口，进入h5页面模式
 * ClassName: BrowserUserController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月2日<br>
 */
@Controller
@RequestMapping(value = "/bxg/oa")
public class OnlineApiController {

    @Autowired
    private UserCoinService userCoinService; //充值service
    @Autowired
    private GiftService giftService;  //礼物service
    @Autowired
    private CommonApiService commonApiService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OnlineApiController.class);

    /**
     * Description：得到用户充值列表
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "userCoinList")
    @ResponseBody
    public ResponseObject checkToken(@Account String accountId, HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (null == req.getParameter("pageNumber") && null == req.getParameter("pageSize")) {
            return ResponseObject.newErrorResponseObject("缺少分页参数");
        }
        int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));

        Page<RechargeRecord> page = userCoinService.getUserCoinIncreaseRecord(accountId
                , pageNumber, pageSize);
        LOGGER.info("page.getPageSize()" + page.getPageSize());
        return ResponseObject.newSuccessResponseObject(page);
    }

    @RequestMapping(value = "giftList")
    @ResponseBody
    public ResponseObject giftList(@Account String accountId, HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (null == req.getParameter("pageNumber") && null == req.getParameter("pageSize")) {
            return ResponseObject.newErrorResponseObject("缺少分页参数");
        }
        int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        @SuppressWarnings("unchecked")
        Page<ReceivedGift> page = (Page<ReceivedGift>) giftService.getReceivedGift(accountId
                , pageNumber, pageSize);
        LOGGER.info("page.getPageSize()" + page.getPageSize());
        return ResponseObject.newSuccessResponseObject(page);
    }

    /**
     * Description：获取打赏集合
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "rewardList")
    @ResponseBody
    public ResponseObject rewardList(@Account String accountId, HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (null == req.getParameter("pageNumber") && null == req.getParameter("pageSize")) {
            return ResponseObject.newErrorResponseObject("缺少分页参数");
        }
        int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        @SuppressWarnings("unchecked")
        Page<ReceivedGift> page = (Page<ReceivedGift>) giftService.getReceivedReward(accountId
                , pageNumber, pageSize);
        LOGGER.info("page.getPageSize()" + page.getPageSize());
        return ResponseObject.newSuccessResponseObject(page);
    }

    /**
     * Description：体现记录
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "cashList")
    @ResponseBody
    public ResponseObject cashList(HttpServletRequest req, HttpServletResponse res) throws Exception {
        throw new RuntimeException("请更新最新版本！");
    }

    /**
     * Description：身份信息
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "jobVo")
    @ResponseBody
    public ResponseObject JobVo(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String group = req.getParameter("group");
        return ResponseObject.newSuccessResponseObject(commonApiService.getJob(group));
    }
}
