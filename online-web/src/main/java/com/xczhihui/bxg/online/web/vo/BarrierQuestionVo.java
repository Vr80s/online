package com.xczhihui.bxg.online.web.vo;

/**
 * 试卷内容的结果封装类
 * @author Rongcai Kang
 */
public class BarrierQuestionVo {

    /**
     * 题的id号
     */
    private String id;
    /**
     * 题干
     */
    private String question_head;
    /**
     * 选项
     */
    private String options;
    /**
     * 题型：0单选、1多选、2判断、3填空、4简答
     */
    private Integer question_type;

    /**
     * 选项图片
     */
    private String  options_picture;

    /**
     * 题目本身分数
     */
    private Integer question_score;

    /**
     * 我的回答
     */
    private String my_answer;

    /**
     * 正确答案
     */
    private String answer;
    /**
     * 答案解析
     */
    private String solution;
    /**
     * 是否正确:0错误 1:正确
     */
    private  Integer is_right;
    
    public String getBarrier_id() {
		return barrier_id;
	}

	public void setBarrier_id(String barrier_id) {
		this.barrier_id = barrier_id;
	}

	private String barrier_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(Integer question_type) {
        this.question_type = question_type;
    }

    public String getOptions_picture() {
        return options_picture;
    }

    public void setOptions_picture(String options_picture) {
        this.options_picture = options_picture;
    }

    public Integer getQuestion_score() {
        return question_score;
    }

    public void setQuestion_score(Integer question_score) {
        this.question_score = question_score;
    }

    public String getMy_answer() {
        return my_answer;
    }

    public void setMy_answer(String my_answer) {
        this.my_answer = my_answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Integer getIs_right() {
        return is_right;
    }

    public void setIs_right(Integer is_right) {
        this.is_right = is_right;
    }
}
