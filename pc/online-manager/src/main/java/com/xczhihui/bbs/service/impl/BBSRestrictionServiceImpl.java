package com.xczhihui.bbs.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.xczhihui.bbs.dao.BBSUserStatusDao;
import com.xczhihui.bbs.service.BBSRestrictionService;
import com.xczhihui.message.dao.MessageDao;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.BBSUserStatus;
import com.xczhihui.bxg.online.common.domain.Message;

@Service
public class BBSRestrictionServiceImpl implements BBSRestrictionService {

	private static final String GAGS_MESSAGE = "你好，你的账号在bbs中被禁言";
	private static final String UN_GAGS_MESSAGE = "你好，你的账号在bbs中被取消禁言";
	private static final String BLACKLIST_MESSAGE = "你好，你的账号在bbs中被拉黑";
	private static final String CANCEL_BLACKLIST_MESSAGE = "你好，你的账号在bbs中被取消拉黑";

	private static final Object CHECK_LOCK = new Object();

	@Autowired
	private BBSUserStatusDao bbsUserStatusDao;
	@Autowired
	private MessageDao messageDao;

	@Override
	public TableVo list(TableVo tableVo) {
		Map<String, Object> params = new HashMap<>();
		Groups groups = Tools.filterGroup(tableVo.getsSearch());
		Group mobile = groups.findByName("mobile");
		if (mobile != null && mobile.getPropertyValue1() != null) {
			params.put("mobile", mobile.getPropertyValue1().toString());
		} else {
			params.put("mobile", null);
		}
		return tableVo.fetch(bbsUserStatusDao.list(params, tableVo));
	}

	@Override
	public ResponseObject updateGags(String mobile, boolean gags) {
		String onlineUserId = bbsUserStatusDao.findByMobile(mobile);
		if (onlineUserId == null) {
			return ResponseObject.newErrorResponseObject("用户不存在");
		}
		checkUserStatus(onlineUserId);
		String message;
		boolean updated;
		if (gags) {
			updated = bbsUserStatusDao.gags(onlineUserId);
			message = GAGS_MESSAGE;
		} else {
			updated = bbsUserStatusDao.unGags(onlineUserId);
			message = UN_GAGS_MESSAGE;
		}
		if (updated) {
			sendMessage(onlineUserId, message);
		}
		return ResponseObject.newSuccessResponseObject(null);
	}

	@Override
	public ResponseObject updateBlacklist(String mobile, boolean blacklist) {
		String onlineUserId = bbsUserStatusDao.findByMobile(mobile);
		if (onlineUserId == null) {
			return ResponseObject.newErrorResponseObject("用户不存在");
		} else {
			checkUserStatus(onlineUserId);
			String message;
			boolean updated;
			if (blacklist) {
				message = BLACKLIST_MESSAGE;
				updated = bbsUserStatusDao.addBlacklist(onlineUserId);
			} else {
				message = CANCEL_BLACKLIST_MESSAGE;
				updated = bbsUserStatusDao.cancelBlacklist(onlineUserId);
			}
			if (updated) {
				sendMessage(onlineUserId, message);
			}
		}
		return ResponseObject.newSuccessResponseObject(null);
	}

	private void checkUserStatus(String userId) {
		synchronized (CHECK_LOCK) {
			BBSUserStatus bbsUserStatus = bbsUserStatusDao
					.findOneEntitiyByProperty(BBSUserStatus.class, "id", userId);
			if (bbsUserStatus == null) {
				bbsUserStatus = new BBSUserStatus();
				bbsUserStatus.setBlacklist("0");
				bbsUserStatus.setGag("0");
				bbsUserStatus.setUserId(userId);
				bbsUserStatusDao.saveOrUpdate(bbsUserStatus);
			}
		}
	}

	private void sendMessage(String userId, String msgText) {
		Message message = new Message();
		message.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		message.setContext(msgText);
		message.setUserId(userId);
		message.setType(0);
		message.setStatus((short) 1);
		message.setReadstatus((short) 0);
		message.setCreatePerson(ManagerUserUtil.getId());
		message.setCreateTime(new Date());
		messageDao.save(message);
	}
}
