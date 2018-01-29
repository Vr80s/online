package com.xczhihui.medical.anchor.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
  * @ClassName: UserBank
  * @Description: 用户银行卡
  * @Author:  wangyishuai
  * @email: 15210815880@163.com
  * @CreateDate: 2018/1/25
  **/
@TableName("oe_user_bank")
public class UserBank extends Model<UserBank> {

    private static final long serialVersionUID = 1L;

	private Integer id;

	/**
	 *用户
	 */
	@TableField("user_id")
	private String userId;

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
	 *用户真实姓名
	 */
	@TableField("acct_name")
	private String acctName;

	/**
	 *身份证号
	 */
	@TableField("cert_id")
	private String certId;

	/**
	 *银行卡号
	 */
	@TableField("acct_pan")
	private String acctPan;

	/**
	 *开卡使用的证件类型；01:身份证，目前只支持身份证
	 */
	@TableField("cert_type")
	private String certType;
	/**
	 * 所属银行
	 */
	@TableField("bank_name")
	private String bankName;
	/**
	 *是否默认
	 */
	@TableField("is_default")
	private boolean isDefault;
	/**
	 * 排序
	 */
	@TableField("sort")
	private Integer sort;
	/**
	 * 电话号码
	 */
	@TableField("tel")
	private String tel;


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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public String getAcctPan() {
		return acctPan;
	}

	public void setAcctPan(String acctPan) {
		this.acctPan = acctPan;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean aDefault) {
		isDefault = aDefault;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
