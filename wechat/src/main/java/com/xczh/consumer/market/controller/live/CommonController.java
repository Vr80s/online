package com.xczh.consumer.market.controller.live;

import java.security.MessageDigest;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.WeihouInterfacesListUtil;

/**
 * 通用控制器
 * ClassName: CommonController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月12日<br>
 */
@Controller
@RequestMapping("/bxg/common")
public class CommonController {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    /**
     * Description：用户主页    -- 讲师基本信息、是否已经关注了这个主播、关注该主播的粉丝数、主播关注的主播数、关注该主播的前六个人的头像
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("userHomePage")
    @ResponseBody
    public ResponseObject userHomePage(HttpServletRequest req,
                                       HttpServletResponse res) throws Exception {
        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * Description：用户主页    -- 课程列表
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("userHomePageCourseList")
    @ResponseBody
    public ResponseObject userHomePageCourseList(HttpServletRequest req,
                                                 HttpServletResponse res, Map<String, String> params) throws Exception {
        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * 判断这个用户是不是讲师
     * Description：
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("judgeUserIsTeacher")
    @ResponseBody
    public ResponseObject judgeUserIsTeacher(HttpServletRequest req,
                                             HttpServletResponse res, Map<String, String> params) throws Exception {

        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }


    /**
     * 判断该课程是否已经购买，付费认证
     * Description：
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("courseIsBuy")
    @ResponseBody
    public ResponseObject courseIsBuy(HttpServletRequest req,
                                      HttpServletResponse res) throws Exception {

        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * 判断当前用户针对这个课程是否还需要密码确认
     * Description：
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("courseIsPwd")
    @ResponseBody
    public ResponseObject courseIsConfirmPwd(HttpServletRequest req,
                                             HttpServletResponse res) throws Exception {

        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * Description：
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("courseStatus")
    @ResponseBody
    public ResponseObject courseStatus(HttpServletRequest req,
                                       HttpServletResponse res) throws Exception {

        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }


    /**
     * 用户输入密码后比较密码是否一致
     * Description：
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("coursePwdConfirm")
    @ResponseBody
    public ResponseObject coursePwdConfirm(HttpServletRequest req,
                                           HttpServletResponse res) throws Exception {
        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * 预约接口
     * Description：
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：liutao
     */
    @RequestMapping("subscribe")
    @ResponseBody
    public ResponseObject subscribe(HttpServletRequest req,
                                    HttpServletResponse res) throws Exception {

        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * 判断这个用户是否已经预约
     * Description：
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("userIsSubscribe")
    @ResponseBody
    public ResponseObject userIsSubscribe(HttpServletRequest req,
                                          HttpServletResponse res) throws Exception {


        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * Description：微吼签名认证得到微吼的视频播放权
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("getWeihouSign")
    @ResponseBody
    public ResponseObject getWeihouSign(@Account OnlineUser user, HttpServletRequest req,
                                        HttpServletResponse res) throws Exception {
        String roomNumber = req.getParameter("video");  //视频id
        String gvhallId = user.getVhallId();
        LOGGER.info("微吼gvhallId:" + gvhallId);
        String email = user.getLoginName();
        if (email != null && email.indexOf("@") == -1) {
            email += "@163.com";
        }
        Date d = new Date();
        String start_time = d.getTime() + "";
        start_time = start_time.substring(0, start_time.length() - 3);

        Map<String, String> map = new TreeMap<String, String>();
        map.put("app_key", WeihouInterfacesListUtil.APP_KEY);  //微吼key
        map.put("signedat", start_time); //时间戳，精确到秒
        map.put("email", email);         //email 自己写的
        map.put("roomid", roomNumber);   //视频id
        map.put("account", user.getId());       //用户帐号
        map.put("username", user.getName());      //用户名
        map.put("sign", getSign(map));
        return ResponseObject.newSuccessResponseObject(map);
    }

    @RequestMapping("h5ShareAfter")
    @ResponseBody
    public ResponseObject h5ShareLink(HttpServletRequest req,
                                      HttpServletResponse res) throws Exception {

        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    @RequestMapping("shareLink")
    @ResponseBody
    public ResponseObject shareLink(HttpServletRequest req,
                                    HttpServletResponse res) throws Exception {

        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * Description：pc端分享
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("pcShareLink")
    public void pcShareLink(HttpServletRequest req, HttpServletResponse res) throws Exception {
        res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html");//
    }

    /**
     * Description：根据课程id得到判断此课程是：直播呢，还是点播呢，还是预约呢？
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("shareJump")
    @ResponseBody
    public ResponseObject shareJump(HttpServletRequest req,
                                    HttpServletResponse res) throws Exception {
        String courseId = req.getParameter("courseId");  //视频id
        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    @RequestMapping("getDomain")
    @ResponseBody
    public ResponseObject getDomain(HttpServletRequest req,
                                    HttpServletResponse res) throws Exception {
        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }


    @RequestMapping("getSystemTime")
    @ResponseBody
    public String getSystemTime(HttpServletRequest req,
                                HttpServletResponse res) throws Exception {
        Long l = System.currentTimeMillis();
        return l.toString();
    }

    public static String getSign(Map<String, String> signkv) {
        Set<String> keySet = signkv.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(WeihouInterfacesListUtil.APP_SECRET_KEY);
        while (iter.hasNext()) {
            String key = iter.next();
            sb.append(key + signkv.get(key));
        }
        sb.append(WeihouInterfacesListUtil.APP_SECRET_KEY);
        return getMD5(sb.toString());
    }

    /**
     * 生成md5
     *
     * @param message
     * @return
     */
    public static String getMD5(String message) {
        String md5str = "";
        try {
            // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 2 将消息变成byte数组
            byte[] input = message.getBytes();
            // 3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);
            // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = bytesToHex(buff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str.toLowerCase();
    }

    /**
     * 二进制转十六进制
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }
}
