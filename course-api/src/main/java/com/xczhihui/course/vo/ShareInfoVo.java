package com.xczhihui.course.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.xczhihui.common.util.XzStringUtils;
import com.xczhihui.common.util.enums.ShareType;


public class ShareInfoVo implements Serializable {

    private String shareId;              //主键id
    private String gradeName;       //标题
    private String name;            //标题
    private String smallImgPath;    //头像
    private String headImg;         //头像
    private String description;     //详情
    private String link;     		//连接
    private Integer type;     	//分享类型
    
    
    public void build(String returnOpenidUri) {
    	
    	Integer type  = this.getType();
    	String id  = this.getShareId();
    	String name =  this.getName();
    	String gradeName = this.getGradeName();
    	String details = this.getDescription();
    	
    	if(ShareType.HOST_SHARE.getCode().equals(type)) {
    		this.setName("中医好主播:"+name);
    	
    	}else if(ShareType.COURSE_SHARE.getCode()
    			.equals(type) || 
    			ShareType.ALBUM_SHARE.getCode()
    			.equals(type)) {
    		
    		this.setName("中医好课程:"+gradeName);
    	}
    	
    	if(StringUtils.isNotBlank(details)) {
    		details = XzStringUtils.delHTMLTag(details);
    		
    		this.setDescription(details);
    	}
    
    	this.setLink(returnOpenidUri+"/wx_share.html?shareType="+type+"&shareId="+id);
    
    }
    
	public String getShareId() {
		return shareId;
	}
	public void setShareId(String shareId) {
		this.shareId = shareId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSmallImgPath() {
		return smallImgPath;
	}
	public void setSmallImgPath(String smallImgPath) {
		this.smallImgPath = smallImgPath;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}