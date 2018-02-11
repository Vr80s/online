package com.xczh.consumer.market.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.utils.HttpUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OLAttachmentCenterServiceImpl implements OLAttachmentCenterService {


	@Value("${attachmentCenterPath}")
	private String attachmentCenterPath;
	
	//<img src="http://attachment-center.ixincheng.com:38080/data/picture/online/2017/09/25/09/e9dfe660b8744af3b39ebd92165fc639.jpg">
	//private String attachmentCenterPath = "http://172.26.108.181:38080/";
	@Override
	public String upload(String createUserId, String projectName,
			String fileName, String contentType, byte[] fileData,
			String fileType, String ticket) {
		
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("createUserId",createUserId);
		parameters.put("projectName",projectName);
		parameters.put("fileType",fileType);
		
	 
		System.out.println("attachmentCenterPath : "+attachmentCenterPath+"/attachment/upload");
		String headImgPath=HttpUtil.uploadFile(attachmentCenterPath+"/attachment/upload", parameters,
				"attachment", fileName, contentType, fileData);
		JSONObject objectJson = JSONObject.parseObject(headImgPath);
		String url = objectJson.get("url").toString();
		System.out.println("url+++++++++++++++++"+url);
		return url;
	}

}
