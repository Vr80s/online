package com.xczh.consumer.market.bean;
import java.io.Serializable;


/**
 * 
 * 微信信息表；
 * @author yanghui
 **/
@SuppressWarnings("serial")
public class WxcpClientUserWxMapping implements Serializable {

	/**关键字id;**/
	 private String wx_id;

	/**会员id;**/
	 private String client_id;

	/**用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息，只有openid和UnionID（在该公众号绑定到了微信开放平台账号时才有）。**/
	 private String subscribe;

	/**用户的标识，对当前公众号唯一**/
	 private String openid;
	 /**微信号名称**/
	 private String openname;
	/**用户的昵称**/
	 private String nickname;

	/**用户的性别，值为1时是男性，值为2时是女性，值为0时是未知**/
	 private String sex;

	/**用户所在城市**/
	 private String city;

	/**用户所在国家**/
	 private String country;

	/**用户所在省份**/
	 private String province;

	/**用户的语言，简体中文为zh_CN**/
	 private String language;

	/**用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。**/
	 private String headimgurl;

	/**用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间**/
	 private java.util.Date subscribe_time;

	/**只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）**/
	 private String unionid;

	/**公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注**/
	 private String remark;

	/**用户所在的分组ID **/
	 private String groupid;

	/**微信公众号**/
	 private String wx_public_id;

	/**微信公众号名称**/
	 private String wx_public_name;

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
}
