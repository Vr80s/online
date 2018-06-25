package com.xczh.consumer.market.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.xczh.consumer.market.utils.DateUtil;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczhihui.common.util.CodeUtil;

import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.sf.json.JSONObject;


/**
 * 微信信息表；
 *
 * @author yanghui
 **/
public class WxcpClientUserWxMapping implements Serializable {

    /**
     * 关键字id;
     **/
    private String wx_id;

    /**
     * 会员id;
     **/
    private String client_id;

    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息，只有openid和UnionID（在该公众号绑定到了微信开放平台帐号时才有）。
     **/
    private String subscribe;

    /**
     * 用户的标识，对当前公众号唯一
     **/
    private String openid;
    /**
     * 微信号名称
     **/
    private String openname;
    /**
     * 用户的昵称
     **/
    private String nickname;

    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     **/
    private String sex;

    /**
     * 用户所在城市
     **/
    private String city;

    /**
     * 用户所在国家
     **/
    private String country;

    /**
     * 用户所在省份
     **/
    private String province;

    /**
     * 用户的语言，简体中文为zh_CN
     **/
    private String language;

    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     **/
    private String headimgurl;

    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     **/
    private java.util.Date subscribe_time;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
     **/
    private String unionid;

    /**
     * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     **/
    private String remark;

    /**
     * 用户所在的分组ID
     **/
    private String groupid;

    /**
     * 微信公众号
     **/
    private String wx_public_id;

    /**
     * 微信公众号名称
     **/
    private String wx_public_name;


    /**
     * 户被打上的标签ID列表
     **/
    private String tagid_list;

    /**
     * 返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他
     **/
    private String subscribe_scene;

    /**
     * 二维码扫码场景（开发者自定义），id
     **/
    private String qr_scene;

    /**
     * 二维码扫码场景描述（开发者自定义）,字符串
     **/
    private String qr_scene_str;

    /**
     * 创建时间
     **/
    private java.util.Date create_time;

    /**
     * 最后一次更新时间
     **/
    private java.util.Date last_update_time;


    
    
    public WxcpClientUserWxMapping() {
    	
    }
    
    public WxcpClientUserWxMapping(JSONObject jsonObject) {
    	
         this.setWx_id(UUID.randomUUID().toString().replace("-", ""));
         this.setWx_public_id(WxPayConst.gzh_appid);
         this.setWx_public_name(WxPayConst.appid4name);
         this.setOpenid((String) jsonObject.get("openid"));
         this.setNickname((String) jsonObject.get("nickname"));
         this.setSex(String.valueOf(jsonObject.get("sex")));
         this.setLanguage((String) jsonObject.get("language"));
         this.setCity((String) jsonObject.get("city"));
         this.setProvince((String) jsonObject.get("province"));
         this.setCountry((String) jsonObject.get("country"));

         String headimgurl_ = (String) jsonObject.get("headimgurl");
         if (!StringUtils.isNotBlank(headimgurl_)) {
             headimgurl_ = WxPayConst.webdomain + "/web/images/defaultHead/18.png";
         }
         this.setHeadimgurl(headimgurl_);
         this.setUnionid((String) jsonObject.get("unionid"));
         this.setSubscribe(String.valueOf(jsonObject.get("subscribe")));
         this.setSubscribe_time(DateUtil.parseDate((String)jsonObject.get("subscribe_time"), DateUtil.FORMAT_CHINA_DAY_TIME));
         this.setRemark((String) jsonObject.get("remark"));
         this.setGroupid((String)jsonObject.get("groupid"));
         this.setSubscribe_scene((String) jsonObject.get("subscribe_scene"));
         this.setQr_scene((String)jsonObject.get("qr_scene"));
         this.setQr_scene_str((String) jsonObject.get("qr_scene_str"));
         
    }
    
    public WxcpClientUserWxMapping(WxMpUser wxMpUser) {
        
        this.setWx_id(CodeUtil.getRandomUUID());
        this.setWx_public_id(WxPayConst.gzh_appid);
        this.setWx_public_name(WxPayConst.appid4name);
        
        this.setOpenid(wxMpUser.getOpenId());
        this.setNickname(wxMpUser.getNickname());
        this.setSex(String.valueOf(wxMpUser.getSex()));
        this.setLanguage(wxMpUser.getLanguage());
        this.setCity(wxMpUser.getCity());
        this.setProvince(wxMpUser.getProvince());
        this.setCountry(wxMpUser.getCountry());
        this.setUnionid(wxMpUser.getUnionId());
    	
        if (!StringUtils.isNotBlank(headimgurl)) {
            headimgurl = WxPayConst.webdomain + "/web/images/defaultHead/18.png";
        }
        this.setHeadimgurl(headimgurl);
    }
    
    
    public void bulidUpdate(JSONObject jsonObject) {
    	
	    this.setGroupid((String)jsonObject.get("groupid"));
	    this.setSubscribe(String.valueOf(jsonObject.get("subscribe")));
        this.setSubscribe_scene((String) jsonObject.get("subscribe_scene"));
        this.setQr_scene((String)jsonObject.get("qr_scene"));
        this.setQr_scene_str((String) jsonObject.get("qr_scene_str"));
        this.setLast_update_time(new Date());
   }
    
    
    
    
    
    public String getWx_id() {
        return wx_id;
    }

    public void setWx_id(String wx_id) {
        this.wx_id = wx_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenname() {
        return openname;
    }

    public void setOpenname(String openname) {
        this.openname = openname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public java.util.Date getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(java.util.Date subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getWx_public_id() {
        return wx_public_id;
    }

    public void setWx_public_id(String wx_public_id) {
        this.wx_public_id = wx_public_id;
    }

    public String getWx_public_name() {
        return wx_public_name;
    }

    public void setWx_public_name(String wx_public_name) {
        this.wx_public_name = wx_public_name;
    }


    public String getTagid_list() {
        return tagid_list;
    }

    public void setTagid_list(String tagid_list) {
        this.tagid_list = tagid_list;
    }

    public String getSubscribe_scene() {
        return subscribe_scene;
    }

    public void setSubscribe_scene(String subscribe_scene) {
        this.subscribe_scene = subscribe_scene;
    }

    public String getQr_scene() {
        return qr_scene;
    }

    public void setQr_scene(String qr_scene) {
        this.qr_scene = qr_scene;
    }

    public String getQr_scene_str() {
        return qr_scene_str;
    }

    public void setQr_scene_str(String qr_scene_str) {
        this.qr_scene_str = qr_scene_str;
    }

    public java.util.Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(java.util.Date create_time) {
        this.create_time = create_time;
    }

    public java.util.Date getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(java.util.Date last_update_time) {
        this.last_update_time = last_update_time;
    }

    @Override
    public String toString() {
        return "WxcpClientUserWxMapping [wx_id=" + wx_id + ", client_id="
                + client_id + ", subscribe=" + subscribe + ", openid=" + openid
                + ", openname=" + openname + ", nickname=" + nickname
                + ", sex=" + sex + ", city=" + city + ", country=" + country
                + ", province=" + province + ", language=" + language
                + ", headimgurl=" + headimgurl + ", subscribe_time="
                + subscribe_time + ", unionid=" + unionid + ", remark="
                + remark + ", groupid=" + groupid + ", wx_public_id="
                + wx_public_id + ", wx_public_name=" + wx_public_name
                + ", tagid_list=" + tagid_list + ", subscribe_scene="
                + subscribe_scene + ", qr_scene=" + qr_scene
                + ", qr_scene_str=" + qr_scene_str + ", create_time="
                + create_time + ", last_update_time=" + last_update_time + "]";
    }


}
