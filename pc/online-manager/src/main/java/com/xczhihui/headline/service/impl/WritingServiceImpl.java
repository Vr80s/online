package com.xczhihui.headline.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorWritings;
import com.xczhihui.bxg.online.common.domain.MedicalWritings;
import com.xczhihui.headline.dao.ArticleDao;
import com.xczhihui.headline.dao.WritingDao;
import com.xczhihui.headline.service.WritingService;
import com.xczhihui.headline.vo.ArticleTypeVo;
import com.xczhihui.headline.vo.ArticleVo;
import com.xczhihui.headline.vo.TagVo;
import com.xczhihui.headline.vo.WritingVo;
@Service("writingService")
public class WritingServiceImpl extends OnlineBaseServiceImpl  implements WritingService{
	
	@Autowired
	ArticleDao articleDao;
	
	@Autowired
	WritingDao writingDao;
	
	@Override
	public Page<MedicalWritings> findCoursePage(WritingVo searchVo, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		Page<MedicalWritings> page = writingDao.findCloudClassCoursePage(searchVo, currentPage, pageSize);
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
	public void addWriting(WritingVo writingVo) {
		// TODO Auto-generated method stub
		
		/**
		 * 新增文章
		 */
		ArticleVo articleVo = new ArticleVo();
		articleVo.setTitle(writingVo.getTitle());
		articleVo.setImgPath(writingVo.getImgPath());
		articleVo.setContent(writingVo.getContent());
		articleVo.setUserId(writingVo.getUserId());

		
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
		/**
		 * 新增著作
		 */
		String id = UUID.randomUUID().toString().replace("-","");
		writingVo.setId(id);
		writingVo.setArticleId(articleVo.getId()+"");
		/*String sql="insert into oe_bxs_article (title,content,type_id,img_path,user_id) values "
				+ "(:title,:content,:typeId,:imgPath,:userId) ";*/
		String sqlWritingVo="insert into medical_writings (id,author,title,buy_link,article_id) values "
				+ "(:id,:author,:title,:buyLink,:articleId) ";
		writingDao.getNamedParameterJdbcTemplate().update(sqlWritingVo, new BeanPropertySqlParameterSource(writingVo),kh );
        
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
	public WritingVo findWritingById(String id) {
		// TODO Auto-generated method stub
		return writingDao.findEntitiesByJdbc(WritingVo.class, "select * from medical_writings where id = '"+id+"'", new HashMap<String, Object>()).get(0);
	}
	@Override
	public void updateWriting(WritingVo writingVo) {
		
		ArticleVo articleVo = new ArticleVo();
		articleVo.setId(Integer.parseInt(writingVo.getArticleId()));
		articleVo.setTitle(writingVo.getTitle());
		articleVo.setImgPath(writingVo.getImgPath());
		articleVo.setContent(writingVo.getContent());
		articleVo.setUserId(writingVo.getUserId());
		
		List<ArticleVo> vos=articleDao.findEntitiesByJdbc(ArticleVo.class, "select * from oe_bxs_article where id!="+articleVo.getId()+" and title = '"+articleVo.getTitle()+"'", new HashMap<String, Object>());
		if(vos!=null&&vos.size()>0){
			throw new IllegalArgumentException("文章名称已存在！");
		}
		
		String sql="update oe_bxs_article SET title =:title ,content =:content,img_path =:imgPath,user_id =:userId where id =:id";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("id",articleVo.getId());
		param.put("title", articleVo.getTitle());
		param.put("content", articleVo.getContent());
		param.put("imgPath", articleVo.getImgPath());
		param.put("userId", articleVo.getUserId());
		articleDao.getNamedParameterJdbcTemplate().update(sql, param);
		
/*		String deleteSql="delete from article_r_tag where article_id=:articleId";
		Map<String,Object> paramDel=new HashMap<String,Object>();
		paramDel.put("articleId",articleVo.getId());
		articleDao.getNamedParameterJdbcTemplate().update(deleteSql, paramDel);
		addArticleTag(articleVo);*/
		
		
		/**
		 * 修改著作信息
		 */
		String sqlWriting="update medical_writings SET author =:author ,title =:title,buy_link =:buyLink where id =:id";
		Map<String,Object> params =new HashMap<String,Object>();
		params.put("id",writingVo.getId());
		params.put("title", writingVo.getTitle());
		params.put("author", writingVo.getAuthor());
		params.put("buyLink", writingVo.getBuyLink());
		articleDao.getNamedParameterJdbcTemplate().update(sqlWriting, params);
		
	}
	@Override
	public void deletes(String[] ids) {
		// TODO Auto-generated method stub
		for(String id:ids){
			
			WritingVo wv = findWritingById(id);
			
			//删除著作
			String deleteSqlwri="delete from medical_writings where id=:id";
			Map<String,Object> paramDelWri=new HashMap<String,Object>();
			paramDelWri.put("id",id);
			articleDao.getNamedParameterJdbcTemplate().update(deleteSqlwri, paramDelWri);
			
			//删除文章  
			String deleteSqlArt="delete from oe_bxs_article where id=:id";
			Map<String,Object> paramDelArt=new HashMap<String,Object>();
			paramDelArt.put("id",wv.getArticleId());
			articleDao.getNamedParameterJdbcTemplate().update(deleteSqlArt, paramDelArt);
			
			
			//删除标签
//			String deleteSql="delete from article_r_tag where article_id=:articleId";
//			Map<String,Object> paramDel=new HashMap<String,Object>();
//			paramDel.put("articleId",id);
//			articleDao.getNamedParameterJdbcTemplate().update(deleteSql, paramDel);
			
			//oe_bxs_appraise删除评论
			String deleteSqlAppraise="delete from oe_bxs_appraise where article_id=:articleId";
			Map<String,Object> paramDelAppraise=new HashMap<String,Object>();
			paramDelAppraise.put("articleId",wv.getArticleId());
			articleDao.getNamedParameterJdbcTemplate().update(deleteSqlAppraise, paramDelAppraise);
			
			//删除关联的信息
			String deleteSqlDocWriting="delete from medical_doctor_writings where writings_id=:writingsId";
			Map<String,Object> paramDelDocWriting=new HashMap<String,Object>();
			paramDelDocWriting.put("writingsId",id);
			articleDao.getNamedParameterJdbcTemplate().update(deleteSqlDocWriting, paramDelDocWriting);
		}
	}
	@Override
	public void updateStatus(String id) {
		
		 WritingVo wv =findWritingById(id);
		 if(wv.getStatus()!=null&&1==wv.getStatus()){
			 wv.setStatus(0);
         }else{
        	 wv.setStatus(1);
         }
	    String sql="update medical_writings SET status =:status  where id =:id";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("id",wv.getId());
		param.put("status", wv.getStatus());
		writingDao.getNamedParameterJdbcTemplate().update(sql, param);
		 
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
		
//		ArticleVo vo=findArticleById(id);
//		 if(vo.getIsRecommend()!=null&&!vo.getIsRecommend()){
//			 vo.setIsRecommend(true);
//        }else{
//        	  throw new IllegalArgumentException("已推荐");
//        }
//	 
//		List<ArticleVo> vos=articleDao.findEntitiesByJdbc(ArticleVo.class, "select * from oe_bxs_article where is_recommend = 1", new HashMap<String, Object>());
//		if(vos!=null&&vos.size()>=6){
//			  throw new IllegalArgumentException("最多推荐6个");
//		}
//		 
//		 String sql="update oe_bxs_article SET is_recommend =:recommend  where id =:id";
//			Map<String,Object> param=new HashMap<String,Object>();
//			param.put("id",vo.getId());
//			param.put("recommend", vo.getIsRecommend());
//			articleDao.getNamedParameterJdbcTemplate().update(sql, param);
	}
	@Override
	public void updateMedicalDoctorWritings(String id, String[] doctorId) {
		List<MedicalDoctorWritings> mhfs = dao.findEntitiesByProperty(MedicalDoctorWritings.class, "writingsId", id);
		for (int i = 0; i < mhfs.size(); i++) {
			dao.delete(mhfs.get(i));
		}
		if(doctorId!=null){
			for (int i = 0; i < doctorId.length; i++) {
				MedicalDoctorWritings medicalDoctorWritings = new MedicalDoctorWritings();
				
				String mid = UUID.randomUUID().toString().replace("-","");
				medicalDoctorWritings.setId(mid);
				
				
				medicalDoctorWritings.setDoctorId(doctorId[i]);
				medicalDoctorWritings.setWritingsId(id);
				medicalDoctorWritings.setCreateTime(new Date());
				dao.save(medicalDoctorWritings);
			}
		}
	}

}
