package com.xczhihui.bxg.online.web.vo;

import java.util.Date;

/**
 * 试卷封装实体类
 * @Author Rongcai.Kang
 * @Date 2017/02/13 10:51
 */
public class PaperVo {

    /**
     * 试卷id
     */
    private  String  id ;

    /**
     * 试卷名字
     */
    private String paper_name;

    /**
     * 试卷截止时间
     */
    private Date end_time;

    /**
     * 试卷状态
     */
    private Integer status;

    /**
     * 试卷完成题数
     */
    private Integer finishSum;

    /**
     * 试卷总题数
     */
    private  Integer amount;

    /**
     * 试卷发布时间
     */
    private Date start_time;

    /**
     * 试卷成绩
     */
    private String score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaper_name() {
        return paper_name;
    }

    public void setPaper_name(String paper_name) {
        this.paper_name = paper_name;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFinishSum() {
        return finishSum;
    }

    public void setFinishSum(Integer finishSum) {
        this.finishSum = finishSum;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
