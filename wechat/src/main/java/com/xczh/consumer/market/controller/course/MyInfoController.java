package com.xczh.consumer.market.controller.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.MyCourseType;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IFocusService;
import com.xczhihui.course.vo.CourseLecturVo;

/**
 * 点播控制器 ClassName: BunchPlanController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@Controller
@RequestMapping("/xczh/myinfo")
public class MyInfoController {

    @Autowired
    private ICourseService courseServiceImpl;

    @Autowired
    @Qualifier("focusServiceRemote")
    private IFocusService ifocusService;

    @Autowired
    private OnlineUserService onlineUserService;
    

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MyInfoController.class);

    @Value("${gift.im.room.postfix}")
    private String postfix;

    /**
     * Description：学习模块课程
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("list")
    @ResponseBody
    public ResponseObject list(HttpServletRequest req,
                               HttpServletResponse res,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize, @Account String accountId)
            throws Exception {
        if (pageSize == null || pageSize == 0) {
            pageSize = Integer.MAX_VALUE;
        }
        List<CourseLecturVo> listAll = courseServiceImpl
                .selectLearningCourseListByUserId(pageSize, accountId);
        List<Map<String, Object>> mapCourseList = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapTj = new HashMap<String, Object>();
        Map<String, Object> mapNw = new HashMap<String, Object>();
        List<CourseLecturVo> listTj = new ArrayList<CourseLecturVo>();
        List<CourseLecturVo> listNw = new ArrayList<CourseLecturVo>();
        for (CourseLecturVo courseLecturVo : listAll) {
            if ("我的课程".equals(courseLecturVo.getNote())) {
                listTj.add(courseLecturVo);
            }
            if ("已结束课程".equals(courseLecturVo.getNote())) {
                listNw.add(courseLecturVo);
            }
        }
        mapTj.put("title", "我的课程");
        mapTj.put("courseList", listTj);

        mapNw.put("title", "已结束课程");
        mapNw.put("courseList", listNw);

        mapCourseList.add(mapTj);
        mapCourseList.add(mapNw);
        return ResponseObject.newSuccessResponseObject(mapCourseList);
    }

    /**
     * Description： 关注的主播（我的关注）
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("myFocus")
    @ResponseBody
    public ResponseObject myFocus(HttpServletRequest req,
                                  HttpServletResponse res, @Account String accountId)
            throws Exception {
        return ResponseObject.newSuccessResponseObject(ifocusService.selectFocusList(accountId));
    }

    /**
     * Description： 取消/增加   关注
     *
     * @param req
     * @param lecturerId
     * @param type
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("updateFocus")
    @ResponseBody
    public ResponseObject updateFocus(HttpServletRequest req,
                                      @RequestParam("lecturerId") String lecturerId,
                                      @RequestParam("type") Integer type, 
                                      @Account String accountId)
            throws Exception {
        OnlineUser onlineLecturer = onlineUserService.findUserById(lecturerId);
        if (null == onlineLecturer) {
            return ResponseObject.newErrorResponseObject("操作失败");
        }
        String lockId = lecturerId + accountId;
        ifocusService.updateFocus(lockId, 
        		lecturerId,accountId, type);
       
        return ResponseObject.newSuccessResponseObject("操作成功");
    }

    /**
     * 我的课程和已结束课程
     *
     * @param req
     * @param pageNumber
     * @param pageSize
     * @return
     * @throws Exception
     */
    @RequestMapping("myCourseType")
    @ResponseBody
    public ResponseObject myCourseType(HttpServletRequest req,
                                       @RequestParam("pageNumber") Integer pageNumber,
                                       @RequestParam("pageSize") Integer pageSize,
                                       @RequestParam("type") Integer type, @Account String accountId) throws Exception {

        String myCourseType = MyCourseType.getTypeText(type);
        if (myCourseType == null) {
            return ResponseObject.newErrorResponseObject("我的课程类型有误：" + MyCourseType.getAllToString());
        }
        int num = (pageNumber - 1) * pageSize;
        num = num < 0 ? 0 : num;
        Page<CourseLecturVo> page = new Page<CourseLecturVo>();
        page.setCurrent(num);
        page.setSize(pageSize);
        Page<CourseLecturVo> pageList = courseServiceImpl.myCourseType(page, accountId, type);
        return ResponseObject.newSuccessResponseObject(pageList.getRecords());
    }
}
