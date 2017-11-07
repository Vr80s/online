package com.xczhihui.bxg.online.manager.cloudClass.service;

import java.util.List;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.NotesVo;

public interface NotesService {

	public Page<NotesVo> findNotesPage(NotesVo notesVo, Integer pageNumber, Integer pageSize);
	
	/**
	 * 根据条件分页获取课程信息。
	 * 
	 * @param groups
	 * @param pageVo
	 * @return
	 */
    public Page<CourseVo> findCoursePage(CourseVo courseVo,  int pageNumber, int pageSize);
	
	/**
	 * 逻辑批量删除
	 * 
	 *@return void
	 */
	public void deletes(String[] ids);

}
