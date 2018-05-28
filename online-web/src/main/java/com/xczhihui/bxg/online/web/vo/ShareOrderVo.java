package com.xczhihui.bxg.online.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 分享订单结果类封装
 * @Author Fudong.Sun【】
 * @Date 2016/12/8 16:52
 */
public class ShareOrderVo {
    private String id;
    /**
     * 购买者用户ID
     */
    private String buy_user_id;
    /**
     * 购买者用户名
     */
    private String login_name;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date create_time;
    /**
     *  是否删除
     */
    private Boolean is_delete = false;
    /**
     *  排序字段
     */
    private Integer sort;
    /**
     *  订单号
     */
    private String order_no;
    /**
     *  佣金订单号
     */
    private String share_order_no;
    /**
     *  补贴对象(上级用户uuid)
     */
    private String target_user_id;
    /**
     *  课程id号
     */
    private Integer course_id = 0;
    /**
     *  课程名称
     */
    private String course_name;
    /**
     *  实际支付
     */
    private String actual_pay = "0.00";
    /**
     *  支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date pay_time;
    /**
     *  补贴级别 0:一级  1:二级  2:三级
     */
    private Integer level = 0;
    /**
     *  补贴金额
     */
    private String subsidies = "0.00";
    /**
     *  订单状态 0:未提现 1:已提现
     */
    private Integer order_status = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuy_user_id() {
        return buy_user_id;
    }

    public void setBuy_user_id(String buy_user_id) {
        this.buy_user_id = buy_user_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Boolean getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(Boolean is_delete) {
        this.is_delete = is_delete;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getTarget_user_id() {
        return target_user_id;
    }

    public void setTarget_user_id(String target_user_id) {
        this.target_user_id = target_user_id;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getActual_pay() {
        return actual_pay;
    }

    public void setActual_pay(String actual_pay) {
        this.actual_pay = actual_pay;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getSubsidies() {
        return subsidies;
    }

    public void setSubsidies(String subsidies) {
        this.subsidies = subsidies;
    }

    public Integer getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Integer order_status) {
        this.order_status = order_status;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getShare_order_no() {
        return share_order_no;
    }

    public void setShare_order_no(String share_order_no) {
        this.share_order_no = share_order_no;
    }
}
