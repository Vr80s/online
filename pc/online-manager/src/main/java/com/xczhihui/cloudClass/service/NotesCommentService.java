package com.xczhihui.cloudClass.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.cloudClass.vo.NotesCommentVo;

public interface NotesCommentService {

	public Page<NotesCommentVo> findNotesCommentPage(NotesCommentVo notesCommentVo, Integer pageNumber, Integer pageSize);
	
	/**
	 * 逻辑批量删除
	 * 
	 *@return void
	 */
	public void deletes(String[] ids);
}
