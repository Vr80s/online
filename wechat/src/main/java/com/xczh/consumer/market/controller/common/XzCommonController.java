package com.xczh.consumer.market.controller.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.VersionService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.VersionCompareUtil;
import com.xczh.consumer.market.vo.VersionInfoVo;
import com.xczhihui.course.enums.MessageTypeEnum;
import com.xczhihui.course.enums.RouteTypeEnum;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.online.api.service.CommonApiService;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.vo.ThirdFlag;
import com.xczhihui.user.center.vo.Token;

/**
 * 通用控制器 ClassName: CommonController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月12日<br>
 */
@Controller
@RequestMapping("/xczh/common")
public class XzCommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(XzCommonController.class);

    @Autowired
    private VersionService versionService;

    @Autowired
    private CommonApiService commonApiService;

    @Autowired
    private ICourseService courseServiceImpl;

    @Autowired
    private ICommonMessageService commonMessageService;
    @Autowired
    private UserCenterService userCenterService;

    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    @Value("${webdomain}")
    private String webdomain;

    @Value("${gift.im.room.postfix}")
    private String postfix;
    @Value("${gift.im.boshService}")
    private String boshService;
    @Value("${gift.im.host}")
    private String host;

    /**
     * 查询单个详情
     *
     * @param type
     * @throws Exception
     */
    @RequestMapping(value = "/richTextDetails")
    @ResponseBody
    public ResponseObject richTextDetails(@RequestParam("type") Integer type, @RequestParam("typeId") String typeId) throws Exception {

        return ResponseObject.newSuccessResponseObject(courseServiceImpl.selectCourseDescription(type, typeId));
    }


    /**
     * 请求转发用于验证用户的登录状态
     *
     * @param req
     * @throws Exception
     */
    @RequestMapping(value = "/verifyLoginStatus")
    @ResponseBody
    public ResponseObject verifyLoginStatus(HttpServletRequest req) throws Exception {
        Integer statusFalg = 1000;
        if (req.getParameter("statusFalg") != null) {
            statusFalg = Integer.parseInt(req.getParameter("statusFalg"));
        }
        String openId = req.getParameter("openId");
        String unionId = req.getParameter("unionId");
        if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(unionId)) {
            ThirdFlag tf = new ThirdFlag();
            tf.setOpenId(openId);
            tf.setUnionId(unionId);
            return ResponseObject.newSuccessResponseObject(tf, statusFalg);
        }
        return ResponseObject.newSuccessResponseObject("登录状态验证", statusFalg);
    }


    /**
     * app端 tokenfilter 验证token是否有效
     *
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "checkToken")
    @ResponseBody
    public ResponseObject checkToken(HttpServletRequest req) throws Exception {

        String token = req.getParameter("token");
        if (!StringUtils.isNotBlank(token)) {
            return ResponseObject.newErrorResponseObject("token不能为空", 1001);
        }
        Token redisToken = userCenterService.getToken(token);
        if (null == redisToken) {
            return ResponseObject.newErrorResponseObject("已过期", 1002);
        } else {
            return ResponseObject.newSuccessResponseObject("有效", 1000);
        }
    }

    /**
     * Description：获取IM服务连接配置信息
     *
     * @param req
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("getImServerConfig")
    @ResponseBody
    public ResponseObject getImServerConfig(
            HttpServletRequest req,
            Integer courseId, @Account OnlineUser account) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("guId", account.getId());
        map.put("guPwd", account.getPassword());
        map.put("host", host);
        map.put("boshService", boshService);
        map.put("roomJId", courseId + postfix);
        return ResponseObject.newSuccessResponseObject(map);
    }

    /**
     * 意见反馈接口
     *
     * @param content
     * @return
     * @throws Exception
     */
    @RequestMapping("addOpinion")
    @ResponseBody
    public ResponseObject addOpinion(@RequestParam("content") String content, @Account(optional = true) Optional<String> accountIdOpt) throws Exception {

        String userId = accountIdOpt.orElse(null);
        commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.FEEDBACK.getVal())
                .buildWeb(content)
                .build(userId, RouteTypeEnum.NONE, userId)
        );
        return ResponseObject.newSuccessResponseObject(null);
    }

    /**
     * Description：检查更新
     *
     * @param userVersion
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("checkUpdate")
    @ResponseBody
    public ResponseObject checkUpdate(@RequestParam("type") Integer type,
                                      @RequestParam("version") String userVersion)
            throws Exception {

        VersionInfoVo newVer = versionService.getNewVersion(type);
        VersionInfoVo defaultNoUpdateResult = new VersionInfoVo();
        defaultNoUpdateResult.setIsUpdate(false);
        if (newVer == null) {
            return ResponseObject.newSuccessResponseObject(defaultNoUpdateResult);
        }
        LOGGER.info("userVersion:" + userVersion);
        LOGGER.info("newVer.getVersion():" + newVer.getVersion());
        // 对比版本号
        String newVersion = newVer.getVersion();
        int diff = VersionCompareUtil.compareVersion(newVersion, userVersion);
        if (diff <= 0) {
            LOGGER.info("{}{}{}{}{}-----》已经是最新版本，不需要更新");
            return ResponseObject.newSuccessResponseObject(defaultNoUpdateResult);
        }
        LOGGER.info("{}{}{}{}{}-----》已经是最新版本，需要更新了" + "-------ismustipdate:" + newVer.getIsMustUpdate());

        newVer.setIsUpdate(true);

        return ResponseObject.newSuccessResponseObject(newVer);
    }

    /**
     * 获取 同环境下的 pc端主域名
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("getDomain")
    @ResponseBody
    public ResponseObject getDomain() throws Exception {
        try {
            return ResponseObject.newSuccessResponseObject(webdomain);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("请求有误");
        }
    }

    /**
     * 得到服务器当前时间的毫秒值
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("getSystemTime")
    @ResponseBody
    public String getSystemTime()
            throws Exception {
        Long l = System.currentTimeMillis();
        return l.toString();
    }

    /**
     * Description：获取 所有问题
     *
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "getProblems")
    @ResponseBody
    public ResponseObject getProblems() throws Exception {
        return ResponseObject.newSuccessResponseObject(commonApiService.getProblems("common_problems"));
    }

    /**
     * Description：获取单个问题和答案
     *
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "getProblemAnswer")
    @ResponseBody
    public ResponseObject getProblemAnswer(@RequestParam("id") String id) throws Exception {
        return ResponseObject.newSuccessResponseObject(commonApiService.getProblemAnswer(id));
    }
}
