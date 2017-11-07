package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.service.VersionService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.VersionCompareUtil;
import com.xczh.consumer.market.vo.VersionInfoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author liutao
 * @create 2017-09-07 20:58
 **/
@Controller
@RequestMapping("/bxg/version")
public class VersionController {


    @Autowired
    private VersionService versionService;

    @RequestMapping("checkUpdate")
    @ResponseBody
    public ResponseObject checkUpdate(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{


        String userVersion=req.getParameter("version");

        if(StringUtils.isBlank(userVersion)){
            return ResponseObject.newErrorResponseObject("参数[version]不能为空");
        }

        VersionInfoVo newVer=versionService.getNewVersion();
        VersionInfoVo defaultNoUpdateResult=new VersionInfoVo();
        defaultNoUpdateResult.setUpdate(false);
        if(newVer==null){
            return ResponseObject.newSuccessResponseObject(defaultNoUpdateResult);
        }


        //对比版本号
        String newVersion=newVer.getVersion();
        int diff = VersionCompareUtil.compareVersion(newVersion, userVersion);
        if (diff <= 0) {
            return ResponseObject.newSuccessResponseObject(defaultNoUpdateResult);
        }
        newVer.setUpdate(true);
        return ResponseObject.newSuccessResponseObject(newVer);
    }






}
