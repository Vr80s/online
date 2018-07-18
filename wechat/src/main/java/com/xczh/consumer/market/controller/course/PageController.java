package com.xczh.consumer.market.controller.course;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.RecordType;
import com.xczhihui.common.util.enums.WechatShareLinkType;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.course.service.IWatchHistoryService;
import com.xczhihui.course.vo.CourseLecturVo;

/**
 * 通过课程id 重定向到前端指定的课程页面
 * @author yangxuan
 *
 */
@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    private ICourseService courseServiceImpl;
    @Value("${returnOpenidUri}")
    private String returnOpenidUri;
    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private IMyInfoService myInfoService;
    @Autowired
    public IWatchHistoryService watchHistoryService;
    
    /**
     * 课程跳转重定向
     * @param id
     * @param accountIdOpt
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "course/{id}", method = RequestMethod.GET)
    public String jump(@PathVariable Integer id,
            @Account(optional = true) Optional<String> accountIdOpt) throws SQLException {
        
        String url = WechatShareLinkType.INDEX_PAGE.getLink();
        try {
            OnlineUser ou = null;
            if(accountIdOpt.isPresent()) {
                ou =  onlineUserService.findUserById(accountIdOpt.get());
            }
            url = coursePage(id,ou);
            if(url!=null) {
                url = url.replaceAll("shareBack=1&", "");
            }
            return "redirect:" + url;
        } catch (Exception e) {
            e.printStackTrace();
            
            return "redirect:" + url;
        }
    }
    
    
    /**
     * 主播页面课程跳转重定向
     * @param id
     * @param accountIdOpt
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "doctor/{id}", method = RequestMethod.GET)
    public String jumpDoctor(@PathVariable String id,
          Integer idType) throws SQLException {
        
        String url = WechatShareLinkType.INDEX_PAGE.getLink();
        try {
           
            if(idType!=null && idType.equals(1)) {//用户id
                Map<String,Object> map  = myInfoService.findHostTypeByUserId(id);
                if(map!=null && map.get("type")!=null &&  map.get("type").toString().equals("1")) {
                    url = WechatShareLinkType.LIVE_PERSONAL.getLink(); 
                    url = url + map.get("doctorId").toString();//医师id
                }else if(map!=null && map.get("type")!=null &&  map.get("type").toString().equals("2")){
                    url = WechatShareLinkType.DOCDOT_SHARE.getLink(); 
                    url = url +id;  //用户id
                }
                
            }else if(idType!=null && idType.equals(2)) { //医师id
                url = WechatShareLinkType.DOCDOT_SHARE.getLink(); 
                url = url +id;
            }
            if(url!=null) {
                url = url.replaceAll("shareBack=1&", "");
            }
            return "redirect:" + url;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:" + url;
        }
    }
    
    
    /**
     * 课程跳转重定向
     * @param id
     * @param accountIdOpt
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "hostType/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject jumpHost(@PathVariable String  userId) throws SQLException {
        Map<String,Object> map  = myInfoService.findHostTypeByUserId(userId);
        return ResponseObject.newSuccessResponseObject(map);
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
        //课程已下架跳转下架页面
        if (cv.getStatus() == 0) {
            coursePage = WechatShareLinkType.UNSHELVE.getLink();
            return coursePage;
        }

        //用户未登录去展示页
        if (ou == null) {
            if (cv.getType().equals(1) || cv.getType().equals(0)) {
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
            //判断有没有学习过啦啦啦
            if (cv.getWatchState().equals(0)) {
                if (cv.getType().equals(1) || cv.getType().equals(2)) {
                    //视频音频购买
                    coursePage = WechatShareLinkType.SCHOOL_AUDIO.getLink();
                } else if (cv.getType().equals(3)) {
                    //直播的
                    coursePage = courseLivePage(cv,ou);
                } else {
                    //线下课购买
                    coursePage = WechatShareLinkType.SCHOOL_CLASS.getLink();
                }
            } else if (cv.getWatchState().equals(2)  || cv.getWatchState().equals(1)) {
              
                if(cv.getType().equals(1) || cv.getType().equals(2)) {

                    //增加学习记录
                    watchHistoryService.addLookHistory(courseId, ou.getId(),
                            RecordType.STUDY.getId(), null);
                    
                    if (cv.getCollection()) {
                        //专辑视频音频播放页
                        coursePage = WechatShareLinkType.LIVE_SELECT_ALBUM.getLink();
                    } else {
                        coursePage = WechatShareLinkType.LIVE_AUDIO.getLink();
                    }
                    
                }else if(cv.getType().equals(3)) {
                    //直播的
                    coursePage = courseLivePage(cv,ou);
                } else {
                    //线下课页面
                    coursePage = WechatShareLinkType.LIVE_CLASS.getLink();
                }
            }
        }
        return coursePage + courseId;
    }

    public String courseLivePage(CourseLecturVo cv, OnlineUser ou) {
        
        String coursePage = WechatShareLinkType.INDEX_PAGE.getLink();
        //付费的
        if(cv.getWatchState().equals(0)) {
            coursePage = WechatShareLinkType.SCHOOL_PLAY.getLink();    
        //免费 且  直播    
        }else if(cv.getWatchState().equals(1) 
                && cv.getLineState().equals(1)) {
            
            //增加学习记录
            watchHistoryService.addLookHistory(cv.getId(), ou.getId(),
                    RecordType.STUDY.getId(), null);
            
            //观看记录
            watchHistoryService.addLookHistory(cv.getId(), ou.getId(),
                    RecordType.LOOK.getId(), null);
            
            
            coursePage = WechatShareLinkType.LIVE.getLink();
            
        }else if(cv.getWatchState().equals(1) 
                && !cv.getLineState().equals(1)) {
            
            //增加学习记录
            watchHistoryService.addLookHistory(cv.getId(), ou.getId(),
                    RecordType.STUDY.getId(), null);
            
            coursePage = WechatShareLinkType.LIVE_PLAY.getLink();
            
        }else if(cv.getWatchState().equals(2) 
                && cv.getLineState().equals(1)) {
            
            //增加学习记录
            watchHistoryService.addLookHistory(cv.getId(), ou.getId(),
                    RecordType.STUDY.getId(), null);
            
            coursePage = WechatShareLinkType.LIVE.getLink();
            
        }else if(cv.getWatchState().equals(2) 
                && !cv.getLineState().equals(1)){
            
            coursePage = WechatShareLinkType.SCHOOL_PLAY.getLink();
        }
        return coursePage;
    }


}
