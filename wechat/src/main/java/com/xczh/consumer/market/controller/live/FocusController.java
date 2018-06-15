package com.xczh.consumer.market.controller.live;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.utils.ResponseObject;

/**
 * 直播控制器
 * ClassName: LiveController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月10日<br>
 */
@Controller
@RequestMapping("/bxg/focus")
public class FocusController {


    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FocusController.class);

    /**
     * Description： 我的主页
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @SuppressWarnings("unused")
    @RequestMapping("myHome")
    @ResponseBody
    public ResponseObject myHome(HttpServletRequest req,
                                 HttpServletResponse res, Map<String, String> params)
            throws Exception {
        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * Description： 我的粉丝
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("myFans")
    @ResponseBody
    public ResponseObject myFans(HttpServletRequest req,
                                 HttpServletResponse res, Map<String, String> params)
            throws Exception {
        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * Description： 关注的人（我的关注）
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("myFocus")
    @ResponseBody
    public ResponseObject myFocus(HttpServletRequest req,
                                  HttpServletResponse res, Map<String, String> params)
            throws Exception {
        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * Description： 取消关注
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("cancel")
    @ResponseBody
    public ResponseObject cancelFocus(HttpServletRequest req,
                                      HttpServletResponse res, Map<String, String> params)
            throws Exception {
        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * Description： 增加关注信息
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("add")
    @ResponseBody
    public ResponseObject addFocus(HttpServletRequest req,
                                   HttpServletResponse res)
            throws Exception {
        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }
}
