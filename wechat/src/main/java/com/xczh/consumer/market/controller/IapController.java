package com.xczh.consumer.market.controller;

import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.utils.ResponseObject;

@Controller
@RequestMapping("/bxg/iap")
public class IapController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(IapController.class);

    /**
     * 接收iOS端发过来的购买凭证
     * @param receipt
     */
    @ResponseBody
    @RequestMapping("/setIapCertificate")
    public Object setIapCertificate(String receipt,String userId,String actualPrice,String version) throws SQLException {

    	LOGGER.info("老版本方法----》》》》setIapCertificate");
    	
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }
}