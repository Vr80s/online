package com.xczhihui.course.service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.vo.NotesCommentVo;

public interface NotesCommentService {

	public Page<NotesCommentVo> findNotesCommentPage(
			NotesCommentVo notesCommentVo, Integer pageNumber, Integer pageSize);

	/**
	 * 逻辑批量删除
	 * 
	 * @return void
	 */
	public void deletes(String[] ids);
}
