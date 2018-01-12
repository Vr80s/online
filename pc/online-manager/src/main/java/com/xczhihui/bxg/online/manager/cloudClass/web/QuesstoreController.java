package com.xczhihui.bxg.online.manager.cloudClass.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.shiro.codec.Base64;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.domain.OperationLog;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.FileUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.annotation.OperationLogger;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.common.domain.QuesStore;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.service.QuestionService;
import com.xczhihui.bxg.online.manager.cloudClass.util.ParseUtil;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.QuestionOptionPicture;
import com.xczhihui.bxg.online.manager.cloudClass.vo.QuestionVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.TreeNode;
import com.xczhihui.bxg.online.manager.common.util.ExcelPoiUtil;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.StringUtil;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.web.utils.UCCookieUtil;

/**
 * 后台题库
 * 
 * @author yxd
 * @date 2015年11月20日
 */
@Controller
@RequestMapping("question")
public class QuesstoreController {
	private static final String PATH = "cloudClass/";
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private QuestionService quesStoreService;// 题库
	
	@Autowired
	private AttachmentCenterService attachmentCenterService;
	
	@Autowired
	private CourseService courseService;//课程
	/**
     * 获取视屏资源列表信息，根据课程ID号查找
     * @return
     */
    @RequestMapping(value = "/findQuesList")
    @ResponseBody
    public TableVo findVideoList(TableVo tableVo){
    	  int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          String params = tableVo.getsSearch();
          Groups groups = Tools.filterGroup(params);
          Group courseIdSearch = groups.findByName("courseId");
          Group chapterIdSearch = groups.findByName("chapterId");
          
          Group questionTypeSearch = groups.findByName("search_questionType");
          Group questionHeadTextSearch = groups.findByName("search_questionHeadText");
       
          QuesStore searchVo=new QuesStore();
          searchVo.setCourseId(courseIdSearch.getPropertyValue1().toString());
          if(chapterIdSearch!=null){
        	  searchVo.setChapterId(chapterIdSearch.getPropertyValue1().toString());
          }
          if(questionTypeSearch!=null){
        	  searchVo.setQuestionType(Integer.parseInt(questionTypeSearch.getPropertyValue1().toString()));
          }
          if(questionHeadTextSearch!=null){
        	  searchVo.setQuestionHeadText(questionHeadTextSearch.getPropertyValue1().toString());
          }
          Page<QuesStore> page= quesStoreService.findPage(searchVo, currentPage, pageSize);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
    	
    }
	
	/**
	 * 新增页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toAdd")
	public String toAdd(HttpServletRequest request, Model model) {

		String courseId = ServletRequestUtils.getStringParameter(request,
		        "courseId", "-1");
		String courseName = ServletRequestUtils.getStringParameter(request,
		        "courseName", "-1");
		String pId = ServletRequestUtils.getStringParameter(request,
		        "pId", "-1");
		String currentNodeId = ServletRequestUtils.getStringParameter(request,
		        "currentNodeId", "-1");
		String currentNodeName = ServletRequestUtils.getStringParameter(request,
		        "currentNodeName", "-1");
		request.setAttribute("courseId", courseId);
		request.setAttribute("courseName", courseName);
		request.setAttribute("pId", pId);
		request.setAttribute("currentNodeId", currentNodeId);
		request.setAttribute("currentNodeName", currentNodeName);

		return PATH + "toAdd";
	}
	
	/**
	 * 编辑页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toEdit")
	public String toEdit(HttpServletRequest request, Model model, String id)throws Exception {
		QuesStore question = quesStoreService.findQuestion(id);
		model.addAttribute("question", question);

		List<CourseVo> kSystem = courseService.findCourseById(Integer.parseInt(question.getCourseId()));
		model.addAttribute("kSystem", kSystem.get(0));
		String kpointNames = "";
		kpointNames = quesStoreService.getKnowledgePointsNameString(id);
		model.addAttribute("kpointNames", kpointNames);
		String knowledgeIds = "";
		knowledgeIds = quesStoreService.getKnowledgePointsIdString(id);
		model.addAttribute("knowledgeIds", knowledgeIds);
		if (question.getQuestionType() == 5 || question.getQuestionType() == 6) {
			Attachment attachment = this.getAttachment(question.getAnswer());
			if (attachment != null) {
				model.addAttribute("orgFileName", attachment.getOrgFileName());
				model.addAttribute("attachmentId", attachment.getId());
			}
		}
		return PATH + "toEdit";
	}
	
	/**
	 * 通过题目id,题目类型,选项编号获取图片资源
	 * 
	 * @param questionId
	 * @param QuestionType
	 * @param optionName
	 * @return
	 */
	@RequestMapping(value = "getQopList")
	@ResponseBody
	public List<QuestionOptionPicture> getQopList(String questionId,
	        String questionType, String optionName) {
		List<QuestionOptionPicture> list = new ArrayList<QuestionOptionPicture>();
		list = quesStoreService.getQopList(questionId,
		        questionType, optionName);
		return list;
	}

	/**
	 * 修改题目
	 * 
	 * @param question
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws IOException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	@OperationLogger(idparameterName = "id",description = "编辑题",
    operationType = OperationLog.OP_EDIT,
    systemType = OperationLog.SYSTEM_KNOWLEDGE_CENTER, tableName = "question")
	public ResponseObject update(HttpServletRequest request, QuesStore question)throws ServletRequestBindingException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0;; i++) {
			String pn = "";
			if (question.getQuestionType() == 0) {
                pn = "radioOptionValue" + (i + 1);
            }
			if (question.getQuestionType() == 1) {
                pn = "multipleOptionValue" + (i + 1);
            }
			String option = ServletRequestUtils.getStringParameter(request, pn,null);
			if (option == null) {
				break;
			}
			String uploadify = "uploadify" + (i + 1);
			String optionPicture = ServletRequestUtils.getRequiredStringParameter(request, uploadify);
			if (!StringUtils.isEmpty(optionPicture)) {
				map.put(ParseUtil.optionArray[i], optionPicture);
			}
		}
		ResponseObject responseObj = new ResponseObject();
		String createTimeStr = ServletRequestUtils.getRequiredStringParameter(request, "createTimeStr");
		question.setCreateTime(DateUtil.parseDate(createTimeStr,"yyyy-MM-dd HH:mm:ss"));
		new QuestionParamParse(attachmentCenterService, request, question).parseParam();

		int type = question.getQuestionType();
		if(type==3){
			String ans = question.getAnswer();
			ans = ans.replace(',', '^');
			question.setAnswer(ans);
//			question.setAnswerText(ans);
		}
		
		QuesStore q = quesStoreService.findQuestion(question.getId());
		String currentUserId = UserHolder.getCurrentUser().getId();
		question.setCreatePerson(currentUserId);
		question.setCreateTime(q.getCreateTime());
		
		quesStoreService.updateQuestion(question);
		
		responseObj.setSuccess(true);
		responseObj.setResultObject(question);
		responseObj.setErrorMessage("操作成功");
		return responseObj;
	}
	
	
	/**
	 * 通过附件id获取附件对象
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getAttachment")
	@ResponseBody
	public Attachment getAttachment(String id) throws Exception {
		if (StringUtil.checkNull(id)) {
			return null;
		}
		Attachment a = attachmentCenterService.getAttachmentObject(id);
		return a;
	}
	
	/**
	 * 获取知识点右侧边树
	 * 
	 * @param knowledgeIds
	 *           知识点ID
	 * @return
	 */
	@RequestMapping(value = "findDataForTreeRight")
	@ResponseBody
	public List<TreeNode> findDataForTreeRight(String currentNodeId) {
		if (StringUtils.isEmpty(currentNodeId)) {
			return null;
		}
		return quesStoreService.findDataForTreeRight(currentNodeId);
	}
	
	/**
	 * 获取知识点左侧边树
	 * 
	 * @param courseId
	 *          
	 * @return List<TreeNode>
	 */
	@RequestMapping(value = "findDataForTreeLeft")
	@ResponseBody
	public List<TreeNode> findDataForTreeLeft(String courseId) {
		if (StringUtils.isEmpty(courseId)) {
			return null;
		}
		return quesStoreService.findDataForTreeLeft(courseId);
	}
	
	/**
	 * 保存题目数据，包括题目知识点对应数据，如果是单选和多选，保存选项对应的图片数据
	 * 
	 * @param request
	 * @param question
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws IOException
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject add(HttpServletRequest request, QuestionVo vo)throws ServletRequestBindingException, IOException {
		ResponseObject responseObj = new ResponseObject();
		String knowledgeIds = vo.getChapterId();
		Map<String, String> map = new HashMap<String, String>();
		
		QuesStore question = new QuesStore();
		BeanUtils.copyProperties(vo, question);
		question.setStatus(Integer.parseInt(vo.getStatus()));
		for (int i = 0;; i++) {
			String pn = "";
			if (question.getQuestionType() == 0) {
                pn = "radioOptionValue" + (i + 1);
            }
			if (question.getQuestionType() == 1) {
                pn = "multipleOptionValue" + (i + 1);
            }
			String option = ServletRequestUtils.getStringParameter(request, pn,null);
			if (option == null) {
				break;
			}
			String uploadify = "uploadify" + (i + 1);
			String optionPicture = ServletRequestUtils.getRequiredStringParameter(request, uploadify);
			if (!StringUtils.isEmpty(optionPicture)) {
				map.put(ParseUtil.optionArray[i], optionPicture);
			}
		}

		String currentUserId = UserHolder.getCurrentUser().getId();
		question.setCreatePerson(currentUserId);
		question.setCreateTime(new Date());
		question.setChapterName(vo.getKnowledgeNames());
		new QuestionParamParse(attachmentCenterService, request, question).parseParam();
		int type = question.getQuestionType();
		if(type==3){
			String ans = question.getAnswer();
			ans = ans.replace(',', '^');
			question.setAnswer(ans);
//					question.setAnswerText(ans);
		}
		
		this.quesStoreService.addQuestion(question, knowledgeIds, map);
				
		responseObj.setSuccess(true);
		responseObj.setResultObject(question);
		responseObj.setErrorMessage("操作成功");
		return responseObj;
	}

	/**
	 * 通过题目主键id从知识点题目关系对应表获取该题目的所有知识点name
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getKnowledgePointsName")
	@ResponseBody
	public List<String> getKnowledgePointsName(HttpServletRequest request,
	        String id) {
		List<String> list = new ArrayList<String>();
		list = quesStoreService.getKnowledgePointsName(id);
		return list;
	}
	
	/**
	 * 删除题,状态置为1,同时删除题库知识点关联表中的数据
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	@OperationLogger(idparameterName = "id",description = "删除题",
    operationType = OperationLog.OP_DELETE,
    systemType = OperationLog.SYSTEM_KNOWLEDGE_CENTER, tableName = "question")
	public ResponseObject del(HttpServletRequest request, Model model) {
		ResponseObject responseObj = new ResponseObject();

		try {
			String id = ServletRequestUtils.getRequiredStringParameter(request,"id");
			String chapterIds = ServletRequestUtils.getRequiredStringParameter(request,"chapterIds");
			quesStoreService.deleteById(id,chapterIds);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("操作成功");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			responseObj.setSuccess(false);
			responseObj.setErrorMessage("操作失败");
		}
		return responseObj;
	}
	
	//批量删除
   @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids,String chapterIds) throws InvocationTargetException, IllegalAccessException {
         ResponseObject responseObject=new ResponseObject();
         if(ids!=null) {
              String[] _ids = ids.split(",");
              for(String id :_ids){
            	  quesStoreService.deleteById(id,chapterIds);  
              }
             
         }
         responseObject.setSuccess(true);
         responseObject.setErrorMessage("删除成功!");
         return responseObject;
    }
   
   	//批量启用
   	@RequestMapping(value = "enbaleStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject enbaleStatus(String ids) throws InvocationTargetException, IllegalAccessException {
         ResponseObject responseObject=new ResponseObject();
         if(ids!=null) {
              String[] _ids = ids.split(",");
              for(String id :_ids){
            	  quesStoreService.updateEnbaleStatus(id);  
              }
             
         }
         responseObject.setSuccess(true);
         responseObject.setErrorMessage("操作成功!");
         return responseObject;
    }
   	
	//批量禁用
   	@RequestMapping(value = "disableStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject disableStatus(String ids) throws InvocationTargetException, IllegalAccessException {
         ResponseObject responseObject=new ResponseObject();
         if(ids!=null) {
              String[] _ids = ids.split(",");
              for(String id :_ids){
            	  quesStoreService.updateDisableStatus(id);  
              }
             
         }
         responseObject.setSuccess(true);
         responseObject.setErrorMessage("操作成功!");
         return responseObject;
    }
   
	/**
	 * 代码题附件下载
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/questionCodeAttachmentDownload")
	public void questionCodeAttachmentDownload(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception {

		String fileName = ServletRequestUtils.getRequiredStringParameter(request,"fileName");
		
			String agent = request.getHeader("USER-AGENT");
			if (null != agent) {
				if (-1 != agent.indexOf("Firefox")) {// Firefox
					fileName = "=?UTF-8?B?"+ (new String(Base64.encode(fileName.getBytes("UTF-8"))))+ "?=";
				} else if (-1 != agent.indexOf("Chrome")) {// Chrome
					fileName = new String(fileName.getBytes(), "ISO8859-1");
				} else {// IE7+
					fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
					fileName = StringUtils.replace(fileName, "+", "%20");// 替换空格
				}
			}
		

		response.setHeader("Content-Type", "application/octet-stream");
		response.setHeader("Content-Disposition","attachment;filename=" + fileName);
		String id = ServletRequestUtils.getRequiredStringParameter(request,"id");
		OutputStream out = null;
		try {
			out = response.getOutputStream();

			byte[] b = attachmentCenterService.getAttachmentData(id);
			out.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					out = null;
				}
			}
		}

	}
	
	  /**
		 * 导入excel时校验通过后批量保存信息
		 * 
		 * @param classId
		 * @param fileName
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "saveBatch", method = RequestMethod.POST)
		@ResponseBody
		public String saveBatch(String fileName, String courseName, HttpServletRequest request) {

			// 获得登陆成功的token
			Token token = UCCookieUtil.readTokenCookie(request);
			if (token == null) {
				return "请登录！";
			}

			Map<String, byte[]> zipMap = new HashMap<String, byte[]>();
			List<QuesStore> excelList = new ArrayList<QuesStore>();
			try {
				String savePath = request.getSession().getServletContext().getRealPath("/");
				String errorMsg = readZipFile(zipMap, savePath);
				if (errorMsg.length() > 0) {
					return errorMsg;
				}
				String xlsfilename = savePath + File.separatorChar + "temp/" + fileName;
				String extName = xlsfilename.substring(xlsfilename.lastIndexOf(".") + 1);
				extName = extName.toLowerCase();
				Sheet sheet = null;
				if ("xls".equals(extName)) {
					try {
						sheet = ExcelPoiUtil.getSheet(xlsfilename);
					} catch (Exception e) {
						return "获取xls sheet出错 :" + e.toString();
					}
				} else if ("xlsx".equals(extName)) {
					try {
						sheet = ExcelPoiUtil.getSheet2007(xlsfilename);
					} catch (Exception e) {
						return "获取xlsx sheet出错 :" + e.toString();
					}
				}
				deleteFile(xlsfilename);
				StringBuffer sBuffer = quesStoreService.checkXls(sheet, excelList,zipMap.keySet(),courseName);
				if (sBuffer != null && sBuffer.length() > 0) {
					return sBuffer.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return quesStoreService.saveBatch(excelList, zipMap, token.getTicket());
		}
		
		/**
		 * 读取zip文件压缩包
		 * 
		 * @param map
		 * @param savePath
		 */
		private String readZipFile(Map<String, byte[]> map, String savePath) {
			String errorMsg = "";
			String zipfilename = savePath + File.separatorChar + "temp/"
			        + "zipFile.zip";
			File file = new File(zipfilename);
			if (!file.exists()) {
				return errorMsg;
			}
			ZipFile zf = null;
			BufferedInputStream Bin = null;
			try {
				zf = new ZipFile(zipfilename, "gbk");
				for (Enumeration entries = zf.getEntries(); entries
				        .hasMoreElements();) {
					ZipEntry entry = ((ZipEntry) entries.nextElement());
					byte[] fileData = new byte[(int) entry.getSize()];
					Bin = new BufferedInputStream(zf.getInputStream(entry));
					Bin.read(fileData);
					map.put(entry.getName(), fileData);
				}
				Bin.close();
				zf.close();
				deleteFile(zipfilename);
			} catch (Exception e) {
				e.printStackTrace();
				deleteFile(zipfilename);
				return errorMsg="读取zip文件出错：" + e.getMessage();
			}
			return errorMsg;
		}
		
		/**
		 * 删除上传附件的临时文件
		 * 
		 * @param fileName
		 */
		private void deleteFile(String fileName) {
			try {
				File file = new File(fileName);
				if (file.exists()) {
					file.delete();
				}
			} catch (Exception e) {
			}
		}
		
		/**
		 * 题库模板下载
		 * 
		 * @param request
		 * @param response
		 */
		@RequestMapping(value = "/questionExcelTemplateDownload")
		public void questionExcelTemplateDownload(HttpServletRequest request,HttpServletResponse response) {

			response.setHeader("Content-disposition","attachment;filename=question.xlsx");
			response.setContentType("multipart/form-data");

			OutputStream out = null;
			try {
				out = response.getOutputStream();

				String path = request.getServletContext().getRealPath("/");
				String fileName = path+ "/WEB-INF/template/批量录题模板.xlsx";
				File f = new File(fileName);
				byte[] b = FileUtil.readFromFile(f);
				out.write(b);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						out = null;
					}
				}
			}

		}

}
