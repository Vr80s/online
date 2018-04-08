package com.xczhihui.ask.web;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.ask.service.TagService;
import com.xczhihui.ask.vo.TagVo;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;

/**
 * 博问答控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("ask/tag")
public class AskTagController {

	@Autowired
    TagService tagService;
	@RequestMapping(value = "/tagIndex" , method = RequestMethod.GET)
	public ModelAndView student(HttpServletRequest request,String page,String menuName,String menuId){
		 ModelAndView mav=new ModelAndView("ask/tag");
		 mav.addObject("page", page);
	     mav.addObject("menuId", menuId);
	     mav.addObject("MenuName", menuName);
	     return mav;
	}
	
	/**
     * 获取班级列表信息，根据课程ID号查找
     * @return
     */
    @RequestMapping(value = "/findTagList")
    @ResponseBody
    public TableVo findTagList(TableVo tableVo){
    	  int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          String params = tableVo.getsSearch();
          Groups groups = Tools.filterGroup(params);
          Group menuId = groups.findByName("menuId");
          Group NameSearch  = groups.findByName("NameSearch");
          Group quesSortSearch  = groups.findByName("quesSort");
          Group citesSortSearch  = groups.findByName("citesSort");
          Group monthSortSearch  = groups.findByName("monthSort");
         /*Group NameSearch = groups.findByName("NameSearch");
          Group isPayment = groups.findByName("isPayment");*/

          TagVo searchVo=new TagVo();

          if(menuId!=null){
              searchVo.setMenuId(menuId.getPropertyValue1().toString());
          }
           if(NameSearch!=null){
              searchVo.setName(NameSearch.getPropertyValue1().toString());
          }
           if(quesSortSearch!=null){
               searchVo.setQuesSort(quesSortSearch.getPropertyValue1().toString());
           }
           
           if(citesSortSearch!=null){
               searchVo.setCitesSort(citesSortSearch.getPropertyValue1().toString());
           }
           
           if(monthSortSearch!=null){
               searchVo.setMonthSort(monthSortSearch.getPropertyValue1().toString());
           }
           
           /* if(isPayment!=null){
              searchVo.setIsPayment(isPayment.getPropertyValue1().toString());
          }*/
          Page<TagVo> page= tagService.findPage(searchVo, currentPage, pageSize);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
    	
    }
    
    /**
	 * 添加
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "addTag", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject add(TagVo tagVo){
		ResponseObject responseObj = new ResponseObject();
		try{
			String message = tagService.addTag(tagVo);
			if("新增成功！".equals(message)){
	            responseObj.setSuccess(true);
	            responseObj.setErrorMessage(message);
			}else{
				responseObj.setSuccess(false);
	            responseObj.setErrorMessage(message);
			}
           
       }catch(Exception e){
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("新增失败");
            e.printStackTrace();
       }
        return responseObj;
	}
    
	
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException, IllegalAccessException {
         ResponseObject responseObject=new ResponseObject();
         String msg = "";
         if(ids!=null) {
              String[] _ids = ids.split(",");
              msg = tagService.deletes(_ids);
         }
         responseObject.setSuccess(true);
         responseObject.setErrorMessage(msg);
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
        	 tagService.updateStatus(id);
              responseObj.setSuccess(true);
              responseObj.setErrorMessage("操作成功");
         }catch(Exception e){
              responseObj.setSuccess(false);
              responseObj.setErrorMessage("操作失败");
         }
         return responseObj;
    }
    
    
    /**
     * 上移
     * @param id
     * @return
     */
    @RequestMapping(value = "up", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject up(String id) {
         ResponseObject responseObj = new ResponseObject();
         try{
        	 tagService.updateDirectionUp(id);
              responseObj.setSuccess(true);
              responseObj.setErrorMessage("操作成功");
         }catch(Exception e){
              responseObj.setSuccess(false);
              responseObj.setErrorMessage("操作失败");
              e.printStackTrace();
         }
         return responseObj;
    }
    
    /**
     * 下移
     * @param id
     * @return
     */
    @RequestMapping(value = "down", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject down(String id) {
         ResponseObject responseObj = new ResponseObject();
         try{
        	 tagService.updateDirectionDown(id);
              responseObj.setSuccess(true);
              responseObj.setErrorMessage("操作成功");
         }catch(Exception e){
              responseObj.setSuccess(false);
              responseObj.setErrorMessage("操作失败");
         }
         return responseObj;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject update(TagVo tagVo) throws InvocationTargetException, IllegalAccessException {
         ResponseObject responseObject=new ResponseObject();
         tagService.updateTag(tagVo);
         responseObject.setSuccess(true);
         responseObject.setErrorMessage("修改成功!");
         return responseObject;
    }
    
}
