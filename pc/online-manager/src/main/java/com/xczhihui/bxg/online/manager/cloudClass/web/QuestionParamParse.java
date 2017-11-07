package com.xczhihui.bxg.online.manager.cloudClass.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.util.bean.QuestionType;
import com.xczhihui.bxg.online.common.domain.QuesStore;
import com.xczhihui.bxg.online.manager.cloudClass.util.ParseUtil;

import com.google.gson.Gson;

class QuestionParamParse {
	static Logger logger = LoggerFactory.getLogger(QuestionParamParse.class);

	private HttpServletRequest request;
	private QuesStore question;

	private AttachmentCenterService attachmentCenterService;

	QuestionParamParse(AttachmentCenterService attachmentCenterService,
	        HttpServletRequest request, QuesStore question) {
		this.attachmentCenterService = attachmentCenterService;
		this.request = request;
		this.question = question;
	}

	void parseParam() throws ServletRequestBindingException {
		Integer type = question.getQuestionType();
		if (type == QuestionType.TYPE_SINGLE_CHOICE.getType()) {
			this.parseSingleChoice();// 单选
		} else if (type == QuestionType.TYPE_MULTIPLE_CHOICE.getType()) {
			this.parseMultipleChoice();// 多选
		}
		else if (type == QuestionType.TYPE_TRUE_FALSE.getType()) {
			this.parseTrueFalse();// 判断题
		}
		else if (type == QuestionType.TYPE_GAP_FILLING.getType()) {
			this.parseGapFilling();// 填空题
		}
		else if (type == QuestionType.TYPE_SHORT_ANSWER.getType()) {
			this.parseShortAnswer();// 简答题
		}
		else if (type == QuestionType.TYPE_CODE.getType()) {
			this.parseCode();// 代码题
		}
		else if (type == QuestionType.TYPE_PRACTICAL.getType()) {
			this.parsePractical();// 代码题
		}
	}

	/**
	 * 单选
	 * 
	 * @throws ServletRequestBindingException
	 */
	private void parseSingleChoice() throws ServletRequestBindingException {
		Gson gson = new Gson();
		List<String> os = new ArrayList<String>();
		List<String> ps = new ArrayList<String>();
		for (int i = 0;; i++) {
			String pn = "radioOptionValue" + (i + 1);
			String option = ServletRequestUtils.getStringParameter(request, pn,
			        null);
			String uploadify ="uploadify" + (i + 1);
			String pictures="";
			try{
				pictures= ServletRequestUtils.getRequiredStringParameter(request, uploadify);
			}catch (Exception e){
				pictures=null;
			}
			
			if (option == null&&pictures == null) {
				break;
			}
			logger.info("pn:{} option:{}", pn, option);
			os.add(option.trim());
			ps.add(pictures.trim());
			
			
		}
		question.setOptions(gson.toJson(os));
		question.setOptionsPicture(gson.toJson(ps));
		String answer = ServletRequestUtils.getRequiredStringParameter(request,
		        "radioOption");
		// 前端传入的是A,B,C...
		int i = answer.charAt(0) - 'A';
		// question.setAnswer(gson.toJson(os.get(i)));
		question.setAnswer(String.valueOf(i));
		logger.info("answer:{}", question.getAnswer());
	}

	/**
	 * 多选
	 * 
	 * @throws ServletRequestBindingException
	 */
	private void parseMultipleChoice() throws ServletRequestBindingException {
		Gson gson = new Gson();
		List<String> os = new ArrayList<String>();
		List<String> ps = new ArrayList<String>();
		for (int i = 0;; i++) {
			String pn = "multipleOptionValue" + (i + 1);
			String option = ServletRequestUtils.getStringParameter(request, pn,
			        null);
			String uploadify ="uploadify" + (i + 1);
			String pictures="";
			try{
				pictures= ServletRequestUtils.getRequiredStringParameter(request, uploadify);
			}catch (Exception e){
				pictures=null;
			}
			if (option == null&&pictures == null) {
				break;
			}
			os.add(option.trim());
			ps.add(pictures.trim());
			logger.info("pn:{} option:{}", pn, option);
		}
		question.setOptions(gson.toJson(os));
		question.setOptionsPicture(gson.toJson(ps));
		String[] answers = ServletRequestUtils.getStringParameters(request,
		        "multipleOption");
		List<String> as = new ArrayList<String>();
		for (String a : answers) {
			int i = a.charAt(0) - 'A';
			as.add(String.valueOf(i));
		}
		question.setAnswer(gson.toJson(as));
		logger.info("answer:{}", question.getAnswer());
	}

	/**
	 * 判断
	 * 
	 * @throws ServletRequestBindingException
	 */
	private void parseTrueFalse() throws ServletRequestBindingException {
		question.setOptions("");
		String answer = ServletRequestUtils.getRequiredStringParameter(request,
		        "judge");
		question.setAnswer(answer);
	}

	private void parseGapFilling() throws ServletRequestBindingException {
		Gson gson = new Gson();
		question.setOptions("");
		String content = question.getQuestionHead();
		List<String> as = ParseUtil.extractAnswers(content);
		content = ParseUtil.replaceAnswers(content, ParseUtil._REGEX,ParseUtil._BLANK);
//		content = content.replaceAll("</?[^>]+>", "");
		question.setQuestionHead(content);
		List<String> os = new ArrayList<String>();
		for (String a : as) {
			a= a.replaceAll("</?[^>]+>", "");
			os.add(a.trim());
		}
		question.setAnswer(gson.toJson(os));

		String contentText = question.getQuestionHeadText();
		List<String> asText = ParseUtil.extractAnswers(contentText);
		contentText = ParseUtil.replaceAnswers(contentText, ParseUtil._REGEX,ParseUtil._BLANK);
		question.setQuestionHeadText(contentText);
		List<String> osText = new ArrayList<String>();
		for (String a : asText) {
			osText.add(a.trim());
		}
		question.setAnswerText(gson.toJson(osText));
	}

	private void parseShortAnswer() {

	}

	private void parsePractical() throws ServletRequestBindingException {
		String answer = ServletRequestUtils.getRequiredStringParameter(request,
		        "answerAttachment");
		String attachmentUrl = ServletRequestUtils.getRequiredStringParameter(request,
		        "attachmentUrl");
		question.setAnswer(answer);
		question.setAttachmentUrl(attachmentUrl);
	}

	private void parseCode() throws ServletRequestBindingException {
		String answer = ServletRequestUtils.getRequiredStringParameter(request,
		        "answerAttachment");
		String attachmentUrl = ServletRequestUtils.getRequiredStringParameter(request,
		        "attachmentUrl");
		question.setAnswer(answer);
		question.setAttachmentUrl(attachmentUrl);
	}

}