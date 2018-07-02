package com.wetcher;

import org.junit.Test;

import com.xczh.consumer.market.wxpay.util.HttpsRequest;

public class WechatSYSTest {


    //获取商户信息
    private static String OBTAIN_MERCHANT_INFORMATION = "https://api.weixin.qq.com/scan/merchantinfo/get?access_token=APPSECRET";

    //创建商品
    private static String CREATE_GOODS = "https://api.weixin.qq.com/scan/merchantinfo/get?access_token=APPSECRET";

    //获取二维码的ticket
    private static String QR_CODE_TICKET = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=APPSECRET";

    //通过ticket 获取二维码图片
    private static String QR_CODE_IMG = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKETS";


    //获取永久素材
    private static String GET_PERMANENT_MATERISL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=APPSECRET";

    /**
     * 获取二维码的票据
     * Description：
     *
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @Test
    public void testEWMTicket() {
        String url = QR_CODE_TICKET.replace("APPSECRET", "8_7oaxQHY9nyj_6ZSUFO36w5_e6USU07EIny2znh-r64vjjdSFPMxx3B_Cy69luIzRWG82edzWGkCP1YJum-8lrdnrzH3vTyrCJXQtnUsQz3qm_pOoE9R_6Yciov4X7SLmPDRTeUnraEorz0zKSYBaAGAJPS");
        String params = "{\"expire_seconds\":2592000,\"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"心承test\"}}}";
        String hehe = HttpsRequest.doHttpsPost(url, params);
    }
}
