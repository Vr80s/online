package com.xczhihui.cloudClass.service;

import java.util.List;
import java.util.Set;

/**
 * 题库接口
 * 
 * @author snow
 * @date
 */
public interface QuestionKnowledgePointService {

	/**
	 * 新建题目时往知识点题目关系对应表插入题目知识点关联数据
	 * 
	 * @param id
	 * @param list
	 */
	public void addKnowledgePoints(String id, List<String> list);

	/**
	 * 题目列表关联
	 * 
	 * @param kpid
	 * @param quesionsIds
	 */
	public void addKnowledgePoints(List<String> kpid, List<String> quesionsIds);

	/**
	 * 通过题目主键id从知识点题目关系对应表获取该题目的所有知识点name
	 * 
	 * @param id
	 * @return
	 */
	public List<String> getKnowledgePointsName(String id);

	/**
	 * 通过题目主键id从知识点题目关系对应表获取该题目的所有知识点的id
	 * 
	 * @param id
	 * @return
	 */
	public List<String> getKnowledgePointsId(String id);

	/**
	 * 通过题目主键id从知识点题目关系对应表获取该题目的所有知识点的id
	 * 
	 * @param id
	 * @return
	 */
	public List<String> getKnowledgePointsByQuestionsIds(List<String> ids);

	/**
	 * 删除题目对应的所有知识点
	 */
	public void deleteKnowledgePointsByQuestionId(String id);

	/**
	 * 通过知识点ID获取题ID
	 * 
	 * @param knowledgePointIds
	 * @return
	 */
	public Set<String> getQuestionIdsInKpointIds(List<String> knowledgePointIds);

	/**
	 * 通过知识点ID和试卷类型获取题ID
	 * 
	 * @param knowledgePointIds
	 * @return
	 */
	public Set<String> getQuestionIdsInKpointIds(
			List<String> knowledgePointIds, String type);

	/**
	 * 
	 * @param kpointIds
	 * @param list
	 * @return
	 */
	public Set<String> getQuestionIdsInKpointIdsNotInQuestionIds(
			List<String> knowledgePointIds, List<String> list);

	/**
	 * 题的标签获取题
	 * 
	 * @param kpointIds
	 * @param list
	 * @return
	 */
	public Set<String> getQuestionIdsInKpointIdsNotInQuestionIds(
			List<String> knowledgePointIds, List<String> list, String type);

	/**
	 * 通过题目id获取按','分隔的知识点名称字符串数据
	 * 
	 * @param id
	 * @return
	 */
	public String getKnowledgePointsNameString(String id);

	/**
	 * 通过题目id获取按','分隔的知识点id字符串数据
	 * 
	 * @param id
	 * @return
	 */
	public String getKnowledgePointsIdString(String id);

}
