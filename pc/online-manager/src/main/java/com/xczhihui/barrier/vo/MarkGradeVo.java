package com.xczhihui.barrier.vo;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/12/22 14:36
 */
public class MarkGradeVo {
    /**
     * 班级ID号
     */
    private Integer id;
    /**
     * 班级名
     */
    private String grade_name;

    /**
     * 课程名
     */
    private String course_name;
    /**
     * 课程ID号
     */
    private Integer course_id;
    /**
     * 学生数
     */
    private Integer student_count;
    /**
     * 关卡数
     */
    private Integer barrier_count;
    /**
     * 通关人数
     */
    private Integer pass_num;
    /**
     * 参加人数
     */
    private Integer join_num;
    /**
     * 平均通关率
     */
    private String pass_rate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public Integer getStudent_count() {
        return student_count;
    }

    public void setStudent_count(Integer student_count) {
        this.student_count = student_count;
    }

    public Integer getBarrier_count() {
        return barrier_count;
    }

    public void setBarrier_count(Integer barrier_count) {
        this.barrier_count = barrier_count;
    }

    public String getPass_rate() {
        return pass_rate;
    }

    public void setPass_rate(String pass_rate) {
        this.pass_rate = pass_rate;
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
}
