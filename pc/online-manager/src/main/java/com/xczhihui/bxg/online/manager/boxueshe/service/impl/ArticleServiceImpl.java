package com.xczhihui.bxg.online.manager.boxueshe.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.manager.boxueshe.dao.ArticleDao;
import com.xczhihui.bxg.online.manager.boxueshe.service.ArticleService;
import com.xczhihui.bxg.online.manager.boxueshe.vo.ArticleTypeVo;
import com.xczhihui.bxg.online.manager.boxueshe.vo.ArticleVo;
import com.xczhihui.bxg.online.manager.boxueshe.vo.TagVo;
@Service("articleService")
public class ArticleServiceImpl implements ArticleService{
	
	@Autowired
	ArticleDao articleDao;
	@Override
	public Page<ArticleVo> findCoursePage(ArticleVo articleVo, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		Page<ArticleVo> page = articleDao.findCloudClassCoursePage(articleVo, currentPage, pageSize);
		return page;
	}
	@Override
	public List<ArticleTypeVo>  getArticleTypes(){
		
		return articleDao.findEntitiesByJdbc(ArticleTypeVo.class, "select * from article_type where status = 1", new HashMap<String, Object>());
	}
	@Override
	public List<TagVo>  getTags(){
		return articleDao.findEntitiesByJdbc(TagVo.class, "select * from oe_bxs_tag where status = 1", new HashMap<String, Object>());
	}
	@Override
	public void addArticle(ArticleVo articleVo) {
		// TODO Auto-generated method stub
		List<ArticleVo> vos=articleDao.findEntitiesByJdbc(ArticleVo.class, "select * from oe_bxs_article where title = '"+articleVo.getTitle()+"'", new HashMap<String, Object>());
		if(vos!=null&&vos.size()>0){
			throw new IllegalArgumentException("文章名称已存在！");
		}
		
		String sql="insert into oe_bxs_article (title,content,type_id,img_path,user_id) values "
				+ "(:title,:content,:typeId,:imgPath,:userId) ";
	
		KeyHolder kh=new GeneratedKeyHolder();
		
		
		articleDao.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(articleVo),kh );
		articleVo.setId(kh.getKey().intValue());
		
		String sqlupdate="update oe_bxs_article SET sort =:sort where id =:id";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("id",articleVo.getId());
		param.put("sort", articleVo.getId());
		articleDao.getNamedParameterJdbcTemplate().update(sqlupdate, param);
        
	}
	@Override
	public void addArticleTag(ArticleVo articleVo){
		String[] tagIds = articleVo.getTagId().split(",");
		for(String tagId :tagIds){
			String sql="insert into article_r_tag (id,tag_id,article_id) values "
					+ "(:id,:tagId,:articleId);";
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("id", UUID.randomUUID().toString().replace("-",""));
			param.put("tagId", tagId);
			param.put("articleId", articleVo.getId());
			articleDao.getNamedParameterJdbcTemplate().update(sql, param);
		}
	}
	@Override
	public ArticleVo findArticleById(Integer id) {
		// TODO Auto-generated method stub
		return articleDao.findEntitiesByJdbc(ArticleVo.class, "select * from oe_bxs_article where id = "+id, new HashMap<String, Object>()).get(0);
	}
	@Override
	public void updateArticle(ArticleVo articleVo) {
		// TODO Auto-generated method stub
		List<ArticleVo> vos=articleDao.findEntitiesByJdbc(ArticleVo.class, "select * from oe_bxs_article where id!="+articleVo.getId()+" and title = '"+articleVo.getTitle()+"'", new HashMap<String, Object>());
		if(vos!=null&&vos.size()>0){
			throw new IllegalArgumentException("文章名称已存在！");
		}
		
		String sql="update oe_bxs_article SET title =:title ,content =:content,type_id =:typeId,img_path =:imgPath,user_id =:userId where id =:id";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("id",articleVo.getId());
		param.put("title", articleVo.getTitle());
		param.put("content", articleVo.getContent());
		param.put("typeId", articleVo.getTypeId());
		param.put("imgPath", articleVo.getImgPath());
		param.put("userId", articleVo.getUserId());
		articleDao.getNamedParameterJdbcTemplate().update(sql, param);
		
		String deleteSql="delete from article_r_tag where article_id=:articleId";
		Map<String,Object> paramDel=new HashMap<String,Object>();
		paramDel.put("articleId",articleVo.getId());
		articleDao.getNamedParameterJdbcTemplate().update(deleteSql, paramDel);
		
		addArticleTag(articleVo);
		
	}
	@Override
	public void deletes(String[] ids) {
		// TODO Auto-generated method stub
		for(String id:ids){
			//删除文章
			String deleteSqlArt="delete from oe_bxs_article where id=:id";
			Map<String,Object> paramDelArt=new HashMap<String,Object>();
			paramDelArt.put("id",id);
			articleDao.getNamedParameterJdbcTemplate().update(deleteSqlArt, paramDelArt);
			//删除标签
			String deleteSql="delete from article_r_tag where article_id=:articleId";
			Map<String,Object> paramDel=new HashMap<String,Object>();
			paramDel.put("articleId",id);
			articleDao.getNamedParameterJdbcTemplate().update(deleteSql, paramDel);
			//oe_bxs_appraise删除评论
			String deleteSqlAppraise="delete from oe_bxs_appraise where article_id=:articleId";
			Map<String,Object> paramDelAppraise=new HashMap<String,Object>();
			paramDelAppraise.put("articleId",id);
			articleDao.getNamedParameterJdbcTemplate().update(deleteSqlAppraise, paramDelAppraise);
		}
	}
	@Override
	public void updateStatus(Integer id) {
		// TODO Auto-generated method stub
		ArticleVo vo=findArticleById(id);
		 if(vo.getStatus()!=null&&1==vo.getStatus()){
			 vo.setStatus(0);
         }else{
        	 vo.setStatus(1);
         }
		 
		 String sql="update oe_bxs_article SET status =:status  where id =:id";
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("id",vo.getId());
			param.put("status", vo.getStatus());
			articleDao.getNamedParameterJdbcTemplate().update(sql, param);
		 
	}
	@Override
	public void addPreArticle(ArticleVo articleVo) {
		// TODO Auto-generated method stub
		String sql="insert into oe_bxs_preview_article (title,content,type_id,img_path,user_id) values "
				+ "(:title,:content,:typeId,:imgPath,:userId) ";
	
		KeyHolder kh=new GeneratedKeyHolder();
		articleDao.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(articleVo),kh );
		articleVo.setId(kh.getKey().intValue());
	}
	@Override
	public void updateRecommend(Integer id) {
		// TODO Auto-generated method stub
		
		ArticleVo vo=findArticleById(id);
		if(vo.getIsRecommend()!=null&&!vo.getIsRecommend()){
			 vo.setIsRecommend(true);
        }else if(vo.getIsRecommend()!=null && vo.getIsRecommend()){
        	 vo.setIsRecommend(false);
        }
	 
//		List<ArticleVo> vos=articleDao.findEntitiesByJdbc(ArticleVo.class, "select * from oe_bxs_article where is_recommend = 1", new HashMap<String, Object>());
//		if(vos!=null&&vos.size()>=6){
//			  throw new IllegalArgumentException("最多推荐6个");
//		}
		 
		 String sql="update oe_bxs_article SET is_recommend =:recommend  where id =:id";
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("id",vo.getId());
			param.put("recommend", vo.getIsRecommend());
			articleDao.getNamedParameterJdbcTemplate().update(sql, param);
	}

}
