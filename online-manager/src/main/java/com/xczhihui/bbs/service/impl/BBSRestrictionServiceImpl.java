package com.xczhihui.bbs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bbs.dao.BBSUserStatusDao;
import com.xczhihui.bbs.service.BBSRestrictionService;
import com.xczhihui.bxg.online.common.domain.BBSUserStatus;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.MessageTypeEnum;
import com.xczhihui.common.util.enums.RouteTypeEnum;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.dao.OnlineUserDao;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

/**
 * @author hejiwei
 */
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
    private OnlineUserDao onlineUserDao;
    @Autowired
    private ICommonMessageService commonMessageService;

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
    public ResponseObject updateGags(List<String> userIds, boolean gags) {
        for (String userId : userIds) {
            if (onlineUserDao.find(userId) != null) {
                checkUserStatus(userId);
                String message;
                boolean updated;
                if (gags) {
                    updated = bbsUserStatusDao.gags(userId);
                    message = GAGS_MESSAGE;
                } else {
                    updated = bbsUserStatusDao.unGags(userId);
                    message = UN_GAGS_MESSAGE;
                }
                if (updated) {
                    commonMessageService.saveMessage(
                            new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal())
                                    .buildWeb(message)
                                    .build(userId, RouteTypeEnum.NONE, ManagerUserUtil.getId()));
                }
            }
        }

        return ResponseObject.newSuccessResponseObject("操作成功");
    }

    @Override
    public ResponseObject updateBlacklist(List<String> userIds, boolean blacklist) {
        for (String userId : userIds) {
            if (onlineUserDao.find(userId) != null) {
                checkUserStatus(userId);
                String message;
                boolean updated;
                if (blacklist) {
                    message = BLACKLIST_MESSAGE;
                    updated = bbsUserStatusDao.addBlacklist(userId);
                } else {
                    message = CANCEL_BLACKLIST_MESSAGE;
                    updated = bbsUserStatusDao.cancelBlacklist(userId);
                }
                if (updated) {
                    commonMessageService.saveMessage(
                            new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal())
                                    .buildWeb(message)
                                    .build(userId, RouteTypeEnum.NONE, ManagerUserUtil.getId()));
                }
            }
        }
        return ResponseObject.newSuccessResponseObject("操作成功");
    }

    @Override
    public ResponseObject isGagsOrBlacklist(List<String> userIds, Integer gagsOrBlacklist) {
        for (String userId : userIds) {
            BBSUserStatus bbsUserStatus = bbsUserStatusDao
                    .findOneEntitiyByProperty(BBSUserStatus.class, "id", userId);
            String blacklist = bbsUserStatus.getBlacklist();
            String gag = bbsUserStatus.getGag();
            if(gagsOrBlacklist == 1){
                if(blacklist.equals("1")){
                    return ResponseObject.newSuccessResponseObject(true);
                }
            } else {
                if(gag.equals("1")){
                    return ResponseObject.newSuccessResponseObject(true);
                }
            }
        }
        return ResponseObject.newSuccessResponseObject(false);
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
}
