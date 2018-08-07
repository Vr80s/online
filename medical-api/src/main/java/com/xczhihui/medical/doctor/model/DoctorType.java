package com.xczhihui.medical.doctor.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @ClassName: MobileProject
 * @Author: wangyishuai
 * @CreateDate: 2018/1/16 10:49
 **/
@TableName("doctor_type")
public class DoctorType extends Model<DoctorType> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    @TableField("title")
    private String title;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;
    
    /**
     * 创建人
     */
    @TableField("create_person")
    private String createPerson;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 是否删除
     */
    @TableField("is_delete")
    private boolean isDelete;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


    @Override
    protected Serializable pkVal() {
        return null;
    }


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public Integer getSort() {
		return sort;
	}


	public void setSort(Integer sort) {
		this.sort = sort;
	}


	public String getCreatePerson() {
		return createPerson;
	}


	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public boolean isDelete() {
		return isDelete;
	}


	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}

  
    
}