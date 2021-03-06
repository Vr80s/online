package com.xczhihui.bxg.online.web.controller.medical;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import com.xczhihui.medical.anchor.vo.CourseAnchorVO;


/**
 * 主播工作台资产控制层
 *
 * @author yuruixin
 */
@RestController
@RequestMapping(value = "/anchor/info")
public class AnchorController extends AbstractController {

    @Autowired
    private IAnchorInfoService anchorInfoService;

    /**
     * 获取主播详情
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseObject detail(HttpServletRequest request) {
        String userId = this.getCurrentUserId(request);
        return ResponseObject.newSuccessResponseObject(anchorInfoService.detail(userId));
    }

    /**
     * 获取主播认证详情
     */
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ResponseObject authInfo(HttpServletRequest request) {
        String userId = this.getCurrentUserId(request);
        return ResponseObject.newSuccessResponseObject(anchorInfoService.authInfo(userId));
    }

    /**
     * 修改主播信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObject update(CourseAnchorVO target, HttpServletRequest request) {
        String userId = this.getCurrentUserId(request);
        target.setUserId(userId);
        anchorInfoService.update(target);
        return ResponseObject.newSuccessResponseObject("修改成功");
    }

    /**
     * 获取发起申请的医师的id
     *
     * @param request
     * @return
     */
    private String getCurrentUserId(HttpServletRequest request) {
        OnlineUser loginUser = getCurrentUser();
        return loginUser.getId();
    }

    @RequestMapping("hasPower")
    public ResponseObject hasPower(HttpServletRequest request) {
        OnlineUser loginUser = getCurrentUser();
//        try {
            anchorInfoService.validateAnchorPermission(loginUser.getId());
//        } catch (Exception e) {
//            return ResponseObject.newErrorResponseObject(e.getMessage());
//        }
        return ResponseObject.newSuccessResponseObject("有主播权限");
    }
}
