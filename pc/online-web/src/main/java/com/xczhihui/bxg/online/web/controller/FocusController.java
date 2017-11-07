package com.xczhihui.bxg.online.web.controller;

/**
 * @author
 * @create 2017-08-20 13:12
 **/

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.Focus;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.FocusService;
import com.xczhihui.bxg.online.web.service.OnlineUserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value = "/focus")
public class FocusController {



    @Autowired
    private OnlineUserCenterService userCenterService;

    @Autowired
    private FocusService focusService;


    /**
     * Description： 增加关注信息
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @return ResponseObject
     * @author name：liutao
     */
    @RequestMapping("add")
    @ResponseBody
    public ResponseObject addFocus(HttpServletRequest req,
                                   HttpServletResponse res, Focus focus)
            throws Exception {

        OnlineUser u =  (OnlineUser)req.getSession().getAttribute("_user_");
        if(u ==null){
            return ResponseObject.newErrorResponseObject("获取用户信息异常");
        }

        OnlineUser lu =  userCenterService.getUser(focus.getLecturerId());
        if(null == lu){
            return ResponseObject.newErrorResponseObject("获取讲师信息异常");
        }

               focusService.addFocusInfo(focus,u,lu);
                return ResponseObject.newSuccessResponseObject("关注成功");

    }
    /**
     * Description： 取消关注
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @return ResponseObject
     * @author name：liutao
     */
    @RequestMapping("cancel")
    @ResponseBody
    public ResponseObject cancelFocus(HttpServletRequest req,
                                      HttpServletResponse res, Focus focus)
            throws Exception {
        OnlineUser u =  (OnlineUser)req.getSession().getAttribute("_user_");
        if(u ==null){
            return ResponseObject.newErrorResponseObject("获取用户信息异常");
        }

        OnlineUser lu =  userCenterService.getUser(focus.getLecturerId());
        if(null == lu){
            return ResponseObject.newErrorResponseObject("获取讲师信息异常");
        }
        return focusService.removeFocus(lu.getId(),u.getId());
    }

    /**
     * Description： 关注的人（我的关注）
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @return ResponseObject
     */
    @RequestMapping("myFocus")
    @ResponseBody
    public ResponseObject myFocus(HttpServletRequest req,
                                  HttpServletResponse res)
            throws Exception {
        String pageNumberS = req.getParameter("pageNumber");
        String pageSizeS = req.getParameter("pageSize");
        if (null == pageNumberS || null == pageSizeS) {
            return ResponseObject.newErrorResponseObject("参数异常");
        }

        OnlineUser u = (OnlineUser) req.getSession().getAttribute("_user_");
        if (u == null) {
            return ResponseObject.newErrorResponseObject("获取用户信息异常");
        }
        int pageNumber = Integer.parseInt(pageNumberS);
        int pageSize = Integer.parseInt(pageSizeS);
        return ResponseObject.newSuccessResponseObject(focusService.findMyFocus(u.getId(), pageNumber, pageSize));

    }

    /**
     * Description： 我的粉丝
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @return ResponseObject
     */
    @RequestMapping("myFans")
    @ResponseBody
    public ResponseObject myFans(HttpServletRequest req,
                                 HttpServletResponse res)
            throws Exception {
        String pageNumberS = req.getParameter("pageNumber");
        String pageSizeS = req.getParameter("pageSize");
        if(null == pageNumberS || null == pageSizeS){
            return ResponseObject.newErrorResponseObject("参数异常");
        }
        OnlineUser u = (OnlineUser) req.getSession().getAttribute("_user_");
        if (u == null) {
            return ResponseObject.newErrorResponseObject("获取用户信息异常");
        }
        int pageNumber =Integer.parseInt(pageNumberS);
        int pageSize = Integer.parseInt(pageSizeS);
        return ResponseObject.newSuccessResponseObject(focusService.findMyFans(u.getId(),pageNumber,pageSize));
    }
}
