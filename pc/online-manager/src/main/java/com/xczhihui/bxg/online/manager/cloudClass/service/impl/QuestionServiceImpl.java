package com.xczhihui.bxg.online.manager.cloudClass.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Chapter;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.QuesStore;
import com.xczhihui.bxg.online.manager.cloudClass.dao.QuestionDao;
import com.xczhihui.bxg.online.manager.cloudClass.service.QuestionService;
import com.xczhihui.bxg.online.manager.cloudClass.util.ParseUtil;
import com.xczhihui.bxg.online.manager.cloudClass.vo.QuestionOptionPicture;
import com.xczhihui.bxg.online.manager.cloudClass.vo.TreeNode;
import com.xczhihui.bxg.online.manager.common.util.ExcelPoiUtil;
import com.xczhihui.bxg.online.manager.utils.StringUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 题库service实现类
 * 
 * @author snow
 */
@Service("quesStoreService")
public class QuestionServiceImpl extends OnlineBaseServiceImpl implements QuestionService {
	@Autowired
	private QuestionDao quesStoreDao;
	
	@Override
	public List<TreeNode> findDataForTreeRight(String currentNodeIds) {
		
		
		
		
		// TODO Auto-generated method stub
		if (StringUtils.isNotEmpty(currentNodeIds)) {
			Map<String, Object> paramMap =null;
			String [] kpos=currentNodeIds.split(",");
			String str="";
			for(int i = 0; i < kpos.length; i++){
				str=kpos[i]+"','"+str;
			}
			str=str.substring(0, str.length()-3);
			String sql="SELECT DISTINCT(p.id),p.name ,p.parent_id as pId,p.level ,p.level as type ,p.level as contenttype  from oe_chapter z ,oe_chapter j ,oe_chapter p where z.id=j.parent_id and j.id=p.parent_id and p.id in( '"+str+"' ) UNION All "
					+ "SELECT DISTINCT(j.id), j.name ,j.parent_id as pId,j.level ,j.level as type ,j.level as contenttype  from oe_chapter z ,oe_chapter j ,oe_chapter p where z.id=j.parent_id and j.id=p.parent_id and p.id in( '"+str+"' )  UNION All "
							+ "SELECT DISTINCT(z.id),z.name ,z.parent_id as pId,z.level ,z.level as type ,z.level as contenttype  from oe_chapter z ,oe_chapter j ,oe_chapter p where z.id=j.parent_id and j.id=p.parent_id and p.id in( '"+str+"' ) ";
			List<TreeNode> treeNodes = dao.findEntitiesByJdbc(TreeNode.class, sql, paramMap);//章、节、知识点
			return treeNodes;
		}
		
		return null;
	}

	@Override
	public List<TreeNode> findDataForTreeLeft(String courseId) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap =null;
		String sql="select id,name ,parent_id as pId,level ,level as type ,level as contenttype from oe_chapter where is_delete=0 and course_id= "+courseId +" order by sort";
		List<TreeNode> treeNodes=dao.findEntitiesByJdbc(TreeNode.class, sql, paramMap);

		return treeNodes;
	}

	@Override
	public void addQuestion(QuesStore question, String knowledgeIds,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		this.dao.save(question);
		String[] array = knowledgeIds.split(",");
		List<String> list = Arrays.asList(array);
		if (!CollectionUtils.isEmpty(list)) {
			for (String kpId : list) {
				
                Map<String,Object> param=new HashMap<String,Object>();
                param.put("kpointId",kpId);
                param.put("questionId",question.getId());
                param.put("courseId",question.getCourseId());
                param.put("id", UUID.randomUUID().toString().replace("-",""));
				String sql="insert into oe_question_kpoint (id,kpoint_id,question_id,course_id) values (:id,:kpointId,:questionId,:courseId);";
				dao.getNamedParameterJdbcTemplate().update(sql, param);
				
			}
		}
	}

	@Override
	public Page<QuesStore> findPage(QuesStore searchVo, int currentPage,
			int pageSize) {
		// TODO Auto-generated method stub
		Page<QuesStore> res=quesStoreDao.findQuesPage(searchVo, currentPage, pageSize);
		for(QuesStore ques:res.getItems()){
			ques.setChapterCount(getKnowledgePointsName(ques.getId()).size()+"");
			ques.setChapterName(getKnowledgePointsNameString(ques.getId()));
		}
		return res;
	}

	@Override
	public QuesStore findQuestion(String id) {
		// TODO Auto-generated method stub
		return quesStoreDao.get(id, QuesStore.class);
	}
	@Override
	public List<String> getKnowledgePointsName(String id) {
		List<String> list = new ArrayList<String>();
		list = quesStoreDao.getKnowledgePointsName(id);
		return list;

	}

	@Override
	public String getKnowledgePointsNameString(String id) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list = quesStoreDao.getKnowledgePointsName(id);
		return ParseUtil.arrayToString(list, ",");
	}

	@Override
	public String getKnowledgePointsIdString(String id) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		list = quesStoreDao.getKnowledgePointsId(id);
		return ParseUtil.arrayToString(list, ",");
	}

	@Override
	public List<QuestionOptionPicture> getQopList(String questionId,
			String questionType, String optionName) {
		// TODO Auto-generated method stub
		QuesStore ques=findQuestion(questionId);
		String Str[]=new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};
		List<QuestionOptionPicture> qps=new  ArrayList<QuestionOptionPicture>();
		if(ques!=null){
			ques.getOptionsPicture();
			if(ques.getOptionsPicture()!=null){
				Gson g = new GsonBuilder().create();
				ArrayList<String> array = g.fromJson(ques.getOptionsPicture(), ArrayList.class);
				for(int i=0;i<array.size();i++){
					if(!"".equals(array.get(i))){
						QuestionOptionPicture qp =new QuestionOptionPicture();
						qp.setOptionName(Str[i]);
						qp.setQuestionId(questionId);
						qp.setQuestionType(ques.getQuestionType());
						qp.setAttachmentId( array.get(i));
						qps.add(qp);
					}
				}
			}
		}
		return qps;
	}

	@Override
	public void updateQuestion(QuesStore question) {
		// TODO Auto-generated method stub
		
		String sql = "delete from oe_question_kpoint where question_id =? ";
		dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, question.getId());
		
		if(!"".endsWith(question.getChapterId())){
			String[] array = question.getChapterId().split(",");
			List<String> list = Arrays.asList(array);
			if (!CollectionUtils.isEmpty(list)) {
				for (String kpId : list) {
					
	                Map<String,Object> params=new HashMap<String,Object>();
	                params.put("kpointId",kpId);
	                params.put("questionId",question.getId());
	                params.put("courseId",question.getCourseId());
	                params.put("id", UUID.randomUUID().toString().replace("-",""));
					String sqlInsert="insert into oe_question_kpoint (id,kpoint_id,question_id,course_id) values (:id,:kpointId,:questionId,:courseId);";
					dao.getNamedParameterJdbcTemplate().update(sqlInsert, params);
					
				}
			}
		}
		dao.update(question);
	}

	@Override
	public void deleteById(String id,String chapterIds) {
		// TODO Auto-generated method stub
		QuesStore entity=dao.get(id, QuesStore.class);
		if(chapterIds==null||"".equals(chapterIds)){
			entity.setDelete(true);
			dao.update(entity);
		}else{
			String[] array = chapterIds.split(",");
			for(String chapterId :array){
				String sql = "delete from oe_question_kpoint where question_id =? and kpoint_id=?";
				dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, entity.getId(),chapterId);
			}
		}
		
	}
	
	@Override
	public void updateEnbaleStatus(String id) {
		// TODO Auto-generated method stub
		QuesStore entity=dao.get(id,QuesStore.class);
		entity.setStatus(1);
		dao.update(entity);
	}
	
	@Override
	public void updateDisableStatus(String id) {
		// TODO Auto-generated method stub
		QuesStore entity=dao.get(id,QuesStore.class);
		entity.setStatus(0);
		dao.update(entity);
	}
	
	
	
	@Override
	public StringBuffer checkXls(Sheet sheet, List<QuesStore> excelList,Set<String> zipSet,String courseName) {
		String currentUserId = UserHolder.getCurrentUser().getId();
		
		// 校验数据
		int totalrow = sheet.getLastRowNum();
		if (totalrow < 1) { // 说明只有标题，没有数据
			return new StringBuffer("错误: 模板中的数据为空！");
		}
		Row titleRow = sheet.getRow(0);
		if (titleRow == null) {
			return new StringBuffer("错误：模板格式不正确！");
		}
		// 错误信息存放变量
		StringBuffer content = new StringBuffer();
		// 用于判断excel中是否存在相同的记录
		// boolean hasNull = false;
		// Excel导入模版6列数据
		int minCol = 8;

		for (int i = 1; i <= totalrow; i++) {
			Row row = sheet.getRow(i);
			if (row == null || ExcelPoiUtil.checkAllNull(row)) { // 空行 不处理
				// hasNull = true;
				continue;
			}
			boolean ischecked = true;
			int rowIndex = (i + 1);
			if (row.getLastCellNum() < minCol - 1) {
				content.append("第" + rowIndex + "行列数少于" + minCol+ "<br/>");
				continue;
			}
			int index = 0;
			
			String type = getStringCellValue(row, index++);
			if (!isEmpty(rowIndex, "题型", type, content)) {
				ischecked = false;
			}
			String  name = "";
			String version="";
			String course = getStringCellValue(row, index++);
			name=course;
			if (!isEmpty(rowIndex, " 课程名称", course, content)) {
				ischecked = false;
			} 
			if(!isEmptyKsystem(rowIndex, " 课程名称", name, content)){
				break;
			}
			
			List<Course> s = dao.findEntitiesByJdbc(Course.class, "select * from oe_course c where c.is_delete=0 and c.type is NULL and c.grade_name = '"+name+"'", new HashMap<String,Object>());
			if(!courseName.equals(name)){
				content.append(" {\"第" + rowIndex + "行 课程名称 " +  name+version+ " 不符合当前课程！\"}");
				
			}
			if(s!=null && s.size()>1){
				content.append(" {\"第" + rowIndex + "行 课程名称 " +  name+version+ " 不唯一！\"}");
				break;
			}else if(s==null || s.size()==0){
				content.append(" {\"第" + rowIndex + "行  课程名称 " +  name+version +" 不存在！\"}");
				break;
			}
			
			
			String ksysId = s.get(0).getId()+"";
			if (row.getCell(2) != null) {// 知识点
				row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
			}
			String knowledgePoints = getStringCellValue(row, index++);
			StringBuilder knowledgePointIds = new StringBuilder();
			if (!StringUtil.checkNull(name)) {
//				if (!isEmpty(rowIndex, "知识点", knowledgePoints, content)) {
//					ischecked = false;
//				} else {
				if(knowledgePoints!=null && !"".equals(knowledgePoints)){
					String[] array = knowledgePoints.split("\\|");
					if(array!=null && array.length>0){
						for (String string : array) {
							if (!isKnowledgeTemplateNotExist(rowIndex, "知识点",ksysId,string, content)) {
								ischecked = false;
							}  else {
								 Map<String, Object> paramMap = new HashMap<String, Object>();
								 String sql="select * from oe_chapter where level=4 and name = '"+string+"' and course_id="+Integer.parseInt(ksysId);
								 List<Chapter> k = dao.findEntitiesByJdbc(Chapter.class, sql, paramMap);
								if (k != null){
									knowledgePointIds.append(k.get(0).getId()).append(",");
								}
							}
						}
					}
				}
//				}
			}

			

			if (row.getCell(4) != null) {// 题干
				row.getCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);
			}
			String questionContent = getStringCellValue(row, index++);
			if (!isEmpty(rowIndex, "题干", questionContent, content)) {
				ischecked = false;
			}

			String option = getStringCellValue(row, index++);
			if (!("填空题".equals(type) || "简答题".equals(type) || "代码题".equals(type) || "实操题".equals(type))) {
				if (!isEmpty(rowIndex, "选项", option, content)) {
					ischecked = false;
				} else if ((("多选题".equals(type)) || ("单选题".equals(type)))
				        && (!isOptionError(rowIndex, "选项", option, content))) {
					ischecked = false;
				}
			}

			if (row.getCell(6) != null) {// 答案
				row.getCell(6).setCellType(HSSFCell.CELL_TYPE_STRING);
			}
			String answer = getStringCellValue(row, index++);
			if (!("填空题".equals(type) || "代码题".equals(type) || "实操题".equals(type))) {
				if (!isEmpty(rowIndex, "答案", answer, content)) {
					ischecked = false;
				} else if ("单选题".equals(type) && !isAnswerError(rowIndex, "答案", option, answer,content)) {
					ischecked = false;
				}
			}
			if (row.getCell(7) != null) {// 答案说明
				row.getCell(7).setCellType(HSSFCell.CELL_TYPE_STRING);
			}
			String status="";
			if (row.getCell(8) != null) {// 答案说明
				row.getCell(8).setCellType(HSSFCell.CELL_TYPE_STRING);
				 status = row.getCell(8).getStringCellValue();
				if("Y".equals(status)){
					status="1";
				}else{
					status="0";
				}
			}
			String solution = getStringCellValue(row, index++);
			if (!ischecked) {
				continue;
			}
			String difficult = getStringCellValue(row, index++);
			if (!isEmpty(rowIndex, "难度", difficult, content)) {
				ischecked = false;
			}
			
			QuesStore question = new QuesStore();

			question.setCourseId(s.get(0).getId()+"");
			if(knowledgePointIds.length()>0){
				question.setChapterId(knowledgePointIds.toString());
			}
			
			
			
			
			int questionType = 0;
			if ("单选题".equals(type)) {
				questionType = 0;
			} else if ("多选题".equals(type)) {
				questionType = 1;
			} else if ("判断题".equals(type)) {
				questionType = 2;
			} else if ("填空题".equals(type)) {
				questionType = 3;
			} else if ("简答题".equals(type)) {
				questionType = 4;
			} else if ("代码题".equals(type)) {
				questionType = 5;
			} else if ("实操题".equals(type)) {
				questionType = 6;
			}

			Gson gson = new Gson();
			question.setQuestionType(questionType);
			if (questionType == 3) {// 填空
				question.setOptions("");
				if(!questionContent.contains("【")){
					content.append("第" + rowIndex + "行，填空题无答案！<br/>");
				}
				
				if(!questionContent.contains("】")){
					content.append("第" + rowIndex + "行，填空题答案格式不正确！<br/>");
				}
				
				List<String> as = ParseUtil.extractAnswers(questionContent);
				if(as.size()<=0){
					content.append("第" + rowIndex + "行，填空题答案为空！<br/>");
				}
				questionContent = ParseUtil.replaceAnswers(questionContent,ParseUtil._REGEX, "");
				questionContent = questionContent.replaceAll("<", "&lt;");// 导入时字符串转义
				/**
				 * 导入后匹配编辑器回车和空格的格式 begin
				 */
				String[] questionContentArray = questionContent.split(("\\n"));
				StringBuffer questionContentStringBuffer = new StringBuffer();
				for (int j = 0; j < questionContentArray.length; j++) {
					if (j > 0) {
						questionContentStringBuffer.append("<p>").append(questionContentArray[j].replaceAll(" ","&nbsp;")).append("</p>");
					} else {
						questionContentStringBuffer.append(questionContentArray[j].replaceAll(" ","&nbsp;"));
					}
				}
				/**
				 * 导入后匹配编辑器回车和空格的格式 end
				 */
				question.setQuestionHead(questionContentStringBuffer.toString());
				question.setQuestionHeadText(questionContent);
				List<String> os = new ArrayList<String>();
				for (String a : as) {
					a= a.replaceAll("</?[^>]+>", "");
					os.add(a.trim());
				}
				Gson sgson = new GsonBuilder().disableHtmlEscaping().create();
				String js = sgson.toJson(os);
				question.setAnswerText(js);
				js=js.replace(',', '^');
				js = js.replaceAll("<", "&lt;");
				question.setAnswer(js);
			} else {
				questionContent = questionContent.replaceAll("<", "&lt;");// 导入时字符串转义
				/**
				 * 导入后匹配编辑器回车和空格的格式 begin
				 */
				String[] questionContentArray = questionContent.split(("\\n"));
				StringBuffer questionContentStringBuffer = new StringBuffer();
				for (int j = 0; j < questionContentArray.length; j++) {
					if (j > 0) {
						questionContentStringBuffer.append("<p>").append(questionContentArray[j].replaceAll(" ","&nbsp;")).append("</p>");
					} else {
						questionContentStringBuffer.append(questionContentArray[j].replaceAll(" ","&nbsp;"));
					}

				}
				/**
				 * 导入后匹配编辑器回车和空格的格式 end
				 */
				question.setQuestionHead(questionContentStringBuffer.toString());
				question.setQuestionHeadText(questionContent);
			}

			if (questionType == 0) {// 单选
				List<String> os = new ArrayList<String>();
				String[] array = option.split("\\|");
				for (String string : array) {
					if (!StringUtil.checkNull(string)) {
						String optionval=string.substring(string.indexOf(".") + 1,string.length());
						os.add(optionval);
					}
				}
				question.setOptions(gson.toJson(os));

				int m = answer.charAt(0) - 'A';
				question.setAnswer(String.valueOf(m));
			} else if (questionType == 1) {// 多选
				List<String> os = new ArrayList<String>();
				String[] array = option.split("\\|");
				for (String string : array) {
					if (!StringUtil.checkNull(string)) {
						os.add(string.substring(string.indexOf(".") + 1,string.length()));
					}
				}
				question.setOptions(gson.toJson(os));

				List<String> as = new ArrayList<String>();
				String[] answers = answer.split("\\|");
				for (String a : answers) {
					int n = a.charAt(0) - 'A';
					as.add(String.valueOf(n));
				}
				question.setAnswer(gson.toJson(as));
			} else if (questionType == 2) {// 判断
				if("对".equals(answer) || "错".equals(answer)){
					question.setAnswer(answer);
					question.setAnswerText(answer);
				}else{
					content.append("判断题答案只能是对或错");
				}
			} else if (questionType == 4) {// 简答
				/**
				 * 导入后匹配编辑器回车和空格的格式 begin
				 */
				answer = answer.replaceAll("<", "&lt;");// 导入时字符串转义
				String[] answerArray = answer.split(("\\n"));
				StringBuffer answerStringBuffer = new StringBuffer();
				for (int j = 0; j < answerArray.length; j++) {
					if (j > 0) {
						answerStringBuffer.append("<p>").append(answerArray[j].replaceAll(" ", "&nbsp;")).append("</p>");
					} else {
						answerStringBuffer.append(answerArray[j].replaceAll(" ", "&nbsp;"));
					}
				}
				/**
				 * 导入后匹配编辑器回车和空格的格式 end
				 */
				question.setAnswer(answerStringBuffer.toString());
				question.setAnswerText(answer);
			} else if (questionType == 6 && StringUtils.isNotBlank(answer)) {// 实操
				String v = answer.substring(answer.lastIndexOf(".") + 1,answer.length()).toUpperCase();
				if ("TXT".equals(v) || "DOC".equals(v) || "DOCX".equals(v)
				        || "XLS".equals(v) || "XLSX".equals(v)
				        || "ZIP".equals(v) || "RAR".equals(v)
				        || "BMP".equals(v) || "GIF".equals(v)
				        || "JPEG".equals(v) || "PNG".equals(v)
				        || "SVG".equals(v) || "JPG".equals(v)) {
					if (zipSet.contains(answer)) {
						question.setAnswer(answer);
					} else {
						content.append("第" + rowIndex + "行附件（ " + answer+ "）在压缩包不存在！<br/>");
					}
				} else {
					content.append("第" + rowIndex + "行附件（ " + answer+ "）格式错误！请上传txt,word,excel,zip,rar或者图片的附件格式.<br/>");
				}
			} else if (questionType == 5 && StringUtils.isNotBlank(answer)) {// 代码
				String v = answer.substring(answer.lastIndexOf(".") + 1,answer.length()).toUpperCase();
				if ("TXT".equals(v) || "DOC".equals(v) || "DOCX".equals(v)
				        || "XLS".equals(v) || "XLSX".equals(v)
				        || "ZIP".equals(v) || "RAR".equals(v)) {
					if (zipSet.contains(answer)) {
						question.setAnswer(answer);
					} else {
						content.append("第" + rowIndex + "行附件（ " + answer + "）在压缩包不存在！<br/>");
					}
				} else {
					content.append("第" + rowIndex + "行附件（ " + answer+ "）格式错误！请上传txt,word,excel,zip,rar的附件格式.<br/>");
				}
			}
			if(!"".equals(difficult)&&!"简单".equals(difficult)&&!"一般".equals(difficult)&&!"困难".equals(difficult)&&!"非常困难".equals(difficult)){
				content.append("第" + rowIndex + "难易度错误");
			}
			question.setDifficulty(difficult);
			switch(difficult){
				case "简单":question.setDifficulty("A");
					break;
				case "一般":question.setDifficulty("B");
					break;
				case "困难":question.setDifficulty("C");
					break;
				case "非常困难":question.setDifficulty("D");
					break;
				
			}
			/**
			 * 导入后匹配编辑器回车和空格的格式 begin
			 */
			StringBuffer solutionStringBuffer = new StringBuffer();
			if (!StringUtil.checkNull(solution)) {
				solution = solution.replaceAll("<", "&lt;");// 导入时字符串转义
				String[] solutionArray = solution.split(("\\n"));

				for (int j = 0; j < solutionArray.length; j++) {
					if (j > 0) {
						solutionStringBuffer.append("<p>").append(solutionArray[j].replaceAll(" ","&nbsp;")).append("</p>");
					} else {
						solutionStringBuffer.append(solutionArray[j].replaceAll(" ", "&nbsp;"));
					}
				}
			}
			/**
			 * 导入后匹配编辑器回车和空格的格式 end
			 */
			question.setSolution(solutionStringBuffer.toString());
			question.setSolutionText(solution);
		
			question.setStatus(Integer.valueOf(status));
			question.setCreatePerson(currentUserId);
			
			QuesStore que = new QuesStore();
			org.springframework.beans.BeanUtils.copyProperties(question, que);
			excelList.add(que);
		}
		// 错误处理
		if (content != null && !"".equals(content.toString())) {
			return content;
		}
		return null;

	}

	private String getStringCellValue(Row row, int index) {
		try {
			return StringUtils.trimToEmpty(row.getCell(index)
			        .getStringCellValue());
		} catch (Exception e) {
			return null;
		}
	}
	
	private boolean isEmpty(int rowIndex, String colName, String value, StringBuffer content) {
		if (StringUtils.isEmpty(value)) {
			content.append("第" + rowIndex + "行 " + colName+ "不能为空！<br/>");
			return false;
		}
		return true;
	}
	
	/**
	 * 课程是否存在
	 * 
	 * @param rowIndex
	 * @param colName
	 * @param value
	 * @param content
	 * @return
	 */
	private boolean isCourseNotExist(int rowIndex, String colName,String value,StringBuffer content) {
		Course ks = dao.findOneEntitiyByProperty(Course.class, "gradeName", value);
		//KSystem ks = system.isExsitName(value, null);
		if (null == ks) {
			content.append(" {\"第" + rowIndex + "行 " + colName + value+ "不存在！\"}");
			return false;
		}
		return true;
	}
	
	private boolean isEmptyKsystem(int rowIndex, String colName, String value, StringBuffer content) {
		if (StringUtils.isEmpty(value)) {
			content.append("第" + rowIndex + "行 " + colName+ "不能为空！<br/>");
			return false;
		}else{
			Pattern pattern = Pattern.compile("^(?!_)(?!.*?_$)[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w\\[\\]\\+]{0,}$");  
			if (!pattern.matcher(value.trim()).matches()) {
				content.append("第" + (rowIndex + 1) + "行 " + colName + "格式错误！<br/>");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 知识点是否存在
	 * 
	 * @param rowIndex
	 * @param colName
	 * @param value
	 * @param content
	 * @return
	 */
	private boolean isKnowledgeTemplateNotExist(int rowIndex, String colName,String ksysid,
	        String value,StringBuffer content) {
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		String sql="select * from oe_chapter where level =4 and name = '"+value+"' and course_id="+Integer.parseInt(ksysid);
		List<Chapter> kPoint = dao.findEntitiesByJdbc(Chapter.class, sql, paramMap);
		/*KPoint kPoint = kPointService.getByKsysIdKpointName(value, ksysid);*/
		if (kPoint == null) {
			content.append(" {\"第" + rowIndex + "行 " + colName + value+ "不存在！\"}");
			return false;
		}
		return true;
	}
	
	private boolean isOptionError(int rowIndex, String colName, String option,
	        StringBuffer content) {
		List<String> os = new ArrayList<String>();
		String[] array = option.split("\\|");
		Map<String,String> hasABCDoption = new HashMap<String,String>();
		for (String string : array) {
			int k = string.indexOf(".");
			if(k<0){
				content.append(" {\"第" + rowIndex + "行 " + colName+ "选项错误！\"}");
			}else{
				String options = string.substring(0, string.indexOf("."));
				if("A".equals(options)){
					hasABCDoption.put("hasA", "A");
				}
				if("B".equals(options)){
					hasABCDoption.put("hasB", "B");
				}
				
				if("C".equals(options)){
					hasABCDoption.put("hasC", "C");
				}
				if("D".equals(options)){
					hasABCDoption.put("hasD", "D");
				}
				String optionval=string.substring(string.indexOf(".") + 1, string.length());
				if("".equals(optionval) || optionval==null){
					content.append(" {\"第" + rowIndex + "行 " + colName+ "答案有空的！\"}");
				}else{
					os.add(optionval);
				}
			}
			
		}
		
		
		if (os.size() < 2) {
			content.append("{\"第" + rowIndex + "行 " + colName+ "格式错误！\"}");
			return false;
		}
		
		if(hasABCDoption.size()>0){
			String a = hasABCDoption.get("hasA");
			String b = hasABCDoption.get("hasB");
			String c = hasABCDoption.get("hasC");
			String d = hasABCDoption.get("hasD");
			if("".equals(a) || a==null){
				content.append("{\"第" + rowIndex + "行 " + colName+ "没有A选项！\"}");
			}
			
			if("".equals(b) || b==null){
				content.append("{\"第" + rowIndex + "行 " + colName+ "没有B选项！\"}");
			}
			if("".equals(c) || c==null){
				content.append("{\"第" + rowIndex + "行 " + colName+ "没有C选项！\"}");
			}
			if("".equals(d) || d==null){
				content.append("{\"第" + rowIndex + "行 " + colName+ "没有D选项！\"}");
			}
		}
		return true;
	}

	private boolean isAnswerError(int rowIndex, String colName, String option, String answer,StringBuffer content) {
		List<String> os = new ArrayList<String>();
		String[] array = option.split(";");
		for (String string : array) {
			os.add(string.substring(string.indexOf(".") + 1, string.length()));
		}
		int i = answer.charAt(0) - 'A';
		if (((os.size() < i) || i < 0) && os.size() > 3) {
			content.append(" {\"第" + rowIndex + "行 " + colName + "格式错误！\"}");
			return false;
		}
		return true;

	}
	
	@Override
	public String saveBatch(List<QuesStore> excelList,Map<String, byte[]> zipMap, String ticket) {
		try {
			// 保存数据
			for (int i = 0; i < excelList.size(); i++) {
				QuesStore q = excelList.get(i);
				q.setCreateTime(new Date());
				/*// 处理附件题
				if (q.getQuestionType() == 5 || q.getQuestionType() == 6) {
					String name = q.getAnswer();
					if (StringUtils.isNotBlank(name)) {
						Attachment attachment = attachmentCenterService
						        .addAttachment(UserHolder.getCurrentUser().getId(),AttachmentType.QUESTION, name,zipMap.get(name), "zip",ticket);

						q.setAnswer(attachment.getId());
					}
				}*/
				String KponitString = q.getChapterId();
				addQuestion(q, KponitString, null);
			}
			return null;
		} catch (Exception e) {
			return e.getMessage();
		}

	}
}
