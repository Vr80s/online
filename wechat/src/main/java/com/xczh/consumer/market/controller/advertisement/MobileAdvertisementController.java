package com.xczh.consumer.market.controller.advertisement;

import com.xczh.consumer.market.utils.APPUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.model.MobileAdvertisement;
import com.xczhihui.course.service.IMobileAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Description：广告页
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/8/7 16:57
 **/
@Controller
@RequestMapping("/xczh/advertisement")
public class MobileAdvertisementController {

    @Autowired
    private IMobileAdvertisementService mobileAdvertisementService;

    /**
     * 广告页
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject mobileAdvertisement(HttpServletRequest req) {
        MobileAdvertisement mobileAdvertisement = mobileAdvertisementService.selectMobileAdvertisement(APPUtil.getMobileSource(req));
        return ResponseObject.newSuccessResponseObject(mobileAdvertisement);
    }

    /**
     * 添加点击量
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addClickSum(Integer id ) {
        mobileAdvertisementService.addClickNum(id);
        return ResponseObject.newSuccessResponseObject("添加成功");
    }
}
