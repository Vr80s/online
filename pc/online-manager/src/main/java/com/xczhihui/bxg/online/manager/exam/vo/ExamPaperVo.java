package com.xczhihui.bxg.online.manager.exam.vo;
import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class ExamPaperVo extends OnlineBaseVo  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Integer type;
    private String courseId;
    private String paperName;
    private Double score;
    private Integer duration;
    private String difficulty;
    private String createPersonName;
    private String kpointIds;
    private String useSum;
    private String settingMethod;//1默认设置 2明细设置
    
	//保存题型及数量的数组
    private String[] strategyCnt;//总共分配多少题
    private String[] strategyTotalScore;//总共分配多少分
    private String[] strategyScore;//每道题多少分
    private String[] questionType;// 题型，0单选、1多选、2判断、3填空、4简答、5代码、6应用
    private String[] difficultyAllocation;//难度分配
    private String[] completionQuestionCntQuestion;//填空题每道题型所对应的问题的分值
    private String[] completionQuestionCntDifficulty;//填空题每道题型所对应的问题的难度
    private String[] shortAnswerQuestionCntQuestion;//简答题题每道题型所对应的问题的分值
    private String[] shortAnswerQuestionCntDifficulty;//简答题每道题型所对应的问题的难度
    private String[] codeQuestionCntQuestion;//代码题每道题型所对应的问题的分值
    private String[] codeQuestionCntDifficulty;//代码题每道题型所对应的问题的难度
    private String[] practicalQuestionCntQuestion;//实操题每道题型所对应的问题的分值
    private String[] practicalQuestionCntDifficulty;//实操题每道题型所对应的问题的难度
    private String[] lastQuestionId;//最终提交的问题ID
    private String[] lastQquestionScore;//最终提交的问题得分
    

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getCreatePersonName() {
		return createPersonName;
	}
	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}
	public String getKpointIds() {
		return kpointIds;
	}
	public void setKpointIds(String kpointIds) {
		this.kpointIds = kpointIds;
	}
	public String getUseSum() {
		return useSum;
	}
	public void setUseSum(String useSum) {
		this.useSum = useSum;
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
	public String[] getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String[] questionType) {
		this.questionType = questionType;
	}
	public String[] getDifficultyAllocation() {
		return difficultyAllocation;
	}
	public void setDifficultyAllocation(String[] difficultyAllocation) {
		this.difficultyAllocation = difficultyAllocation;
	}
	public String[] getCompletionQuestionCntQuestion() {
		return completionQuestionCntQuestion;
	}
	public void setCompletionQuestionCntQuestion(String[] completionQuestionCntQuestion) {
		this.completionQuestionCntQuestion = completionQuestionCntQuestion;
	}
	public String[] getCompletionQuestionCntDifficulty() {
		return completionQuestionCntDifficulty;
	}
	public void setCompletionQuestionCntDifficulty(String[] completionQuestionCntDifficulty) {
		this.completionQuestionCntDifficulty = completionQuestionCntDifficulty;
	}
	public String[] getShortAnswerQuestionCntQuestion() {
		return shortAnswerQuestionCntQuestion;
	}
	public void setShortAnswerQuestionCntQuestion(String[] shortAnswerQuestionCntQuestion) {
		this.shortAnswerQuestionCntQuestion = shortAnswerQuestionCntQuestion;
	}
	public String[] getShortAnswerQuestionCntDifficulty() {
		return shortAnswerQuestionCntDifficulty;
	}
	public void setShortAnswerQuestionCntDifficulty(String[] shortAnswerQuestionCntDifficulty) {
		this.shortAnswerQuestionCntDifficulty = shortAnswerQuestionCntDifficulty;
	}
	public String[] getCodeQuestionCntQuestion() {
		return codeQuestionCntQuestion;
	}
	public void setCodeQuestionCntQuestion(String[] codeQuestionCntQuestion) {
		this.codeQuestionCntQuestion = codeQuestionCntQuestion;
	}
	public String[] getCodeQuestionCntDifficulty() {
		return codeQuestionCntDifficulty;
	}
	public void setCodeQuestionCntDifficulty(String[] codeQuestionCntDifficulty) {
		this.codeQuestionCntDifficulty = codeQuestionCntDifficulty;
	}
	public String[] getPracticalQuestionCntQuestion() {
		return practicalQuestionCntQuestion;
	}
	public void setPracticalQuestionCntQuestion(String[] practicalQuestionCntQuestion) {
		this.practicalQuestionCntQuestion = practicalQuestionCntQuestion;
	}
	public String[] getPracticalQuestionCntDifficulty() {
		return practicalQuestionCntDifficulty;
	}
	public void setPracticalQuestionCntDifficulty(String[] practicalQuestionCntDifficulty) {
		this.practicalQuestionCntDifficulty = practicalQuestionCntDifficulty;
	}
	public String getSettingMethod() {
		return settingMethod;
	}
	public void setSettingMethod(String settingMethod) {
		this.settingMethod = settingMethod;
	}
	public String[] getLastQuestionId() {
		return lastQuestionId;
	}
	public void setLastQuestionId(String[] lastQuestionId) {
		this.lastQuestionId = lastQuestionId;
	}
	public String[] getLastQquestionScore() {
		return lastQquestionScore;
	}
	public void setLastQquestionScore(String[] lastQquestionScore) {
		this.lastQquestionScore = lastQquestionScore;
	}
}
