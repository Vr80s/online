package com.xczhihui.course.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.xczhihui.course.push.PushConst;
import com.xczhihui.course.push.XgMessage;
import com.xczhihui.course.push.XgMessageIOS;
import com.xczhihui.course.service.IXgPushService;

/**
 * @author hejiwei
 */
@Component
public class XgPushServiceImpl implements IXgPushService {

    private Long accessId;
    private String secretKey;

    private String generateSign(String method, String url, Map<String, Object> params) {
        List<Map.Entry<String, Object>> paramList = new ArrayList<>(params.entrySet());
        paramList.sort(Comparator.comparing(Map.Entry::getKey));
        StringBuilder paramBuilder = new StringBuilder();
        StringBuilder md5Builder = new StringBuilder();
        String md5Str = "";
        for (Map.Entry<String, Object> entry : paramList) {
            paramBuilder.append(entry.getKey()).append("=").append(entry.getValue().toString());
        }
        try {
            URL u = new URL(url);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            String s = method + u.getHost() + u.getPath() + paramBuilder.toString() + this.secretKey;
            byte[] bArr = s.getBytes("UTF-8");
            byte[] md5Value = md5.digest(bArr);
            BigInteger bigInt = new BigInteger(1, md5Value);
            md5Str = bigInt.toString(16);
            while (md5Str.length() < 32) {
                md5Str = "0" + md5Str;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return md5Str;
    }

    private JSONObject callRestful(String url, Map<String, Object> params, String method) {
        StringBuilder retBuilder = new StringBuilder();
        JSONObject jsonRet = null;
        String sign = generateSign(method, url, params);
        if (sign.isEmpty()) {
            return new JSONObject("{\"ret_code\":-1,\"err_msg\":\"generateSign error\"}");
        }
        params.put("sign", sign);
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod(method);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(3000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            StringBuilder param = new StringBuilder();
            for (String key : params.keySet()) {
                param.append(key).append("=").append(URLEncoder.encode(params.get(key).toString(), "UTF-8")).append("&");
            }
            conn.getOutputStream().write(param.toString().getBytes("UTF-8"));

            conn.getOutputStream().flush();
            conn.getOutputStream().close();
            InputStreamReader isr = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String temp;
            while ((temp = br.readLine()) != null) {
                retBuilder.append(temp);
            }
            br.close();
            isr.close();
            conn.disconnect();

            jsonRet = new JSONObject(retBuilder.toString());
        } catch (SocketTimeoutException e) {
            jsonRet = new JSONObject("{\"ret_code\":-1,\"err_msg\":\"call restful timeout\"}");
        } catch (Exception e) {
            jsonRet = new JSONObject("{\"ret_code\":-1,\"err_msg\":\"call restful error\"}");
        }
        return jsonRet;
    }

    private boolean validateToken(String token) {
        if (this.accessId >= PushConst.IOS_MIN_ID) {
            return token.length() == 64;
        }
        return (token.length() == 40) || (token.length() == 64);
    }

    private Map<String, Object> initParams() {
        Map<String, Object> params = new HashMap<>(2);
        params.put("access_id", this.accessId);
        params.put("timestamp", System.currentTimeMillis() / 1000L);
        return params;
    }

    private boolean validateMessageType(XgMessage xgMessage) {
        return this.accessId < PushConst.IOS_MIN_ID;
    }

    private boolean validateMessageType(XgMessageIOS message, int environment) {
        return (this.accessId >= PushConst.IOS_MIN_ID) && ((environment == PushConst.IOSENV_PROD) || (environment == PushConst.IOSENV_DEV));
    }

    @Override
    public JSONObject pushSingleDevice(String deviceToken, XgMessage xgMessage) {
        if (!validateMessageType(xgMessage)) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage type error!'}");
        }
        if (!xgMessage.isValid()) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage invalid!'}");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("access_id", this.accessId);
        params.put("expire_time", xgMessage.getExpireTime());
        params.put("send_time", xgMessage.getSendTime());
        params.put("multi_pkg", xgMessage.getMultiPkg());
        params.put("device_token", deviceToken);
        params.put("message_type", xgMessage.getType());
        params.put("message", xgMessage.toJson());
        params.put("timestamp", System.currentTimeMillis() / 1000L);

        return callRestful(PushConst.RESTAPI_PUSHSINGLEDEVICE, params, PushConst.HTTP_POST);
    }

    @Override
    public JSONObject pushSingleDevice(String deviceToken, XgMessageIOS message, int environment) {
        if (!validateMessageType(message, environment)) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage type or environment error!'}");
        }
        if (!message.isValid()) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage invalid!'}");
        }
        Map<String, Object> params = new HashMap<>(16);
        params.put("access_id", this.accessId);
        params.put("expire_time", message.getExpireTime());
        params.put("send_time", message.getSendTime());
        params.put("device_token", deviceToken);
        params.put("message_type", message.getType());
        params.put("message", message.toJson());
        params.put("timestamp", System.currentTimeMillis() / 1000L);
        params.put("environment", environment);
        if ((message.getLoopInterval() > 0) && (message.getLoopTimes() > 0)) {
            params.put("loop_interval", message.getLoopInterval());
            params.put("loop_times", message.getLoopTimes());
        }
        return callRestful(PushConst.RESTAPI_PUSHSINGLEDEVICE, params, PushConst.HTTP_POST);
    }

    @Override
    public JSONObject pushSingleAccount(int deviceType, String account, XgMessage xgMessage) {
        if (!validateMessageType(xgMessage)) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage type error!'}");
        }
        if (!xgMessage.isValid()) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage invalid!'}");
        }
        Map<String, Object> params = new HashMap<>(16);
        params.put("access_id", this.accessId);
        params.put("expire_time", xgMessage.getExpireTime());
        params.put("send_time", xgMessage.getSendTime());
        params.put("multi_pkg", xgMessage.getMultiPkg());
        params.put("account", account);
        params.put("message_type", xgMessage.getType());
        params.put("message", xgMessage.toJson());
        params.put("timestamp", System.currentTimeMillis() / 1000L);

        return callRestful(PushConst.RESTAPI_PUSHSINGLEACCOUNT, params, PushConst.HTTP_POST);
    }

    @Override
    public JSONObject pushSingleAccount(int deviceType, String account, XgMessageIOS message, int environment) {
        if (!validateMessageType(message, environment)) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage type or environment error!'}");
        }
        if (!message.isValid()) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage invalid!'}");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("access_id", this.accessId);
        params.put("expire_time", message.getExpireTime());
        params.put("send_time", message.getSendTime());
        params.put("device_type", deviceType);
        params.put("account", account);
        params.put("message_type", message.getType());
        params.put("message", message.toJson());
        params.put("timestamp", System.currentTimeMillis() / 1000L);
        params.put("environment", environment);

        return callRestful(PushConst.RESTAPI_PUSHSINGLEACCOUNT, params, PushConst.HTTP_POST);
    }

    @Override
    public JSONObject pushAccountList(int deviceType, List<String> accountList, XgMessage xgMessage) {
        if (!validateMessageType(xgMessage)) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage type error!'}");
        }
        if (!xgMessage.isValid()) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage invalid!'}");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("access_id", this.accessId);
        params.put("expire_time", xgMessage.getExpireTime());
        params.put("multi_pkg", xgMessage.getMultiPkg());
        params.put("device_type", deviceType);
        params.put("account_list", new JSONArray(accountList).toString());
        params.put("message_type", xgMessage.getType());
        params.put("message", xgMessage.toJson());
        params.put("timestamp", System.currentTimeMillis() / 1000L);

        return callRestful(PushConst.RESTAPI_PUSHACCOUNTLIST, params, PushConst.HTTP_POST);
    }

    @Override
    public JSONObject pushAccountList(int deviceType, List<String> accountList, XgMessageIOS message, int environment) {
        if (!validateMessageType(message, environment)) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage type or environment error!'}");
        }
        if (!message.isValid()) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage invalid!'}");
        }
        Map<String, Object> params = new HashMap<>(16);
        params.put("access_id", this.accessId);
        params.put("expire_time", message.getExpireTime());
        params.put("account_list", new JSONArray(accountList).toString());
        params.put("device_type", deviceType);
        params.put("message_type", message.getType());
        params.put("message", message.toJson());
        params.put("timestamp", System.currentTimeMillis() / 1000L);
        params.put("environment", environment);

        return callRestful(PushConst.RESTAPI_PUSHACCOUNTLIST, params, PushConst.HTTP_POST);
    }

    @Override
    public JSONObject pushAllDevice(int deviceType, XgMessage xgMessage) {
        if (!validateMessageType(xgMessage)) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage type error!'}");
        }
        if (!xgMessage.isValid()) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage invalid!'}");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("access_id", this.accessId);
        params.put("expire_time", xgMessage.getExpireTime());
        params.put("send_time", xgMessage.getSendTime());
        params.put("multi_pkg", xgMessage.getMultiPkg());
        params.put("device_type", deviceType);
        params.put("message_type", xgMessage.getType());
        params.put("message", xgMessage.toJson());
        params.put("timestamp", System.currentTimeMillis() / 1000L);
        if ((xgMessage.getLoopInterval() > 0) && (xgMessage.getLoopTimes() > 0)) {
            params.put("loop_interval", xgMessage.getLoopInterval());
            params.put("loop_times", xgMessage.getLoopTimes());
        }
        return callRestful(PushConst.RESTAPI_PUSHALLDEVICE, params, PushConst.HTTP_POST);
    }

    @Override
    public JSONObject pushAllDevice(int deviceType, XgMessageIOS message, int environment) {
        if (!validateMessageType(message, environment)) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage type or environment error!'}");
        }
        if (!message.isValid()) {
            return new JSONObject("{'ret_code':-1,'err_msg':'xgMessage invalid!'}");
        }
        Map<String, Object> params = new HashMap<>(16);
        params.put("access_id", this.accessId);
        params.put("expire_time", message.getExpireTime());
        params.put("send_time", message.getSendTime());
        params.put("device_type", deviceType);
        params.put("message_type", message.getType());
        params.put("message", message.toJson());
        params.put("timestamp", System.currentTimeMillis() / 1000L);
        params.put("environment", environment);
        if ((message.getLoopInterval() > 0) && (message.getLoopTimes() > 0)) {
            params.put("loop_interval", message.getLoopInterval());
            params.put("loop_times", message.getLoopTimes());
        }
        return callRestful(PushConst.RESTAPI_PUSHALLDEVICE, params, PushConst.HTTP_POST);
    }

    public Long getAccessId() {
        return accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
