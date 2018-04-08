package com.xczhihui.course.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.course.vo.NotesCommentVo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class NotesCommentDao extends SimpleHibernateDao {

	public Page<NotesCommentVo> findNotesCommentPage(NotesCommentVo notesCommentVo, Integer pageNumber, Integer pageSize){
		   Map<String,Object> paramMap=new HashMap<String,Object>();
		   StringBuilder sql=new StringBuilder("select " +
											   "	onc.id, " +
											   "	onc.content, " +
											   "	onc.create_person " +
											   "from " +
											   "	oe_notes_comment onc where onc.is_delete = 0 ");
		   if(notesCommentVo.getNotesId() != null){
			   sql.append(" and onc.notes_id = :notesId");
			   paramMap.put("notesId", notesCommentVo.getNotesId());
		   }
		   Page<NotesCommentVo> ms = this.findPageBySQL(sql.toString(), paramMap, NotesCommentVo.class, pageNumber, pageSize);
      	   return ms;
	}
}

