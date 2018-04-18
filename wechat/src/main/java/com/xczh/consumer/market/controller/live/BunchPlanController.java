package com.xczh.consumer.market.controller.live;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.*;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.MenuVo;

/**
 * 点播控制器 ClassName: BunchPlanController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@Controller
@RequestMapping("/bxg/bunch")
public class BunchPlanController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BunchPlanController.class);

    @Autowired
    private OLCourseServiceI wxcpCourseService;

    @Autowired
    private OnlineCourseService onlineCourseService;

    @Autowired
    private FocusService focusService;

    @Autowired
    private AppBrowserService appBrowserService;

    @Autowired
    private OnlineWebService onlineWebService;

    @Autowired
    private MenuService menuService;


    @Value("${gift.im.room.postfix}")
    private String postfix;
    @Value("${gift.im.boshService}")
    private String boshService;//im服务地址
    @Value("${gift.im.host}")
    private String host;

    // 新增的课程列表页
    @RequestMapping("categorylist")
    @ResponseBody
    public ResponseObject categoryXCList(HttpServletRequest req, HttpServletResponse res, Map<String, String> params)
            throws Exception {

        return ResponseObject.newSuccessResponseObject(wxcpCourseService.categoryXCList());
    }

    // 新增点播课程list
    @RequestMapping("list")
    @ResponseBody
    public ResponseObject courseXCList(HttpServletRequest req,
                                       HttpServletResponse res, Map<String, String> params)
            throws Exception {
        //多媒体类型1视频2音频
        String menid = req.getParameter("menu_id");
        String s = req.getParameter("pageNumber");
        String e = req.getParameter("pageSize");
        LOGGER.info("pageNumber:" + s + "===========================pageSize:" + e);
        String multimedia_type = req.getParameter("multimedia_type");
        if ("".equals(menid) || menid == null || "null".equals(menid)) {
            return ResponseObject.newErrorResponseObject("分类id不能为空");
        }
        if ("".equals(multimedia_type) || multimedia_type == null || "null".equals(multimedia_type)) {
            return ResponseObject.newErrorResponseObject("媒体类型不能为空");
        }
        int number = 0;
        if (!"".equals(s) && s != null && !"null".equals(s)) {
            number = Integer.valueOf(s);
        }
        int pageSize = 0;
        if ("".equals(e) || e == null || "null".equals(e)) {
            pageSize = 6;
        } else {
            pageSize = Integer.valueOf(e);
        }
        List<CourseLecturVo> list = wxcpCourseService.courseXCListByCategory(menid, number, pageSize, Integer.parseInt(multimedia_type));
        LOGGER.info("list.size():" + list.size());
        return ResponseObject.newSuccessResponseObject(list);
    }

    /***
     * 课程详细信息
     * @param req
     * @param res
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping("detail")
    @ResponseBody
    @Transactional
    public ResponseObject courseDetail(HttpServletRequest req,
                                       HttpServletResponse res)
            throws Exception {
        String courseid = req.getParameter("course_id");
        if ("".equals(courseid) || courseid == null || "null".equals(courseid)) {
            return ResponseObject.newErrorResponseObject("课程ID是空的");
        }
        OnlineUser user = appBrowserService.getOnlineUserByReq(req);
        CourseLecturVo courseLecturVo = wxcpCourseService.bunchDetailsByCourseId(Integer.parseInt(courseid));


        if (courseLecturVo == null) {
            return ResponseObject.newSuccessResponseObject("获取课程异常");
        }
        LOGGER.info("getWatchState:" + courseLecturVo.getWatchState());
        if (user != null) {
            Integer isFours = focusService.myIsFourslecturer(user.getId(), courseLecturVo.getUserId());
            courseLecturVo.setIsfocus(isFours);

            if (courseLecturVo.getWatchState() == 0) {
                onlineWebService.saveEntryVideo(Integer.parseInt(courseid), user);
            } else {

                LOGGER.info("getUserId:" + courseLecturVo.getUserId() + "======" + user.getId());

                if (courseLecturVo.getUserId().equals(user.getId()) ||
                        onlineWebService.getLiveUserCourse(Integer.parseInt(courseid), user.getId())) {
                    //LOGGER.info("同学,当前课程您已经报名了!");
                    courseLecturVo.setWatchState(0);
                }
                ;
            }
        }
        /*
		 * 我的粉丝总数
		 */
        Integer countFans = focusService.findMyFansCount(courseLecturVo.getUserId());
        courseLecturVo.setCountFans(countFans);
		/*
		 * 我的礼物总数 
		 */
        //做下播放的兼容性
        String flag = req.getParameter("flag");//传递一个参数
        String newflag = req.getParameter("newflag");//传递一个参数
        if (StringUtils.isNotBlank(newflag)) {
            flag = newflag;
        }
        String appUniqueId = req.getParameter("appUniqueId");
        LOGGER.info("flag:" + flag);
        LOGGER.info("appUniqueId:" + appUniqueId);
        LOGGER.info("liveid:" + courseLecturVo.getDirectId());

        if ((!StringUtils.isNotBlank(flag) && StringUtils.isNotBlank(appUniqueId))) { //等于null的是以前的版本需要判断是否需要获取视频id
            courseLecturVo = changeLiveId(courseLecturVo);
            LOGGER.info("liveid:" + courseLecturVo.getDirectId());
        }
        return ResponseObject.newSuccessResponseObject(courseLecturVo);
    }

    public CourseLecturVo changeLiveId(CourseLecturVo courseLecturVo) {
//		姚老师：
//		562965798    238481982   598747364
//		王老师
//		340273573    337055289    362080337
//		郝万山
//		265106673    593193792   814649885
        Map<Integer, String> map = new HashMap<Integer, String>();
        // key 课程id   value  对应的视频id
        map.put(611, "562965798");
        map.put(612, "238481982");
        map.put(613, "598747364");

        map.put(614, "340273573");
        map.put(615, "337055289");
        map.put(616, "362080337");

        map.put(608, "265106673");
        map.put(609, "593193792");
        map.put(610, "814649885");
/*		map.put(4, "340273573");
		map.put(5, "337055289");
		map.put(6, "362080337");
		map.put(7, "265106673");
		map.put(8, "593193792");
		map.put(9, "814649885");*/
        for (Integer key : map.keySet()) {
            System.out.println("key= " + key + " and value= " + map.get(key));
            if (key.equals(new Integer(courseLecturVo.getId()))) {
                courseLecturVo.setDirectId(map.get(key));
            }
        }
        return courseLecturVo;
    }

    /**
     * 线下培训班
     */
    @RequestMapping("offLineClass")
    @ResponseBody
    public ResponseObject offLineClass(HttpServletRequest req,
                                       HttpServletResponse res, Map<String, String> params)
            throws Exception {
        //多媒体类型1视频2音频
        String keyWord = req.getParameter("keyWord");
        String s = req.getParameter("pageNumber");
        String e = req.getParameter("pageSize");
        LOGGER.info("pageNumber:" + s + "===========================pageSize:" + e);
        int number = 0;
        if (!"".equals(s) && s != null && !"null".equals(s)) {
            number = Integer.valueOf(s);
        }
        int pageSize = 0;
        if ("".equals(e) || e == null || "null".equals(e)) {
            pageSize = 6;
        } else {
            pageSize = Integer.valueOf(e);
        }


        List<CourseLecturVo> list = wxcpCourseService.offLineClass(keyWord, number, pageSize);
        LOGGER.info("list.size():" + list.size());
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 线下培训班列表
     */
    @RequestMapping("offLineClassList")
    @ResponseBody
    public ResponseObject offLineClassList(HttpServletRequest req,
                                           HttpServletResponse res, Map<String, String> params)
            throws Exception {
        //多媒体类型1视频2音频
        String s = req.getParameter("pageNumber");
        String e = req.getParameter("pageSize");
        LOGGER.info("pageNumber:" + s + "===========================pageSize:" + e);
        int number = 1;
        if (!"".equals(s) && s != null && !"null".equals(s)) {
            number = Integer.valueOf(s);
        }
        int pageSize = 0;
        if ("".equals(e) || e == null || "null".equals(e)) {
            pageSize = 6;
        } else {
            pageSize = Integer.valueOf(e);
        }
        List<CourseLecturVo> list = wxcpCourseService.offLineClassListOld(number, pageSize);
        for (CourseLecturVo courseLecturVo : list) {
            String city = courseLecturVo.getAddress();
            String[] citys = city.split("-");
            courseLecturVo.setCity(citys[1]);
        }
        LOGGER.info("list.size():" + list.size());
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 线下培训班详情
     */
    @RequestMapping("offLineClassItem")
    @ResponseBody
    public ResponseObject offLineClassItem(HttpServletRequest req,
                                           HttpServletResponse res, Integer id)
            throws Exception {

        String userId = req.getParameter("userId");
        CourseLecturVo courseLecturVo = wxcpCourseService.offLineClassItem(id, userId);
        if (userId != null) {
            OnlineUser onlineUser = new OnlineUser();
            onlineUser.setId(userId);
            ResponseObject resp = onlineCourseService.courseIsBuy(onlineUser, id);
            if (resp.isSuccess()) {//已经付过费了
                courseLecturVo.setWatchState(0);
            } else {

            }
        }
        return ResponseObject.newSuccessResponseObject(courseLecturVo);
    }


   
}
