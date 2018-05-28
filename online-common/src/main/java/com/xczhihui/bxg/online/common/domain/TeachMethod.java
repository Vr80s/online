package com.xczhihui.bxg.online.common.domain;

import com.xczhihui.common.support.domain.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 授课方式表实体类
 *  @author Rongcai Kang
 */
@Entity
@Table(name = "teach_method")
public class TeachMethod extends BasicEntity {
    /**
     * 授课方式名称
     */
    @Column(name = "name")
    private String  name;

    /**
     * 授课方式排序字段
     */
    @Column(name = "sort")
    private Integer sort;
    
    //备注
    @Column(name="remark")
    private String remark;
    
    //状态
    @Column(name="status")
    private boolean status;
    

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
    
}
