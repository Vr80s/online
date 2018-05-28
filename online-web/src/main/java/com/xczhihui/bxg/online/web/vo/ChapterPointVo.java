package com.xczhihui.bxg.online.web.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/11/2.
 * 章节表vo层
 */
public class ChapterPointVo extends  AskVo {
    private  String  name;
    /**
     * 已学习的视频数量  只有知识点有
     */
    private Integer studyFinish;
    /**
     * 学习中的视频数量  只有知识点有
     */
    private Integer videoCount;
    /**
     * 未学习的视频数量  只有知识点有
     */
    private Integer unStudy;
    /**
     * 未启用视频数量  只有知识点有
     */
    private Integer historyNum;

    /**
     * CC直播视频提供的id
     */
    private  String ccvideoId;

    /**
     * 章下-小节的id
     */
    private String sectionId;


    /**
     * 小节下的 知识点的id
     */
    private String pointId;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 学习状态 0：未学习，1：已学习，2：学习中
     */
    private  Integer study_status;

    /**
     * 视频排序字段
     */
    private  Integer sort;

    /**
     * 锁状态 1:解锁  0:未解决
     */
    private Integer lock_status;

    /**
     * 关卡通关状态:0:未通关  1:已通关
     */
    private Integer barrier_status;

    /**
     * 类型 0:知识点  1:关卡
     */
    private Integer type;

    /**
     * 关节id
     */
    private String barrier_id;

    private List<ChapterPointVo> chapterSons = new ArrayList<ChapterPointVo>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStudyFinish() {
        return studyFinish;
    }

    public void setStudyFinish(Integer studyFinish) {
        this.studyFinish = studyFinish;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public Integer getUnStudy() {
        return unStudy;
    }

    public void setUnStudy(Integer unStudy) {
        this.unStudy = unStudy;
    }

    public List<ChapterPointVo> getChapterSons() {
        return chapterSons;
    }

    public void setChapterSons(List<ChapterPointVo> chapterSons) {
        this.chapterSons = chapterSons;
    }


    public String getCcvideoId() {
        return ccvideoId;
    }

    public void setCcvideoId(String ccvideoId) {
        this.ccvideoId = ccvideoId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getStudy_status() {
        return study_status;
    }

    public void setStudy_status(Integer study_status) {
        this.study_status = study_status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getHistoryNum() {
        return historyNum;
    }

    public void setHistoryNum(Integer historyNum) {
        this.historyNum = historyNum;
    }

    public Integer getLock_status() {
        return lock_status;
    }

    public void setLock_status(Integer lock_status) {
        this.lock_status = lock_status;
    }

    public Integer getType() {
        return type==null ? 0 : type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBarrier_status() {
        return barrier_status;
    }

    public void setBarrier_status(Integer barrier_status) {
        this.barrier_status = barrier_status;
    }

    public String getBarrier_id() {
        return barrier_id ;
    }

    public void setBarrier_id(String barrier_id) {
        this.barrier_id = barrier_id;
    }
}
