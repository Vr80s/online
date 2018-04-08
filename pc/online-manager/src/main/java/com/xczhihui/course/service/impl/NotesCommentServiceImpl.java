package com.xczhihui.course.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Notes;
import com.xczhihui.bxg.online.common.domain.NotesComment;

import com.xczhihui.course.vo.NotesCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.course.dao.NotesCommentDao;
import com.xczhihui.course.service.NotesCommentService;

@Service
public class NotesCommentServiceImpl extends OnlineBaseServiceImpl implements NotesCommentService {

	@Autowired
    private NotesCommentDao notesCommentDao;
	
	@Override
	public Page<NotesCommentVo> findNotesCommentPage(NotesCommentVo notesCommentVo, Integer pageNumber, Integer pageSize) {
		Page<NotesCommentVo> page = notesCommentDao.findNotesCommentPage(notesCommentVo, pageNumber, pageSize);
		return page;
	}

	@Override
	public void deletes(String[] ids) {
		// TODO Auto-generated method stub
		for(String id:ids){
			String hqlPre="from NotesComment where isDelete=0 and id = ?";
			NotesComment notesComment= dao.findByHQLOne(hqlPre,new Object[] {id});
            if(notesComment !=null){
            	notesComment.setDelete(true);
                dao.update(notesComment);
            }
            hqlPre = " from Notes where id = ? ";
            Notes notes = dao.findByHQLOne(hqlPre,new Object[] {notesComment.getNotesId()});
            notes.setCommentSum((notes.getCommentSum() - 1));
            dao.update(notes);            
        }
	}
}
