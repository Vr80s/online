package com.xczhihui.wechat.course.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * ClassName: OfflineCity.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年1月14日<br>
 */
@TableName("oe_offline_city")
public class OfflineCity extends Model<OfflineCity> {

    private static final long serialVersionUID = 1L;

	private Integer id;

	/**
	 *创建人
	 */
	@TableField("create_person")
	private String createPerson;

	/**
	 *创建时间
	 */
	@TableField("create_time")
	private Date createTime;

	/**
	 *是否删除
	 */
	@TableField("is_delete")
	private boolean isDelete;

	/**
	 *名称
	 */
	@TableField("city_name")
	private String cityName;

	/**
	 *图标
	 */
	@TableField("icon")
	private String icon;

	/**
	 *排序
	 */
	@TableField("sort")
	private Integer sort;

	/**
	 *点击量
	 */
	@TableField("is_recommend")
	private Integer isRecommend;

	/**
	 *状态，0禁用，1启用
	 */
	@TableField("status")
	private Integer status;

	/**
	 *备注
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

	public void setDelete(boolean delete) {
		isDelete = delete;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
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
