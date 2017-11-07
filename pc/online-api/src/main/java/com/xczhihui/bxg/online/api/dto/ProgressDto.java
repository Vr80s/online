package com.xczhihui.bxg.online.api.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liutao
 * @create 2017-09-19 14:51
 **/
public class ProgressDto implements Serializable{

    private Date reviewerTime;
    private String againstReason;
    private Date appealTime;

    public Date getReviewerTime() {
        return reviewerTime;
    }

    public void setReviewerTime(Date reviewerTime) {
        this.reviewerTime = reviewerTime;
    }

    public String getAgainstReason() {
        return againstReason;
    }

    public void setAgainstReason(String againstReason) {
        this.againstReason = againstReason;
    }

    public Date getAppealTime() {
        return appealTime;
    }

    public void setAppealTime(Date appealTime) {
        this.appealTime = appealTime;
    }
}
