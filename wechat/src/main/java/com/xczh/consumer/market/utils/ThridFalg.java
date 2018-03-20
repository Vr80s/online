package com.xczh.consumer.market.utils;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

/**
 * 微信第三方登录标识   -- 用于存储unionId 和openId
 * ClassName: ThridFalg.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月26日<br>
 */
public class ThridFalg implements Serializable,Writable {
	
	private static final long serialVersionUID = 7175888406581656483L;
	
	private String unionId;
	private String openId;
	private String nickName;
	private String headImg;
	
	public String getUnionId() {
		return unionId;
	}
	
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	
	@Override
	public String toString() {
		return "ThridFalg [unionId=" + unionId + ", openId=" + openId
				+ ", nickName=" + nickName + ", headImg=" + headImg + "]";
	}

	@Override
	public void write(DataOutput out) throws IOException {
		
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		
	}
}
