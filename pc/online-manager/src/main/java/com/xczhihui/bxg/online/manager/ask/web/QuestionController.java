package com.xczhihui.bxg.online.manager.ask.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.manager.ask.service.QuestionService;
import com.xczhihui.bxg.online.manager.ask.service.TagService;
import com.xczhihui.bxg.online.manager.ask.vo.QuestionVo;
import com.xczhihui.bxg.online.manager.ask.vo.TagVo;
import com.xczhihui.bxg.online.manager.cloudClass.service.CloudClassMenuService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.MenuVo;
import com.xczhihui.bxg.online.manager.support.shiro.ManagerUserUtil;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import com.xczhihui.user.center.utils.CodeUtil;

/**
 * 问答管理
 *
 * @author 王高伟
 * @create 2016-10-13 17:55:40
 */
@RestController
@RequestMapping(value = "/ask/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CloudClassMenuService menuService;

    @Autowired
    private TagService tagService;

    @Value("${online.web.url}")
    private String weburl;

    /**
     * 问答管理
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("ask/question");

        mav.addObject("md5UserName", CodeUtil.MD5Encode(CodeUtil.MD5Encode(ManagerUserUtil.getUsername() + "WWW.ixincheng.com20161021")));

        weburl = (weburl == null) ? "http://www.ixincheng.com" : weburl;
        request.setAttribute("weburl", weburl);

        return mav;
    }

    //@RequiresPermissions("ask:question")
    @RequestMapping(value = "/findQuestionList", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findQuestionList(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group statusGroup = groups.findByName("status");
        Group titleGroup = groups.findByName("title");
        Group nameGroup = groups.findByName("name");
        Group startTimeGroup = groups.findByName("startTime");
        Group stopTimeGroup = groups.findByName("stopTime");
        Group answerSumGroup = groups.findByName("answerSum");
        Group browseSumGroup = groups.findByName("browseSum");
        Group mentIdGroup = groups.findByName("mentId");
        Group tagsGroup = groups.findByName("tags");
        Group userFormGroup = groups.findByName("userForm");

        QuestionVo searchVo = new QuestionVo();
        if (statusGroup != null) {
            searchVo.setStatus(statusGroup.getPropertyValue1().toString());
        }
        if (titleGroup != null) {
            searchVo.setTitle(titleGroup.getPropertyValue1().toString());
        }
        if (nameGroup != null) {
            searchVo.setCreatePersonName(nameGroup.getPropertyValue1().toString());
        }
        if (startTimeGroup != null) {
            searchVo.setStartTime(DateUtil.parseDate(startTimeGroup.getPropertyValue1().toString(), "yyyy-MM-dd"));
        }
        if (stopTimeGroup != null) {
            searchVo.setStopTime(DateUtil.parseDate(stopTimeGroup.getPropertyValue1().toString(), "yyyy-MM-dd"));
        }
        if (answerSumGroup != null) {
            searchVo.setAnswerSum(Integer.parseInt(answerSumGroup.getPropertyValue1().toString()));
        }
        if (browseSumGroup != null) {
            searchVo.setBrowseSum(Integer.parseInt(browseSumGroup.getPropertyValue1().toString()));
        }
        if (mentIdGroup != null) {
            searchVo.setMentId(Integer.parseInt(mentIdGroup.getPropertyValue1().toString()));
        }
        if (tagsGroup != null) {
            searchVo.setTags(tagsGroup.getPropertyValue1().toString());
        }
        if (userFormGroup != null) {
            String[] str = userFormGroup.getPropertyValue1().toString().split("_");
            for (int i = 0; i < str.length; i++) {
                if (i == 0 && str[0] != "") {
                    searchVo.setOrigin(str[0]);
                }
                if (i == 1 && str[1] != "") {
                    searchVo.setType(Integer.parseInt(str[1]));
                }
            }
        }

        Page<QuestionVo> page = questionService.findQuestionPage(searchVo, currentPage, pageSize);

        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    //@RequiresPermissions("ask:question")
    @RequestMapping(value = "/getMenuList", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuVo> getMenuList() {
        TagVo tagVo = new TagVo();
        List<MenuVo> list = menuService.getMenuList();
        for (MenuVo menuVo : list) {
            tagVo.setMenuId(menuVo.getId());
            menuVo.setListTagVo(tagService.findTagVo(tagVo));
        }
        return list;
    }

    //@RequiresPermissions("ask:question")
    @RequestMapping(value = "/checkQuestionStatus", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject checkQuestionStatus(QuestionVo questionVo) {
        ResponseObject responseObject = new ResponseObject();
        if (questionService.checkQuestionStatus(questionVo)) {//已经处理
            responseObject.setSuccess(true);
            responseObject.setErrorMessage("该问题已经解决!");
        } else {
            responseObject.setSuccess(false);
        }
        return responseObject;
    }
}
