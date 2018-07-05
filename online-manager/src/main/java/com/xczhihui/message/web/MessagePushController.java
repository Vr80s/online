package com.xczhihui.message.web;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.anchor.service.AnchorService;
import com.xczhihui.bxg.online.common.domain.CourseAnchor;
import com.xczhihui.bxg.online.common.domain.MessageRecord;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.RouteTypeEnum;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.vo.CourseVo;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.menu.service.CommonMenuService;
import com.xczhihui.message.body.MessageBody;
import com.xczhihui.message.service.MessageService;
import com.xczhihui.message.vo.MessageVo;
import com.xczhihui.user.service.UserService;
import com.xczhihui.utils.TableVo;

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
    @Autowired
    private CommonMenuService commonMenuService;
    @Autowired
    private AnchorService anchorService;
    @Autowired
    private CourseService courseService;

    /**
     * 跳转到页面
     *
     * @return 指定页面
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("message/messagePush");
        List<MenuVo> list = commonMenuService.list();
        mav.addObject("menus", list);
        mav.addObject("courses", courseService.listByMenuId(list.get(0).getId()));
        mav.addObject("anchors", anchorService.list(1));
        return mav;
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
        MessageVo searchMessageVo = new MessageVo();

        Page<MessageRecord> page = messageService.findPageMessages(searchMessageVo, pageNumber,
                pageSize);
        List<MessageRecord> records = page.getItems().stream().map(messageVo -> {
            String routeType = messageVo.getRouteType();
            if (StringUtils.isNotBlank(routeType)) {
                String detailId = messageVo.getDetailId();
                if (StringUtils.isNotBlank(detailId)) {
                    if (routeType.equals(RouteTypeEnum.COMMON_COURSE_DETAIL_PAGE.name())) {
                        CourseVo course = courseService.getCourseById(Integer.parseInt(detailId));
                        if (course != null) {
                            messageVo.setCourse(course.getCourseName());
                        }
                    } else if (routeType.equals(RouteTypeEnum.ANCHOR_INDEX.name())) {
                        CourseAnchor courseAnchor = anchorService.findByUserId(detailId);
                        if (courseAnchor != null) {
                            messageVo.setCourse(courseAnchor.getName());
                        }
                    }
                }
                if (StringUtils.isNotBlank(messageVo.getUrl()) && routeType.equals(RouteTypeEnum.H5.name())) {
                    messageVo.setCourse(messageVo.getUrl());
                }
            }
            return messageVo;
        }).collect(Collectors.toList());
        int total = page.getTotalCount();
        tableVo.setAaData(records);
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
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
    public ResponseObject deleteById(@PathVariable Integer id, HttpServletRequest request) {
        ResponseObject responseObj = new ResponseObject();
        try {
            messageService.deleteMessageRecord(id);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
            return responseObj;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        responseObj.setSuccess(false);
        responseObj.setErrorMessage("操作失败");
        return responseObj;
    }

    /**
     * 删除消息
     *
     * @param id      编号
     * @param request 请求对象
     * @return 响应对象
     */
    @RequestMapping(value = "updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(@PathVariable String id, HttpServletRequest request) {
        ResponseObject responseObj = new ResponseObject();
        try {
            if (StringUtils.isNotEmpty(id)) {
                messageService.updateStatus(id);
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

    @RequestMapping(value = "doctorAnchor", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject getDoctorAnchor() {
        return ResponseObject.newSuccessResponseObject(anchorService.list(1));
    }

    @RequestMapping(value = "course", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject findCourseByMenuId(@RequestParam String menuId) {
        return ResponseObject.newSuccessResponseObject(courseService.listByMenuId(menuId));
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject saveMessage(HttpServletRequest request) {
        MessageBody messageBody = new MessageBody();
        messageBody.setContent(request.getParameter("content"));
        messageBody.setDetailId(request.getParameter("detailId"));
        messageBody.setPushTime(request.getParameter("pushTime"));
        messageBody.setRouteType(request.getParameter("routeType"));
        messageBody.setTitle(request.getParameter("title"));
        messageBody.setUrl(request.getParameter("url"));
        messageBody.setPushType(Integer.parseInt(request.getParameter("pushType")));
        if (request.getContentType().contains("multipart/form-data")) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();
            if (fileNames.hasNext()) {
                messageBody.setPushUserFile(multipartHttpServletRequest.getFile(fileNames.next()));
            }
        }
        messageService.savePushMessageRecord(messageBody);
        return ResponseObject.newSuccessResponseObject();
    }
}
