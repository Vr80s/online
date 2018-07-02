package com.xczhihui.user.center.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2018-05-14
 */
@TableName("user_coin")
public class UserCoin extends Model<UserCoin> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户-代币表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private String userId;
    /**
     * 充值余额
     */
    private BigDecimal balance;
    /**
     * 赠送余额
     */
    @TableField("balance_give")
    private BigDecimal balanceGive;
    /**
     * 主播用户收到的打赏礼物余额
     */
    @TableField("balance_reward_gift")
    private BigDecimal balanceRewardGift;
    /**
     * 主播用户人民币余额
     */
    private BigDecimal rmb;
    private String version;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    /**
     * 1有效0无效
     */
    private Boolean status;
    /**
     * 1已删除0未删除
     */
    private Boolean deleted;
    private String remark;


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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalanceGive() {
        return balanceGive;
    }

    public void setBalanceGive(BigDecimal balanceGive) {
        this.balanceGive = balanceGive;
    }

    public BigDecimal getBalanceRewardGift() {
        return balanceRewardGift;
    }

    public void setBalanceRewardGift(BigDecimal balanceRewardGift) {
        this.balanceRewardGift = balanceRewardGift;
    }

    public BigDecimal getRmb() {
        return rmb;
    }

    public void setRmb(BigDecimal rmb) {
        this.rmb = rmb;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserCoin{" +
                ", id=" + id +
                ", userId=" + userId +
                ", balance=" + balance +
                ", balanceGive=" + balanceGive +
                ", balanceRewardGift=" + balanceRewardGift +
                ", rmb=" + rmb +
                ", version=" + version +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                ", deleted=" + deleted +
                ", remark=" + remark +
                "}";
    }
}
