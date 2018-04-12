package com.xczhihui.wechat.course.model;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 熊猫中医在线网站用户。
 * @author Haicheng Jiang
 */
@TableName("weibo_client_user_mapping")
public class WeiboClientUserMapping extends  Model<WeiboClientUserMapping>{
	
	private static final long serialVersionUID = 1L;
	
	@TableField("id")
    private String id;

	@TableField("uid")
    private String uid;
	
	@TableField("user_id")
    private String userId;

	@TableField("screen_name")
    private String screenName;

	@TableField("name")
    private String name;

	@TableField("province")
    private Integer province;

	@TableField("city")
    private Integer city;

	@TableField("location")
    private String location;

	@TableField("description")
    private String description;

	@TableField("url")
    private String url;

	@TableField("profile_image_url")
    private String profileImageUrl;

	@TableField("profile_url")
    private String profileUrl;

	@TableField("user_domain")
    private String userDomain;
    
	@TableField("gender")
    private String gender;

	@TableField("followers_count")
    private Integer followersCount;

	@TableField("friends_count")
    private Integer friendsCount;

	@TableField("statuses_count")
    private Integer statusesCount;

	@TableField("favourites_count")
    private Integer favouritesCount;

	@TableField("created_at")
    private String createdAt;

	@TableField("verified")
    private String verified;

	@TableField("remark")
    private String remark;

	@TableField("lang")
    private String lang;

	@TableField("weihao")
    private String weihao;
    
	@TableField("create_time")
    private Date createTime;
    
	public WeiboClientUserMapping(){
		
	}
	
	public WeiboClientUserMapping(JSONObject json) {
		 if (json != null) {
			 try {
				uid = json.getString("idstr");
		        screenName = json.getString("screen_name");
		        name = json.getString("name");
		        province = json.getInt("province");
		        city = json.getInt("city");
		        location = json.getString("location");
		        description = json.getString("description");
		        url = json.getString("url");
		        profileImageUrl = json.getString("profile_image_url");
		        userDomain = json.getString("domain");
		        gender = json.getString("gender");
		        followersCount = json.getInt("followers_count");
		        friendsCount = json.getInt("friends_count");
		        favouritesCount = json.getInt("favourites_count");
		        statusesCount = json.getInt("statuses_count");
		        createdAt = json.getString("created_at");
		        verified = json.getString("verified");
		        if (!json.getString("remark").isEmpty()) {
		          remark = json.getString("remark");
		        }
		        lang = json.getString("lang");
		        weihao = json.getString("weihao");
			} catch (Exception jsone) {
				jsone.printStackTrace();
			}
	        
		}
	}


	public String getId() {
        return id;
    }
    

	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}


	public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName == null ? null : screenName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl == null ? null : profileImageUrl.trim();
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl == null ? null : profileUrl.trim();
    }


    public String getUserDomain() {
		return userDomain;
	}

	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}

	public String getWeihao() {
        return weihao;
    }

    public void setWeihao(String weihao) {
        this.weihao = weihao == null ? null : weihao.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(Integer friendsCount) {
        this.friendsCount = friendsCount;
    }

    public Integer getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(Integer statusesCount) {
        this.statusesCount = statusesCount;
    }

    public Integer getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(Integer favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt == null ? null : createdAt.trim();
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified == null ? null : verified.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang == null ? null : lang.trim();
    }
    

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	

	@Override
	public String toString() {
		return "WeiboClientUserMapping [id=" + id + ", uid=" + uid
				+ ", userId=" + userId + ", screenName=" + screenName
				+ ", name=" + name + ", province=" + province + ", city="
				+ city + ", location=" + location + ", description="
				+ description + ", url=" + url + ", profileImageUrl="
				+ profileImageUrl + ", profileUrl=" + profileUrl
				+ ", userDomain=" + userDomain + ", gender=" + gender
				+ ", followersCount=" + followersCount + ", friendsCount="
				+ friendsCount + ", statusesCount=" + statusesCount
				+ ", favouritesCount=" + favouritesCount + ", createdAt="
				+ createdAt + ", verified=" + verified + ", remark=" + remark
				+ ", lang=" + lang + ", weihao=" + weihao + ", createTime="
				+ createTime + "]";
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}
}