package com.xczhihui.bxg.online.manager.barrier.vo;

import java.util.Date;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class BarrierVo extends OnlineBaseVo  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String courseId;
    private String name;
    private Integer totalScore;
    private Integer limitTime;
    private Integer passScorePercent;
    private String parentId;
    private String kpointId;
    private String kpointIds;
    private String oldKpointIds;
    private String kpointNames;
    
    private String chapterId;
    private Integer chapterLevel;
    
    //保存题型及数量的数组
    private String[] strategyCnt;
    private String[] strategyTotalScore;
    private String[] strategyScore;
    private String[] strategyQuestionType;
    
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	public Integer getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(Integer limitTime) {
		this.limitTime = limitTime;
	}
	public Integer getPassScorePercent() {
		return passScorePercent;
	}
	public void setPassScorePercent(Integer passScorePercent) {
		this.passScorePercent = passScorePercent;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getKpointId() {
		return kpointId;
	}
	public void setKpointId(String kpointId) {
		this.kpointId = kpointId;
	}
	public String getChapterId() {
		return chapterId;
	}
	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}
	public Integer getChapterLevel() {
		return chapterLevel;
	}
	public void setChapterLevel(Integer chapterLevel) {
		this.chapterLevel = chapterLevel;
	}
	public String[] getStrategyCnt() {
		return strategyCnt;
	}
	public void setStrategyCnt(String[] strategyCnt) {
		this.strategyCnt = strategyCnt;
	}
	public String[] getStrategyTotalScore() {
		return strategyTotalScore;
	}
	public void setStrategyTotalScore(String[] strategyTotalScore) {
		this.strategyTotalScore = strategyTotalScore;
	}
	public String[] getStrategyScore() {
		return strategyScore;
	}
	public void setStrategyScore(String[] strategyScore) {
		this.strategyScore = strategyScore;
	}
	public String getKpointIds() {
		return kpointIds;
	}
	public void setKpointIds(String kpointIds) {
		this.kpointIds = kpointIds;
	}
	public String[] getStrategyQuestionType() {
		return strategyQuestionType;
	}
	public void setStrategyQuestionType(String[] strategyQuestionType) {
		this.strategyQuestionType = strategyQuestionType;
	}
	public String getKpointNames() {
		return kpointNames;
	}
	public void setKpointNames(String kpointNames) {
		this.kpointNames = kpointNames;
	}
	public String getOldKpointIds() {
		return oldKpointIds;
	}
	public void setOldKpointIds(String oldKpointIds) {
		this.oldKpointIds = oldKpointIds;
	}
}
