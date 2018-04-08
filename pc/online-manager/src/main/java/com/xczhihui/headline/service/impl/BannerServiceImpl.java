package com.xczhihui.headline.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.headline.dao.BannerDao;
import com.xczhihui.headline.service.ArticleService;
import com.xczhihui.headline.service.BannerService;
import com.xczhihui.headline.vo.ArticleVo;


@Service
public class BannerServiceImpl extends OnlineBaseServiceImpl implements BannerService {
	@Autowired
	private BannerDao bannerDao;
	@Autowired
	private ArticleService articleService;
	@Override
	public Page<ArticleVo> findBannerPage(ArticleVo articleVo, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		Page<ArticleVo> page = bannerDao.findBannerPage(articleVo, currentPage, pageSize);
		return page;
	}
	@Override
	public void updateSortUp(Integer id) {
		// TODO Auto-generated method stub
		//查询排序相邻的两个文章分类
        String sql = "select id,title,create_time,status,sort from oe_bxs_article where id=?";
        List<Map<String,Object>> articleList = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql,id);
        Map<String,Object> vo = articleList.size()>0? articleList.get(0):null;
        String preId = vo.get("id").toString();
        int sort = Integer.parseInt(vo.get("sort").toString());
        String nextSql = "select id,title,create_time,status,sort from oe_bxs_article  where is_recommend =1 and sort >"+ sort +" order by sort asc limit 1";
        List<Map<String,Object>> nextArticleList = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(nextSql);
        Map<String,Object> nextVo = nextArticleList.size()>0? nextArticleList.get(0):null;
        String nextId = nextVo.get("id").toString();
        int nextSort = Integer.parseInt(nextVo.get("sort").toString());
        if(vo!=null && nextVo!=null){
            //交换更新sort
            String edit_sql = "update oe_bxs_article set sort=? where id=?";
            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(edit_sql, nextSort, preId);
            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(edit_sql, sort, nextId);
        }
	}
	@Override
	public void updateSortDown(Integer id) {
		// TODO Auto-generated method stub
		 String sql = "select id,title,create_time,status,sort from oe_bxs_article where id=?";
	        List<Map<String,Object>> articleList = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql,id);
	        Map<String,Object> vo = articleList.size()>0? articleList.get(0):null;
	        String preId = vo.get("id").toString();
	        int sort = Integer.parseInt(vo.get("sort").toString());
	        String nextSql = "select id,title,create_time,status,sort from oe_bxs_article  where is_recommend =1 and sort <"+ sort +" order by sort desc limit 1";
	        List<Map<String,Object>> nextArticleList = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(nextSql);
	        Map<String,Object> nextVo = nextArticleList.size()>0? nextArticleList.get(0):null;
	        String nextId = nextVo.get("id").toString();
	        int nextSort = Integer.parseInt(nextVo.get("sort").toString());
	        if(vo!=null && nextVo!=null){
	            //交换更新sort
	            String edit_sql = "update oe_bxs_article set sort=? where id=?";
	            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(edit_sql, nextSort, preId);
	            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(edit_sql, sort, nextId);
	        }
	}
	@Override
	public void updateBannerPath(ArticleVo articleVo) {
		// TODO Auto-generated method stub
		String sql="update oe_bxs_article SET banner_path =:bannerPath where id =:id";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("id",articleVo.getId());
		param.put("bannerPath",articleVo.getBannerPath());
		bannerDao.getNamedParameterJdbcTemplate().update(sql, param);
	}
	@Override
	public void updateRec(Integer id) {
		// TODO Auto-generated method stub
		ArticleVo vo=articleService.findArticleById(id);
		 if(vo.getIsRecommend()!=null&&vo.getIsRecommend()){
			 vo.setIsRecommend(false);
       }else{
       	  throw new IllegalArgumentException("已取消推荐");
       }
		 
		String sql="update oe_bxs_article SET is_recommend =:recommend  where id =:id";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", vo.getId());
		param.put("recommend", vo.getIsRecommend());
		bannerDao.getNamedParameterJdbcTemplate().update(sql, param);
	}

	
		
		
	
	
	


		
	
}
