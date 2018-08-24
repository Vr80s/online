/**  
* <p>Title: MinuteTaskMessageVo.java</p>  
* <p>Description: </p>  
* @author yangxuan@ixincheng.com  
* @date 2018年8月23日 
*/  
package com.xczhihui.common.util.bean;

import java.io.Serializable;
import java.util.Date;

/**
* @ClassName: MinuteTaskMessageVo
* @Description: 每分钟定时任务保存在redis中数据的bean
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年8月23日
*
*/

public class MinuteTaskMessageVo implements  Serializable{

	private static final long serialVersionUID = 8080612633895345818L;
	
	
	private String messageType;
	//类型唯一标识符   --如果通知过的可以删除操作
	private String typeUnique;
	
	private Date startTime;
	private Date endTime;
	
	private String doctorUserId;
	private String userId;
	
	private String doctorName;
	
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	public String getTypeUnique() {
		return typeUnique;
	}
	public void setTypeUnique(String typeUnique) {
		this.typeUnique = typeUnique;
	}
	public String getDoctorUserId() {
		return doctorUserId;
	}
	public void setDoctorUserId(String doctorUserId) {
		this.doctorUserId = doctorUserId;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	@Override
	public String toString() {
		return "MinuteTaskMessageVo [messageType=" + messageType + ", typeUnique=" + typeUnique + ", startTime="
				+ startTime + ", endTime=" + endTime + ", doctorUserId=" + doctorUserId + ", userId=" + userId
				+ ", doctorName=" + doctorName + "]";
	}
	
	
}
