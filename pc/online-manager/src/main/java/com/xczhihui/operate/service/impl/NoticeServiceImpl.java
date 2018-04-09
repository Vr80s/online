package com.xczhihui.operate.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;

import com.xczhihui.operate.service.NoticeService;
import com.xczhihui.operate.vo.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Service;

import com.xczhihui.operate.dao.NoticeDao;

@Service
public class NoticeServiceImpl extends OnlineBaseServiceImpl implements
		NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	@Override
	public Page<NoticeVo> findNoticePage(NoticeVo noticeVo, Integer pageNumber,
			Integer pageSize) {
		Page<NoticeVo> page = noticeDao.findNoticePage(noticeVo, pageNumber,
				pageSize);
		return page;
	}

	@Override
	public void addNotice(NoticeVo noticeVo) {
		String sql = " INSERT INTO oe_notice (`notice_content`, `show_start_time`, `show_end_time`, `status`, `create_person`, `create_time`, `is_delete`) "
				+ " VALUES "
				+ " (:noticeContent, null, null, :status, :createPerson, now(), 0) ";
		noticeVo.setStatus(0);// 未发布
		noticeDao.getNamedParameterJdbcTemplate().update(sql,
				new BeanPropertySqlParameterSource(noticeVo));
	}

	@Override
	public void updateNotice(NoticeVo noticeVo) {
		String sql = " UPDATE oe_notice "
				+ " SET notice_content = :noticeContent " + " WHERE "
				+ "	`id` = :id";
		noticeDao.getNamedParameterJdbcTemplate().update(sql,
				new BeanPropertySqlParameterSource(noticeVo));
	}

	@Override
	public boolean updateStatus(NoticeVo noticeVo) {
		if (noticeVo.getStatus() == 1) {// 如果要发布 需要先验证是否有最新已经发布的 否则就异常
			String sql = "select count(1) from oe_notice t where t.is_delete = 0 and t.`status` = 1 ";
			if (noticeDao.queryForInt(sql) > 0) {
				throw new RuntimeException("同时只能发布一条公告！");
			}
		}

		String sql = " UPDATE oe_notice " + " SET status = :status ";
		if (noticeVo.getStatus() == 1) {
			sql += ",show_start_time = DATE_FORMAT(NOW(),'%Y-%m-%d'), show_end_time = null ";
		} else if (noticeVo.getStatus() == 2) {
			sql += ",show_end_time = DATE_FORMAT(NOW(),'%Y-%m-%d') ";
		}
		sql += " WHERE " + "	`id` = :id";
		return noticeDao.getNamedParameterJdbcTemplate().update(sql,
				new BeanPropertySqlParameterSource(noticeVo)) > 0;
	}

	@Override
	public void deletes(String[] ids) {
		NoticeVo noticeVo = new NoticeVo();
		for (String id : ids) {
			noticeVo.setId(id);
			noticeDao.getNamedParameterJdbcTemplate().update(
					" delete from oe_notice where id = :id",
					new BeanPropertySqlParameterSource(noticeVo));
		}
	}
}
