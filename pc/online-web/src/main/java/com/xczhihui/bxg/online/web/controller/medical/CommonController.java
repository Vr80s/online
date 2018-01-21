package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.OnlineResponse;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.bxg.online.web.vo.UserDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/medical/common")
public class CommonController {

    @Autowired
    private UserService userService;
    @Autowired
    private AttachmentCenterService attachmentCenterService;

    /**
     * 上传图片
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseObject upload(HttpServletRequest request) throws ServletRequestBindingException, IOException {

        // 获取当前用户
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if (loginUser == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录！");
        }
        UserDataVo currentUser = userService.getUserData(loginUser);

        String content = ServletRequestUtils.getRequiredStringParameter(request, "image");
        int i = content.indexOf(',');
        if (i > 0) {
            content = content.substring(i + 1);
        }
        byte[] image = Base64.getDecoder().decode(content);

        Attachment attachment = attachmentCenterService.addAttachment(currentUser.getUid(),
                AttachmentType.ONLINE,
                currentUser.getUid() + System.currentTimeMillis() + ".png", image,
                org.springframework.util.StringUtils.getFilenameExtension(currentUser.getUid()+ System.currentTimeMillis()  + ".png"),
                null);

        return ResponseObject.newSuccessResponseObject(attachment.getUrl());

    }


}
