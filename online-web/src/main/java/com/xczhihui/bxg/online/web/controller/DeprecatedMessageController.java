package com.xczhihui.bxg.online.web.controller;


import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.OnlineResponse;
import com.xczhihui.bxg.online.web.service.MessageService;
import com.xczhihui.bxg.online.web.service.OnlineUserCenterService;
import com.xczhihui.bxg.online.web.vo.MessageVo;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.service.ICommonMessageService;

/**
 * TODO 消息改版完成后删除该类
 */
@Deprecated
@Controller
@RequestMapping(value = "/online/message/")
public class DeprecatedMessageController extends AbstractController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private ICommonMessageService commonMessageService;
    @Autowired
    private OnlineUserCenterService onlineUserCenterService;

    /**
     * 获取当前用户的系统消息
     * 访问路径(相对)  online/career/getMessageList
     *
     * @return 返回登录用户类型为type的消息记录
     * 如果type为null 则返回当前用户的所有消息
     * @throws ParseException
     */
    @RequestMapping(path = "getMessageList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getMessageList(HttpSession s, Integer type, Integer pageSize, Integer pageNumber) {
        OnlineUser u = getCurrentUser();
        Page<MessageVo> page = messageService.findMessagePage(u, type, pageSize, pageNumber);
        page.getItems().forEach(messageVo -> messageVo.setUrl(MultiUrlHelper.getUrl(messageVo.getRouteType(), MultiUrlHelper.URL_TYPE_WEB, messageVo.getDetailId(), null)));
        return ResponseObject.newSuccessResponseObject(page);
    }

    /**
     * 删除消息
     *
     * @param id
     * @return
     * @throws ParseException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(path = "deleteMessage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteMessage(HttpSession s, String id) throws ParseException, InvocationTargetException, IllegalAccessException {

        OnlineUser u = getCurrentUser();
        if (u == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录!");
        }
        messageService.deleteById(id, u.getId());
        return OnlineResponse.newSuccessOnlineResponse("操作成功");
    }


    /**
     * 全部消息设置已读状态
     */
    @RequestMapping(path = "readMessage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject readMessage(HttpServletRequest request, Integer type) throws ParseException, InvocationTargetException, IllegalAccessException {
        BxgUser user = getCurrentUser();
        if (user == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录!");
        }
        messageService.updateReadStatus(user.getId(), type);
        return OnlineResponse.newSuccessOnlineResponse("操作成功");
    }

    /**
     * 将某条消息标志已读状态
     *
     * @param id 消息id
     */
    @RequestMapping(path = "updateReadStatusById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateReadStatusById(String id, HttpServletRequest request) {
        BxgUser user = getCurrentUser();
        if (user == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录!");
        }
        messageService.updateReadStatusById(id, user.getId());
        return OnlineResponse.newSuccessOnlineResponse("操作成功");
    }

    /**
     * 获取未读消息总数
     *
     * @return
     */
    @RequestMapping(path = "findMessageCount", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject findMessageCount(HttpSession s) {
        OnlineUser user = getCurrentUser();
        if (user == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录!");
        }
        return OnlineResponse.newSuccessOnlineResponse(messageService.findMessageCount(user.getId()));
    }

    /**
     * 获取最新公告
     */
    @RequestMapping(path = "findNewestNotice", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject findNewestNotice(HttpSession s) {
        OnlineUser user = getCurrentUser();
        return OnlineResponse.newSuccessOnlineResponse(messageService.findNewestNotice(user));
    }

    /**
     * 添加意见反馈消息
     *
     * @param title
     * @param describe
     * @return
     * @throws ParseException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(path = "addFeedBack", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addFeedBack(HttpSession s, String title, String describe) {

        OnlineUser u = getCurrentUser();
        if (u == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录!");
        }
        onlineUserCenterService.addFeedBack(u.getId(), title, describe);
        return OnlineResponse.newSuccessOnlineResponse("操作成功");
    }
}
