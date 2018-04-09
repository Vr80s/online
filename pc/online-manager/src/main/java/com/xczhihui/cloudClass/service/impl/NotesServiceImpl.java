package com.xczhihui.cloudClass.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.cloudClass.dao.NotesDao;
import com.xczhihui.cloudClass.vo.CourseVo;
import com.xczhihui.cloudClass.vo.NotesVo;
import com.xczhihui.cloudClass.service.NotesService;

@Service
public class NotesServiceImpl extends OnlineBaseServiceImpl implements
		NotesService {

	@Autowired
	private NotesDao notesDao;

	@Override
	public Page<NotesVo> findNotesPage(NotesVo notesVo, Integer pageNumber,
			Integer pageSize) {
		Page<NotesVo> page = notesDao.findNotesPage(notesVo, pageNumber,
				pageSize);
		return page;
	}

	@Override
	public Page<CourseVo> findCoursePage(CourseVo courseVo, int pageNumber,
			int pageSize) {
		Page<CourseVo> page = notesDao.findCloudClassCoursePage(courseVo,
				pageNumber, pageSize);
		return page;

	}

	@Override
	public void deletes(String[] ids) {
		// TODO Auto-generated method stub
		for (String id : ids) {
			String hqlPre = "from Notes where isDelete=0 and id = ?";
			Notes notes = dao.findByHQLOne(hqlPre, new Object[] { id });
			if (notes != null) {
				notes.setDelete(true);
				dao.update(notes);
			}
		}
	}
}
