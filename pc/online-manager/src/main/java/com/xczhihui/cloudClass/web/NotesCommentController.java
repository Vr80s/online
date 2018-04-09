package com.xczhihui.cloudClass.web;

import com.xczhihui.cloudClass.service.NotesCommentService;
import com.xczhihui.cloudClass.vo.NotesCommentVo;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;

@Controller
@RequestMapping(value = "/cloudClass/notesComment")
public class NotesCommentController {

	@Autowired
	private NotesCommentService notesCommentService;

	// @RequiresPermissions("cloudClass:menu:notes")
	@RequestMapping(value = "/findNotesCommentList", method = RequestMethod.POST)
	@ResponseBody
	public TableVo findNotesCommentList(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);
		Group notesIdGroup = groups.findByName("notesId");

		NotesCommentVo searchVo = new NotesCommentVo();
		if (notesIdGroup != null) {
			searchVo.setNotesId(notesIdGroup.getPropertyValue1().toString());
		}
		Page<NotesCommentVo> page = notesCommentService.findNotesCommentPage(
				searchVo, currentPage, pageSize);

		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;
	}

	/**
	 * 批量逻辑删除
	 * 
	 * @param Integer
	 *            id
	 * @return
	 */
	@RequestMapping(value = "deletes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deletes(String ids) {
		ResponseObject responseObject = new ResponseObject();
		if (ids != null) {
			String[] _ids = ids.split(",");
			notesCommentService.deletes(_ids);
		}
		responseObject.setSuccess(true);
		responseObject.setErrorMessage("删除完成!");
		return responseObject;
	}
}
