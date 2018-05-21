package com.xczhihui.course.service;

import java.util.List;

import org.json.JSONObject;

import com.xczhihui.course.push.XgMessage;
import com.xczhihui.course.push.XgMessageIOS;

/**
 * 信鸽推送服务
 *
 * @author hejiwei
 */
public interface IXgPushService {

    /**
     * 根据设备号推送安卓单条消息
     *
     * @param deviceToken 设备号
     * @param xgMessage     消息
     * @return
     */
    JSONObject pushSingleDevice(String deviceToken, XgMessage xgMessage);

    /**
     * 根据设备号推送IOS单条消息
     *
     * @param deviceToken 设备号
     * @param message     消息
     * @param environment 环境
     * @return
     */
    JSONObject pushSingleDevice(String deviceToken, XgMessageIOS message, int environment);

    /**
     * 根据账号推送安卓单条消息
     *
     * @param account 账号
     * @param xgMessage 消息
     * @return
     */
    JSONObject pushSingleAccount(int deviceType, String account, XgMessage xgMessage);

    /**
     * 根据账号推送ios单条消息
     *
     * @param account     账号
     * @param message     消息
     * @param environment 环境
     * @return
     */
    JSONObject pushSingleAccount(int deviceType, String account, XgMessageIOS message, int environment);

    /**
     * 根据多个账号推送安卓消息
     *
     * @param accountList 账号列表
     * @param xgMessage     消息
     * @return
     */
    JSONObject pushAccountList(int deviceType, List<String> accountList, XgMessage xgMessage);

    /**
     * 根据账号列表推送ios消息
     *
     * @param accountList 账号列表
     * @param message     消息列表
     * @param environment 环境
     * @return
     */
    JSONObject pushAccountList(int deviceType, List<String> accountList, XgMessageIOS message, int environment);

    /**
     * 给全部的安卓用户推消息
     *
     * @param xgMessage 消息
     * @return
     */
    JSONObject pushAllDevice(int deviceType, XgMessage xgMessage);

    /**
     * 给全部的IOS用户推送消息
     *
     * @param message 消息
     * @param environment 环境
     * @return
     */
    JSONObject pushAllDevice(int deviceType, XgMessageIOS message, int environment);
}
