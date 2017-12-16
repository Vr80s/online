package com.xczhihui.medical.field.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@TableName("medical_field")
public class MedicalField extends Model<MedicalField> {

    private static final long serialVersionUID = 1L;

    /**
     * 医疗领域表
     */
	private String id;
    /**
     * 领域名称
     */
	private String name;
    /**
     * 父领域id
     */
//	@TableField("parent_id")
//	private String parentId;
    /**
     * 1已删除0未删除
     */
//	private Boolean deleted;
    /**
     * 启用状态
     */
//	private Boolean status;
    /**
     * 创建时间
     */
//	@TableField("create_time")
//	private Date createTime;
    /**
     * 创建人id
     */
//	@TableField("create_person")
//	private String createPerson;
    /**
     * 更新时间
     */
//	@TableField("update_time")
//	private Date updateTime;
    /**
     * 更新人id
     */
//	@TableField("update_person")
//	private String updatePerson;
    /**
     * 版本
     */
//	private String version;
    /**
     * 备注
     */
//	private String remark;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MedicalField{" +
			", id=" + id +
			", name=" + name +
//			", parentId=" + parentId +
//			", deleted=" + deleted +
//			", status=" + status +
//			", createTime=" + createTime +
//			", createPerson=" + createPerson +
//			", updateTime=" + updateTime +
//			", updatePerson=" + updatePerson +
//			", version=" + version +
//			", remark=" + remark +
			"}";
	}
}
