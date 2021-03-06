package com.xczhihui.message.web;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.online.common.domain.Message;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.common.service.CommonService;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.vo.KeyValVo;
import com.xczhihui.message.service.FeedbackService;
import com.xczhihui.message.vo.FeedBackVo;
import com.xczhihui.message.vo.MessageVo;
import com.xczhihui.user.service.UserService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

/**
 * 意见反馈
 *
 * @author duanqh
 */
@Controller
@RequestMapping("feedback")
public class FeedbackController {

    /**
     * 意见反馈服务层
     */
    @Autowired
    public FeedbackService service;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 公共字典服务层
     */
    @Autowired
    private CommonService commonService;
    /**
     * 用户服务层
     */
    @Autowired
    private UserService userService;

    /**
     * 跳转页面
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        return "feedback/index";
    }

    /**
     * 问题列表数据
     *
     * @param request
     * @param tableVo
     * @return
     */
    @RequestMapping(value = "initFeedback")
    @ResponseBody
    public TableVo list(HttpServletRequest request, TableVo tableVo)
            throws InvocationTargetException, IllegalAccessException {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int pageNumber = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        MessageVo searchMessageVo = new MessageVo();
        for (Group g : groups.getGroupList()) {
            if ("q_name".equals(g.getPropertyName()) && g.getPropertyValue1() != null && !StringUtils.isEmpty(String.valueOf(g
                    .getPropertyValue1()))) {

                searchMessageVo.setUserName(String.valueOf(g
                        .getPropertyValue1()));
            }
            if ("q_answerStatus".equals(g.getPropertyName())
                    && g.getPropertyValue1() != null
                    && !StringUtils.isEmpty(String.valueOf(g
                    .getPropertyValue1()))) {
                searchMessageVo.setAnswerStatus(Short.parseShort(String
                        .valueOf(g.getPropertyValue1())));
            }
            if ("q_title".equals(g.getPropertyName())
                    && g.getPropertyValue1() != null
                    && !StringUtils.isEmpty(String.valueOf(g
                    .getPropertyValue1()))) {
                searchMessageVo.setTitle(String.valueOf(g.getPropertyValue1()));
            }

            if ("q_content".equals(g.getPropertyName())
                    && g.getPropertyValue1() != null && !StringUtils.isEmpty(String.valueOf(g.getPropertyValue1()))) {

                searchMessageVo.setContext(String.valueOf(g.getPropertyValue1()));
            }


            if ("time_start".equals(g.getPropertyName())
                    && g.getPropertyValue1() != null
                    && !StringUtils.isEmpty(String.valueOf(g
                    .getPropertyValue1()))) {
                searchMessageVo.setTime_start(String.valueOf(g
                        .getPropertyValue1()));
            }
            if ("time_end".equals(g.getPropertyName())
                    && g.getPropertyValue1() != null
                    && !StringUtils.isEmpty(String.valueOf(g
                    .getPropertyValue1()))) {
                searchMessageVo.setTime_end(String.valueOf(g
                        .getPropertyValue1()));
            }
        }
        Page<MessageVo> page = service.findPageMessages(searchMessageVo,
                pageNumber, pageSize);

        List<KeyValVo> keyValVos = commonService.findListByGroup("oe_message");
        int rowId = 1;
        if (!CollectionUtils.isEmpty(keyValVos)
                && !CollectionUtils.isEmpty(page.getItems())) {
            for (MessageVo messageVo : page.getItems()) {
                messageVo.setRowId(rowId++);

                messageVo.setTitle(messageVo.getTitle());

                for (KeyValVo keyValVo : keyValVos) {
                    if (keyValVo.get_key() != null) {
                        if (Short
                                .parseShort(String.valueOf(keyValVo.get_key())) == messageVo
                                .getStatus()) {
                            messageVo.setStatusStr(String.valueOf(keyValVo
                                    .getName()));

                            if (messageVo.getLastTime() != null) {
                                // messageVo.setLastTimeStr(TimeUtil.formatDate(messageVo.getLastTime()));
                                messageVo.setLastTimeStr(messageVo
                                        .getLastTimeStr());
                            }
                            if (messageVo.getCreateTime() != null) {
                                // messageVo.setCreateTimeStr(TimeUtil.formatDate(messageVo.getCreateTime()));
                                messageVo.setCreateTimeStr(messageVo
                                        .getCreateTimeStr());
                            }
                            if (messageVo.getUserId() != null) {
                                User user = userService.getUserById(messageVo
                                        .getUserId());
                                if (user != null) {
                                    messageVo.setAnswerName(user.getName());
                                }
                            }

                        }
                    }
                }
            }
        }

        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);

        return tableVo;
    }

    /**
     * 更新删除.
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "updateDelete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(HttpServletRequest request, Model model) {
        ResponseObject responseObj = new ResponseObject();
        try {
            String id = ServletRequestUtils.getRequiredStringParameter(request,
                    "id");
            service.updateStatus(id);
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
     * 更新添加.
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "updateoradd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateOrAdd(FeedBackVo vo) {
        ResponseObject responseObj = new ResponseObject();
        service.addContext(vo);
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("操作成功");
        return responseObj;
    }

    /**
     * 反馈回复信息
     *
     * @param feedId
     * @return
     */
    @RequestMapping(value = "findFeekBackByFeedId", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject findFeekBackByFeedId(String feedId)
            throws InvocationTargetException, IllegalAccessException {
        ResponseObject responseObj = new ResponseObject();
        Message message = service.findFeekBackByFeedId(feedId);
        MessageVo messageVo = new MessageVo();
        if (message != null) {
            BeanUtils.copyProperties(messageVo, message);
            // messageVo.setLastTimeStr(TimeUtil.formatDate(messageVo.getLastTime()));
            messageVo.setLastTimeStr(messageVo.getLastTimeStr());
            // messageVo.setCreateTimeStr(TimeUtil.formatDate(messageVo.getCreateTime()));
            messageVo.setCreateTimeStr(messageVo.getCreateTimeStr());
            responseObj.setResultObject(messageVo);
        }

        return responseObj;
    }

}
