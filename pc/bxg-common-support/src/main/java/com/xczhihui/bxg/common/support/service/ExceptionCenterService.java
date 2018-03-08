package com.xczhihui.bxg.common.support.service;

import java.util.Date;

import com.xczhihui.bxg.common.support.domain.BxgException;
import com.xczhihui.bxg.common.util.bean.TableVo;

/**
 * 异常中心接口
 * @author Haicheng Jiang
 */
public interface ExceptionCenterService {
	/**
	 * 记录异常信息
	 * @param createUserLoginName 当前操作用户的登录名
	 * @param projectName 业务系统，dual/univ/online……
	 * @param message 异常信息
	 * @param content 异常堆栈信息
	 * @param headerMsg 请求头信息
	 * @return 异常的id
	 */
	public String add(String createUserLoginName, String projectName, String message, String content,String headerMsg);
	/**
	 *  查询
	 * @param vo
	 * @return
	 */
	public TableVo list(TableVo vo);
	/**
	 * 获得一个异常信息
	 * @param id 异常的id
	 * @return
	 */
	public BxgException get(String id);
}
