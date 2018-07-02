package com.xczhihui.bxg.online.web.controller;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.online.common.base.controller.OnlineBaseController;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.OnlineResponse;
import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.TokenExpires;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.utils.UCCookieUtil;
import com.xczhihui.user.center.vo.OeUserVO;
import com.xczhihui.user.center.vo.Token;

/**
 * 用户相关
 *
 * @author Haicheng Jiang
 */
@Controller
@RequestMapping(value = "/online/user")
public class UserController extends OnlineBaseController {

    @Autowired
    private UserService service;

    @Autowired
    private UserCenterService userCenterService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseObject login(String username, String password, HttpServletResponse response) {
        Token t = userCenterService.loginMobile(username, password, TokenExpires.Year);
        UCCookieUtil.writeTokenCookie(response, t);
        return ResponseObject.newSuccessResponseObject("登录成功");
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ResponseObject logout(HttpServletRequest request, HttpServletResponse response) {
        UCCookieUtil.clearTokenCookie(response);
        return ResponseObject.newSuccessResponseObject(null);
    }

    /**
     * 手机提交注册
     *
     * @param username
     * @param password
     * @param code
     * @param req
     * @return
     */
    @RequestMapping(value = "phoneRegist", method = RequestMethod.POST)
    public ResponseObject phoneRegist(String username, String password, String code, String nikeName, HttpServletRequest req, HttpServletResponse resp) {
        service.addPhoneRegist(req, username, password, code, nikeName);
        autoLogin(resp, username, password);
        return ResponseObject.newSuccessResponseObject("注册成功");
    }

    private void autoLogin(HttpServletResponse resp, String loginname, String password) {
        Token t = userCenterService.loginMobile(loginname, password, TokenExpires.Year);
        UCCookieUtil.writeTokenCookie(resp, t);
    }

    /**
     * 手机重置用户密码
     *
     * @param username
     * @param password
     * @param
     * @param req
     * @return
     */
    @RequestMapping(value = "resetUserPassword", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject resetUserPassword(String username, String password, String code, HttpServletRequest req) {
        service.updateUserPassword(username, password, code);
        return ResponseObject.newSuccessResponseObject("修改成功");
    }

    /**
     * 是否登录
     *
     * @return
     */
    @RequestMapping(value = "isAlive")
    @ResponseBody
    public OnlineResponse isAlive(HttpServletRequest request) {
        BxgUser loginUser = UserLoginUtil.getLoginUser();
        if (loginUser == null) {
            return OnlineResponse.newErrorOnlineResponse("未登录");
        }
        return OnlineResponse.newSuccessOnlineResponse(service.isAlive(loginUser.getLoginName()));
    }

    /**
     * 检查当前用户昵称是否存在，存在返回：true   不存在返回：false
     *
     * @param nickName 昵称
     * @return
     */
    @RequestMapping(value = "checkNickName")
    @ResponseBody
    public ResponseObject checkNickName(String nickName) {
        OnlineUser u = (OnlineUser) UserLoginUtil.getLoginUser();
        return ResponseObject.newSuccessResponseObject(service.checkNickName(nickName, u));
    }

    /***
     * 获取用户资料
     * @return
     */
    @RequestMapping(value = "getUserData")
    @ResponseBody
    public ResponseObject getUserData(HttpServletRequest request) {
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser();
        if (loginUser == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录！");
        }
        return ResponseObject.newSuccessResponseObject(service.getUserData(loginUser));
    }

    /**
     * 修改用户资料
     *
     * @param request
     * @param
     * @return
     * @throws ServletRequestBindingException
     */
    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    @ResponseBody
    public OnlineResponse updateUser(HttpServletRequest request, OeUserVO oeUserVO) throws ServletRequestBindingException {
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser();
        oeUserVO.setId(loginUser.getId());
        userCenterService.update(oeUserVO);
        return OnlineResponse.newSuccessOnlineResponse("修改成功");
    }

    /**
     * 修改用户头像
     *
     * @param request
     * @param
     * @return
     * @throws ServletRequestBindingException
     */
    @RequestMapping(value = "updateUserHeadImg", method = RequestMethod.POST)
    @ResponseBody
    public OnlineResponse updateUserHeadImg(HttpServletRequest request) throws ServletRequestBindingException {
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser();
        String img = ServletRequestUtils.getStringParameter(request, "img");
        OeUserVO oeUserVO = new OeUserVO();
        oeUserVO.setId(loginUser.getId());
        oeUserVO.setSmallHeadPhoto(img);
        userCenterService.update(oeUserVO);
        return OnlineResponse.newSuccessOnlineResponse("修改成功");
    }

    /**
     * 修改头像
     *
     * @param request
     * @return
     * @throws ServletRequestBindingException
     * @throws IOException
     */
    @RequestMapping(value = "updateHeadPhoto")
    @ResponseBody
    public ResponseObject updateHeadPhoto(HttpServletRequest request) throws ServletRequestBindingException, IOException {
        //获取当前登录用户信息
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser();

        String content = ServletRequestUtils.getRequiredStringParameter(request, "image");
        int i = content.indexOf(',');
        if (i > 0) {
            content = content.substring(i + 1);
        }
        byte[] image = Base64.getDecoder().decode(content);
        service.updateHeadPhoto(user.getId(), image);

        return this.isAlive(request);
    }

    /**
     * 联动显示省
     *
     * @param
     * @return
     */
    @RequestMapping(value = "listProvinces")
    @ResponseBody
    public ResponseObject listProvinces() {
        return ResponseObject.newSuccessResponseObject(service.listProvinces());
    }

    /**
     * 联动显示市
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "listCities")
    @ResponseBody
    public ResponseObject listCities(String provinceId) {
        return ResponseObject.newSuccessResponseObject(service.listCities(provinceId));
    }

    /**
     * Description：快速登录（方便后台管理）
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin
     * @Date: 2018/6/19 0019 下午 5:29
     **/
    @RequestMapping(value = "/fastLogin/{loginName}/{token}", method = RequestMethod.GET)
    public void fastLogin(@PathVariable String token, @PathVariable String loginName, HttpServletResponse response) throws ServletException, IOException {
        Token t = userCenterService.fastLogin(loginName, token, TokenExpires.Day);
        UCCookieUtil.writeTokenCookie(response, t);
        response.sendRedirect("/");
    }
}
