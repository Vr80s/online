package com.xczhihui.bxg.online.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.OnlineResponse;
import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.bxg.online.web.service.MenuTagService;
import com.xczhihui.bxg.online.web.vo.AskAccuseVo;
import com.xczhihui.bxg.online.web.vo.MenuTagVo;
import com.xczhihui.common.util.bean.ResponseObject;

/**
 * 标签以及投诉等控制层
 *
 * @author Rongcai Kang
 */
@RestController
@RequestMapping(value = "/online/menutag")
public class MenuTagController {

    @Autowired
    private MenuTagService menuTagService;

    /**
     * 获取到所有一二级菜单数据
     *
     * @return
     */
    @RequestMapping(value = "/allMenu", method = RequestMethod.GET)
    @ResponseBody
    public OnlineResponse getForumMenu() {
        List<MenuTagVo> result = menuTagService.getAllMenu();
        return OnlineResponse.newSuccessOnlineResponse(result);
    }


    /**
     * 博文答首页全部学科以及学科下的标签
     *
     * @return 菜单集合
     */
    @RequestMapping(value = "/getMenuTags")
    public ResponseObject getMenuTags() throws InvocationTargetException, IllegalAccessException {
        return ResponseObject.newSuccessResponseObject(menuTagService.getMenuTags());
    }

    /**
     * 博文答提问时获取有权限学科以及学科下的标签
     *
     * @return 菜单集合
     */
    @RequestMapping(value = "/getQuestionMenuTags")
    public ResponseObject getQuestionMenuTags(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        return ResponseObject.newSuccessResponseObject(menuTagService.getQuestionMenuTags(request));
    }

    /**
     * 投诉
     *
     * @param ac 参数封装对象
     */
    @RequestMapping(value = "/saveAccuse", method = RequestMethod.POST)
    public ResponseObject saveAccuse(AskAccuseVo ac) {
        OnlineUser u = (OnlineUser) UserLoginUtil.getLoginUser();
        ac.setCreate_person(u.getLoginName());
        ac.setUser_id(u.getId());
        return ResponseObject.newSuccessResponseObject(menuTagService.saveAccuse(ac));
    }

    /**
     * 修改投诉状态
     */
    @RequestMapping(value = "/updateAccuseStatus", method = RequestMethod.GET)
    public ResponseObject updateAccuseStatus(AskAccuseVo ac) {
        return ResponseObject.newSuccessResponseObject(menuTagService.updateAccuseStatus(ac));
    }
}
