package com.xczhihui.bxg.online.manager.barrier.vo;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/12/22 14:36
 */
public class MarkBarrierVo {
    /**
     * 关卡ID号
     */
    private String id;
    /**
     * 关卡名
     */
    private String barrier_name;
    /**
     * 通关人数
     */
    private Integer pass_num;
    /**
     * 参加人数
     */
    private Integer join_num;
    /**
     * 班级人数
     */
    private Integer student_num;
    /**
     * 一次通关数
     */
    private Integer once_num;
    /**
     * 二次通关数
     */
    private Integer twice_num;
    /**
     * 平均通关率
     */
    private String pass_rate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarrier_name() {
        return barrier_name;
    }

    public void setBarrier_name(String barrier_name) {
        this.barrier_name = barrier_name;
    }

    public Integer getPass_num() {
        return pass_num;
    }

    public void setPass_num(Integer pass_num) {
        this.pass_num = pass_num;
    }

    public Integer getJoin_num() {
        return join_num;
    }

    public void setJoin_num(Integer join_num) {
        this.join_num = join_num;
    }

    public Integer getStudent_num() {
        return student_num;
    }

    public void setStudent_num(Integer student_num) {
        this.student_num = student_num;
    }

    public Integer getOnce_num() {
        return once_num;
    }

    public void setOnce_num(Integer once_num) {
        this.once_num = once_num;
    }

    public Integer getTwice_num() {
        return twice_num;
    }

    public void setTwice_num(Integer twice_num) {
        this.twice_num = twice_num;
    }

    public String getPass_rate() {
        return pass_rate;
    }

    public void setPass_rate(String pass_rate) {
        this.pass_rate = pass_rate;
    }
}
