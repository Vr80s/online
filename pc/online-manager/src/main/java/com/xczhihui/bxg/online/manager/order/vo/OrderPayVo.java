package com.xczhihui.bxg.online.manager.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by rongcai  Kang  on 2016/11/8.
 */
public class OrderPayVo implements Serializable {

    private String id;
    /**
     * 订单号
     */
    private String order_no;
    /**
     * 优惠方式
     */
    private String preferenty_way;
    /**
     *优惠金额
     */
    private String preferenty_money;
    /**
     * 课程id
     */
    private Integer course_id;

    /**
     * 实际支付
     */
    private String actual_pay;

    /**
     * 购买者
     */
    private String  purchaser;

    /**
     * 课程名称
     */
    private String  course_name;

    /**
     * 创建人登录名
     */
    private String create_person;

    /**
     * 支付状态:  0:未支付 1:已支付 2:已关闭
     */
    private Integer order_status;

    /**
     * 原价
     */
    private String  original_cost;

    /**
     * 课程图片
     */
    private String smallimg_path;

    /**
     * 支付账号
     */
    private  String   pay_account;

    /**
     * 当前登录用户
     */
    private String  user_id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
//    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss", timezone = "GMT+8")
    private Date create_time;
    
    /**
	 * 订单来源，0官网（本系统），1分销系统，2线下（刷数据）
	 */
    private Integer order_from;

    /**
     * 订单详情：订单中包含的所有课程
     */
    private List<Map<String,Object>> orderDetail;
    /**
     * 班级id
     */
    private String class_id;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPreferenty_way() {
        return preferenty_way;
    }

    public void setPreferenty_way(String preferenty_way) {
        this.preferenty_way = preferenty_way;
    }

    public String getPreferenty_money() {
        return preferenty_money;
    }

    public void setPreferenty_money(String preferenty_money) {
        this.preferenty_money = preferenty_money;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public String getActual_pay() {
        return actual_pay;
    }

    public void setActual_pay(String actual_pay) {
        this.actual_pay = actual_pay;
    }

    public String getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Integer getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Integer order_status) {
        this.order_status = order_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_person() {
        return create_person;
    }

    public void setCreate_person(String create_person) {
        this.create_person = create_person;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getOriginal_cost() {
        return original_cost;
    }

    public void setOriginal_cost(String original_cost) {
        this.original_cost = original_cost;
    }

    public String getSmallimg_path() {
        return smallimg_path;
    }

    public void setSmallimg_path(String smallimg_path) {
        this.smallimg_path = smallimg_path;
    }

    public String getPay_account() {
        return pay_account;
    }

    public void setPay_account(String pay_account) {
        this.pay_account = pay_account;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

	public Integer getOrder_from() {
		return order_from;
	}

	public void setOrder_from(Integer order_from) {
		this.order_from = order_from;
	}

    public List<Map<String, Object>> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<Map<String, Object>> orderDetail) {
        this.orderDetail = orderDetail;
    }

	public String getClass_id() {
		return class_id;
	}

	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
}
