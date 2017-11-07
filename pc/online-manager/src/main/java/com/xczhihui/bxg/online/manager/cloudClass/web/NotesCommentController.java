package com.xczhihui.bxg.online.manager.cloudClass.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;


import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.manager.ask.vo.AccuseVo;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

import com.xczhihui.bxg.online.manager.cloudClass.service.NotesCommentService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.NotesCommentVo;

@Controller
@RequestMapping(value = "/cloudClass/notesComment")
public class NotesCommentController{
	
	@Autowired
	private NotesCommentService notesCommentService;
	
	@RequiresPermissions("cloudClass:menu:notes")
    @RequestMapping(value = "/findNotesCommentList", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findNotesCommentList(TableVo tableVo) {
         int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();
         Groups groups = Tools.filterGroup(params);
         Group notesIdGroup = groups.findByName("notesId");
         
         NotesCommentVo searchVo=new NotesCommentVo();
         if(notesIdGroup!=null){
              searchVo.setNotesId(notesIdGroup.getPropertyValue1().toString());
         }
         Page<NotesCommentVo> page = notesCommentService.findNotesCommentPage(searchVo, currentPage, pageSize);

         int total = page.getTotalCount();
         tableVo.setAaData(page.getItems());
         tableVo.setiTotalDisplayRecords(total);
         tableVo.setiTotalRecords(total);
         return tableVo;
    }
	
	/**
	 * 批量逻辑删除
	 * @param Integer id
	 * @return
	 */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) {
         ResponseObject responseObject=new ResponseObject();
         if(ids!=null) {
              String[] _ids = ids.split(",");
              notesCommentService.deletes(_ids);
         }
         responseObject.setSuccess(true);
         responseObject.setErrorMessage("删除完成!");
         return responseObject;
    }
}
