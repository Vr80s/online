package com.xczh.consumer.market.service.impl;


import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OLAttachmentCenterServiceImpl implements OLAttachmentCenterService {


	//<img src="http://attachment-center.ixincheng.com:38080/data/picture/online/2017/09/25/09/e9dfe660b8744af3b39ebd92165fc639.jpg">
	private String attachmentCenterPath = "http://www.ixincheng.com:38080/";
	
	@Override
	public String upload(String createUserId, String projectName,
			String fileName, String contentType, byte[] fileData,
			String fileType, String ticket) {
		// TODO Auto-generated method stub
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("createUserId",createUserId);
		parameters.put("projectName",projectName);
		parameters.put("fileType",fileType);
		Map<String, String> cookies = new HashMap<String, String>();
		
		return HttpUtil.uploadFile(attachmentCenterPath+"/attachment/upload", parameters,
				"attachment", fileName, contentType, fileData);
	}

}
