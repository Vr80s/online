package com.xczhihui.bxg.online.manager.cloudClass.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.common.domain.Files;
import com.xczhihui.bxg.online.manager.cloudClass.service.PPTResService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

/**
 * PPT资源控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("cloudclass/ppt")
public class PPTResController {
	@Autowired
	PPTResService pptResService;
	
	/**
     * 获取PPT列表信息，根据课程ID号查找
     * @return
     */
    @RequestMapping(value = "/findpptList")
    @ResponseBody
    public TableVo findPPTList(TableVo tableVo){
    	  int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          String params = tableVo.getsSearch();
          Groups groups = Tools.filterGroup(params);
          Group courseIdSearch = groups.findByName("courseId");
          Group chapterIdSearch = groups.findByName("chapterId");
       
          Files searchVo=new Files();
          searchVo.setCourseId(Integer.valueOf(courseIdSearch.getPropertyValue1().toString()));
          if(chapterIdSearch!=null){
        	  searchVo.setChapterId(chapterIdSearch.getPropertyValue1().toString());
          }
          Page<Files> page= pptResService.findPage(searchVo, currentPage, pageSize);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
    	
    }
    
    /**
     * 获取案例列表信息，根据课程ID号查找
     * @return
     */
    @RequestMapping(value = "/findcaseList")
    @ResponseBody
    public TableVo findCaseList(TableVo tableVo,HttpServletRequest request){
    	  StringBuffer url = request.getRequestURL();  
    	  String domain = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
    	  int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          String params = tableVo.getsSearch();
          Groups groups = Tools.filterGroup(params);
          Group courseIdSearch = groups.findByName("courseId");
          Group chapterIdSearch = groups.findByName("chapterId");
       
          Files searchVo=new Files();
          searchVo.setCourseId(Integer.valueOf(courseIdSearch.getPropertyValue1().toString()));
          if(chapterIdSearch!=null){
        	  searchVo.setChapterId(chapterIdSearch.getPropertyValue1().toString());
          }
          Page<Files> page= pptResService.findCasePage(searchVo, currentPage, pageSize,domain);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
    	
    }
    
    
    @RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject add(Files resourcePpt) {
		ResponseObject responseObject = new ResponseObject();
		String message ="";
		resourcePpt.setDelete(false);
		// resourcePpt.setName(StringEscapeUtils.escapeHtml(resourcePpt.getName()));
			resourcePpt.setCreateTime(new Date());
			resourcePpt.setCreatePerson(UserHolder.getCurrentUser().getLoginName());
			resourcePpt.setStatus(0);
			if(".ppt".equals(resourcePpt.getSuffix())||".pptx".equals(resourcePpt.getSuffix())){
				resourcePpt.setType(0);
				message="新增PPT成功!";
			}else{
				resourcePpt.setType(1);
				message="新增案例成功!";
			}
			
			pptResService.save(resourcePpt);
			responseObject.setSuccess(true);
			responseObject.setErrorMessage(message);
		
		return responseObject;
	}
    
    @RequestMapping(value = "updatePPT", method = RequestMethod.POST)
   	@ResponseBody
   	public ResponseObject updatePPT(Files resourcePpt,String isuploadFile) {
   		ResponseObject responseObject = new ResponseObject();

		pptResService.updatePPT(resourcePpt,isuploadFile);
		responseObject.setSuccess(true);
		responseObject.setErrorMessage("修改成功！");
   		
   		return responseObject;
   	}
    
    @RequestMapping(value = "deletePPTById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletePPTById(String id) {
   	 ResponseObject responseObj = new ResponseObject();
		
   	    pptResService.deletePPTById(id);
		responseObj.setSuccess(true);
		responseObj.setErrorMessage("删除成功");
		return responseObj;
    }
    
    
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException, IllegalAccessException {
         ResponseObject responseObject=new ResponseObject();
         if(ids!=null) {
              String[] _ids = ids.split(",");
              pptResService.deletes(_ids);
         }
         responseObject.setSuccess(true);
         responseObject.setErrorMessage("删除成功!");
         return responseObject;
    }

	  /**
   * 禁用or启用
   * @param request
   * @param model
   * @return
   */
  @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
  @ResponseBody
  public ResponseObject updateStatus(String id) {
       ResponseObject responseObj = new ResponseObject();
       try{
    	   pptResService.updateStatus(id);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
       }catch(Exception e){
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
       }
       return responseObj;
  }
  
  @RequestMapping(value = "previewmd")
  @ResponseBody
  public void previewmd(HttpServletResponse res,String id) throws Exception {
	  String html = pptResService.mdFile2Html(id);
	  res.setHeader("content-type", "text/html;charset=UTF-8");
	  res.getWriter().write(html);
	  res.flushBuffer();
  }
  
}
