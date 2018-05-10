package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * 熊猫中医在线后台管理用户。
 *
 * @author Haicheng Jiang
 */
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"login_name"})})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Id
    private String id;

    /**
     * 实体是否删除
     */
    @Column(name = "is_delete")
    private boolean isDelete;

    /**
     * 创建人ID
     */
    @Column(name = "create_person")
    private String createPerson;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    private String salt;

    private String qq;

    private String description;
    /**
     * 昵称给其他用户看的名。
     */
    private String name;

    /**
     * 登录名
     */
    @Column(name = "login_name")
    private String loginName;

    private String password;

    /**
     * 性别
     */
    private int sex = 2;

    /**
     * email
     */
    private String email;

    /**
     * 电话号码
     */
    private String mobile;

    @Transient
    private Boolean anchor;

    @Transient
    private String roleNames;

    @Transient
    private String roleIds;

    @Transient
    private String createTimeStr;

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getSalt() {
        return salt;
    }

    
    
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getAnchor() {
        return anchor;
    }

    public void setAnchor(Boolean anchor) {
        this.anchor = anchor;
    }
}
