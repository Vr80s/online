package com.xczhihui.bxg.online.manager.homework.vo;

import java.util.List;
import java.util.Map;

/**
 * @Author Fudong.Sun【】
 * @Date 2017/3/3 19:17
 */
public class UserPaperQuestionVo {
    private String id;
    /**
     * 学员试卷id
     */
    private String user_paper_id;
    /**
     * 题型，0单选、1多选、2判断、3填空、4简答'
     */
    private Integer question_type;
    /**
     * 题干（富文本）
     */
    private String question_head;
    /**
     * 选项
     */
    private String options;
    /**
     * 选项图片
     */
    private String options_picture;
    /**
     * 附件
     */
    private String attachment_url;
    /**
     * 难度等级，A简单，B一般，C困难
     */
    private String difficulty;
    /**
     * 答案
     */
    private String answer;
    /**
     * 学生答案附件
     */
    private String answer_attachment_url;
    /**
     * 答案解析
     */
    private String solution;
    /**
     * 题目本身分数
     */
    private Double question_score;
    /**
     * 用户回答
     */
    private String user_answer;
    /**
     * 用户得分
     */
    private Double user_score;
    /**
     * 是否已发布，1已发布，0未发布
     */
    private Integer published;
    /**
     * 标准附件文件名
     */
    private String orgFileName;
    /**
     * 学员附件文件名
     */
    private String usrFileName;

    /**
     * 选项拼装数据
     */
    private List<Map<String,Object>> optionList;

    /**
     * '0没做，1错，2对'
     */
    Integer rightOrWrong;

    /**
     * 是否批阅，0未批阅，1已批阅
     */
    Integer read_over;

    public Integer getRightOrWrong() {
        return rightOrWrong;
    }

    public void setRightOrWrong(Integer rightOrWrong) {
        this.rightOrWrong = rightOrWrong;
    }

    public Integer getRead_over() {
        return read_over;
    }

    public void setRead_over(Integer read_over) {
        this.read_over = read_over;
    }

    public List<Map<String, Object>> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Map<String, Object>> optionList) {
        this.optionList = optionList;
    }

    public String getOrgFileName() {
        return orgFileName;
    }

    public void setOrgFileName(String orgFileName) {
        this.orgFileName = orgFileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_paper_id() {
        return user_paper_id;
    }

    public void setUser_paper_id(String user_paper_id) {
        this.user_paper_id = user_paper_id;
    }

    public Integer getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(Integer question_type) {
        this.question_type = question_type;
    }

    public String getQuestion_head() {
        return question_head;
    }

    public void setQuestion_head(String question_head) {
        this.question_head = question_head;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getOptions_picture() {
        return options_picture;
    }

    public void setOptions_picture(String options_picture) {
        this.options_picture = options_picture;
    }

    public String getAttachment_url() {
        return attachment_url;
    }

    public void setAttachment_url(String attachment_url) {
        this.attachment_url = attachment_url;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer_attachment_url() {
        return answer_attachment_url;
    }

    public void setAnswer_attachment_url(String answer_attachment_url) {
        this.answer_attachment_url = answer_attachment_url;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Double getQuestion_score() {
        return question_score;
    }

    public void setQuestion_score(Double question_score) {
        this.question_score = question_score;
    }

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

    public Double getUser_score() {
        return user_score;
    }

    public void setUser_score(Double user_score) {
        this.user_score = user_score;
    }

    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }

    public String getUsrFileName() {
        return usrFileName;
    }

    public void setUsrFileName(String usrFileName) {
        this.usrFileName = usrFileName;
    }
}
