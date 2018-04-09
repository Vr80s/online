package com.xczhihui.cloudClass.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.cloudClass.vo.VideoResVo;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Chapter;
import com.xczhihui.common.dao.HibernateDao;

@Repository("videoResDao")
public class VideoResDao extends HibernateDao<VideoResVo> {

	public Page<VideoResVo> findCloudVideoResPage(VideoResVo searchVo,
			int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select v.* from oe_chapter c,oe_video v where v.course_id = "
						+ searchVo.getCourseId()
						+ " and c.course_id="
						+ searchVo.getCourseId()
						+ " and c.is_delete = 0 and v.is_delete = 0 and c.id=v.chapter_id");
		if (searchVo.getChapterId() != null) {
			sql.append(" and v.chapter_id in( ");
			String[] ids = searchVo.getChapterId().split(",");
			for (int i = 0; i < ids.length; i++) {
				if (i != 0) {
					sql.append(",");
				}
				sql.append("'" + ids[i] + "'");
			}
			sql.append(" ) ");
		}
		sql.append(" order by v.sort ");
		Page<VideoResVo> ms = this.findPageBySQL(sql.toString(), paramMap,
				VideoResVo.class, currentPage, pageSize);
		return ms;
	}

	public List<Chapter> findChapterByOrderId(String id, Integer order_id,
			Integer target_order_id) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(Chapter.class);
		dc.add(Restrictions.between("sort", order_id, target_order_id));
		dc.add(Restrictions.eq("id", id));
		dc.addOrder(Order.asc("sort"));
		return this.findEntities(dc);
	}

}
