package com.xczhihui.operate.dao;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.operate.vo.NoticeVo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class NoticeDao extends SimpleHibernateDao {

	public Page<NoticeVo> findNoticePage(NoticeVo noticeVo, Integer pageNumber,
			Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(" SELECT " + "	ono.id, "
				+ "	ono.notice_content, " + "	ono.show_start_time, "
				+ "	ono.show_end_time, " + "	ono.`status`, "
				+ "	ou.`name` createPersonName" + " FROM " + "	oe_notice ono "
				+ " JOIN user ou ON ono.create_person = ou.login_name "
				+ " where 1 = 1 ");

		if (noticeVo.getNoticeContent() != null
				&& !"".equals(noticeVo.getNoticeContent())) {
			sql.append(" and ono.notice_content like :noticeContent");
			paramMap.put("noticeContent", "%" + noticeVo.getNoticeContent()
					+ "%");
		}

		if (noticeVo.getStatus() != null) {
			sql.append(" and ono.status = :status ");
			paramMap.put("status", noticeVo.getStatus());
		}

		sql.append(" order by field(status,1,0,2),ono.create_time desc");
		Page<NoticeVo> ms = this.findPageBySQL(sql.toString(), paramMap,
				NoticeVo.class, pageNumber, pageSize);
		return ms;
	}
}
