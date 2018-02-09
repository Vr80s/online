package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.medical.anchor.service.IAssetService;
import com.xczhihui.medical.anchor.service.IUserBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 主播工作台资产控制层
 * @author yuruixin
 */
@RestController
@RequestMapping(value = "/anchor/info")
public class AnchorController {

    @Autowired
    private IAssetService assetService;


}
