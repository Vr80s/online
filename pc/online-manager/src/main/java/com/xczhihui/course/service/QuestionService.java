package com.xczhihui.course.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xczhihui.course.vo.QuestionOptionPicture;
import com.xczhihui.course.vo.TreeNode;
import org.apache.poi.ss.usermodel.Sheet;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.QuesStore;


/**
 * 题库接口
 * 
 * @author snow
 * @date 2015年11月20日
 */
public interface QuestionService {

	public List<TreeNode> findDataForTreeRight(String currentNodeId);

	public List<TreeNode> findDataForTreeLeft(String courseId);

	public void addQuestion(QuesStore question, String knowledgeIds,
			Map<String, String> map);

	public Page<QuesStore> findPage(QuesStore searchVo, int currentPage,
			int pageSize);

	public QuesStore findQuestion(String id);

	List<String> getKnowledgePointsName(String id);

	public String getKnowledgePointsNameString(String id);

	public String getKnowledgePointsIdString(String id);

	public List<QuestionOptionPicture> getQopList(String questionId,
                                                  String questionType, String optionName);

	public void updateQuestion(QuesStore question);

	public void deleteById(String id,String chapterIds);

	StringBuffer checkXls(Sheet sheet, List<QuesStore> excelList,Set<String> zipSet,String courseName);

	String saveBatch(List<QuesStore> excelList, Map<String, byte[]> zipMap,
			String ticket);

	public void updateEnbaleStatus(String id);

	public void updateDisableStatus(String id);

	
}
