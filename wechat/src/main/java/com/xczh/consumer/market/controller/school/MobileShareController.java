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
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ConfigUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseVo;
import com.xczh.consumer.market.vo.LecturVo;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczhihui.common.util.enums.ShareType;
import com.xczhihui.common.util.enums.TokenExpires;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.util.XzStringUtils;
import com.xczhihui.course.vo.CourseLecturVo;
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

    @Autowired
    private OnlineCourseService onlineCourseService;
    @Value("${returnOpenidUri}")
    private String returnOpenidUri;
    @Autowired
    private OnlineUserMapper onlineUserMapper;
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private ICourseService courseServiceImpl;
    @Autowired
    private OnlineWebService onlineWebService;
    @Autowired
    private OnlineUserService onlineUserService;


    @Value("${webdomain}")
    private String webdomain;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileShareController.class);

    /**
     *	app---> 课程、主播分享
     */
    @RequestMapping("courseShare")
    @ResponseBody
    public ResponseObject courseShare(
            @RequestParam(value="shareId")String shareId,
            @RequestParam(value="shareType")Integer shareType)
            throws Exception{
        try {
            if(ShareType.COURSE_SHARE.getCode() == shareType ||
                    ShareType.ALBUM_SHARE.getCode()== shareType){ // 课程分享

                CourseVo courseLectur = onlineCourseService.courseShare(Integer.parseInt(shareId));
                if(courseLectur==null){
                    return ResponseObject.newErrorResponseObject("课程信息有误");
                }
                courseLectur.setGradeName("中医好课程:"+courseLectur.getGradeName());
                if(courseLectur.getDescription()!=null){
                    String description = courseLectur.getDescription();
                    description = XzStringUtils.delHTMLTag(description);
                    courseLectur.setDescription(description);
                }
                courseLectur.setLink(returnOpenidUri+"/wx_share.html?shareType="+shareType+"&shareId="+Integer.parseInt(shareId));
                return ResponseObject.newSuccessResponseObject(courseLectur);
            }else {			 //  主播分享
                LecturVo lectur = onlineCourseService.lectureShare(shareId);
                if(lectur==null){
                    return ResponseObject.newErrorResponseObject("主播信息有误");
                }
				/*
				 * 课程名增加一个中医好课程
				 */
                lectur.setName("中医好主播:"+lectur.getName());
                if(lectur.getDescription()!=null){
                    String description = lectur.getDescription();
                    description = XzStringUtils.delHTMLTag(description);
                    lectur.setDescription(description);
                }
                lectur.setLink(returnOpenidUri+"/wx_share.html?shareType=2&shareId="+shareId);
                return ResponseObject.newSuccessResponseObject(lectur);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("请求有误");
        }
    }

    /**
     * Description：用户点击my_share.html页面后，进入到这个方法，
     *   根据用户点击的不同浏览器来进行重定向。
     *   根据课程和主播是否有效判断是否跳转到其他页面啦
     * @param req
     * @param res
     * @param params
     * @return
     * @throws Exception
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("shareBrowserDifferentiation")
    public void shareBrowserDifferentiation(HttpServletRequest req,
            HttpServletResponse res,
            @RequestParam(value="shareId")String shareId,
            @RequestParam(value="shareType")Integer shareType,
            @RequestParam(value="wxOrbrower")String wxOrbrower, 
            @Account(optional = true) Optional<String> accountIdOpt)throws Exception{
        /**
         * 这里有个问题就是。如果去分享页面的话
         */
        LOGGER.info("shareId:"+shareId+",shareType:"+shareType);
        String shareIdAndType = shareId+"_"+shareType;

        /**
         * 判断课程或者主播是否存在
         */
        if(ShareType.COURSE_SHARE.equals(shareType) ||
                ShareType.ALBUM_SHARE.equals(shareType)){ //课程分享
            Integer courseId = Integer.parseInt(shareId);

            CourseLecturVo  courseLecturVo =
                    courseServiceImpl.selectCourseStatusDeleteUserLecturerId(courseId);

            LOGGER.info("status():"+courseLecturVo.getStatus()+
                    ",isDelete():"+courseLecturVo.getIsDelete()+
                    ",userLecturerId():"+courseLecturVo.getUserLecturerId());

            if(courseLecturVo.getStatus() == 0 && !accountIdOpt.isPresent()) { //课程下架了
                LOGGER.info("课程被下架,用户为登录");
                res.sendRedirect(returnOpenidUri +"/xcview/html/unshelve.html");
                return;
            }else if(courseLecturVo.getStatus() == 0 &&  accountIdOpt.isPresent()) { //课程虽然被下架。但判断此用户是否购买过啊
                Boolean falg = onlineWebService.getLiveUserCourse(courseId, accountIdOpt.get());
                if(!falg) {
                    LOGGER.info("课程虽然被下架。用户也没有购买");
                    res.sendRedirect(returnOpenidUri +"/xcview/html/unshelve.html");
                    return;
                }
            }else if(courseLecturVo.getIsDelete()) { //课程被删除
                LOGGER.info("课程被物理删除");
                res.sendRedirect(returnOpenidUri +"/xcview/html/unshelve.html");
                return;

            }else if(StringUtils.isBlank(courseLecturVo.getUserLecturerId())) {
                LOGGER.info("课程的教师没有找到");
                res.sendRedirect(returnOpenidUri +"/xcview/html/unshelve.html");
                return;
            }
        }else if(ShareType.HOST_SHARE.equals(shareType)){ //主播分享
            OnlineUser o = onlineUserService.findUserById(shareId);
            if (o != null) {
                if (o.isDelete() || o.getStatus() == -1) {
                	res.sendRedirect(returnOpenidUri +"/xcview/html/unshelve.html");
                	return;
                }
            }
        }else { //分享类型有误 --》首页
        	res.sendRedirect(returnOpenidUri +"/xcview/html/unshelve.html");
        	return;
        }
		/*
		 * 这里需要判断下是不是微信浏览器
		 */
        if(StringUtils.isNotBlank(wxOrbrower) && "wx".equals(wxOrbrower)){
            String strLinkHome 	= "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WxPayConst.gzh_appid+"&redirect_uri="+returnOpenidUri+"/xczh/share/viewUser?shareIdAndType="+shareIdAndType+"&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect".replace("appid=APPID", "appid="+ WxPayConst.gzh_appid);
            res.sendRedirect(strLinkHome);
        }else if(StringUtils.isNotBlank(wxOrbrower) && "brower".equals(wxOrbrower)){
            res.sendRedirect(returnOpenidUri +"/xczh/share/viewUser?shareId="+shareId+"&wxOrbrower=brower"+"&shareType="+shareType);//
        }
    }

    /**
     *
     * Description：真正的分享后，响应给用户的页面
     * @param req
     * @param res
     * @throws Exception
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
    @RequestMapping("viewUser")
    public void viewUser(HttpServletRequest req, HttpServletResponse res, @Account(optional = true) Optional<String> accountIdOpt) throws Exception{

        LOGGER.info("WX return code:" + req.getParameter("code"));

        try {
            String code = req.getParameter("code");
            String shareId = req.getParameter("shareId");
            String shareType = req.getParameter("shareType");
            String wxOrbrower = req.getParameter("wxOrbrower");

            String shareIdAndType = req.getParameter("shareIdAndType");

            if(StringUtils.isNotBlank(shareIdAndType)){
                String [] idAndType =shareIdAndType.split("_");
                shareId = idAndType[0];
                shareType = idAndType[1];
            }
            LOGGER.info("shareId:" +shareId+"shareType:" +shareType+"wxOrbrower:" +wxOrbrower);
            OnlineUser ou =null;
            if(!StringUtils.isNotBlank(wxOrbrower)){ //微信浏览器
                WxcpClientUserWxMapping wxw = onlineUserService.saveWxInfo(code);
				/*
				 * 判断此微信用户是否已经存在与我们的账户系统中不
				 */
                if(StringUtils.isNotBlank(wxw.getClient_id())){ //说明这个用户已经绑定过了
                    /**
                     * 判断这个用户session是否有效了，如果有效就不用登录了
                     */
                    ou = onlineUserMapper.findUserById(wxw.getClient_id());

                    if(!accountIdOpt.isPresent()){ //直接跳转到分享页面
                        Token t = userCenterService.loginThirdPart(ou.getLoginName(),TokenExpires.TenDay);
                        ou.setTicket(t.getTicket());
                        UCCookieUtil.writeTokenCookie(res, t);
                    }
                }else {
                	 UCCookieUtil.writeThirdPartyCookie(res,onlineUserService.buildThirdFlag(wxw));
                }
            } else {
                ou = accountIdOpt.isPresent() ? onlineUserService.findUserById(accountIdOpt.get()) : null;
            }

            /**
             * 如果这个用户信息已经保存进去了，那么就直接登录就ok
             */
            ConfigUtil cfg = new ConfigUtil(req.getSession());
            String returnOpenidUri = cfg.getConfig("returnOpenidUri");
            if("1".equals(shareType) || "3".equals(shareType)){ //课程分享啦

                LOGGER.info("shareType:"+shareType);
                LOGGER.info("shareId:+"+shareId);

                Integer courseId = Integer.parseInt(shareId);
                com.xczhihui.course.vo.CourseLecturVo  cv=null;

                //判断这个课程类型啦
                if(ou!=null){  //说明已经登录了
                    cv= courseServiceImpl.selectUserCurrentCourseStatus(courseId,ou.getId());
                }else{
                    cv= courseServiceImpl.selectCurrentCourseStatus(courseId);
                }
                //如果课程id没有找到，就去首页
                if(cv==null){
                    res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html");
                    return;
                }
                LOGGER.info("cv.getWatchState():+"+cv.getWatchState()+",cv.getType()=:+"+cv.getType()+",cv.getCollection()=:+"+cv.getCollection());

                if(ou == null) {
                    if(cv.getType()==1 || cv.getType()==2){
                        //视频音频购买
                        res.sendRedirect(returnOpenidUri + "/xcview/html/school_audio.html?shareBack=1&course_id="+shareId);
                    }else if(cv.getType()==3){
                        //直播购买
                        res.sendRedirect(returnOpenidUri + "/xcview/html/school_play.html?shareBack=1&course_id="+shareId);
                    }else{
                        //线下课购买
                        res.sendRedirect(returnOpenidUri + "/xcview/html/school_class.html?shareBack=1&course_id="+shareId);
                    }
                }else {
                    if(cv.getWatchState() == 0){
                        if(cv.getType()==1 || cv.getType()==2){
                            //视频音频购买
                            res.sendRedirect(returnOpenidUri + "/xcview/html/school_audio.html?shareBack=1&course_id="+shareId);
                        }else if(cv.getType()==3){
                            //直播购买
                            res.sendRedirect(returnOpenidUri + "/xcview/html/school_play.html?shareBack=1&course_id="+shareId);
                        }else{
                            //线下课购买
                            res.sendRedirect(returnOpenidUri + "/xcview/html/school_class.html?shareBack=1&course_id="+shareId);
                        }
                    }else if(cv.getWatchState()==1 || cv.getWatchState() == 2){
                        if(cv.getType()==1||cv.getType()==2){
                            if(cv.getCollection()){
                                //专辑视频音频播放页
                                res.sendRedirect(returnOpenidUri + "/xcview/html/live_select_album.html?shareBack=1&course_id="+shareId);
                            }else{
                                if("3".equals(shareType)) { //说明是单个专辑  -->跳转到总专辑那个地方
                                    res.sendRedirect(returnOpenidUri + "/xcview/html/school_audio.html?shareBack=1&course_id="+shareId);
                                }else {
                                    //单个视频音频播放
                                    res.sendRedirect(returnOpenidUri + "/xcview/html/live_audio.html?shareBack=1&my_study="+shareId);
                                }
                            }
                        }else if(cv.getType()==3){
                            //播放页面
                            res.sendRedirect(returnOpenidUri + "/xcview/html/live_play.html?shareBack=1&my_study="+shareId);
                        }else{
                            //线下课页面
                            res.sendRedirect(returnOpenidUri + "/xcview/html/live_class.html?shareBack=1&my_study="+shareId);
                        }
                    }
                }
            }else if("2".equals(shareType)){ //主播分享  -->设置下cookie
                res.sendRedirect(returnOpenidUri + "/xcview/html/live_personal.html?shareBack=1&userLecturerId="+shareId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过连接我们的二维码来获取用户信息啦，
     * @param req
     * @param res
     * @throws Exception
     */
    @RequestMapping("xcCustomQrCode")
    public void xcCustomQrCode(HttpServletRequest req,
                               HttpServletResponse res)throws Exception{

        /**
         * 这里有个问题就是。如果去分享页面的话
         */
        String searchUrl = req.getParameter("search_url");  //来自哪里的浏览器
        String wxOrbrower = req.getParameter("wxOrbrower");  //来自哪里的浏览器
        String realUrl = URLEncoder.encode(searchUrl, "UTF-8");

        LOGGER.info("啦啦啦，我是卖报的小画家：searchUrl:"+searchUrl+"==="+",wxOrbrower:"+wxOrbrower);
		/*
		 * 这里需要判断下是不是微信浏览器
		 */
        if(StringUtils.isNotBlank(wxOrbrower) && "wx".equals(wxOrbrower)){
            String strLinkHome 	= "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WxPayConst.gzh_appid+"&redirect_uri="+returnOpenidUri+"/xczh/share/xcCustomQrCodeViewUser?realUrl="+realUrl+"&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect".replace("appid=APPID", "appid="+ WxPayConst.gzh_appid);
            res.sendRedirect(strLinkHome);
        }else if(StringUtils.isNotBlank(wxOrbrower) && "brower".equals(wxOrbrower)){
            res.sendRedirect(returnOpenidUri +"/xczh/share/xcCustomQrCodeViewUser?realUrl="+realUrl+"&wxOrbrower=brower");//
        }
    }

    /**
     *
     * Description：真正的分享后，响应给用户的页面
     * @param req
     * @param res
     * @throws Exception
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
    @RequestMapping("xcCustomQrCodeViewUser")
    public void xcCustomQrCodeViewUser(HttpServletRequest req, HttpServletResponse res, @Account(optional = true) Optional<String> accountIdOpt) throws Exception{

        LOGGER.info("WX return code:" + req.getParameter("code"));
        try {
            String code = req.getParameter("code");
            String realUrl = req.getParameter("realUrl");
            String wxOrbrower = req.getParameter("wxOrbrower");

            LOGGER.info("code:" +code+"realUrl:" +realUrl+"wxOrbrower:" +wxOrbrower);
            OnlineUser ou =null;
            if(!StringUtils.isNotBlank(wxOrbrower)){ //微信浏览器
                WxcpClientUserWxMapping wxw = onlineUserService.saveWxInfo(code);
				/*
				 * 判断此微信用户是否已经存在与我们的账户系统中不
				 */
                if(StringUtils.isNotBlank(wxw.getClient_id())){ //说明这个用户已经绑定过了
                    /**
                     * 判断这个用户session是否有效了，如果有效就不用登录了
                     */
                    ou = onlineUserMapper.findUserById(wxw.getClient_id());
                    if(!accountIdOpt.isPresent()){ //直接跳转到分享页面
                        //这里不用判断用户有没有登录了。没哟登录帮他登录
                        Token t = userCenterService.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);
                        ou.setTicket(t.getTicket());
                        UCCookieUtil.writeTokenCookie(res, t);
                    }
                }
                UCCookieUtil.writeThirdPartyCookie(res,onlineUserService.buildThirdFlag(wxw));
            }
            res.sendRedirect(realUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}