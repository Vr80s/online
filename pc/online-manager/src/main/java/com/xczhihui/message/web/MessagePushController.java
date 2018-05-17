package com.xczhihui.message.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.online.common.domain.MessageRecord;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.UserVo;
import com.xczhihui.course.enums.MessageTypeEnum;
import com.xczhihui.course.enums.RouteTypeEnum;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.message.service.MessageService;
import com.xczhihui.message.vo.MessageVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.service.UserService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

/**
 * 消息管理控制器
 *
 * @author majian
 * @date 2016-3-4 10:52:53
 */
@Controller
@RequestMapping("/message/messagePush")
public class MessagePushController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 消息服务
     */
    @Autowired
    private MessageService messageService;

    /**
     * 用户服务
     */
    @Autowired
    private UserService userService;

    @Autowired
    private ICommonMessageService commonMessageService;

    /**
     * 跳转到页面
     *
     * @return 指定页面
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("message/messagePush");
        return mav;
    }

    /**
     * 返回所有用户信息
     *
     * @param request 请求对象
     * @return 所有用户信息
     */
    @RequestMapping(value = "userlist")
    @ResponseBody
    public ResponseObject findUsers(HttpServletRequest request) {
        ResponseObject responseObj = new ResponseObject();
        List<UserVo> users = userService.findAll();
        responseObj.setResultObject(users);
        return responseObj;
    }

    /**
     * 返回站内全部推送
     *
     * @param tableVo 表格对象
     * @return 表格对象
     */
    @RequestMapping(value = "messageslist")
    @ResponseBody
    public TableVo loadMessages(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int pageNumber = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        MessageVo searchMessageVo = new MessageVo();

        Page<MessageVo> page = messageService.findPageMessages(searchMessageVo, pageNumber,
                pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    /**
     * 批量删除常量
     *
     * @param ids id列表
     * @return 响应对象
     */
    @RequestMapping(value = "deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteBatch(String ids) {
        ResponseObject responseObj = new ResponseObject();
        try {
            String[] idArr = ids.split(",");
            messageService.deleteBatch(idArr);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }

    /**
     * 加载制定菜单下的视频
     *
     * @param userId  用户编号
     * @param tableVo 表格对象
     * @return 表格对象
     */
    @RequestMapping(value = "load/user/{userId}/messages")
    @ResponseBody
    public TableVo loadVideoByMenu(@PathVariable String userId, TableVo tableVo) {
        if (StringUtils.isNotEmpty(userId)) {
            int pageSize = tableVo.getiDisplayLength();
            int index = tableVo.getiDisplayStart();
            int pageNumber = index / pageSize + 1;
            String params = tableVo.getsSearch();
            Groups groups = Tools.filterGroup(params);
            String vNameOrCreater = "";
            for (Group g : groups.getGroupList()) {
                if ("vNameOrCreater".equals(g.getPropertyName())) {
                    vNameOrCreater = g.getPropertyValue1().toString();
                }
            }
            UserVo userVo = new UserVo();
            userVo.setId(userId);
            Page<MessageVo> page = messageService.findPageMessagesByUser(userVo, vNameOrCreater, pageNumber,
                    pageSize);

            int total = page.getTotalCount();
            tableVo.setAaData(page.getItems());
            tableVo.setiTotalDisplayRecords(total);
            tableVo.setiTotalRecords(total);
        }

        return tableVo;
    }

    /**
     * 删除消息
     *
     * @param id      编号
     * @param request 请求对象
     * @return 响应对象
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteById(@PathVariable String id, HttpServletRequest request) {
        ResponseObject responseObj = new ResponseObject();

        try {
            if (StringUtils.isNotEmpty(id)) {
                messageService.deleteById(id);
                responseObj.setSuccess(true);
                responseObj.setErrorMessage("操作成功");
                return responseObj;
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        responseObj.setSuccess(false);
        responseObj.setErrorMessage("操作失败");
        return responseObj;
    }

    /**
     * 保存消息推送信息
     *
     * @param messageVo 消息对象
     * @return 响应对象
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public ResponseObject save(HttpServletRequest request, MessageVo messageVo) throws IllegalAccessException, InvocationTargetException {
        ResponseObject responseObj = new ResponseObject();
        String userIdList = messageVo.getUserIdList();
        String[] strs = userIdList.split(",");
        for (int i = 0, len = strs.length; i < len; i++) {
            String userId = strs[i];
            commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal())
                    .buildWeb(messageVo.getContext())
                    .build(userId, RouteTypeEnum.COURSE_DETAIL_PAGE, ManagerUserUtil.getId()));
        }
        if (strs.length == 0) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("没有用户，请重试！");
            return responseObj;
        }
        MessageRecord messageRecord = new MessageRecord();
        messageRecord.setContext(messageVo.getContext());
        messageRecord.setCreateTime(new Date());
        messageRecord.setCreatePerson(ManagerUserUtil.getName());
        messageRecord.setPushTime(messageVo.getPushTime());
        messageRecord.setPushType(messageVo.getPushType());
        messageRecord.setPushAction(messageVo.getPushAction());
        messageRecord.setUrl(messageVo.getUrl());
        messageRecord.setPushCount(0);
        messageRecord.setUserCount(strs.length);
        messageService.saveMessageRecord(messageRecord);
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("操作成功");
        return responseObj;
    }
}
