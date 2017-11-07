package com.xczhihui.bxg.online.manager.exam.service;

import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.QuestionVo;
import com.xczhihui.bxg.online.manager.exam.vo.ExamPaperQuestionVo;
import com.xczhihui.bxg.online.manager.exam.vo.ExamPaperVo;

public interface ExamPaperService {

	public Page<ExamPaperVo> findExamPaperPage(ExamPaperVo examPaperVo, Integer pageNumber, Integer pageSize);
	
	/**
	 * 根据条件分页获取课程信息。
	 * 
	 * @param groups
	 * @param pageVo
	 * @return
	 */
    public Page<CourseVo> findCoursePage(CourseVo courseVo,  int pageNumber, int pageSize);
	
	/**
	 * 构建试卷
	 * 
	 *@return void
	 * @throws Exception 
	 */
	public Map<String,Object> bulidExamPaper(ExamPaperVo examPaperVo) throws Exception;
	
	/**
	 * 新增
	 * 
	 *@return void
	 */
	public void addExamPaper(ExamPaperVo examPaperVo);
	
	/**
	 * 获取到问题
	 * 
	 *@return void
	 */
	public List getQuestions(String kpointIds);
	
	/**
	 * 获取到问题
	 * 
	 *@return void
	 */
	public List getQuestionsCnt(String kpointIds,String questionType);

    /**
     * 获取到问题page
     * 
     * @param groups
     * @param pageVo
     * @return
     */
    public Page<QuestionVo> findQuestionPage(QuestionVo questionVo,  int pageNumber, int pageSize);

	/**
	 * 刷新难度
	 *@return void
	 */
	public String refreshDifficulty(ExamPaperVo examPaperVo);

	/**
	 * 
	 *@return void
	 */
	public void updateExamPaper(ExamPaperVo examPaperVo);

	/**
	 * 修改状态
	 * 
	 *@return void
	 * @throws Exception 
	 */
	public List<Map<String,Object>> getExamPaperQuestion(ExamPaperVo examPaperVo) throws Exception;
	
	/**
	 * 逻辑批量删除
	 * 
	 *@return void
	 */
	public void deletes(String[] ids);
}
