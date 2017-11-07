package com.xczh.consumer.market.service;


public interface OLAttachmentCenterService {

	
	public String upload(String createUserId, String projectName, String fileName, String contentType, byte[] fileData, String fileType, String ticket);
}
