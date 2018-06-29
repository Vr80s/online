package com.xczh.consumer.market.wxpay.util;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.qq.connect.utils.QQConnectConfig;
import com.xczh.consumer.market.bean.WxcpWxTrans;
import com.xczh.consumer.market.wxpay.SignAbledBean;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.entity.PayInfo;
import com.xczh.consumer.market.wxpay.entity.SendRedPack;

/**
 * 微信支付相关通过用工具
 */
public class CommonUtil {

    public static String CreateNoncestr(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < length; i++) {
            Random rd = new Random();
            res += chars.indexOf(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    public static String CreateNoncestr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    /**
     * Description：随机生成的邮件啦
     *
     * @return String
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public static String CreateNoncestrXC() {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        String res = "";
        for (int i = 0; i < 11; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }


    public static String FormatQueryParaMap(HashMap<String, String> parameters) throws SDKRuntimeException {
        String buff = "";
        try {
            List<Entry<String, String>> infoIds = new ArrayList<Entry<String, String>>(parameters.entrySet());

            Collections.sort(infoIds, new Comparator<Entry<String, String>>() {
                @Override
                public int compare(Entry<String, String> o1, Entry<String, String> o2) {
                    return o1.getKey().compareTo(o2.getKey());
                }
            });

            for (int i = 0; i < infoIds.size(); i++) {
                Entry<String, String> item = infoIds.get(i);
                if (item.getKey() != "") {
                    buff += item.getKey() + "=" + URLEncoder.encode(item.getValue(), "utf-8") + "&";
                }
            }
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            throw new SDKRuntimeException(e.getMessage());
        }
        return buff;
    }

    /**
     * 获取签名
     *
     * @param payInfo
     * @return
     * @throws Exception
     */
    public static String getSign(SignAbledBean payInfo) throws Exception {
        String signTemp = orderParamByAscii(payInfo);
        String sign = MD5SignUtil.Sign(signTemp, WxPayConst.gzh_ApiKey);
        return sign;
    }

    /**
     * 为了坑爹的微信package
     *
     * @param payInfo
     * @return
     * @throws Exception
     * @author 周铭株 at 2016年5月16日 下午6:33:40
     */
    public static String getSign(Map<String, String> payInfo) throws Exception {

        Iterator<String> itor = payInfo.keySet().iterator();
        List<String> sort = new ArrayList<String>();
        while (itor.hasNext()) {
            sort.add(itor.next());
        }
        Collections.sort(sort);

        StringBuffer bf = new StringBuffer();
        Object fdvalue = null;
        boolean isft = true;// 是否第一次

        for (String fdname : sort) {
            fdvalue = payInfo.get(fdname);
            if (fdvalue != null && fdvalue.toString().length() > 0) {
                if (!isft) {
                    bf.append("&");
                } else {
                    isft = false;
                }
                bf.append(fdname + "=" + fdvalue);
            }
        }

        String sign = MD5SignUtil.Sign(bf.toString(), WxPayConst.gzh_ApiKey);
        return sign;
    }

    /**
     * 按acsii码排序请求串
     *
     * @param bean
     * @return
     */
    public static String orderParamByAscii(SignAbledBean bean) {

        StringBuffer bf = new StringBuffer();

        Field[] fields = bean.getClass().getDeclaredFields();
        List<String> sortlst = new ArrayList<String>();
        String name = null;
        for (Field field : fields) {
            name = field.getName();
            if ("serialVersionUID".equals(name)) {
                continue;
            }
            sortlst.add(name);
        }

        Collections.sort(sortlst);
        Object fdvalue = null;
        boolean isft = true;// 是否第一次
        for (String fdname : sortlst) {
            fdvalue = bean.getAttributeValue(fdname);
            if (fdvalue != null && fdvalue.toString().length() > 0) {
                if (!isft) {
                    bf.append("&");
                } else {
                    isft = false;
                }
                bf.append(fdname + "=" + fdvalue);
            }
        }
        return bf.toString();
    }


    public static String FormatBizQueryParaMap(HashMap<String, String> paraMap,
                                               boolean urlencode)
            throws SDKRuntimeException {

        String buff = "";
        try {

            List<Entry<String, String>> infoIds = new ArrayList<Entry<String, String>>(paraMap.entrySet());

            Collections.sort(infoIds, new Comparator<Entry<String, String>>() {
                @Override
                public int compare(Entry<String, String> o1, Entry<String, String> o2) {
                    return o1.getKey().compareTo(o2.getKey());
                }
            });

            for (int i = 0; i < infoIds.size(); i++) {
                Entry<String, String> item = infoIds.get(i);
                if (item.getKey() != "") {

                    String key = item.getKey();
                    String val = item.getValue();
                    if (urlencode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    buff += key.toLowerCase() + "=" + val + "&";

                }
            }

            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            throw new SDKRuntimeException(e.getMessage());
        }

        return buff;
    }

    public static boolean IsNumeric(String str) {
        if (str.matches("\\d *")) {
            return true;
        } else {
            return false;
        }
    }

    public static String ArrayToXml(HashMap<String, String> arr) {

        String xml = "<xml>";

        Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            String key = entry.getKey();
            String val = entry.getValue();
            if (IsNumeric(val)) {
                xml += "<" + key + ">" + val + "</" + key + ">";

            } else {
                xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
            }
        }

        xml += "</xml>";
        return xml;
    }

    /**
     * 获取微信用户信息：通过uniondid机制
     * TODO 此处因引入的微信公众号中间件，有些字段（subscribe_scene）未更新，所以保留以下方式调用
     * @param accessToken
     * @param openid
     * @return
     * @throws Exception
     */
    public static String getUserManagerGetInfo(String accessToken, String openid) throws Exception {
        String out = "";
        String in = WxPayConst.USERMANAGER_GETINFO_URL.replace("access_token=ACCESS_TOKEN", "access_token=" + accessToken).replace("openid=OPENID", "openid=" + openid);
        StringBuffer buffer = HttpsRequest.httpsRequest(in, "GET", out);
        return buffer.toString();
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(String xml) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        if (xml == null) {
            return map;
        }

        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }

        return map;
    }

    static SimpleDateFormat dateFormate = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 生成微信特定格式时间戳
     *
     * @return
     */
    public static String getTimeStamp() {
        return dateFormate.format(new Date());
    }

    public static Map<String, String> createSendRedPackOrderSign(SendRedPack redPack) {
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("nonce_str", redPack.getNonce_str());
        sParaTemp.put("mch_billno", redPack.getMch_billno());
        sParaTemp.put("mch_id", redPack.getMch_id());
        sParaTemp.put("wxappid", redPack.getWxappid());
        sParaTemp.put("nick_name", redPack.getNick_name());
        sParaTemp.put("send_name", redPack.getSend_name());
        sParaTemp.put("re_openid", redPack.getRe_openid());
        sParaTemp.put("total_amount", String.valueOf(redPack.getTotal_amount()));
        sParaTemp.put("min_value", String.valueOf(redPack.getMin_value()));
        sParaTemp.put("max_value", String.valueOf(redPack.getMax_value()));
        sParaTemp.put("total_num", String.valueOf(redPack.getTotal_num()));
        sParaTemp.put("wishing", redPack.getWishing());
        sParaTemp.put("client_ip", redPack.getClient_ip());
        sParaTemp.put("act_name", redPack.getAct_name());
        sParaTemp.put("remark", redPack.getRemark());

        Map<String, String> sPara = CommonUtil.paraFilter(sParaTemp);
        String prestr = CommonUtil.createLinkString(sPara);
        String key = "&key=" + WxPayConst.gzh_ApiKey;
        String sign = prestr + key;
        sign = MD5Util.MD5(sign.toString()).toUpperCase();//??return DigestUtils.md5Hex(sign.toString()).toUpperCase();        
        redPack.setSign(sign);
        sParaTemp.put("sign", sign);
        return sParaTemp;
    }

    public static Map<String, String> createWxTransOrderSign(WxcpWxTrans wxTrans) {

        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("trans_id", wxTrans.getTrans_id());
        sParaTemp.put("mch_appid", wxTrans.getMch_appid());
        sParaTemp.put("mchid", wxTrans.getMchid());
        //sParaTemp.put("device_info",wxTrans.getDevice_info());
        sParaTemp.put("nonce_str", wxTrans.getNonce_str());
        sParaTemp.put("sign", wxTrans.getSign());
        sParaTemp.put("partner_trade_no", wxTrans.getPartner_trade_no());
        sParaTemp.put("openid", wxTrans.getOpenid());
        sParaTemp.put("re_user_name", wxTrans.getRe_user_name());
        sParaTemp.put("amount", String.valueOf(wxTrans.getAmount()));
        sParaTemp.put("desc", wxTrans.getDesc());
        sParaTemp.put("spbill_create_ip", wxTrans.getSpbill_create_ip());
        //sParaTemp.put("return_code",wxTrans.getReturn_code());
        //sParaTemp.put("result_code",wxTrans.getResult_code());
        //sParaTemp.put("return_msg",wxTrans.getReturn_msg());
        //sParaTemp.put("payment_no",wxTrans.getPayment_no());
        //sParaTemp.put("payment_time",wxTrans.getPayment_time());

        Map<String, String> sPara = CommonUtil.paraFilter(sParaTemp);
        String prestr = CommonUtil.createLinkString(sPara);
        String key = "&key=" + WxPayConst.gzh_ApiKey;
        String sign = prestr + key;
        sign = MD5Util.MD5(sign.toString()).toUpperCase();//??return DigestUtils.md5Hex(sign.toString()).toUpperCase();        
        wxTrans.setSign(sign);
        sParaTemp.put("sign", sign);
        return sParaTemp;
    }

    public static Map<String, String> sendTrans(WxcpWxTrans wxTrans) throws Exception {

        Map<String, String> sParaTemp = createWxTransOrderSign(wxTrans);

        XMLUtil xmlUtil = new XMLUtil();
        xmlUtil.xstream().alias("xml", wxTrans.getClass());
        String xml = xmlUtil.xstream().toXML(wxTrans);
        xml = xml.replace("com.xczh.consumer.market.bean.WxcpWxTrans", "xml");
        xml = xml.replace("__", "_");

        String sendWxTranskUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
        String response = HttpsRequest.sslPost(sendWxTranskUrl, xml);
        if (response == null) {
            response = "";
        }
        response = response.replace("__", "_");

        Map<String, String> responseMap = xmlUtil.parseXml(response);
        return responseMap;
    }

    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || "".equals(value) || "sign".equalsIgnoreCase(key)
                    || "sign_type".equalsIgnoreCase(key)) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    //send red pack request；
    public static Map<String, String> sendRedPack(SendRedPack redPack) throws Exception {

        Map<String, String> sParaTemp = createSendRedPackOrderSign(redPack);//String sign = createSendRedPackOrderSign(redPack);//redPack.setSign(sign);

        XMLUtil xmlUtil = new XMLUtil();
        xmlUtil.xstream().alias("xml", redPack.getClass());
        String xml = xmlUtil.xstream().toXML(redPack);
        xml = xml.replace("com.xczh.consumer.market.wxpay.entity.SendRedPack", "xml");
        xml = xml.replace("__", "_");

        String sendRedPackUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
        String response = HttpsRequest.sslPost(sendRedPackUrl, xml);
        if (response == null) {
            response = "";
        }
        response = response.replace("__", "_");

        Map<String, String> responseMap = xmlUtil.parseXml(response);
        return responseMap;
    }

    //	public static String getSign(SignAbledBean payInfo) throws Exception {
//		String signTemp = orderParamByAscii(payInfo);
//		String sign = MD5SignUtil.Sign(signTemp, WxPayConst.APP_KEY);
//		return sign;
//	}
    public static String getSign(SignAbledBean payInfo, String tradeType) throws SDKRuntimeException {
        String signTemp = orderParamByAscii(payInfo);
        String sign = "";
        if (PayInfo.TRADE_TYPE_APP.equals(tradeType)) {
            sign = MD5SignUtil.Sign(signTemp, WxPayConst.app_ApiKey);
        } else {
            sign = MD5SignUtil.Sign(signTemp, WxPayConst.gzh_ApiKey);
        }
        return sign;
    }

    //app二次签名
    public static Map<String, String> getSignER(Map<String, String> map) throws SDKRuntimeException {


        String timestamp = System.currentTimeMillis() / 1000 + "";

        String str = "appid=" + map.get("appid") + "&noncestr=" + map.get("nonce_str") + ""
                + "&package=Sign=WXPay"
                + "&partnerid=" + map.get("mch_id") + "&prepayid=" + map.get("prepay_id") + ""
                + "&timestamp=" + timestamp + "";
        String sign = "";
        sign = MD5SignUtil.Sign(str, WxPayConst.app_ApiKey);
        map.put("sign", sign);
        map.put("timestamp", timestamp);
        return map;
    }
}
