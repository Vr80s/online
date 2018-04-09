package com.xczhihui.bxg.online.api.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liutao
 * @create 2017-09-19 14:35
 **/
public class ExamineProgressVo implements Serializable{

  public  class Progress implements Serializable{
        String name;
        Date examinTime;
        Boolean showAppeal=false;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getExaminTime() {
            return examinTime;
        }

        public void setExaminTime(Date examinTime) {
            this.examinTime = examinTime;
        }

        public Boolean getShowAppeal() {
            return showAppeal;
        }

        public void setShowAppeal(Boolean showAppeal) {
            this.showAppeal = showAppeal;
        }
    }

    private List<Progress> progressList;
    private LiveExamineInfoVo liveExamineInfoVo;


    public List<Progress> getProgressList() {
        return progressList;
    }

    public void setProgressList(List<Progress> progressList) {
        this.progressList = progressList;
    }

    public LiveExamineInfoVo getLiveExamineInfoVo() {
        return liveExamineInfoVo;
    }

    public void setLiveExamineInfoVo(LiveExamineInfoVo liveExamineInfoVo) {
        this.liveExamineInfoVo = liveExamineInfoVo;
    }
}
