package com.xczh.consumer.market.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面跳转相关
 * @author Alex Wang
 */
public interface PageService {
	/**
	 * 检查是否存在此分享码
	 * @param shareCode
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public boolean needWriteShareCode(String shareCode, HttpServletRequest req) throws Exception;
}
