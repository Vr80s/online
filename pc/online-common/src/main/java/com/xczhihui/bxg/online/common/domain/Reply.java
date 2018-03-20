package com.xczhihui.bxg.online.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xczhihui.bxg.common.support.domain.BasicEntity;

@Entity
@Table(name = "oe_reply")
public class Reply extends BasicEntity  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6921285561593657883L;
    
	@Column(name = "reply_content")
	private String replyContent;
	
	@OneToOne(targetEntity=OnlineUser.class,fetch = FetchType.EAGER)              //指定一对多关系
    @Cascade(value={CascadeType.ALL})        									 //设定级联关系
    @JoinColumn(name="reply_user",unique = true)   
	private OnlineUser onlineUser;
	
	//多对一，@JoinColumn与@column类似，指定映射的数据库字段  
	@ManyToOne(targetEntity = Criticize.class,fetch=FetchType.LAZY,optional = false)  
	@Cascade(value={CascadeType.ALL})
	@JoinColumn(name="criticize_id",updatable=false)
	@JsonIgnore //将不需要返回的属性上添加忽略
	private Criticize criticize; 
	
	public String getReplyContent() {
		return replyContent;
	}


	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public OnlineUser getOnlineUser() {
		return onlineUser;
	}


	public void setOnlineUser(OnlineUser onlineUser) {
		this.onlineUser = onlineUser;
	}


	public Criticize getCriticize() {
		return criticize;
	}


	public void setCriticize(Criticize criticize) {
		this.criticize = criticize;
	}
	
	
}
