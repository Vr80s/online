package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "wxcp_client_user_wx_mapping")
public class WechatUser implements Serializable{
	
	private static final long serialVersionUID = 8080612633895345828L;
	
	@Id
	@Column(name = "wx_id", nullable = false)
    private String wxId;

	@Column(name = "client_id")
    private String clientId;

	@Column(name = "subscribe")
    private String subscribe;

	@Column(name = "openid")
    private String openid;

	@Column(name = "openname")
    private String openname;

	@Column(name = "nickname")
    private String nickname;

	@Column(name = "sex")
    private String sex;

	@Column(name = "city")
    private String city;

	@Column(name = "country")
    private String country;

	@Column(name = "province")
    private String province;

	@Column(name = "language")
    private String language;

	@Column(name = "headimgurl")
    private String headimgurl;

	@Column(name = "subscribe_time")
    private Date subscribeTime;

	@Column(name = "unionid")
    private String unionid;

	@Column(name = "remark")
    private String remark;

	@Column(name = "groupid")
    private String groupid;

	@Column(name = "wx_public_id")
    private String wxPublicId;

	@Column(name = "wx_public_name")
    private String wxPublicName;

	@Column(name = "tagid_list")
    private String tagidList;

	@Column(name = "subscribe_scene")
    private String subscribeScene;

	@Column(name = "qr_scene")
    private String qrScene;

	@Column(name = "qr_scene_str")
    private String qrSceneStr;

	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "last_update_time")
	private Date lastUpdateTime;
	
	@Transient
	private String channelName;

	@Transient
	private String loginName;
	
    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId == null ? null : wxId.trim();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe == null ? null : subscribe.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getOpenname() {
        return openname;
    }

    public void setOpenname(String openname) {
        this.openname = openname == null ? null : openname.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl == null ? null : headimgurl.trim();
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid == null ? null : unionid.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    public String getWxPublicId() {
        return wxPublicId;
    }

    public void setWxPublicId(String wxPublicId) {
        this.wxPublicId = wxPublicId == null ? null : wxPublicId.trim();
    }

    public String getWxPublicName() {
        return wxPublicName;
    }

    public void setWxPublicName(String wxPublicName) {
        this.wxPublicName = wxPublicName == null ? null : wxPublicName.trim();
    }

    public String getTagidList() {
        return tagidList;
    }

    public void setTagidList(String tagidList) {
        this.tagidList = tagidList == null ? null : tagidList.trim();
    }

    public String getSubscribeScene() {
        return subscribeScene;
    }

    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene == null ? null : subscribeScene.trim();
    }

    public String getQrScene() {
        return qrScene;
    }

    public void setQrScene(String qrScene) {
        this.qrScene = qrScene == null ? null : qrScene.trim();
    }

    public String getQrSceneStr() {
        return qrSceneStr;
    }

    public void setQrSceneStr(String qrSceneStr) {
        this.qrSceneStr = qrSceneStr == null ? null : qrSceneStr.trim();
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
    
    
    
}