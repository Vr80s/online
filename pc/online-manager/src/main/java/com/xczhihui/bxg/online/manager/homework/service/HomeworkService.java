package com.xczhihui.bxg.online.manager.homework.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.manager.cloudClass.vo.GradeDetailVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.GradeVo;
import com.xczhihui.bxg.online.manager.homework.vo.ClassPaperVo;
import com.xczhihui.bxg.online.manager.homework.vo.PaperVo;
import com.xczhihui.bxg.online.manager.homework.vo.ReadOverPaperQuestionTypeInfoVo;
import com.xczhihui.bxg.online.manager.homework.vo.UserPaperVo;

import java.util.Map;

/**
 * 作业管理模块业务接口
 * @Author Fudong.Sun【】
 * @Date 2017/2/28 9:28
 */
public interface HomeworkService {
    /**
     * 查询职业课班级列表
     * @param gradeVo
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<GradeVo> findGradeList(GradeDetailVo gradeVo, Integer pageNumber, Integer pageSize);

    /**
     * 查询班级下试卷列表
     * @param searchVo
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<ClassPaperVo> findPaperList(ClassPaperVo searchVo, Integer pageNumber, Integer pageSize);

    /**
     * 查询作业试卷列表
     * @param searchVo
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<PaperVo> queryHomeworkList(PaperVo searchVo, Integer pageNumber, Integer pageSize);

    /**
     * 查询学员列表
     * @param searchVo
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<UserPaperVo> queryStudentList(UserPaperVo searchVo, Integer pageNumber, Integer pageSize);

    /**
     * 布置试卷
     * @param classPaper
     */
    public void addHomework(ClassPaperVo classPaper);

    /**
     * 发布作业卷
     * @param id
     * @return
     */
    public ResponseObject savePublishHomework(String id);

    /**
     * 删除作业卷
     * @param id
     */
    public void deleteHomework(String id);

    /**
     * 为学生保存成绩
     * @param userPaperIds
     * @return
     */
    public String saveUserResult(String userPaperIds);

    /**
     * 根据用户试卷id查询试题信息
     * @param userPaperId
     * @return
     */
    public Map<Integer, ReadOverPaperQuestionTypeInfoVo> getQuestionsByPaperId(String userPaperId,String sign) throws Exception;

    /**
     * 给主观题打分
     * @param questionId
     * @param totleScore
     * @param score
     */
    public void saveReadOverQuestion(String questionId,Double totleScore, Double score);

    /**
     * 判断是否还有未批阅的试题
     * @param userPaperId
     * @return
     */
    public ResponseObject judgeReadOverAllQuestions(String userPaperId);

    /**
     * 批阅整个试卷，打分，修改状态
     * @param userPaperId
     * @param comment
     */
    public void saveReadOverStudentPaper(String userPaperId,String comment);

    /**
     * 判断是否还有未批阅的学员试卷
     * @param classPaperId
     * @return
     */
    public ResponseObject judgeHasNextUnReadOverPaper(String classPaperId,String userPaperId);

    /**
     * 查找班级下一个未批阅的试卷
     * @param classPaperId
     * @return
     */
    public String findNextUnReadOverPaper(String classPaperId,String userPaperId);

    /**
     * 查询试卷评语
     * @param userPaperId
     * @return
     */
    public String findCommentById(String userPaperId);

    /**
     * 阅卷的时候判断如果班级下批阅完成，修改班级试卷状态为待发成绩
     * @param classPaperId
     */
    public void saveClassReadOverStatus(String classPaperId);

    /**
     * 发布成绩的时候判断班级下所有用户试卷都发布了，修改班级试卷状态为已完成
     * @param classPaperId
     */
    public void saveClassPublishStatus(String classPaperId);
}
