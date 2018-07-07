package com.xczh.consumer.market.controller.course;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczhihui.common.util.enums.WechatShareLinkType;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.vo.CourseLecturVo;

/**
 * 通过课程id 重定向到前端指定的课程页面
 * @author yangxuan
 *
 */
@Controller
@RequestMapping("/course")
public class PageController {

    @Autowired
    private ICourseService courseServiceImpl;
    @Value("${returnOpenidUri}")
    private String returnOpenidUri;
    @Autowired
    private OnlineUserService onlineUserService;

    /**
     * 课程跳转重定向
     * @param id
     * @param accountIdOpt
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String jump(@PathVariable Integer id,
            @Account(optional = true) Optional<String> accountIdOpt) throws SQLException {
        
        OnlineUser ou = null;
        if(accountIdOpt.isPresent()) {
            ou =  onlineUserService.findUserById(accountIdOpt.get());
        }
        String url = coursePage(id,ou);
        if(url!=null) {
            url = url.replaceAll("shareBack=1&", "");
        }
        return "redirect:" + url;
    }
    
    
    public String coursePage(Integer courseId, OnlineUser ou) {
        String coursePage = WechatShareLinkType.INDEX_PAGE.getLink();
        CourseLecturVo cv = null;
        if (ou != null) {  
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
        return coursePage + courseId;
    }
}
