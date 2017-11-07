package com.xczhihui.bxg.online.web.vo;

import java.util.Date;

/**
 * 关卡调用的结果封装类
 * @author Rongcai Kang
 */
public class BarrierVo {

    /**
     * 关卡id
     */
    private String id;

    /**
     * 关卡名
     */
    private String name;

    /**
     * 关卡所在知识点id
     */
    private String kpoint_id;

    /**
     * 锁状态 1:解锁 0:未解锁
     */
    private Integer lock_status;

    /**
     * 关卡通关状态:0:未通关  1:已通关
     */
    private Integer barrier_status;

    /**
     * 关卡总分数
     */
    private Integer total_score;

    /**
     * 通关分数
     */
    private Integer pass_score_percent;

    /**
     * 通关时常
     */
    private Integer limit_time;

    /**
     * 关卡总题数
     */
    private Integer sum_total;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 是否通过 0:未通过 1:已通过
     */
    private Integer result;

    /**
     * 使用时长 单位：分
     */
    private  Integer use_time;

    /**
     * 提交时间
     */
    private Date submit_time;

    /**
     * 已通关总人数
     */
    private Integer number_pass;

    /**
     * 排名
     */
    private Integer rank;

    /**
     * 当前用时
     */
    private Integer current_usetime;


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

    public Integer getBarrier_status() {
        return barrier_status;
    }

    public void setBarrier_status(Integer barrier_status) {
        this.barrier_status = barrier_status;
    }

    public String getKpoint_id() {
        return kpoint_id;
    }

    public void setKpoint_id(String kpoint_id) {
        this.kpoint_id = kpoint_id;
    }

    public Integer getLock_status() {
        return lock_status;
    }

    public void setLock_status(Integer lock_status) {
        this.lock_status = lock_status;
    }

    public Integer getTotal_score() {
        return total_score;
    }

    public void setTotal_score(Integer total_score) {
        this.total_score = total_score;
    }

    public Integer getPass_score_percent() {
        return pass_score_percent;
    }

    public void setPass_score_percent(Integer pass_score_percent) {
        this.pass_score_percent = pass_score_percent;
    }

    public Integer getLimit_time() {
        return limit_time;
    }

    public void setLimit_time(Integer limit_time) {
        this.limit_time = limit_time;
    }

    public Integer getSum_total() {
        return sum_total;
    }

    public void setSum_total(Integer sum_total) {
        this.sum_total = sum_total;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getUse_time() {
        return use_time;
    }

    public void setUse_time(Integer use_time) {
        this.use_time = use_time;
    }

    public Date getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(Date submit_time) {
        this.submit_time = submit_time;
    }

    public Integer getNumber_pass() {
        return number_pass;
    }

    public void setNumber_pass(Integer number_pass) {
        this.number_pass = number_pass;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getCurrent_usetime() {
        return current_usetime;
    }

    public void setCurrent_usetime(Integer current_usetime) {
        this.current_usetime = current_usetime;
    }
}
