package com.xczhihui.bxg.online.manager.boxueshe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.boxueshe.dao.AppraiseDao;
import com.xczhihui.bxg.online.manager.boxueshe.service.AppraiseService;
import com.xczhihui.bxg.online.manager.boxueshe.vo.AppraiseVo;

@Service
public class AppraiseServiceImpl extends OnlineBaseServiceImpl implements AppraiseService{
	@Autowired
	private AppraiseDao appraiseDao;

	@Override
	public Page<AppraiseVo> findAppraisePage(AppraiseVo appraiseVo, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		Page<AppraiseVo> page = appraiseDao.findAppraisePage(appraiseVo, currentPage, pageSize);
		return page;
	}

	@Override
	public void deleteById(String id,String articleId) {
		// TODO Auto-generated method stub
		appraiseDao.deleteById(id);
		
		  //修改文章评论数
        String updateSql="update oe_bxs_article  set comment_sum=comment_sum-1 where  id=:articleId";
        appraiseDao.getNamedParameterJdbcTemplate().update(updateSql,new MapSqlParameterSource().addValue("articleId", articleId));
	}












}
