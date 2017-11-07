package com.xczhihui.bxg.online.manager.exam.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.math.Fraction;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.QuestionVo;
import com.xczhihui.bxg.online.manager.exam.dao.ExamPaperDao;
import com.xczhihui.bxg.online.manager.exam.service.ExamPaperService;
import com.xczhihui.bxg.online.manager.exam.vo.ExamPaperVo;
/**
 * 
 * @author wgw
 *
 */
@Service
public class ExamPaperServiceImpl extends OnlineBaseServiceImpl implements ExamPaperService {

	@Autowired
    private ExamPaperDao examPaperDao;
	
    @Autowired
    private AttachmentCenterService attachmentCenterService;
	
	@Override
	public Page<ExamPaperVo> findExamPaperPage(ExamPaperVo examPaperVo, Integer pageNumber, Integer pageSize) {
		Page<ExamPaperVo> page = examPaperDao.findExamPaperPage(examPaperVo, pageNumber, pageSize);
		return page;
	}
	
    @Override
	public Page<CourseVo> findCoursePage(CourseVo courseVo,  int pageNumber, int pageSize) {
    	Page<CourseVo> page = examPaperDao.findCloudClassCoursePage(courseVo, pageNumber, pageSize);
    	return page;
	}
    
	@Override
	public Map<String,Object> bulidExamPaper(ExamPaperVo examPaperVo) throws Exception {
		//生成所有试卷的主体信息 包括ID值  要连同试卷一并返回
		examPaperVo.setId(UUID.randomUUID().toString().replace("-", ""));
		examPaperVo.setType(0);//默认都是作业
		
		//计算难度
		String[] difficultyAllocation = examPaperVo.getDifficultyAllocation();//首先获取所有难题分配数量
		//每个难度的题数
		int[] difficultyCnts = {0,0,0,0};//这个数组是用来计算试卷的难度系数
		
		for(int i = 0;i<difficultyAllocation.length;i++){
			String[] tempDifficulty = difficultyAllocation[i].split(",");
			if(tempDifficulty.length > 1){
				difficultyCnts[0] += Integer.parseInt("".equals(tempDifficulty[0])?"0":tempDifficulty[0]);
				difficultyCnts[1] += Integer.parseInt("".equals(tempDifficulty[1])?"0":tempDifficulty[1]);
				difficultyCnts[2] += Integer.parseInt("".equals(tempDifficulty[2])?"0":tempDifficulty[2]);
				difficultyCnts[3] += Integer.parseInt("".equals(tempDifficulty[3])?"0":tempDifficulty[3]);
			}
		}

		//如果是明细设置 则需要执行下面四个方法  如果是默认设置 下面四个方法执行以后 对本身的  difficultyCnts 也不会有改变
		
		//填空题有值
		sumSubjectiveQuestions(difficultyCnts,examPaperVo.getCompletionQuestionCntDifficulty());
		//简答题
		sumSubjectiveQuestions(difficultyCnts,examPaperVo.getShortAnswerQuestionCntDifficulty());
		//代码题
		sumSubjectiveQuestions(difficultyCnts,examPaperVo.getCodeQuestionCntDifficulty());
		//实操题
		sumSubjectiveQuestions(difficultyCnts,examPaperVo.getPracticalQuestionCntDifficulty());
		
		examPaperVo.setDifficulty(reckonDifficulty(difficultyCnts));

		//根据规则查询出所有的试卷信息
		String [] strategyQuestionType = examPaperVo.getQuestionType();
		List<Map<String,Object>> qustionList = new ArrayList<>();

		//把主体信息与试卷信息存入Map值返回
		for(int i = 0; i < strategyQuestionType.length;i++){
			Map<String,Object> map = new HashMap<>();
			String questionType = strategyQuestionType[i];
			String strategyCnt = examPaperVo.getStrategyCnt()[i] != null && !"".equals(examPaperVo.getStrategyCnt()[i])?examPaperVo.getStrategyCnt()[i]:"0";
			String strategyScore = "0";
			
			if(i < 3 || (i >= 3 && "1".equals(examPaperVo.getSettingMethod()))){//只有前三个肯定有  第三个之后需要单独判断是 默认设置 还是基本设置  1 是默认设置
				strategyScore = examPaperVo.getStrategyScore()[i];
			}
			
			if(Integer.parseInt(strategyCnt) > 0){//哪一个题型分配了题获取哪个
				map.put("strategyCnt", strategyCnt);//总题数
				map.put("totalScore", examPaperVo.getStrategyTotalScore()[i]);//存入总分
				map.put("questionType", questionType);//题型
				
				List<QuestionVo> tempQuestionList = new ArrayList<>();
				if(i < 3){//前三个肯定这样计算
					tempQuestionList = getQuestion(questionType,examPaperVo.getKpointIds(),difficultyAllocation[i].split(","),strategyScore);
				}else{//第三个以后
					int[] tempDifficultyCnts = {0,0,0,0};//此处该数组用来组成明细设置中 每个难度的题分别有多少
					if("2".equals(examPaperVo.getSettingMethod())){//只有前三个肯定有  第三个之后需要单独判断是 默认设置 还是基本设置  1 是默认设置
						//只要是明细设置的 都需要把单独处理题
						List<QuestionVo> tempQuestionList2 = new ArrayList<>();
						
						if("3".equals(strategyQuestionType[i])){
							sumSubjectiveQuestions(tempDifficultyCnts,examPaperVo.getCompletionQuestionCntDifficulty());
							tempQuestionList = getQuestion(questionType,examPaperVo.getKpointIds(),tempDifficultyCnts,strategyScore);
							dealDetailQuestionList(examPaperVo.getCompletionQuestionCntQuestion(),examPaperVo.getCompletionQuestionCntDifficulty(),tempQuestionList,tempQuestionList2);
						}else if("4".equals(strategyQuestionType[i])){
							sumSubjectiveQuestions(tempDifficultyCnts,examPaperVo.getShortAnswerQuestionCntDifficulty());
							tempQuestionList = getQuestion(questionType,examPaperVo.getKpointIds(),tempDifficultyCnts,strategyScore);
							dealDetailQuestionList(examPaperVo.getShortAnswerQuestionCntQuestion(),examPaperVo.getShortAnswerQuestionCntDifficulty(),tempQuestionList,tempQuestionList2);
						}else if("5".equals(strategyQuestionType[i])){
							sumSubjectiveQuestions(tempDifficultyCnts,examPaperVo.getCodeQuestionCntDifficulty());
							tempQuestionList = getQuestion(questionType,examPaperVo.getKpointIds(),tempDifficultyCnts,strategyScore);
							dealDetailQuestionList(examPaperVo.getCodeQuestionCntQuestion(),examPaperVo.getCodeQuestionCntDifficulty(),tempQuestionList,tempQuestionList2);
						}else if("6".equals(strategyQuestionType[i])){
							sumSubjectiveQuestions(tempDifficultyCnts,examPaperVo.getPracticalQuestionCntDifficulty());
							tempQuestionList = getQuestion(questionType,examPaperVo.getKpointIds(),tempDifficultyCnts,strategyScore);
							dealDetailQuestionList(examPaperVo.getPracticalQuestionCntQuestion(),examPaperVo.getPracticalQuestionCntDifficulty(),tempQuestionList,tempQuestionList2);
						}

						tempQuestionList = tempQuestionList2;
					}else{
						tempQuestionList = getQuestion(questionType,examPaperVo.getKpointIds(),difficultyAllocation[i].split(","),strategyScore);
					}
				}
				
				map.put("questionList", tempQuestionList);//把试题数据放进去
				qustionList.add(map);//存储总list中
			}
		}

		Map<String,Object> map = new HashMap<>();
		map.put("examPaper", examPaperVo);//主体信息
		map.put("qustionList", qustionList);//问题列表
		return map;
	}

	/**
	 * 从数据库中获取该题型的试题
	 * @param questionType 问题类型
	 * @param kpoinkId 问题的知识点范围
	 * @param difficultyAllocation 该题型具体的各个难度的题的数量（字符串）
	 * @param defalutScore 默认分数 如果是明细设置的主观题 此处需要在得到结果后单独处理
	 * @return
	 * @throws Exception 
	 */
	public List<QuestionVo> getQuestion(String questionType,String kpoinkId,String[] difficultyAllocation,String defalutScore) throws Exception{//获取到具体的试题
		String [] results = {"D","C","B","A"};
		String [] kpoinkIds = kpoinkId.split(",");
		List<QuestionVo> resultsList = new ArrayList<>();
		kpoinkId = "";
		for(int i = 0; i < kpoinkIds.length; i ++){//知识点
			kpoinkId += "\'"+kpoinkIds[i]+"\'";
			if(i < kpoinkIds.length - 1){
				kpoinkId += ",";
			}
		}

		for(int i = 0; i < difficultyAllocation.length; i++){
			String dal = difficultyAllocation[i];
			if(dal ==null || "".equals(dal) || Integer.parseInt(dal) == 0){
				continue;
			}
			String sql ="SELECT " +
						"	distinct " +
						"	t1.id, " +
						"	t1.question_head, " +
						"	t1.answer, " +
						"	t1.difficulty, " +
						"	ifnull(t1.solution,'暂无') solution, " +
						"	t1.options, " +
						"	t1.options_picture, " +
						"	t1.question_type, " +
						"	"+defalutScore+" questionScore " +
						"FROM " +
						"	oe_question t1, " +
						"	oe_question_kpoint t2 " +
						"WHERE " +
						"	t1.id = t2.question_id " +
						"AND t2.kpoint_id IN ("+kpoinkId+") " +
						"AND upper(t1.difficulty) = '"+results[i]+"' " +
						"AND t1.status = 1 " +
						"AND t1.question_type = " + questionType + " " +
						"ORDER BY " +
						"	t1.question_head asc " +
						"LIMIT  " +difficultyAllocation[i] + " ";

			List<QuestionVo> list = dao.getNamedParameterJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(QuestionVo.class));
			for(int j = 0;j < list.size();j ++){
				if(5 == list.get(j).getQuestionType() || 6 == list.get(j).getQuestionType()){
					if(!StringUtils.isEmpty(list.get(j).getAnswer())){
	                	Attachment att = attachmentCenterService.getAttachmentObject(list.get(j).getAnswer());
	                	if(att!=null) {
	                        String fileName = att.getOrgFileName();
	                        list.get(j).setFileName(fileName);
	                	}
					}
				}
			}
			resultsList.addAll(list);
		}

		return resultsList;
	}

	/**
	 * 从数据库中获取该题型的试题
	 * @param questionType 问题类型
	 * @param kpoinkId 问题的知识点范围
	 * @param difficultyAllocation 该题型具体的各个难度的题的数量 整形数组（整形）
	 * @param defalutScore 默认分数 如果是明细设置的主观题 此处需要在得到结果后单独处理
	 * @return
	 * @throws Exception 
	 */
	public List<QuestionVo> getQuestion(String questionType,String kpoinkId,int[] difficultyAllocation,String defalutScore) throws Exception{//获取到具体的试题
		String [] results = {"D","C","B","A"};
		String [] kpoinkIds = kpoinkId.split(",");
		List<QuestionVo> resultsList = new ArrayList<>();
		kpoinkId = "";
		for(int i = 0; i < kpoinkIds.length; i ++){//知识点
			kpoinkId += "\'"+kpoinkIds[i]+"\'";
			if(i < kpoinkIds.length - 1){
				kpoinkId += ",";
			}
		}
		
		for(int i = 0; i < difficultyAllocation.length; i++){
			int dal = difficultyAllocation[i];
			if(dal == 0){
				continue;
			}
			String sql ="SELECT " +
					"	distinct " +
					"	t1.id, " +
					"	t1.question_head, " +
					"	t1.answer, " +
					"	t1.difficulty, " +
					"	ifnull(t1.solution,'暂无') solution, " +
					"	t1.options, " +
					"	t1.options_picture, " +
					"	t1.question_type, " +
					"	"+defalutScore+" questionScore " +
					"FROM " +
					"	oe_question t1, " +
					"	oe_question_kpoint t2 " +
					"WHERE " +
					"	t1.id = t2.question_id " +
					"AND t2.kpoint_id IN ("+kpoinkId+") " +
					"AND upper(t1.difficulty) = '"+results[i]+"' " +
					"AND t1.status = 1 " +
					"AND t1.question_type = " + questionType + " " +
					"ORDER BY " +
					"	t1.question_head asc " +
					"LIMIT  " + difficultyAllocation[i] + " ";
//			System.out.println("执行的SQL:"+sql);
			List<QuestionVo> list = dao.getNamedParameterJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(QuestionVo.class));
			for(int j = 0;j < list.size();j ++){
				
				if(5 == list.get(j).getQuestionType() || 6 == list.get(j).getQuestionType()){
					if(!StringUtils.isEmpty(list.get(j).getAnswer())){
	                	Attachment att = attachmentCenterService.getAttachmentObject(list.get(j).getAnswer());
	                	if(att!=null) {
	                        String fileName = att.getOrgFileName();
	                        list.get(j).setFileName(fileName);
	                	}
					}
				}
			}
			resultsList.addAll(dao.getNamedParameterJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(QuestionVo.class)));
		}
		
		return resultsList;
	}

	/**
	 * 
	 * @param questionCnt 每道题对应的分值
	 * @param questionCntDifficulty 每道题对应的难度
	 * @param oldList 已经从数据中查询出的试题信息
	 * @param newList 要重新组成的新的list
	 * @return
	 */
	public List<QuestionVo> dealDetailQuestionList(String[] questionCnt,String[] questionCntDifficulty,List<QuestionVo> oldList,List<QuestionVo> newList){
		
		for(int i = 0 ;i < questionCnt.length; i++){//遍历循环前台传过来的配置信息
			String difficulty = questionCntDifficulty[i];
			QuestionVo tempQuestion = null;
			for(int j = 0;j < oldList.size() ; j ++){
				if(difficulty.equals(oldList.get(j).getDifficulty())){//第一次要取难度比对 如果符合就取出来 该题 然后从oldList 中将该题移除
					tempQuestion = oldList.get(j);
					oldList.remove(j);
					break;//从该次循环中跳出
				} 
			}
			if(tempQuestion == null){
				throw new RuntimeException("试题数据异常[试题数量不匹配]，请重新处理后提交！");
			}
			//获取到一个符合条件的QuestionVo 以后 设置该题的分数 然后放入新的list中
			tempQuestion.setQuestionScore(questionCnt[i]);
			newList.add(tempQuestion);
		}
		
		return newList;
	}
	
	/**
	 * 评估试卷的难度
	 * @param difficultyCnts 各个难度 对应的试题数量
	 * @return
	 */
	public String reckonDifficulty(int[] difficultyCnts){
		//开始进行难度判断
		int cnt0 = 0;//计算有几个是0
		for(int i = 0;i<difficultyCnts.length;i++){
			if(difficultyCnts[i] == 0){
				cnt0 ++;
			}
		}

		if(cnt0 == 4){//全部为0 抛出异常
			throw new RuntimeException("试题数据异常[全部为0]，请重新提交！");
		}else if(cnt0 == 3){//如果只有单个有题目 那么返回
			return this.getDifficulty(NumberUtils.max(difficultyCnts),difficultyCnts);//取出最大的数返回下标所对于数组的字母
		}else if(cnt0 == 2){//如果有两种  
			/**
			 * 试卷整体难度最多的一类试题数百分比-两类试题的平均数大于等于10%则试卷整体难度为试题最多的类型。小于10% 则整卷难度为难度题少的。
			 * 举例：困难题10题  一般题5题（66%-50%=16%大于10%，则此试卷难度为困难）
			 */
			//取数组中最大的那个数字  除以 数组之和  求出最大值占比  是长整形  因为计算到小数点一位 所以 乘了1000 相应的伐值也乘以 10
			Long maxPercentage = Math.round(Fraction.getFraction(NumberUtils.max(difficultyCnts),sum(difficultyCnts)).doubleValue() * 1000);
			if(maxPercentage - 500 >= 100){
				return this.getDifficulty(NumberUtils.max(difficultyCnts),difficultyCnts);
			}else{
				return this.getDifficulty(NumberUtils.min(difficultyCnts),difficultyCnts);
			}
		}else if(cnt0 == 1){//如果有三个有题目 
			/**
			 * .试卷中有三类难度，试卷整体难度为最多的一类试题数百分比减3类试题的平均数大于等于16.7%则试卷整体难度为题型最多的题目类型，小于16.7%试卷难度为其他两类题数最多的一类。
			 * 
			 */
			//取数组中最大的那个数字  除以 数组之和  求出最大值占比  是长整形
			Long maxPercentage = Math.round(Fraction.getFraction(NumberUtils.max(difficultyCnts),sum(difficultyCnts)).doubleValue() * 1000);
			
			if(maxPercentage - 333 >= 167){
				return this.getDifficulty(NumberUtils.max(difficultyCnts),difficultyCnts);
			}else{
				Arrays.sort(difficultyCnts);//对数组从小到大排序  取下标为2的值 则是 有值的三个中第二大的
				return this.getDifficulty(difficultyCnts[2],difficultyCnts);
			}
		}else if(cnt0 == 0){//如果有四个有题目 
			/**
			 * 试卷中有四类难度，试卷整体难度为最多的一类试题数百分比减4类试题的平均数大于等于21.5%则试卷整体难度为提醒最多的一类题，若小于21.5%则试卷整体难度为其他3类题目数量最多的一类
			 * 
			 */
			//取数组中最大的那个数字  除以 数组之和  求出最大值占比  是长整形
			Long maxPercentage = Math.round(Fraction.getFraction(NumberUtils.max(difficultyCnts),sum(difficultyCnts)).doubleValue() * 1000);
			if(maxPercentage - 250 >= 215){
				return this.getDifficulty(NumberUtils.max(difficultyCnts),difficultyCnts);
			}else{
				Arrays.sort(difficultyCnts);//对数组从小到大排序  取下标为2的值 则是 有值的三个中第二大的
				return this.getDifficulty(difficultyCnts[2],difficultyCnts);
			}
		}
		
		throw new RuntimeException("试题数据异常，请重新提交！");
	}
	
	/**
	 * 计算数组之和
	 * @param difficultyCnts
	 * @return
	 */
	public Integer sum(int[] difficultyCnts){
		Integer result = 0;
		for(int i = 0;i<difficultyCnts.length;i++){
			result += difficultyCnts[i];
		}
		return result;
	}
	
	/**
	 * 返回数字对于下标的字母  String [] results = {"D","C","B","A"};
	 * @param difficultyCnt 要比较的值
	 * @param difficultyCnts 要比对的数组
	 * @return
	 */
	public String getDifficulty(int difficultyCnt,int[] difficultyCnts){
		String [] results = {"D","C","B","A"};
		String result = "";
		for(int i = 0; i < 4; i++){
			if(difficultyCnt == difficultyCnts[i]){
				result = results[i];
				break;
			}
		}
		return result;
	}
	
	/**
	 * 将主观题的明细题型信息添加到主计算信息中
	 * @param difficultyCnts
	 * @param questionCntDifficulty
	 * @return
	 */
	public void sumSubjectiveQuestions(int[] difficultyCnts,String[] questionCntDifficulty){
		//i ++ 
		for(int i = 0;questionCntDifficulty != null && i<questionCntDifficulty.length;i++){
			if("D".equals(questionCntDifficulty[i])){
				difficultyCnts[0] ++;
			}else if("C".equals(questionCntDifficulty[i])){
				difficultyCnts[1] ++;
			}else if("B".equals(questionCntDifficulty[i])){
				difficultyCnts[2] ++;
			}else if("A".equals(questionCntDifficulty[i])){
				difficultyCnts[3] ++;
			}
		}
	}

    @Override
	public Page<QuestionVo> findQuestionPage(QuestionVo questionVo,  int pageNumber, int pageSize) {
    	Page<QuestionVo> page = examPaperDao.findCloudClassQuestionPage(questionVo, pageNumber, pageSize);
    	return page;
	}
	
	@Override
	public void addExamPaper(ExamPaperVo examPaperVo) {
		//生成所有试卷的主体信息 包括ID值  要连同试卷一并返回
		examPaperVo.setId(UUID.randomUUID().toString().replace("-", ""));
		examPaperVo.setType(0);//默认都是作业
		//保存主表信息
		String sql = " INSERT INTO oe_exam_paper  ( id ,  type ,  course_id ,  paper_name ,  score ,  duration ,  difficulty ,  create_person ,  create_time ,  use_sum ) " +
					 " VALUES (?, ?, ?, ?, ?, ?, ?, ?, now(), 0)";
		dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, new Object[]{examPaperVo.getId(),
						examPaperVo.getType(),examPaperVo.getCourseId(),examPaperVo.getPaperName(),
						examPaperVo.getScore(),examPaperVo.getDuration(),examPaperVo.getDifficulty(),examPaperVo.getCreatePerson()});

		for(int i = 0; i<examPaperVo.getLastQuestionId().length ;i++){
			//保存明细表信息
			sql = " INSERT INTO oe_exam_paper_question  (id ,  paper_id ,  question_type ,  question_head ,  options ,  options_picture ,  attachment_url ,  difficulty ,  answer ,  solution ,  question_score, question_id )  " +
				  " select replace(UUID(),'-',''),?,oq.question_type,oq.question_head,oq.options,oq.options_picture,oq.attachment_url,oq.difficulty,oq.answer,oq.solution,?,oq.id " +
				  " from oe_question oq where oq.id = ? ";
			dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,new Object[]{examPaperVo.getId(),examPaperVo.getLastQquestionScore()[i],examPaperVo.getLastQuestionId()[i]});
		}
	}
    
	@Override
	public void updateExamPaper(ExamPaperVo examPaperVo) {
		//修改试卷的名字和难度
		String sql ="UPDATE oe_exam_paper " +
					"SET paper_name = ?, " +
					" 	 difficulty = ? " +
					"WHERE " +
					"	id = ?";
		dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, new Object[]{examPaperVo.getPaperName(),examPaperVo.getDifficulty(),examPaperVo.getId()});
		//把所有的副表信息都删除
		sql = "delete from oe_exam_paper_question where paper_id = ? ";
		dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,new Object[]{examPaperVo.getId()});
		//重新插入
		for(int i = 0; i<examPaperVo.getLastQuestionId().length ;i++){
			//保存明细表信息
			sql = "INSERT INTO oe_exam_paper_question  (id ,  paper_id ,  question_type ,  question_head ,  options ,  options_picture ,  attachment_url ,  difficulty ,  answer ,  solution ,  question_score,question_id )  " +
				  "select replace(UUID(),'-',''),?,oq.question_type,oq.question_head,oq.options,oq.options_picture,oq.attachment_url,oq.difficulty,oq.answer,oq.solution,?, oq.id " +
				  "from oe_question oq where oq.id = ? ";
			dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,new Object[]{examPaperVo.getId(),examPaperVo.getLastQquestionScore()[i],examPaperVo.getLastQuestionId()[i]});
		}
	}

	@Override
	public List<Map<String,Object>> getExamPaperQuestion(ExamPaperVo examPaperVo) throws Exception {
		int [] questionType = {0,1,2,3,4,5,6};
		List<Map<String,Object>> list = new ArrayList<>();
		String sql = "";
		for(int i = 0;i<questionType.length;i++){
			Map<String,Object> map = new HashMap<>();
			sql ="SELECT " +
				"	t.question_Id, " +
				"	t.paper_id, " +
				"	t.question_type, " +
				"	t.question_head, " +
				"	t.`options`, " +
				"	t.options_picture, " +
				"	t.attachment_url, " +
				"	t.difficulty, " +
				"	t.answer, " +
				"	ifnull(t.solution,'暂无') solution, " +
				"	t.question_score " +
				"FROM " +
				"	oe_exam_paper_question t where t.question_type = '" +questionType[i]+ "'" +
				" and t.paper_id = '"+examPaperVo.getId() + "'" +
				" order by t.id asc ";
			List<QuestionVo> questionList = dao.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,BeanPropertyRowMapper.newInstance(QuestionVo.class));
			if(questionList.size() > 0){//如果该题型有题
				
				for(int j = 0;j < questionList.size();j ++){
					if(5 == questionList.get(j).getQuestionType() || 6 == questionList.get(j).getQuestionType()){
						if(!StringUtils.isEmpty(questionList.get(j).getAnswer())){
		                	Attachment att = attachmentCenterService.getAttachmentObject(questionList.get(j).getAnswer());
		                	if(att!=null) {
		                        String fileName = att.getOrgFileName();
		                        questionList.get(j).setFileName(fileName);
		                	}
						}
					}
					
				}
				
				int score = 0;
				for(int j = 0;j < questionList.size(); j ++){
					score += Integer.valueOf(questionList.get(j).getQuestionScore());
				}
				map.put("questionType", questionType[i]);
				map.put("questionScore", score);
				map.put("questionList", questionList);

				list.add(map);
			}
		}
		return list;
	}

	@Override
	public void deletes(String[] ids) {
		for(String id:ids){
			String hqlPre = "select count(1) from oe_exam_paper where use_sum > 0 and id = ? ";
			if(dao.queryForInt(hqlPre, new Object[]{id}) > 0){
				throw new RuntimeException("试卷已经被使用,不能删除！");
			}
				
			hqlPre="delete from oe_exam_paper where id = ?";
			dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(hqlPre,new Object[]{id});
			hqlPre = "delete from oe_exam_paper_question where paper_id = ?";
			dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(hqlPre,new Object[]{id});
		}
	}

	@Override
	public List getQuestions(String kpointIds) {
		StringBuffer sql = new StringBuffer("SELECT " +
											"	oq.question_type questionType, " +
											"	count(DISTINCT oq.id) cnt " +
											"FROM " +
											"	oe_question oq,oe_question_kpoint oqk " +
											"WHERE oq.is_delete = 0 " +
											"AND oq.`status` = 1 " + 
											"and oq.id = oqk.question_id ");
		
		Map<String,Object> paramMap=new HashMap<String,Object>();
		
		if( kpointIds != null && kpointIds != ""){
			sql.append(" and oqk.kpoint_id in( ");
			String[] kpointId = kpointIds.split(","); 
			for(int i = 0;i<kpointId.length;i++){
				if(i!=0){
					sql.append(",");
				}
				sql.append("'"+kpointId[i]+"'");
			}
				sql.append(" ) ");
		}else{
				sql.append(" and 1 = 2 ");//如果为空就什么也查不出来
		}

		sql.append("GROUP BY oq.question_type ");
		//System.out.println("所有的问题数量："+sql.toString());
		return dao.getNamedParameterJdbcTemplate().queryForList(sql.toString(),paramMap);
	}

	@Override
	public List getQuestionsCnt(String kpointIds,String questionType) {
		StringBuffer sql = new StringBuffer(" SELECT " +
											"		oq.difficulty, " +
											"		count(DISTINCT oq.id) cnt " +
											" FROM " +
											"		oe_question oq, " +
											"		oe_question_kpoint oqk " +
											" WHERE " +
											"		oq.is_delete = 0 " +
											" AND oq.`status` = 1 " +
											" AND oq.id = oqk.question_id ");
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		if( kpointIds != null && kpointIds != ""){
			sql.append(" and oqk.kpoint_id in( ");
			String[] kpointId = kpointIds.split(","); 
			for(int i = 0;i<kpointId.length;i++){
				if(i!=0){
					sql.append(",");
				}
				sql.append("'"+kpointId[i]+"'");
			}
			sql.append(" ) ");
		}else{
			sql.append(" and 1 = 2 ");//如果为空就什么也查不出来
		}
		
		if(questionType != null && !"".equals(questionType)){
			sql.append(" and oq.question_type = :questionType ");
			paramMap.put("questionType", questionType);
		}
		
		sql.append(" group by difficulty ");
//		System.out.println("所有的问题数量："+sql.toString());
		return dao.getNamedParameterJdbcTemplate().queryForList(sql.toString(),paramMap);
	}

	@Override
	public String refreshDifficulty(ExamPaperVo examPaperVo) {
		//计算难度
		String questionIds = examPaperVo.getId();//从前台传过来已经选择的所有问题的ID拼接字符串
		String sql = "select upper(t.difficulty) difficulty,count(1) cnt from oe_question t where t.id in ("+questionIds+") group by upper(t.difficulty) ";
		List list = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql);
		//每个难度的题数
		int[] difficultyCnts = {0,0,0,0};//这个数组是用来计算试卷的难度系数		
		for(int i = 0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if("D".equals(map.get("difficulty").toString())){
				difficultyCnts[3] = Integer.valueOf(map.get("cnt").toString());
			}else if("C".equals(map.get("difficulty").toString())){
				difficultyCnts[2] = Integer.valueOf(map.get("cnt").toString());
			}else if("B".equals(map.get("difficulty").toString())){
				difficultyCnts[1] = Integer.valueOf(map.get("cnt").toString());
			}else if("A".equals(map.get("difficulty").toString())){
				difficultyCnts[0] = Integer.valueOf(map.get("cnt").toString());
			}
		}
		return reckonDifficulty(difficultyCnts);
	}
}
