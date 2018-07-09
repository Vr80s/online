package com.xczh.consumer.market.controller.school;

import java.net.URLEncoder;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.dao.OnlineUserMapper;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczhihui.common.util.enums.ShareType;
import com.xczhihui.common.util.enums.TokenExpires;
import com.xczhihui.common.util.enums.WechatShareLinkType;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.ICriticizeService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.ShareInfoVo;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.service.IMedicalDoctorArticleService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.vo.MobileArticleVO;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.utils.UCCookieUtil;
import com.xczhihui.user.center.vo.Token;

/**
 * 热门搜索控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>默认关键字与热门搜索
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/share")
public class MobileShareController {


    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileShareController.class);
    @Autowired
    private OnlineUserMapper onlineUserMapper;
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private ICourseService courseServiceImpl;
    @Autowired
    private ICriticizeService criticizeService;
    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private IMedicalDoctorArticleService medicalDoctorArticleService;
    @Value("${webdomain}")
    private String webdomain;
    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    /**
     * app---> 课程、主播分享
     */
    @RequestMapping("courseShare")
    @ResponseBody
    public ResponseObject courseShare(
            @RequestParam(value = "shareId") String shareId,
            @RequestParam(value = "shareType") Integer shareType)
            throws Exception {

        try {
            ShareInfoVo sv = courseServiceImpl.selectShareInfoByType(shareType, shareId);
            //构造下分享出去的参数
            sv.build(returnOpenidUri,webdomain);
            return ResponseObject.newSuccessResponseObject(sv);
        } catch (Exception e) {
            e.printStackTrace();

            //如果异常默认用这个
            ShareInfoVo sv = new ShareInfoVo();
            sv.setName("熊猫中医");
            sv.setDescription("熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。");
            sv.setHeadImg(webdomain + "/web/images/defaultHead/18.png");
            sv.setLink(returnOpenidUri + WechatShareLinkType.INDEX_PAGE.getLink());
            return ResponseObject.newSuccessResponseObject(sv);
        }
    }

    /**
     * Description：用户点击my_share.html页面后，进入到这个方法，
     * 根据用户点击的不同浏览器来进行重定向。
     * 根据课程和主播是否有效判断是否跳转到其他页面啦
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("shareBrowserDifferentiation")
    public void shareBrowserDifferentiation(HttpServletRequest req,HttpServletResponse res,
            @RequestParam(value = "shareId") String shareId,
            @RequestParam(value = "shareType") Integer shareType,
            @RequestParam(value = "wxOrbrower") String wxOrbrower,
            @Account(optional = true) Optional<String> accountIdOpt) throws Exception {

        try {
            /**
             * 这里有个问题就是。如果去分享页面的话
             */
            LOGGER.info("shareId:" + shareId + ",shareType:" + shareType);
            String shareIdAndType = shareId + "_" + shareType;
            //课程分享
            if (ShareType.COURSE_SHARE.getCode().equals(shareType)
                    || ShareType.ALBUM_SHARE.getCode().equals(shareType)) {

                Integer courseId = Integer.parseInt(shareId);
                CourseLecturVo courseLecturVo =
                        courseServiceImpl.selectCourseStatusDeleteUserLecturerId(courseId);
                if (courseLecturVo == null) {
                    res.sendRedirect(returnOpenidUri + WechatShareLinkType.UNSHELVE.getLink());
                    return;
                }
                /**
                 *  判断课程是否下架  和  用户是否登录
                 */
                boolean status = (courseLecturVo.getStatus() == 0 && !accountIdOpt.isPresent());
                boolean iSdelete = courseLecturVo.getIsDelete();
                boolean exitUserId = StringUtils.isBlank(courseLecturVo.getUserLecturerId());

                //课程下架了 或者被删除 或者 主播不登录空
                if (status || iSdelete || exitUserId) {
                    res.sendRedirect(returnOpenidUri + WechatShareLinkType.UNSHELVE.getLink());
                    return;

                    //课程虽然被下架。但判断此用户是否购买过啊
                } else if (courseLecturVo.getStatus() == 0 && accountIdOpt.isPresent()) {
                    Integer falg = criticizeService.hasCourse(accountIdOpt.get(), courseId);
                    if (falg > 0) {
                        res.sendRedirect(returnOpenidUri + WechatShareLinkType.UNSHELVE.getLink());
                        return;
                    }
                }

                //主播分享
            } else if (ShareType.HOST_SHARE.getCode().equals(shareType)) {

                OnlineUser o = onlineUserService.findUserById(shareId);
                if (o == null) {
                    res.sendRedirect(returnOpenidUri + WechatShareLinkType.UNSHELVE.getLink());
                    return;
                }

                //没有找到，并且删除或者 禁用
            } else if (ShareType.DOCDOT_SHARE.getCode().equals(shareType)) {
                MedicalDoctor md = medicalDoctorBusinessService.get(shareId);
                if (md == null || md.getDeleted() || !md.getStatus()) {
                    res.sendRedirect(returnOpenidUri + WechatShareLinkType.UNSHELVE.getLink());
                    return;
                }
            } else if (ShareType.ACTICLE_SHARE.getCode().equals(shareType)) {
                MobileArticleVO mobileArticleVO = medicalDoctorArticleService.get(Integer.parseInt(shareId));
                if (mobileArticleVO == null) {
                    res.sendRedirect(returnOpenidUri + WechatShareLinkType.UNSHELVE.getLink());
                    return;
                }

            } else if (ShareType.MEDICAL_CASES.getCode().equals(shareType)) {
                MobileArticleVO mobileArticleVO = medicalDoctorArticleService.get(Integer.parseInt(shareId));
                if (mobileArticleVO == null) {
                    res.sendRedirect(returnOpenidUri + WechatShareLinkType.UNSHELVE.getLink());
                    return;
                }
            }

            if (StringUtils.isNotBlank(wxOrbrower) && "wx".equals(wxOrbrower)) {
                String strLinkHome = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxPayConst.gzh_appid + "&redirect_uri=" + returnOpenidUri + "/xczh/share/viewUser?shareIdAndType=" + shareIdAndType + "&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect".replace("appid=APPID", "appid=" + WxPayConst.gzh_appid);
                res.sendRedirect(strLinkHome);
            } else if (StringUtils.isNotBlank(wxOrbrower) && "brower".equals(wxOrbrower)) {
                res.sendRedirect(returnOpenidUri + "/xczh/share/viewUser?shareIdAndType=" + shareIdAndType + "&wxOrbrower=brower");//
            }
        } catch (Exception e) {
            e.printStackTrace();

            res.sendRedirect(returnOpenidUri + WechatShareLinkType.INDEX_PAGE.getLink());
        }
    }

    /**
     * Description：真正的分享后，响应给用户的页面
     *
     * @param req
     * @param res
     * @return void
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("viewUser")
    public void viewUser(HttpServletRequest req, HttpServletResponse res,
                         @Account(optional = true) Optional<String> accountIdOpt) throws Exception {

        LOGGER.info("WX return code:" + req.getParameter("code"));
        try {
            //获取分享页面转发过来的参数
            String wxOrbrower = req.getParameter("wxOrbrower");
            String shareIdAndType = req.getParameter("shareIdAndType");
            String code = req.getParameter("code");

            String shareId = null;
            Integer shareType = 0;
            if (StringUtils.isNotBlank(shareIdAndType)) {
                String[] idAndType = shareIdAndType.split("_");
                shareId = idAndType[0];
                shareType = Integer.parseInt(idAndType[1]);
            }
            /*
             * 判断微信环境还是普通浏览器环境
             * 判断用户是否登录过，是否还有效
             */
            OnlineUser ou = null;
            if ("wx".equals(wxOrbrower)) {
                WxcpClientUserWxMapping wxw = onlineUserService.saveWxInfo(code);
                /**
                 * 判断此微信用户是否  存在或者绑定过手机号没
                 */
                //绑定过
                if (wxw != null && StringUtils.isNotBlank(wxw.getClient_id())) {
                    //获取用户信息
                    ou = onlineUserMapper.findUserById(wxw.getClient_id());
                    if (!accountIdOpt.isPresent()) {
                        Token t = userCenterService.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);
                        ou.setTicket(t.getTicket());
                        UCCookieUtil.writeTokenCookie(res, t);
                    }
                } else {
                    //清理cookie，可能应该之前用户惨存的
                    UCCookieUtil.clearTokenCookie(res);
                }
                UCCookieUtil.writeThirdPartyCookie(res, onlineUserService.buildThirdFlag(wxw));
            } else {
                ou = accountIdOpt.isPresent() ? onlineUserService.findUserById(accountIdOpt.get()) : null;
            }

            //课程分享啦
            if (ShareType.COURSE_SHARE.getCode().equals(shareType)
                    || ShareType.ALBUM_SHARE.getCode().equals(shareType)) {

                String coursepage = shareCoursePage(shareId, shareType, ou);
                res.sendRedirect(coursepage);

                //主播分享
            } else if (ShareType.HOST_SHARE.getCode().equals(shareType)) {
                res.sendRedirect(returnOpenidUri + WechatShareLinkType.LIVE_PERSONAL.getLink() + shareId);

                //师承分享
            } else if (ShareType.APPRENTICE_SHARE.getCode().equals(shareType)) {
                res.sendRedirect(returnOpenidUri + WechatShareLinkType.APPRENTICE.getLink() + shareId);

                //医师分享
            } else if (ShareType.DOCDOT_SHARE.getCode().equals(shareType)) {

                res.sendRedirect(returnOpenidUri + WechatShareLinkType.DOCDOT_SHARE.getLink() + shareId);

                //文章分享
            } else if (ShareType.ACTICLE_SHARE.getCode().equals(shareType)) {

                res.sendRedirect(returnOpenidUri + WechatShareLinkType.ACTICLE_SHARE.getLink()+ shareId);

                //医案分享
            } else if (ShareType.MEDICAL_CASES.getCode().equals(shareType)) {

                res.sendRedirect(returnOpenidUri + WechatShareLinkType.MEDICAL_CASES.getLink()+ shareId);
            }
        } catch (Exception e) {
            e.printStackTrace();

            res.sendRedirect(returnOpenidUri + WechatShareLinkType.INDEX_PAGE.getLink());
        }
    }

    /**
     * 通过连接我们的二维码来获取用户信息啦，
     *
     * @param req
     * @param res
     * @throws Exception
     */
    @RequestMapping("xcCustomQrCode")
    public void xcCustomQrCode(HttpServletRequest req,
                               HttpServletResponse res) throws Exception {

        /**
         * 这里有个问题就是。如果去分享页面的话
         */
        String searchUrl = req.getParameter("search_url");  //来自哪里的浏览器
        String wxOrbrower = req.getParameter("wxOrbrower");  //来自哪里的浏览器
        String realUrl = URLEncoder.encode(searchUrl, "UTF-8");

        LOGGER.info("啦啦啦，我是卖报的小画家：searchUrl:" + searchUrl + "===" + ",wxOrbrower:" + wxOrbrower);
        /*
         * 这里需要判断下是不是微信浏览器
		 */
        if (StringUtils.isNotBlank(wxOrbrower) && "wx".equals(wxOrbrower)) {
            String strLinkHome = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxPayConst.gzh_appid + "&redirect_uri=" + returnOpenidUri + "/xczh/share/xcCustomQrCodeViewUser?realUrl=" + realUrl + "&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect".replace("appid=APPID", "appid=" + WxPayConst.gzh_appid);
            res.sendRedirect(strLinkHome);
        } else if (StringUtils.isNotBlank(wxOrbrower) && "brower".equals(wxOrbrower)) {
            res.sendRedirect(returnOpenidUri + "/xczh/share/xcCustomQrCodeViewUser?realUrl=" + realUrl + "&wxOrbrower=brower");//
        }
    }

    /**
     * Description：真正的分享后，响应给用户的页面
     *
     * @param req
     * @param res
     * @return void
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("xcCustomQrCodeViewUser")
    public void xcCustomQrCodeViewUser(HttpServletRequest req, HttpServletResponse res, @Account(optional = true) Optional<String> accountIdOpt) throws Exception {

        LOGGER.info("WX return code:" + req.getParameter("code"));
        try {
            String code = req.getParameter("code");
            String realUrl = req.getParameter("realUrl");
            String wxOrbrower = req.getParameter("wxOrbrower");

            LOGGER.info("code:" + code + "realUrl:" + realUrl + "wxOrbrower:" + wxOrbrower);
            OnlineUser ou = null;
            if (!StringUtils.isNotBlank(wxOrbrower)) { //微信浏览器
                WxcpClientUserWxMapping wxw = onlineUserService.saveWxInfo(code);
				/*
				 * 判断此微信用户是否已经存在与我们的账户系统中不
				 */
                if (StringUtils.isNotBlank(wxw.getClient_id())) { //说明这个用户已经绑定过了
                    /**
                     * 判断这个用户session是否有效了，如果有效就不用登录了
                     */
                    ou = onlineUserMapper.findUserById(wxw.getClient_id());
                    if (!accountIdOpt.isPresent()) { //直接跳转到分享页面
                        //这里不用判断用户有没有登录了。没哟登录帮他登录
                        Token t = userCenterService.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);
                        ou.setTicket(t.getTicket());
                        UCCookieUtil.writeTokenCookie(res, t);
                    }
                }else {
                    //清理cookie，可能应该之前用户惨存的
                    UCCookieUtil.clearTokenCookie(res);
                }
                UCCookieUtil.writeThirdPartyCookie(res, onlineUserService.buildThirdFlag(wxw));
            }
            res.sendRedirect(realUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String shareCoursePage(String shareId, Integer shareType, OnlineUser ou) {

        String coursePage = WechatShareLinkType.INDEX_PAGE.getLink();

        Integer courseId = Integer.parseInt(shareId);
        CourseLecturVo cv = null;
        //判断这个课程类型啦
        if (ou != null) {  //说明已经登录了
            cv = courseServiceImpl.selectUserCurrentCourseStatus(courseId, ou.getId());
        } else {
            cv = courseServiceImpl.selectCurrentCourseStatus(courseId);
        }

        if (cv == null) {
            return coursePage;
        }
        
        //用户未登录去展示页
        if (ou == null) {
            if (cv.getType().equals(1) || cv.getType().equals(2)) {
                //视频音频购买
                coursePage = WechatShareLinkType.SCHOOL_AUDIO.getLink();
            } else if (cv.getType().equals(3)) {
                //直播购买
                coursePage = WechatShareLinkType.SCHOOL_PLAY.getLink();
            } else {
                //线下课购买
                coursePage = WechatShareLinkType.SCHOOL_CLASS.getLink();
            }
            
        //用户登录 判断课程收费情况，或者是否购买    
        } else {
            if (cv.getWatchState().equals(0)) {
                if (cv.getType().equals(1) || cv.getType().equals(2)) {
                    //视频音频购买
                    coursePage = WechatShareLinkType.SCHOOL_AUDIO.getLink();
                } else if (cv.getType().equals(3)) {
                    //直播购买
                    coursePage = WechatShareLinkType.SCHOOL_PLAY.getLink();
                } else {
                    //线下课购买
                    coursePage = WechatShareLinkType.SCHOOL_CLASS.getLink();
                }
            } else if (cv.getWatchState().equals(1) || cv.getWatchState().equals(2)) {
                
                if (cv.getType().equals(1) || cv.getType().equals(2)) {
                    if (cv.getCollection()) {
                        //专辑视频音频播放页
                        coursePage = WechatShareLinkType.LIVE_SELECT_ALBUM.getLink();
                    } else {
                        coursePage = WechatShareLinkType.LIVE_AUDIO.getLink();
                    }
                } else if (cv.getType().equals(3)) {
                    //播放页面
                    coursePage = WechatShareLinkType.LIVE_PLAY.getLink();
                } else {
                    //线下课页面
                    coursePage = WechatShareLinkType.LIVE_CLASS.getLink();
                }
            }
        }
        return returnOpenidUri + coursePage + shareId;
    }
}